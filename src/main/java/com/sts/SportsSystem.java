package com.sts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractGame;
import com.sts.control.EventHandler;
import com.sts.control.GamesList;
import com.sts.control.GamesReader;
import com.sts.control.PlayersList;
import com.sts.control.PlayersReader;
import com.sts.control.StoreDataFromInputFile;
import com.sts.control.TeamsList;
import com.sts.control.TeamsReader;
import com.sts.util.model.KeyForGamesMap;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*Class that starts the system
 * Main resides in this class that will call a method to display options for user
 * to use the system.
 */
public class SportsSystem {
	
	//List to keep track of games that have started that needs to be updated when the game finishes
	private ArrayList<KeyForGamesMap> _GamesThatNeedUpdating;
	//Logger
    private Logger _logger;
    //Current date and time
    private ZonedDateTime timeNow;
    //Object to store map of all games
    private GamesList _listofGames;
    //Object to store map of all teams
    private TeamsList _listofTeams;
    //Object to store map of all players
    private PlayersList _listofPlayers;
    private BufferedReader reader;
	// object to read data for a team
	private TeamsReader teamsReader;
	// object to read data for a player 
	private PlayersReader playersReader;
	// object to read data for a game
	private GamesReader gamesReader;

	//Handler to handle requests
	private EventHandler handler;
        
    //Constructor
	SportsSystem() throws RuntimeException, IOException, ParseException{
	    _logger = LoggerFactory.getLogger(getClass().getSimpleName());
	    _GamesThatNeedUpdating = new ArrayList<KeyForGamesMap>();
	    reader = new BufferedReader(new InputStreamReader(System.in));
	    teamsReader = new TeamsReader();
	    playersReader = new PlayersReader();
	    gamesReader = new GamesReader();
	    handler = new EventHandler();
	    
	}
	
	/*Thread to run every specified time interval to check if any new games have started
	 * 	If a game has started, then add game to list of games that need updating.
	 * The game that have started will need the following data when the game is finished:
	 * 1. Home team final score
	 * 2. Away team final score
	 * 3. Attendance for the game
	 * 4. Duration of the game
	 */
	public void RunThreadToCheckForGamesThatStarted(GamesList _listofGames_){
 	    Runnable runnable = new Runnable() {
 	    	public void run() {
 	    		timeNow = ZonedDateTime.now();  /* Get the time now in the current time zone
 	    										* This is used to partition the map into games that
 	    										* have passed or games that have not passed
 	    										*/
 	    		int tempUID = -1;
 	    		KeyForGamesMap lowestkey = new KeyForGamesMap(timeNow,tempUID);
 	    		//Map of all games who's start time is past the current time
 	    		
 	    		//This sorted map is a subset of the whole games map. It uses tailMap to retrieve only the games
 	    		// whose start times have passed from the entire gamesMap
 	    		SortedMap<KeyForGamesMap, AbstractGame> pastGames = _listofGames_.getGamesMap().tailMap(lowestkey);
 	    		
 	    		//Iterate through all these games that have started and find the ones that have not been updated as finished
 	    		for(Entry<KeyForGamesMap, AbstractGame> entry : pastGames.entrySet()) {
 	    			if(entry.getValue().getDuration() == null) {
 	    				 	    				
 	    				//Set default duration to 4 hours until updated
 	    				entry.getValue().setDuration(Duration.parse("PT4H"));
 	    				
 	    				_logger.trace("A new game has started(GameID: {} , Start Time:{})", entry.getKey().getGameUID(), entry.getKey().getStartTime());
 	    				_logger.info("Please update the game when it is finished");
 	    				
 	    				//Add game to list of games that need updating. This list contains all the keys of the gamesMap for games that need updating.
 	    				_GamesThatNeedUpdating.add(entry.getKey());
 	    				_logger.info("There are {} game that needs updating", _GamesThatNeedUpdating.size());
 	    			}
 	    			else {
 	    				break;
 	    			}
 	    		}
 	    	}
 	    };
 	    
 	    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
 	    //This method is ran every 10 seconds
 	    service.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);
    }

    //Main functionality of system in this method
	public void startSystem() throws RuntimeException, IOException, ParseException {
		//Create a GamesList object to hold all games 
		_listofGames = new GamesList();
		//Create a TeamsList object to hold all teams
		_listofTeams = new TeamsList();
		//Create a PlayersList object to hold all players
		_listofPlayers = new PlayersList();
		//Read from input files; initiating maps

		//Initialize some data from files
		
		//Read in data from file that have a list of teams
		StoreDataFromInputFile.storeDataIntoTeamList("/Teams.csv", _listofGames, _listofTeams);
		
		//Read in data from file that have a list of players
		StoreDataFromInputFile.storeDataIntoPlayerList("/Players.csv", _listofGames, _listofTeams, _listofPlayers);
	    
		//Read in data from file that have a list of games
		StoreDataFromInputFile.storeDataIntoGameList("/games.csv", _listofGames, _listofTeams, _listofPlayers);
	   
		//Run method with thread to keep checking for if any games have started while system has been running
		//If a game has started, the user needs to update the game when the game has finished
		RunThreadToCheckForGamesThatStarted(_listofGames);
	    _logger.info("Welcome to sports Tracking system");

	    boolean isNumber = false; // used to make sure correct input has been chosen
	    int choice = 0;   
	    boolean c = true;
	    
	    do {
				
					while (c) {
						//Log all games that have finished
						_logger.info("1: Enter 1 to log list of finished games");
						
						//Log all upcoming games
						_logger.info("2: Enter 2 to log list of upcoming games");
						
						//This option allows the user to create and add a player to be tracked by system
						_logger.info("3: Enter 3 to create a player");
						
						//This option allows the user to create and add a team to be tracked by system
						_logger.info("4: Enter 4 to create a team");
						
						//This option allows the user to create and add a game to be tracked by system
						_logger.info("5: Enter 5 to create a game");
						
						//Allows user to update any games that have started while the system has been running
						//With the needed data for when the game has finished
						_logger.info("6: Enter 6 to update game(s)");
						
						//Handles even when a player is moved to another team
						_logger.info("7: Enter 7 to move a player to another team");
						
						//Option to log all players being tracked in playersMap
						_logger.info("8: Enter 8 to log all players being tracked");
						
						//Option to log the current roster for a specified team
						_logger.info("9: Enter 9 to log all players for a specified team");
						
						// Option to remove a player off their team
						_logger.info("10: Enter 10 to remove a player from his current team");
						
						//Turn off system
						_logger.info("11: Enter 11 to exit");
						
						

						try {
							choice = Integer.parseInt(reader.readLine());
						}
						catch(Exception e_) {
							_logger.error("Please choose an integer value from the above options");
							continue;
						}	

						switch (choice) {
						case 1: // Log all finished games
							_listofGames.logFinishedGames(_listofPlayers);
							break;
							
						case 2: // Log all upcoming games
							_listofGames.logUpcomingGames(_listofPlayers);
							break;
						
						case 3: //prompts user for data to create a player and keep track of player in player's map
							playersReader.createPlayers(_listofTeams, _listofPlayers);
							break;
							
						case 4://prompts user for data to create a team and keep track of team
							teamsReader.createTeam(_listofTeams);
							break;
						
						case 5://prompts user for data to create a game and keep track of game
							try {
								gamesReader.createGames(_listofGames, _listofTeams, _listofPlayers);
							} catch (Exception e_) {
								_logger.error("Game was not created: " + e_.toString());
							}
							break;
							
						case 6: // Update finished games
							
							_logger.info("There are {} game(s) that need to be updated", _GamesThatNeedUpdating.size());
							handler.updateGames(_listofGames, _GamesThatNeedUpdating);
							break;
							
						case 7: // Move a player to another team
							handler.movePlayer(_listofTeams, _listofPlayers);
							break;
							
						case 8: // Log all players 
							handler.requestToLogAllPlayersInPlayerMap(_listofPlayers);
							   break;
						case 9:
							// Log all players on a current team that the user specifies
							handler.requestToLogAllPlayersOnTeam(_listofTeams);
							
							break;
						case 10:
							//Remove a player off of his current team
							handler.removePlayerFromTeam(_listofTeams, _listofPlayers);
							break;
						case 11: // Shut off system
							System.exit(0);
							break;	
							
							
						default:

							_logger.info("Wrong choice input. Please choose from the following options");

							break;
						
						}
					}
				
			} while (!isNumber);

	}
	
	
	//MAIN
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem();
		system.startSystem();
		

	}
}