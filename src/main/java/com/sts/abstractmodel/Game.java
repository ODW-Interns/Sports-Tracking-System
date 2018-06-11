package com.sts.abstractmodel;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//
public  class Game implements Comparable<Game>, InterfaceModel {

	private int gameUID;
    private ZonedDateTime startTime;
    private ZonedDateTime finishTime;
    private Duration duration;
    private AbstractTeam homeTeam;
    private AbstractTeam awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int attendence;
    private String category;

    private ArrayList<Integer> listOfAwayPlayers = new ArrayList<Integer>();
    private ArrayList<Integer> listofHomePlayers = new ArrayList<Integer>();
    
    
    public Game()
    {
    }
    
    /**
     *
     */
    public Game(ZonedDateTime startTime_, AbstractTeam home_, AbstractTeam away_)
    {
        setStartTime(startTime_);
        setHomeTeam(home_);
        setAwayTeam(away_);
       
    }
    
    public Game(ZonedDateTime startTime_, AbstractTeam home_, AbstractTeam away_, int homeScore_, int awayScore_, int attendance_, Duration duration_) {
    	ZonedDateTime finish;
    	setStartTime(startTime_);
    	setHomeTeam(home_);
    	setAwayTeam(away_);
    	setAwayTeamScore(awayScore_);
    	setHomeTeamScore(homeScore_);
    	setAttendence(attendance_);
    	setDuration(duration_);
    	finish = startTime.plus(duration);
    	setFinishTime(finish);
    }

    
    public ArrayList<Integer> getListOfAwayPlayers() {
		return listOfAwayPlayers;
	}

	public void setListOfAwayPlayers(ArrayList<Integer> listOfAwayPlayers) {
		this.listOfAwayPlayers = listOfAwayPlayers;
	}

	public ArrayList<Integer> getListofHomePlayers() {
		return listofHomePlayers;
	}

	public void setListofHomePlayers(ArrayList<Integer> listofHomePlayers) {
		this.listofHomePlayers = listofHomePlayers;
	}

	/**
     * method to return the date and start time of the game
     */
    public void setStartTime(ZonedDateTime startTime_) {
    	startTime = startTime_;
    	
    }
    
    public ZonedDateTime getStartTime() {
        return startTime;
    }
    
    public void setFinishTime(ZonedDateTime finishTime_) {
    	finishTime = finishTime_;
    }
    
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
    public int getAttendence() {
        return attendence;
    }

    /**
     * method to set attendance
     */
    public void setAttendence(int att) {
        attendence = att;
    }
    
    /*
     * retrieve the list of away players for game
     */
    
    public ArrayList<Integer> getAwayTeamRoster() {
    	return listOfAwayPlayers;
    }
    
    /*
     * retrieve the list of home players for game
     */
    public ArrayList<Integer> getHomeTeamRoster() {
    	return listofHomePlayers;
    }
    
    /**
     * 
     */
    public final Duration getDuration() {
        return duration;
    }

    /**
     * 
     */
    public final void setDuration(Duration duration_) {
        duration = duration_;
    }
    
	public int getGameUID() {
		return gameUID;
	}

	public void setGameUID(int gameUID) {
		this.gameUID = gameUID;
	}


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

    /***
     * TODO: this is flat out wrong... you need to compare t
     * (non-Javadoc)
     */
    @Override
    public int compareTo(Game game_) {
    	
    	
    	if(this.startTime == game_.getStartTime() && this.finishTime == game_.getFinishTime() && 
    			this.duration == game_.getDuration() && this.homeTeam == game_.getHomeTeam() &&
    			this.awayTeam == game_.getAwayTeam() && this.attendence == game_.getAttendence()) {
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
