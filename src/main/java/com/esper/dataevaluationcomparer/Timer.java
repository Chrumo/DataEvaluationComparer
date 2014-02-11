/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer;

/**
 *
 * @author tomas
 */
public class Timer {
    private long startTime;
    
    public Timer() {
        startTime = 0;
    }
    
    public void start() {
        startTime = System.currentTimeMillis();
    }
    
    public long stop() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - startTime);
    }
    
    public void restart() {
        startTime = 0;
    }

    void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
