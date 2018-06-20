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
