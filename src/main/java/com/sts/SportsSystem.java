package com.sts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.AbstractGame;
import com.sts.abstractmodel.AbstractPlayer;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.Key;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamPlayer;
import com.sts.concretemodel.TeamsList;
import com.sts.control.GamesReader;
import com.sts.control.PlayersReader;
import com.sts.control.StoreDataFromInputFile;
import com.sts.control.TeamsReader;
import com.sts.model.exception.PlayerNotFoundException;
import com.sts.model.exception.TeamNotFoundException;

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
	//Initialize object to read data for a team
	private TeamsReader teamsReader;
	//Initialize object to read data for a player 
	private PlayersReader playersReader;
	//Initialize object to read data for a game
	private GamesReader gamesReader;
        
    //Constructor
	SportsSystem() throws RuntimeException, IOException, ParseException{
	    _logger = LoggerFactory.getLogger(getClass().getSimpleName());
	    _GamesThatNeedUpdating = new ArrayList<Key>();
	    reader = new BufferedReader(new InputStreamReader(System.in));
	    teamsReader = new TeamsReader();
	    playersReader = new PlayersReader();
	    gamesReader = new GamesReader();
	    
	}
	
	//Thread to run every specified time interval to check if any new games have started
	//If a game has started, then add game to list of games that need updating
    public void RunThreadToCheckForGamesThatStarted(GamesList _listofGames_){
 	    Runnable runnable = new Runnable() {
 	    	public void run() {
 	    		AbstractTeam awayTeam;
 	    		AbstractTeam homeTeam;
 	    		ArrayList<TeamPlayer> listofPlayersOnTeam = new ArrayList<TeamPlayer>();
 	    		timeNow = ZonedDateTime.now();
 	    		int tempUID = -1;
 	    		Key lowestkey = new Key(timeNow,tempUID);
 	    		//Map of all games who's start time is past the current time
 	    		SortedMap<Key, AbstractGame> pastGames = _listofGames_.getGamesMap().tailMap(lowestkey);
 	    		for(Entry<Key, AbstractGame> entry : pastGames.entrySet()) {
 	    			if(entry.getValue().getDuration() == null) {
 	    				//Set players that played in game to current rosters of each team
 	    				awayTeam = _listofTeams.getTeamMap().get(entry.getValue().getAwayTeam().fullTeamName());
 	    				listofPlayersOnTeam = awayTeam.getCurrentPlayers();
 	    				for(int i = 0 ; i < listofPlayersOnTeam.size(); i++) {
 	    					entry.getValue().getListOfAwayPlayers().add(listofPlayersOnTeam.get(i).getPlayer().get_playerID());
 	    				}
 	    				homeTeam = _listofTeams.getTeamMap().get(entry.getValue().getHomeTeam().fullTeamName());
 	    				listofPlayersOnTeam = homeTeam.getCurrentPlayers();
 	    				for(int i = 0 ; i < listofPlayersOnTeam.size(); i++) {
 	    					entry.getValue().getListofHomePlayers().add(listofPlayersOnTeam.get(i).getPlayer().get_playerID());
 	    				}
 	    				//entry.getValue().setListOfAwayPlayers(awayTeam.getCurrentPlayers());
 	    				//entry.getValue().setListofHomePlayers(homeTeam.getListOfPLayers());
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
	    boolean c = true;
	    
	    String city;
	    String teamName;
	    String fullTeamName;

			do {
				
					while (c) {

						_logger.info("1: Enter 1 to display list of finished games");
						_logger.info("2: Enter 2 to display list of upcoming games");
						_logger.info("3: Enter 3 to create a player");
						_logger.info("4: Enter 4 to create a team");
						_logger.info("5: Enter 5 to create a game");
						_logger.info("6: Enter 6 to update game(s)");
						_logger.info("7: Enter 7 to move a player to another team");
						_logger.info("8: Enter 8 to view all players being tracked");
						_logger.info("9: Enter 9 to view all players for a specified team");
						_logger.info("10: Enter 10 to exit");
						
						

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
							_listofGames.logFinishedGames(_listofPlayers);
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
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
							
						case 6: // Update finished games that have are missing data such as final scores and total attendance
							
							_logger.info("There are {} game(s) that need to be updated", _GamesThatNeedUpdating.size());
							updateGames(_listofGames);
							break;
							
						case 7: // Move a player to another team
							movePlayer(_listofTeams, _listofPlayers);
							break;
							
						case 8: // Log all players 
							   for(Entry<Integer, AbstractPlayer> entry : _listofPlayers.returnPlayersMap().entrySet()) {
								   _logger.info(entry.getValue().toString());
							   }
							   break;
						case 9:
							_logger.info("Enter the team whose roster you would like to see");
							_logger.info("Enter city of team:");
							city = reader.readLine();
							_logger.info("Enter name of the team:");
							teamName = reader.readLine();
							fullTeamName = city + " " + teamName;
							try {
								if(_listofTeams.getTeamMap().containsKey(fullTeamName)) 
									logTeamRoster(_listofTeams.getTeamMap().get(fullTeamName), _listofTeams);
								else
									throw new TeamNotFoundException(fullTeamName);
							}
							catch(Exception e_) {
								_logger.error("Invalid Team Input: " + e_.toString());
							}
							
							break;
						case 10: // Shut off system
							System.exit(0);
							break;	
							
							
						default:

							_logger.info("Wrong choice input. Please choose from the following options");

							break;
						
						}
					}
				
			} while (!isNumber);

	}
	
	public void logTeamRoster(AbstractTeam team_, TeamsList listofTeams_) {
		Iterator<TeamPlayer> teamPlayerIterator;
		teamPlayerIterator = team_.getCurrentPlayers().iterator();
		_logger.trace("These are the current players on the team {}" , team_.fullTeamName());
		System.out.println(team_.getCurrentPlayers().size());
		while(teamPlayerIterator.hasNext()) {
			_logger.info(teamPlayerIterator.next().getPlayer().toString());
		}	
	}
	
	public void movePlayer(TeamsList listofTeams_, PlayersList listofPlayers_) throws IOException {
		int playerID = -1;
		AbstractPlayer playerBeingMoved = null;
		AbstractTeam oldTeam = null;
		int indexOfCurrentTeamHistory = -1;
		int indexofCurrentPlayerHistory = -1;
		String teamCity = null;
		String teamName = null;
		String fullTeamName = null;
		TeamPlayer newTeamHistory = null;
		String oldTeamNameStr = null;
		
		_logger.info("Enter the player ID for the player you wish you move:");
		try {
			playerID = Integer.parseInt(reader.readLine());
		} 
		catch (NumberFormatException e_) {
			_logger.error("Could not parse the ID entered: " + e_.toString());
		} 
		try {
			if(!listofPlayers_.returnPlayersMap().containsKey(playerID)) {
				throw new PlayerNotFoundException(playerID);
			}
			else {
				playerBeingMoved = listofPlayers_.returnPlayersMap().get(playerID);
				oldTeamNameStr = playerBeingMoved.getCurrentTeam().fullTeamName();
			}
			
		}
		catch(Exception e_) {
			_logger.error("Error finding player: " + e_.toString());
			return;
		}
		if(listofTeams_.getTeamMap().get(playerBeingMoved.getCurrentTeam().fullTeamName()).getEntireHistoryPlayers().contains(playerBeingMoved.getCurrentTeamHistory())) {
			oldTeam = listofTeams_.getTeamMap().get(oldTeamNameStr);
			indexOfCurrentTeamHistory = oldTeam.getEntireHistoryPlayers().indexOf(playerBeingMoved.getCurrentTeamHistory());
		}
		//playerBeingMoved.getCurrentTeamHistory().setStatus(false);
		if(playerBeingMoved.getPlayerTeams().contains(playerBeingMoved.getCurrentTeamHistory())) {
			indexofCurrentPlayerHistory = playerBeingMoved.getPlayerTeams().indexOf(playerBeingMoved.getCurrentTeamHistory());
		}
		
		try {
			_logger.info("Enter the player's jersey number for the new team");
			playerBeingMoved.set_jerseyNum(Integer.parseInt(reader.readLine()));
		}
		catch(Exception e_) {
			_logger.error("Error occured for jersey number input: " + e_.toString());
		}
		try {
			_logger.info("Enter the team the player is moving to");
			_logger.info("Enter the city first: ");
			teamCity = reader.readLine();
			_logger.info("Enter the team name: ");
			teamName = reader.readLine();
			fullTeamName = teamCity + " " + teamName;
		}
		catch(Exception e_) {
			_logger.error("Something went wrong with reading the team: " + e_.toString());
			return;
		}
		
		try {
				newTeamHistory = new TeamPlayer();
				newTeamHistory.setStartDate(new Date());
				newTeamHistory.setStatus(true);
				playersReader.parseCurrentTeam(_listofTeams, fullTeamName, playerBeingMoved, _listofPlayers, newTeamHistory);
				listofTeams_.getTeamMap().get(oldTeamNameStr).getEntireHistoryPlayers().get(indexOfCurrentTeamHistory).setStatus(false);
				listofTeams_.getTeamMap().get(oldTeamNameStr).getEntireHistoryPlayers().get(indexOfCurrentTeamHistory).setEndDate(new Date());
				playerBeingMoved.getPlayerTeams().get(indexofCurrentPlayerHistory).setStatus(false);
				playerBeingMoved.getPlayerTeams().get(indexofCurrentPlayerHistory).setEndDate(new Date());;
				newTeamHistory.setPlayer(playerBeingMoved);
				listofTeams_.getTeamMap().get(fullTeamName).getEntireHistoryPlayers().add(newTeamHistory);

		}
		catch(Exception e_) {
			_logger.error("Error with moving this player to this team: " + e_.toString());
			return;
		}
		_logger.trace("{} {} was successfully moved to team {}", playerBeingMoved.getFirstName(), playerBeingMoved.getLastName(), playerBeingMoved.getCurrentTeam().fullTeamName());
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