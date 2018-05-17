package model;

public class Game implements Comparable<Game> {

    private String date;
    private String time;
    // private String location;
    private String homeTeam;
    private String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int attendence;

    /**
     *
     */
    public Game() {
        this("general", "general", "general", 0, "general", 0, 0);
    }

    /**
     *
     */
    public Game(String date_, String time_, String aTeam_, int aTeamScore, String hTeam_, int hTeamScore_, int attendence_) {
        setDate(date_);
        setTime(time_);
        sethTeam(hTeam_);
        setaTeam(aTeam_);
        setaTeamScore(aTeamScore);
        sethTeamScore(hTeamScore_);
        setAttendence(attendence_);
    }

    /**
     * 
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     */
    public void setDate(String dat) {
        date = dat;
    }

    /**
     * 
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     */
    public void setTime(String tim) {
        time = tim;
    }

    /**
     * 
     */
    public String gethTeam() {
        return homeTeam;
    }

    /**
     * 
     */
    public void sethTeam(String hT) {
        homeTeam = hT;
    }

    /**
     * 
     */
    public String getaTeam() {
        return awayTeam;
    }

    /**
     * 
     */
    public void setaTeam(String aT) {
        awayTeam = aT;
    }

    /**
     * 
     */
    public int gethTeamScore() {
        return homeTeamScore;
    }

    /**
     * 
     */
    public void sethTeamScore(int hTScore) {
        homeTeamScore = hTScore;
    }

    /**
     * 
     */
    public int getaTeamScore() {
        return awayTeamScore;
    }

    public void setaTeamScore(int aTScore) {
        awayTeamScore = aTScore;
    }

    /**
     * 
     */
    public int getAttendence() {
        return attendence;
    }

    /**
     * 
     */
    public void setAttendence(int att) {
        attendence = att;
    }

    /** 
     * (non-Javadoc)
     */
    @Override
    public String toString() {
        return "Game [date=" + date + ", time=" + time + ", homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", homeTeamScore=" + homeTeamScore + ", awayTeamScore=" + awayTeamScore + ", attendence=" + attendence + "]";
    }

    /** 
     * TODO: fix this
     */
    @Override
    public int compareTo(Game o_) {
        return 0;
    }

}
