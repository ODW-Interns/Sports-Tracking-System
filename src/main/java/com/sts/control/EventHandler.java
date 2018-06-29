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
import com.sts.model.exception.PlayerNotFoundException;
import com.sts.model.exception.TeamNotFoundException;
import com.sts.util.CustomValidations;
import com.sts.util.model.KeyForGamesMap;
import com.sts.util.model.KeyForTeamsMap;
import com.sts.util.model.TeamPlayerHistory;
import com.sts.view.LogRequest;
import com.sts.view.TeamsCityRequest;

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
	    private TeamsCityRequest teamReq;
	    
	    //Constructor
	    public EventHandler() {
	    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	 	    reader = new BufferedReader(new InputStreamReader(System.in));
	 	    logReq = new LogRequest();
	 	    teamReq = new TeamsCityRequest();
	    }
		
		//Method to move one player from one team to another
		public void movePlayer(TeamsList listofTeams_, PlayersList listofPlayers_) throws IOException {
			int playerID = -1;
			AbstractPlayer playerBeingMoved = null; // player that is being moved
			AbstractTeam oldTeam = null; // player's old team
			int indexOfCurrentTeamHistory = -1; 
			int indexofCurrentPlayerHistory = -1;
			String teamCity = null;
			String teamName = null;
			KeyForTeamsMap teamKey = null;
			TeamPlayerHistory newTeamHistory = null;
			KeyForTeamsMap oldTeamKey = null;
			PlayersReader playersReader = new PlayersReader();
			
			_logger.info("Enter the player ID for the player you wish you move from the following players:");
			logReq.logAllPlayersInPlayerMap(listofPlayers_);
			try {
				playerID = Integer.parseInt(reader.readLine());
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
					if(playerBeingMoved.getCurrentTeamHistory().getTeam() != null)
						oldTeamKey = new KeyForTeamsMap(playerBeingMoved.getCurrentTeamHistory().getTeam().getLocation(), playerBeingMoved.getCurrentTeamHistory().getTeam().getTeamName());
				}
				
			}
			catch(Exception e_) {
				_logger.error("Error finding player: " + e_.toString());
				return;
			}
			if(oldTeamKey != null && listofTeams_.getTeamMap().get(oldTeamKey).getEntireHistoryPlayers().contains(playerBeingMoved.getCurrentTeamHistory())) {
				oldTeam = listofTeams_.getTeamMap().get(oldTeamKey);
				indexOfCurrentTeamHistory = oldTeam.getEntireHistoryPlayers().indexOf(playerBeingMoved.getCurrentTeamHistory());
			}
			//playerBeingMoved.getCurrentTeamHistory().setStatus(false);
			
			/*if the player is on a team currently*/
			if(playerBeingMoved.getCurrentTeamHistory().getTeam() != null && playerBeingMoved.getPlayerTeams().contains(playerBeingMoved.getCurrentTeamHistory())) {
				indexofCurrentPlayerHistory = playerBeingMoved.getPlayerTeams().indexOf(playerBeingMoved.getCurrentTeamHistory());
			}
			
			try {
				_logger.info("Enter the player's jersey number for the new team");
				playerBeingMoved.set_jerseyNum(Integer.parseInt(reader.readLine()));
			}
			catch(Exception e_) {
				_logger.error("Error occured for jersey number input: " + e_.toString());
				return;
			}
			try {
				_logger.info("Enter the team the player is moving to. Choose from the following teams:");
				teamReq.displayTeams(listofTeams_, playerBeingMoved.get_sportCategory());
				
				_logger.info("Enter the city first: ");
				teamCity = reader.readLine();
				_logger.info("Enter the team name: ");
				teamName = reader.readLine();
				teamKey = new KeyForTeamsMap(teamCity, teamName);
			}
			catch(Exception e_) {
				_logger.error("Something went wrong with reading the team: " + e_.toString());
				return;
			}
			
			try {
					newTeamHistory = new TeamPlayerHistory();
					newTeamHistory.setStartDate(new Date());
					newTeamHistory.setStatus(true);
					playersReader.parseCurrentTeam(listofTeams_, teamKey, playerBeingMoved, listofPlayers_, newTeamHistory);
					
					if(oldTeamKey != null) {
					listofTeams_.getTeamMap().get(oldTeamKey).getEntireHistoryPlayers().get(indexOfCurrentTeamHistory).setStatus(false);
					listofTeams_.getTeamMap().get(oldTeamKey).getEntireHistoryPlayers().get(indexOfCurrentTeamHistory).setEndDate(new Date());
					playerBeingMoved.getPlayerTeams().get(indexofCurrentPlayerHistory).setStatus(false);
					playerBeingMoved.getPlayerTeams().get(indexofCurrentPlayerHistory).setEndDate(new Date());;
					}
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

	//Method to log all players that are being tracked by the system
	public void requestToLogAllPlayersInPlayerMap(PlayersList listofPlayers_) {
		   logReq.logAllPlayersInPlayerMap(listofPlayers_);
	}

	public void requestToLogAllPlayersOnTeam(TeamsList listofTeams_) {
		String city = null;
		String teamName = null;
		KeyForTeamsMap teamKey = null;
		TeamsCityRequest req = new TeamsCityRequest();
		CustomValidations cvalidations = new CustomValidations();
		boolean isValid = false;
		
		_logger.info("Enter the team whose roster you would like to see from the following teams");
		req.displayAllTeams(listofTeams_);

		do {
			_logger.info("Enter the city of the team");
			try {
				city = reader.readLine();
				if(cvalidations.cityValidation(listofTeams_,city)) {
					isValid=true;
					break;
				}else {
					isValid=false;
					continue;
				}
			} catch (IOException e) {
				_logger.error("Invalid city.. Please re-enter " + e.toString());
			} 
		} while (!isValid);
		
		
		/*
		 * Reading the Team Name 
		 */
		do {
			try {
				_logger.info("Enter the away team name corresponding to "+city);
				teamName = reader.readLine();
				if(cvalidations.teamValidation(listofTeams_, teamName,city)) {
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
	
	public void removePlayerFromTeam(TeamsList listofTeams_, PlayersList listofPlayers_) {
		int playerID;
		AbstractPlayer playerBeingRemoved = null;
		KeyForTeamsMap teamKey = null;
		boolean isValid = false;
		
		_logger.info("Enter the player ID to remove this player from his team");
		_logger.info("Players that can be chosen from are listed below:");
		logReq.logAllPlayersInPlayerMap(listofPlayers_);
		do {
			try {
				playerID = Integer.parseInt(reader.readLine());
				if(listofPlayers_.returnPlayersMap().containsKey(playerID)) {
					playerBeingRemoved = listofPlayers_.returnPlayersMap().get(playerID);
					isValid = true;
				}
				else
					throw new NullPointerException("This player ID does not exist");
			} catch (NumberFormatException | IOException | NullPointerException e_) {
				_logger.error("There was an error with this player ID: " + e_.toString());
				_logger.info("Reenter a valid player ID");
			}
		}while(!isValid);
		
		isValid = false;
			
		//Player is currently not on a team , so can't remove him from a team
		if(playerBeingRemoved.getCurrentTeamHistory().getTeam() == null) {
			return;
		}
		
		
		try {
			//Set end date and status to false for the the team in the player's entire team history he's played for
			int indexOfCurrentTeam = playerBeingRemoved.getPlayerTeams().indexOf(playerBeingRemoved.getCurrentTeamHistory());
			playerBeingRemoved.getPlayerTeams().get(indexOfCurrentTeam).setEndDate(new Date());
			playerBeingRemoved.getPlayerTeams().get(indexOfCurrentTeam).setStatus(false);
			
			//Remove player from the team
			teamKey = new KeyForTeamsMap(playerBeingRemoved.getCurrentTeamHistory().getTeam().getLocation(), playerBeingRemoved.getCurrentTeamHistory().getTeam().getTeamName());
			int indexOfCurrentPlayer = listofTeams_.getTeamMap().get(teamKey).getEntireHistoryPlayers().indexOf(playerBeingRemoved.getCurrentTeamHistory());
			listofTeams_.getTeamMap().get(teamKey).getEntireHistoryPlayers().get(indexOfCurrentPlayer).setEndDate(new Date());
			listofTeams_.getTeamMap().get(teamKey).getEntireHistoryPlayers().get(indexOfCurrentPlayer).setStatus(false);
			
			//Mark the player's current history with the updated info.
			// His current team  and start date is now null
			// His status on a team is false
			playerBeingRemoved.getCurrentTeamHistory().setTeam(null);
			playerBeingRemoved.getCurrentTeamHistory().setStartDate(null);
			playerBeingRemoved.getCurrentTeamHistory().setStatus(false);
		}
		catch(Exception e_) {
			_logger.error("Error occurred while removing this player: " + e_.toString());
			return;
		}
			
		_logger.trace("Player {} {} has been removed from their team", playerBeingRemoved.getFirstName(), playerBeingRemoved.getLastName());	
		
	}
}
