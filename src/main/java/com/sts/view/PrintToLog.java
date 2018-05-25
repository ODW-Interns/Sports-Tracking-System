package com.sts.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.model.GamesList;

//class used to log to a file
public class PrintToLog {

	private static final Logger logger = LoggerFactory.getLogger(PrintToLog.class);
	private static final Logger secondLogger = LoggerFactory.getLogger(PrintToLog.class);

	public static void logGamesList(GamesList list) {
		logger.info("Printing All Games Requested");
		/*for (int i = 0; i < list.size(); i++) {
			logger.info(list.get(i).toString());
		}*/
		logger.info("Successfully logged all games requested");
		logger.info("\n");
	}
	

	public static void secondLogGamesList(GamesList list) {
		logger.info("Printing All Games Requested");
		/*for (int i = 0; i < list.size(); i++) {
			secondLogger.info(list.get(i).toString());
		}*/
		secondLogger.info("Successfully logged all games requested");
		
	}
	
	public static void printSportCategory(int sportChosen) {
		String sport;
		if(sportChosen == 1)
			sport = "NBA";
		else if(sportChosen == 2)
			sport = "NFL";
		else if(sportChosen == 3)
			sport = "MLB";
		else if(sportChosen == 4)
			sport = "NHL";
		else return;
		
		logger.info("<<<<<<<<<<Currently In " + sport + " Category>>>>>>>>>>", sport);
	}
	
	public static void printMainMenuAlert() {
		logger.info("====================MAIN MENU PAGE====================");
	}

	/*public static void logAllRosters(TeamsList teams_) {
		logger.info("Printing current rosters for all teams");
		//Iterator<Team> teamIterator = teams_.iterator();
		
		while(teamIterator.hasNext()) {
			Team currentTeam = teamIterator.next();
			logger.info("Current roster of the:");
			logger.info(currentTeam.toString());
			Iterator<Player> playerIterator = currentTeam.getListOfPLayers().iterator();
			while(playerIterator.hasNext()) {
				logger.info(playerIterator.next().toString() + ",");
			}
		}
		logger.info("\n");
	}*/

}
