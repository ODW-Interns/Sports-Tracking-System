package model;

public class Player {

	String firstName;
	String lastName;
	int jerseyNum;
	String position;
	
	public Player() {
		this("N/A", "N/A", "N/A", -1);
	}
	
	public Player(String first_, String last_, String pos_, int jersey_){
		firstName = first_;
		lastName = last_;
		position = pos_;
		jerseyNum = jersey_;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first_) {
		firstName = first_;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last_) {
		lastName = last_;
	}

	public int getJerseyNum() {
		return jerseyNum;
	}

	public void setJerseyNum(int jersey_) {
		jerseyNum = jersey_;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String pos_) {
		position = pos_;
	}
	
	
}
