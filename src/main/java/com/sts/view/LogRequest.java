package com.sts.view;

import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamPlayer;
import com.sts.concretemodel.TeamsList;
import org.slf4j.Logger;

public class LogRequest {

	private Logger _logger;
	
	public LogRequest() {
    	_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	}
	
    //Method to log the roster of a team specified by user
		public void logTeamRoster(AbstractTeam team_, TeamsList listofTeams_) {
			Iterator<TeamPlayer> teamPlayerIterator;
			teamPlayerIterator = team_.getCurrentPlayers().iterator();
			_logger.trace("These are the current players on the team {}" , team_.fullTeamName());
			while(teamPlayerIterator.hasNext()) {
				_logger.info(teamPlayerIterator.next().getPlayer().toString());
			}	
		}
		

		//Method to log all players that are being tracked by the system
		public void logAllPlayersInPlayerMap(PlayersList listofPlayers_) {
			   for(Entry<Integer, AbstractPlayer> entry : listofPlayers_.returnPlayersMap().entrySet()) {
				   _logger.info(entry.getValue().toString());
			   }
		}
		
		

}
