package IdentityClasses;

public class Game {

	private String date;
	private String time;
	//private String location;
	private String hTeam, aTeam;
	private int hTeamScore, aTeamScore, attendence;
	
	
	
	
	public Game() {
		this("general","general","general", 0, "general",0,0);
	}
	
	
	
	public Game(String date, String time, String aTeam, int aTeamScore, String hTeam, int hTeamScore,
			int attendence) {

		setDate(date);
		setTime(time);
		sethTeam(hTeam);
		setaTeam(aTeam);
		setaTeamScore(aTeamScore);
		sethTeamScore(hTeamScore);
		setAttendence(attendence);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String dat) {
		date = dat;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String tim) {
		time = tim;
	}
	/*
	public String getLocation() {
		return location;
	}
	public void setLocation(String loc) {
		location = loc;
	}
	*/
	public String gethTeam() {
		return hTeam;
	}
	public void sethTeam(String hT) {
		hTeam = hT;
	}
	public String getaTeam() {
		return aTeam;
	}
	public void setaTeam(String aT) {
		aTeam = aT;
	}
	public int gethTeamScore() {
		return hTeamScore;
	}
	public void sethTeamScore(int hTScore) {
		hTeamScore = hTScore;
	}
	public int getaTeamScore() {
		return aTeamScore;
	}
	public void setaTeamScore(int aTScore) {
		aTeamScore = aTScore;
	}
	public int getAttendence() {
		return attendence;
	}
	public void setAttendence(int att) {
		attendence = att;
	}
	
	
	
	
	}
