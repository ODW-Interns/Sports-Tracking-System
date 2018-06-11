package com.sts.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;
import com.sts.control.StoreDataFromInputFile;

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
		StoreDataFromInputFile.storeDataIntoTeamList("/Teams.txt", listofGames, listofTeams);
		StoreDataFromInputFile.storeDataIntoPlayerList("/Players.csv", listofGames, listofTeams, listofPlayers);
	    StoreDataFromInputFile.storeDataIntoGameList("/games.csv", listofGames, listofTeams, listofPlayers);
	    
	    Logger _logger;
	    
	    _logger = LoggerFactory.getLogger(getClass().getSimpleName());
	    
	    Scanner input= new Scanner(System.in);
	    _logger.info("Welcome to sports Tracking system");

	    boolean isNumber;
	    int choice = 0;   
	    int choice2 = 0;
	    String fileName;
	    boolean c=true;
	    
	    while (c) {
	    	
	    	_logger.info("1: Enter 1 to display list of finished games");
	    	_logger.info("2: Enter 2 to display list of upcoming games");
	    	_logger.info("3: Enter 3 to update player(s))");
	    	_logger.info("4: Enter 4 to exit");
		    
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
				
				listofGames.logFinishedGames(listofPlayers);
				
				break;
			case 2:
				
			    listofGames.logUpcomingGames(listofPlayers);
				
			    break;
			case 3:
				input.nextLine();
				_logger.info("Enter the file name to update player(s) you want");
				fileName = input.nextLine();
				String path = "/";
				path = path + fileName;
				try {
					
					System.out.println(path);
					StoreDataFromInputFile.storeDataIntoPlayerList(path, listofGames, listofTeams, listofPlayers);
				}
				catch(Exception e_1) {
					_logger.error("File not found: " + e_1.toString());
				}
				break;
			case 4:
				System.exit(0);
				break;

			default:
				
				_logger.info("Wrong choice input. Please choose from the following options");
				
				break;
			
				
			
			}
		}
		

	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}