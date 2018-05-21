package lists;

import java.util.ArrayList;

import model.Team;

//class inheriting from ArrayList that contains Team objects
public class TeamsList extends ArrayList<Team>{
	
	
	private static final long serialVersionUID = 1L;

	public TeamsList() {
	}

	public void addNBATeam(String city_, String mascot_) {
		Team addedTeam = new Team(city_, mascot_, 0);
		addToList(addedTeam);
	}
	
	public void addToList(Team team_) {
		add(team_);
	}

}
