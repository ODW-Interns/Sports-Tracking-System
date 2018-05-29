package com.sts.concretemodel;

import com.sts.model.Team;

public class TeamNFL extends Team {
	
	private int numbPlayers;
	
	public TeamNFL() {
		// TODO Auto-generated constructor stub
		
	}
	public void setNumbPlayers(int n) {
		numbPlayers = n;
	}
	
	public int getNumbPlayers() {
		return numbPlayers;
	}

}
