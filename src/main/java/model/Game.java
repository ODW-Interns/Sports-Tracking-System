package model;

//
public class Game implements Comparable<Game> {

    private String date;
    private String time;
    private String homeTeam;
    private String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int attendence;

    public PlayersList getListOfAwayPlayers() {
		return listOfAwayPlayers;
	}

	public void setListOfAwayPlayers(PlayersList listOfAwayPlayers) {
		this.listOfAwayPlayers = listOfAwayPlayers;
	}

	public PlayersList getListofHomePlayers() {
		return listofHomePlayers;
	}

	public void setListofHomePlayers(PlayersList listofHomePlayers) {
		this.listofHomePlayers = listofHomePlayers;
	}

	private PlayersList listOfAwayPlayers;
    private PlayersList listofHomePlayers;
    
    /**
     *Default Constructor
     */
    public Game() {
        this("general", "general", "general", 0, "general", 0, 0);
    }

    /**
     *Constructor with given arguments
     */
    public Game(String date_, String time_, String aTeam_, int aTeamScore, String hTeam_, int hTeamScore_, int attendence_) {
        setDate(date_);
        setTime(time_);
        sethTeam(hTeam_);
        setaTeam(aTeam_);
        setaTeamScore(aTeamScore);
        sethTeamScore(hTeamScore_);
        setAttendence(attendence_);
        listOfAwayPlayers = new PlayersList();
        listofHomePlayers = new PlayersList();
    }

    
    
    
    /**
     * method to return the String date
     */
    public String getDate() {
        return date;
    }

    /**
     * method to set the date of the game
     */
    public void setDate(String dat) {
        date = dat;
    }

    /**
     * method to return the String time
     */
    public String getTime() {
        return time;
    }

    /**
     * method to set the time of the game
     */
    public void setTime(String tim) {
        time = tim;
    }

    /**
     * method to return the home team
     */
    public String gethTeam() {
        return homeTeam;
    }

    /**
     * method to set the home team
     */
    public void sethTeam(String hT) {
        homeTeam = hT;
    }

    /**
     * method to return the away team
     */
    public String getaTeam() {
        return awayTeam;
    }

    /**
     * method to set the away team
     */
    public void setaTeam(String aT) {
        awayTeam = aT;
    }

    /**
     * method to return home team's score
     */
    public int gethTeamScore() {
        return homeTeamScore;
    }

    /**
     * method to set home team's score
     */
    public void sethTeamScore(int hTScore) {
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
    public void setaTeamScore(int aTScore) {
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
     * (non-Javadoc)
     */
    @Override
    public String toString() {
        return "Game [date=" + date + ", time=" + time + ", homeTeam=" + homeTeam +", awayTeam=" + awayTeam+
        		", homeTeamScore=" + homeTeamScore + ", awayTeamScore=" + awayTeamScore + 
        		", attendence=" + attendence + "First Home Player :" + getHomeTeamRoster().get(0).toString()+"]";
    }

    
    
    @Override
    public int compareTo(Game o_) {
    	if(this.attendence > o_.attendence) {
    		return 1;
    	}
    	else
    		return 0;
        	
    }
    

}
