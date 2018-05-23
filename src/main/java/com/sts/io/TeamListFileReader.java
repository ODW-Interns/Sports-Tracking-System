package com.sts.io;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.sts.model.Team;
import com.sts.model.TeamsList;


public class TeamListFileReader extends AbstractFileReader {
	
	private static final String DELIM = "|";
	
	public TeamListFileReader() {}
	
	@Override
	//method to read from an input file and add to the list of teams
	public void readFromFileForLists(Reader is_, ArrayList listofTeams_) {
		try (BufferedReader reader = new BufferedReader(is_)) {
			StringTokenizer tokenizer;
			String line;
			
			while ((line = reader.readLine()) != null) {
				// don't process empty lines
				if ("".equals(line))
					continue;

				Team team = new Team();
				tokenizer = new StringTokenizer(line, DELIM);

				try {
					team.setLocation(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setLocation:" + e_.toString());
					continue;
				}

				try {
					team.setTeamName(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setTeamName:" + e_.toString());
					continue;
				}
				((TeamsList)listofTeams_).add(team);
			}
				
		}catch (Exception e_) {
			e_.printStackTrace();
		}
	}
}
		
	
		

