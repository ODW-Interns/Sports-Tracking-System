package com.sts.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractGame;
import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.Key;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamPlayer;
import com.sts.concretemodel.TeamsList;
import com.sts.mlb.models.MLBGame;
import com.sts.mlb.models.MLBPlayer;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.mlb.models.TeamMLB;
import com.sts.model.exception.DuplicateTeamException;
import com.sts.model.exception.InvalidPlayersException;
import com.sts.model.exception.MismatchGameandTeamSportException;
import com.sts.model.exception.MismatchPlayerandGameSportException;
import com.sts.model.exception.NegativeAttendanceException;
import com.sts.model.exception.NegativeScoreException;
import com.sts.model.exception.PlayerNotOnTeamException;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.nba.models.NBAGame;
import com.sts.nba.models.NBAPlayer;
import com.sts.nba.models.TeamNBA;
import com.sts.nfl.models.NFLGame;
import com.sts.nfl.models.NFLPlayer;
import com.sts.nfl.models.TeamNFL;
import com.sts.nhl.models.NHLGame;
import com.sts.nhl.models.NHLPlayer;
import com.sts.nhl.models.TeamNHL;


public class Service {
	
		private Logger _logger;
	    private BufferedReader reader;
	
	    public Service() {
	    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	 	    reader = new BufferedReader(new InputStreamReader(System.in));
	    }
	    
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
	    }
	    
		public void createGames(GamesList gamesList_, TeamsList teamsList_, PlayersList playersList_) throws Exception {
				SportsCategory category = null;
				int gameID = 0;
				ZonedDateTime dateTime = null;
				String awayCity = null;
				String awayTeamName = null;
				
				String homeCity = null;
				String homeTeamName = null;
				
				int homeTeamScore;
				int awayTeamScore;
				
				int awayTeamCount = 0;
				int homeTeamCount = 0;
				
				int gameAttendance;
				
				Duration gameDuration;
				Set<Key> keys;
				
				AbstractGame game = null;
				AbstractTeam home=null;
				
				int tempPlayerID;
				
				ArrayList<Integer> awayTeamList=new ArrayList<Integer>();
				ArrayList<Integer> homeTeamList=new ArrayList<Integer>();
				
				
				
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
                	_logger.error("Failed to initialize player:" + e_.toString());
                }
				
				
				/*
				 * Reading Game ID
				 */
				_logger .info("Enter the Game ID");
				try {
					keys = gamesList_.getGamesMap().keySet();
					gameID=Integer.parseInt(reader.readLine());
					for(Key key: keys) {
                		
                		if(key.getGameUID()==gameID) {
                			
                			throw new Exception("Game ID already exists");
                			
                		}
                	}
				} catch (IOException e) {
					_logger.error("The ePlayerIsOnTeamntered ID is : " + e.toString() );
				}
				game.setGameUID(gameID);
				
				
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
					_logger.error("The entered away team is : " +e.toString());
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
				_logger.info("How many members are there in the away team");
				try {
					awayTeamCount=Integer.parseInt(reader.readLine());
				} catch (IOException e) {
					_logger.error("The count for away team members is : " + e.toString());
				}
				
				/*
				 * User Entering the player ID's 
				 */
				_logger.info("Enter the player ID's of AWAY team");
				for(int i=0;i<awayTeamCount;i++) {
					try {
						tempPlayerID=Integer.parseInt(reader.readLine());
						parsePlayerID(tempPlayerID, game, playersList_, teamsList_, awayTeamName, game.getListOfAwayPlayers());
						awayTeamList.add(tempPlayerID);
						
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
				} catch (IOException e) {
					_logger.error("The count for away team members is : " + e.toString());
				}
				
				/*
				 * User Entering the player ID's 
				 */
				_logger.info("Enter the player ID's of HOME team");
				for(int i=0;i<homeTeamCount;i++) {
					try {
						tempPlayerID=Integer.parseInt(reader.readLine());
						parsePlayerID(tempPlayerID, game, playersList_, teamsList_, homeTeamName, game.getListOfAwayPlayers());
						
						homeTeamList.add(reader.read());
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
						
		}
		
		private void parsePlayerID(int playerID_, AbstractGame game_, PlayersList playersList_, TeamsList teamsList_, String teamName_, ArrayList<Integer> listOfPlayers_) throws InvalidPlayersException, MismatchPlayerandGameSportException, PlayerNotOnTeamException {
	    	
	    

	    		//Check to see if the player exists in the player map
	    		if(playersList_.returnPlayersMap().get(playerID_) == null) {
	    			_logger.error("There does not exist a player with the ID:" + playerID_ + ". Player was not added to game");
	    			throw new InvalidPlayersException();
	    		}
	    		else {
	    			//Check if the player found matches the sport as the game
	    			if(!playersList_.returnPlayersMap().get(playerID_).get_sportCategory().equals(game_.getCategory()))
	    				throw new MismatchPlayerandGameSportException();
	    			else {
	    				//Check if the player is on the corresponding team
	    				if(!PlayerIsOnTeam(playerID_, teamsList_, teamName_, playersList_)) {
	    					throw new PlayerNotOnTeamException();		
	    				}
	    				else {
	    					//Player is validated, add to the list of players for that game 
	    					listOfPlayers_.add(playerID_);
	    				}
	    			}
	    		}
	    	
	    }
		private boolean PlayerIsOnTeam(int playerID_,TeamsList teamsList_, String teamName_,PlayersList playersList_) {
			AbstractPlayer player = playersList_.returnPlayersMap().get(playerID_);
	    	if(player.getCurrentTeamHistory().getTeam().equals(teamsList_.getTeamMap().get(teamName_))){
	    		return true;
	    	}
	    	else
	    		return false;
	    }
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
		
		
		/*
		 * Method for creating and adding new player
		 * TODO: Add exceptions for adding player
		 */

		public void createPlayers(TeamsList listofTeams_, PlayersList listofPlayers_) throws IOException {
			SportsCategory category;
			AbstractPlayer player = null;
			String lineForTeam = null;
			TeamPlayer currentHistory = null;
			Date StartDate;
			_logger.info("Enter Sport of Player");
			category = SportsCategory.valueOf(reader.readLine());
			
			//Prompt for which sport the player being created plays
		      try {
	                if(category.equals(SportsCategory.valueOf("NBA"))) {
	                	player = new NBAPlayer();
	                }
	                else if(category.equals(SportsCategory.valueOf("NFL"))) {
	                	player = new NFLPlayer();
	                }
	                else if(category.equals(SportsCategory.valueOf("NHL"))) {
	                	player = new NHLPlayer();
	                }
	                else if(category.equals(SportsCategory.valueOf("MLB"))) {
	                	player = new MLBPlayer();
	                }
	                else
	                	throw new Exception("Invalid category.");
              }
              catch(Exception e_) {
              	_logger.error("Failed to initialize player:" + e_.toString());
              }
		    
		      // Set player ID
		    player.set_playerID(listofPlayers_.returnPlayersMap().size() + 1);
			
		    //Prompt for Player's first name
			_logger.info("Enter Player's First Name: ");
			try {
				player.setFirstName(reader.readLine());
			}
			catch(Exception e_) {
				_logger.error("Entering First Name: " + e_.toString());
			}
			
			//Prompt for Player's last name
			_logger.info("Enter Player's Last Name: ");
			try {
				player.setLastName(reader.readLine());
			}
			catch(Exception e_){
				_logger.error("Entering Last Name: " + e_.toString());
			}
			
			//Prompt for Player's jersey number
			_logger.info("Enter Player's Jersey Number: ");
			try {
				player.set_jerseyNum(Integer.parseInt(reader.readLine()));
			}
			catch(Exception e_) {
				_logger.error("Entering Jersey Number: " + e_.toString());
			}
			
			_logger.info("Enter Player's Current Team: ");
			_logger.info("If player is currently not on a team, then leave blank");
			lineForTeam = reader.readLine();
			try {
				if(lineForTeam == "") {
					listofPlayers_.returnPlayersMap().put(player.get_playerID(), player);
					_logger.trace("New player successfully created and added to player's map");
					return;
				}
				else {
					if(listofTeams_.getTeamMap().get(lineForTeam) != null) {
						currentHistory = new TeamPlayer();
						currentHistory.setTeam(listofTeams_.getTeamMap().get(lineForTeam));
					}
					else {
						throw new TeamNotFoundException(lineForTeam);
					}
				}
			}
			catch(Exception e_) {
				_logger.error("Player not created: " + e_.toString());
				return;
			}
			
			
			_logger.info("Enter the start date of the player on this team in this format(yyyy-mm-dd):");
			try {
	           	StartDate = new SimpleDateFormat("yyyy-MM-dd").parse(reader.readLine());
	           	currentHistory.setStartDate(StartDate);
	           	currentHistory.setStatus(true);
			}
			catch(Exception e_) {
				_logger.error("Setting Start Date on team: " + e_.toString());
			}
			
			currentHistory.setPlayer(player);
			player.setCurrentTeamHistory(currentHistory);
			listofPlayers_.returnPlayersMap().put(player.get_playerID(), player);
			listofTeams_.getTeamMap().get(player.getCurrentTeamHistory().getTeam().fullTeamName()).getEntireHistoryPlayers().add(player.getCurrentTeamHistory());
			_logger.trace("Successfully added player to player's map");
		}
		
		/*
		 * method for creating & adding a new team 
		 */
		public void createTeam(TeamsList listofTeams) throws IOException {
			SportsCategory category;
			AbstractTeam team = null;
			String newTeam;
			
			//promt for new team sport
			_logger.info("Enter Team's Sport Category");
			category =  SportsCategory.valueOf(reader.readLine());
				
			//set team category
			  try {
	                if(category.equals(SportsCategory.valueOf("NBA"))) {
	                	team = new TeamNBA();
	                	team.setTeamSport(SportsCategory.valueOf("NBA"));
	                }
	                else if(category.equals(SportsCategory.valueOf("NFL"))) {
	                	team = new TeamNFL();
	                	team.setTeamSport(SportsCategory.valueOf("NFL"));
	                }
	                else if(category.equals(SportsCategory.valueOf("NHL"))) {
	                	team = new TeamNHL();
	                	team.setTeamSport(SportsCategory.valueOf("NHL"));
	                }
	                else if(category.equals(SportsCategory.valueOf("MLB"))) {
	                	team = new TeamMLB();
	                	team.setTeamSport(SportsCategory.valueOf("MLB"));
	                }
	                else
	                	throw new Exception("Invalid category.");
            }
            catch(Exception e_) {
            	_logger.error("Failed to initialize Team:" + e_.toString());
            }
			  
			  //promt for new team location
			  _logger.info("Enter Team Location");
			  
			  //set team location
			  try {
				  team.setLocation(reader.readLine());
			  }
			  catch (Exception e_){
				  _logger.error("Entering Location" + e_.toString());
			  }
			  
			  //promt for new team name
			  _logger.info("Enter Team Name");
			  
			  //set team name
			  try {
				  team.setTeamName(reader.readLine());
			  }
			  catch (Exception e_) {
				  _logger.error("Entering Team Name" + e_.toString());
			  }
			
			  if (!team.isValid())
		        {
		            _logger.error("Refusing to add invalid team: {}", team);
		            return;
		        }
		        
		        //
		        //Add game object to the list of games provided
		        //
		        listofTeams.getTeamMap().put(team.fullTeamName(), team);

		        if (_logger.isTraceEnabled())
		            _logger.trace("Adding new team to team map: {}", team.toString());
		    

		}
		public void movePlayer() {
			
			
		}
}
