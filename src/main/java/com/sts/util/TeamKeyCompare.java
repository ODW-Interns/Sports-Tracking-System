package com.sts.util;

import java.util.Comparator;

import com.sts.util.model.KeyForTeamsMap;

public class TeamKeyCompare implements Comparator<KeyForTeamsMap>{


	@Override
	public int compare(KeyForTeamsMap arg0, KeyForTeamsMap arg1) {
		int compareCity;
		int compareName;
		compareCity = arg0.get_city().compareTo(arg1.get_teamName());
		if(compareCity == 0) {
			compareName = arg0.get_teamName().compareTo(arg1.get_teamName());
			return compareName;
		}
		else
			return compareCity;
	}

}
