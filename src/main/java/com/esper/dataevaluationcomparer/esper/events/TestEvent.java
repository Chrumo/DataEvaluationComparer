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
    @JsonProperty("test")
    private String test;
    
    @JsonProperty("timestamp")
    private long timestamp;
    
    @JsonProperty("b")
    private String b;

    public TestEvent() {
        test = "";
        timestamp = -1;
    }

    public TestEvent(String test, long timestamp) {
        this.test = test;
        this.timestamp = timestamp;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
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
