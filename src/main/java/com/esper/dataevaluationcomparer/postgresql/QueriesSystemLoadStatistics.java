/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esper.dataevaluationcomparer.postgresql;

/**
 *
 * @author tomas
 */
public class QueriesSystemLoadStatistics {
    
    public static final String SYSTEMLOADSTATISTICS_DROP_TABLES = "DROP TABLE IF EXISTS userLogged, userNotLogged, possibleThreat, threat";

    public static final String SYSTEMLOADSTATISTICS_DROP_USER_LOGGED_TRIGGER = "DROP TRIGGER IF EXISTS userLoggedTrigger ON userLogged";

    public static final String SYSTEMLOADSTATISTICS_DROP_USER_NOT_LOGGED_TRIGGER = "DROP TRIGGER IF EXISTS userLoggedNotTrigger ON userNotLogged";

    public static final String SYSTEMLOADSTATISTICS_DROP_USER_POSSIBLE_THREAT_TRIGGER = "DROP TRIGGER IF EXISTS possibleThreatTrigger ON possibleThreat";

    public static final String SYSTEMLOADSTATISTICS_DROP_ISUSERLOGGED_FUNCTION = "DROP FUNCTION IF EXISTS isUserLogged()";

    public static final String SYSTEMLOADSTATISTICS_DROP_ISPOSSIBLETHREAT_FUNCTION = "DROP FUNCTION IF EXISTS isPossibleThreat()";

    public static final String SYSTEMLOADSTATISTICS_DROP_ISTHREAT_FUNCTION = "DROP FUNCTION IF EXISTS isThreat()";

    public static final String SYSTEMLOADSTATISTICS_CREATE_TABLE_USER_LOGGED = "CREATE TABLE IF NOT EXISTS userLogged (" +
                                                                            "id serial PRIMARY KEY," +
                                                                            "timestamp bigint," +
                                                                            "type varchar(40)," +
                                                                            "host varchar(20)," +
                                                                            "schema varchar(50)," +
                                                                            "success boolean," +
                                                                            "sourceHost varchar(20)," +
                                                                            "sourcePort int," +
                                                                            "user_ varchar(15)," +
                                                                            "application varchar(40)," +
                                                                            "level int" +
                                                                          ")";

    public static final String SYSTEMLOADSTATISTICS_CREATE_TABLE_USER_NOT_LOGGED = "CREATE TABLE IF NOT EXISTS userNotLogged (" +
                                                                            "id serial PRIMARY KEY," +
                                                                            "timestamp bigint," +
                                                                            "type varchar(40)," +
                                                                            "host varchar(20)," +
                                                                            "schema varchar(50)," +
                                                                            "success boolean," +
                                                                            "sourceHost varchar(20)," +
                                                                            "sourcePort int," +
                                                                            "user_ varchar(15)," +
                                                                            "application varchar(40)," +
                                                                            "level int" +
                                                                          ")";

    public static final String SYSTEMLOADSTATISTICS_CREATE_TABLE_POSSIBLE_THREAT = "CREATE TABLE IF NOT EXISTS possibleThreat (" +
                                                                               "id serial PRIMARY KEY," +
                                                                               "timestamp bigint," +
                                                                               "host varchar(20)," +
                                                                               "schema varchar(50)," +
                                                                               "value1 varchar(10)," +
                                                                               "value2 int," +
                                                                               "application varchar(40)," +
                                                                               "level int" +
                                                                             ")";

    public static final String SYSTEMLOADSTATISTICS_CREATE_TABLE_THREAT = "CREATE TABLE IF NOT EXISTS threat (" +
                                                                      "timestamp bigint," +
                                                                      "host varchar(20)," +
                                                                      "count_ int" +
                                                                    ")";
    //for table userLogged
    public static final String SYSTEMLOADSTATISTICS_CREATE_FUNCTION_ISUSERLOGGED = "CREATE OR REPLACE FUNCTION isUserLogged() RETURNS TRIGGER AS $userLogged$" +
                                                                               "BEGIN " +
                                                                                 "INSERT INTO userNotLogged(timestamp, type, host, schema, success, sourceHost, sourcePort, user_, application, level) SELECT timestamp, type, host, schema, success, sourceHost, sourcePort, user_, application, level FROM userLogged WHERE success = false;" +
                                                                                 "RETURN NEW;" +
                                                                               "END" +
                                                                             "$userLogged$ LANGUAGE plpgsql";

    public static final String SYSTEMLOADSTATISTICS_CREATE_TRIGGER_USERLOGGED_TRIGGER = "CREATE TRIGGER userLoggedTrigger AFTER INSERT ON userLogged FOR EACH ROW EXECUTE PROCEDURE isUserLogged()";

//for table userNotLogged
    public static final String SYSTEMLOADSTATISTICS_CREATE_FUNCTION_ISPOSSIBLETHREAT = "CREATE OR REPLACE FUNCTION isPossibleThreat() RETURNS TRIGGER AS $posThreat$" +
                                                                                   "BEGIN " +
                                                                                     "INSERT INTO possibleThreat(timestamp, host, schema, value1, value2, application, level) SELECT NEW.timestamp, host, NEW.schema, 'ahoj', 42, NEW.application, NEW.level FROM userNotLogged GROUP BY user_, host HAVING COUNT(*) > 1000;" +
                                                                                     "RETURN NEW;" +
                                                                                   "END" +
                                                                                 "$posThreat$ LANGUAGE plpgsql";

    public static final String SYSTEMLOADSTATISTICS_CREATE_TRIGGER_USERNOTLOGGED_TRIGGER = "CREATE TRIGGER userNotLoggedTrigger AFTER INSERT ON userNotLogged FOR EACH ROW EXECUTE PROCEDURE isPossibleThreat()";

//for table possibleThreat
    public static final String SYSTEMLOADSTATISTICS_CREATE_FUNCTION_ISTHREAT = "CREATE OR REPLACE FUNCTION isThreat() RETURNS TRIGGER AS $res$" +
                                                                           "BEGIN " +
                                                                             "INSERT INTO threat(timestamp, host, count_) SELECT NEW.timestamp, host, COUNT(*) FROM possibleThreat GROUP BY host HAVING COUNT(*) > 10;" +
                                                                             "RETURN NEW;" +
                                                                           "END" +
                                                                         "$res$ LANGUAGE plpgsql";

    public static final String SYSTEMLOADSTATISTICS_CREATE_TRIGGER_POSSIBLETHREAT_TRIGGER = "CREATE TRIGGER possibleThreatTrigger AFTER INSERT ON possibleThreat FOR EACH ROW EXECUTE PROCEDURE isThreat()";

    public static final String SYSTEMLOADSTATISTICS_SELECT_RESULT = "SELECT COUNT(*) FROM threat";
}
