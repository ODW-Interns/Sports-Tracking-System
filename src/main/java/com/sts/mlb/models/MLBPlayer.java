package com.sts.mlb.models;

import com.sts.abstractmodel.AbstractPlayer;

/**
 * Concrete class extending from Player 
 */
public  class MLBPlayer extends AbstractPlayer {
	
	public MLBPlayer() {
		super();
	}
	public MLBPlayer(int ID_, int jersey_, String first_, String last_) {
		super(ID_, jersey_, first_, last_);
	}

}
