package control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.main.mainTemp;

import io.NBAPastGamesFileReader;
import io.StoreDataFromInputFile;
import io.TeamListFileReader;
import lists.GamesList;
import lists.TeamsList;
import view.ConsolePrinter;
import view.PrintToLog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException{
		startSystem();
	}
	
	public static void startSystem() throws RuntimeException, IOException {
		int sportChosen = -1;	
		String request;
		GamesList listofGames = StoreDataFromInputFile.storeDataIntoGameList("/16-17_NBA_RegSzn.txt");
		System.out.println(listofGames.size());
		System.out.println("<<<<<<<<<<Welcome to the Sports Tracking System>>>>>>>>>>");
		
		while(sportChosen != 0){
			sportChosen = ControllerToHandleUserInput.readSportsChosenByUser();
			if(ControllerToHandleUserInput.sportChosenIsValid(sportChosen)){
				do {
					request = ControllerToHandleUserInput.readRequestByUser();
					switch(request) {
					case "1": // Print to Log
						PrintToLog.logGamesList(listofGames);
						break;
					}
				}while(request != "B");
			ConsolePrinter.printOutOptionsForSport();
			
			}
		}
	}
	public static void main(String[] args) throws RuntimeException, IOException {
		SportsSystem system = new SportsSystem();
		

	}
}