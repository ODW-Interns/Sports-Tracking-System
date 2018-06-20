package com.sts.concretemodel;

import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractGame;
import com.sts.abstractmodel.AbstractTeam;


/**
 * Class to hold structure (Tree Map) to map all games being tracked
 */
public class GamesList{
	private TreeMap<Key, AbstractGame> map;
    private Logger _logger;

    /**
     * Constructor
     */
    public GamesList(){
    	map = new TreeMap<Key, AbstractGame>(new DateCompare());
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
   public TreeMap<Key, AbstractGame> getGamesMap() {
	   return map;
   }
   
   /**
    * Log games in the future. Roster of each team are also logged
    */
   public void logUpcomingGames(PlayersList playersList_) {
	   ZonedDateTime timeNow = ZonedDateTime.now();
	   TeamPlayer teamPlayer;
	   Iterator<TeamPlayer> homeIterator;
	   Iterator<TeamPlayer> awayIterator;
	   int tempUID = -1;
	   Key lowestKey = new Key(timeNow,tempUID);
	   AbstractTeam homeTeam;
	   AbstractTeam awayTeam;
	   SortedMap<Key, AbstractGame> upcomingGames = map.headMap(lowestKey);
	   
	   _logger.info("ALL UPCOMING GAMES:");
	   for(Entry<Key, AbstractGame> entry : upcomingGames.entrySet()) {
		   _logger.trace(entry.getValue().toString());
		   homeTeam = entry.getValue().getHomeTeam();
		   awayTeam = entry.getValue().getAwayTeam();
		   homeIterator = homeTeam.getEntireHistoryPlayers().iterator();
		   awayIterator = awayTeam.getEntireHistoryPlayers().iterator();
		   _logger.trace("Home Players:");
		   while(homeIterator.hasNext()) {
			   teamPlayer = homeIterator.next();
			   _logger.info(playersList_.returnPlayersMap().get(teamPlayer.getPlayer().get_playerID()).toString());
		   }
		   _logger.trace("Away Players:");
		   while(awayIterator.hasNext()) {
			   teamPlayer = awayIterator.next();
			   _logger.info(playersList_.returnPlayersMap().get(teamPlayer.getPlayer().get_playerID()).toString());
		   }
		   
	   }
   }
   
   /**
    * Log games that are finished. Players that have played in the game are also logged
    */
   public void logFinishedGames(PlayersList playersList_) {
	   ZonedDateTime timeNow = ZonedDateTime.now();
	   int playerID;
	   Iterator<Integer> homeIterator;
	   Iterator<Integer> awayIterator;
	   int tempUID = -1;
	   Key highestKey = new Key(timeNow,tempUID);

	   SortedMap<Key, AbstractGame> finishedGames = map.tailMap(highestKey);
	   
	   _logger.info("ALL FINISHED GAMES");
	   for(Entry<Key, AbstractGame> entry : finishedGames.entrySet()) {
		   if(entry.getValue().getFinishTime() != null) {
			   _logger.trace(entry.getValue().toString());
			   homeIterator = entry.getValue().getListofHomePlayers().iterator();
			   awayIterator = entry.getValue().getListOfAwayPlayers().iterator();

			   _logger.trace("Home Players:");
			   while(homeIterator.hasNext()) {
				   playerID = homeIterator.next();
				   _logger.info(playersList_.returnPlayersMap().get(playerID).toString());
			   }
			   _logger.trace("Away Players:");
			   while(awayIterator.hasNext()) {
				   playerID = awayIterator.next();
				   _logger.info(playersList_.returnPlayersMap().get(playerID).toString());
			   }
		   }
	   }
   }

}
