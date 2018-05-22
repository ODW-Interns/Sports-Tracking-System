package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import model.Game;
import model.GamesList;
import model.Player;



public class PastGamesPlayersFileReader {

	private static final String DELIM = "|";
	
    public void readData(InputStream is_, GamesList list_) throws FileNotFoundException, RuntimeException {
        if (is_ == null)
            throw new FileNotFoundException();

        if (list_ == null)
            throw new RuntimeException("No list provided");

        try (InputStreamReader sr = new InputStreamReader(is_)) {
            readFromFileForLists(sr, list_);
       
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }
    }

	
	public void readFromFileForLists(Reader is_, GamesList listofPastGames_) throws IOException {
		try (BufferedReader reader = new BufferedReader(is_)) {
			StringTokenizer tokenizer;
			String line;
			//Game game;
			int i = -1;
			Player player;
			while ((line = reader.readLine()) != null) {
				i++;
				// don't process empty lines
				if ("".equals(line))
					continue;
				tokenizer = new StringTokenizer(line, DELIM);
				
				
						
				Game temp = listofPastGames_.get(i);
					for(int j = 0; j < 12; j++){
						 player = new Player();
				
						try {
							String jerseyNum = tokenizer.nextToken();
							
							int jerseyInt = Integer.parseInt(jerseyNum);
							player.setJerseyNum(jerseyInt);
							//game.getListOfAwayPlayers()
						}catch (Exception e_) {
							System.err.println("Jersey #" + e_.toString());
							continue;
						}
				
						try {
							player.setFirstName(tokenizer.nextToken());
						}catch (Exception e_) {
							System.err.println("First Name" + e_.toString());
							continue;
						}
				
						try {
							player.setLastName(tokenizer.nextToken());
						}catch (Exception e_) {
							System.err.println("Last Name" + e_.toString());
							continue;
						}
						listofPastGames_.get(i).getListofHomePlayers().add(player);
					}
					for(int k = 0; k < 12; k++) {
						player = new Player();
						try {
							String jerseyNum = tokenizer.nextToken();
							int jerseyInt = Integer.parseInt(jerseyNum);
							player.setJerseyNum(jerseyInt);
							//game.getListOfAwayPlayers()
						}catch (Exception e_) {
							System.err.println("Jersey #" + e_.toString());
							continue;
						}
				
						try {
							player.setFirstName(tokenizer.nextToken());
						}catch (Exception e_) {
							System.err.println("First Name" + e_.toString());
							continue;
						}
				
						try {
							player.setLastName(tokenizer.nextToken());
						}catch (Exception e_) {
							System.err.println("Last Name" + e_.toString());
							continue;
						}
						listofPastGames_.get(i).getListOfAwayPlayers().add(player);
					}
				}
			}
		}
	}
				
					
			
		
			
	
	
	

