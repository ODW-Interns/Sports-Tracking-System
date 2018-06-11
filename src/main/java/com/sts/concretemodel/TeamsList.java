package com.sts.concretemodel;

import java.util.concurrent.ConcurrentHashMap;

import com.sts.abstractmodel.AbstractTeam;

//Class holding structure(Concurrent Hash Map) to hold all teams tracked
public class TeamsList{
	
	private ConcurrentHashMap<String,AbstractTeam> TeamMap_;

	//Constructor
	public TeamsList() {
		TeamMap_ = new ConcurrentHashMap<String, AbstractTeam>();
	}
	
	//Return: Teams Map
	public ConcurrentHashMap<String, AbstractTeam> getTeamMap(){
		return TeamMap_;
	}

	
	
	
	
	
}
