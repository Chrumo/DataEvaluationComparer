/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer.postgresql;

import com.esper.dataevaluationcomparer.Timer;
import com.esper.dataevaluationcomparer.esper.events.TestEvent;
import com.esper.dataevaluationcomparer.esper.events.UserLoggedInfo;
import static com.esper.dataevaluationcomparer.postgresql.QueriesAntiDictAttack.*;
import static com.esper.dataevaluationcomparer.postgresql.QueriesGroupByTest.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomas
 */
public class PostgreSQL {

    public void runAntiDictAttack(List<UserLoggedInfo> events, Timer stopWatch) {
        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String url = "jdbc:postgresql://localhost:5432/esperdb";
        Properties props = new Properties();
        props.setProperty("user","tomas");
        props.setProperty("password","password");
        try {
            con = DriverManager.getConnection(url, props);
            pst = con.prepareStatement(ANTIDICTATTACK_DROP_TABLES);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_TABLE_USER_LOGGED);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_TABLE_USER_NOT_LOGGED);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_TABLE_POSSIBLE_THREAT);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_TABLE_THREAT);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_DROP_ISUSERLOGGED_FUNCTION);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_FUNCTION_ISUSERLOGGED);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_DROP_USER_LOGGED_TRIGGER);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_TRIGGER_USERLOGGED_TRIGGER);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_DROP_ISPOSSIBLETHREAT_FUNCTION);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_FUNCTION_ISPOSSIBLETHREAT);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_DROP_USER_NOT_LOGGED_TRIGGER);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_TRIGGER_USERNOTLOGGED_TRIGGER);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_DROP_ISTHREAT_FUNCTION);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_FUNCTION_ISTHREAT);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_DROP_USER_POSSIBLE_THREAT_TRIGGER);
            pst.executeUpdate();
            pst = con.prepareStatement(ANTIDICTATTACK_CREATE_TRIGGER_POSSIBLETHREAT_TRIGGER);
            pst.executeUpdate();
            stopWatch.start();
            for(UserLoggedInfo e : events){
                pst = con.prepareStatement("INSERT INTO userLogged(timestamp, type, host, schema, " +
                                           "success, sourceHost, sourcePort, user_, application, level) " +
                                           "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setLong(1, e.getTimestamp());
                pst.setString(2, e.getType());
                pst.setString(3, e.getHost());
                pst.setString(4, e.getSchema());
                pst.setBoolean(5, e.isSuccess());
                pst.setString(6, e.getSourceHost());
                pst.setInt(7, e.getSourcePort());
                pst.setString(8, e.getUser());
                pst.setString(9, e.getApplication());
                pst.setInt(10, e.getLevel());
                pst.executeUpdate();
            }
            st = con.createStatement();
            rs = st.executeQuery(ANTIDICTATTACK_SELECT_RESULT);

            while (rs.next()) {
                System.out.println(rs.getInt("COUNT(*)"));
            }
            long timeElapsed = stopWatch.stop();
            System.out.println("PostgreSQL-AntiDictAttack: " + timeElapsed + " ms");
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(PostgreSQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(PostgreSQL.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public void runSystemLoadStatistics(List<UserLoggedInfo> events, Timer stopWatch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void runGroupByTest(List<TestEvent> events, Timer stopWatch){
        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String url = "jdbc:postgresql://localhost:5432/esperdb";
        Properties props = new Properties();
        props.setProperty("user","tomas");
        props.setProperty("password","password");
        try {
            con = DriverManager.getConnection(url, props);
            pst = con.prepareStatement(GROUPBY_TEST_DROP_TABLES);
            pst.executeUpdate();
            pst = con.prepareStatement(GROUPBY_TEST_CREATE_TABLE_GROUPBY_TEST);
            pst.executeUpdate();
            pst = con.prepareStatement(GROUPBY_TEST_CREATE_TABLE_GROUPBY_RESULT);
            pst.executeUpdate();
            pst = con.prepareStatement(GROUPBY_TEST_DROP_ISGROUPBY_FUNCTION);
            pst.executeUpdate();
            pst = con.prepareStatement(GROUPBY_TEST_CREATE_FUNCTION_ISGROUPBYTEST);
            pst.executeUpdate();
            pst = con.prepareStatement(GROUPBY_TEST_DROP_TEST_TRIGGER);
            pst.executeUpdate();
            pst = con.prepareStatement(GROUPBY_TEST_CREATE_TRIGGER_TEST_TRIGGER);
            pst.executeUpdate();
            stopWatch.start();
            for(TestEvent te : events){
                pst = con.prepareStatement("INSERT INTO groupByTest(test, timestamp) VALUES(?, ?)");
                pst.setString(1, te.getTest());
                pst.setLong(2, te.getTimestamp());
                pst.executeUpdate();
            }
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM groupByResult");

            while (rs.next()) {
                System.out.println(rs.getInt("timestamp") + " " + rs.getString("test"));
            }
            long timeElapsed = stopWatch.stop();
            System.out.println("PostgreSQL-GroupBy Test: " + timeElapsed + " ms");
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(PostgreSQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(PostgreSQL.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public void runJoinTest(List<TestEvent> testEvents, Timer stopWatch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void runSequenceSQL(List<TestEvent> testEvents, Timer stopWatch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

