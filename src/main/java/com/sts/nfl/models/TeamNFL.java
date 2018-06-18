package com.sts.nfl.models;

import com.sts.abstractmodel.AbstractTeam;

/**
 * Concrete class extending from Team
 */

public class TeamNFL extends AbstractTeam {
	
	private final int _numbPlayers = 55;
	
	public TeamNFL() {		
	}
	
	public int getNumbPlayers() {
		return _numbPlayers;
	}

}
