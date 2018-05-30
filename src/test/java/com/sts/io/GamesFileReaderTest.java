/****************************************************************************
 * FILE: GamesFileReaderTest.java
 * DSCRPT:
 ****************************************************************************/

package com.sts.io;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

import com.sts.model.Game;
import com.sts.model.GamesList;
import com.sts.model.TeamsList;

public class GamesFileReaderTest {

    @Test
    public void test() {
        GamesFileReader gr = new GamesFileReader();
        try (InputStream is = getClass().getResourceAsStream("/games.csv")) {
        	GamesList games = new GamesList();
	    	TeamsList teams = new TeamsList();
            gr.readData(is, games, teams);
        }
        catch (Exception e_) {
            assert(false);
            e_.printStackTrace();
        }

    }
    
    @Test
    public void testInvalid() {
        GamesFileReader gr = new GamesFileReader();
        try (InputStream is = getClass().getResourceAsStream("/invalidGames.csv")) {
        	GamesList games = new GamesList();
	    	TeamsList teams = new TeamsList();
            gr.readData(is, games, teams);
        }
        catch (Exception e_) {
            assert(false);
            e_.printStackTrace();
        }

    }

}
