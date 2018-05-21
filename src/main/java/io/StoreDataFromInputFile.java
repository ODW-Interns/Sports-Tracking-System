package io;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import lists.GamesList;
import lists.PlayersList;
import lists.TeamsList;

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
	
	//method to store players data into list of players and return the list
	
	
	public static PlayersList storeDataIntoPlaersList(String inputfile_, TeamsList listofTeams) throws RuntimeException, IOException {
		PlayersList listofPlayers = new PlayersList();
		PlayerListFileReader in = new PlayerListFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is,listofTeams);
		is.close();
		
		return listofPlayers;
	}
	
	
	
	public static TeamsList storeDataIntoTeamsList(String inputfile_) throws RuntimeException, IOException {
		TeamsList listofTeams = new TeamsList();
		TeamListFileReader in = new TeamListFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, listofTeams);
		is.close();
		
		return listofTeams;
	}
	

}
