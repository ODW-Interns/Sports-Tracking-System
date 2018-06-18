package com.sts.nhl.models;

import com.sts.abstractmodel.AbstractTeam;

/**
 * Concrete class extending from Team
 */

public class TeamNHL extends AbstractTeam {

	private final int _numbPlayers=20;
	
	
	public TeamNHL() {

	}
	
	
	public int getNumbPlayers() {
		return _numbPlayers;
	}
	
}
