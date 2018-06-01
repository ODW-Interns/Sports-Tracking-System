package com.sts.abstractModel;

public abstract class Player {

	protected String _firstName;
	protected String _lastName;
	protected int _jerseyNum;
	protected int _playerID;	
	protected Team currentTeam;
	protected String _sportCategory;
	
	
	public String get_sportCategory() {
		return _sportCategory;
	}


	public void set_sportCategory(String _sportCategory) {
		this._sportCategory = _sportCategory;
	}
	


	public Team getCurrentTeam() {
		return currentTeam;
	}


	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}


	
	//Default Constructor
	public Player() {
		this(-1,-1,"N/A", "N/A");
	}

	
	public Player(int playerID_, int jerseyNum_, String firstName_, String lastName_){

		_firstName = firstName_;
		_lastName = lastName_;
		_playerID = playerID_;
		_jerseyNum = jerseyNum_;
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


	public void set_jerseyNum(int _jerseyNum) {
		this._jerseyNum = _jerseyNum;
	}


	public int get_playerID() {
		return _playerID;
	}



	public void set_playerID(int _playerID) {
		this._playerID = _playerID;
	}

	/**
     * indicates if the player has all fields initialized 
     */
    public boolean isValidPlayer() {
        return ((this.get_playerID() > -1) &&
                (this.getJerseyNum() > -1) &&
                (this.getFirstName() != null) &&
                (this.getLastName() != null) && this.get_sportCategory() != null);
    }

	@Override
	public String toString() {

		return "Player [_firstName=" + _firstName + ", _lastName=" + _lastName + ", _jerseyNum=" + _jerseyNum
				+ ", _playerID=" + _playerID + "]";
	}

	
}
