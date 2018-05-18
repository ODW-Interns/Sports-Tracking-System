package model;

public class Player {

	String firstName;
	String lastName;
	int jerseyNum;
	String position;
	
	//Default Constructor
	public Player() {
		this("N/A", "N/A", "N/A", -1);
	}

	//Constructor with given arguments
	public Player(String first_, String last_, String pos_, int jersey_){
		firstName = first_;
		lastName = last_;
		position = pos_;
		jerseyNum = jersey_;
	}

	//method to retrieve first name of player
	public String getFirstName() {
		return firstName;
	}

	//method to set first name of player
	public void setFirstName(String first_) {
		firstName = first_;
	}

	//method to retrieve last name of player
	public String getLastName() {
		return lastName;
	}
	
	//method to set last name of player
	public void setLastName(String last_) {
		lastName = last_;
	}

	//method to retrieve jersey number of player
	public int getJerseyNum() {
		return jerseyNum;
	}

	//method to set jersey number of player
	public void setJerseyNum(int jersey_) {
		jerseyNum = jersey_;
	}

	//method to retrieve position of player
	public String getPosition() {
		return position;
	}

	//method to set postition of player
	public void setPosition(String pos_) {
		position = pos_;
	}
	
	
}
