package control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import model.Game;
import model.GamesList;

//class to handle and check data in real time
public class RealTimeDataChecker {
	
	//method to refresh data to see any updates from games lists
	public static void refreshDataLists(int numInvalid, GamesList upcoming_, GamesList past_) throws ParseException {
		Date current = new Date();
		Date dateToCheck;
		/*
		 * Following variables are used to generate random scores
		 * and attendance for a temporary solution to test if the data
		 * checker works correctly
		 */
		int randomHomeScore;
		int randomAwayScore;
		int randomAttendance;
		int minScore = 60;
		int maxScore = 130;
		int minAttendance = 8000;
		int maxAttendance = 13000;
		
		for(int i = 0; i < numInvalid; i++) {
			dateToCheck = parseDateAndFormat(upcoming_.get(0).getDate(), upcoming_.get(0).getTime());
			System.out.println("Date to Check" + dateToCheck);
			if(current.after(dateToCheck)){
				//temporary refresh fix before implementing DB
				randomHomeScore = generateRandomScore(minScore, maxScore);
				randomAwayScore = generateRandomScore(minScore, maxScore);
				randomAttendance = generateRandomAttendance(minAttendance, maxAttendance);
				upcoming_.get(0).setaTeamScore(randomAwayScore);
				upcoming_.get(0).sethTeamScore(randomHomeScore);
				upcoming_.get(0).setAttendence(randomAttendance);
				/*
				 * If there exists a game in the upcoming games list whose date is 
				 * has already been passed, then these lists need to be updated.
				 * 1)Delete the game from the upcoming list
				 * 2)Append game to end of the past games list
				 */
				past_.add(upcoming_.get(0));
				upcoming_.remove(upcoming_.get(0));
			}
		}
	}
	
	//method to generate a random score to place into game
	public static int generateRandomScore(int minScore, int maxScore) {
		
		int randomScore = (int)(Math.random()*((maxScore-minScore)+1)) + minScore;
		return randomScore;
	}
	
	//method to generate a random attendance number to set in game
	public static int generateRandomAttendance(int minAttendance, int maxAttendance) {
		int randomAttendance = (int)(Math.random()*((maxAttendance-minAttendance)+1)) + minAttendance;
		return randomAttendance;
	}
	
	/*
	 * method to check if there are any games in the upcoming list that
	 * have already passed. If there are any, return the number of upcoming
	 * games that have already passed.
	 */
	
	public static int thereAreInvalidUpcomingGames(GamesList upcomingList_) throws ParseException {
		
		Date current = new Date();
		int gamesInvalid = 0;
		
		Game game = new Game();
		Iterator<Game> gameIterator = upcomingList_.iterator();
		while(gameIterator.hasNext()) {
			game = gameIterator.next();
			
			if(current.after(RealTimeDataChecker.parseDateAndFormat(game.getDate(), game.getTime()))){
				gamesInvalid++;
			}
			else 
				break;
		}
		
		return gamesInvalid;
		
	}
	
	/*
	 * method to take the date string and parse/format into a Java Date object
	 * RETURN: Date Object with form dd/MM/yyyy HH:mm:ss
	 */
	public static Date parseDateAndFormat(String unformattedDate_, String time) throws ParseException {
		final String delim = " ";
		String monthNum;
		String dateNum;
		String yearNum;
		String formattedString;
		StringTokenizer tokenizer;
		Date formattedDate = null;
	
		tokenizer = new StringTokenizer(unformattedDate_, delim);
		monthNum = convertMonthStringtoNum(tokenizer.nextToken());
		dateNum = tokenizer.nextToken();
		yearNum = tokenizer.nextToken();
		formattedString = dateNum + "/" + monthNum + "/" + yearNum + " " + time + ":00";
		System.out.println(formattedString);
		formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(formattedString);
		return formattedDate;
	}
	
	
	//method to convert word month to the number of the month
	public static String convertMonthStringtoNum(String month) {
		
		month = month.substring(0, 3);
		if(month.toUpperCase().equals("JAN"))
			return "1";
		else if(month.toUpperCase().equals("FEB"))
			return "2";
		else if(month.toUpperCase().equals("MAR"))
			return "3";
		else if(month.toUpperCase().equals("APR"))
			return "4";
		else if(month.toUpperCase().equals("MAY"))
			return "5";
		else if(month.toUpperCase().equals("JUN"))
			return "6";
		else if(month.toUpperCase().equals("JUL"))
			return "7";
		else if(month.toUpperCase().equals("AUG"))
			return "8";
		else if(month.toUpperCase().equals("SEP"))
			return "9";
		else if(month.toUpperCase().equals("OCT"))
			return "10";
		else if(month.toUpperCase().equals("NOV"))
			return "11";
		else if(month.toUpperCase().equals("DEC"))
			return "12";
		else 
			return month;
	}

}
