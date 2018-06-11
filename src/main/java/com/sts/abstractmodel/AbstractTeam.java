
package com.sts.abstractmodel;

import java.util.ArrayList;

/*
 * Abstract class to represent a team for a sport
 */
public abstract class AbstractTeam {

	//The sport of the team
	private String _teamSport;
	//The name(mascot) of the team
	private String _teamName;
	//The city of the team
	private String _location;
	
	//The current team's roster represented by a list of player IDs
	private ArrayList<Integer> listofPlayers;
	
	public AbstractTeam() {
		// Default Constructor
		this("general", "general");
	}

	//Constructor
	public AbstractTeam(String team_, String loc_) {
		setTeamName(team_);
		setLocation(loc_);
	
		listofPlayers = new ArrayList<Integer>();
	}

	/*
	 * method to retrieve team name
	 */
	public String getTeamName() {
		return _teamName;
	}

	/*
	 * method to set team name
	 */
	public void setTeamName(String team_) {
		_teamName = team_;
	}

	/*
	 * method to retrieve location of the team
	 */
	public String getLocation() {
		return _location;
	}

	/*
	 * method to set the team location
	 */
	public void setLocation(String loc_) {
		_location = loc_;
	}
	
	/*
	 * Return: the sport of the team
	 */
	public String getTeamSport() {
		return _teamSport;
	}

	/*
	 * method to set the team's sport
	 */
	public void setTeamSport(String teamSport_) {
		this._teamSport = teamSport_;
	}

	/*
	 * Return: the current roster of the team
	 */
	public ArrayList<Integer> getListOfPLayers() {
		return listofPlayers;
	}
	
	/*
	 * method to check if the required fields for the team has been initialized
	 */
	public boolean isValidTeam() {
		if(_teamName != null && _location != null && _teamSport != null) {
			return true;
		}
		else 
			return false;
	}
	
	/*
	 * Return: String of both location and team name
	 */
	public String fullTeamName() {
		
		String fullTeamName = _location+ " " +_teamName;
		
		return fullTeamName;
		
	}
	
	/*
	 * Return: String of team's info
	 * (non-Javadoc)
	 */
	 @Override
	    public String toString() {
	        return "Team [Name = " + _teamName + " Location = "+
	    _location + " Team Sport=" +_teamSport +"]";
	    }
	 
	 /*
	  * Returns true if the two team objects being compared are equal to each other
	  * (non-Javadoc)
	  */
	 @Override
	 	public boolean equals(Object obj) {
		 return _teamSport.equals(((AbstractTeam)obj).getTeamSport()) &&
				 _teamName.equals(((AbstractTeam)obj).getTeamName()) && 
				 _location.equals(((AbstractTeam)obj).getLocation());
	 }
}
