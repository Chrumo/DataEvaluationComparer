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
 *
 * @author tomas
 */
public class MyEventStream {
    private EPAdministrator cepAdm;
    private List<EPStatement> listOfStatements;
    private EPRuntime cepRT;
    
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
    
    public EPStatement createStatement(String statement) {
        EPStatement returnValue = cepAdm.createEPL(statement);
        listOfStatements.add(returnValue);
        return returnValue;
    }
    
    public void appendListener(EPStatement statement, UpdateListener listener) {
        for(EPStatement stat : listOfStatements){
            if(stat.equals(statement)) {
                stat.addListener(listener);
            }
        }
    }
    
    public EPRuntime getRuntime() {
        return cepRT;
    }
    
    public void sendEvent(Object event) {
        cepRT.sendEvent(event);
    }
    
    public void cleanUp(){
        cepAdm.destroyAllStatements();
    }
}
