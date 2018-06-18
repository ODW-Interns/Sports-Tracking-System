package com.sts.mlb.models;

import com.sts.abstractmodel.AbstractTeam;

/**
 * Concrete class extending from Team
 */
public class TeamMLB extends AbstractTeam{

	private final int  _numbPlayers=25;

	public TeamMLB() {
	}

	
	public int getNumbPlayers() {
		return _numbPlayers;
	}
	
}
