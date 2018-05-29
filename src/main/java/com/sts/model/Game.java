package com.sts.model;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//
public class Game implements Comparable<Game> {

    private ZonedDateTime startTime;
    private ZonedDateTime finishTime;
    private Duration duration;
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int attendence;
    private String category;
   

	private PlayersList listOfAwayPlayers;
    private PlayersList listofHomePlayers;

    
    public Game()
    {
    }
    
    /**
     *
     */
    public Game(ZonedDateTime startTime_, Team home_, Team away_)
    {
        setStartTime(startTime_);
        setHomeTeam(home_);
        setAwayTeam(away_);
       
    }
    
    public Game(ZonedDateTime startTime_, Team home_, Team away_, int homeScore_, int awayScore_, int attendance_, Duration duration_) {
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

    /**
     * method to return the String date
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
    public Team getHomeTeam() {
        return homeTeam;
    }

    /**
     * method to set the home team
     */
    public void setHomeTeam(Team ht_) {
        homeTeam = ht_;
    }

    /**
     * method to return the away team
     */
    public Team getAwayTeam() {
        return awayTeam;
    }

    /**
     * method to set the away team
     */
    public void setAwayTeam(Team at_) {
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
    
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category_) {
		category = category_;
	}
    
    /*
     * retrieve the list of away players for game
     */
    
    public PlayersList getAwayTeamRoster() {
    	return listOfAwayPlayers;
    }
    
    /*
     * retrieve the list of home players for game
     */
    public PlayersList getHomeTeamRoster() {
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

    /** 
     * (non-Javadoc)
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Game [");
        str.append("date=");
        if ( getStartTime()!=null )
        {
            str.append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ").format(getStartTime()));
        }
        else
        {
            str.append("UNK");
        }
      
        str.append(", homeTeam=").append(getHomeTeam());
        str.append(", awayTeam=").append(getAwayTeam());
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
    public boolean isValidGame() {
        return ((this.getStartTime() != null) &&
                (this.getHomeTeam() != null) &&
                (this.getAwayTeam() != null) &&
                (!this.getAwayTeam().equals(getHomeTeam()))

        );
    }
}   
