package io;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import lists.GamesList;

public abstract class AbstractGameReader {

    public void readData(InputStream is_, GamesList listofGames_) throws FileNotFoundException, RuntimeException {
        if (is_ == null)
            throw new FileNotFoundException();

        if (listofGames_ == null)
            throw new RuntimeException("No catalog provided");

        try (InputStreamReader sr = new InputStreamReader(is_)) {
            readFromFileWithPreviousGames(sr, listofGames_);
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }
    }

    abstract void readFromFileWithPreviousGames(Reader is_, GamesList listofGames_);
}
