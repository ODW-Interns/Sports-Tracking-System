package com.sts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.AbstractGame;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.Key;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;
import com.sts.control.StoreDataFromInputFile;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Class that starts the system
public class SportsSystem {
	
	//List to keep track of games that have started that needs to be updated when the game finishes
	private ArrayList<Key> _GamesThatNeedUpdating;
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
    
    //Constructor
	SportsSystem() throws RuntimeException, IOException, ParseException{
	    _logger = LoggerFactory.getLogger(getClass().getSimpleName());
	    _GamesThatNeedUpdating = new ArrayList<Key>();
	    reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	//Thread to run every specified time interval to check if any new games have started
	//If a game has started, then add game to list of games that need updating
    public void RunThreadToCheckForGamesThatStarted(GamesList _listofGames_){
 	    Runnable runnable = new Runnable() {
 	    	public void run() {
 	    		AbstractTeam awayTeam;
 	    		AbstractTeam homeTeam;
 	    		timeNow = ZonedDateTime.now();
 	    		int tempUID = -1;
 	    		Key lowestkey = new Key(timeNow,tempUID);
 	    		//Map of all games who's start time is past the current time
 	    		SortedMap<Key, AbstractGame> pastGames = _listofGames_.getGamesMap().tailMap(lowestkey);
 	    		for(Entry<Key, AbstractGame> entry : pastGames.entrySet()) {
 	    			if(entry.getValue().getDuration() == null) {
 	    				//Set players that played in game to current rosters of each team
 	    				awayTeam = _listofTeams.getTeamMap().get(entry.getValue().getAwayTeam().fullTeamName());
 	    				homeTeam = _listofTeams.getTeamMap().get(entry.getValue().getHomeTeam().fullTeamName());
 	    				entry.getValue().setListOfAwayPlayers(awayTeam.getListOfPLayers());
 	    				entry.getValue().setListofHomePlayers(homeTeam.getListOfPLayers());
 	    				//Set default duration to 4 hours until updated
 	    				entry.getValue().setDuration(Duration.parse("PT4H"));
 	    				
 	    				_logger.trace("A new game has started(GameID: {} , Start Time:{})", entry.getKey().getGameUID(), entry.getKey().getStartTime());
 	    				_logger.info("Please update the game when it is finished");
 	    				//Add game to list of games that need updating
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
 	    //Run every 10 seconds
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
		StoreDataFromInputFile.storeDataIntoTeamList("/Teams.csv", _listofGames, _listofTeams);
		StoreDataFromInputFile.storeDataIntoPlayerList("/Players.csv", _listofGames, _listofTeams, _listofPlayers);
	    StoreDataFromInputFile.storeDataIntoGameList("/games.csv", _listofGames, _listofTeams, _listofPlayers);
	    RunThreadToCheckForGamesThatStarted(_listofGames);
	    _logger.info("Welcome to sports Tracking system");

	    boolean isNumber = false; // used to make sure correct input has been chosen
	    int choice = 0;   
	    String fileName;
	    boolean c = true;

			do {
				
					while (c) {

						_logger.info("1: Enter 1 to display list of finished games");
						_logger.info("2: Enter 2 to display list of upcoming games");
						_logger.info("3: Enter 3 to update player(s)");
						_logger.info("4: Enter 4 to update game(s)");
						_logger.info("5: Enter 5 to exit");

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
						case 3: //prompts user for a file of all players who need updating with the player's new data
							_logger.info("Enter the file name to update player(s) you want");
							fileName = reader.readLine();
							String path = "/";
							path = path + fileName;
							try {
								//Parse file line by line to update player's data
								StoreDataFromInputFile.storeDataIntoPlayerList(path, _listofGames, _listofTeams,
										_listofPlayers);
							} catch (Exception e_) {
								_logger.error("File not found: " + e_.toString());
							}

							break;
						case 4: // Update games
							
							_logger.info("There are {} game(s) that need to be updated", _GamesThatNeedUpdating.size());
							updateGames(_listofGames);
							break;
						case 5:
							System.exit(0);
							break;
						default:

							_logger.info("Wrong choice input. Please choose from the following options");

							break;

						}
					}
				
			} while (!isNumber);

	}
	
	/**
	 * Method to update games that finished and need results of game
	 */
	public void updateGames(GamesList _listofGames_) {
	    Iterator<Key> KeyIterator;
		Key currentKey;
		AbstractGame gameUpdating = null;
	    int durationHours;
	    int durationMinutes;
	    int durationSeconds;
	    StringBuilder durationString = new StringBuilder("PT");
		KeyIterator = _GamesThatNeedUpdating.iterator();		
		while(KeyIterator.hasNext()) {
			currentKey = KeyIterator.next();
			gameUpdating = _listofGames_.getGamesMap().get(currentKey);
			_logger.info("Updating Game - GameID: {} , Start Time: {}", currentKey.getGameUID(), currentKey.getStartTime());
			_logger.info("Enter Duration of the game"); // Prompt User for the duration of the game
			_logger.info("Hour(s):"); // Hours the game lasted
			try {
				durationHours = Integer.parseInt(reader.readLine());
			}
			catch(Exception e_) {
				_logger.error(e_.toString());
				continue;
			}
			_logger.info("Minutes(s):"); // Minutes the game lasted
			try {
				durationMinutes = Integer.parseInt(reader.readLine());
			}
			catch(Exception e_) {
				_logger.error(e_.toString());
				continue;
			}
			_logger.info("Second(s):"); // Seconds the game lasted
			try {
				durationSeconds = Integer.parseInt(reader.readLine());
			}
			catch(Exception e_) {
				_logger.error(e_.toString());
				continue;
			}
			durationString.append(durationHours).append("H").append(durationMinutes).append("M").append(durationSeconds).append("S");
			gameUpdating.setDuration(Duration.parse(durationString)); // set the duration for the game
			
			gameUpdating.setFinishTime(gameUpdating.getStartTime().plus(gameUpdating.getDuration())); // Finish time = start time + duration
			_listofGames_.getGamesMap().get(currentKey).setDuration(gameUpdating.getDuration()); 
			_listofGames_.getGamesMap().get(currentKey).setFinishTime(gameUpdating.getFinishTime());
			_logger.info("Update final scores");
			_logger.info("Home Team Final Score:"); // Final Score for home team
			try {
				gameUpdating.setHomeTeamScore(Integer.parseInt(reader.readLine()));
			}
			catch(Exception e_) {
				_logger.error(e_.toString());
				continue;
			}
			_logger.info("Away Team Final Score:"); // Final Score for away team
			try {
				gameUpdating.setAwayTeamScore(Integer.parseInt(reader.readLine()));
			}
			catch(Exception e_) {
				_logger.error(e_.toString());
				continue;
			}
			_listofGames_.getGamesMap().get(currentKey).setAwayTeamScore(gameUpdating.getaTeamScore());
			_listofGames_.getGamesMap().get(currentKey).setHomeTeamScore(gameUpdating.getHomeTeamScore());
			_logger.info("Enter attendance of game:");
			try {
				gameUpdating.setAttendance(Integer.parseInt(reader.readLine())); // attendance of game
			}
			catch(Exception e_) {
				_logger.error(e_.toString());
				continue;
			}
			_logger.trace("Game successfully updated:");
			_logger.info(_listofGames_.getGamesMap().get(currentKey).toString());
		}
	}
	
	//MAIN
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem();
		system.startSystem();
		

	}
}