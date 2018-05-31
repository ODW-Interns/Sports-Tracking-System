package com.sts.abstractModel;

public abstract class Player {

	protected String _firstName;
	protected String _lastName;
	protected int _jerseyNum;
	
	//Default Constructor
	public Player() {
		this("N/A", "N/A", -1);
	}

	
	public Player(String first_, String last_, int jersey_){

		_firstName = first_;
		_lastName = last_;
		
		_jerseyNum = jersey_;
	}

	public String getFirstName() {
		return _firstName;
	}
	
	public void setFirstName(String first_) {
		_firstName = first_;
	}

	public String getLastName() {
		return _lastName;
	}
	
	public void setLastName(String last_) {
		_lastName = last_;
	}

	public int getJerseyNum() {
		return _jerseyNum;
	}

	public void setJerseyNum(int jersey_) {
		_jerseyNum = jersey_;
	}

	@Override
	public String toString() {
	     return "Player [Firstname = " + getFirstName() + " Lastname = "+
	    getLastName() + " Jersey Number = " + getJerseyNum() +  "]";
	   }
	
}
