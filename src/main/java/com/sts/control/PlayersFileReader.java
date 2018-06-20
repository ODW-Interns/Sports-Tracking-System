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
import com.sts.concretemodel.TeamPlayer;
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
	private BufferedReader reader;

    private static final String DELIM = "|";
    //Constructor
    public PlayersFileReader() {
        _logger = LoggerFactory.getLogger(getClass().getSimpleName());
        reader = new BufferedReader(new InputStreamReader(System.in));
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
    public Date convertStringToDate(String dateString_) throws Exception
    {
        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try{
            date = ((Date)df.parse(dateString_));
        }
        catch ( Exception e_ ){
        	_logger.error(e_.toString());
        	throw e_;
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
    private void parseCurrentTeam(TeamsList teamList_, String teamStr_, AbstractPlayer player_, PlayersList playerlist_, TeamPlayer currentTeamHistory_) throws Exception {
    	AbstractPlayer tempPlayer;
    	ArrayList<TeamPlayer> temp1;
    	AbstractTeam temp;
    	int tempJnumb;
    	
    	//Check to see if team actually exists for the player to be on
    	if(teamList_.getTeamMap().get(teamStr_) == null) {
    		throw new TeamNotFoundException(teamStr_);
    	}
    	else {
    		//checks to see if the team and player are of the same sport type
    		if(teamList_.getTeamMap().get(teamStr_).getTeamSport() != player_.get_sportCategory()) {
    			throw new MismatchPlayerandTeamSportException();
    		}
    		else {
    	
    			temp = teamList_.getTeamMap().get(teamStr_);
    			temp1 = temp.getCurrentPlayers();
    			//Check for duplicate Jersey #'s on the same team
    			for(int i=0;i<temp1.size();i++) {

    				tempPlayer=playerlist_.returnPlayersMap().get(temp1.get(i).getPlayer().get_playerID());
    				tempJnumb=tempPlayer.getJerseyNum();
    				if(tempJnumb==player_.getJerseyNum()) {
    					throw new Exception ("Duplicate Jersey Number");
    					
    				}
    			}
    			

    		}
    		currentTeamHistory_.setTeam(teamList_.getTeamMap().get(teamStr_));
    		player_.setCurrentTeamHistory(currentTeamHistory_);
    		player_.getPlayerTeams().add(currentTeamHistory_);

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
			 String line;
			 String teamOfPlayer;
			 Date StartDate;
			 TeamPlayer currentTeam;
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
	                	teamOfPlayer = tokenizer.nextToken();
	                   	StartDate = convertStringToDate(tokenizer.nextToken());
	                	currentTeam = new TeamPlayer();
	                	currentTeam.setStartDate(StartDate);
	                	currentTeam.setStatus(true);
	                	parseCurrentTeam(teamsList_, teamOfPlayer, player, playerlist_, currentTeam);
	               
	                }
	                catch(Exception e_) {
	                	_logger.error("setTeam:" + e_.toString());
	                	continue;
	                }
	                
	            	 // Add new player to players map
	            	 try {
	            		 addPlayer(player, playerlist_);
	            		 // Add player to their current team
	            		 currentTeam.setPlayer(player);
	            		 teamsList_.getTeamMap().get(player.getCurrentTeamHistory().getTeam().fullTeamName()).getEntireHistoryPlayers().add(currentTeam);
	            	 }
	            	 catch(Exception e_) {
	            		 _logger.error("Player's current team is not a valid team: " + e_.toString());
	            	 }
	                
	           }
		 } 
	}
	
	/*
	 * Method to read in player data to add player, from a string of data. It does this 
	 * in a way similar to reading from a data file in the method readFromFileForLists.
	 */
	
	public void readFromStringForList(String line, PlayersList playersList_, TeamsList teamsList_) throws Exception {
		 String teamOfPlayer;
		 Date StartDate;
		 TeamPlayer currentTeam;
		 StringTokenizer tokenizer;   
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
        	teamOfPlayer = tokenizer.nextToken();
           	StartDate = convertStringToDate(tokenizer.nextToken());
        	currentTeam = new TeamPlayer();
        	currentTeam.setStartDate(StartDate);
        	currentTeam.setStatus(true);
        	parseCurrentTeam(teamsList_, teamOfPlayer, player, playersList_, currentTeam);
        }
        catch(Exception e_) {
        	_logger.error("setTeam:" + e_.toString());
        	throw e_;
        }

    	 // Add new player to players map
    	 try {
    		 addPlayer(player, playersList_);
    		 // Add player to their current team
    		 currentTeam.setPlayer(player);
    		 teamsList_.getTeamMap().get(player.getCurrentTeamHistory().getTeam().fullTeamName()).getEntireHistoryPlayers().add(currentTeam);
    	 }
    	 catch(Exception e_) {
    		 _logger.error("Player's current team is not a valid team: " + e_.toString());
    		 throw e_;
    	 }
        
   
	}
	
	/*
	 * Method used to satisfy user's request to create and track a new player.
	 * It will prompt for all the necessary data to create a player and
	 * will check exceptions such as
	 * 1) Making sure the team the player is on actually exists in the team's map
	 * 2) No 2 players on the same team can have the same jersey number
	 * 3) Making sure that this newly created player has its own unique player ID
	 * 4) Making sure the sport this player plays matches the team's sport
	 */
	public void createPlayers(TeamsList listofTeams_, PlayersList listofPlayers_) throws IOException {
		SportsCategory category; // Sport the player plays
		AbstractPlayer player = null;
		String lineForTeam = null;
		TeamPlayer currentHistory = null;   // Will store the current history of the team for this player
		Date StartDate;  // Start Date of this player on this team
		_logger.info("Enter Sport of Player"); // User enter in which sport the player plays
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
          	return;
          }
	      
	      try {
          	player.set_sportCategory(category);
          }
          catch(Exception e_) {
          	_logger.error("setCategory:" + e_.toString());
          	return;
          	
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
			return;
		}
		
		//Prompt for Player's last name
		_logger.info("Enter Player's Last Name: ");
		try {
			player.setLastName(reader.readLine());
		}
		catch(Exception e_){
			_logger.error("Entering Last Name: " + e_.toString());
			return;
		}
		
	     try {
         	//Check here if player ID has already been used for another player
         	if(listofPlayers_.returnPlayersMap().containsKey(player.get_playerID()) && 
         			(!(listofPlayers_.returnPlayersMap().get(player.get_playerID()).getFirstName().equals(player.getFirstName())) || 
         			!listofPlayers_.returnPlayersMap().get(player.get_playerID()).getLastName().equals(player.getLastName()))) {
         		throw new Exception("Player ID already exists for another player in map");}
         }
         catch(Exception e_) {
         	_logger.error(e_.toString());
         	return;
         }
		
		_logger.info("Enter Player's Current Team: ");
		_logger.info("If player is currently not on a team, then leave blank"); //Will track player without a team as well (free agent)
		lineForTeam = reader.readLine();
		try {
			if(lineForTeam.equals("")) {   // Player without a team
				listofPlayers_.returnPlayersMap().put(player.get_playerID(), player);
				_logger.trace("New player successfully created and added to player's map");
				return;
			}
			else { 
				if(listofTeams_.getTeamMap().get(lineForTeam) != null) { // If player does have a team, make sure the team exists
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
		
		//Prompt for Player's jersey number
		_logger.info("Enter Player's Jersey Number: ");
		try {
			player.set_jerseyNum(Integer.parseInt(reader.readLine()));
			System.out.println("Setting Jersey Num" + player.getJerseyNum());
		}
		catch(Exception e_) {
			_logger.error("Entering Jersey Number: " + e_.toString());
			return;
		}
		
		//Prompt for date of when the player started on this team
		_logger.info("Enter the start date of the player on this team in this format(yyyy-mm-dd):");
		try {
           	StartDate = convertStringToDate(reader.readLine());
           	currentHistory.setStartDate(StartDate);
           	currentHistory.setStatus(true);
           	parseCurrentTeam(listofTeams_, lineForTeam, player, listofPlayers_,currentHistory);
		}
		catch(Exception e_) {
			_logger.error("Setting Start Date on team: " + e_.toString());
			_logger.error("Player was not created");
			return;
		}
		 // Add player to their current team		
		currentHistory.setPlayer(player);
		player.setCurrentTeamHistory(currentHistory);  // This is the player's current team
		addPlayer(player, listofPlayers_); // Add player to player's map
		//Add player to the team's history of players
		listofTeams_.getTeamMap().get(player.getCurrentTeamHistory().getTeam().fullTeamName()).getEntireHistoryPlayers().add(player.getCurrentTeamHistory()); 
		_logger.trace("Successfully added player to player's map");
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
