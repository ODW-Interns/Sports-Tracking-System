package com.sts.view;

import java.util.Locale.Category;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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
	
	public void displayTeams(TeamsList teamsList_, SportsCategory category, Boolean city){
	
		ConcurrentHashMap<KeyForTeamsMap, AbstractTeam> teammap = teamsList_.getTeamMap();
		for (Entry<KeyForTeamsMap, AbstractTeam> entry : teammap.entrySet()) {
			
			if(entry.getValue().getTeamSport().equals(category)&& city) {
	
		        _logger.info(entry.getValue().getLocation());
		    
		    }
			
			if(entry.getValue().getTeamSport().equals(category)&& !city) {
				
		        _logger.info(entry.getValue().getTeamName());
		    
		    }
			
		
	    }
	
    }
}
