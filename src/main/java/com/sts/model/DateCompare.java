package com.sts.model;

import java.util.Comparator;

/*
 * Class to allow comparisons to sort the key object by their date and time.
 * If the date and time are equal, then it will compare the away team's names.
 */
public class DateCompare implements Comparator<Key>{

	public DateCompare() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Key key1, Key key2) {
		// TODO Auto-generated method stub
		
		if(key1.getStartTime().isBefore(key2.getStartTime())) {
			return 1;
		}
		else if(key1.getStartTime().isAfter(key2.getStartTime())) {
			return -1;
		}
		else {
			return key1.getAwayTeamName().toString().compareTo(key2.getAwayTeamName().toString());
		}
	}

}
