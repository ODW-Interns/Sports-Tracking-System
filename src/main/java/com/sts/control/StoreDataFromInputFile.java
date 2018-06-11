package com.sts.control;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;

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
	
	public static void storeDataIntoTeamList(String inputfile_, GamesList gamesList_, TeamsList teamsList_) throws RuntimeException, IOException {
		TeamsFileReader in = new TeamsFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, gamesList_, teamsList_);
		is.close();
	}
	

}
