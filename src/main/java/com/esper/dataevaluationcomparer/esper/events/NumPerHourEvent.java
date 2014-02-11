/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.events;

/**
 *
 * @author tomas
 */
public class NumPerHourEvent {
    long timestamp;
    String host;
    boolean success;
    String user;
    int amount;

    public NumPerHourEvent() {
        timestamp = -1;
        host = "";
        success = false;
        user = "";
        amount = 0;
    }

    public NumPerHourEvent(long timestamp, String host, boolean success, String user, int amount) {
        this.timestamp = timestamp;
        this.host = host;
        this.success = success;
        this.user = user;
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Host: " + host +
               ", success: " + success +
               ", user: " + user +
               ", amount: " + amount;
    }
}
