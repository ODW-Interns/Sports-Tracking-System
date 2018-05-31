package com.sts.concreteModel;

import com.sts.abstractModel.Player;

//Concrete class extending from Player 
public class MLBPlayer extends Player {
	
	public MLBPlayer() {
		super();
	}
	public MLBPlayer(int ID_, int jersey_, String first_, String last_) {
		super(ID_, jersey_, first_, last_);
	}

}
