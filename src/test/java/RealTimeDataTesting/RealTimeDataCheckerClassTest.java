package RealTimeDataTesting;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import lists.GamesList;
import model.Game;
import model.RealTimeDataChecker;

public class RealTimeDataCheckerClassTest {

	private static GamesList upComingGamesListTest = new GamesList();
   	private static GamesList pastGamesListTest = new GamesList();
	
	@BeforeClass
	public static void setLists(){
		Game game1 = new Game("May 2 2018" , "12:00", "Washington Wizards", 89, "Cleveland Cavaliers", 97, 12387);
		Game game2 = new Game("May 3 2018" , "8:00", "Houston Rockets", 80, "Golden State Warriors", 141, 13890);
		Game game3 = new Game("May 27 2018" , "3:00", "Boston Celtics", 102, "Toronto Raptors", 91, 13789);
		upComingGamesListTest.add(game1);
		upComingGamesListTest.add(game2);
		upComingGamesListTest.add(game3);
	}
	
	@Test
	public void testToCheckIfUpcomingListsAndPastListsGetsUpdated() throws ParseException {
	
		int invalidGames = RealTimeDataChecker.thereAreInvalidUpcomingGames(upComingGamesListTest);
		assertEquals("There should be 2 passed games in upcoming games list", 2, invalidGames);
		RealTimeDataChecker.refreshDataLists(invalidGames, upComingGamesListTest, pastGamesListTest);
		assertEquals("After refresh, upcoming games list should be size of: 1", 1, upComingGamesListTest.size());
		assertEquals("After refresh, past games list should be size of: 2", 2, pastGamesListTest.size());
	}
	/*
	@Test
	public void returnsRightDataTest() throws ParseException {
		Date formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("21/05/2018 1:00:00");
		assertEquals("Date has to match", formattedDate , RealTimeDataChecker.parseDateAndFormat("May 21 2018", "1:00"));
	}*/
/*
	@Test
	public void convertingFromWordMonthToNumberMonthTest() {
		assertEquals("Should return 1", "1" , RealTimeDataChecker.convertMonthStringtoNum("JaNauarY"));
		assertEquals("Should return 2", "2" , RealTimeDataChecker.convertMonthStringtoNum("FEbr"));
		assertEquals("Should return 3", "3" , RealTimeDataChecker.convertMonthStringtoNum("maRCH"));
		assertEquals("Should return 4", "4" , RealTimeDataChecker.convertMonthStringtoNum("Apr"));
		assertEquals("Should return 5", "5" , RealTimeDataChecker.convertMonthStringtoNum("May"));
		assertEquals("Should return 6", "6" , RealTimeDataChecker.convertMonthStringtoNum("JUNE"));
		assertEquals("Should return 7", "7" , RealTimeDataChecker.convertMonthStringtoNum("jul"));
		assertEquals("Should return 8", "8" , RealTimeDataChecker.convertMonthStringtoNum("Augus"));
		assertEquals("Should return 9", "9" , RealTimeDataChecker.convertMonthStringtoNum("sept"));
		assertEquals("Should return 10", "10" , RealTimeDataChecker.convertMonthStringtoNum("October"));
		assertEquals("Should return 11", "11" , RealTimeDataChecker.convertMonthStringtoNum("NovemBER"));
		assertEquals("Should return 12", "12" , RealTimeDataChecker.convertMonthStringtoNum("deCEM"));
		
	}*/
	
}
