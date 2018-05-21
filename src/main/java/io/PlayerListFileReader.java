package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

import lists.TeamsList;
import model.Player;

public class PlayerListFileReader {

	private static final String DELIM = "|";
	
	public PlayerListFileReader() {
	
	}
	
    public void readData(InputStream is_, TeamsList listofTeams_) throws FileNotFoundException, RuntimeException {
        if (is_ == null)
            throw new FileNotFoundException();

        if (listofTeams_ == null)
            throw new RuntimeException("No list of teams provided");

        try (InputStreamReader sr = new InputStreamReader(is_)) {
            readFromRostersFileAndAddtoTeamsLists(sr, listofTeams_);
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }
    }
	
		
		public void readFromRostersFileAndAddtoTeamsLists(Reader is_, TeamsList listofTeams_) {
			try (BufferedReader reader = new BufferedReader(is_)) {
				StringTokenizer tokenizer;
				String line;
				Player player;
				
				int i = 0; // index for iterating through list of teams
				while ((line = reader.readLine()) != null) {
					// don't process empty lines
					if ("".equals(line))
						continue;

					
					tokenizer = new StringTokenizer(line, DELIM);

					while(tokenizer.hasMoreTokens()) {
						player = new Player();
						try {
							String jerseyNumString = tokenizer.nextToken();
							int jerseyNum= Integer.parseInt(jerseyNumString);
							player.setJerseyNum(jerseyNum);
						} catch (Exception e_) {
							System.err.println("Player Num: " + e_.toString());
							continue;
						}
						try {
							player.setFirstName(tokenizer.nextToken());
						} catch (Exception e_) {
							System.err.println("First Name: " + e_.toString());
							continue;
						}

						try {
							player.setLastName(tokenizer.nextToken());
						} catch (Exception e_) {
							System.err.println("Last Name: " + e_.toString());
							continue;
						}
				
						listofTeams_.get(i).getListOfPLayers().add(player);						
					}
					
					i++;
					//add a function to convert players number from string to int
				
					
				}
					
			}catch (Exception e_) {
				e_.printStackTrace();
			}
		
	}
}
