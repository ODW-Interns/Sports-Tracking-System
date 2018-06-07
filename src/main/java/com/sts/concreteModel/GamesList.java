package com.sts.concreteModel;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractModel.Game;
import com.sts.abstractModel.Team;


//class inheriting from ArrayList to contain game objects
public class GamesList{
	private TreeMap<Key, Game> map;
    private Logger _logger;


    public GamesList(){
    	map = new TreeMap<Key, Game>(new DateCompare());
        _logger = LoggerFactory.getLogger(getClass().getSimpleName());

    }
    
    @Deprecated
    public void addGame(String date_, String time_, String aTeam_, int aScore_, String hTeam_, int hScore_,
                        int attendence_) {
        LoggerFactory.getLogger(getClass().getSimpleName()).error("This method is deprecated and taking no acction");
        return;
        
        //add(new Game(date_, time_, aTeam_, aScore_, hTeam_, hScore_, attendence_));
    }
    
    
    
    public void addPassedGame(int gameID_, String category_, ZonedDateTime date_, Duration duration, Team away_, Team home_, int awayScore_, int homeScore_, int attendance)
    {
        ZonedDateTime dateWithTZ = ZonedDateTime.from(date_);
        Game game = new Game();
        game.setGameUID(gameID_);
        game.setCategory(category_);
        game.setStartTime(dateWithTZ);
        game.setDuration(duration);
        game.setAwayTeam(away_);
        game.setHomeTeam(home_);
        game.setAwayTeamScore(awayScore_);
        game.setHomeTeamScore(homeScore_);
        int uid = -1;
        map.put(new Key(date_, uid), game);
    }

   public TreeMap<Key, Game> getGamesMap() {
	   return map;
   }
   
   public void logUpcomingGames(PlayersList playersList_) {
	   ZonedDateTime timeNow = ZonedDateTime.now();
	   int playerID;
	   Iterator<Integer> homeIterator;
	   Iterator<Integer> awayIterator;
	   int tempUID = -1;
	   Key lowestKey = new Key(timeNow,tempUID);
	   
	   SortedMap<Key, Game> upcomingGames = map.headMap(lowestKey);
	   
	   _logger.info("ALL UPCOMING GAMES:");
	   for(Entry<Key, Game> entry : upcomingGames.entrySet()) {
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
   
   public void logFinishedGames(PlayersList playersList_) {
	   ZonedDateTime timeNow = ZonedDateTime.now();
	   int playerID;
	   Iterator<Integer> i;
	   int tempUID = -1;
	   Key highestKey = new Key(timeNow,tempUID);
	   
	   SortedMap<Key, Game> upcomingGames = map.tailMap(highestKey);
	   
	   _logger.info("ALL FINISHED GAMES");
	   for(Entry<Key, Game> entry : upcomingGames.entrySet()) {
		   _logger.trace(entry.getValue().toString());
		   i = entry.getValue().getListofHomePlayers().iterator();
		   while(i.hasNext()) {
			   playerID = i.next();
			   _logger.info(playersList_.returnPlayersMap().get(playerID).toString());
		   }
	   }
   }
    
   public void logAllGamesInMap(PlayersList playersList_) {
	   int playerID;
	   Iterator<Integer> i;
	   _logger.info("All GAMES");
	   for(Entry<Key, Game> entry : map.entrySet()) {
		   _logger.trace(entry.getValue().toString());
		   i = entry.getValue().getListofHomePlayers().iterator();
		   while(i.hasNext()) {
			   playerID = i.next();
			   _logger.info(playersList_.returnPlayersMap().get(playerID).toString());
		   }
	   }
   }
   
   public void logAllPlayersForEachGame(PlayersList playersList_) {
	   _logger.info("Home Players:");
	   int playerID;
	   Iterator<Integer> i;
	   for(Entry<Key, Game> entry : map.entrySet()) {
		 
		   
	   }
   }

}
