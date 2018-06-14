package GameTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Game;

public class NegativesTesting {

	@Test
	public void testNegatives() {
		//fail("Not yet implemented");
		Game tester = new Game();
		tester.setAttendence(-1818);
		tester.setaTeamScore(13);
		tester.sethTeamScore(-18);


		assertFalse(tester.getAttendence()>=0);
		assertTrue(tester.getaTeamScore()>=0);
		assertFalse(tester.gethTeamScore()>=0);
}
}
