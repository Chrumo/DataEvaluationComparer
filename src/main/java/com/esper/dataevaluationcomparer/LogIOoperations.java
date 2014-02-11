/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer;

import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import com.esper.dataevaluationcomparer.esper.events.UserLoggedEvent;
import com.esper.dataevaluationcomparer.esper.events.UserLoggedInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;

/**
 *
 * @author tomas
 */
public class LogIOoperations {
    
    public static List<UserLoggedInfo> getArrayOfEventsFromFile(String filename) {
        BufferedReader br = null;
        ArrayList<UserLoggedInfo> users = new ArrayList<UserLoggedInfo>();
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.exit(1);
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectReader reader = mapper.reader(UserLoggedEvent.class);
            String line = br.readLine();
            while(line != null){
                UserLoggedEvent obj;
                if(line.contains("success")){
                    obj = reader.readValue(line);
                }else{
                    line = br.readLine();
                    continue;
                }
                users.add(obj.getEvent());
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
        return users;
    }
    
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
    
    public static void writeToFile(String filename, String text){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LogIOoperations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        try{
            bw.write(text, 0, text.length());
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
    }
}
