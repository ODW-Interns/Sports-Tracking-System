package com.sts.control;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractModel.Team;
import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.TeamMLB;
import com.sts.concreteModel.TeamNBA;
import com.sts.concreteModel.TeamNFL;
import com.sts.concreteModel.TeamNHL;
import com.sts.concreteModel.TeamsList;


	public class TeamsFileReader {
		private static final String DELIM = "|";
		private Logger _logger;
		
		public TeamsFileReader() {
			
			 _logger = LoggerFactory.getLogger(getClass().getSimpleName());
		}
			    
		  public void readData(InputStream is_, GamesList gamelist_, TeamsList teamlist_ ) throws FileNotFoundException, RuntimeException {
		        if (is_ == null)
		            throw new FileNotFoundException();

		        if (gamelist_ == null)
		            throw new RuntimeException("No list provided");
		        if(teamlist_ == null)
		        	throw new RuntimeException("No list provided");

		        try (InputStreamReader sr = new InputStreamReader(is_)) {
		            readFromFileForLists(sr, teamlist_);
		       
		        }
		        catch (Exception e_) {
		            e_.printStackTrace();
		        }
		    }
		
		
		//method to read from an input file and add to the list of teams
		public void readFromFileForLists(Reader is_, TeamsList listofTeams_) {
			try (BufferedReader reader = new BufferedReader(is_)) {
				StringTokenizer tokenizer;
				String line;
				String category;
				Team team = null;
				
				while ((line = reader.readLine()) != null) {
					// don't process empty lines
					if ("".equals(line))
						continue;
					tokenizer = new StringTokenizer(line, DELIM);

					try {
						category = tokenizer.nextToken();
					} catch (Exception e_) {
						System.err.println("Reading Sport Category:" + e_.toString());
						continue;
					}
					
					try {
						if(category.equals("NBA"))
							team = new TeamNBA();
						else if(category.equals("NHL"))
							team = new TeamNHL();
						else if(category.equals("NFL"))
							team = new TeamNFL();
						else if(category.equals("MLB"))
							team = new TeamMLB();
					}
					catch(Exception e_) {
						_logger.error("Failed to initialize team:" + e_.toString());
						continue;
					}

					try {
						team.setTeamSport(category);
					}
					catch(Exception e_) {
						_logger.error("setTeamSport:" + e_.toString());
					}
					try {
						team.setLocation(tokenizer.nextToken());
					} catch (Exception e_) {
						_logger.error("setLocation:" + e_.toString());
						continue;
					}

					try {
						team.setTeamName(tokenizer.nextToken());
					} catch (Exception e_) {
						_logger.error("setTeamName:" + e_.toString());
						continue;
					}
				
					
					//adding to hash table here
					addTeam(team, listofTeams_);
					
					
				}
					
			}catch (Exception e_) {
				e_.printStackTrace();
			}
		}
		
		public void readFromStringforList(String line, TeamsList listofTeams_) {
			StringTokenizer tokenizer = new StringTokenizer(line, "|");
			String category = "";
			Team team = null;
			
			if ("".equals(line))
				return;
			tokenizer = new StringTokenizer(line, DELIM);

			try {
				category = tokenizer.nextToken();
			} catch (Exception e_) {
				System.err.println("Reading Sport Category:" + e_.toString());
			}
			
			try {
				if(category.equals("NBA"))
					team = new TeamNBA();
				else if(category.equals("NHL"))
					team = new TeamNHL();
				else if(category.equals("NFL"))
					team = new TeamNFL();
				else if(category.equals("MLB"))
					team = new TeamMLB();
			}
			catch(Exception e_) {
				_logger.error("Failed to initialize team:" + e_.toString());
			}

			try {
				team.setTeamSport(category);
			}
			catch(Exception e_) {
				_logger.error("setTeamSport:" + e_.toString());
			}
			try {
				team.setLocation(tokenizer.nextToken());
			} catch (Exception e_) {
				_logger.error("setLocation:" + e_.toString());
			}

			try {
				team.setTeamName(tokenizer.nextToken());
			} catch (Exception e_) {
				_logger.error("setTeamName:" + e_.toString());
			}
		
			
			//adding to hash table here
			addTeam(team, listofTeams_);
		}

	    private void addTeam(Team team_, TeamsList listOfTeams_)
	    {
	        if (!team_.isValidTeam())
	        {
	            _logger.error("Refusing to add invalid team: {}", team_);
	            return;
	        }
	        
	        //
	        //Add game object to the list of games provided
	        //
	        listOfTeams_.getTeamMap().put(team_.fullTeamName(), team_);

	        if (_logger.isTraceEnabled())
	            _logger.trace("Adding new team to team map: {}", team_.toString());
	    
	    }
		
	}
	
	
