package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.main.mainTemp;

import lists.GamesList;

public class PrintToLog {

	private static final Logger logger = LoggerFactory.getLogger(mainTemp.class);
	
	public static void logGamesList(GamesList list) {
		for (int i = 0; i < list.size(); i++) {
			logger.info(list.get(i).toString());
		}
		logger.info("Successfully logged all games requested");
		
	}

}
