package com.sts.concretemodel;

import com.sts.model.Player;

public class NBAPlayer extends Player{

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String first_) {
		firstName = first_;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String last_) {
		lastName = last_;
	}

	@Override
	public int getJerseyNum() {
		return jerseyNum;
	}

	@Override
	public void setJerseyNum(int jersey_) {
		jerseyNum = jersey_;
	}
	
	@Override
	 public String toString() {
	     return "Player [Firstname = " + getFirstName() + " Lastname = "+
	    getLastName() + " Jersey Number = " + getJerseyNum() +  "]";
	   }


}
