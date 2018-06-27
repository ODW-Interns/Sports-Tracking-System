package com.sts.view;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.control.TeamsList;
import com.sts.util.model.KeyForTeamsMap;


public class TeamsRequest {

private Logger _logger;
	
	public TeamsRequest() {
		
    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	}
	
	public void displayAllTeamCities(TeamsList teamsList_, SportsCategory category){
	
		ConcurrentHashMap<KeyForTeamsMap, AbstractTeam> teammap = teamsList_.getTeamMap();
		for (Entry<KeyForTeamsMap, AbstractTeam> entry : teammap.entrySet()) {
			
			if(entry.getValue().getTeamSport().equals(category)) {
	
		        _logger.info(entry.getValue().getLocation());
		    
		    }
		
	    }
	
    }
	
	public void displayAllTeamNames(TeamsList teamsList_, SportsCategory category) {
		
		Iterator<Entry<KeyForTeamsMap, AbstractTeam>> teamIterator;
		teamIterator = teamsList_.getTeamMap().entrySet().iterator();
		Entry<KeyForTeamsMap, AbstractTeam> entry;
		while(teamIterator.hasNext()) {
			entry = teamIterator.next();
			if(entry.getValue().getTeamSport().equals(category))
				_logger.info(entry.getValue().getTeamName());
		}
	}
}
