package com.sts.control;
import java.io.IOException;
import java.io.InputStream;

import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.PlayersList;
import com.sts.concreteModel.TeamsList;

public class StoreDataFromInputFile {
	
	//method to store data from past games file and place into a list of games and return list
	public static void storeDataIntoGameList(String inputfile_, GamesList gamesList_, TeamsList teamsList_, PlayersList playersList_) throws RuntimeException, IOException {
		
		GamesFileReader in = new GamesFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, gamesList_, teamsList_, playersList_);
		is.close();
		
	}
	
	public static void storeDataIntoPlayerList(String inputfile_, GamesList gamesList_, TeamsList teamsList_, PlayersList playersList_) throws IOException {
		PlayersFileReader in = new PlayersFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, playersList_, teamsList_, gamesList_);
		is.close();
	}
	

}
