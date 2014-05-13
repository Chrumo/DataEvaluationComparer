/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer;

import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;

/**
 *
 * @author tomas
 */
public class LogIOoperations {
    //array, which I used while testing examples with string atribute test
    private static String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    
    /**
     * Loads events from file and convert it from JSON format to Java object
     * @param filename
     * @return list of test events
     */
    public static List<TestEvent> getArrayOfTestEventsFromFile(String filename) {
        BufferedReader br = null;
        ArrayList<TestEvent> tests = new ArrayList<TestEvent>();
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.exit(1);
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectReader reader = mapper.reader(TestEvent.class);
            String line = br.readLine();
            while(line != null){
                TestEvent obj;
                obj = reader.readValue(line);
                tests.add(obj);
                line = br.readLine();
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                System.exit(1);
            }
        }
        return tests;
    }
    
    /**
     * Generate file named test.log containing randomly generated events in JSON format
     * @param length number of events
     * @param seed seed to be generated by
     */
    public static void generateTestFile(long length, long seed){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("test.log", false));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        String text;
        int randomNumber, randomDirection;
        Random randNum = new Random(seed);
        System.out.println("Generating file with seed " + seed + "...");
        long ts = -1;
        try{
            for(int i = 0; i < length; i++){
                if(i % 150 == 0){
                    ts++;
                }
                randomNumber = randNum.nextInt(342);
                randomDirection = randNum.nextInt(2);
                text = "{\"test\": \"" + randomNumber + "\", \"timestamp\": " + ts + ", \"b\": \"" + (randomDirection == 0 ? "l" : "r") + "\"}";
                bw.write(text, 0, text.length());
                bw.newLine();
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                System.exit(1);
            }
        }
        System.out.println("File generated.");
    }
    
    /**
     * Generates array of test events, comment or uncomment if you want random events or not
     * @param length number of events
     * @param distinctTests max value of test attribute, for example if equals 7, test is from interval < 0,6 >
     * @param seed seed to be generated by
     * @return array of generated test events
     */
    public static ArrayList<TestEvent> generateTestArray(long length, int distinctTests, long seed){
        ArrayList<TestEvent> tests = new ArrayList<TestEvent>();
        int randomNumber;
        Random randNum = new Random(seed);
        TestEvent te;
        long ts = -1;
        System.out.println("Generating array with seed " + seed + "...");
        for(int i = 0; i < length; i++){
            if(i % 150 == 0){
                ts++;
            }
            te = new TestEvent();
            randomNumber = randNum.nextInt(distinctTests);
            te.setTimestamp(ts);
            //te.setTest(randomNumber);
            te.setTest(length - i);
            //te.setTest(i % 3);
            te.setB((i % 2 == 0 ? "l" : "r"));
            tests.add(te);
        }
        System.out.println("Array generated.");
        return tests;
    }
}
