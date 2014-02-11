/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.events;

import java.util.Date;

/**
 *
 * @author tomas
 */
public class PossibleThreatEvent {
    private Date occurrenceTime;
    private long timestamp;
    private String type;
    private String host;
    private String schema;
    private String value1;
    private int value2;
    private String application;
    private int level;
    
    public PossibleThreatEvent() {
        this.occurrenceTime = new Date();
        this.timestamp = 0;
        this.type = "";
        this.host = "";
        this.schema = "";
        this.value1 = "";
        this.value2 = 42;
        this.application = "";
        this.level = -1;
    }

    public PossibleThreatEvent(long timestamp, String type, String host, String schema, String value1, int value2, String application, int level) {
        this.occurrenceTime = new Date();
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
        this.schema = schema;
        this.value1 = value1;
        this.value2 = value2;
        this.application = application;
        this.level = level;
    }

    public Date getOccurrenceTime() {
       return occurrenceTime;
    }
    
    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public String getSchema() {
        return schema;
    }

    public String getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }

    public String getApplication() {
       return application;
    }

    public int getLevel() {
        return level;
    }

    public void setOccurrenceTime(Date occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    @Override
    public String toString() {
        return "Occurence time: " + occurrenceTime +
               ", type: " + type +
               ", host: " + host +
               ", schema: " + schema +
               ", value1: " + value1 +
               ", value2: " + value2 +
               ", application: " + application +
               ", level: " + level;
    }
}
