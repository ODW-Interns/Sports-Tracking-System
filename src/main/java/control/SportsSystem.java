package control;

import io.StoreDataFromInputFile;
import lists.GamesList;
import view.ConsolePrinter;
import view.PrintToLog;

import java.io.IOException;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException{
		startSystem();
	}
	
	public static void startSystem() throws RuntimeException, IOException {
		int sportChosen = -1;	
		int request = -1;
		// List of Games from the past NBA season
		GamesList listofPastGames = StoreDataFromInputFile.storeDataIntoGameList("/17-18NBA_RegSzn.txt");
		System.out.println("<<<<<<<<<<Welcome to the Sports Tracking System>>>>>>>>>>");
		
		/*
		 * Prompt user for input
		 * 1)(Main Menu)Prompts the user for the sport they want to track first
		 * 2)Asks the user if they want to see a list of games in the last season or upcoming games
		 * for the sport chosen
		 * 
		 * To Stop System -> User has to enter 0 in the main menu
		 * */
		
		while(sportChosen != 0){
			sportChosen = ControllerToHandleUserInput.readSportsChosenByUser();
			if(ControllerToHandleUserInput.sportChosenIsValid(sportChosen) && sportChosen != 0){
				do {
					request = ControllerToHandleUserInput.readRequestByUser();
					switch(request) {
					case 1: // Print to Log
						System.out.println("1 was selected");
						PrintToLog.logGamesList(listofPastGames);
						//call second log
						PrintToLog.secondLogGamesList(listofPastGames);
						break;
					}
				}while(request != 0);
			ConsolePrinter.printOutOptionsForSport();
			
			}
		}
	}
	public static void main(String[] args) throws RuntimeException, IOException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}