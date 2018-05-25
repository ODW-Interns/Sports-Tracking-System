package com.sts.control;

import java.util.Comparator;

import com.sts.model.Key;

public class DateComp implements Comparator<Key> {

	@Override
	public int compare(Key key1, Key key2) {
		if(key1.getDate().isAfter(key2.getDate()))
			return 1;
		else if(key1.getDate().isBefore(key2.getDate()))
			return -1;
		else
			return 0;
	}

}
