package com.sts.control;

import java.io.IOException;
import java.text.ParseException;

import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.PlayersList;
import com.sts.concreteModel.TeamsList;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException, ParseException{
		startSystem();
	}
	

	public void startSystem() throws RuntimeException, IOException, ParseException {
		//Create a GamesList object to hold all games 
		GamesList listofGames = new GamesList();
		//Create a TeamsList object to hold all teams
		TeamsList listofTeams = new TeamsList();
		PlayersList listofPlayers = new PlayersList();
		//Read from "games.csv" file and store all games and teams read
		StoreDataFromInputFile.storeDataIntoPlayerList("/Players.csv", listofGames, listofTeams, listofPlayers);
		System.out.println(listofPlayers.returnPlayersMap().size());
		StoreDataFromInputFile.storeDataIntoGameList("/games.csv", listofGames, listofTeams, listofPlayers);
	    listofGames.logFinishedGames();
	    listofGames.logUpcomingGames();
		

	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}