package com.sts.concreteModel;

import com.sts.abstractModel.Player;

public class NHLPlayer extends Player{

	@Override
	public String getFirstName() {
		return _firstName;
	}

	@Override
	public void setFirstName(String first_) {
		_firstName = first_;
	}

	@Override
	public String getLastName() {
		return _lastName;
	}

	@Override
	public void setLastName(String last_) {
		_lastName = last_;
	}

	@Override
	public int getJerseyNum() {
		return _jerseyNum;
	}

	@Override
	public void setJerseyNum(int jersey_) {
		_jerseyNum = jersey_;
	}
	
	@Override
	 public String toString() {
	     return "Player [Firstname = " + getFirstName() + " Lastname = "+
	    getLastName() + " Jersey Number = " + getJerseyNum() +  "]";
	   }

}
