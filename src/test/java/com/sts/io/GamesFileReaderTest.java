/****************************************************************************
 * FILE: GamesFileReaderTest.java
 * DSCRPT:
 ****************************************************************************/

package com.sts.io;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

import com.sts.model.Game;

public class GamesFileReaderTest {

    @Test
    public void test() {
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

}
