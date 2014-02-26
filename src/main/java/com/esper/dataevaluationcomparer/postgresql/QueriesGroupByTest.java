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
public class QueriesGroupByTest {

    public static final String GROUPBY_TEST_DROP_TABLES = "DROP TABLE IF EXISTS groupByTest, groupByResult";

    public static final String GROUPBY_TEST_DROP_TEST_TRIGGER = "DROP TRIGGER IF EXISTS testTrigger ON groupByTest";

    public static final String GROUPBY_TEST_DROP_ISGROUPBY_FUNCTION = "DROP FUNCTION IF EXISTS isGroupByTest()";

    public static final String GROUPBY_TEST_CREATE_TABLE_GROUPBY_TEST = "CREATE TABLE IF NOT EXISTS groupByTest (" +
                                                                          "id serial PRIMARY KEY," +
                                                                          "test varchar(5)," +
                                                                          "timestamp int" +
                                                                        ")";

    public static final String GROUPBY_TEST_CREATE_TABLE_GROUPBY_RESULT = "CREATE TABLE IF NOT EXISTS groupByResult (" +
                                                                            "id serial PRIMARY KEY," +
                                                                            "timestamp int," +
                                                                            "test varchar(5)," +
                                                                            "count int" +
                                                                          ")";
    //for table groupByTest
    public static final String GROUPBY_TEST_CREATE_FUNCTION_ISGROUPBYTEST = "CREATE OR REPLACE FUNCTION isGroupByTest() RETURNS TRIGGER AS $groupByTestik$" +
                                                                              "BEGIN " +
                                                                                "INSERT INTO groupByResult(timestamp, test, count) SELECT NEW.timestamp, test, COUNT(*) FROM groupByTest GROUP BY test HAVING test = NEW.test;" +
                                                                                "RETURN NEW;" +
                                                                              "END" +
                                                                            "$groupByTestik$ LANGUAGE plpgsql";

    public static final String GROUPBY_TEST_CREATE_TRIGGER_TEST_TRIGGER = "CREATE TRIGGER testTrigger AFTER INSERT ON groupByTest FOR EACH ROW EXECUTE PROCEDURE isGroupByTest()";

    public static final String GROUPBY_TEST_SELECT_RESULT = "SELECT * FROM groupByResult";
}
