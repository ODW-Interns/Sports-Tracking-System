package com.sts.io;
import java.io.IOException;
import java.io.InputStream;

import com.sts.model.GamesList;
import com.sts.model.PlayersList;
import com.sts.model.TeamsList;

public class StoreDataFromInputFile {
	
	//method to store data from past games file and place into a list of games and return list
	public static GamesList storeDataIntoGameList(String inputfile_, GamesFileReader in_) throws RuntimeException, IOException {
		GamesList listofGames = new GamesList();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in_.readData(is, listofGames.getGamesMap());
		is.close();
		
		return listofGames;
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
	
	
	
	public static TeamsList storeDataIntoTeamsList(String inputfile_) throws RuntimeException, IOException {
		TeamsList listofTeams = new TeamsList();
		TeamListFileReader in = new TeamListFileReader();
		InputStream is = StoreDataFromInputFile.class.getResourceAsStream(inputfile_);
		in.readData(is, listofTeams.returnTeamMap());
		is.close();
		
		return listofTeams;
	}
	

}
