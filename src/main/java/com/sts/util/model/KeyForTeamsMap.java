package com.sts.util.model;

/**
 * Class to store data used as a key for the teams map.
 * The key contains two strings for the team:
 * 1)the city of the team
 * 2) the name of the team
 */
public class KeyForTeamsMap {

    private final String _city;
    private final String _teamName;
    

    //Constructor
    public KeyForTeamsMap(String x_, String y_) {
        _city = x_;
        _teamName = y_;
    }

    // returns the team's city
	public String get_city() {
		return _city;
	}

	// returns the team's name
	public String get_teamName() {
		return _teamName;
	}

	// returns string of the key's info
	public String toString() {
		String key = "Team City:" + _city + ", Team Name:" + _teamName;
		return key;
	}

	//method to compare two keys for the team map
    @Override
    public boolean equals(Object o_) {
        if (!(o_ instanceof KeyForTeamsMap)) return false;
        KeyForTeamsMap key = (KeyForTeamsMap) o_;
        return _city.equals(key._city) && _teamName.equals(key._teamName);
    }
    
    //method to create a hashcode for the key
    @Override
    public int hashCode() {
    	int result;
    	String hashString = _city.concat(_teamName);
        result = hashString.hashCode();
        return result;
    }
}
