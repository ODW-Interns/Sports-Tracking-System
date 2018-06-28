package com.sts.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomValidations {

	public Boolean dateValidation(String strDate) {
		
		if (strDate.trim().equals("")){
		    return true;
		}else{
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
		    sdfrmt.setLenient(false);
		 
		    try{
		        Date javaDate = sdfrmt.parse(strDate);  
		    }catch (ParseException e){
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
		        return false;
		    }
		    return true;
		}	
	}
}
