package model;

import java.util.ArrayList;

//class inheriting from ArrayList to contain game objects
public class GamesList extends ArrayList<Game> {

    private static final long serialVersionUID = 1L;

    public void addGame(String date_, String time_, String aTeam_, int aScore_, String hTeam_, int hScore_,
                        int attendence_) {
        add(new Game(date_, time_, aTeam_, aScore_, hTeam_, hScore_, attendence_));
    }

    

}