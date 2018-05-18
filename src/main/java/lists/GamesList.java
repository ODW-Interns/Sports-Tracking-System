package lists;

import java.util.ArrayList;

import model.Game;

//class inheriting from ArrayList to contain game objects
public class GamesList extends ArrayList<Game> {

    public void addGame(String date_, String time_, String aTeam_, int aScore_, String hTeam_, int hScore_,
                        int attendence_) {
        Game addedGame = new Game(date_, time_, aTeam_, aScore_, hTeam_, hScore_, attendence_);
        addToList(addedGame);
    }

    public void addToList(Game game_) {
        add(game_);
    }

}
