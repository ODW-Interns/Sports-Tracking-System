package com.sts.model;

import java.util.concurrent.ConcurrentHashMap;

public class PlayersList {
	
	private ConcurrentHashMap<Integer, Player> _playersMap;
	
	public void addPlayer(String first_, String last_, int jersey_) {
		
		//add(new Player(first_, last_, jersey_));
	}
	
	public ConcurrentHashMap<Integer, Player> returnPlayersMap(){
		return _playersMap;
	}

	

}


