package io;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import lists.GamesList;

/*
 * Abstract class used by classes that will read from files with games listed
 * */
public abstract class AbstractGameReader {

	//method to check that the input file and Games List structure exists
    public void readData(InputStream is_, GamesList listofGames_) throws FileNotFoundException, RuntimeException {
        if (is_ == null)
            throw new FileNotFoundException();

        if (listofGames_ == null)
            throw new RuntimeException("No catalog provided");

        try (InputStreamReader sr = new InputStreamReader(is_)) {
            readFromFileWithGames(sr, listofGames_);
        }
        catch (Exception e_) {
            e_.printStackTrace();
        }
    }

    //Abstract method for classes to implement to read and add to GamesList
    abstract void readFromFileWithGames(Reader is_, GamesList listofPastGames_);

}
