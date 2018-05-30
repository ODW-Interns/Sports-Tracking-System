/****************************************************************************
 * FILE: GamesFileReaderTest.java
 * DSCRPT:
 ****************************************************************************/

package com.sts.io;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

import com.sts.abstractModel.Game;
import com.sts.concreteModel.GamesList;
import com.sts.concreteModel.TeamsList;
import com.sts.control.GamesFileReader;

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
