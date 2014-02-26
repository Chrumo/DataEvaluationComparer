/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.listeners;

import com.esper.dataevaluationcomparer.esper.events.NumPerHourEvent;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.time.CurrentTimeEvent;

/**
 *
 * @author tomas
 */
public class LoggedHourListener implements UpdateListener  {
    EPRuntime runtime;
    long time;
    long old_time;
    
    public LoggedHourListener(EPRuntime r) {
        runtime = r;
        old_time = 0;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        for(EventBean eb : newData){
            long timestamp = Long.valueOf(eb.get("timestamp").toString());
            String host = (String) eb.get("host");
            boolean success = Boolean.valueOf(eb.get("success").toString());
            String user = (String) eb.get("user");
            int amount = Integer.valueOf(eb.get("count(*)").toString());
            NumPerHourEvent event = new NumPerHourEvent(timestamp, host, success, user, amount);
            CurrentTimeEvent timeEvent;
            time = timestamp;
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
    }
}
