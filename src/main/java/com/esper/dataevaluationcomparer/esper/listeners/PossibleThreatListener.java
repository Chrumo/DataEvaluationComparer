/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.listeners;

import com.esper.dataevaluationcomparer.esper.events.PossibleThreatEvent;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.time.CurrentTimeEvent;

/**
 *
 * @author tomas
 */
public class PossibleThreatListener  implements UpdateListener {
    EPRuntime runtime;
        long time;
        long old_time;
        
        public PossibleThreatListener(EPRuntime r) {
            runtime = r;
            old_time = 0;
        }
        
        @Override
        public void update(EventBean[] newData, EventBean[] oldData) {
            EventBean eb = newData[0];
            long timestamp = Long.valueOf(eb.get("timestamp").toString());
            String type = (String) eb.get("type");
            String host = (String) eb.get("host");
            String schema = (String) eb.get("schema");
            String value1 = "ahoj";
            int value2 = 42;
            String application = (String) eb.get("application");
            int level = Integer.valueOf(eb.get("level").toString());
            PossibleThreatEvent event = new PossibleThreatEvent(timestamp, type, host, schema, value1, value2, application, level);
            CurrentTimeEvent timeEvent;
            time = timestamp;
            timeEvent = new CurrentTimeEvent(time);
            if(time > old_time){
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
}
