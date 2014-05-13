/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper;

import com.esper.dataevaluationcomparer.Timer;
import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import com.esper.dataevaluationcomparer.esper.listeners.TestListener;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Esper {
    /**
     * This function tests speed of GROUP BY operator
     * @param events array of input test events
     * @param stopWatch timer to measure length of calculation
     * @return number of milliseconds equal to length of calculation
     */
    public long runGroupByTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        EPStatement testStatement = testStream.createStatement("select * " +
                                                "from Test.win:time(10 sec) " +
                                                "group by test");
        testStream.appendListener(testStatement, new TestListener());
        EPRuntime runtime = testStream.getRuntime();
        stopWatch.start();
        long old_time = -1;
        long time;
        for(TestEvent event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
        long timeElapsed = stopWatch.stop();
        System.out.println("Esper-GroupByTest: " + timeElapsed + " ms");
        testStream.cleanUp();
        return timeElapsed;
    }
    
    /**
     * This function tests speed of JOIN operator
     * @param events array of input test events
     * @param stopWatch timer to measure length of calculation
     * @return number of milliseconds equal to length of calculation
     */
    public long runJoinTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        testStream.createStatement("create window TestLeft.win:time(10 sec) as select * from " + TestEvent.class.getName());
        testStream.createStatement("create window TestRight.win:time(10 sec) as select * from " + TestEvent.class.getName());
        testStream.createStatement("insert into TestLeft select * from Test where b = \"l\"");
        testStream.createStatement("insert into TestRight select * from Test where b = \"r\"");
//        EPStatement testStatement = testStream.createStatement("select TestLeft.test, TestLeft.timestamp, " +
//                                                "TestRight.test, TestRight.timestamp " + 
//                                                "from TestLeft, TestRight");
        EPStatement testStatement = testStream.createStatement("select * " + 
                                                "from TestLeft, TestRight");
        testStream.appendListener(testStatement, new TestListener());
        EPRuntime runtime = testStream.getRuntime();
        stopWatch.start();
        long old_time = -1;
        long time;
        for(TestEvent event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
        long timeElapsed = stopWatch.stop();
        System.out.println("Esper-JoinTest: " + timeElapsed + " ms");
        testStream.cleanUp();
        return timeElapsed;
    }
    
    /**
     * This function tests speed of SEQUENCE operator
     * @param events array of input test events
     * @param stopWatch timer to measure length of calculation
     * @return number of milliseconds equal to length of calculation
     */
    public long runSequenceTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        EPStatement testStatement = testStream.createStatement("select * from pattern[" +
                                            "every Test(test=0) -> every Test(test=1) -> every Test(test=2)]");
        testStream.appendListener(testStatement, new TestListener());
        EPRuntime runtime = testStream.getRuntime();
        stopWatch.start();
        long old_time = -1;
        long time;
        for(TestEvent event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
        long timeElapsed = stopWatch.stop();
        System.out.println("Esper-SequenceTest: " + timeElapsed + " ms");
        testStream.cleanUp();
        return timeElapsed;
    }
    
    /**
     * This function tests speed of Aggregate operators
     * @param events array of input test events
     * @param stopWatch timer to measure length of calculation
     * @return number of milliseconds equal to length of calculation
     */
    public long runAggregateTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        EPStatement testStatement = testStream.createStatement("select max(test) " +
                                                "from Test.win:time(10 sec)");
        testStream.appendListener(testStatement, new TestListener());
        EPRuntime runtime = testStream.getRuntime();
        stopWatch.start();
        long old_time = -1;
        long time;
        for(TestEvent event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
        long timeElapsed = stopWatch.stop();
        System.out.println("Esper-AgregateTest: " + timeElapsed + " ms");
        testStream.cleanUp();
        return timeElapsed;
    }
    
    /**
     * This function tests speed of ORDER BY operator
     * @param events array of input test events
     * @param stopWatch timer to measure length of calculation
     * @return number of milliseconds equal to length of calculation
     */
    public long runOrderByTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        EPStatement testStatement = testStream.createStatement("select * " +
                                                "from Test.win:keepall() " +
                                                "output every 1000000 events " +
                                                "order by test");
        testStream.appendListener(testStatement, new TestListener());
        EPRuntime runtime = testStream.getRuntime();
        stopWatch.start();
        long old_time = -1;
        long time;
        for(TestEvent event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
        long timeElapsed = stopWatch.stop();
        System.out.println("Esper-SortTest: " + timeElapsed + " ms");
        testStream.cleanUp();
        return timeElapsed;
    }
    
    /**
     * This function tests speed of FIRST N operator
     * @param events array of input test events
     * @param stopWatch timer to measure length of calculation
     * @return number of milliseconds equal to length of calculation
     */
    public long runFirstNTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        EPStatement testStatement = testStream.createStatement("select * " +
                                                "from Test.win:firstlength(1000000)");
        testStream.appendListener(testStatement, new TestListener());
        EPRuntime runtime = testStream.getRuntime();
        stopWatch.start();
        long old_time = -1;
        long time;
        for(TestEvent event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
        long timeElapsed = stopWatch.stop();
        System.out.println("Esper-FirstNTest: " + timeElapsed + " ms");
        testStream.cleanUp();
        return timeElapsed;
    }
}
