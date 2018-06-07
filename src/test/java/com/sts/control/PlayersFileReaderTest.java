package com.sts.control;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.PlayersList;
import com.sts.concreteModel.TeamsList;

public class PlayersFileReaderTest {

	 public static GamesList listofGames = new GamesList();
	 public static TeamsList listofTeams = new TeamsList();
	 public static PlayersList listofPlayers = new PlayersList();
	 public static PlayersFileReader pr = new PlayersFileReader();

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
	public void MismatchPlayerandTeamSportExceptionTest() {
		String playerData = "NFL|1000000|29|F1|L1|Brooklyn Nets";
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
		String playerData = "NBA|10000001|28|F1|L1|Brook Nets";
		boolean thrown = false;
		try {
			pr.readFromStringForList(playerData, listofPlayers, listofTeams);
		}
		catch(Exception e_) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
}
