package com.sts.model;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.SortedMap;

import org.junit.Test;

import com.sts.io.GamesFileReader;

public class GamesListTest {

	@Test
	public void upcomingGamesListTest() {
		// TODO Auto-generated constructor stub
	   
        GamesList gameslist = new GamesList();
        
	    
	    try (InputStream is = getClass().getResourceAsStream("/games.csv")) {
		    GamesFileReader gr = new GamesFileReader(); 
	    	gr.readData(is, gameslist.getGamesMap());
	    }
	    catch (Exception e_) {
	    	assert(false);
	        e_.printStackTrace();
	    } 
	    ZonedDateTime timeNow = ZonedDateTime.now();
		Team tempTeam = new Team();
		Key highestKey = new Key(timeNow,tempTeam);
		   
		SortedMap<Key, Game> upcomingGames = gameslist.getGamesMap().headMap(highestKey);
		// Should return saying that submap of GamesMap has 3 elements
		 assertEquals(3, upcomingGames.size());  
		   
	}

}
