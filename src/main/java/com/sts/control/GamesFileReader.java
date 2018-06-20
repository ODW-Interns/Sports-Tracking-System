package com.sts.control;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractGame;
import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.SportsCategory;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.Key;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;
import com.sts.mlb.models.MLBGame;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.model.exception.DuplicateTeamException;
import com.sts.model.exception.InvalidPlayersException;
import com.sts.model.exception.MismatchGameandTeamSportException;
import com.sts.model.exception.MismatchPlayerandGameSportException;
import com.sts.model.exception.NegativeAttendanceException;
import com.sts.model.exception.NegativeScoreException;
import com.sts.model.exception.PlayerNotOnTeamException;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.nba.models.NBAGame;
import com.sts.nfl.models.NFLGame;
import com.sts.nhl.models.NHLGame;

/**
 *  class to read from file with upcoming/finished games
 *
 */
public class GamesFileReader {
    private Logger _logger;

	private int tempGameId;

	private ZonedDateTime tempStartTime;

    private static final String DELIM = "|";
    
    private BufferedReader reader;

    //Constructor
    public GamesFileReader() {
    	reader = new BufferedReader(new InputStreamReader(System.in));
        _logger = LoggerFactory.getLogger(getClass().getSimpleName());
    }

    /*
     * Method to make sure file exists and the two objects(GamesList & TeamsList) have been created
     */
    public void readData(InputStream is_, GamesList gamelist_, TeamsList teamlist_, PlayersList playerslist_ ) throws FileNotFoundException, RuntimeException {
        if (is_ == null)
            throw new FileNotFoundException();

        if (gamelist_ == null)
            throw new RuntimeException("No list provided");
        if(teamlist_ == null)
        	throw new RuntimeException("No list provided");

        try (InputStreamReader sr = new InputStreamReader(is_)) {
            readFromFileForLists(sr, gamelist_, teamlist_, playerslist_);
       
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }
    }


    /**
     * Parse the date with the given string and returns the date and time
     * in the current system's time zone
     */
    public ZonedDateTime parseDate(String str_) {
        try {
            DateTimeFormatter formatter =DateTimeFormatter.ISO_DATE_TIME;
            // Get current system's time zone
            ZoneId defaultZone = ZoneId.systemDefault();
            ZonedDateTime inputTime = ZonedDateTime.parse(str_,  formatter);
            ZonedDateTime currentTime = inputTime.withZoneSameInstant(defaultZone);
            return currentTime;
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
     * Else, throw team not found exception
     */
    private AbstractTeam parseTeam(SportsCategory category, String cityStr_,String teamStr_, TeamsList teamsList_) throws TeamNotFoundException, MismatchGameandTeamSportException {
        String searchString = cityStr_ + " " + teamStr_;
    	AbstractTeam lclTeam = teamsList_.getTeamMap().get(searchString);
        if(lclTeam != null){
        	if(lclTeam.getTeamSport().equals(category)){
        		return lclTeam;
        	}
        	else
        		throw new MismatchGameandTeamSportException();
        }
        else 
        	throw new TeamNotFoundException(teamStr_);
       /* if (lclTeam == null) {
        	if(category.equals("NBA")) {
        		lclTeam = new TeamNBA();
        	}
        	else if(category.equals("NFL")) {
        		lclTeam = new TeamNFL();
        	}
        	else if(category.equals("NHL")) {
        		lclTeam = new TeamNHL();
        	}
        	else if(category.equals("MLB")) {
        		lclTeam = new TeamMLB();
        	}
            lclTeam.setTeamName(teamStr_);
            lclTeam.setLocation(cityStr_);
            lclTeam.setTeamSport(category);
            teamsList_.getTeamMap().put(teamStr_, lclTeam);
            if (_logger.isInfoEnabled())
                _logger.info("New team identified:{}. adding to mapping Team:{}", teamStr_, lclTeam);
        }

        // log it
        if (_logger.isTraceEnabled())
            _logger.trace("found team for key: {}", teamStr_);

        // done
        return lclTeam;*/
    }

    /**
     *  Parse the players from the tokenized string.
     *  These are the players that have played in the
     *  game.
     */
    private void parsePlayerIDs(String playerIDs_, AbstractGame game_, PlayersList playersList_, TeamsList teamsList_, String teamName_, ArrayList<Integer> listOfPlayers_) throws InvalidPlayersException, MismatchPlayerandGameSportException, PlayerNotOnTeamException {
    	StringTokenizer tokenizer;
    	tokenizer = new StringTokenizer(playerIDs_, ",");
    	int playerID;
    	while(tokenizer.hasMoreTokens()) {
    		playerID = Integer.parseInt(tokenizer.nextToken());
    		//Check to see if the player exists in the player map
    		if(playersList_.returnPlayersMap().get(playerID) == null) {
    			_logger.error("There does not exist a player with the ID:" + playerID + ". Player was not added to game");
    			throw new InvalidPlayersException();
    		}
    		else {
    			//Check if the player found matches the sport as the game
    			if(!playersList_.returnPlayersMap().get(playerID).get_sportCategory().equals(game_.getCategory()))
    				throw new MismatchPlayerandGameSportException();
    			else {
    				//Check if the player is on the corresponding team
    				if(!PlayerIsOnTeam(playerID, teamsList_, teamName_, playersList_)) {
    					throw new PlayerNotOnTeamException();		
    				}
    				else {
    					//Player is validated, add to the list of players for that game 
    					listOfPlayers_.add(playerID);
    				}
    			}
    		}
    	}
    }


    /**
     *  Method to check if a player is on a specified team
     */
    private boolean PlayerIsOnTeam(int playerID_,TeamsList teamsList_, String teamName_, PlayersList playersList_) {
    	AbstractPlayer player = playersList_.returnPlayersMap().get(playerID_);
    	if(player.getCurrentTeamHistory().getTeam().equals(teamsList_.getTeamMap().get(teamName_))){
    		return true;
    	}
    	else
    		return false;
    }
    
    /** 
     * (non-Javadoc)
     * Method to tokenize lines from the game data file and add the game to the games list
     * if valid.
     */
    
    void readFromFileForLists(Reader is_, GamesList gamesList_ , TeamsList teamsList_, PlayersList playersList_) {
        try (BufferedReader reader = new BufferedReader(is_)) {
            StringTokenizer tokenizer;
            String line;
            String team;
            String city;
            String playerIDs;
            String teamName;
            AbstractGame game = null;
            AbstractTeam home;
            SportsCategory category;
            Set<Key> keys;
            
            while ((line = reader.readLine()) != null) {
                // don't process empty lines
                if ("".equals(line))
                    continue;

                
                tokenizer = new StringTokenizer(line, DELIM);

                //Parse Game's Sport
                try {
                	category = SportsCategory.valueOf(tokenizer.nextToken());
                }
                catch(Exception e_) {
                	_logger.error("setCategory:" + e_.toString());
                	continue;
                }
                
                //Instantiate game object based on sport
                try {
	                if(category.equals(SportsCategory.valueOf("NBA"))) {
	                	game = new NBAGame();
	                	game.setCategory(category);
	                }
	                else if(category.equals(SportsCategory.valueOf("NFL"))) {
	                	game = new NFLGame();
	                	game.setCategory(category);
	                }
	                else if(category.equals(SportsCategory.valueOf("NHL"))) {
	                	game = new NHLGame();
	                	game.setCategory(category);
	                }
	                else if(category.equals(SportsCategory.valueOf("MLB"))) {
	                	game = new MLBGame();
	                	game.setCategory(category);
	                }
	                
                }
                catch(Exception e_) {
                	_logger.error("Failed to initialize player:" + e_.toString());
                	continue;
                }
                
                // Parse Game's UID
                try {
                	tempGameId = Integer.parseInt(tokenizer.nextToken());
                	keys = gamesList_.getGamesMap().keySet();
                	for(Key key: keys) {
                		
                		if(key.getGameUID()==tempGameId) {
                			
                			throw new Exception("Game ID already exists");
                			
                		}
                	}
                	
                	game.setGameUID(tempGameId);
                }
                catch(Exception e_) {
                	_logger.error("setGameUID:" + e_.toString());
                	continue;
                }
                

                //Parse Game's Start Time
                try {
                    game.setStartTime(parseDate(tokenizer.nextToken()));
                }
                catch (Exception e_) {
                    _logger.error("setDate:" + e_.toString());
                    continue;
                }

                //
                // Parse teams
                //
                
                try {
                	city = tokenizer.nextToken();
                	team = tokenizer.nextToken();
                    game.setAwayTeam(parseTeam(category,city,team, teamsList_));
                }
                catch (Exception e_) {
                    _logger.error("setAwayTeam:" + e_.toString());
                    continue;
                }

                try {
                	city = tokenizer.nextToken();
                	team = tokenizer.nextToken();
                    home = parseTeam(category,city, team, teamsList_);
                    //Check to make sure the home team and away team are not the same
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
                	
                	addGame(game, gamesList_);
                	continue;
                }              


                //
                // this game is in the past, read in the scores
                //
                try {
                	int awayScore = parseInteger(tokenizer.nextToken());
                	//Check to make sure there are no negative scores
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
                    game.setHomeTeamScore(homeScore);              
                }
                catch (Exception e_) {
                    _logger.error("sethTeamScore:" + e_.toString());
                }
                
                // Parse Player IDs that played in the game
                try {
                	playerIDs = tokenizer.nextToken();
                    teamName = game.getAwayTeam().fullTeamName();
                	parsePlayerIDs(playerIDs, game, playersList_, teamsList_, teamName, game.getListOfAwayPlayers());
                }
                catch(Exception e_) {
                	_logger.error("There were invalid player IDs:" + e_.toString());
                	throw e_;
                }
                
                try {
                	playerIDs = tokenizer.nextToken();
                    teamName = game.getHomeTeam().fullTeamName();
                	parsePlayerIDs(playerIDs, game, playersList_, teamsList_, teamName, game.getListofHomePlayers() );
                }
                catch(Exception e_) {
                	_logger.error("There were invalid player IDs:" + e_.toString());
                	throw e_;
                }
                
                //Parse the attendance of the game
                try {
                	int attendance = parseInteger(tokenizer.nextToken());
                	//Check to make sure there isn't a negative attendance
                	if(attendance < 0) {
                		throw new NegativeAttendanceException();
                	}
                    game.setAttendance(attendance);
                    }
                catch (Exception e_) {
                    _logger.error("setAttendance:" + e_.toString());
                }
                
                //Parse the duration of the game
                try {
                	game.setDuration(Duration.parse(tokenizer.nextToken()));
                	//Set the finish time of the game by adding the start time and duration
                	game.setFinishTime(game.getStartTime().plus(game.getDuration()));
                }
                catch(Exception e_) {
                	_logger.error("setDuration:" + e_.toString());
                }
                
                addGame(game, gamesList_);
                
               
            }
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }
    }
    
    /**
     * Method to read in a game from an input string
     */
    public void readFromStringForList(String line, TeamsList teamsList_, PlayersList playersList_, GamesList gamesList_) throws Exception {
      
            StringTokenizer tokenizer;
            String team = null;
            String city = null;
            String teamName = null;
            AbstractGame game = null;
            AbstractTeam home;
            SportsCategory category = null;
            String playerIDs = null;
            Set<Key> keys;
         
                if ("".equals(line))
                    return;

                tokenizer = new StringTokenizer(line, DELIM);

                //Parse Game's Sport
                try {
                	category = SportsCategory.valueOf(tokenizer.nextToken());
                }
                catch(Exception e_) {
                	_logger.error("setCategory:" + e_.toString());
                	throw e_;
                }
                
                //Instantiate game object based on sport
                try {
	                if(category.equals(SportsCategory.valueOf("NBA"))) {
	                	game = new NBAGame();
	                	game.setCategory(category);
	                }
	                else if(category.equals(SportsCategory.valueOf("NFL"))) {
	                	game = new NFLGame();
	                	game.setCategory(category);
	                }
	                else if(category.equals(SportsCategory.valueOf("NHL"))) {
	                	game = new NHLGame();
	                	game.setCategory(category);
	                }
	                else if(category.equals(SportsCategory.valueOf("MLB"))) {
	                	game = new MLBGame();
	                	game.setCategory(category);
	                }
	                
                }
                catch(Exception e_) {
                	_logger.error("Failed to initialize player:" + e_.toString());
                	throw e_;
                }
                
                // Parse Game's UID
                try {
                	tempGameId = Integer.parseInt(tokenizer.nextToken());
                	keys = gamesList_.getGamesMap().keySet();
                	for(Key key: keys) {
                		
                		if(key.getGameUID()==tempGameId) {
                			
                			throw new Exception("Game ID already exists");
                			
                		}
                	}
                	
                	game.setGameUID(tempGameId);
                }
                catch(Exception e_) {
                	_logger.error("setGameUID:" + e_.toString());
                	throw e_;
                }
                //Parse string for game's start time
                try {
                	tempStartTime=parseDate(tokenizer.nextToken());
                    game.setStartTime(tempStartTime);
                }
                catch (Exception e_) {
                    _logger.error("setDate:" + e_.toString());
                    throw e_;
                }

                //
                // teams
                //
                
                try {
                	city = tokenizer.nextToken();
                	team = tokenizer.nextToken();
                    game.setAwayTeam(parseTeam(category,city,team, teamsList_));
                }
                catch (Exception e_) {
                    _logger.error("setAwayTeam:" + e_.toString());
                    throw e_;
                }
                

                try {
                	city = tokenizer.nextToken();
                	team = tokenizer.nextToken();
                    home = parseTeam(category,city, team, teamsList_);
                    //Check to make sure home team is not the away team too
                    if (home.equals(game.getAwayTeam()))
                        throw new DuplicateTeamException("Home team cannot be the same as away", home);
                    game.setHomeTeam(home);
                }
                catch (Exception e_) {
                    _logger.error("sethTeam:" + e_.toString());
                    throw e_;
                }

                //
                // future or past game?
                //
                if (game.getStartTime().isAfter(ZonedDateTime.now())) {
                    // this is a game in the future, do not process any more data
                	
                	addGame(game, gamesList_);
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
                    throw e_;
                }

                try {
                	int homeScore = parseInteger(tokenizer.nextToken());
                	if(homeScore < 0) {
                		throw new NegativeScoreException();
                	}
                    game.setHomeTeamScore(homeScore);              
                }
                catch (Exception e_) {
                    _logger.error("sethTeamScore:" + e_.toString());
                    throw e_;
                }
                
                // Parse Player IDs who played in the game
                try {
                	playerIDs = tokenizer.nextToken();
                	teamName = city + " " + team;
                	parsePlayerIDs(playerIDs, game, playersList_, teamsList_, teamName, game.getListOfAwayPlayers());
                }
                catch(Exception e_) {
                	_logger.error("There were invalid player IDs:" + e_.toString());
                	throw e_;
                }

                try {
                	playerIDs = tokenizer.nextToken();
                	teamName = city + " " + team;
                	parsePlayerIDs(playerIDs, game, playersList_, teamsList_, teamName, game.getListofHomePlayers());
                }
                catch(Exception e_) {
                	_logger.error("There were invalid player IDs:" + e_.toString());
                	throw e_;
                }
                
                // Parse Attendance
                try {
                	int attendance = parseInteger(tokenizer.nextToken());
                	//make sure attendance is not negative
                	if(attendance < 0) {
                		throw new NegativeAttendanceException();
                	}
                    game.setAttendance(attendance);
                    }
                catch (Exception e_) {
                    _logger.error("setAttendance:" + e_.toString());
                    throw e_;
                }
                try {
                	game.setDuration(Duration.parse(tokenizer.nextToken()));
                	game.setFinishTime(game.getStartTime().plus(game.getDuration()));
                }
                catch(Exception e_) {
                	_logger.error("setDuration:" + e_.toString());
                	throw e_;
                }
                
                addGame(game, gamesList_);
                
    }
    
	public void createGames(GamesList gamesList_, TeamsList teamsList_, PlayersList playersList_) throws Exception {
		SportsCategory category = null;
		int gameID = 0;
		ZonedDateTime dateTime = null;
		String awayCity = null;
		String awayTeamName = null;
		
		String homeCity = null;
		String homeTeamName = null;
		String teamName = null;
		int homeTeamScore;
		int awayTeamScore;
		
		int awayTeamCount = 0;
		int homeTeamCount = 0;
		
		int gameAttendance;
		
		Duration gameDuration;
		Set<Key> keys;
		
		AbstractGame game = null;
		AbstractTeam home=null;
		
		String tempPlayerID;		
		
		_logger.info("Enter the Game Details: ");
		
		/*
		 * Reading Game Category
		 */
		_logger.info("Enter the Game Category");
		try {
			category=SportsCategory.valueOf(reader.readLine());
			
		} catch (IOException e) {
			_logger.error("The entered category is : " + e.toString());
		}
		
		/*
		 * Instantiate game object based on sport
		 */
        try {
            if(category.equals(SportsCategory.valueOf("NBA"))) {
            	game = new NBAGame();
            	game.setCategory(category);
            }
            else if(category.equals(SportsCategory.valueOf("NFL"))) {
            	game = new NFLGame();
            	game.setCategory(category);
            }
            else if(category.equals(SportsCategory.valueOf("NHL"))) {
            	game = new NHLGame();
            	game.setCategory(category);
            }
            else if(category.equals(SportsCategory.valueOf("MLB"))) {
            	game = new MLBGame();
            	game.setCategory(category);
            }
            
        }
        catch(Exception e_) {
        	_logger.error("Failed to initialize game:" + e_.toString());
        }
		
		
		/*
		 * Check Game ID
		 */
		try {
			keys = gamesList_.getGamesMap().keySet();
			gameID=gamesList_.getGamesMap().size() + 1;
			for(Key key: keys) {
        		
        		if(key.getGameUID()==gameID) {
        			
        			throw new Exception("Game ID already exists");
        			
        		}
        	}
			game.setGameUID(gameID);

		} catch (IOException e) {
			_logger.error("The ePlayerIsOnTeamntered ID is : " + e.toString() );
		}
		
		
		/*
		 * Reading Date
		 */
		_logger.info("Enter the Game Date");
		try {
			dateTime=parseDate(reader.readLine());
		} catch (IOException e) {
			_logger.error("The entered Date and Time is : " + e.toString());
		}
		game.setStartTime(dateTime);
		
		/*
		 * Reading the Away city 
		 */
		_logger.info("Enter the away city ");
		try {
			awayCity=reader.readLine();
			
		} catch (IOException e) {
			_logger.info("Entered away city is : " +e.toString());
		}
		
		/*
		 * Reading the Away Team Name 
		 */
		_logger.info("Enter the away team name: ");
		try {
			awayTeamName=reader.readLine();
			game.setAwayTeam(parseTeam(category,awayCity,awayTeamName, teamsList_));
		} catch (IOException e) {
			_logger.error("The entered away team is : " +e.toString());
		}
		
		/*
		 * Reading the Home city 
		 */
		_logger.info("Enter the home city ");
		try {
			homeCity=reader.readLine();
			
		} catch (IOException e) {
			_logger.info("Entered away city is : " +e.toString());
		}
		
		/*
		 * Reading the Home Team Name 
		 */
		_logger.info("Enter the home team name: ");
		try {
			homeTeamName=reader.readLine();
			home=(parseTeam(category,homeCity,homeTeamName, teamsList_));
		} catch (IOException e) {
			_logger.error("The entered home team is : " +e.toString());
		}
		if (home.equals(game.getAwayTeam()))
            throw new DuplicateTeamException("Home team cannot be the same as away", home);
		game.setHomeTeam(home);
			
		/*
		 * Checking if future game or past game
		 */
			if (game.getStartTime().isAfter(ZonedDateTime.now())) {
                // this is a game in the future, do not process any more data
            	
            	addGame(game, gamesList_);
            	return;
            }          
		
		
		/*
		 * Reading the away team score
		 */
		_logger.info("Enter the away team score");
		try {
			awayTeamScore=Integer.parseInt(reader.readLine());
			if(awayTeamScore < 0) {
        		throw new NegativeScoreException();
        	}
			game.setAwayTeamScore(awayTeamScore);
			
		} catch (IOException e) {
			_logger.error("The entered score is : " +e.toString() );
			
		}
		
		/*
		 * Reading the home team score
		 */
		_logger.info("Enter the home team score");
		try {
			homeTeamScore=Integer.parseInt(reader.readLine());
			if(homeTeamScore < 0) {
        		throw new NegativeScoreException();
        	}
            game.setHomeTeamScore(homeTeamScore);
		} catch (IOException e) {
			_logger.error("The entered score is : " +e.toString() );
			
		}
		
		/*
		 * Asking user about how many AWAY team ID's he want to enter 
		 */
		_logger.info("How many members are there in the away team?");
		try {
			awayTeamCount=Integer.parseInt(reader.readLine());
			if(awayTeamCount <= 0)
				throw new Exception("Players played for away team should be greater than 0");
		} catch (IOException e) {
			_logger.error("The count for away team members is : " + e.toString());
		}
		
		/*
		 * User Entering the player ID's 
		 */
		_logger.info("Enter the player ID's of AWAY team");
		for(int i=0;i<awayTeamCount;i++) {
			try {
				tempPlayerID=reader.readLine();
                teamName = game.getAwayTeam().fullTeamName();
				parsePlayerIDs(tempPlayerID, game, playersList_, teamsList_, teamName, game.getListOfAwayPlayers());				
			} catch (IOException e) {
				_logger.error("Entered away team ID is : " + e.toString());
			}
		}
		
		/*
		 * Asking user about how many HOME team ID's he want to enter 
		 */
		_logger.info("How many members are there in the HOME team");
		try {
			homeTeamCount=Integer.parseInt(reader.readLine());
			if(homeTeamCount <= 0)
				throw new Exception("Players played for home team should be greater than 0");
		} catch (IOException e) {
			_logger.error("The count for home team members is : " + e.toString());
		}
		
		/*
		 * User Entering the player ID's 
		 */
		_logger.info("Enter the player ID's of HOME team");
		for(int i=0;i<homeTeamCount;i++) {
			try {
				tempPlayerID=reader.readLine();
                teamName = game.getHomeTeam().fullTeamName();
				parsePlayerIDs(tempPlayerID, game, playersList_, teamsList_, teamName, game.getListOfAwayPlayers());
				
			} catch (IOException e) {
				_logger.error("Entered HOME team ID is : " + e.toString());
			}
		}
		
		/*
		 * Reading the Attendance
		 */
		_logger.info("Enter the game attendence ");
		try {
			gameAttendance=Integer.parseInt(reader.readLine());
			if(gameAttendance < 0) {
        		throw new NegativeAttendanceException();
        	}
			game.setAttendance(gameAttendance);
		} catch (IOException e) {
			_logger.error("Entered Game Attendence is " + e.toString());
		}
		
		/*
		 * Reading the Duration
		 */
		_logger.info("Enter the Game Duration");
		try {
			gameDuration=Duration.parse(reader.readLine());
			game.setDuration(gameDuration);
        	game.setFinishTime(game.getStartTime().plus(game.getDuration()));
		} catch (IOException e) {
			_logger.error("Entered duration is : " + e.toString());
		}
		
    	addGame(game, gamesList_);						
}
	
	
    /**
     * Add the game to the map of games if the game is valid
     */
    private void addGame(AbstractGame game_, GamesList gamesList_)
    {
        if (!game_.isValid())
        {
            _logger.error("Refusing to add invalid game: {}", game_);
            return;
        }
        
        //
        //Add game object to the map of games provided
        //
        gamesList_.getGamesMap().put(new Key(game_.getStartTime(), game_.getGameUID()), game_);

        if (_logger.isTraceEnabled())
            _logger.trace("Adding new game to game mapt: {}", game_.toString());
    }
}
