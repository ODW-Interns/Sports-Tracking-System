package GameTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Game;

public class DuplicatesTesting {

	@Test
	public void testDuplicates() {
		//fail("Not yet implemented");
		Game tester = new Game(); 
		tester.setaTeam("Maccabi Tel-Aviv");
		tester.sethTeam("Maccabi Tel-Aviv");

		assertFalse(tester.getaTeam()!=tester.gethTeam());
	}

}
