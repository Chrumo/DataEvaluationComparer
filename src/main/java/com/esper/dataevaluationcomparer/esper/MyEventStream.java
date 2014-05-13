/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is working directly with Esper library
 * @author tomas
 */
public class MyEventStream {
    //administrator tool
    private final EPAdministrator cepAdm;
    //list of statements to append a listener on them
    private final List<EPStatement> listOfStatements;
    //runtime to send events
    private final EPRuntime cepRT;
    
    /**
     * Constructor
     * @param eventType name of event type using by Esper
     * @param streamName name of the stream using by Esper
     * @param className Java class name
     */
    public MyEventStream(String eventType, String streamName, String className) {
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType(eventType, className);
        cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider(streamName, cepConfig);
        cepRT = cep.getEPRuntime();
        cepAdm = cep.getEPAdministrator();
        listOfStatements = new ArrayList<EPStatement>();
    }
    
    /**
     * Creates statement from event processing language expression
     * @param statement event processing language expression
     * @return created statement
     */
    public EPStatement createStatement(String statement) {
        EPStatement returnValue = cepAdm.createEPL(statement);
        listOfStatements.add(returnValue);
        return returnValue;
    }
    
    /**
     * Appends listener to existing statement
     * @param statement statement to append listener on
     * @param listener listener to be appended on statement
     */
    public void appendListener(EPStatement statement, UpdateListener listener) {
        for(EPStatement stat : listOfStatements){
            if(stat.equals(statement)) {
                stat.addListener(listener);
            }
        }
    }
    
    /**
     * getter for attribute runtime
     * @return cepRT attribute
     */
    public EPRuntime getRuntime() {
        return cepRT;
    }
    
    /**
     * Function which allow to send events to stream
     * @param event event to be send to the stream
     */
    public void sendEvent(Object event) {
        cepRT.sendEvent(event);
    }
    
    /**
     * Cleans up all statements destroying also listeners appended to them
     */
    public void cleanUp(){
        cepAdm.destroyAllStatements();
    }
}
