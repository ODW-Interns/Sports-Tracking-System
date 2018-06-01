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

import com.sts.abstractModel.Player;
import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.MLBPlayer;
import com.sts.concreteModel.NBAPlayer;
import com.sts.concreteModel.NFLPlayer;
import com.sts.concreteModel.NHLPlayer;
import com.sts.concreteModel.PlayersList;
import com.sts.concreteModel.TeamsList;

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
    
    
    
    
	private void readFromFileForLists(Reader is_, PlayersList playerlist_, TeamsList teamsList_) throws IOException {
		// TODO Auto-generated method stub
		 try (BufferedReader reader = new BufferedReader(is_)) {
	         StringTokenizer tokenizer;   
			 String line, first, last, category, teamStr;
	            Player player = null;
	            int jersey, id;
	       
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
	                }
	                catch(Exception e_){
	                	_logger.error("setLastName:" + e_.toString());
	                }
	                
	                try {
	                	teamStr = tokenizer.nextToken();
	                	player.setCurrentTeam(teamsList_.getTeamMap().get(teamStr));
	                	
	                }
	                catch(Exception e_) {
	                	_logger.error("setTeam:" + e_.toString());
	                }
	             addPlayer(player, playerlist_);
	            }
		 }
	}
	
	
	
	private void addPlayer(Player player_, PlayersList PlayersList_)
    {
        if (!player_.isValidPlayer())
        {
            _logger.error("Refusing to add invalid player: {}", player_);
            return;
        }
        
        //
        //Add game object to the map of games provided
        //
        PlayersList_.returnPlayersMap().put(player_.get_playerID(),player_);

        if (_logger.isTraceEnabled())
            _logger.trace("Adding new player to past list: {}", player_.toString());
    }


}
