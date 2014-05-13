/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.events;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author tomas
 */
public class TestEvent {
    //attribute used for test purpose
    @JsonProperty("test")
    private long test;
    //timestamp of creation of event
    @JsonProperty("timestamp")
    private long timestamp;
    //test to which stream is this event to send
    @JsonProperty("b")
    private String b;

    /**
     * Constructor, create new object with default values test = timestamp = -1
     */
    public TestEvent() {
        test = -1;
        timestamp = -1;
    }

    /**
     * Constructor, create new object with given values
     * @param test
     * @param timestamp 
     */
    public TestEvent(long test, long timestamp) {
        this.test = test;
        this.timestamp = timestamp;
    }

    public long getTest() {
        return test;
    }

    public void setTest(long test) {
        this.test = test;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Timestamp: " + timestamp + " test: " + test + " b: " + b;
    }
}
