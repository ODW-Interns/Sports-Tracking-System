package com.sts.control;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	    StoreDataFromInputFile.storeDataIntoGameList("/games.csv", listofGames, listofTeams, listofPlayers);
	    
	    Logger _logger;
	    
	    _logger = LoggerFactory.getLogger(getClass().getSimpleName());
	    
	    Scanner input= new Scanner(System.in);
	    _logger.info("Welcome to sports Tracking system");

	    boolean isNumber;
	    int choice = 0;    
	    boolean c=true;
	    
	    while (c) {
	    	
	    	_logger.info("1: Enter 1 to display list of finished games");
	    	_logger.info("2: Enter 2 to display list of upcoming games");	
	    	_logger.info("3: Enter 3 to exit");
		    
		    do {
		    	
		    	if(input.hasNextInt() ){
			    	
			   		choice=input.nextInt();
			   		isNumber=true;	
			   		
			   	}
			    else {
			    	
			    	_logger.info("Please choose an integer value from the above options");
			    	isNumber=false;
			    	input.nextInt();
			    	
			    }
		    	
		    }while(!isNumber);
			 
		    
		    switch (choice) {
			case 1:
				
				listofGames.logFinishedGames();
				
				break;
			case 2:
				
			    listofGames.logUpcomingGames();
				
			    break;
			case 3:
				
			    System.exit(0);
				
			    break;    
			    
			default:
				
				_logger.info("Wrong choice input. Please choose from the following options");
				
				break;
			
				
			
			}
		    input.close();
		}
		

	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}