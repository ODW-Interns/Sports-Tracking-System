package com.sts.abstractmodel;

import java.util.ArrayList;
import java.util.Iterator;

import com.sts.util.model.TeamPlayerHistory;

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
	
	//Data that connects the history between a player and a team
	//This keeps track of the current team the player is on
	TeamPlayerHistory _currentTeamHistory;

	//A list that contains all the history of a player (All the teams the player have played for)
	private ArrayList<TeamPlayerHistory> playerTeams;
		
	/*Default Constructor*/
	public AbstractPlayer() {
		this(-1,-1,"N/A", "N/A");
		_currentTeamHistory = new TeamPlayerHistory();
	}

	/*Constructor*/
	public AbstractPlayer(int playerID_, int jerseyNum_, String firstName_, String lastName_){

		_firstName = firstName_;
		_lastName = lastName_;
		_playerID = playerID_;
		_jerseyNum = jerseyNum_;
		playerTeams = new ArrayList<TeamPlayerHistory>();
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
	
	//Sets the current team history for the player
	public void setCurrentTeamHistory(TeamPlayerHistory currentTeam) {
		this._currentTeamHistory = currentTeam;
	}
		
	//get the current team history of the player
	public TeamPlayerHistory getCurrentTeamHistory() {
		return _currentTeamHistory;
	}
	
	//This returns the current team the player plays on by 
	//going through the players history and checking which team
	//he currently is on
	public AbstractTeam getCurrentTeam() {
		// TODO filter team players by status/enddate.
		Iterator<TeamPlayerHistory> i;
		i = playerTeams.iterator();
		TeamPlayerHistory tempTeamPlayer = null;
		
		while(i.hasNext()) {
			tempTeamPlayer = i.next();
			if(tempTeamPlayer.isStatus() == true) {
				return tempTeamPlayer.getTeam();
			}
		}
		return tempTeamPlayer.getTeam();
	}

	// Returns entire history of a player
	public ArrayList<TeamPlayerHistory> getPlayerTeams() {
		return playerTeams;
	}

	//Set player's history
	public void setPlayerTeams(ArrayList<TeamPlayerHistory> playerTeams) {
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

		StringBuilder playerString = new StringBuilder();
		
		playerString.append("Player [_firstName=").append(_firstName).append(", _lastName=").append(_lastName).append(", _jerseyNum=");
		
		if(_jerseyNum == -1)
			playerString.append("N/A");
		else
			playerString.append(_jerseyNum);
		playerString.append(", _playerID=").append(_playerID).append(", current team= ");
		if(_currentTeamHistory.getTeam() == null) {
			playerString.append("N/A").append("]");
		}
		else {
			playerString.append(_currentTeamHistory.getTeam().fullTeamName()).append("]");
			}
		return playerString.toString();

	}

	/*Returns true if the two players are equal*/
	@Override
	public boolean equals(Object obj) {
		return _firstName.equals(((AbstractPlayer)obj).getFirstName()) &&
				_lastName.equals(((AbstractPlayer)obj).getLastName()) &&
				_playerID == ((AbstractPlayer)obj).get_playerID();
	}
		
}
