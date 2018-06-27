package com.sts.util.model;

/**
 * Generating a key for the TreeMap (Games)
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;

public class KeyForGamesMap{

    private final ZonedDateTime _startTime;
    private final int _UID;
    

    public KeyForGamesMap(ZonedDateTime x_, int y_) {
        _startTime = x_;
        _UID = y_;
    }

    @Override
    public boolean equals(Object o_) {
        if (this == o_) return true;
        if (!(o_ instanceof KeyForGamesMap)) return false;
        KeyForGamesMap key = (KeyForGamesMap) o_;
        return _startTime == key._startTime && _UID == key._UID;
    }

   @Override
    public int hashCode() {
    	int result;
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(_startTime);
        String hashString = strDate.concat(Integer.toString(_UID));
        result = Integer.parseInt(hashString);
        return result;
    }


    
	public ZonedDateTime getStartTime() {
		return _startTime;
	}


	public int getGameUID() {
		return _UID;
	}

	public String toString() {
		String key = "GameID:" + _UID + ", StartTime:" + _startTime;
		return key;
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
