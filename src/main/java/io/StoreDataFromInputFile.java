package io;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.sts.main.mainTemp;

import lists.GamesList;

public class StoreDataFromInputFile {
	
	//method to store data from past games file and place into a list of games and return list
	public static GamesList storeDataIntoPastGameList(String inputfile_) throws RuntimeException, IOException {
		GamesList listofGames = new GamesList();
		PastGamesFileReader in = new PastGamesFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, listofGames);
		is.close();
		
		return listofGames;
	}
	
	//method to store data from upcoming games file and place into a list of games and return list
	public static GamesList storeDataIntoUpcomingGameList(String inputfile_) throws RuntimeException, IOException {
		GamesList listofGames = new GamesList();
		UpcomingFileReader in = new UpcomingFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, listofGames);
		is.close();
		
		return listofGames;
	}
	

}
