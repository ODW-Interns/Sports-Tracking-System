package io;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.sts.main.mainTemp;

import lists.GamesList;

public class StoreDataFromInputFile {

	public StoreDataFromInputFile(){}
	
	public static GamesList storeDataIntoGameList(String inputfile_) throws RuntimeException, IOException {
		GamesList listofGames = new GamesList();
		NBAPastGamesFileReader in = new NBAPastGamesFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, listofGames);
		is.close();
		
		return listofGames;
	}

}
