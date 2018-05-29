package com.sts.model;

public abstract class Player {

	String firstName;
	String lastName;
	int jerseyNum;
	
	//Default Constructor
	public Player() {
		this("N/A", "N/A", -1);
	}

	
	public Player(String first_, String last_, int jersey_){

		firstName = first_;
		lastName = last_;
		
		jerseyNum = jersey_;
	}

	abstract String getFirstName();

	//method to set first name of player
	abstract void setFirstName(String first_); 

	//method to retrieve last name of player
	abstract String getLastName();
	
	//method to set last name of player
	abstract void setLastName(String last_);

	//method to retrieve jersey number of player
	abstract int getJerseyNum();

	//method to set jersey number of player
	abstract void setJerseyNum(int jersey_);
	




	@Override
	 public String toString() {
	     return "Player [Firstname = " + getFirstName() + " Lastname = "+
	    getLastName() + " Jersey Number = " + getJerseyNum() +  "]";
	   }
	
}
