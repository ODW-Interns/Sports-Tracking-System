package IOClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

import IdentityClasses.Game;
import IdentityClasses.Team;


public class TeamListFileReader {
	
	private static final String DELIM = ",";
	
	public TeamListFileReader() {
	
	}

	public void readData(InputStream is_, TeamsList listofTeams_) throws FileNotFoundException, RuntimeException {
		if (is_ == null)
			throw new FileNotFoundException();
		
		if (listofTeams_ == null)
			throw new RuntimeException("No catalog provided");
		
		try (InputStreamReader sr = new InputStreamReader(is_)) {
			readFromFileAndAddtoList(sr, listofTeams_);
		} catch (Exception e_) {
			e_.printStackTrace();
		}
	}
		
	
	public void readFromFileAndAddtoList(Reader is_, TeamsList listofTeams_) {
		try (BufferedReader reader = new BufferedReader(is_)) {
			StringTokenizer tokenizer;
			String line;
			Team team;
			while ((line = reader.readLine()) != null) {
				// don't process empty lines
				if ("".equals(line))
					continue;

				team = new Team();
				tokenizer = new StringTokenizer(line, DELIM);

				try {
					team.setLocation(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setDate:" + e_.toString());
					continue;
				}

				try {
					team.setTeamName(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setTime:" + e_.toString());
					continue;
				}
			}
				listofTeams_.add(team);
		}catch (Exception e_) {
			e_.printStackTrace();
		}
	}
}
		
	
		

