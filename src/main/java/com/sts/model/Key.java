package com.sts.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;

public class Key{

    private final ZonedDateTime startTime;
    private final Team awayTeamName;
    

    public Key(ZonedDateTime x_, Team y_) {
        startTime = x_;
        awayTeamName = y_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return startTime == key.startTime && awayTeamName == key.awayTeamName;
    }

   @Override
    public int hashCode() {
    	int result;
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(startTime);
        String hashString = strDate.concat(awayTeamName.toString());
        result = Integer.parseInt(hashString);
        return result;
    }


    
	public ZonedDateTime getStartTime() {
		return startTime;
	}


	public Team getAwayTeamName() {
		return awayTeamName;
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
