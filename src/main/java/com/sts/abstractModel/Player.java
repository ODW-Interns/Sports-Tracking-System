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

	public abstract String getFirstName();

	//method to set first name of player
	public abstract void setFirstName(String first_); 

	//method to retrieve last name of player
	public abstract String getLastName();
	
	//method to set last name of player
	public abstract void setLastName(String last_);

	//method to retrieve jersey number of player
	public abstract int getJerseyNum();

	//method to set jersey number of player
	public abstract void setJerseyNum(int jersey_);
	




	@Override
	 public String toString() {
	     return "Player [Firstname = " + getFirstName() + " Lastname = "+
	    getLastName() + " Jersey Number = " + getJerseyNum() +  "]";
	   }
	
}
