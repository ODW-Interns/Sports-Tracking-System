
package com.sts.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class KeyForTeamsList {

	private String teamName;
	//private String location;
	private String sport;

	public KeyForTeamsList(String name_, String sport_) {
		teamName = name_;
		sport = sport_;
	
	}
	
	public String getTeamName() {
		return teamName;
	}

	public String getSport() {
		return sport;
	}

}