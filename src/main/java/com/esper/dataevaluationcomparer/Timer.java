package com.esper.dataevaluationcomparer;

/**
 * This class represents timer. It has various functions needs to properly start and stop timer.
 * @author tomas
 */
public class Timer {
    //unix timestamp
    private long startTime;
    
    /**
     * Constructor constructs Timer object setting startTime to zero
     */
    public Timer() {
        startTime = 0;
    }
    
    /**
     * Function to start the timer
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Function to stop the timer
     * @return number of measured milliseconds
     */
    public long stop() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - startTime);
    }
    
    /**
     * Function to restart the times, sets startTime back to zero
     */
    public void restart() {
        startTime = 0;
    }
}
