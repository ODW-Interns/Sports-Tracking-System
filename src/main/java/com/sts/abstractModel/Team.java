
package com.sts.abstractModel;

import com.sts.concreteModel.PlayersList;

public abstract class Team {

	private String _teamSport;
	private String _teamName;
	private String _location;
	
	private PlayersList listofPlayers;
	

	public Team() {
		// Default Constructor
		this("general", "general");
	}

	//Constructor
	public Team(String team_, String loc_) {
		setTeamName(team_);
		setLocation(loc_);
	
		listofPlayers = new PlayersList();
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
	
	public String getTeamSport() {
		return _teamSport;
	}

	public void setTeamSport(String teamSport_) {
		this._teamSport = teamSport_;
	}

	public PlayersList getListOfPLayers() {
		return listofPlayers;
	}
	
	public boolean isValidTeam() {
		if(_teamName != null && _location != null && _teamSport != null) {
			return true;
		}
		else 
			return false;
	}

	public String fullTeamName() {
		
		String fullTeamName = _location+_teamName;
		
		return fullTeamName;
		
	}
	 @Override
	    public String toString() {
	        return "Team [Name = " + _teamName + " Location = "+
	    _location + " Team Sport=" +_teamSport +"]";
	    	}
}
