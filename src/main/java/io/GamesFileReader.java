package io;


import java.io.BufferedReader;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.model.exception.DuplicateTeamException;

import model.Game;
import model.GamesList;
import model.Team;

// class to read from file with past/finished games
public class GamesFileReader extends AbstractFileReader<Game> {
    private Logger _logger;

    private static final String DELIM = "|";


    private ConcurrentHashMap<String, Team> _teamMaps;





    public GamesFileReader() {
        _logger = LoggerFactory.getLogger(getClass().getSimpleName());
        _teamMaps = new ConcurrentHashMap<>();
    }





    /**
     * TODO:
     * TimeZone should be configrable or location dependent
     */
    private ZonedDateTime parseDate(String str_) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-ddTkk:mm");
            return ZonedDateTime.parse(str_, formatter);
        }
        catch (DateTimeParseException exc) {
            _logger.error("%s is not parsable!%n", str_);
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





    private Team parseTeam(String teamStr_) {
        Team lclTeam = _teamMaps.get(teamStr_);
        if (lclTeam == null) {
            lclTeam = new Team();
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
     */
    @Override
    void readFromFileForLists(Reader is_, ArrayList<Game> listOfGames_) {
        try (BufferedReader reader = new BufferedReader(is_)) {
            StringTokenizer tokenizer;
            String line;
            Game game;
            Team home;
            while ((line = reader.readLine()) != null) {
                // don't process empty lines
                if ("".equals(line))
                    continue;

                //
                //
                //
                game = new Game();
                tokenizer = new StringTokenizer(line, DELIM);



                //
                // Read in the date and set date in game object
                // since I don't know how long the game lasts, use
                // the default
                //
                try {
                    game.setDate(parseDate(tokenizer.nextToken()));
                }
                catch (Exception e_) {
                    _logger.error("setDate:" + e_.toString());
                    continue;
                }




                //
                // teams
                //
                try {
                    game.setAwayTeam(parseTeam(tokenizer.nextToken()));
                }
                catch (Exception e_) {
                    _logger.error("setAawayTeam:" + e_.toString());
                    continue;
                }

                try {
                    home = parseTeam(tokenizer.nextToken());
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
                if (game.getDate().isAfter(ZonedDateTime.now())) {
                    // this is a game in the future, do not process any more data
                    continue;
                }



                //
                // this game is in the past, read in the scores
                //
                try {
                    game.setAwayTeamScore(parseInteger(tokenizer.nextToken()));
                }
                catch (Exception e_) {
                    _logger.error("setaTeamScore:" + e_.toString());
                }

                try {
                    game.setHomeTeamScore(parseInteger(tokenizer.nextToken()));
                }
                catch (Exception e_) {
                    _logger.error("sethTeamScore:" + e_.toString());
                }

                try {
                    game.setAttendence(parseInteger(tokenizer.nextToken()));
                }
                catch (Exception e_) {
                    _logger.error("setAttendance:" + e_.toString());
                }


                //
                //Add game object to the list of games provided
                //
                ((GamesList)listOfGames_).add(game);
                if (_logger.isTraceEnabled())
                    _logger.trace("Adding new game to past list: {}", game.toString());
            }
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }

    }
}
