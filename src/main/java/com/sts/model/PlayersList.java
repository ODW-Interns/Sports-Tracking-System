package com.sts.model;

import java.util.ArrayList;

public class PlayersList extends ArrayList<Player> {
	
	private static final long serialVersionUID = 1L;
	
	public void addPlayer(String first_, String last_, int jersey_) {
		
		add(new Player(first_, last_, jersey_));
	}


}


