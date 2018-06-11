package com.sts.concretemodel;

import java.util.concurrent.ConcurrentHashMap;

import com.sts.abstractmodel.AbstractTeam;

//class inheriting from ArrayList that contains Team objects
public class TeamsList{
	
	private ConcurrentHashMap<String,AbstractTeam> TeamMap_;
	//private static final long serialVersionUID = 1L;


	public TeamsList() {
		TeamMap_ = new ConcurrentHashMap<String, AbstractTeam>();
	}

	public void addTeam(String city_, String name_) {
		/*Team team= new Team();
		team.setLocation(city_);
		team.setTeamName(name_);
		TeamMap_.put(new KeyForTeamsList(team.getTeamName(), team.getTeamSport()), team);*/
	}
	
	public ConcurrentHashMap<String, AbstractTeam> getTeamMap(){
		return TeamMap_;
	}

	
	
	
	
	
}
