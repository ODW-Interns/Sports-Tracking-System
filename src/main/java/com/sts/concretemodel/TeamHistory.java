package com.sts.concretemodel;

import java.util.Date;

/**
 * Class for storing player's team history  including dates
 */
public class TeamHistory {

	String _TeamCity;
	String _TeamName;
	Date _StartDate;
	Date _EndDate;
	
	public TeamHistory(String TeamCity_, String TeamName_) {
		_TeamCity = TeamCity_;
		_TeamName = TeamName_;
		
	}
	
	public String get_TeamCity() {
		return _TeamCity;
	}

	public void set_TeamCity(String _TeamCity) {
		this._TeamCity = _TeamCity;
	}

	public String get_TeamName() {
		return _TeamName;
	}

	public void set_TeamName(String _TeamName) {
		this._TeamName = _TeamName;
	}

	public Date get_StartDate() {
		return _StartDate;
	}

	public void set_StartDate(Date _StartDate) {
		this._StartDate = _StartDate;
	}

	public Date get_EndDate() {
		return _EndDate;
	}
	
	 @Override
	    public String toString() {
		 StringBuilder str = new StringBuilder("Team History: ");
		 str.append("Team: ").append(get_TeamName());
		 str.append("Start Date: ").append(get_StartDate());
		 str.append("End Date: ").append(get_EndDate());
		 return str.toString();
	 }

	public void set_EndDate(Date _EndDate) {
		this._EndDate = _EndDate;
	}


}
