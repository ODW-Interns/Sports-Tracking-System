package com.sts.control;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

import com.sts.io.GamesFileReader;
import com.sts.io.StoreDataFromInputFile;
import com.sts.model.Game;
import com.sts.model.GamesList;
import com.sts.model.TeamsList;
import com.sts.view.ConsolePrinter;
import com.sts.view.PrintToLog;

public class SportsSystem {

	SportsSystem() throws RuntimeException, IOException, ParseException{
		startSystem();
	}
	
	public void startSystem() throws RuntimeException, IOException, ParseException {
		 GamesFileReader gr = new GamesFileReader();
	        try (InputStream is = getClass().getResourceAsStream("/games.csv")) {
	            ArrayList<Game> list = new ArrayList<Game>();
	            gr.readData(is, list);
	        }
	        catch (Exception e_) {
	            assert(false);
	            e_.printStackTrace();
	        }
	}
	
	
	
	
	public static void main(String[] args) throws RuntimeException, IOException, ParseException {
		SportsSystem system = new SportsSystem(); // Entry, starts system
		

	}
}