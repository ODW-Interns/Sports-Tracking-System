package com.sts.main;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.Game;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.Key;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;
import com.sts.control.StoreDataFromInputFile;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SportsSystem {
	
	private ArrayList<Key> _GamesThatNeedUpdating;
    private Logger _logger;
    private ZonedDateTime timeNow;
    private Scanner input;
    private GamesList _listofGames;
    private TeamsList _listofTeams;
    private PlayersList _listofPlayers;
    
	SportsSystem() throws RuntimeException, IOException, ParseException{
	    _logger = LoggerFactory.getLogger(getClass().getSimpleName());
	    _GamesThatNeedUpdating = new ArrayList<Key>();
	    input= new Scanner(System.in);
	}
	
    public void RunThreadToCheckForGamesThatStarted(GamesList listofGames_){
 	    Runnable runnable = new Runnable() {
 	    	public void run() {
 	    		AbstractTeam awayTeam;
 	    		AbstractTeam homeTeam;
 	    		timeNow = ZonedDateTime.now();
 	    		int tempUID = -1;
 	    		Key lowestkey = new Key(timeNow,tempUID);
 	    		SortedMap<Key, Game> pastGames = listofGames_.getGamesMap().tailMap(lowestkey);
 	    		for(Entry<Key, Game> entry : pastGames.entrySet()) {
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
 	    service.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);
    }

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

	   
	    boolean isNumber;
	    int choice = 0;   
	    String fileName;
	    boolean c = true;
	    
	    while (c) {
	    	
	    	_logger.info("1: Enter 1 to display list of finished games");
	    	_logger.info("2: Enter 2 to display list of upcoming games");
	    	_logger.info("3: Enter 3 to update player(s))");
	    	_logger.info("4: Enter 4 to update game(s))");
	    	_logger.info("5: Enter 5 to exit");
		    
		    do {
		    	
		    	if(input.hasNextInt() ){
			    	
			   		choice=input.nextInt();
			   		isNumber=true;	
			   		
			   	}
			    else {
			    	
			    	_logger.info("Please choose an integer value from the above options");
			    	isNumber=false;
			    	input.nextInt();
			    	
			    }
		    	
		    }while(!isNumber);
			 
		    
		    switch (choice) {
			case 1:
				
				_listofGames.logFinishedGames(_listofPlayers);
				
				break;
			case 2:
				
			    _listofGames.logUpcomingGames(_listofPlayers);
				
			    break;
			case 3:
				input.nextLine();
				_logger.info("Enter the file name to update player(s) you want");
				fileName = input.nextLine();
				String path = "/";
				path = path + fileName;
				try {
					StoreDataFromInputFile.storeDataIntoPlayerList(path, _listofGames, _listofTeams, _listofPlayers);
				}
				catch(Exception e_1) {
					_logger.error("File not found: " + e_1.toString());
				}
				break;
			case 4:
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
		

	}
	
	
	public void updateGames(GamesList listofGames_) {
	    Iterator<Key> KeyIterator;
		Key currentKey;
		Game gameUpdating = null;
	    int durationHours;
	    int durationMinutes;
	    int durationSeconds;
	    StringBuilder durationString = new StringBuilder("PT");
		KeyIterator = _GamesThatNeedUpdating.iterator();		
		System.out.println(listofGames_.getGamesMap().size());
		while(KeyIterator.hasNext()) {
			currentKey = KeyIterator.next();
			gameUpdating = listofGames_.getGamesMap().get(currentKey);
			_logger.info("Updating Game - GameID: {} , Start Time: {}", currentKey.getGameUID(), currentKey.getStartTime());
			_logger.info("Enter Duration of the game");
			_logger.info("Hour(s):");
			durationHours = input.nextInt();
			input.nextLine();
			_logger.info("Minutes(s):");
			durationMinutes = input.nextInt();
			input.nextLine();
			_logger.info("Second(s):");
			durationSeconds = input.nextInt();
			input.nextLine();
			durationString.append(durationHours).append("H").append(durationMinutes).append("M").append(durationSeconds).append("S");
			gameUpdating.setDuration(Duration.parse(durationString));
			
			gameUpdating.setFinishTime(gameUpdating.getStartTime().plus(gameUpdating.getDuration()));
			listofGames_.getGamesMap().get(currentKey).setDuration(gameUpdating.getDuration());
			listofGames_.getGamesMap().get(currentKey).setFinishTime(gameUpdating.getFinishTime());
			_logger.info("Update final scores");
			_logger.info("Home Team Final Score:");
			gameUpdating.setHomeTeamScore(input.nextInt());
			input.nextLine();
			_logger.info("Away Team Final Score:");
			gameUpdating.setAwayTeamScore(input.nextInt());
			input.nextLine();
			listofGames_.getGamesMap().get(currentKey).setAwayTeamScore(gameUpdating.getaTeamScore());
			listofGames_.getGamesMap().get(currentKey).setHomeTeamScore(gameUpdating.getHomeTeamScore());
			_logger.info("Enter attendance of game:");
			gameUpdating.setAttendance(input.nextInt());
			input.nextLine();
			_logger.info(listofGames_.getGamesMap().get(currentKey).toString());
		}
	}
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem();
		system.startSystem();
		

	}
}