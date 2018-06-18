package com.sts.nfl.models;

import com.sts.abstractmodel.AbstractPlayer;

public  class NFLPlayer extends AbstractPlayer{
	
	public NFLPlayer() {
		super();
	}
	
	public NFLPlayer(int ID_, int jersey_, String first_, String last_) {
		super(ID_, jersey_, first_, last_);
	}

}
