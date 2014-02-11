package com.esper.dataevaluationcomparer;

import com.esper.dataevaluationcomparer.esper.Esper;
import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import com.esper.dataevaluationcomparer.esper.events.UserLoggedInfo;
import com.esper.dataevaluationcomparer.postgresql.PostgreSQL;
import java.util.List;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * Hello world!
 *
 */
public class DataEvaluationComparer 
{
    private static final String filename = "jel.log";
    private static final String testFilename = "test.log";
    
    public static void main( String[] args )
    {
        ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel((Level) Level.WARN);
        Timer stopWatch = new Timer();
        /*stopWatch.start();
        List<UserLoggedInfo> events = LogIOoperations.getArrayOfEventsFromFile(filename);
        long timeElapsed = stopWatch.stop();
        System.out.println("Elapsed: " + timeElapsed + " ms");
        System.out.println("Number of events: " + events.size());*/
        stopWatch.start();
        List<TestEvent> testEvents = LogIOoperations.getArrayOfTestEventsFromFile(testFilename);
        long timeElapsed = stopWatch.stop();
        System.out.println("Elapsed: " + timeElapsed + " ms");
        System.out.println("Number of events: " + testEvents.size());
        Esper esper = new Esper();
        /*stopWatch.start();
        esper.runAntiDictAttack(events, stopWatch);
        timeElapsed = stopWatch.stop();
        System.out.println("Esper-AntiDictAttack: " + timeElapsed + " ms");*/
        /*stopWatch.start();
        esper.runSystemLoadStatistics(events, stopWatch);
        timeElapsed = stopWatch.stop();
        System.out.println("Esper-SystemLoadStatistics: " + timeElapsed + " ms");*/
        /*stopWatch.start();
        esper.runGroupByTest(testEvents, stopWatch);
        timeElapsed = stopWatch.stop();
        System.out.println("Esper-GroupByTest: " + timeElapsed + " ms");*/
        stopWatch.start();
        esper.runJoinTest(testEvents, stopWatch);
        timeElapsed = stopWatch.stop();
        System.out.println("Esper-JoinTest: " + timeElapsed + " ms");
        /*stopWatch.start();
        esper.runSequenceTest(testEvents, stopWatch);
        timeElapsed = stopWatch.stop();
        System.out.println("Esper-SequenceTest: " + timeElapsed + " ms");*/
        
        //-------------------------
        /*
        PostgreSQL db = new PostgreSQL();
        db.runPostgreSQL();*/
    }
}
