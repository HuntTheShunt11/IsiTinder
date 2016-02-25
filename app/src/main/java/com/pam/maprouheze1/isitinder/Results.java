package com.pam.maprouheze1.isitinder;

import java.util.List;

/**
 * Created by maprouheze1 on 27/01/2016.
 */
public class Results {

    private int currentIndex;

    public List<Result> results;
    public String nationality;
    public String seed;
    public String version;

    public Results() {
        currentIndex = 0;
    }

    public boolean hasNext() {
        return (currentIndex + 1) < results.size();
    }

    public Result next() {
        ++currentIndex;
        return results.get(currentIndex);
    }
    /*public void IncCurrentIndex(){
        ++currentIndex;
    }*/

    public int getCurrentIndex()
    {
        return currentIndex;
    }
}
