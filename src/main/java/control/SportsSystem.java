package control;

import io.StoreDataFromInputFile;
import lists.GamesList;
import model.RealTimeDataChecker;
import model.Team;
import view.ConsolePrinter;
import view.PrintToLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException, ParseException{
		startSystem();
	}
	
	public static void startSystem() throws RuntimeException, IOException, ParseException {
		int sportChosen = -1;	
		int request = -1;
		int numOfInvalidUpcomingGames = 0;
		// List of Games from the past NBA season
		GamesList listofPastGames = StoreDataFromInputFile.storeDataIntoPastGameList("/17-18NBA_RegSzn.txt");
		// List of upcoming Games in the NBA
		GamesList listofUpcomingGames = StoreDataFromInputFile.storeDataIntoUpcomingGameList("/NBA_Upcoming.txt");
		System.out.println("<<<<<<<<<<Welcome to the Sports Tracking System>>>>>>>>>>");
		
		/*
		 * Prompt user for input
		 * 1)(Main Menu)Prompts the user for the sport they want to track first
		 * 2)Asks the user if they want to see a list of games in the last season or upcoming games
		 * for the sport chosen
		 * 
		 * To Stop System -> User has to enter 0 in the main menu
		 * */
		
		//While the system is on
		while(sportChosen != 0){ // (MAIN MENU)
			sportChosen = ControllerToHandleUserInput.readSportsChosenByUser(); // read in valid input from user
			if(ControllerToHandleUserInput.sportChosenIsValid(sportChosen) && sportChosen != 0){
				do { 
					// Inside individual sport category
					request = ControllerToHandleUserInput.readRequestByUser();
					switch(request) {
					case 0:  // Refresh & Go back to Main Menu	
							numOfInvalidUpcomingGames = RealTimeDataChecker.thereAreInvalidUpcomingGames(listofUpcomingGames);
							if(numOfInvalidUpcomingGames > 0) {
								RealTimeDataChecker.refreshDataLists(numOfInvalidUpcomingGames, listofUpcomingGames, listofPastGames);
							}
							break;
					case 1: //1. Refresh Both upcoming games and finished games list
							//2.Print to Log
							System.out.println("1 was selected");
							numOfInvalidUpcomingGames = RealTimeDataChecker.thereAreInvalidUpcomingGames(listofUpcomingGames);
							if(numOfInvalidUpcomingGames > 0) {
								RealTimeDataChecker.refreshDataLists(numOfInvalidUpcomingGames, listofUpcomingGames, listofPastGames);
							}
							PrintToLog.logGamesList(listofPastGames);
							break;
					}
				}while(request != 0);	
				//Back in Main Menu
				//Print options for user and prompts for input with menu
			ConsolePrinter.printOutOptionsForSport(); 
			
			}
		}
	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}