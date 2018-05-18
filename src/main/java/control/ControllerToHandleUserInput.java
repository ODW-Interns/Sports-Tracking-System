package control;

import java.util.InputMismatchException;
import java.util.Scanner;

import view.ConsolePrinter;

public class ControllerToHandleUserInput {
	
	public static Scanner s = new Scanner(System.in);
	
	ControllerToHandleUserInput(){
	}
	
	public static int readSportsChosenByUser() {
		int sportsChosenByUser = 0;
		boolean valid = false;
		while(!valid) {
			ConsolePrinter.printOutSportsToChooseFrom();
			try {
				sportsChosenByUser = s.nextInt();
				if(ControllerToHandleUserInput.sportChosenIsValid(sportsChosenByUser))
					valid = true;
				else {
					System.out.println("Invalid input, try again");
				}
			}
			catch(InputMismatchException ime) {
				System.out.println("Invalid input, try again");
				s.next();
			}
		}
		return sportsChosenByUser;
	}
	
	public static String readRequestByUser() {
		String optionChosen = "";
		boolean valid = false;
		while(!valid) {
			ConsolePrinter.printOutOptionsForSport();
			try {
				optionChosen = s.nextLine();
				if(ControllerToHandleUserInput.requestByUserIsValid(optionChosen)) {
					valid = true;
				}
				else {
					System.out.println("Invalid input, try again");
				}
			}
			catch(InputMismatchException ime){
				System.out.println("Invalid input, try again");
				s.next();
			}
		}
		return optionChosen;
	}
	
	public static boolean sportChosenIsValid(int sport) {
		if(sport < 0 || sport > 4)
			return false;
		else
			return true;
	}
	
	public static boolean requestByUserIsValid(String request) {
		if(request != "1" || request != "2" || request != "B") {
			return false;
		}
		else
			return true;
	}
}
