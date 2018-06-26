package com.sts.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractGame;
import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.KeyForGamesMap;
import com.sts.concretemodel.KeyForTeamsMap;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamPlayer;
import com.sts.concretemodel.TeamsList;
import com.sts.model.exception.PlayerNotFoundException;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.view.LogRequest;

/*
 * Class that is used to handle all the requests/events from the system
 * Methods include: 
 * 1)Logging the roster for a specified team
 * 2)Moving a player from one team to another
 * 3)Logging all players that are being tracked by the system
 * 4)Update any games that have started since the system has been running
 * that have finished and needs additionl data to update the game as finished
 */
public class EventHandler {
	
		private Logger _logger;
	    private BufferedReader reader;
	    private LogRequest logReq;
	    
	    //Constructor
	    public EventHandler() {
	    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	 	    reader = new BufferedReader(new InputStreamReader(System.in));
	 	    logReq = new LogRequest();
	    }
		
		//Method to move one player from one team to another
		public void movePlayer(TeamsList listofTeams_, PlayersList listofPlayers_) throws IOException {
			System.out.println("HERE");
			int playerID = -1;
			AbstractPlayer playerBeingMoved = null; // player that is being moved
			AbstractTeam oldTeam = null; // player's old team
			int indexOfCurrentTeamHistory = -1; 
			int indexofCurrentPlayerHistory = -1;
			String teamCity = null;
			String teamName = null;
			KeyForTeamsMap teamKey = null;
			TeamPlayer newTeamHistory = null;
			KeyForTeamsMap oldTeamKey = null;
			PlayersReader playersReader = new PlayersReader();
			
			_logger.info("Enter the player ID for the player you wish you move:");
			try {
				playerID = Integer.parseInt(reader.readLine().trim());
			} 
			catch (NumberFormatException e_) {
				_logger.error("Could not parse the ID entered: " + e_.toString());
				return;
			} 
			try {
				if(!listofPlayers_.returnPlayersMap().containsKey(playerID)) {
					throw new PlayerNotFoundException(playerID);
				}
				else {
					playerBeingMoved = listofPlayers_.returnPlayersMap().get(playerID);
					oldTeamKey = new KeyForTeamsMap(playerBeingMoved.getCurrentTeamHistory().getTeam().getLocation(), playerBeingMoved.getCurrentTeamHistory().getTeam().getTeamName());
				}
				
			}
			catch(Exception e_) {
				_logger.error("Error finding player: " + e_.toString());
				return;
			}
			if(listofTeams_.getTeamMap().get(oldTeamKey).getEntireHistoryPlayers().contains(playerBeingMoved.getCurrentTeamHistory())) {
				oldTeam = listofTeams_.getTeamMap().get(oldTeamKey);
				indexOfCurrentTeamHistory = oldTeam.getEntireHistoryPlayers().indexOf(playerBeingMoved.getCurrentTeamHistory());
			}
			//playerBeingMoved.getCurrentTeamHistory().setStatus(false);
			if(playerBeingMoved.getPlayerTeams().contains(playerBeingMoved.getCurrentTeamHistory())) {
				indexofCurrentPlayerHistory = playerBeingMoved.getPlayerTeams().indexOf(playerBeingMoved.getCurrentTeamHistory());
			}
			
			try {
				_logger.info("Enter the player's jersey number for the new team");
				playerBeingMoved.set_jerseyNum(Integer.parseInt(reader.readLine().trim()));
			}
			catch(Exception e_) {
				_logger.error("Error occured for jersey number input: " + e_.toString());
				return;
			}
			try {
				_logger.info("Enter the team the player is moving to");
				_logger.info("Enter the city first: ");
				teamCity = reader.readLine().trim();
				_logger.info("Enter the team name: ");
				teamName = reader.readLine().trim();
				teamKey = new KeyForTeamsMap(teamCity, teamName);
			}
			catch(Exception e_) {
				_logger.error("Something went wrong with reading the team: " + e_.toString());
				return;
			}
			
			try {
					newTeamHistory = new TeamPlayer();
					newTeamHistory.setStartDate(new Date());
					newTeamHistory.setStatus(true);
					playersReader.parseCurrentTeam(listofTeams_, teamKey, playerBeingMoved, listofPlayers_, newTeamHistory);
					listofTeams_.getTeamMap().get(oldTeamKey).getEntireHistoryPlayers().get(indexOfCurrentTeamHistory).setStatus(false);
					listofTeams_.getTeamMap().get(oldTeamKey).getEntireHistoryPlayers().get(indexOfCurrentTeamHistory).setEndDate(new Date());
					playerBeingMoved.getPlayerTeams().get(indexofCurrentPlayerHistory).setStatus(false);
					playerBeingMoved.getPlayerTeams().get(indexofCurrentPlayerHistory).setEndDate(new Date());;
					newTeamHistory.setPlayer(playerBeingMoved);
					listofTeams_.getTeamMap().get(teamKey).getEntireHistoryPlayers().add(newTeamHistory);

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
		public void updateGames(GamesList _listofGames_, ArrayList<KeyForGamesMap> GamesThatNeedUpdating_) {
		    Iterator<KeyForGamesMap> KeyIterator;
			KeyForGamesMap currentKey;
			AbstractGame gameUpdating = null;
		    int durationHours;
		    int durationMinutes;
		    int durationSeconds;
		    StringBuilder durationString = new StringBuilder("PT");
			KeyIterator = GamesThatNeedUpdating_.iterator();		
			while(KeyIterator.hasNext()) {
				currentKey = KeyIterator.next();
				gameUpdating = _listofGames_.getGamesMap().get(currentKey);
				_logger.info("Updating Game - GameID: {} , Start Time: {}", currentKey.getGameUID(), currentKey.getStartTime());
				_logger.info("Enter Duration of the game"); // Prompt User for the duration of the game
				_logger.info("Hour(s):"); // Hours the game lasted
				try {
					durationHours = Integer.parseInt(reader.readLine().trim());
				}
				catch(Exception e_) {
					_logger.error(e_.toString());
					continue;
				}
				_logger.info("Minutes(s):"); // Minutes the game lasted
				try {
					durationMinutes = Integer.parseInt(reader.readLine().trim());
				}
				catch(Exception e_) {
					_logger.error(e_.toString());
					continue;
				}
				_logger.info("Second(s):"); // Seconds the game lasted
				try {
					durationSeconds = Integer.parseInt(reader.readLine().trim());
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
					gameUpdating.setHomeTeamScore(Integer.parseInt(reader.readLine().trim()));
				}
				catch(Exception e_) {
					_logger.error(e_.toString());
					continue;
				}
				_logger.info("Away Team Final Score:"); // Final Score for away team
				try {
					gameUpdating.setAwayTeamScore(Integer.parseInt(reader.readLine().trim()));
				}
				catch(Exception e_) {
					_logger.error(e_.toString());
					continue;
				}
				_listofGames_.getGamesMap().get(currentKey).setAwayTeamScore(gameUpdating.getaTeamScore());
				_listofGames_.getGamesMap().get(currentKey).setHomeTeamScore(gameUpdating.getHomeTeamScore());
				_logger.info("Enter attendance of game:");
				try {
					gameUpdating.setAttendance(Integer.parseInt(reader.readLine().trim())); // attendance of game
				}
				catch(Exception e_) {
					_logger.error(e_.toString());
					continue;
				}
				_logger.trace("Game successfully updated:");
				_logger.info(_listofGames_.getGamesMap().get(currentKey).toString());
			}
		}

	//Method to log all players that are being tracked by the system
	public void requestToLogAllPlayersInPlayerMap(PlayersList listofPlayers_) {
		   logReq.logAllPlayersInPlayerMap(listofPlayers_);
	}

	public void requestToLogAllPlayersOnTeam(TeamsList listofTeams_) {
		String city;
		String teamName;
		KeyForTeamsMap teamKey = null;
		_logger.info("Enter the team whose roster you would like to see");
		_logger.info("Enter city of team:");
		try {
			city = reader.readLine().trim();
		} catch (IOException e_) {
			_logger.error("Error reading in city: " + e_.toString());
			return;
		}
		_logger.info("Enter name of the team:");
		try {
			teamName = reader.readLine().trim();
		} catch (IOException e_) {
			_logger.error("Error reading in team name: " + e_.toString());
			return;
		}
		teamKey = new KeyForTeamsMap(city, teamName);
		try {
			if(listofTeams_.getTeamMap().containsKey(teamKey)) 
				logReq.logTeamRoster(listofTeams_.getTeamMap().get(teamKey), listofTeams_);
			else
				throw new TeamNotFoundException(teamKey.toString());
		}
		catch(Exception e_) {
			_logger.error("Invalid Team Input: " + e_.toString());
		}
	}
}
