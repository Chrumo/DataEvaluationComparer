/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.listeners;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 *
 * @author tomas
 */
public class TestListener implements UpdateListener{
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        EventBean eb = newData[0];
//        long timestamp = Long.valueOf(eb.get("timestamp").toString());
//        int count = Integer.valueOf(eb.get("count(*)").toString());
//        String test = eb.get("test").toString();
//        System.out.println("Timestamp: " + timestamp + " count: " + count + " test: " + test);
////        System.out.print("matched ");
        String lTest = eb.get("TestLeft.test").toString();
        String rTest = eb.get("TestRight.test").toString();
        long ltms = Long.valueOf(eb.get("TestLeft.timestamp").toString());
        long rtms = Long.valueOf(eb.get("TestRight.timestamp").toString());
//        System.out.println(ltms + " left: " + lTest);
        System.out.println(ltms + " left: " + lTest + " " + rtms + " right: " + rTest);
    }
}
