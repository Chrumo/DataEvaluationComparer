/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.events;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author tomas
 */
public class UserLoggedInfo {
    private Date occurrenceTime;
    private long timestamp;
    private String type;
    private String host;
    @JsonProperty("_")
    private UserLoggedUnderscoreInfo info;
    private String application;
    private int level;

    public UserLoggedInfo(){
        this.occurrenceTime = new Date();
        this.timestamp = 0;
        this.type = "";
        this.host = "";
        this.info = new UserLoggedUnderscoreInfo();
        this.application = "";
        this.level = 0;
    }
    
    public UserLoggedInfo(long timestamp, String type, String host, String schema, boolean success, String sourceHost, int sourcePort, String user, String application, int level) {
        this.occurrenceTime = new Date();
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
        this.info = new UserLoggedUnderscoreInfo(schema, success, sourceHost, sourcePort, user);
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

    public String getApplication() {
        return application;
    }

    public int getLevel() {
        return level;
    }

    public void setOccurrenceTime(Date occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
        this.timestamp = occurrenceTime.getTime();
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

    public void setApplication(String application) {
        this.application = application;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public boolean isSuccess() {
        return info.isSuccess();
    }
    
    public String getUser() {
        return info.getUser();
    }
    
    public String getSchema() {
        return info.getSchema();
    }
    
    public String getSourceHost() {
        return info.getSourceHost();
    }
    
    public int getSourcePort() {
        return info.getSourcePort();
    }
    
    @Override
    public String toString(){
        return "occurenceTime: " + occurrenceTime +
                " type: " + type +
                " host: " + host +
                info.toString() +
                " application: " + application +
                " level: " + level;
    }
}
