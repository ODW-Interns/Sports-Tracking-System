package com.sts.control;

import java.io.IOException;
import java.text.ParseException;
import com.sts.io.StoreDataFromInputFile;
import com.sts.model.GamesList;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException, ParseException{
		startSystem();
	}
	

	public void startSystem() throws RuntimeException, IOException, ParseException {
	    GamesList gameslist = StoreDataFromInputFile.storeDataIntoGameList("/games.csv");
	    gameslist.logFinishedGames();
		gameslist.logUpcomingGames();
		
		/*
		 * Prompt user for input
		 * 1)(Main Menu)Prompts the user for the sport they want to track first
		 * 2)Asks the user if they want to see a list of games in the last season or upcoming games
		 * for the sport chosen
		 * 
		 * To Stop System -> User has to enter 0 in the main menu
		 * */
		/*
		//While the system is on
		while(sportChosen != 0){ // (MAIN MENU)
			PrintToLog.printMainMenuAlert();
			sportChosen = ControllerToHandleUserInput.readSportsChosenByUser(); // read in valid input from user
			PrintToLog.printSportCategory(sportChosen);
			
			if(sportChosen == 1) {
				genericUpcomingGamesList = listofUpcomingNBAGames;
				genericPastGamesList = listofPastNBAGames;
				genericTeamsList = listofNBATeams;
			}
			else if(sportChosen == 2) {
				//genericUpcomingGamesList = listofUpcomingNFLGames;
				genericPastGamesList = listofPastNFLGames;
				genericTeamsList = listofNFLTeams;
			}
			else if(sportChosen == 3) {
				//genericUpcomingGamesList = listofUpcomingMLBGames;
				genericPastGamesList = listofPastMLBGames;
				genericTeamsList = listofMLBTeams;
			}
			else if(sportChosen == 4) {
				//genericUpcomingGamesList = listofUpcomingNHLGames;
				genericPastGamesList = listofPastNHLGames;
				genericTeamsList = listofNHLTeams;
			}
			if(ControllerToHandleUserInput.sportChosenIsValid(sportChosen) && sportChosen != 0){
				do { 
					// Inside individual sport category
					// Refresh
					numOfInvalidUpcomingGames = RealTimeDataChecker.thereAreInvalidUpcomingGames(listofUpcomingNBAGames);
					if(numOfInvalidUpcomingGames > 0) {
					RealTimeDataChecker.refreshDataLists(numOfInvalidUpcomingGames, listofUpcomingNBAGames, listofPastNBAGames);
					}
					request = ControllerToHandleUserInput.readRequestByUser();
					switch(request) {

					case 1: //1. Refresh Both upcoming games and finished games list
							//2.Print to Log the list of finished games
							System.out.println("1 was selected");
							numOfInvalidUpcomingGames = RealTimeDataChecker.thereAreInvalidUpcomingGames(listofUpcomingNBAGames);
							if(numOfInvalidUpcomingGames > 0) {
								RealTimeDataChecker.refreshDataLists(numOfInvalidUpcomingGames, listofUpcomingNBAGames, listofPastNBAGames);
							}
							PrintToLog.logGamesList(listofPastNBAGames);
							break;
					case 2: // 1. Refresh both upcoming games and finished games list
							// 2. Print to Log the list of upcoming games
							numOfInvalidUpcomingGames = RealTimeDataChecker.thereAreInvalidUpcomingGames(listofUpcomingNBAGames);
							if(numOfInvalidUpcomingGames > 0) {
								RealTimeDataChecker.refreshDataLists(numOfInvalidUpcomingGames, listofUpcomingNBAGames, listofPastNBAGames);
							}
							PrintToLog.logGamesList(listofUpcomingNBAGames);
							break;
					case 3: //Print out all teams & let user choose from team to see current roster
							PrintToLog.logAllRosters(listofNBATeams);
					}
					
				}while(request != 0);	
				//Back in Main Menu
				//Print options for user and prompts for input with menu
			ConsolePrinter.printOutOptionsForSport(); 
			
			}
		}*/

	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}