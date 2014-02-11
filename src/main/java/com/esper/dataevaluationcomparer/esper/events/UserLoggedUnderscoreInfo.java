/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.esper.events;

/**
 *
 * @author tomas
 */
public class UserLoggedUnderscoreInfo {
    private String schema;
    private boolean success;
    private String sourceHost;
    private int sourcePort;
    private String user;
    
    public UserLoggedUnderscoreInfo() {
        this.schema = "";
        this.success = false;
        this.sourceHost = "";
        this.sourcePort = 0;
        this.user = "";
    }
        
        public UserLoggedUnderscoreInfo(String schema, boolean success, String sourceHost, int sourcePort, String user) {
            this.schema = schema;
            this.success = success;
            this.sourceHost = sourceHost;
            this.sourcePort = sourcePort;
            this.user = user;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getSourceHost() {
            return sourceHost;
        }

        public void setSourceHost(String sourceHost) {
            this.sourceHost = sourceHost;
        }

        public int getSourcePort() {
            return sourcePort;
        }

        public void setSourcePort(int sourcePort) {
            this.sourcePort = sourcePort;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
        
        @Override
        public String toString(){
            return " schema: " + schema +
                    " success: " + success +
                    " sourceHost: " + sourceHost +
                    " sourcePort: " + sourcePort +
                    " user: " + user;
        }
}
