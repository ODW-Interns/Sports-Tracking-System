package com.sts.control;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractGame;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.util.GameKeyCompare;
import com.sts.util.model.KeyForGamesMap;
import com.sts.util.model.TeamPlayerHistory;


/**
 * Class to hold structure (Tree Map) to map all games being tracked
 */
public class GamesList{
	private TreeMap<KeyForGamesMap, AbstractGame> map;
    private Logger _logger;

    /**
     * Constructor
     */
    public GamesList(){
    	map = new TreeMap<KeyForGamesMap, AbstractGame>(new GameKeyCompare());
        _logger = LoggerFactory.getLogger(getClass().getSimpleName());

    }
    
    @Deprecated
    public void addGame(String date_, String time_, String aTeam_, int aScore_, String hTeam_, int hScore_,
                        int attendence_) {
        LoggerFactory.getLogger(getClass().getSimpleName()).error("This method is deprecated and taking no acction");
        return;
        
        //add(new Game(date_, time_, aTeam_, aScore_, hTeam_, hScore_, attendence_));
    }
    
    
    /**
     * method to add game to games map
     */

   /**
    * @return
    * tree map(Map of Games Sorted by Time)
    */
   public TreeMap<KeyForGamesMap, AbstractGame> getGamesMap() {
	   return map;
   }
   
   /**
    * Log games in the future. Roster of each team are also logged
    */
   public void logUpcomingGames(PlayersList playersList_) {
	   //timeNow is used to partition the gamesMap to retrieve just the upcoming games
	   ZonedDateTime timeNow = ZonedDateTime.now();
	   
	   TeamPlayerHistory teamPlayer;
	   
	   //Iterators to iterate through the away and home teams players
	   Iterator<TeamPlayerHistory> homeIterator;
	   Iterator<TeamPlayerHistory> awayIterator;
	  
	   int tempUID = -1;
	   KeyForGamesMap lowestKey = new KeyForGamesMap(timeNow,tempUID);
	   AbstractTeam homeTeam;
	   AbstractTeam awayTeam;
	   SortedMap<KeyForGamesMap, AbstractGame> upcomingGames = map.headMap(lowestKey); // Partition for just the upcoming games 
	   																		// from the gamesMap by using headMap
	   
	   _logger.info("ALL UPCOMING GAMES:");
	   
	   //Iterate through the upcoming games
	   for(Entry<KeyForGamesMap, AbstractGame> entry : upcomingGames.entrySet()) {
		   _logger.trace(entry.getValue().toString());
		   homeTeam = entry.getValue().getHomeTeam();
		   awayTeam = entry.getValue().getAwayTeam();
		   homeIterator = homeTeam.getCurrentPlayers().iterator();
		   awayIterator = awayTeam.getCurrentPlayers().iterator();
		   _logger.trace("Home Players:");
		   
		   //For each game, iterate through the home teams current roster and log these players 
		   while(homeIterator.hasNext()) {
			   teamPlayer = homeIterator.next();
			   _logger.info(playersList_.returnPlayersMap().get(teamPlayer.getPlayer().get_playerID()).toString());
		   }
		   
		   _logger.trace("Away Players:");
		   //Now log the away team's current roster as well
		   while(awayIterator.hasNext()) {
			   teamPlayer = awayIterator.next();
			   _logger.info(playersList_.returnPlayersMap().get(teamPlayer.getPlayer().get_playerID()).toString());
		   }
		   
	   }
   }
   
   /**
    * Log games that are finished. Players that have were on these teams in the game are also logged
    */
   public void logFinishedGames(PlayersList playersList_) {
	   //timeNow used to partition the gamesMap to retrieve the finished games only
	   ZonedDateTime timeNow = ZonedDateTime.now(); 
	   
	   //Iterators to iterator through the home and away teams players
	   Iterator<TeamPlayerHistory> homeIterator;
	   Iterator<TeamPlayerHistory> awayIterator;
	   
	   int tempUID = -1;
	   KeyForGamesMap highestKey = new KeyForGamesMap(timeNow,tempUID);
	   AbstractTeam awayTeam = null;
	   AbstractTeam homeTeam = null;
	   TeamPlayerHistory player = null;

	   // tailMap used to partition the entire games map to retrieve just the finished games
	   SortedMap<KeyForGamesMap, AbstractGame> finishedGames = map.tailMap(highestKey); 
	   
	   _logger.info("ALL FINISHED GAMES");
	   for(Entry<KeyForGamesMap, AbstractGame> entry : finishedGames.entrySet()) {
		   if(entry.getValue().getFinishTime() != null) { // the game has finished, then
			   _logger.trace(entry.getValue().toString());
			   homeTeam = entry.getValue().getHomeTeam(); // home team of game
			   awayTeam = entry.getValue().getAwayTeam(); //away team of game

			   homeIterator = homeTeam.getEntireHistoryPlayers().iterator();
			   _logger.trace("Home Players:");
			   
			   //Iterate through the entire history of the home team(history of players)
			   //If this player was on the team at the time of the game, then log this player
			   //as a player on the team at the time of the game
			   while(homeIterator.hasNext()) {
				   player = homeIterator.next();
				   if(player.getStartDate().before(Date.from(entry.getValue().getStartTime().toInstant())) && 
						  ( player.getEndDate() == null || player.getEndDate().after(Date.from(entry.getValue().getStartTime().toInstant()))))
						  _logger.info(player.getPlayer().toString());
			   }
			   
			   //Do the same for the away team
			   awayIterator = awayTeam.getEntireHistoryPlayers().iterator();
			   _logger.trace("Away Players:");
			   while(awayIterator.hasNext()) {
				   player = awayIterator.next();
				   if(player.getStartDate().before(Date.from(entry.getValue().getStartTime().toInstant())) && 
							  ( player.getEndDate() == null || player.getEndDate().after(Date.from(entry.getValue().getStartTime().toInstant()))))
							  _logger.info(player.getPlayer().toString());			
				   }
		   }
	   }
   }
}
