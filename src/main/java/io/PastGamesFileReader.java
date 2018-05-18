package io;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.StringTokenizer;

import lists.GamesList;
import model.Game;

//class to read from file with past/finished games
public class PastGamesFileReader extends AbstractGameReader {

	private static final String DELIM = "|";
	
	/** 
	 * (non-Javadoc)
	 */
	@Override
	public void readFromFileWithGames(Reader is_, GamesList listofGames_) {
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

				//Read in the date and set date in game object
				try {
					game.setDate(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setDate:" + e_.toString());
					continue;
				}

				//Read in the time and set time in game object
				try {
					game.setTime(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setTime:" + e_.toString());
					continue;
				}

				//Read in the away team and set in game object
				try {
					game.setaTeam(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setaTeam:" + e_.toString());
					continue;
				}

				//Read in the away team's score for the game and set in game object
				try {
					String awayScoreString = tokenizer.nextToken();
					int aTScore = Integer.parseInt(awayScoreString);
					game.setaTeamScore(aTScore);
				} catch (Exception e_) {
					System.err.println("setaTeamScore:" + e_.toString());
				}

				//Read in the home team and set in game object
				try {
					game.sethTeam(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("sethTeam:" + e_.toString());
				}
				
				//Read in the home team's score for the game set in game object
				try {
					String homeScoreString = tokenizer.nextToken();
					int homeScore = Integer.parseInt(homeScoreString);
					game.sethTeamScore(homeScore);
				} catch (Exception e_) {
					System.err.println("sethTeamScore:" + e_.toString());
				}

				//Read in the game attendance and set in game object
				try {
					String attendanceString = tokenizer.nextToken();
					attendanceString = attendanceString.replace(",", "");
					int attend = Integer.parseInt(attendanceString);
					game.setAttendence(attend);
				} catch (Exception e_) {
					System.err.println("setAttendance:" + e_.toString());
				}
 
				//Add game object to the list of games provided
				listofGames_.add(game);
			}
		} catch (Exception e_) {
			e_.printStackTrace();
		}
		
	}
	
}
