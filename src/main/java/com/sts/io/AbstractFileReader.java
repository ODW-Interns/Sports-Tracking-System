package com.sts.io;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/*
 * Abstract class used by classes that will read from files with games listed
 * */
public abstract class AbstractFileReader<T> {

	//method to check that the input file and Games List structure exists
    public void readData(InputStream is_, ArrayList<T> list_) throws FileNotFoundException, RuntimeException {
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

    //Abstract method for classes to implement to read and add to GamesList
    abstract void readFromFileForLists(Reader is_, ArrayList<T> games_);
}
