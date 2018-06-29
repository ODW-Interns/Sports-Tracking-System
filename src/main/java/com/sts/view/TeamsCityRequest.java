package com.sts.view;

import java.util.Iterator;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.control.TeamsList;
import com.sts.util.model.KeyForTeamsMap;


public class TeamsCityRequest {

private Logger _logger;
	
	public TeamsCityRequest() {
		
    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	}
	
	public void displayTeams(TeamsList teamsList_, SportsCategory category){
		Iterator<Entry<KeyForTeamsMap, AbstractTeam>> teamIterator;
		teamIterator = teamsList_.getTeamMap().entrySet().iterator();
		Entry<KeyForTeamsMap, AbstractTeam> entry;
		while(teamIterator.hasNext()) {
			entry = teamIterator.next();
			if(entry.getValue().getTeamSport().equals(category)) {
		        _logger.info(entry.getValue().fullTeamName());
		    
		    }		
	    }
    }
	
	public void displayAllTeams(TeamsList teamsList_) {
		Iterator<Entry<KeyForTeamsMap, AbstractTeam>> teamIterator;
		teamIterator = teamsList_.getTeamMap().entrySet().iterator();
		Entry<KeyForTeamsMap, AbstractTeam> entry;

		while(teamIterator.hasNext()) {
			entry = teamIterator.next();
		    _logger.info(entry.getValue().fullTeamName());
		    		
	    }
	}
	
}
