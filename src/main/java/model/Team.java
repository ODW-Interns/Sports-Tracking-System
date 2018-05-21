package model;

import lists.PlayersList;

public class Team {

	private String teamName;
	private String location;
	private int numPlayers;
	private PlayersList listofPlayers;
	
	public Team() {
		// Default Constructor
		this("general", "general", 0);
	}

	//Constructor
	public Team(String team, String loc, int players) {
		setTeamName(team);
		setLocation(loc);
		setNumPlayers(players);
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

	/*
	 * method to retrieve the number of players on the team
	 */
	
	public int getNum_players() {
		return numPlayers;
	}

	/*
	 * method to set the number of players on a team
	 */
	public void setNumPlayers(int players) {
		numPlayers = players;
	}
	
	public PlayersList getListOfPLayers() {
		return listofPlayers;
	}

	 @Override
	    public String toString() {
	        return "Team [Name = " + teamName + " Location = "+
	    location +" # of Players = " + numPlayers+ "]";
	    	}
}
