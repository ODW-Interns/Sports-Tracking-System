package com.sts.abstractModel;

import java.util.ArrayList;

import com.sts.concreteModel.TeamHistory;

public abstract class Player {

	private String _firstName;
	private String _lastName;
	private int _jerseyNum;
	private int _playerID;	
	private Team _currentTeam;
	private String _sportCategory;
	
	private ArrayList<TeamHistory> _HistoryOfTeamsForPlayer;
	
	
	public String get_sportCategory() {
		return _sportCategory;
	}


	public void set_sportCategory(String _sportCategory) {
		this._sportCategory = _sportCategory;
	}
	


	public Team getCurrentTeam() {
		return _currentTeam;
	}


	public void setCurrentTeam(Team currentTeam) {
		this._currentTeam = currentTeam;
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
		_HistoryOfTeamsForPlayer = new ArrayList<TeamHistory>();
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
	
	

	public ArrayList<TeamHistory> get_HistoryOfTeamsForPlayers() {
		return _HistoryOfTeamsForPlayer;
	}


	public void set_HistoryOfTeamsForPlayers(ArrayList<TeamHistory> HistoryOfTeamsForPlayer_) {
		_HistoryOfTeamsForPlayer = HistoryOfTeamsForPlayer_;
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

	@Override
	public boolean equals(Object obj) {
		return _firstName == ((Player)obj).getFirstName() &&
				_lastName == ((Player)obj).getLastName() &&
				_jerseyNum == ((Player)obj).getJerseyNum() &&
				_currentTeam.equals(((Player)obj).getCurrentTeam())
			;
	}
	
	
}
