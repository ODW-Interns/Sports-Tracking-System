package com.sts.concretemodel;

import java.util.concurrent.ConcurrentHashMap;

import com.sts.abstractmodel.AbstractPlayer;

public class PlayersList {
	

	private ConcurrentHashMap<Integer, AbstractPlayer> PlayerMap_;

	public void addPlayer(int PlayerID_, int jerseyNum_, String firstName_, String lastName_) {

		
	}
	public PlayersList() {
		PlayerMap_ = new ConcurrentHashMap<Integer, AbstractPlayer>();
	}
	
	public ConcurrentHashMap<Integer, AbstractPlayer> returnPlayersMap(){
		return PlayerMap_;
	}

	

}


