package com.sts.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;
import com.sts.mlb.models.MLBPlayer;
import com.sts.mlb.models.TeamMLB;
import com.sts.nba.models.NBAPlayer;
import com.sts.nba.models.TeamNBA;
import com.sts.nfl.models.NFLPlayer;
import com.sts.nfl.models.TeamNFL;
import com.sts.nhl.models.NHLPlayer;
import com.sts.nhl.models.TeamNHL;
import com.sts.control.TeamsFileReader;

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
	    
	    
		public void createGames() {
				SportsCategory category;
				int gameID;
				ZonedDateTime dateTime;
				String awayCity;
				
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
				 * Reading Game ID
				 */
				_logger .info("Enter the Game ID");
				try {
					gameID=reader.read();
				} catch (IOException e) {
					_logger.error("The entered ID is : " + e.toString() );
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
				
				/*
				 * Reading the Away city 
				 */
				_logger.info("Enter the away city ");
				try {
					awayCity=reader.readLine();
				} catch (IOException e) {
					_logger.info("Entered away city is : " +e.toString());
				}
				
		}
		
		public void createPlayers(TeamsList listofTeams_, PlayersList listofPlayers_) throws IOException {
			String category;
			AbstractPlayer player = null;
			String lineForTeam = null;
			
			_logger.info("Enter Sport of Player");
			category = reader.readLine();
			
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
			
			try {
				if(lineForTeam == "") {
					listofPlayers_.returnPlayersMap().put(player.get_playerID(), player);
					_logger.trace("New player successfully created and added to player's map");
				}
			}
			catch(Exception e_) {
				
			}
			
		}
		public void createTeam(TeamsList listofTeams) throws IOException {
			SportsCategory category;
			AbstractTeam team = null;
			String newTeam;
			
			_logger.info("Enter Team's Sport Category");
			category =  SportsCategory.valueOf(reader.readLine());
								
			  try {
	                if(category.equals(category.valueOf("NBA"))) {
	                	team = new TeamNBA();
	                	team.setTeamSport(SportsCategory.valueOf("NBA"));
	                }
	                else if(category.equals(category.valueOf("NFL"))) {
	                	team = new TeamNFL();
	                	team.setTeamSport(SportsCategory.valueOf("NFL"));
	                }
	                else if(category.equals(category.valueOf("NHL"))) {
	                	team = new TeamNHL();
	                	team.setTeamSport(SportsCategory.valueOf("NHL"));
	                }
	                else if(category.equals(category.valueOf("MLB"))) {
	                	team = new TeamMLB();
	                	team.setTeamSport(SportsCategory.valueOf("MLB"));
	                }
	                else
	                	throw new Exception("Invalid category.");
            }
            catch(Exception e_) {
            	_logger.error("Failed to initialize Team:" + e_.toString());
            }
			  
			  _logger.info("Enter Team Location");
			  try {
				  team.setLocation(reader.readLine());
			  }
			  catch (Exception e_){
				  _logger.error("Entering Location" + e_.toString());
			  }
			  
			  _logger.info("Enter Team Name");
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
