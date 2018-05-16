package IOClasses;

import java.util.ArrayList;

//import IdentityClasses.Game;
import IdentityClasses.Team;

public class TeamsList extends ArrayList<Team>{
	private final int NBAplayers =12;
	//private static final long serialVersionUID = 1L;

	public TeamsList() {
	}

	public void addNBATeam(String city_, String mascot_) {
		Team addedTeam = new Team(city_, mascot_, NBAplayers);
		addToList(addedTeam);
	}
	
	public void addToList(Team team_) {
		add(team_);
	}

}
