package io;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.StringTokenizer;

import model.Game;

public class NBAPastGamesFileReader extends AbstractGameReader {

	private static final String DELIM = "|";
	
	/** 
	 * (non-Javadoc)
	 */
	@Override
	public void readFromFileWithPreviousGames(Reader is_, GamesList listofGames_) {
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
					String awayScoreString = tokenizer.nextToken();
					int aTScore = Integer.parseInt(awayScoreString);
					game.setaTeamScore(aTScore);
				} catch (Exception e_) {
					System.err.println("setaTeamScore:" + e_.toString());
				}

				try {
					game.sethTeam(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("sethTeam:" + e_.toString());
				}
				try {
					String homeScoreString = tokenizer.nextToken();
					int homeScore = Integer.parseInt(homeScoreString);
					game.setaTeamScore(homeScore);
				} catch (Exception e_) {
					System.err.println("sethTeamScore:" + e_.toString());
				}

				try {
					String attendanceString = tokenizer.nextToken();
					attendanceString = attendanceString.replace(",", "");
					int attend = Integer.parseInt(attendanceString);
					game.setAttendence(attend);
				} catch (Exception e_) {
					System.err.println("setAttendance:" + e_.toString());
				}
 
				listofGames_.add(game);
			}
		} catch (Exception e_) {
			e_.printStackTrace();
		}
		
	}
}
