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

import javax.swing.text.ChangedCharSetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.mlb.models.MLBPlayer;
import com.sts.mlb.models.TeamMLB;
import com.sts.model.exception.MismatchPlayerandTeamSportException;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.nba.models.NBAPlayer;
import com.sts.nba.models.TeamNBA;
import com.sts.nfl.models.NFLPlayer;
import com.sts.nfl.models.TeamNFL;
import com.sts.nhl.models.NHLPlayer;
import com.sts.nhl.models.TeamNHL;
import com.sts.util.CustomValidations;
import com.sts.util.model.KeyForTeamsMap;
import com.sts.util.model.TeamPlayerHistory;
import com.sts.view.TeamsCityRequest;
/**
 * This class will read from the players list input, and will deal with switching teams and changing Jersey numbers.
 * We will store all the players in a hash map when the ID is the key for the map.
 * We also reading the sport category and declare if the player in NFl, NHl, NBA, or MLB. We map each player to his category defined class
 * so we can update unique statistics in the future.
 */
public class PlayersReader {
	private Logger _logger;
	private BufferedReader reader;

    private static final String DELIM = "|";
    //Constructor
    public PlayersReader() {
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
    public void parseCurrentTeam(TeamsList teamList_, KeyForTeamsMap teamKey_, AbstractPlayer player_, PlayersList playerlist_, TeamPlayerHistory currentTeamHistory_) throws Exception {
    	AbstractPlayer tempPlayer;
    	ArrayList<TeamPlayerHistory> temp1;
    	AbstractTeam temp;
    	int tempJnumb;
   
    	//Check to see if team actually exists for the player to be on
    	if(teamList_.getTeamMap().get(teamKey_) == null) {
    		throw new TeamNotFoundException(teamKey_.toString());
    	}
    	else {
    		//checks to see if the team and player are of the same sport type
    		if(teamList_.getTeamMap().get(teamKey_).getTeamSport() != player_.get_sportCategory()) {
    			throw new MismatchPlayerandTeamSportException();
    		}
    		else {
    			temp = teamList_.getTeamMap().get(teamKey_);
    			temp1 = temp.getCurrentPlayers();
    			//Check for duplicate Jersey #'s on the same team
    			for(int i=0;i<temp1.size();i++) {
    				tempPlayer=playerlist_.returnPlayersMap().get(temp1.get(i).getPlayer().get_playerID());
    				tempJnumb=tempPlayer.getJerseyNum();
    				if(tempJnumb==player_.getJerseyNum()) {
    					throw new Exception ("Duplicate Jersey Number found for the same team, this is not allowed");
    					
    				}
    			}
    			

    		}
    		currentTeamHistory_.setTeam(teamList_.getTeamMap().get(teamKey_));
    		player_.setCurrentTeamHistory(currentTeamHistory_);
    		player_.getPlayerTeams().add(currentTeamHistory_);

    }
  }
    
    
    /**
     * Read all the players from a file and store into a players map
     */
	public void readFromFileForLists(Reader is_, PlayersList playerlist_, TeamsList teamsList_) throws IOException {
		// TODO Auto-generated method stub
		 try (BufferedReader reader = new BufferedReader(is_)) {
	         StringTokenizer tokenizer;   
	         AbstractPlayer player = null;
	         int playerID;
			 String line;
			 String teamCity;
			 String teamName;
			 KeyForTeamsMap teamKey = null;
			 Date StartDate;
			 TeamPlayerHistory currentTeam;
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
	                	playerID = Integer.parseInt(tokenizer.nextToken());
	                	if(playerlist_.returnPlayersMap().containsKey(playerID))
	                		throw new Exception("This player ID has already been used for another player");
	                	else
	                		player.set_playerID(playerID);
	     	
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
	                	teamCity = tokenizer.nextToken();
	                	teamName = tokenizer.nextToken();
	                	teamKey = new KeyForTeamsMap(teamCity, teamName);
	                   	StartDate = convertStringToDate(tokenizer.nextToken());
	                	currentTeam = new TeamPlayerHistory();
	                	currentTeam.setStartDate(StartDate);
	                	currentTeam.setStatus(true);
	                	parseCurrentTeam(teamsList_, teamKey, player, playerlist_, currentTeam);
	                }
	                catch(Exception e_) {
	                	_logger.error("setTeam:" + e_.toString());
	                	continue;
	                }
	                
	            	 // Add new player to players map
	            	 try {
	            		 currentTeam.setPlayer(player);
	            		 teamsList_.getTeamMap().get(teamKey).getEntireHistoryPlayers().add(currentTeam);

	            		 addPlayer(player, playerlist_);

	            		 // Add player to their current team
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
		 String teamCity;
		 String teamName;
		 KeyForTeamsMap teamKey = null;
		 Date StartDate;
		 TeamPlayerHistory currentTeam;
		 StringTokenizer tokenizer;   
		 SportsCategory category = null;
         AbstractPlayer player = null;
         int playerID;
		
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
        	playerID = Integer.parseInt(tokenizer.nextToken());
        	if(playersList_.returnPlayersMap().containsKey(playerID))
        		throw new Exception("This player ID has already been used for another player");
        	else
        		player.set_playerID(playerID);
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
        	teamCity = tokenizer.nextToken();
        	teamName = tokenizer.nextToken();
           	StartDate = convertStringToDate(tokenizer.nextToken());
        	currentTeam = new TeamPlayerHistory();
        	currentTeam.setStartDate(StartDate);
        	currentTeam.setStatus(true);
        	teamKey = new KeyForTeamsMap(teamCity, teamName);
        	parseCurrentTeam(teamsList_, teamKey, player, playersList_, currentTeam);
        }
        catch(Exception e_) {
        	_logger.error("setTeam:" + e_.toString());
        	throw e_;
        }

    	 // Add new player to players map
    	 try {
    		 currentTeam.setPlayer(player);
    		 addPlayer(player, playersList_);
    		 // Add player to their current team
    		 teamsList_.getTeamMap().get(teamKey).getEntireHistoryPlayers().add(currentTeam);
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
		SportsCategory category = null; // Sport the player plays
		AbstractPlayer player = null;
		AbstractTeam teamofPlayer = null;
		String lineForTeam = null;
		TeamPlayerHistory currentHistory = null;   // Will store the current history of the team for this player
		Date StartDate = null;  // Start Date of this player on this team
		KeyForTeamsMap teamKey = null;
		TeamsCityRequest teamReq = new TeamsCityRequest();
		boolean isValid = false; //to check if the input by the user is valid. If not, prompt
								// for input again
		String teamCity = null;
		String teamName = null;
		CustomValidations cvalidations = new CustomValidations();
		int jerseyNumber = -1;
		String date = null;
		String tempFirstName;
		String tempLastName;
		
		_logger.info("Enter Sport of Player"); // User enter in which sport the player plays
		//Prompt for which sport the player being created plays
		do {
			try {
				
				category = SportsCategory.valueOf(reader.readLine().toUpperCase());
				isValid=true;
				
				
			} catch (Exception e) {
				
				_logger.error("You have entered Invalid Category.");
				_logger.info("Please enter from the following "+java.util.Arrays.asList(SportsCategory.values()));
			} 
		} while (!isValid);
	      
		try {
                if(category.equals(SportsCategory.valueOf("NBA"))) {
                	player = new NBAPlayer();
                	teamofPlayer = new TeamNBA();
                	
                }
                else if(category.equals(SportsCategory.valueOf("NFL"))) {
                	player = new NFLPlayer();
                	teamofPlayer = new TeamNFL();
                }
                else if(category.equals(SportsCategory.valueOf("NHL"))) {
                	player = new NHLPlayer();
                	teamofPlayer = new TeamNHL();
                }
                else if(category.equals(SportsCategory.valueOf("MLB"))) {
                	player = new MLBPlayer();
                	teamofPlayer = new TeamMLB();
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
		
		do {
			try {
				_logger.info("Enter Player's First Name: ");
				tempFirstName = reader.readLine();
				if (cvalidations.nameValidation(tempFirstName)) {
					isValid = true;
					tempFirstName = tempFirstName.substring(0, 1).toUpperCase() + tempFirstName.substring(1);
					break;
				} else {
					isValid=false;
					continue;
				}

				
			} catch (Exception e_) {
				_logger.error("Entering First Name: " + e_.toString());
				return;
			} 
		} while (!isValid);
		player.setFirstName(tempFirstName);
		
		//Prompt for Player's last name
		do {
			try {
				_logger.info("Enter Player's Last Name: ");
				tempLastName = reader.readLine();
				if (cvalidations.nameValidation(tempLastName)) {
					isValid = true;
					tempLastName=tempLastName.substring(0,1).toUpperCase()+tempLastName.substring(1);
					break;
				} else {
					isValid = false;
					continue;
				}
			} catch (Exception e_) {
				_logger.error("Entering Last Name: " + e_.toString());
				return;
			} 
		} while (!isValid);
		player.setLastName(tempLastName);
		
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
		
		_logger.info("Enter the player's current team from the following valid teams: ");
		teamReq.displayTeams(listofTeams_, category);
		_logger.info("If player is currently not on a team, then enter 'N/A'"); //Will track player without a team as well (free agent)
		_logger.info("Enter team's city: ");
		isValid = false;

			do {
				try {
					teamCity = reader.readLine();
					if(teamCity.equals("N/A")) {
						player.getCurrentTeamHistory().setStatus(false);
						player.getCurrentTeamHistory().setPlayer(player);
						listofPlayers_.returnPlayersMap().put(player.get_playerID(), player);
						_logger.trace("New player successfully created and added to player's map");
						return;
					}
					else {
						if(cvalidations.cityValidation(listofTeams_,teamCity)) {
							isValid=true;
							break;
						}
						else {
							isValid=false;
							continue;
						}
					}
				} catch (IOException e) {
					_logger.info("Invalid city.. Please re-enter " + e.toString());
				} 
			} while (!isValid);
		
		
		if(isValid)
			teamofPlayer.setLocation(lineForTeam);
		
		isValid = false;
		
		do {
			_logger.info("Valid sports cities and team for "+category.toString()+" are [Cities : Teams] ");
			teamReq.displayTeams(listofTeams_,category);
			try {
				_logger.info("Enter the team name corresponding to "+teamCity);
				teamName = reader.readLine();
				if(cvalidations.teamValidation(listofTeams_, teamName,teamCity)) {
					isValid=true;
					break;
				}else {
					isValid=false;
					continue;
				}
			}catch (IOException e) {
				_logger.error("Invalid Team Name. Please re-enter " + e.toString());
				isValid=false;
			} 
		} while (!isValid);
		
		
		if(isValid) {
			teamofPlayer.setLocation(teamCity);
			teamofPlayer.setTeamName(teamName);
			teamofPlayer.setTeamSport(category);
			player.getCurrentTeamHistory().setTeam(teamofPlayer);
			teamKey = new KeyForTeamsMap(player.getCurrentTeamHistory().getTeam().getLocation(), player.getCurrentTeamHistory().getTeam().getTeamName());
			currentHistory = new TeamPlayerHistory();
			currentHistory.setTeam(listofTeams_.getTeamMap().get(teamKey));
		}
		
		isValid = false;
		
		//Prompt for Player's jersey number
		do {
			_logger.info("Enter Player's Jersey Number: ");
			try {
				jerseyNumber = Integer.parseInt(reader.readLine());
	           	player.set_jerseyNum(jerseyNumber);
			 	currentHistory.setStatus(true);
	           	parseCurrentTeam(listofTeams_, teamKey, player, listofPlayers_,currentHistory);
				isValid = true;
				break;
			}
			catch(Exception e_) {
				_logger.error("Entering Jersey Number: " + e_.toString());
				isValid = false;
				
			}
		}while(!isValid);
		
		
		do {
			try {
				_logger.info("Enter the Game Date in this format YYYY-MM-DD");
				date = reader.readLine();
	
				if(cvalidations.dateValidation(date)) {
					isValid=true;
					break;
				}else {
					isValid=false;
					continue;
				}
			} catch (Exception e) {
				_logger.error("Please enter the date in the following format YYYY-MM-DD"+e.toString());
				isValid=false;
			} 
		} while (!isValid);
		
		if(isValid) {
			try {
				StartDate = convertStringToDate(date);
			   	currentHistory.setStartDate(StartDate);
			} catch (Exception e_) {
				_logger.error("Error parsing date: " + e_.toString());
				_logger.error("Player not created");
				return;
			}
        
		}

		 // Add player to their current team		
		currentHistory.setPlayer(player);
		player.setCurrentTeamHistory(currentHistory);  // This is the player's current team
		addPlayer(player, listofPlayers_); // Add player to player's map
		//Add player to the team's history of players
		listofTeams_.getTeamMap().get(teamKey).getEntireHistoryPlayers().add(player.getCurrentTeamHistory()); 
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
