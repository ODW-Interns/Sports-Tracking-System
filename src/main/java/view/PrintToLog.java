package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.main.mainTemp;

import lists.GamesList;

//class used to log to a file
public class PrintToLog {

	private static final Logger logger = LoggerFactory.getLogger(PrintToLog.class);
	private static final Logger secondLogger = LoggerFactory.getLogger(PrintToLog.class);

	public static void logGamesList(GamesList list) {
		for (int i = 0; i < list.size(); i++) {
			logger.info(list.get(i).toString());
		}
		logger.info("Successfully logged all games requested");
		
	}
	
	public static void secondLogGamesList(GamesList list) {
		for (int i = 0; i < list.size(); i++) {
			secondLogger.info(list.get(i).toString());
		}
		secondLogger.info("Successfully logged all games requested");
		
	}


}
