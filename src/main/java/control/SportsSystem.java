package control;

import io.StoreDataFromInputFile;
import model.GamesList;
import model.TeamsList;
import view.ConsolePrinter;
import view.PrintToLog;

import java.io.IOException;
import java.text.ParseException;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException, ParseException{
		startSystem();
	}
	
	public static void startSystem() throws RuntimeException, IOException, ParseException {
		int sportChosen = -1;	
		int request = -1;
		int numOfInvalidUpcomingGames = 0;
		
		GamesList genericUpcomingGamesList = new GamesList();
		GamesList genericPastGamesList = new GamesList();
		TeamsList genericTeamsList = new TeamsList();
		// List of Games from the past NBA season
		GamesList listofPastNBAGames = StoreDataFromInputFile.storeDataIntoPastGameList("/17-18NBA_RegSzn.txt");
		// List of upcoming Games in the NBA
		GamesList listofUpcomingNBAGames = StoreDataFromInputFile.storeDataIntoUpcomingGameList("/NBA_Upcoming.txt");
		// List of Players in NBA teams 
		// List of Teams in the NBA
		TeamsList listofNBATeams = StoreDataFromInputFile.storeDataIntoTeamsList("/NBA_Teams.txt");
		StoreDataFromInputFile.storeDataIntoPlayersList("/AllCurrentNBARosters.txt", listofNBATeams);
		
		// List of Games from the past NFL season
		GamesList listofPastNFLGames = StoreDataFromInputFile.storeDataIntoPastGameList("/17-18NFL_RegSzn.txt");
		// List of upcoming Games in the NFL Games
		// List of Players in NFL teams
		// List of Teams in the NFL
		TeamsList listofNFLTeams = StoreDataFromInputFile.storeDataIntoTeamsList("/NFL_Teams.txt");
		
		// List of Games from the past MLB season
		GamesList listofPastMLBGames = StoreDataFromInputFile.storeDataIntoPastGameList("/17-18MLB_RegSzn.txt");
		//List of upcoming games in the MLB
		//List of players in the MLB
		//List of teams in the MLB
		TeamsList listofMLBTeams = StoreDataFromInputFile.storeDataIntoTeamsList("/MLB_Teams.txt");
		
		//List of Games from the past NHL season
		GamesList listofPastNHLGames = StoreDataFromInputFile.storeDataIntoPastGameList("/17-18NHL_RegSzn.txt");
		// List of upcoming games in the NHL
		// List of players in the MLB
		//List of teams in the NHL
		TeamsList listofNHLTeams = StoreDataFromInputFile.storeDataIntoTeamsList("/NHL_Teams.txt");


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
					case 2: // 1. Refresh both upcoming games and finished gams list
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
		}
	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}