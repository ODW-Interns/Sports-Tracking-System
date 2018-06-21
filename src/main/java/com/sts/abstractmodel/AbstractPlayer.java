package com.sts.abstractmodel;

import java.util.ArrayList;
import java.util.Iterator;

import com.sts.concretemodel.TeamPlayer;

/**
 * Abstract class to represent a player for a sport
 */

public abstract class AbstractPlayer implements InterfaceModel{


	//Player's first name
	private String _firstName;
	//Player's last name
	private String _lastName;
	//Player's jersey #
	private int _jerseyNum;
	//Player's Unique ID
	private int _playerID;	

	//The sport the player plays
	private SportsCategory _sportCategory;
	
	TeamPlayer _currentTeamHistory;


	private ArrayList<TeamPlayer> playerTeams;
		
	/*Default Constructor*/
	public AbstractPlayer() {
		this(-1,-1,"N/A", "N/A");
	}

	/*Constructor*/
	public AbstractPlayer(int playerID_, int jerseyNum_, String firstName_, String lastName_){

		_firstName = firstName_;
		_lastName = lastName_;
		_playerID = playerID_;
		_jerseyNum = jerseyNum_;
		playerTeams = new ArrayList<TeamPlayer>();
	}

	
	/*Return:the player's sport*/
	public SportsCategory get_sportCategory() {
		return _sportCategory;
	}

	/*Set the player's sport*/
	public void set_sportCategory(SportsCategory _sportCategory) {
		this._sportCategory = _sportCategory;
	}
	
	
	/*Return: The player's first name*/
	public String getFirstName() {
		return _firstName;
	}
	
	/*Set the player's first name*/
	public void setFirstName(String first_) {
		_firstName = first_;
	}

	/*Return: Player's last name*/
	public String getLastName() {
		return _lastName;
	}
	
	/*Set player's last name*/
	public void setLastName(String last_) {
		_lastName = last_;

	}

	/*Return: Player's Jersey # */
	public int getJerseyNum() {
		return _jerseyNum;
	}

	/*Set player's jersey # */
	public void set_jerseyNum(int _jerseyNum) {
		this._jerseyNum = _jerseyNum;
	}

	/*Return: Player's Unique ID*/
	public int get_playerID() {
		return _playerID;
	}

	/*Set player's unique ID*/
	public void set_playerID(int _playerID) {
		this._playerID = _playerID;
	}
	
	public void setCurrentTeamHistory(TeamPlayer currentTeam) {
		this._currentTeamHistory = currentTeam;
	}
		
	public TeamPlayer getCurrentTeamHistory() {
		return _currentTeamHistory;
	}
	
	public AbstractTeam getCurrentTeam() {
		// TODO filter team players by status/enddate.
		Iterator<TeamPlayer> i;
		i = playerTeams.iterator();
		TeamPlayer tempTeamPlayer = null;
		
		while(i.hasNext()) {
			tempTeamPlayer = i.next();
			if(tempTeamPlayer.isStatus() == true) {
				return tempTeamPlayer.getTeam();
			}
		}
		return tempTeamPlayer.getTeam();
	}

	
	public ArrayList<TeamPlayer> getPlayerTeams() {
		return playerTeams;
	}

	public void setPlayerTeams(ArrayList<TeamPlayer> playerTeams) {
		this.playerTeams = playerTeams;
	}

	/**
     * indicates if the player has all fields initialized 
     */
	@Override
	public boolean isValid() {
        return ((this.get_playerID() > -1) &&
                (this.getJerseyNum() > -1) &&
                (this.getFirstName() != null) &&
                (this.getLastName() != null) && this.get_sportCategory() != null);
    }
    /*Return: String with player's info */
	@Override
	public String toString() {

		return "Player [_firstName=" + _firstName + ", _lastName=" + _lastName + ", _jerseyNum=" + _jerseyNum
				+ ", _playerID=" + _playerID + ", current team= " + _currentTeamHistory.getTeam().fullTeamName() + "]";
	}

	/*Returns true if the two players are equal*/
	@Override
	public boolean equals(Object obj) {
		return _firstName == ((AbstractPlayer)obj).getFirstName() &&
				_lastName == ((AbstractPlayer)obj).getLastName() &&
				_jerseyNum == ((AbstractPlayer)obj).getJerseyNum() &&
				getCurrentTeam().equals(((AbstractPlayer)obj).getCurrentTeam());
	}
		
}
