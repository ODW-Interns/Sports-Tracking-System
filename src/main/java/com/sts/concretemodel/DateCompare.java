package com.sts.concretemodel;

import java.util.Comparator;

/*
 * Class to allow comparisons to sort the key object by their date and time.
 * If the date and time are equal, then it will compare the two games's UID.
 */
public class DateCompare implements Comparator<Key>{

	public DateCompare() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Key key1_, Key key2_) {
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
			else
				return -1;
		}
	}

}
