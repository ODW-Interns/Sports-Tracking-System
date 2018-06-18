package com.sts.control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamHistory;
import com.sts.concretemodel.TeamsList;
import com.sts.mlb.models.MLBPlayer;
import com.sts.model.exception.MismatchPlayerandTeamSportException;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.nba.models.NBAPlayer;
import com.sts.nfl.models.NFLPlayer;
import com.sts.nhl.models.NHLPlayer;
/**
 * This class will read from the players list input, and will deal with switching teams and changing Jersey numbers.
 * We will store all the players in a hash map when the ID is the key for the map.
 * We also reading the sport category and declare if the player in NFl, NHl, NBA, or MLB. We map each player to his category defined class
 * so we can update unique statistics in the future.
 */
public class PlayersFileReader {
	private Logger _logger;

    private static final String DELIM = "|";
    //Constructor
    public PlayersFileReader() {
        _logger = LoggerFactory.getLogger(getClass().getSimpleName());
    }
    
    /**
     * Method to check if the input file exists and if the player's map exists
     */
    public void readData(InputStream is_, PlayersList playerlist_, TeamsList teamsList_, GamesList gamesList_) throws FileNotFoundException {
    	 if (is_ == null)
             throw new FileNotFoundException();

         if (playerlist_ == null)
             throw new RuntimeException("No list provided");
    
	    try (InputStreamReader sr = new InputStreamReader(is_)) {
	        readFromFileForLists(sr, playerlist_, teamsList_);
	   
	    }
	    catch (Exception e_) {
	        e_.printStackTrace();
	    }
    }
    
    /**
     * Method to convert a given date string to a formatted date object and returns the date
     */
    public Date convertStringToDate(String dateString_)
    {
        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = ((Date)df.parse(dateString_));
        }
        catch ( Exception e_ ){
        	_logger.error(e_.toString());
        }
        return date;
    }
/*
    private Team parsePlayer(int playerId_,int jerseyNum_, String firstName_, String lastName_, PlayersList PlayersList_) {
        Player lclPlayer = PlayersList_.returnPlayersMap();
        		getPlayerMap().get(teamStr_);
        if (lclPlayer == null) {
        	
            if (_logger.isInfoEnabled())
                _logger.info("New team identified:{}. adding to mapping Team:{}", teamStr_, lclTeam);
        }

        // log it
        if (_logger.isTraceEnabled())
            _logger.trace("found team for key: {}", teamStr_);

        // done
        return lclTeam;
    }*/
    
    /**
     * method to parse the team from the data file
     */
    private void parseCurrentTeam(TeamsList teamList_, String teamStr_, AbstractPlayer player_, PlayersList playerlist_) throws Exception {
    	AbstractPlayer tempPlayer;
    	ArrayList<Integer> temp1;
    	AbstractTeam temp;
    	
    	//Check to see if team actually exists for the player to be on
    	if(teamList_.getTeamMap().get(teamStr_) == null) {
    		throw new TeamNotFoundException(teamStr_);
    	}
    	else {
    		//checks to see if the team and player are of the same sport type
    		if(!teamList_.getTeamMap().get(teamStr_).getTeamSport().equals(player_.get_sportCategory())) {
    			throw new MismatchPlayerandTeamSportException();
    		}
    		else {
    			
    			temp = teamList_.getTeamMap().get(teamStr_);
    			temp1 = temp.getListOfPLayers();
    			//Check for duplicate Jersey #'s on the same team
    			for(int i=0;i<temp1.size();i++) {
    			tempPlayer=playerlist_.returnPlayersMap().get(temp1.get(i));
    			int tempJnumb=tempPlayer.getJerseyNum();
    			if(tempJnumb==player_.getJerseyNum() && tempJnumb != playerlist_.returnPlayersMap().get(player_.get_playerID()).getJerseyNum()) {
    				throw new Exception ("Duplicate Jersey Number");
    					
    			}
    		}
    			
    	}
    		player_.setCurrentTeam(teamList_.getTeamMap().get(teamStr_));
    }
  }
    
    
    /**
     * Method to update a player if their jersey number or team has changed
     * @throws Exception 
     */
    public void updatePlayer(AbstractPlayer player_, AbstractPlayer existingPlayer_, PlayersList playersList_, TeamHistory currentTeamHistory_, TeamsList teamsList_) throws Exception {
		 int sizeOfTeamHistoryList = existingPlayer_.get_HistoryOfTeamsForPlayers().size();

    	//Update jersey number if the player's jersey has changed
    	if(player_.getJerseyNum() != existingPlayer_.getJerseyNum()) {
    		_logger.info("Updating Player Jersey Number:");
    		_logger.info("Changing Player Jersey Number from {} to Jersey Number {}", existingPlayer_.getJerseyNum(), player_.getJerseyNum());
         	playersList_.returnPlayersMap().get(existingPlayer_.get_playerID()).set_jerseyNum(player_.getJerseyNum());
    	}
    	//Update player's current team if they moved team
    	if(!player_.getCurrentTeam().equals(existingPlayer_.getCurrentTeam())) {
    		_logger.info("Updating Player's Current Team:");
    		_logger.info("Changing Player's Current Team from {} to {}", existingPlayer_.getCurrentTeam().fullTeamName(), player_.getCurrentTeam().fullTeamName());
   		 	teamsList_.getTeamMap().get(existingPlayer_.getCurrentTeam().fullTeamName()).getListOfPLayers().remove(Integer.valueOf(existingPlayer_.get_playerID()));
    		playersList_.returnPlayersMap().get(existingPlayer_.get_playerID()).setCurrentTeam(player_.getCurrentTeam());
    		teamsList_.getTeamMap().get(playersList_.returnPlayersMap().get(existingPlayer_.get_playerID()).getCurrentTeam().fullTeamName()).getListOfPLayers().add(Integer.valueOf(existingPlayer_.get_playerID()));

    		if(sizeOfTeamHistoryList > 0) {
    			 try {
    				 //Makes sure that the start date of the new team is not before the start date of the previous team
    				 if(!(currentTeamHistory_.get_StartDate().before(existingPlayer_.get_HistoryOfTeamsForPlayers().get(sizeOfTeamHistoryList-1).get_StartDate()))) {
    					 //If everything is valid, make the start date of the new team as the end date of the previous team
 	             		playersList_.returnPlayersMap().get(existingPlayer_.get_playerID()).get_HistoryOfTeamsForPlayers().get(sizeOfTeamHistoryList-1).set_EndDate(currentTeamHistory_.get_StartDate());
 	            		playersList_.returnPlayersMap().get(existingPlayer_.get_playerID()).get_HistoryOfTeamsForPlayers().add(currentTeamHistory_);

    				 }
    				 else 
    					 throw new Exception("New Team's start date is before the Old Team's end date");
    			 }
    			 catch(Exception e_) {
    				 _logger.error(e_.toString());
    				 throw e_;
    			 }
    	   	}
    	}	
    }
    
    
    /**
     * Read all the players from a file and store into a players map
     */
	private void readFromFileForLists(Reader is_, PlayersList playerlist_, TeamsList teamsList_) throws IOException {
		// TODO Auto-generated method stub
		 try (BufferedReader reader = new BufferedReader(is_)) {
	         StringTokenizer tokenizer;   
	         AbstractPlayer player = null;
	         TeamHistory currentTeamHistory = null;
			 String line;
			 SportsCategory category;
	            while ((line = reader.readLine()) != null) {
	                // don't process empty lines
	                if ("".equals(line))
	                    continue;
	                tokenizer = new StringTokenizer(line, DELIM);
	                //
	                //
	                //
	                //
	                try {
	                	category = SportsCategory.valueOf(tokenizer.nextToken());
	                }
	                catch(Exception e_) {
	                	_logger.error("Read in category:" + e_.toString());
	                	continue;
	                }
	                //Instantiate player based on their sport
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
		                
	                }
	                catch(Exception e_) {
	                	_logger.error("Failed to initialize player:" + e_.toString());
	                	continue;
	                }
	                
	                try {
	                	player.set_sportCategory(category);
	                }
	                catch(Exception e_) {
	                	_logger.error("setCategory:" + e_.toString());
	                }
	                
	                //Player ID, used as key in players map
	                try {
	                	player.set_playerID(Integer.parseInt(tokenizer.nextToken()));
	     	
	                }	                
	                catch(Exception e_){
	                	_logger.error("setPlayerID:" + e_.toString());
	                	continue;
	                }
	  
	                //Jersey Number
	                try {
	                	player.set_jerseyNum(Integer.parseInt(tokenizer.nextToken()));
	                }
	                catch(Exception e_) {
	                	_logger.error("setJerseyNum:" + e_.toString());
	                	continue;
	                }
	                
	                //First Name
	                try {
	                	player.setFirstName(tokenizer.nextToken());
	                }
	                catch(Exception e_) {
	                	_logger.error("setFirstName:" + e_.toString());
	                	continue;
	                
	                }
	                
	                //Last Name
	                try {
	                	player.setLastName(tokenizer.nextToken());
	                
	                }
	                catch(Exception e_){
	                	_logger.error("setLastName:" + e_.toString());
	                	continue;
	                }
	                
	                try {
	                	//Check here if player ID has already been used for another player
	                	if(playerlist_.returnPlayersMap().containsKey(player.get_playerID()) && 
	                			(!(playerlist_.returnPlayersMap().get(player.get_playerID()).getFirstName().equals(player.getFirstName())) || 
	                			!playerlist_.returnPlayersMap().get(player.get_playerID()).getLastName().equals(player.getLastName()))) {
	                		throw new Exception("Player ID already exists for another player in map");}
	                }
	                catch(Exception e_) {
	                	_logger.error(e_.toString());
	                	continue;
	                }
	                
	                //Player's current team
	                try {
	                	parseCurrentTeam(teamsList_, tokenizer.nextToken(), player, playerlist_);
	               
	                }
	                catch(Exception e_) {
	                	_logger.error("setTeam:" + e_.toString());
	                	continue;
	                }
	                
	                //Potentially add this team to the player's history
	                try {
	                	currentTeamHistory = new TeamHistory(player.getCurrentTeam().getLocation(), player.getCurrentTeam().getTeamName());
	                	currentTeamHistory.set_StartDate(convertStringToDate(tokenizer.nextToken()));
	                }
	                catch(Exception e_){
	                	_logger.error(e_.toString());
	                }
	            
	                //If the player already exists in the players map, then update player if
	                //player needs updating
	             if(playerlist_.returnPlayersMap().get(player.get_playerID()) != null) {
	            	 AbstractPlayer existingPlayer = playerlist_.returnPlayersMap().get(player.get_playerID());
	            	 if(!existingPlayer.equals(player)) { // If either the player's jersey changed or they moved team	            		
	            		 //update existing player with new data
	            		 try {
	            			 updatePlayer(player, existingPlayer, playerlist_, currentTeamHistory, teamsList_);

	            		 }
	            		 catch(Exception e_) {
	            			 _logger.error("Something went wrong: Player has not been updated");
	            		 }

	            	 }
	            }
	            //Else if player does not already exist in players map
	             else {
	            	 // Add new player to players map
	            	 try {
	            		 player.get_HistoryOfTeamsForPlayers().add(currentTeamHistory);
	            		 addPlayer(player, playerlist_);
	            		 // Add player to their current team
	            		 teamsList_.getTeamMap().get(player.getCurrentTeam().fullTeamName()).getListOfPLayers().add(player.get_playerID());
	            	 }
	            	 catch(Exception e_) {
	            		 _logger.error("Player's current team is not a valid team: " + e_.toString());
	            	 }
	             }    
	           }
		 } 
	}
	
	public void readFromStringForList(String line, PlayersList playersList_, TeamsList teamsList_) throws Exception {
		
		 StringTokenizer tokenizer;   
		 TeamHistory currentTeamHistory = null;
		 SportsCategory category = null;
         AbstractPlayer player = null;
		
		if ("".equals(line))
			throw new Exception();
        
		tokenizer = new StringTokenizer(line, DELIM);

        try {
        	category = SportsCategory.valueOf(tokenizer.nextToken());
        }
        catch(Exception e_) {
        	_logger.error("Read in category:" + e_.toString());
        	throw e_;
        }
        
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
            
        }
        catch(Exception e_) {
        	_logger.error("Failed to initialize player:" + e_.toString());
        	throw e_;
        }
        
        try {
        	player.set_sportCategory(category);
        }
        catch(Exception e_) {
        	_logger.error("setCategory:" + e_.toString());
        	throw e_;
        }
        
        try {
        	player.set_playerID(Integer.parseInt(tokenizer.nextToken()));
        }
        catch(Exception e_){
        	_logger.error("setPlayerID:" + e_.toString());
        	throw e_;
        }
        
        try {
        	
        	player.set_jerseyNum(Integer.parseInt(tokenizer.nextToken()));
        }
        catch(Exception e_) {
        	_logger.error("setJerseyNum:" + e_.toString());
        	throw e_;
        }
        
        try {
        	player.setFirstName(tokenizer.nextToken());
        }
        catch(Exception e_) {
        	_logger.error("setFirstName:" + e_.toString());
        	throw e_;
        
        }
        try {
        	player.setLastName(tokenizer.nextToken());
        }
        catch(Exception e_){
        	_logger.error("setLastName:" + e_.toString());
        	throw e_;
        }
        
        try {
        	parseCurrentTeam(teamsList_, tokenizer.nextToken(), player, playersList_);	           
        }
        catch(Exception e_) {
        	_logger.error("setTeam:" + e_.toString());
        	throw e_;
        }
        //Potentially add this team to the player's history
        try {
        	currentTeamHistory = new TeamHistory(player.getCurrentTeam().getLocation(), player.getCurrentTeam().getTeamName());
        	currentTeamHistory.set_StartDate(convertStringToDate(tokenizer.nextToken()));
        }
        catch(Exception e_){
        	_logger.error(e_.toString());
        }
    
        //If the player already exists in the players map, then update player if
        //player needs updating
     if(playersList_.returnPlayersMap().get(player.get_playerID()) != null) {
    	 AbstractPlayer existingPlayer = playersList_.returnPlayersMap().get(player.get_playerID());
    	 if(!existingPlayer.equals(player)) { // If either the player's jersey changed or they moved team
    		
    		 //update existing player with new data
    		 try {
    			 updatePlayer(player, existingPlayer, playersList_, currentTeamHistory, teamsList_);
    		 }
    		 catch(Exception e_) {
    			 _logger.error("Something went wrong: Player has not been updated");
    			 throw e_;
    		 }

    	 }
    }
    //Else if player does not already exist in players map
     else {
    	 // Add new player to players map
    	 try {
    		 player.get_HistoryOfTeamsForPlayers().add(currentTeamHistory);
    		 addPlayer(player, playersList_);
    		 // Add player to their current team
    		 teamsList_.getTeamMap().get(player.getCurrentTeam().fullTeamName()).getListOfPLayers().add(player.get_playerID());
    	 }
    	 catch(Exception e_) {
    		 _logger.error("Player's current team is not a valid team: " + e_.toString());
    		 throw e_;
    	 }
     }    
   
	}
	
	private void addPlayer(AbstractPlayer player_, PlayersList PlayersList_)
    {
        if (!player_.isValid())
        {
            _logger.error("Refusing to add invalid player: {}", player_);
            return;
        }
        
        //
        //Add game object to the map of games provided
        //
        PlayersList_.returnPlayersMap().put(player_.get_playerID(),player_);

        if (_logger.isTraceEnabled())
            _logger.trace("Adding new player to player map: {}", player_.toString());
    }


}
