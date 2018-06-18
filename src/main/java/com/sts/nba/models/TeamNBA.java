package com.sts.nba.models;

/**
 * Concrete class extending from Team
 */

public  class TeamNBA extends com.sts.abstractmodel.AbstractTeam{
	
	private final int _numbPlayers = 12;
	
	public TeamNBA() {		
		
	}
	
	public int getNumbPlayers() {
		return _numbPlayers;
	}


}
