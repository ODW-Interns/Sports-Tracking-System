package com.sts.control;

import java.io.IOException;
import java.text.ParseException;

import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.TeamsList;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException, ParseException{
		startSystem();
	}
	

	public void startSystem() throws RuntimeException, IOException, ParseException {
		//Create a GamesList object to hold all games 
		GamesList _listofGames = new GamesList();
		//Create a TeamsList object to hold all teams
		TeamsList _listofTeams = new TeamsList();
		//Read from "games.csv" file and store all games and teams read
	    StoreDataFromInputFile.storeDataIntoGameList("/games.csv", _listofGames, _listofTeams );
	    _listofGames.logFinishedGames();
	    _listofGames.logUpcomingGames();
		

	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}