package model;

import java.util.ArrayList;

//class inheriting from ArrayList that contains Team objects
public class TeamsList extends ArrayList<Team>{
	
	
	private static final long serialVersionUID = 1L;

	public TeamsList() {
	}

	public void addNBATeam(String city_, String mascot_) {
		add(new Team(city_, mascot_, 0));
	}

}
