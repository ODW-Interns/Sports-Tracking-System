package com.sts.model;

public class NBAPlayer extends Player{

	@Override
	String getFirstName() {
		// TODO Auto-generated method stub
		return firstName;
	}

	@Override
	void setFirstName(String first_) {
		// TODO Auto-generated method stub
		firstName = first_;
	}

	@Override
	String getLastName() {
		// TODO Auto-generated method stub
		return lastName;
	}

	@Override
	void setLastName(String last_) {
		// TODO Auto-generated method stub
		lastName = last_;
	}

	@Override
	int getJerseyNum() {
		// TODO Auto-generated method stub
		return jerseyNum;
	}

	@Override
	void setJerseyNum(int jersey_) {
		// TODO Auto-generated method stub
		jerseyNum = jersey_;
	}
	
	@Override
	 public String toString() {
	     return "Player [Firstname = " + getFirstName() + " Lastname = "+
	    getLastName() + " Jersey Number = " + getJerseyNum() +  "]";
	   }


}
