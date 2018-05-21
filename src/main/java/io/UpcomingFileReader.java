package io;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.StringTokenizer;

import lists.GamesList;
import model.Game;

public class UpcomingFileReader extends AbstractGameReader {
	
	private static final String DELIM ="|";
	
	
	
	public void readFromFileWithGames(Reader is_, GamesList listofUpcoming_) {
		try (BufferedReader reader = new BufferedReader(is_)) {
			StringTokenizer tokenizer;
			String line;
			Game game;
			while ((line = reader.readLine()) != null) {
				// don't process empty lines
				if ("".equals(line))
					continue;

				game = new Game();
				tokenizer = new StringTokenizer(line, DELIM);

				try {
					game.setDate(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setDate:" + e_.toString());
					continue;
				}

				try {
					game.setTime(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setTime:" + e_.toString());
					continue;
				}

				try {
					game.setaTeam(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setaTeam:" + e_.toString());
					continue;
				}

				
				try {
					game.sethTeam(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("sethTeam:" + e_.toString());
				}
				

				
 
				listofUpcoming_.add(game);
			}
		} catch (Exception e_) {
			e_.printStackTrace();
		}
		
	}

}
