package com.sts.concretemodel;

import com.sts.model.Team;

//Concrete class extending from Team

public class TeamNFL extends Team {
	
	private final int _numbPlayers = 55;
	
	public TeamNFL() {		
	}
	
	public int getNumbPlayers() {
		return _numbPlayers;
	}

}
