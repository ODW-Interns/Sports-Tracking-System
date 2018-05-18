package io;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.StringTokenizer;

import lists.PlayersList;
import model.Player;
import model.Team;

public class PlayerListFileReader {

	private static final String DELIM = "|";
	
	public PlayerListFileReader() {
	
	}
	
		
		public void readFromFileAndAddtoList(Reader is_, PlayersList listofPlayers_) {
			try (BufferedReader reader = new BufferedReader(is_)) {
				StringTokenizer tokenizer;
				String line;
				
				while ((line = reader.readLine()) != null) {
					// don't process empty lines
					if ("".equals(line))
						continue;

					Player player = new Player();
					tokenizer = new StringTokenizer(line, DELIM);

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
					//add a function to convert players number from string to int
					try {
						player.setJerseyNum(tokenizer.nextToken());
					} catch (Exception e) {
						System.err.println("Player Num: " + e_.toString());
						continue;
					}
					listofPlayers_.add(player);
				}
					
			}catch (Exception e_) {
				e_.printStackTrace();
			}
		
	}
}
