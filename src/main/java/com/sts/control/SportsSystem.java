package com.sts.control;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;

import com.sts.io.GamesFileReader;
import com.sts.io.StoreDataFromInputFile;
import com.sts.model.Game;
import com.sts.model.GamesList;
import com.sts.model.Key;
import com.sts.model.Team;
import com.sts.model.TeamsList;
import com.sts.view.ConsolePrinter;
import com.sts.view.PrintToLog;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException, ParseException{
		startSystem();
	}
	

	public void startSystem() throws RuntimeException, IOException, ParseException {
	     
		ArrayList<Game> list = null;
		GamesFileReader gr = new GamesFileReader();
	        try (InputStream is = getClass().getResourceAsStream("/games.csv")) {
	           list = new ArrayList<Game>();
	            gr.readData(is, list);
	        }
	        catch (Exception e_) {
	            assert(false);
	            e_.printStackTrace();
	        }
		
		GamesList map =  new GamesList();
		
		
		
		
		String str_1 = "2018-05-22T16:00:00+04:00";
		String str_2 = "2018-05-12T16:00:00+04:00";
		String str_3 = "2018-05-28T16:00:00+04:00";
		String str_4 = "2017-05-29T16:00:00+04:00";
		DateTimeFormatter formatter =DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime date1 = ZonedDateTime.parse(str_1, formatter);
        ZonedDateTime date2 = ZonedDateTime.parse(str_2, formatter);
        ZonedDateTime date3 = ZonedDateTime.parse(str_3, formatter);
        ZonedDateTime date4 = ZonedDateTime.parse(str_4, formatter);
		
        Team team1 = new Team("Warriors", "Golden State", 0);
        Team team2 = new Team("Rockets", "Houston", 0);
        Team team3 = new Team("Cavaliers", "Cleveland", 0);
        
		//GamesList games = new GamesList();
		System.out.println("here");
		map.put(new Key(list.get(0).getDate(), list.get(0).getAwayTeam(), list.get(0).getHomeTeam()), list.get(0));
		map.put(new Key(list.get(1).getDate(), list.get(1).getAwayTeam(), list.get(1).getHomeTeam()), list.get(1));
		map.put(new Key(list.get(2).getDate(), list.get(2).getAwayTeam(), list.get(2).getHomeTeam()), list.get(2));
		System.out.println("Last Away team:" + list.get(2).toString());
		//games.addGame(date3, team1, team3);
		
        System.out.println(map.size());
        
		for(Key key : map.keySet()) {
		    System.out.println(map.get(key));
		}
		
		


		System.out.println("<<<<<<<<<<Welcome to the Sports Tracking System>>>>>>>>>>");
		
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