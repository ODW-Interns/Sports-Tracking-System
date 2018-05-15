package IdentityClasses;

public class Team {

	private String teamName; 
	private String location;
	private int numPlayers;
	
	public Team() {
		// Call Constructor
		this("general", "general", 0);
	}
	
	public Team(String team, String loc, int players){
		setTeamName(team);
		setLocation(loc);
		setNumPlayers(players);
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String team) {
		teamName = team;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String loc) {
		location = loc;
	}

	public int getNum_players() {
		return numPlayers;
	}

	public void setNumPlayers(int players) {
		numPlayers = players;
	}
	
	
	

}
