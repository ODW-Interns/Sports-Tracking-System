
package com.sts.model;

public abstract class Team {

	private String teamSport;
	private String teamName;
	private String location;
	
	private PlayersList listofPlayers;
	

	public Team() {
		// Default Constructor
		this("general", "general");
	}

	//Constructor
	public Team(String team, String loc) {
		setTeamName(team);
		setLocation(loc);
	
		listofPlayers = new PlayersList();
	}

	/*
	 * method to retrieve team name
	 */
	public String getTeamName() {
		return teamName;
	}

	/*
	 * method to set team name
	 */
	public void setTeamName(String team) {
		teamName = team;
	}

	/*
	 * method to retrieve location of the team
	 */
	public String getLocation() {
		return location;
	}

	/*
	 * method to set the team location
	 */
	public void setLocation(String loc) {
		location = loc;
	}
	
	public String getTeamSport() {
		return teamSport;
	}

	public void setTeamSport(String teamSport) {
		this.teamSport = teamSport;
	}

	public PlayersList getListOfPLayers() {
		return listofPlayers;
	}
	
	public boolean isValidTeam() {
		if(teamName != null && location != null && teamSport != null) {
			return true;
		}
		else 
			return false;
	}

	 @Override
	    public String toString() {
	        return "Team [Name = " + teamName + " Location = "+
	    location + " Team Sport=" +teamSport +"]";
	    	}
}
