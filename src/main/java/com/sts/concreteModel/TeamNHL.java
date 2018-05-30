package com.sts.concreteModel;

import com.sts.abstractModel.Team;

//Concrete class extending from Team

public class TeamNHL extends Team {

	private final int _numbPlayers=20;
	
	
	public TeamNHL() {

	}
	
	
	public int getNumbPlayers() {
		return _numbPlayers;
	}
	
}
