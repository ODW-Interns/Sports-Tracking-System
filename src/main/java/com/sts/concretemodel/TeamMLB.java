package com.sts.concretemodel;

import com.sts.model.Team;

//Concrete class extending from Team
public class TeamMLB extends Team{

	private final int  _numbPlayers=25;

	public TeamMLB() {
	}

	
	public int getNumbPlayers() {
		return _numbPlayers;
	}
}
