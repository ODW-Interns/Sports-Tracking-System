package com.sts.control;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class PlayersReaderTest {

	 public static GamesList listofGames = new GamesList();
	 public static TeamsList listofTeams = new TeamsList();
	 public static PlayersList listofPlayers = new PlayersList();
	 public static PlayersReader pr = new PlayersReader();

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
	public void MismatchPlayerandTeamSportExceptionTest() {
		String playerData = "NFL|1000000|29|F1|L1|Brooklyn|Nets|2017-01-01";
		boolean thrown = false;
		try {
			pr.readFromStringForList(playerData, listofPlayers, listofTeams);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}

	@Test
	public void TeamNotFoundExceptionTest() {
		String playerData = "NBA|10000001|28|F1|L1|Brook|Nets|2017-01-01";
		boolean thrown = false;
		try {
			pr.readFromStringForList(playerData, listofPlayers, listofTeams);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	@Test
	public void InvalidStartDateExceptionTest() {
		String playerData = "NBA|78|28|F2|L2|Atlanta|Hawks|2018-14-15";
		boolean thrown = false;
		try {
			pr.readFromStringForList(playerData,  listofPlayers, listofTeams);
			}
		catch(Exception e_) {
			thrown = true;
		}
			assertTrue(thrown);
	}
	
	@Test
	public void samePlayerIDBeingUsed() {
		String playerData = "NBA|1|28|F1|L1|Boston Celtics|2018-07-01";
		boolean thrown = false;
		try {
			pr.readFromStringForList(playerData,  listofPlayers, listofTeams);
			}
		catch(Exception e_) {
			thrown = true;
		}
			assertTrue(thrown);
		
				
	}
	
}
