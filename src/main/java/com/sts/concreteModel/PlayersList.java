package com.sts.concreteModel;

import java.util.concurrent.ConcurrentHashMap;

import com.sts.abstractModel.Player;

public class PlayersList {
	
	private ConcurrentHashMap<Integer, Player> _playersMap;
	
	public void addPlayer(int playerID_, String first_, String last_, int jersey_, String category_) {
		
		if(category_ == "NBA")
		_playersMap.put(playerID_, new NBAPlayer(first_, last_, jersey_));
	}
	
	public ConcurrentHashMap<Integer, Player> returnPlayersMap(){
		return _playersMap;
	}

	

}


