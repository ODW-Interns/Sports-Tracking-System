package com.sts.control;

import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.BeforeClass;
import org.junit.Test;

public class GamesReaderTest {

	 public static GamesList listofGames = new GamesList();
	 public static TeamsList listofTeams = new TeamsList();
	 public static PlayersList listofPlayers = new PlayersList();
	 public static GamesReader gr = new GamesReader();

	@BeforeClass
	public static void runBeforeTests() {
   

	    try{
			StoreDataFromInputFile.storeDataIntoTeamList("/Teams.csv", listofGames, listofTeams);
			StoreDataFromInputFile.storeDataIntoPlayerList("/Players.csv", listofGames, listofTeams, listofPlayers);
		    StoreDataFromInputFile.storeDataIntoGameList("/games.csv", listofGames, listofTeams, listofPlayers);
	    }
	    catch (Exception e_) {
	    	assert(false);
	        e_.printStackTrace();
	    } 
	}
	
	@Test
	public void DuplicateTeamExceptionTest() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Arizona|Diamondbacks|5|4|12589|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	
	@Test
	public void MismatchGameandTeamSportExceptionTestOne() {
		String gameData = "NBA|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Baltimore|Orioles|5|4|12589|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	@Test
	public void MismatchGameandTeamSportExceptionTestTwo() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Cardinals|Baltimore|Orioles|5|4|12589|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}	
	
	
	@Test
	public void NegativeScoreExceptionTest() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Baltimore|Orioles|-1|4|12589|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	@Test
	public void NegativeAttendanceExceptionTest() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Baltimore|Orioles|1|4|-1|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	
	@Test
	public void TeamNotFoundException() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamonds|Baltimore|Orioles|1|4|12000|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	@Test
	public void MismatchPlayerandGameSportExceptionTest() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Baltimore|Orioles|5|4|11,2|13,14|12589|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}			
		assertTrue(thrown);
	}
	
	@Test
	public void InvalidPlayersExceptionTest() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Baltimore|Orioles|5|4|11,20000|13,14|12589|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}	
		assertTrue(thrown);
	}
	
	@Test
	public void MismatchPlayerandTeamSportExceptionTest() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Baltimore|Orioles|5|4|1,12|13,14|12589|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}		
		assertTrue(thrown);
	}
	
	@Test
	public void PlayerNotOnTeamExceptionTest() {
		String gameData = "MLB|100|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|Baltimore|Orioles|1|4|13,12|11,14|12000|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}		
		assertTrue(thrown);
	}
	
	@Test
	public void parseDateMethodTest() {
		boolean sameDate = false;
		String date = "2018-05-24T00:00-07:00"; // Pacific Time - should convert to 00:00 PT
		  DateTimeFormatter formatter =DateTimeFormatter.ISO_DATE_TIME;
          ZonedDateTime date1 = ZonedDateTime.parse(date,  formatter);	
         ZonedDateTime date2 = gr.parseDate("2018-05-24T03:00-04:00"); // Eastern time is 3 hours ahead
		if(date1.withZoneSameInstant(ZoneId.systemDefault()).equals(date2))
			sameDate = true;
		assertTrue(sameDate);
	}
	
	
}
