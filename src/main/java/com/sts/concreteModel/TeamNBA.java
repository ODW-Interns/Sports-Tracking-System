package com.sts.concreteModel;

//Concrete class extending from Team

public class TeamNBA extends com.sts.abstractModel.Team{
	
	private final int _numbPlayers = 12;
	
	public TeamNBA() {		
		
	}
	
	public int getNumbPlayers() {
		return _numbPlayers;
	}

}
