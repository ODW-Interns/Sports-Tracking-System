package com.sts.control;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.PlayersList;
import com.sts.concreteModel.TeamsList;

public class GamesFileReaderTest {

	 public static GamesList listofGames = new GamesList();
	 public static TeamsList listofTeams = new TeamsList();
	 public static PlayersList listofPlayers = new PlayersList();
	 public static GamesFileReader gr = new GamesFileReader();

	@BeforeClass
	public static void runBeforeTests() {
   

	    try{
			StoreDataFromInputFile.storeDataIntoTeamList("/Teams.txt", listofGames, listofTeams);
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|11,12|Arizona|Diamondbacks|13,14|5|4|12589|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|11,2|Baltimore|Orioles|13,14|5|4|12589|PT3H2M35S";
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
		String gameData = "100|NBA|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|11,12|Baltimore|Orioles|13,14|5|4|12589|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Cardinals|11,12|Baltimore|Orioles|13,14|5|4|12589|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|11,20000|Baltimore|Orioles|13,14|5|4|12589|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|1,12|Baltimore|Orioles|13,14|5|4|12589|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|11,12|Baltimore|Orioles|13,14|-1|4|12589|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|11,12|Baltimore|Orioles|13,14|1|4|-1|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamondbacks|13,12|Baltimore|Orioles|11,14|1|4|12000|PT3H2M35S";
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
		String gameData = "100|MLB|2018-05-24T13:00:00+06:00|Arizona|Diamonds|11,12|Baltimore|Orioles|13,14|1|4|12000|PT3H2M35S";
		boolean thrown = false;
		try {
			gr.readFromStringForList(gameData, listofTeams, listofPlayers, listofGames);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
}
