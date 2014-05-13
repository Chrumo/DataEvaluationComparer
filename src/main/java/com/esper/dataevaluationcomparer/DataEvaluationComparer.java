package com.esper.dataevaluationcomparer;

import com.esper.dataevaluationcomparer.esper.Esper;
import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import java.util.List;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * Application to test speed and function of event stream operator in Esper
 *
 */
public class DataEvaluationComparer 
{
    private static final String testFilename = "test.log";
    
    public static void main( String[] args )
    {
        ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel((Level) Level.WARN);
        long timeElapsed = 0;
        Timer stopWatch = new Timer();
        stopWatch.start();
        //LogIOoperations.generateTestFile(20, 1);
        stopWatch.start();
        //List<TestEvent> testEvents = LogIOoperations.getArrayOfTestEventsFromFile(testFilename);
        List<TestEvent> testEvents = LogIOoperations.generateTestArray(500000, 500, 1);
        //System.out.println(testEvents.toString()); //verifying generated array
        timeElapsed = stopWatch.stop();
        System.out.println("Elapsed: " + timeElapsed + " ms");
        System.out.println("Number of events: " + testEvents.size());
        
        //-------------------------
        //comment/uncommet test you want to test, you can't test more than one test at time
        Esper esper = new Esper();
        esper.runGroupByTest(testEvents, stopWatch);
        //esper.runJoinTest(testEvents, stopWatch);
        //esper.runSequenceTest(testEvents, stopWatch);
        //esper.runAggregateTest(testEvents, stopWatch);
        //esper.runOrderByTest(testEvents, stopWatch);
        //esper.runFirstNTest(testEvents, stopWatch);
    }
}
