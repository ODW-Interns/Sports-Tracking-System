package com.sts.control;

import java.util.concurrent.ConcurrentHashMap;

import com.sts.abstractmodel.AbstractPlayer;

/**
 * class to hold structure(Concurrent Hash Map) for mapping players
 */
public class PlayersList {
	

	private ConcurrentHashMap<Integer, AbstractPlayer> PlayerMap_;

	//Constructor
	public PlayersList() {
		PlayerMap_ = new ConcurrentHashMap<Integer, AbstractPlayer>();
	}
	
	// Return: Players Map
	public ConcurrentHashMap<Integer, AbstractPlayer> returnPlayersMap(){
		return PlayerMap_;
	}

	

}


