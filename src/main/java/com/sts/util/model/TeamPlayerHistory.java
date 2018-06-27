package com.sts.util.model;

import java.util.Date;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;

/**
 * This class contains connection between team and player and player's engagement.
 */
public class TeamPlayerHistory {
	// The player involved in the connection
	private AbstractPlayer player; 
	
	//The team involved in the connection
	private AbstractTeam team;
	
	//The date the player started on this team
	private Date startDate;
	
	//The date the player was removed from this team
	//Will be null if the player is still currently on the team
	private Date endDate;
	
	//This contains information if player is still active in team.
	private boolean status;
	
	//Default Constructor
	public TeamPlayerHistory() {
		super();
	}
	
	//Constructor
	public TeamPlayerHistory(AbstractPlayer player_, AbstractTeam team_, Date startDate_, boolean status_) {
		player = player_;
		team = team_;
		startDate = startDate_;
		status = status_;
	}
	
	//Get the player of the connection
	public AbstractPlayer getPlayer() {
		return player;
	}
	
	//Set the player in the connection
	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}
	
	//get the team in the connection
	public AbstractTeam getTeam() {
		return team;
	}
	
	//set the team in the connection
	public void setTeam(AbstractTeam team) {
		this.team = team;
	}
	
	//get the starting date of the player on the team
	public Date getStartDate() {
		return startDate;
	}
	
	//set the start date of the player on the team
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	
	//get the end date of the player with this team
	public Date getEndDate() {
		return endDate;
	}
	
	//set the end date of the player on this team
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	//returns true if the player is currently on this team; false otherwise
	public boolean isStatus() {
		return status;
	}
	
	//sets the status of the player on this team or not
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	//Method used to compare two of TeamPlayer objects
	@Override
	public boolean equals(Object obj) {
		return this.player.equals(((TeamPlayerHistory)obj).getPlayer()) &&
				this.startDate.equals(((TeamPlayerHistory)obj).getStartDate()) &&
				this.team.equals(((TeamPlayerHistory)obj).getTeam());
	}
	
}

