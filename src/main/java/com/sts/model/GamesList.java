package com.sts.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

import org.slf4j.LoggerFactory;

//class inheriting from ArrayList to contain game objects
public class GamesList extends TreeMap<Key, Game>{

    private static final long serialVersionUID = 1L;

    @Deprecated
    public void addGame(String date_, String time_, String aTeam_, int aScore_, String hTeam_, int hScore_,
                        int attendence_) {
        LoggerFactory.getLogger(getClass().getSimpleName()).error("This method is deprecated and taking no acction");
        return;
        
        //add(new Game(date_, time_, aTeam_, aScore_, hTeam_, hScore_, attendence_));
    }
    
    
    
    public void addGame(ZonedDateTime date_, Team away_, Team home_)
    {
        ZonedDateTime dateWithTZ = ZonedDateTime.from(date_);
        Game game = new Game();
        game.setDate(dateWithTZ);
        game.setAwayTeam(away_);
        game.setHomeTeam(home_);
        put(new Key(date_, away_, home_), game);
    }

   
  
    

}
