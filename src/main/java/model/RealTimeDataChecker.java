package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import lists.GamesList;

public class RealTimeDataChecker {

	public RealTimeDataChecker() {
		// TODO Auto-generated constructor stub
	}
	
	public static void refreshDataLists(int numInvalid, GamesList upcoming_, GamesList past_) throws ParseException {
		Date current = new Date();
		Date dateToCheck;
		int randomHomeScore;
		int randomAwayScore;
		int randomAttendance;
		int minScore = 60;
		int maxScore = 130;
		int minAttendance = 8000;
		int maxAttendance = 13000;
		
		for(int i = 0; i < numInvalid; i++) {
			dateToCheck = parseDateAndFormat(upcoming_.get(i).getDate(), upcoming_.get(i).getTime());
			if(current.after(dateToCheck)){
				//temporary refresh fix before implementing DB
				randomHomeScore = generateRandomScore(minScore, maxScore);
				randomAwayScore = generateRandomScore(minScore, maxScore);
				randomAttendance = generateRandomAttendance(minAttendance, maxAttendance);
				upcoming_.get(i).setaTeamScore(randomAwayScore);
				upcoming_.get(i).sethTeamScore(randomHomeScore);
				upcoming_.get(i).setAttendence(randomAttendance);
				past_.add(upcoming_.get(i));
				upcoming_.remove(upcoming_.get(i));
			}
		}
	}
	
	public static int generateRandomScore(int minScore, int maxScore) {
		
		int randomScore = (int)(Math.random()*((maxScore-minScore)+1)) + minScore;
		return randomScore;
	}
	
	public static int generateRandomAttendance(int minAttendance, int maxAttendance) {
		int randomAttendance = (int)(Math.random()*((maxAttendance-minAttendance)+1)) + minAttendance;
		return randomAttendance;
	}
	
	public static int thereAreInvalidUpcomingGames(GamesList upcomingList_) throws ParseException {
		
		Date formattedDate = parseDateAndFormat(upcomingList_.get(0).getDate(), upcomingList_.get(0).getTime());
		Date current = new Date();
		int gamesInvalid = 0;
		int i = 0;
		while(current.after(formattedDate)) {
			gamesInvalid++;
			formattedDate = parseDateAndFormat(upcomingList_.get(gamesInvalid).getDate(), upcomingList_.get(gamesInvalid).getTime());
		}
		
		return gamesInvalid;
		
	}
	
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
	
	
	
	public static String convertMonthStringtoNum(String month) {
		
		month = month.substring(0, 3);

		if(month.equals("Jan"))
			return "1";
		else if(month.equals("Feb"))
			return "2";
		else if(month.equals("Mar"))
			return "3";
		else if(month.equals("Apr"))
			return "4";
		else if(month.equals("May"))
			return "5";
		else if(month.equals("Jun"))
			return "6";
		else if(month.equals("Jul"))
			return "7";
		else if(month.equals("Aug"))
			return "8";
		else if(month.equals("Sep"))
			return "9";
		else if(month.equals("Oct"))
			return "10";
		else if(month.equals("Nov"))
			return "11";
		else if(month.equals("Dec"))
			return "12";
		else 
			return month;
	}

}
