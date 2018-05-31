package com.sts.abstractModel;

public abstract class Player {

	protected String _firstName;
	protected String _lastName;
	protected int _jerseyNum;
	protected int _playerID;	

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

	

	public String get_firstName() {
		return _firstName;
	}


	public void set_firstName(String _firstName) {
		this._firstName = _firstName;
	}


	public String get_lastName() {
		return _lastName;
	}


	public void set_lastName(String _lastName) {
		this._lastName = _lastName;
	}


	public int get_jerseyNum() {
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
                (this.get_jerseyNum() > -1) &&
                (this.get_firstName() != null) &&
                (this.get_lastName() != null));
    }

	@Override
	public String toString() {
		return "Player [_firstName=" + _firstName + ", _lastName=" + _lastName + ", _jerseyNum=" + _jerseyNum
				+ ", _playerID=" + _playerID + "]";
	}
	
}
