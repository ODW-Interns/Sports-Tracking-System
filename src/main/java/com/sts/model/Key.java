package com.sts.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;

public class Key{

    private final ZonedDateTime startTime;
    private final int UID;
    

    public Key(ZonedDateTime x_, int y_) {
        startTime = x_;
        UID = y_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return startTime == key.startTime && UID == key.UID;
    }

   @Override
    public int hashCode() {
    	int result;
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(startTime);
        String hashString = strDate.concat(Integer.toString(UID));
        result = Integer.parseInt(hashString);
        return result;
    }


    
	public ZonedDateTime getStartTime() {
		return startTime;
	}


	public int getGameUID() {
		return UID;
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
