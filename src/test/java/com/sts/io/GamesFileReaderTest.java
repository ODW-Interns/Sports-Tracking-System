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

public class GamesFileReaderTest {

    @Test
    public void test() {
        GamesFileReader gr = new GamesFileReader();
        try (InputStream is = getClass().getResourceAsStream("/games.csv")) {
            GamesList gameslist = new GamesList();
            gr.readData(is, gameslist.getGamesMap());
        }
        catch (Exception e_) {
            assert(false);
            e_.printStackTrace();
        }

    }

}
