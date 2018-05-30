package com.sts.concretemodel;

import com.sts.model.Player;

public class MLBPlayer extends Player {
	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return firstName;
	}

	@Override
	public void setFirstName(String first_) {
		// TODO Auto-generated method stub
		firstName = first_;
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return lastName;
	}

	@Override
	public void setLastName(String last_) {
		// TODO Auto-generated method stub
		lastName = last_;
	}

	@Override
	public int getJerseyNum() {
		// TODO Auto-generated method stub
		return jerseyNum;
	}

	@Override
	public void setJerseyNum(int jersey_) {
		// TODO Auto-generated method stub
		jerseyNum = jersey_;
	}
	
	@Override
	 public String toString() {
	     return "Player [Firstname = " + getFirstName() + " Lastname = "+
	    getLastName() + " Jersey Number = " + getJerseyNum() +  "]";
	   }

}
