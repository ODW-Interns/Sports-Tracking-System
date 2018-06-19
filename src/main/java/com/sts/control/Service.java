package com.sts.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;
import com.sts.mlb.models.MLBPlayer;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.nba.models.NBAPlayer;
import com.sts.nfl.models.NFLPlayer;
import com.sts.nhl.models.NHLPlayer;

public class Service {
	
		private Logger _logger;
	    private BufferedReader reader;
	
	    public Service() {
	    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	 	    reader = new BufferedReader(new InputStreamReader(System.in));
	    }
	    
		public void createGames() {
			
		}
		
		/**
		 * TODO: Finish this method to handle event of creating and tracking a player
		 */
		public void createPlayers(TeamsList listofTeams_, PlayersList listofPlayers_) throws IOException {
			SportsCategory category;
			AbstractTeam teamOfPlayer = null;
			AbstractPlayer player = null;
			String lineForTeam = null;
			
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
						player.setCurrentTeam(listofTeams_.getTeamMap().get(lineForTeam));
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
			
			try {
				
			}
			catch(Exception e_) {
				
			}
			
			
		}
		public void createTeam() {
			
			
		}
		public void movePlayer() {
			
			
		}
}
