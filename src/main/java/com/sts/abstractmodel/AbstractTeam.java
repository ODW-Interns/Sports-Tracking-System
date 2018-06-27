
package com.sts.abstractmodel;

import java.util.ArrayList;
import java.util.Iterator;

import com.sts.util.model.TeamPlayerHistory;


/**
 * Abstract class to represent a team for a sport
 */

public abstract class AbstractTeam implements InterfaceModel{

	//The sport of the team
	private SportsCategory _teamSport;
	//The name(mascot) of the team
	private String _teamName;
	//The city of the team
	private String _location;
	
	/*
	 * This is a list that contains the entire history of team
	 * (All the players that have played on this team)
	 * TeamPlayer: Data object that connects a team and a player
	 */
	private ArrayList<TeamPlayerHistory> teamPlayers;
	
	
	public AbstractTeam() {
		// Default Constructor
		this("general", "general");
	}

	//Constructor
	public AbstractTeam(String team_, String loc_) {
		setTeamName(team_);
		setLocation(loc_);
	
		teamPlayers = new ArrayList<TeamPlayerHistory>();
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
	public SportsCategory getTeamSport() {
		return _teamSport;
	}

	/*
	 * method to set the team's sport
	 */
	public void setTeamSport(SportsCategory category) {
		this._teamSport = category;
	}

	/*
	 * method to check if the required fields for the team has been initialized
	 */
	@Override
	public boolean isValid() {
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
	 * Returns the current players on this team by filtering through the entire history
	 * and grabbing only players that are on this team
	 */
	public ArrayList<TeamPlayerHistory> getCurrentPlayers() {
		ArrayList<TeamPlayerHistory> filteredPlayersList = new ArrayList<>();
		// TODO filter team players by status/enddate.
		Iterator<TeamPlayerHistory> i;
		i = teamPlayers.iterator();
		TeamPlayerHistory tempTeamPlayer = null;
		while(i.hasNext()) {
			tempTeamPlayer = i.next();
			if(tempTeamPlayer.isStatus() == true && tempTeamPlayer.getTeam().equals(this)) {
				filteredPlayersList.add(tempTeamPlayer);
			}
			
		}
		return filteredPlayersList; 
	}
	
	//Returns the entire history of the team
	public ArrayList<TeamPlayerHistory> getEntireHistoryPlayers(){
		return teamPlayers;
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
