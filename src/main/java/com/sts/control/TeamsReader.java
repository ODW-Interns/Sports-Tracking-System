package com.sts.control;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.abstractmodel.SportsCategory;
import com.sts.mlb.models.TeamMLB;
import com.sts.nba.models.TeamNBA;
import com.sts.nfl.models.TeamNFL;
import com.sts.nhl.models.TeamNHL;
import com.sts.util.model.KeyForTeamsMap;

/**
 * The class will read from an input file and add to the list of teams
 */
	public class TeamsReader {
		private static final String DELIM = "|";
		private Logger _logger;
		private BufferedReader reader;

		public TeamsReader() {
	        reader = new BufferedReader(new InputStreamReader(System.in));
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
		
		
		
		public void readFromFileForLists(Reader is_, TeamsList listofTeams_) {
			try (BufferedReader reader = new BufferedReader(is_)) {
				StringTokenizer tokenizer;
				String line;
				SportsCategory category;
				AbstractTeam team = null;
				
				while ((line = reader.readLine()) != null) {
					// don't process empty lines
					if ("".equals(line))
						continue;
					tokenizer = new StringTokenizer(line, DELIM);

					try {
						category = SportsCategory.valueOf(tokenizer.nextToken());
					} catch (Exception e_) {
						continue;
					}
					
					try {
						if(category.equals(SportsCategory.valueOf("NBA")))
							team = new TeamNBA();
						else if(category.equals(SportsCategory.valueOf("NHL")))
							team = new TeamNHL();
						else if(category.equals(SportsCategory.valueOf("NFL")))
							team = new TeamNFL();
						else if(category.equals(SportsCategory.valueOf("MLB")))
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
			SportsCategory category = null;
			AbstractTeam team = null;
			
			if ("".equals(line))
				return;
			tokenizer = new StringTokenizer(line, DELIM);

			try {
				category = SportsCategory.valueOf(tokenizer.nextToken());
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

	    private void addTeam(AbstractTeam team_, TeamsList listOfTeams_)
	    {
	        if (!team_.isValid())
	        {
	            _logger.error("Refusing to add invalid team: {}", team_);
	            return;
	        }
	        
	        //
	        //Add game object to the list of games provided
	        //
	        listOfTeams_.getTeamMap().put(new KeyForTeamsMap(team_.getLocation(), team_.getTeamName()), team_);

	        if (_logger.isTraceEnabled())
	            _logger.trace("Adding new team to team map: {}", team_.toString());
	    
	    }
	    
	    public void createTeam(TeamsList listofTeams) throws IOException {
			SportsCategory category = null;
			AbstractTeam team = null;
			boolean isValid = false;
					
			do {
				_logger.info("Enter Sport of Player"); // User enter in which sport the team belongs
				try {
					category = SportsCategory.valueOf(reader.readLine());
					isValid=true;
					
				} catch (Exception e) {
					
					_logger.error("You have entered Invalid Category.");
					_logger.info("Please enter from the following "+java.util.Arrays.asList(SportsCategory.values()));
				} 
			} while (!isValid);
				
			//set team category
			  try {
	                if(category.equals(SportsCategory.valueOf("NBA"))) {
	                	team = new TeamNBA();
	                	team.setTeamSport(SportsCategory.valueOf("NBA"));
	                }
	                else if(category.equals(SportsCategory.valueOf("NFL"))) {
	                	team = new TeamNFL();
	                	team.setTeamSport(SportsCategory.valueOf("NFL"));
	                }
	                else if(category.equals(SportsCategory.valueOf("NHL"))) {
	                	team = new TeamNHL();
	                	team.setTeamSport(SportsCategory.valueOf("NHL"));
	                }
	                else if(category.equals(SportsCategory.valueOf("MLB"))) {
	                	team = new TeamMLB();
	                	team.setTeamSport(SportsCategory.valueOf("MLB"));
	                }
	                else
	                	throw new Exception("Invalid category.");
            }
            catch(Exception e_) {
            	_logger.error("Failed to initialize Team:" + e_.toString());
            }
			  
			  //promt for new team location
			  _logger.info("Enter Team Location");
			  
			  //set team location
			  try {
				  team.setLocation(reader.readLine());
			  }
			  catch (Exception e_){
				  _logger.error("Entering Location" + e_.toString());
			  }
			  
			  //promt for new team name
			  _logger.info("Enter Team Name");
			  
			  //set team name
			  try {
				  team.setTeamName(reader.readLine());
			  }
			  catch (Exception e_) {
				  _logger.error("Entering Team Name" + e_.toString());
			  }
			
			  if (!team.isValid())
		        {
		            _logger.error("Refusing to add invalid team: {}", team);
		            return;
		        }
		     
			  //checking if the team is already exists in the system
			   
			  if(listofTeams.getTeamMap().containsValue(team)) {
				try {
					throw new Exception ("Team already exists");
				} catch (Exception e_) {
					_logger.error("Error" + e_.toString());
				}
				return;
			  } 
			
			  
		        //
		        //Add game object to the list of games provided
		        //
		        listofTeams.getTeamMap().put(new KeyForTeamsMap(team.getLocation(), team.getTeamName()), team);

		        if (_logger.isTraceEnabled())
		            _logger.trace("Adding new team to team map: {}", team.toString());
		    

		}
	
	}
	
	
