package com.esper.dataevaluationcomparer;

import com.esper.dataevaluationcomparer.esper.Esper;
import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import com.esper.dataevaluationcomparer.esper.events.UserLoggedInfo;
import com.esper.dataevaluationcomparer.postgresql.PostgreSQL;
import java.util.ArrayList;
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
        stopWatch.start();
        List<UserLoggedInfo> events = LogIOoperations.getArrayOfEventsFromFile(filename);
        long timeElapsed = stopWatch.stop();
        System.out.println("Elapsed: " + timeElapsed + " ms");
        System.out.println("Number of events: " + events.size());
        /*stopWatch.start();
        List<TestEvent> testEvents = LogIOoperations.getArrayOfTestEventsFromFile(testFilename);
        timeElapsed = stopWatch.stop();
        System.out.println("Elapsed: " + timeElapsed + " ms");
        System.out.println("Number of events: " + testEvents.size());*/
        
        //-------------------------
        
        //Esper esper = new Esper();
        //esper.runAntiDictAttack(events, stopWatch);
        //esper.runSystemLoadStatistics(events, stopWatch);
        //esper.runGroupByTest(testEvents, stopWatch);
        //esper.runJoinTest(testEvents, stopWatch);
        //esper.runSequenceTest(testEvents, stopWatch);
        
        //-------------------------
        
        PostgreSQL db = new PostgreSQL();
        db.runAntiDictAttack(events, stopWatch);
        //db.runSystemLoadStatistics(events, stopWatch);
        //db.runGroupByTest(testEvents, stopWatch);
        //db.runJoinTest(testEvents, stopWatch);
        //db.runSequenceSQL(testEvents, stopWatch);
    }
}
