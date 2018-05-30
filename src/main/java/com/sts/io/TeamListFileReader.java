package com.sts.io;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.model.Game;
import com.sts.model.Key;
import com.sts.model.KeyForTeamsList;
import com.sts.model.Team;
import com.sts.model.TeamsList;


public class TeamListFileReader  {
	
	private static final String DELIM = "|";
	private Logger _logger;
	
	public TeamListFileReader() {
		
		 _logger = LoggerFactory.getLogger(getClass().getSimpleName());
		
		    
	}
	
	
	//method to read from an input file and add to the list of teams
	public void readFromFileForLists(Reader is_, AbstractMap<KeyForTeamsList, Team> teamMap_) {
		/*try (BufferedReader reader = new BufferedReader(is_)) {
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
				try {
					team.setTeamSport(tokenizer.nextToken());
				} catch (Exception e_) {
					System.err.println("setTeamSport:" + e_.toString());
					continue;
				}
				
				//adding to hash table here
				addTeam(team, (ConcurrentHashMap<KeyForTeamsList, Team>) teamMap_);
				
				
			}
				
		}catch (Exception e_) {
			e_.printStackTrace();
		}
	}

    private void addTeam(Team team_, ConcurrentHashMap<KeyForTeamsList, Team> listOfTeams_)
    {
        if (!team_.isValidTeam())
        {
            _logger.error("Refusing to add invalid game: {}", team_);
            return;
        }
        
        //
        //Add game object to the list of games provided
        //
        listOfTeams_.put(new KeyForTeamsList(team_.getTeamName(), team_.getTeamSport()), team_);

        if (_logger.isTraceEnabled())
            _logger.trace("Adding new game to past list: {}", team_.toString());
    }*/
}
}
		
	
		

