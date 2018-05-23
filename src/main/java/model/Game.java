package model;

import java.time.Duration;
import java.time.ZonedDateTime;

//
public class Game implements Comparable<Game> {

    private ZonedDateTime date;
    private Duration duration;
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int attendence;

    private PlayersList listOfAwayPlayers;
    private PlayersList listofHomePlayers;

    
    public Game()
    {
    }
    
    /**
     *
     */
    public Game(ZonedDateTime date_, Duration duration_, Team home_, Team away_)
    {
        setDate(date_, duration_);
        setHomeTeam(home_);
        setAwayTeam(away_);
    }

    /**
     * method to return the String date
     */
    public ZonedDateTime getDate() {
        return date;
    }
    
    
    /**
     * sets the date with a time zone, and defaults the game duration to 4 hours
     */
    public void setDate(ZonedDateTime date_)
    {
        setDate(date_, Duration.ofHours(4));
    }

    /**
     * method to set the date of the game
     */
    public void setDate(ZonedDateTime date_, Duration duration_) {
        date = date_;
        duration = duration_;
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
        StringBuilder str = new StringBuilder();
        return str.toString(); 
    }

    /***
     * TODO: this is flat out wrong... you need to compare t
     * (non-Javadoc)
     */
    @Override
    public int compareTo(Game o_) {
    	if(attendence > o_.attendence) {
    		return 1;
    	}
    	else
    		return 0;
    }





    /**
     * indicates if the game has all fields initialized 
     */
    public boolean isValidGame() {
        return ((this.getDate() != null) &&
                (this.getDuration() != null) &&
                (this.getHomeTeam() != null) &&
                (this.getAwayTeam() != null) &&
                (!this.getAwayTeam().equals(getHomeTeam()))

        );
    }
}   
