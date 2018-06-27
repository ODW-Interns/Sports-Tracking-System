package com.sts.control;

import java.util.concurrent.ConcurrentHashMap;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.util.model.KeyForTeamsMap;

/**
 * Class holding structure(Concurrent Hash Map) to hold all teams tracked
 *
 */
public class TeamsList{
	
	private ConcurrentHashMap<KeyForTeamsMap,AbstractTeam> TeamMap_;

	//Constructor
	public TeamsList() {
		TeamMap_ = new ConcurrentHashMap<KeyForTeamsMap, AbstractTeam>();
	}
	
	//Return: Teams Map
	public ConcurrentHashMap<KeyForTeamsMap, AbstractTeam> getTeamMap(){
		return TeamMap_;
	}

	
	
	
	
	
}
