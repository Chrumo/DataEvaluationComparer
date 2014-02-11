/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.listeners;

import com.esper.dataevaluationcomparer.Timer;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 *
 * @author tomas
 */
public class LastListener implements UpdateListener  {
    Timer stopWatch;
    EPRuntime runtime;
    long time;
    long old_time;
    int count;
    
    public LastListener(Timer t) {
        stopWatch = t;
        count = 0;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        long elapsedTime = stopWatch.stop();
        //System.out.println("Elapsed: " + elapsedTime + " ms");
    }
}
