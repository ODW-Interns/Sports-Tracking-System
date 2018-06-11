package com.sts.control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.MLBPlayer;
import com.sts.concretemodel.NBAPlayer;
import com.sts.concretemodel.NFLPlayer;
import com.sts.concretemodel.NHLPlayer;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamHistory;
import com.sts.concretemodel.TeamsList;
import com.sts.model.exception.MismatchPlayerandTeamSportException;
import com.sts.model.exception.TeamNotFoundException;
/*
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
    
    private void parseCurrentTeam(TeamsList teamList_, String teamStr_, AbstractPlayer player_) throws TeamNotFoundException, MismatchPlayerandTeamSportException {
    	if(teamList_.getTeamMap().get(teamStr_) == null) {
    		throw new TeamNotFoundException(teamStr_);
    	}
    	else {
    		if(!teamList_.getTeamMap().get(teamStr_).getTeamSport().equals(player_.get_sportCategory())) {
    			throw new MismatchPlayerandTeamSportException();
    		}
    		else {
    	      	player_.setCurrentTeam(teamList_.getTeamMap().get(teamStr_));
    		}
    	}
    	
    }
    
    
    public AbstractPlayer updatePlayer(AbstractPlayer player_, AbstractPlayer existingPlayer_, PlayersList playersList_) {
    	if(player_.getJerseyNum() != existingPlayer_.getJerseyNum()) {
    		_logger.info("Updating Player Jersey Number:");
    		_logger.info("Changing Player Jersey Number from {} to Jersey Number {}", existingPlayer_.getJerseyNum(), player_.getJerseyNum());
    		playersList_.returnPlayersMap().get(player_.get_playerID()).set_jerseyNum(existingPlayer_.getJerseyNum());
    		existingPlayer_.set_jerseyNum(player_.getJerseyNum());
    		
    	}
    	if(!player_.getCurrentTeam().equals(existingPlayer_.getCurrentTeam())) {
    		_logger.info("Updating Player's Current Team:");
    		_logger.info("Changing Player's Current Team from {} to {}", existingPlayer_.getCurrentTeam().fullTeamName(), player_.getCurrentTeam().fullTeamName());
   		 	playersList_.returnPlayersMap().get(player_.get_playerID()).setCurrentTeam(existingPlayer_.getCurrentTeam());
    		existingPlayer_.setCurrentTeam(player_.getCurrentTeam());
    	}
    	return existingPlayer_;
    }
    
	private void readFromFileForLists(Reader is_, PlayersList playerlist_, TeamsList teamsList_) throws IOException {
		// TODO Auto-generated method stub
		 try (BufferedReader reader = new BufferedReader(is_)) {
	         StringTokenizer tokenizer;   
			 String line, category;
	            AbstractPlayer player = null;
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
	                	category = tokenizer.nextToken();
	                }
	                catch(Exception e_) {
	                	_logger.error("Read in category:" + e_.toString());
	                	continue;
	                }
	                
	                try {
		                if(category.equals("NBA")) {
		                	player = new NBAPlayer();
		                }
		                else if(category.equals("NFL")) {
		                	player = new NFLPlayer();
		                }
		                else if(category.equals("NHL")) {
		                	player = new NHLPlayer();
		                }
		                else if(category.equals("MLB")) {
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
	                
	                try {
	                	player.set_playerID(Integer.parseInt(tokenizer.nextToken()));
	                		
	                }	                
	                catch(Exception e_){
	                	_logger.error("setPlayerID:" + e_.toString());
	                	continue;
	                }
	                        	             	
	                	
	                try {
	                	
	                	player.set_jerseyNum(Integer.parseInt(tokenizer.nextToken()));
	                }
	                catch(Exception e_) {
	                	_logger.error("setJerseyNum:" + e_.toString());
	                }
	                
	                try {
	                	player.setFirstName(tokenizer.nextToken());
	                }
	                catch(Exception e_) {
	                	_logger.error("setFirstName:" + e_.toString());
	                
	                }
	                try {
	                	player.setLastName(tokenizer.nextToken());
	                	if(playerlist_.returnPlayersMap().contains(player.get_playerID()) && 
	                			!playerlist_.returnPlayersMap().get(player.get_playerID()).getFirstName().equals(player.getFirstName()) && 
	                			!playerlist_.returnPlayersMap().get(player.get_playerID()).getLastName().equals(player.getLastName()))
	                		throw new Exception("Player ID already exists for another player in map");
	                }
	                catch(Exception e_){
	                	_logger.error("setLastName:" + e_.toString());
	                }
	                
	                try {
	                	parseCurrentTeam(teamsList_, tokenizer.nextToken(), player);
	               
	                }
	                catch(Exception e_) {
	                	_logger.error("setTeam:" + e_.toString());
	                	continue;
	                }
	             if(playerlist_.returnPlayersMap().get(player.get_playerID()) != null) {
	            	 AbstractPlayer existingPlayer = playerlist_.returnPlayersMap().get(player.get_playerID());
	            	 if(!existingPlayer.equals(player)) {
	            		 teamsList_.getTeamMap().get(existingPlayer.getCurrentTeam().fullTeamName()).getListOfPLayers().remove(Integer.valueOf(player.get_playerID()));
	            		 TeamHistory oldTeam = new TeamHistory(existingPlayer.getCurrentTeam().getLocation(), existingPlayer.getCurrentTeam().getTeamName());
	            		 playerlist_.returnPlayersMap().get(existingPlayer.get_playerID()).get_HistoryOfTeamsForPlayers().add(oldTeam);
	            		
	            		 //update existing player with new data
	            		 existingPlayer = updatePlayer(player, existingPlayer, playerlist_);
	            		
	            		 teamsList_.getTeamMap().get(existingPlayer.getCurrentTeam().fullTeamName()).getListOfPLayers().add(player.get_playerID());
	            	 }
	             }
	             else {
	            	 
	            	 try {
	            		 addPlayer(player, playerlist_);
		 
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
		 String category = "";
            AbstractPlayer player = null;
		
		if ("".equals(line))
			throw new Exception();
        
		tokenizer = new StringTokenizer(line, DELIM);

        try {
        	category = tokenizer.nextToken();
        }
        catch(Exception e_) {
        	_logger.error("Read in category:" + e_.toString());
        	throw e_;
        }
        
        try {
            if(category.equals("NBA")) {
            	player = new NBAPlayer();
            }
            else if(category.equals("NFL")) {
            	player = new NFLPlayer();
            }
            else if(category.equals("NHL")) {
            	player = new NHLPlayer();
            }
            else if(category.equals("MLB")) {
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
        	parseCurrentTeam(teamsList_, tokenizer.nextToken(), player);	           
        }
        catch(Exception e_) {
        	_logger.error("setTeam:" + e_.toString());
        	throw e_;
        }
        if(playersList_.returnPlayersMap().get(player.get_playerID()) != null) {
       	 AbstractPlayer existingPlayer = playersList_.returnPlayersMap().get(player.get_playerID());
       	 if(!existingPlayer.equals(player)) {
       		 teamsList_.getTeamMap().get(existingPlayer.getCurrentTeam().fullTeamName()).getListOfPLayers().remove(Integer.valueOf(player.get_playerID()));
       		 TeamHistory oldTeam = new TeamHistory(existingPlayer.getCurrentTeam().getLocation(), existingPlayer.getCurrentTeam().getTeamName());
       		 playersList_.returnPlayersMap().get(existingPlayer.get_playerID()).get_HistoryOfTeamsForPlayers().add(oldTeam);
       		
       		 //update existing player with new data
       		 existingPlayer = updatePlayer(player, existingPlayer, playersList_);
       		
       		 teamsList_.getTeamMap().get(existingPlayer.getCurrentTeam().fullTeamName()).getListOfPLayers().add(player.get_playerID());
       	 }
        }
        else {
       	 
       	 try {
       		 addPlayer(player, playersList_);
           	 //TODO: NULL POINT exception possible here
           	 //Also shouldn't add team if there is an invalid 
       		 teamsList_.getTeamMap().get(player.getCurrentTeam().fullTeamName()).getListOfPLayers().add(player.get_playerID());
       	 }
       	 catch(Exception e_) {
       		 _logger.error("Player's current team is not a valid team: " + e_.toString());
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
