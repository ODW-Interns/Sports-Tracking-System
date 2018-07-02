package com.sts.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sts.abstractmodel.AbstractTeam;
import com.sts.control.TeamsList;
import com.sts.util.model.KeyForTeamsMap;

public class CustomValidations {

	private Logger _logger;
	public CustomValidations(){
		_logger = LoggerFactory.getLogger(getClass().getSimpleName());
	}
	
	
	public Boolean dateValidation(String strDate) {
		
		if (strDate.trim().equals("")){
		    return true;
		}else{
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
		    sdfrmt.setLenient(false);
		 
		    try{
		        Date javaDate = sdfrmt.parse(strDate);  
		    }catch (ParseException e){
		    	_logger.error("Invalid Date");
		        return false;
		    }
		    return true;
		}
	}
	
	public boolean timeValidation(String strTime) {
		if (strTime.trim().equals("")){
		    return true;
		}else {
			
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
			fmt.setLenient(false);
			try{
		        Date javaDate = fmt.parse(strTime);  
		    }catch (ParseException e){
		    	_logger.error("Invalid Time");
		        return false;
		    }
		    return true;
		}	
	}
	
	public boolean cityValidation(TeamsList teamsList_, String city_) {
		Iterator<Entry<KeyForTeamsMap, AbstractTeam>> teamIterator;
		teamIterator = teamsList_.getTeamMap().entrySet().iterator();
		Entry<KeyForTeamsMap, AbstractTeam> entry;
		while(teamIterator.hasNext()) {
			entry = teamIterator.next();
			if(entry.getValue().getLocation().equals(city_)) {
		        return true;
		    }
		}
		_logger.error("Invalid city");
		return false;
	
	}
	public boolean teamValidation(TeamsList teamsList_, String teamName_, String city_) {
		Iterator<Entry<KeyForTeamsMap, AbstractTeam>> teamIterator;
		teamIterator = teamsList_.getTeamMap().entrySet().iterator();
		Entry<KeyForTeamsMap, AbstractTeam> entry;
		while(teamIterator.hasNext()) {
			entry = teamIterator.next();
			if(entry.getValue().getTeamName().equals(teamName_)&&entry.getValue().getLocation().equals(city_)) {
		        return true;
		        
		    }
		}
		_logger.error("Invalid Team");
		return false;
	}
	
	public Boolean nameValidation(String name) {
		
		if (name.matches("[a-zA-Z_]+")) {
			return true;
		}
		_logger.error("Invalid Name. Please Re-enter");
		return false;
		
		
	}
}
	
