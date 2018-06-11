package com.sts.abstractmodel;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


// Class to represent game of a sport
public  class Game implements Comparable<Game>, InterfaceModel {

	//Unique ID for this game
	private int gameUID;
	// Start time of the game
    private ZonedDateTime startTime;
    // Time the game finished
    private ZonedDateTime finishTime;
    // Duration of the game
    private Duration duration;
    // Home team for the game
    private AbstractTeam homeTeam;
    // Away team for the game
    private AbstractTeam awayTeam;
    // Home Score for the game
    private int homeTeamScore;
    // Away Score for the game
    private int awayTeamScore;
    // Attendance of game
    private int attendance;
    // Sport of the game
    private String category;

    //List of players on the away team
    private ArrayList<Integer> listOfAwayPlayers = new ArrayList<Integer>();
    //List of players on the home team
    private ArrayList<Integer> listofHomePlayers = new ArrayList<Integer>();
    
    /**
     *Constructor: Used for upcoming game
     */
    
    public Game() {
    	super();
    }
    
    public Game(ZonedDateTime startTime_, AbstractTeam home_, AbstractTeam away_)
    {
        setStartTime(startTime_);
        setHomeTeam(home_);
        setAwayTeam(away_);
       
    }
    
    /*
     * Constructor: Used for finished game
     */
    public Game(ZonedDateTime startTime_, AbstractTeam home_, AbstractTeam away_, int homeScore_, int awayScore_, int attendance_, Duration duration_) {
    	ZonedDateTime finish;
    	setStartTime(startTime_);
    	setHomeTeam(home_);
    	setAwayTeam(away_);
    	setAwayTeamScore(awayScore_);
    	setHomeTeamScore(homeScore_);
    	setAttendance(attendance_);
    	setDuration(duration_);
    	finish = startTime.plus(duration);
    	setFinishTime(finish);
    }

    /*
     * Return: List of Away Players
     */
    public ArrayList<Integer> getListOfAwayPlayers() {
		return listOfAwayPlayers;
	}

    /*
     * Set list of away players
     */
	public void setListOfAwayPlayers(ArrayList<Integer> listOfAwayPlayers) {
		this.listOfAwayPlayers = listOfAwayPlayers;
	}

	/*
	 * Return: List of home players
	 */
	public ArrayList<Integer> getListofHomePlayers() {
		return listofHomePlayers;
	}

	/*
	 * Set list of home players
	 */
	public void setListofHomePlayers(ArrayList<Integer> listofHomePlayers) {
		this.listofHomePlayers = listofHomePlayers;
	}

	/**
     * method to set the date and start time of the game
     */
    public void setStartTime(ZonedDateTime startTime_) {
    	startTime = startTime_;
    	
    }
    
    /*
     * Return: start time of game 
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }
    
    /*
     * method to set finish time of game
     */
    public void setFinishTime(ZonedDateTime finishTime_) {
    	finishTime = finishTime_;
    }
    
    /*
     * Return: Time the game finished
     */
    public ZonedDateTime getFinishTime() {
    	return finishTime;
    }    


    /**
     * method to return the home team
     */
    public AbstractTeam getHomeTeam() {
        return homeTeam;
    }

    /**
     * method to set the home team
     */
    public void setHomeTeam(AbstractTeam ht_) {
        homeTeam = ht_;
    }

    /**
     * method to return the away team
     */
    public AbstractTeam getAwayTeam() {
        return awayTeam;
    }

    /**
     * method to set the away team
     */
    public void setAwayTeam(AbstractTeam at_) {
        awayTeam = at_;
    }

    /**
     * method to return home team's score
     */
    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    /**
     * method to set home team's score
     */
    public void setHomeTeamScore(int hTScore) {
        homeTeamScore = hTScore;
    }

    /**
     * method to return away team's score
     */
    public int getaTeamScore() {
        return awayTeamScore;
    }

    /**
     * method to set away team's score
     */
    public void setAwayTeamScore(int aTScore) {
        awayTeamScore = aTScore;
    }

    /**
     * method to return attendance of the game
     */
    public int getAttendance() {
        return attendance;
    }

    /**
     * method to set attendance
     */
    public void setAttendance(int att) {
        attendance = att;
    }
    
    
    /**
     * Return: Duration of game
     */
    public final Duration getDuration() {
        return duration;
    }

    /**
     * Set duration of game
     */
    public final void setDuration(Duration duration_) {
        duration = duration_;
    }
    
    /**
     * Return: Game's UID
     */
	public int getGameUID() {
		return gameUID;
	}

	/**
	 * Set game's UID
	 */
	public void setGameUID(int gameUID) {
		this.gameUID = gameUID;
	}

	/**
	 * 
	 */
    public String getCategory() {
		return category;
	}

	public void setCategory(String category_) {
		category = category_;
	}

	
	/** 
     * (non-Javadoc)
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Game [");
        str.append("Start Time=");
        if ( getStartTime()!=null )
        {
            str.append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ").format(getStartTime()));
        }
        else
        {
            str.append("UNK");
        }
        if(getFinishTime() != null) {
        	System.out.print("NOTNULL");
        	str.append(", Finish Time=");
            str.append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ").format(getFinishTime()));
        }
        else
        	System.out.println("NULL");
        str.append(", homeTeam=").append(getHomeTeam());
        str.append(", homeScore=").append(getHomeTeamScore());
        str.append(", awayTeam=").append(getAwayTeam());
        str.append(", awayScore=").append(getaTeamScore());
        return str.toString();
    }

    @Override
    public int compareTo(Game game_) {
    	
    	
    	if(this.startTime == game_.getStartTime() && this.finishTime == game_.getFinishTime() && 
    			this.duration == game_.getDuration() && this.homeTeam == game_.getHomeTeam() &&
    			this.awayTeam == game_.getAwayTeam() && this.attendance == game_.getAttendance()) {
    		return 1;
    	}
    	else
    		return 0;
    	
    }

    /**
     * indicates if the game has all fields initialized 
     */
    @Override
    public boolean isValid() {
        return ((this.getStartTime() != null) &&
                (this.getHomeTeam() != null) &&
                (this.getAwayTeam() != null) &&
                (!this.getAwayTeam().equals(getHomeTeam()))

        );
    }

}   
