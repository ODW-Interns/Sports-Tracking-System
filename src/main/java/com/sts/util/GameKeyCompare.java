package com.sts.util;

import java.util.Comparator;

import com.sts.util.model.KeyForGamesMap;

/**
 * Class to allow comparisons to sort the key object by their date and time.
 * If the date and time are equal, then it will compare the two games's UID.
 */
public class GameKeyCompare implements Comparator<KeyForGamesMap>{

	@Override
	public int compare(KeyForGamesMap key1_, KeyForGamesMap key2_) {
		// TODO Auto-generated method stub
		
		if(key1_.getStartTime().isBefore(key2_.getStartTime())) {
			return 1;
		}
		else if(key1_.getStartTime().isAfter(key2_.getStartTime())) {
			return -1;
		}
		else {
			if(key1_.getGameUID() < key2_.getGameUID()) {
				return 1;
			}
			else if(key1_.getGameUID() == key2_.getGameUID()){
				return 0;
			}
			else
				return -1;
		}
	}

}
