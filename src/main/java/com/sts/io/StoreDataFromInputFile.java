package com.sts.io;
import java.io.IOException;
import java.io.InputStream;
import com.sts.model.GamesList;
import com.sts.model.TeamsList;

public class StoreDataFromInputFile {
	
	//method to store data from past games file and place into a list of games and return list
	public static void storeDataIntoGameList(String inputfile_, GamesList gamesList_, TeamsList teamsList_) throws RuntimeException, IOException {
		
		GamesFileReader in = new GamesFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, gamesList_, teamsList_);
		is.close();
		
	}
	
	

}
