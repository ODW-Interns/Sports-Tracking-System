package com.sts.concretemodel;

import java.util.Date;

import com.sts.abstractmodel.AbstractPlayer;
import com.sts.abstractmodel.AbstractTeam;

/**
 * This class contains connection between team and player and player's engagement.
 */
public class TeamPlayer {
	private AbstractPlayer player;
	private AbstractTeam team;
	private Date startDate;
	private Date endDate;
	/**
	 * This contains information if player is still active in team.
	 */
	private boolean status;
	
	public TeamPlayer() {
		super();
	}
	
	public TeamPlayer(AbstractPlayer player_, AbstractTeam team_, Date startDate_, boolean status_) {
		player = player_;
		team = team_;
		startDate = startDate_;
		status = status_;
	}
	
	public AbstractPlayer getPlayer() {
		return player;
	}
	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}
	public AbstractTeam getTeam() {
		return team;
	}
	public void setTeam(AbstractTeam team) {
		this.team = team;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.player.equals(((TeamPlayer)obj).getPlayer()) &&
				this.startDate.equals(((TeamPlayer)obj).getStartDate()) &&
				this.team.equals(((TeamPlayer)obj).getTeam());
	}
	
}

