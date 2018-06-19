package com.sts.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamPlayer;
import com.sts.concretemodel.TeamsList;
import com.sts.mlb.models.MLBPlayer;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.mlb.models.TeamMLB;
import com.sts.nba.models.NBAPlayer;
import com.sts.nba.models.TeamNBA;
import com.sts.nfl.models.NFLPlayer;
import com.sts.nfl.models.TeamNFL;
import com.sts.nhl.models.NHLPlayer;
import com.sts.nhl.models.TeamNHL;

public class Service {
	
		private Logger _logger;
	    private BufferedReader reader;
	
	    public Service() {
	    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	 	    reader = new BufferedReader(new InputStreamReader(System.in));
	    }
	    
		public void createGames() {
			
		}
		
		/*
		 * Method for creating and adding new player
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
