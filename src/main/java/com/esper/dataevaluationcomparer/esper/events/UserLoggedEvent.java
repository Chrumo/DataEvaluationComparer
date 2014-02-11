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
public class UserLoggedEvent {
    @JsonProperty("Event")
    private UserLoggedInfo event;
    
    public UserLoggedEvent(){
        event = new UserLoggedInfo();
    }
    
    public UserLoggedEvent(UserLoggedInfo event){
        this.event = event;
    }

    public UserLoggedInfo getEvent() {
        return event;
    }
    
    public long getTimestamp() {
        return event.getTimestamp();
    }

    public void setEvent(UserLoggedInfo event) {
        this.event = event;
    }
    
    @Override
    public String toString(){
        return event.toString();
    }
}
