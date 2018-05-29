package com.sts.io;


import java.io.BufferedReader;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.model.Game;
import com.sts.model.Key;
import com.sts.model.Team;
import com.sts.model.exception.DuplicateTeamException;
import com.sts.model.exception.NegativeAttendanceException;
import com.sts.model.exception.NegativeScoreException;

// class to read from file with upcoming/finished games
public class GamesFileReader extends AbstractFileReader<Key,Game> {
    private Logger _logger;

    private static final String DELIM = "|";

   //HashMap containing all the teams detected from the games data file
    private ConcurrentHashMap<String, Team> _teamMaps;




    //Constructor
    public GamesFileReader() {
        _logger = LoggerFactory.getLogger(getClass().getSimpleName());
        _teamMaps = new ConcurrentHashMap<>();
    }





    /**
     * TODO:
     * TimeZone should be configurable or location dependent
     */
    private ZonedDateTime parseDate(String str_) {
        try {
            DateTimeFormatter formatter =DateTimeFormatter.ISO_DATE_TIME;
            return ZonedDateTime.parse(str_, formatter);
        }
        catch (DateTimeParseException exc) {
            _logger.error("{} is not parsable!", str_);
            throw exc;
        }
    }





    /**
     * TODO:
     * local should be configurable 
     */
    private int parseInteger(String str_) {
        try {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
            return nf.parse(str_).intValue();
        }
        catch (ParseException e_) {
            _logger.error(e_.toString(), e_);
            return -1;
        }
    }




    /*
     * Method to check and see if the team string that was tokenized already exists in the
     * team Hashmap
     * If it does already exist, team found
     * Else, put in hashmap
     */
    private Team parseTeam(String category, String cityStr_,String teamStr_) {
        Team lclTeam = _teamMaps.get(teamStr_);
        if (lclTeam == null) {
            lclTeam = new Team();
            lclTeam.setTeamName(teamStr_);
            lclTeam.setLocation(cityStr_);
            _teamMaps.put(teamStr_, lclTeam);
            if (_logger.isInfoEnabled())
                _logger.info("New team identified:{}. adding to mapping Team:{}", teamStr_, lclTeam);
        }

        // log it
        if (_logger.isTraceEnabled())
            _logger.trace("found team for key: {}", teamStr_);

        // done
        return lclTeam;
    }





    /** 
     * (non-Javadoc)
     * Method to tokenize lines from the game data file and add the game to the games list
     * if valid.
     */
    @Override
    void readFromFileForLists(Reader is_, AbstractMap<Key,Game> listOfGames_) {
        try (BufferedReader reader = new BufferedReader(is_)) {
            StringTokenizer tokenizer;
            String line;
            String team;
            String city;
            Game game;
            Team home;
            String category;
            
            while ((line = reader.readLine()) != null) {
                // don't process empty lines
                if ("".equals(line))
                    continue;

                //
                //
                //
                game = new Game();
                tokenizer = new StringTokenizer(line, DELIM);


                try {
                	game.setCategory(tokenizer.nextToken());
                	category = game.getCategory();
                }
                catch(Exception e_) {
                	_logger.error("setCategory:" + e_.toString());
                	continue;
                }

                //
                // Read in the date and set date in game object
                // since I don't know how long the game lasts, use
                // the default
                //
                
                try {
                    game.setStartTime(parseDate(tokenizer.nextToken()));
                }
                catch (Exception e_) {
                    _logger.error("setDate:" + e_.toString());
                    continue;
                }




                //
                // teams
                //
                
                try {
                	city = tokenizer.nextToken();
                	team = tokenizer.nextToken();
                    game.setAwayTeam(parseTeam(category,city,team));
                }
                catch (Exception e_) {
                    _logger.error("setAawayTeam:" + e_.toString());
                    continue;
                }

                try {
                	city = tokenizer.nextToken();
                	team = tokenizer.nextToken();
                    home = parseTeam(category,city, team);
                    if (home.equals(game.getAwayTeam()))
                        throw new DuplicateTeamException("Home team cannot be the same as away", home);
                    game.setHomeTeam(home);
                }
                catch (Exception e_) {
                    _logger.error("sethTeam:" + e_.toString());
                }





                //
                // future or past game?
                //
                if (game.getStartTime().isAfter(ZonedDateTime.now())) {
                    // this is a game in the future, do not process any more data
                    listOfGames_.put(new Key(game.getStartTime(), game.getAwayTeam()), game);
                    continue;
                }



                //
                // this game is in the past, read in the scores
                //
                try {
                	int awayScore = parseInteger(tokenizer.nextToken());
                	if(awayScore < 0) {
                		throw new NegativeScoreException();
                	}
                    game.setAwayTeamScore(awayScore);
                   
                }
                catch (Exception e_) {
                    _logger.error("setaTeamScore:" + e_.toString());
                }

                try {
                	int homeScore = parseInteger(tokenizer.nextToken());
                	if(homeScore < 0) {
                		throw new NegativeScoreException();
                	}
                    game.setHomeTeamScore(homeScore);                }
                catch (Exception e_) {
                    _logger.error("sethTeamScore:" + e_.toString());
                }

                try {
                	int attendance = parseInteger(tokenizer.nextToken());
                	if(attendance < 0) {
                		throw new NegativeAttendanceException();
                	}
                    game.setAttendence(attendance);
                    }
                catch (Exception e_) {
                    _logger.error("setAttendance:" + e_.toString());
                }
                try {
                	
                	game.setDuration(Duration.parse(tokenizer.nextToken()));
                }
                catch(Exception e_) {
                	_logger.error("setDuration:" + e_.toString());
                }
                addGame(game, (TreeMap<Key, Game>)listOfGames_);
            }
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }
    }
    
    
    
    /**
     * Add the game to the map of games if the game is valid
     */
    private void addGame(Game game_, TreeMap<Key,Game> listOfGames_)
    {
        if (!game_.isValidGame())
        {
            _logger.error("Refusing to add invalid game: {}", game_);
            return;
        }
        
        //
        //Add game object to the list of games provided
        //
        listOfGames_.put(new Key(game_.getStartTime(), game_.getAwayTeam()), game_);

        if (_logger.isTraceEnabled())
            _logger.trace("Adding new game to past list: {}", game_.toString());
    }






}
