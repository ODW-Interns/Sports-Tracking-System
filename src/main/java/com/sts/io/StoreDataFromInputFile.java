package com.sts.io;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.sts.model.Game;
import com.sts.model.GamesList;
import com.sts.model.Key;
import com.sts.model.PlayersList;
import com.sts.model.Team;
import com.sts.model.TeamsList;

public class StoreDataFromInputFile {
	
	//method to store data from past games file and place into a list of games and return list
	public static void storeDataIntoGameList(String inputfile_, GamesList gamesList_, TeamsList teamsList_) throws RuntimeException, IOException {
		
		GamesFileReader in = new GamesFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, gamesList_, teamsList_);
		is.close();
		
	}
	
	
	//method to store players data into list of players and return the list
	
	
	/*public static PlayersList storeDataIntoPlayersList(String inputfile_, TeamsList listofTeams) throws RuntimeException, IOException {
		PlayersList listofPlayers = new PlayersList();
		PlayerListFileReader in = new PlayerListFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is,listofTeams);
		is.close();
		
		return listofPlayers;
	}*/
	
	

}
