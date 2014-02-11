/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.listeners;

import com.esper.dataevaluationcomparer.esper.events.LastEvent;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.time.CurrentTimeEvent;
import java.util.Date;

/**
 *
 * @author tomas
 */
public class NotLoggedStatisticsListener implements UpdateListener {
    EPRuntime runtime;
    long time;
    long old_time;
    
    public NotLoggedStatisticsListener(EPRuntime r) {
        runtime = r;
        old_time = 0;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        EventBean eb = newData[0];
        long timestamp = Long.valueOf(eb.get("timestamp").toString());
        boolean success = Boolean.valueOf(eb.get("success").toString());
        int amount = Integer.valueOf(eb.get("count(*)").toString());
        double avg = Double.valueOf(eb.get("avg(amount)").toString());
        int max = Integer.valueOf(eb.get("max(amount)").toString());
        int min = Integer.valueOf(eb.get("min(amount)").toString());
        System.out.println("---------------------------------");
        System.out.println("timestamp: " + new Date(timestamp).toString());
        System.out.println("success: " + success);
        System.out.println("amount: " + amount);
        System.out.println("avg: " + avg);
        System.out.println("max: " + max);
        System.out.println("min: " + min);
        System.out.println("*********************************");
        LastEvent event = new LastEvent(timestamp);
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
