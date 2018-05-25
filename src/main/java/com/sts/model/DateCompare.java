package com.sts.model;

import java.util.Comparator;

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
