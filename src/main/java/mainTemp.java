

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import IdentityClasses.Game;
import IdentityClasses.Team;

import java.io.IOException;
import java.io.InputStream;

import IOClasses.FileReader;
import IOClasses.GamesList;

import IOClasses.NBAFileReader;

import IOClasses.TeamListFileReader;
import IOClasses.TeamsList;


public class mainTemp {

	private static final Logger logger = LoggerFactory.getLogger(mainTemp.class);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		GamesList listofGames = new GamesList();
		String filename = "16-17_NBA_RegSzn.txt";
		NBAFileReader in = new NBAFileReader();
		InputStream is = mainTemp.class.getResourceAsStream(filename);
		in.readData(is, listofGames);
		is.close();
		
		
		TeamsList listofTeams = new TeamsList();
		String file2name = "NBA_Teams.txt";
		TeamListFileReader in2 = new TeamListFileReader();
		InputStream is2 = mainTemp.class.getResourceAsStream(file2name);
		in2.readData(is2, listofTeams);
		is2.close();
		
		System.out.println(listofTeams.get(7).getLocation());
		System.out.println(listofTeams.get(7).getNum_players());
		System.out.println(listofTeams.get(7).getTeamName());


		System.out.println("TEST PRINT");
		for (int i = 0; i < listofGames.size(); i++) {
			logger.info(listofGames.get(i).getDate());
			logger.info(listofGames.get(i).getTime());
			logger.info(listofGames.get(i).getaTeam());
			// logger.info(listofGames.get(i).getaTeamScore());
			logger.info(listofGames.get(i).gethTeam());
			// System.out.print(listofGames.get(i).gethTeamScore());
			// System.out.print(listofGames.get(i).getAttendence());
			// System.out.println();

		}	
	}
}
