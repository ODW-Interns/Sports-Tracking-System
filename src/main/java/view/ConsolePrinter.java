package view;

public class ConsolePrinter {


	public static void printOutSportsToChooseFrom() {
		System.out.println("Choose a sport to track!");
		System.out.println("Enter 1-4:");
		System.out.println("1.NBA | 2.NFL | 3.MLB | 4.NHL");
		System.out.println("Enter 0 to turn off system");
	}
	
	public static void printOutOptionsForSport() {
		System.out.println("What would you like to do?");
		System.out.println("Enter 1 to see all games played from last season");
		System.out.println("Enter 2 to see all upcoming games");
		System.out.println("Enter B to go back to main menu");
	}
	
}
