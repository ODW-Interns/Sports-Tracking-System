package com.sts.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;

public class Key{

    private final ZonedDateTime date;
    private final Team awayTeamName;
    private final Team homeTeamName;

    public Key(ZonedDateTime x_, Team y_, Team z_) {
        date = x_;
        awayTeamName = y_;
        homeTeamName = z_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return date == key.date && awayTeamName == key.awayTeamName;
    }

   @Override
    public int hashCode() {
    	int result;
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        String hashString = strDate.concat(awayTeamName.toString());
        result = Integer.parseInt(hashString);
        return result;
    }


    
	public ZonedDateTime getDate() {
		return date;
	}


	public Team getAwayTeamName() {
		return awayTeamName;
	}


	public Team getHomeTeamName() {
		return homeTeamName;
	}


	/*@Override
	public int compareTo(Key o) {
		// TODO Auto-generated method stub
		if(this.date.isAfter((Key)o.)){
			return 1;
		}
		else
		*/

}
