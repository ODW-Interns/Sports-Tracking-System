package com.sts.model;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.SortedMap;

import org.junit.Test;

import com.sts.abstractModel.Game;
import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.Key;
import com.sts.concreteModel.TeamsList;
import com.sts.control.GamesFileReader;

public class GamesListTest {

	@Test
	public void upcomingGamesListTest() {
		// TODO Auto-generated constructor stub
	   
        GamesList gameslist = new GamesList();
        
	    
	    try (InputStream is = getClass().getResourceAsStream("/games.csv")) {
	    	GamesList games = new GamesList();
	    	TeamsList teams = new TeamsList();
		    GamesFileReader gr = new GamesFileReader(); 
	    	gr.readData(is, games, teams);
	    }
	    catch (Exception e_) {
	    	assert(false);
	        e_.printStackTrace();
	    } 
	    ZonedDateTime timeNow = ZonedDateTime.now();
		int tempUID = -1;
		Key highestKey = new Key(timeNow,tempUID);
		   
		SortedMap<Key, Game> upcomingGames = gameslist.getGamesMap().headMap(highestKey);
		// Should return saying that submap of GamesMap has 3 elements
		 assertEquals(1, upcomingGames.size());  
		   
	}

}
