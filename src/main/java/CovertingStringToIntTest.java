import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import control.RealTimeDataChecker;

public class CovertingStringToIntTest {

	@Test
	public void test() throws ParseException {
		Date formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("21/05/2018 1:00:00");
		assertEquals("Date has to match", formattedDate , RealTimeDataChecker.parseDateAndFormat("May 21 2018", "1:00"));
	}

}
