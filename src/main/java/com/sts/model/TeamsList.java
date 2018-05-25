package com.sts.model;

import java.util.concurrent.ConcurrentHashMap;

//class inheriting from ArrayList that contains Team objects
public class TeamsList{
	
	private ConcurrentHashMap<KeyForTeamsList,Team> TeamMap_;
	private static final long serialVersionUID = 1L;


	public TeamsList() {
		TeamMap_ = new ConcurrentHashMap<KeyForTeamsList, Team>();
	}

	public void addTeam(String city_, String name_) {
		Team team= new Team();
		team.setLocation(city_);
		team.setTeamName(name_);
		TeamMap_.put(new KeyForTeamsList(team.getTeamName(), team.getTeamSport()), team);
	}

	
	
	
	
	
}
