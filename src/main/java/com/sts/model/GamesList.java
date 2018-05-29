package com.sts.model;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    
    
    
    public void addGame(ZonedDateTime date_, Duration duration, Team away_, Team home_)
    {
        ZonedDateTime dateWithTZ = ZonedDateTime.from(date_);
        Game game = new Game();
        game.setStartTime(dateWithTZ);
        game.setDuration(duration);
        game.setAwayTeam(away_);
        game.setHomeTeam(home_);
        map.put(new Key(date_, away_), game);
    }

   public TreeMap<Key, Game> getGamesMap() {
	   return map;
   }
   
   public void logUpcomingGames() {
	   ZonedDateTime timeNow = ZonedDateTime.now();
	   Team tempTeam = new Team();
	   Key lowestKey = new Key(timeNow,tempTeam);
	   
	   SortedMap<Key, Game> upcomingGames = map.headMap(lowestKey);
	   
	   _logger.info("ALL UPCOMING GAMES:");
	   for(Entry<Key, Game> entry : upcomingGames.entrySet()) {
		   _logger.trace(entry.getValue().toString());
	   }
   }
   
   public void logFinishedGames() {
	   ZonedDateTime timeNow = ZonedDateTime.now();
	   Team tempTeam = new Team();
	   Key highestKey = new Key(timeNow,tempTeam);
	   
	   SortedMap<Key, Game> upcomingGames = map.tailMap(highestKey);
	   
	   _logger.info("ALL FINISHED GAMES");
	   for(Entry<Key, Game> entry : upcomingGames.entrySet()) {
		   _logger.trace(entry.getValue().toString());
	   }
   }
    
   public void logAllGamesInMap() {
	   _logger.info("All GAMES");
	   for(Entry<Key, Game> entry : map.entrySet()) {
		   _logger.trace(entry.getValue().toString());
	   }
   }

}
