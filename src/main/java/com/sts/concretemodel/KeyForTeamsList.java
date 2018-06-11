
package com.sts.concretemodel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class KeyForTeamsList {

	private String _teamName;
	//private String location;
	private String _sport;

	public KeyForTeamsList(String name_, String sport_) {
		_teamName = name_;
		_sport = sport_;
	
	}
	
	public String getTeamName() {
		return _teamName;
	}

	public String getSport() {
		return _sport;
	}

}