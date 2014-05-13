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
    //number of events this listener has catched
    private int count;
    
    /**
     * Constructor, create new Object setting count to zero
     */
    public TestListener(){
        count = 0;
    }
    
    //comment or uncommend what you want to see from event which was catched by this listener
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
//        System.out.println(newData.length);
        for(EventBean eb : newData){
//            long timestamp = Long.valueOf(eb.get("timestamp").toString());
////            int count = Integer.valueOf(eb.get("count(*)").toString());
            String test = eb.get("test").toString();
////            System.out.println("Timestamp: " + timestamp + " count: " + count + " test: " + test);
//            System.out.println("Timestamp: " + timestamp + " test: " + test);
//            System.out.print("matched ");
//            String lTest = eb.get("TestLeft.test").toString();
//            String rTest = eb.get("TestRight.test").toString();
//            long ltms = Long.valueOf(eb.get("TestLeft.timestamp").toString());
//            long rtms = Long.valueOf(eb.get("TestRight.timestamp").toString());
//            System.out.println(ltms + " left: " + lTest + " " + rtms + " right: " + rTest);
  //          System.out.println(eb.get("min(test)").toString() + " " + timestamp);
        }
//        count += newData.length;
//        System.out.println(count);
        
    }
}
