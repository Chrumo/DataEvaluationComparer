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

/**
 *
 * @author tomas
 */
public class ThreatListener implements UpdateListener  {
    EPRuntime runtime;
    long time;
    long old_time;
    int count;
    
    public ThreatListener(EPRuntime r){
        runtime = r;
        old_time = 0;
        count = 0;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        for(EventBean eb : newData){
            long timestamp = Long.valueOf(eb.get("timestamp").toString());
            LastEvent event = new LastEvent(timestamp);
            CurrentTimeEvent timeEvent;
            time = timestamp;
            timeEvent = new CurrentTimeEvent(time);
            if(time > old_time){
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
//            count++;
//            System.out.println(count);
        }
    }
}
