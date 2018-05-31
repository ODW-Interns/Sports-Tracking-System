package com.sts.concreteModel;

import java.util.concurrent.ConcurrentHashMap;

import com.sts.abstractModel.Player;
import com.sts.abstractModel.Team;

public class PlayersList {
	
	private ConcurrentHashMap<Integer, Player> PlayerMap_;

	public void addPlayer(int PlayerID_, int jerseyNum_, String firstName_, String lastName_) {
		
		//add(new Player(first_, last_, jersey_));
	}
	
	public PlayersList() {
		PlayerMap_ = new ConcurrentHashMap<Integer, Player>();
	}
	
	public ConcurrentHashMap<Integer, Player> returnPlayersMap(){
		return PlayerMap_;
	}

	

}


