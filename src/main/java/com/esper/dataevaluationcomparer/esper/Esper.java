/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper;

import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import com.esper.dataevaluationcomparer.Timer;
import com.esper.dataevaluationcomparer.esper.events.LastEvent;
import com.esper.dataevaluationcomparer.esper.events.NumPerHourEvent;
import com.esper.dataevaluationcomparer.esper.events.PossibleThreatEvent;
import com.esper.dataevaluationcomparer.esper.events.UserLoggedInfo;
import com.esper.dataevaluationcomparer.esper.listeners.DivideListener;
import com.esper.dataevaluationcomparer.esper.listeners.LastListener;
import com.esper.dataevaluationcomparer.esper.listeners.LoggedHourListener;
import com.esper.dataevaluationcomparer.esper.listeners.LoggedStatisticsListener;
import com.esper.dataevaluationcomparer.esper.listeners.NotLoggedHourListener;
import com.esper.dataevaluationcomparer.esper.listeners.NotLoggedListener;
import com.esper.dataevaluationcomparer.esper.listeners.NotLoggedStatisticsListener;
import com.esper.dataevaluationcomparer.esper.listeners.PossibleThreatListener;
import com.esper.dataevaluationcomparer.esper.listeners.TestListener;
import com.esper.dataevaluationcomparer.esper.listeners.ThreatListener;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Esper {
    public void runAntiDictAttack(List<UserLoggedInfo> events, Timer t) {
        MyEventStream notLoggedStream = new MyEventStream("UserLogged", "filter", UserLoggedInfo.class.getName());
        MyEventStream possibleThreatStream = new MyEventStream("UserNotLogged", "possibleThreat", UserLoggedInfo.class.getName());
        MyEventStream threatStream = new MyEventStream("PossibleThreat", "threat", PossibleThreatEvent.class.getName());
        MyEventStream lastStream = new MyEventStream("PatternMatched", "checkLast", LastEvent.class.getName());
        EPStatement notLoggedStatement = notLoggedStream.createStatement("select * from UserLogged " +
                                      "where success = false");
        EPStatement possibleThreatStatement = possibleThreatStream.createStatement("select *, count(*) " +
                                "from UserNotLogged.win:time(60 sec) " +
                                "group by user, host " + 
                                "having count(*) > 1000");
        EPStatement threatStatement = threatStream.createStatement("select timestamp, host, count(*) " +
                                "from PossibleThreat.win:time(120 sec) " +
                                "group by host " +
                                "having count(*) > 10");
        EPStatement lastStatement = lastStream.createStatement("select timestamp " +
                                "from PatternMatched.win:length(42)");
        notLoggedStream.appendListener(notLoggedStatement, new NotLoggedListener(possibleThreatStream.getRuntime()));
        possibleThreatStream.appendListener(possibleThreatStatement, new PossibleThreatListener(threatStream.getRuntime()));
        threatStream.appendListener(threatStatement, new ThreatListener(lastStream.getRuntime()));
        lastStream.appendListener(lastStatement, new LastListener(t));
        EPRuntime runtime = notLoggedStream.getRuntime();
        t.start();
        long old_time = 0;
        long time;
        for(UserLoggedInfo event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
    }
    //output snapshot
    public void runSystemLoadStatistics(List<UserLoggedInfo> events, Timer stopWatch) {
        MyEventStream dividerStream = new MyEventStream("StatusDivider", "divider", UserLoggedInfo.class.getName());
        MyEventStream loggedHourStream = new MyEventStream("LoggedHourCounter", "loggedHour", UserLoggedInfo.class.getName());
        MyEventStream loggedStream = new MyEventStream("LoggedStatistics", "loggedStat", NumPerHourEvent.class.getName());
        MyEventStream notLoggedHourStream = new MyEventStream("NotLoggedHourCounter", "notLoggedHour", UserLoggedInfo.class.getName());
        MyEventStream notLoggedStream = new MyEventStream("NotLoggedStatistics", "notLoggedStat", NumPerHourEvent.class.getName());
        MyEventStream lastStream = new MyEventStream("PatternMatched", "checkLast", LastEvent.class.getName());
        EPStatement dividerStatement = dividerStream.createStatement("select * from StatusDivider");
        EPStatement loggedHourStatement = loggedHourStream.createStatement("select *, count(*) " +
                                "from LoggedHourCounter.win:time_batch(1 sec)");
        EPStatement loggedStatement = loggedStream.createStatement("select timestamp, host, success, user, count(*), " +
                                "avg(amount), max(amount), min(amount) " +
                                "from LoggedStatistics.win:time_batch(60 sec)");
        EPStatement notLoggedHourStatement = notLoggedHourStream.createStatement("select *, count(*) " +
                                "from NotLoggedHourCounter.win:time_batch(1 sec)");
        EPStatement notLoggedStatement = notLoggedStream.createStatement("select timestamp, host, success, user, count(*), " +
                                "avg(amount), max(amount), min(amount) " +
                                "from NotLoggedStatistics.win:time_batch(60 sec)");
        EPStatement lastStatement = lastStream.createStatement("select timestamp " +
                                "from PatternMatched.win:length(42)");
        dividerStream.appendListener(dividerStatement, new DivideListener(loggedHourStream.getRuntime(), notLoggedHourStream.getRuntime()));
        loggedHourStream.appendListener(loggedHourStatement, new LoggedHourListener(loggedStream.getRuntime()));
        loggedStream.appendListener(loggedStatement, new LoggedStatisticsListener(lastStream.getRuntime()));
        notLoggedHourStream.appendListener(notLoggedHourStatement, new NotLoggedHourListener(notLoggedStream.getRuntime()));
        notLoggedStream.appendListener(notLoggedStatement, new NotLoggedStatisticsListener(lastStream.getRuntime()));
        lastStream.appendListener(lastStatement, new LastListener(stopWatch));
        EPRuntime runtime = dividerStream.getRuntime();
        stopWatch.start();
        long old_time = 0;
        long time;
        for(UserLoggedInfo event : events) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
    }
    
    public void runGroupByTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        EPStatement testStatement = testStream.createStatement("select timestamp, test, count(*) " +
                                                "from Test.win:length(4) " +
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
    }
    
    public void runJoinTest(List<TestEvent> eventsLeft, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        testStream.createStatement("create window TestLeft.win:keepall() as select * from " + TestEvent.class.getName());
        testStream.createStatement("create window TestRight.win:keepall() as select * from " + TestEvent.class.getName());
        testStream.createStatement("insert into TestLeft select * from Test where b = \"Left\"");
        testStream.createStatement("insert into TestRight select * from Test where b = \"Right\"");
        EPStatement testStatementLeft = testStream.createStatement("select TestLeft.test, TestLeft.timestamp, " +
                                                "TestRight.test, TestRight.timestamp " + 
                                                "from TestLeft right outer join TestRight");
        testStream.appendListener(testStatementLeft, new TestListener());
        EPRuntime runtime = testStream.getRuntime();
        stopWatch.start();
        long old_time = -1;
        long time;
        for(TestEvent event : eventsLeft) {
            CurrentTimeEvent timeEvent;
            time = event.getTimestamp();
            if(time > old_time){
                timeEvent = new CurrentTimeEvent(time);
                old_time = time;
                runtime.sendEvent(timeEvent);
            }
            runtime.sendEvent(event);
        }
    }
    
    public void runSequenceTest(List<TestEvent> events, Timer stopWatch) {
        MyEventStream testStream = new MyEventStream("Test", "test", TestEvent.class.getName());
        EPStatement testStatement = testStream.createStatement("select * from pattern[every Test(test='a') " +
                                            "-> every Test(test='b')]");
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
    }
}
