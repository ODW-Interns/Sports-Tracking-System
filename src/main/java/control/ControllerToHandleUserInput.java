package control;

import java.util.InputMismatchException;
import java.util.Scanner;

import view.ConsolePrinter;

/*Class to handle and validate
 * all user input from the console
 */
public class ControllerToHandleUserInput {
	
	public static Scanner s = new Scanner(System.in);
	
	ControllerToHandleUserInput(){
	}
	
	// method to return a valid input by the user in the main menu
	public static int readSportsChosenByUser() {
		int sportsChosenByUser = 0;
		boolean valid = false;
	
		/*catches invalid input and prompts user again to 
		 * enter a valid input
		 * */
		
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
	
	//method to return valid request by user
	public static int readRequestByUser() {
		int optionChosen = -1;
		boolean valid = false;
		
		/*catches invalid input and prompts user again to 
		 * enter a valid input
		 * */
		
		while(!valid) {
			ConsolePrinter.printOutOptionsForSport();
			try {
				optionChosen = s.nextInt();
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
	
	//method that returns true if input in main menu is valid
	public static boolean sportChosenIsValid(int sport) {
		if(sport < 0 || sport > 4)
			return false;
		else
			return true;
	}
	
	//method that returns true if the request made is valid
	public static boolean requestByUserIsValid(int request) {
		if(request != 1 && request != 2 && request != 0 && request != 3) {
			return false;
		}
		else
			return true;
	}
}
