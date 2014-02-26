/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.listeners;

import com.esper.dataevaluationcomparer.esper.events.UserLoggedInfo;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.time.CurrentTimeEvent;

/**
 *
 * @author tomas
 */
public class NotLoggedListener implements UpdateListener {
    EPRuntime runtime;
    long time;
    long old_time;
    
    public NotLoggedListener(EPRuntime r) {
        runtime = r;
        old_time = 0;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        for(EventBean eb : newData){
            long timestamp = Long.valueOf(eb.get("timestamp").toString());
            String type = (String) eb.get("type");
            String host = (String) eb.get("host");
            String schema = (String) eb.get("schema");
            boolean success = Boolean.valueOf(eb.get("success").toString());
            String sourceHost = (String) eb.get("sourceHost");
            int sourcePort = Integer.valueOf(eb.get("sourcePort").toString());
            String user = (String) eb.get("user");
            String application = (String) eb.get("application");
            int level = Integer.valueOf(eb.get("level").toString());
            UserLoggedInfo event = new UserLoggedInfo(timestamp, type, host, schema, success, sourceHost, sourcePort, user, application, level);
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
