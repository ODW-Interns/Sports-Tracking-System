
package com.sts.abstractmodel;

import java.util.ArrayList;

public abstract class AbstractTeam implements InterfaceModel{

	private String _teamSport;
	private String _teamName;
	private String _location;
	
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
	
	public String getTeamSport() {
		return _teamSport;
	}

	public void setTeamSport(String teamSport_) {
		this._teamSport = teamSport_;
	}

	public ArrayList<Integer> getListOfPLayers() {
		return listofPlayers;
	}
	@Override
	public boolean isValid() {
		if(_teamName != null && _location != null && _teamSport != null) {
			return true;
		}
		else 
			return false;
	}

	public String fullTeamName() {
		
		String fullTeamName = _location+ " " +_teamName;
		
		return fullTeamName;
		
	}
	 @Override
	    public String toString() {
	        return "Team [Name = " + _teamName + " Location = "+
	    _location + " Team Sport=" +_teamSport +"]";
	    }
	 
	 @Override
	 	public boolean equals(Object obj) {
		 return _teamSport.equals(((AbstractTeam)obj).getTeamSport()) &&
				 _teamName.equals(((AbstractTeam)obj).getTeamName()) && 
				 _location.equals(((AbstractTeam)obj).getLocation());
	 }
}
