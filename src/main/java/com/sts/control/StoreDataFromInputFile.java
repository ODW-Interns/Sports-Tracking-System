package com.sts.control;
import java.io.IOException;
import java.io.InputStream;
/**
 * Class to store data from input files into Game/ Players/ Teams maps
 */
public class StoreDataFromInputFile {
	
	// method to store data into games map
	public static void storeDataIntoGameList(String inputfile_, GamesList gamesList_, TeamsList teamsList_, PlayersList playersList_) throws RuntimeException, IOException {
		
		GamesReader in = new GamesReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, gamesList_, teamsList_, playersList_);
		is.close();
		
	}
	
	//method to store data into players map
	public static void storeDataIntoPlayerList(String inputfile_, GamesList gamesList_, TeamsList teamsList_, PlayersList playersList_) throws IOException {
		PlayersReader in = new PlayersReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, playersList_, teamsList_, gamesList_);
		is.close();
	}
	
	//method to store data into teams map
	public static void storeDataIntoTeamList(String inputfile_, GamesList gamesList_, TeamsList teamsList_) throws RuntimeException, IOException {
		TeamsReader in = new TeamsReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, gamesList_, teamsList_);
		is.close();
	}
	

}
