package com.sts.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

import org.slf4j.LoggerFactory;

import com.sts.control.DateComp;

//class inheriting from ArrayList to contain game objects
public class GamesList {
	private TreeMap<Key, Game> map;

    private static final long serialVersionUID = 1L;

     public GamesList(){
    	map = new TreeMap<Key, Game>(new DateComp());
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
        map.put(new Key(date_, away_, home_), game);
    }

   
  
    

}
