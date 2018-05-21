package lists;

import java.util.ArrayList;

import model.Player;

public class PlayersList extends ArrayList<Player> {
	
	private static final long serialVersionUID = 1L;
	
	public void addPlayer(String first_, String last_, int jersey_) {
		
		Player addedPlayer =new Player(first_, last_, jersey_);
		addToList(addedPlayer);
	}
public void addToList(Player player_) {
	add(player_);
}

}
