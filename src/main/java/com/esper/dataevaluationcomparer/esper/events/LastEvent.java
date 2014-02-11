/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.events;

/**
 *
 * @author tomas
 */
public class LastEvent {
    private long timestamp;

    public LastEvent() {
        timestamp = -1;
    }

    public LastEvent(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Timestamp: " + timestamp;
    }
    
    
}
