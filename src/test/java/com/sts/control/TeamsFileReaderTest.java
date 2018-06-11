/****************************************************************************
 * FILE: GamesFileReaderTest.java
 * DSCRPT:
 ****************************************************************************/

package com.sts.control;

import org.junit.BeforeClass;

import com.sts.concretemodel.GamesList;
import com.sts.concretemodel.PlayersList;
import com.sts.concretemodel.TeamsList;

public class TeamsFileReaderTest {

	   public static GamesList listofGames = new GamesList();
	   public static TeamsList listofTeams = new TeamsList();
	   public static PlayersList listofPlayers = new PlayersList();
	
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
	

}
