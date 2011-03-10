/*
 *  Copyright (C) 2011 Glenn Rune Strandbåten
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.Warhammer.Database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import jcolibri.cbrcore.CBRCase;
import jcolibri.connector.DataBaseConnector;
import jcolibri.exception.InitializingException;
import jcolibri.util.FileIO;
import jcolibri.cbrcore.Connector;

/**
 * Singleton class responsible for the connections and executions of
 * statements to the database.
 * @author Glenn Rune Strandbåten
 * @version 0.4
 */
public class DatabaseManager {

    private String HIBERNATE_CONFIG_FILE = "org/Warhammer/Database/databaseconfig.xml";
    private String HIBERNATE_INIT_FILE = "org/Warhammer/Database/hibernate.cfg.xml";
    private Connection connection = null;
    private Connector casebaseConnector = null;
    public static final int UPDATE_QUERY = 100;
    public static final int SELECT_QUERY = 200;
    public static final int TABLE_QUERY = 300;


    /**
     * Singleton class with private constructor. Use
     * DatabaseManager.getInstance() to aquire an instance of this class.
     */
    private DatabaseManager(){}

    /**
     * Private innere class to hold the singleton instance.
     */
    private static class DatabaseManagerHolder{
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }
    
    /**
     * Method used to aquire an instance of this class. This method also creates
     * the instance if it is uninstantiated.
     * @return The DatabaseManager instanse.
     */
    public static DatabaseManager getInstance(){
        return DatabaseManagerHolder.INSTANCE;
    }

    /**
     * Method to connect to the database using the configuration settings
     * found in the Database/databaseconfig.xml file.
     * @return null if the connection could not be established, the
     * jcolibri.cbrcore.connector object if connection were established.
     */
    public Connector connect(){
        try {
            casebaseConnector = new DataBaseConnector();
            URL fileURL = FileIO.findFile(HIBERNATE_CONFIG_FILE);
            casebaseConnector.initFromXMLfile(fileURL);

            return casebaseConnector;
        } catch (InitializingException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    /**
     * Method to aquire the jcolibri.cbrcore.connector object.
     * @return Return null if the connection not is established, the
     * jcolibri.cbrcore.connector object if connection were established.
     */
    public Connector getConnector(){
        return casebaseConnector;
    }

    /**
     * Method to shutdown the database connector established with the xml
     * file.
     */
    public void disconnect(){
        casebaseConnector.close();
        casebaseConnector = null;
    }

    /**
     * Method to commit the changes made to the database to store those
     * changes to file. The autoCommit feature is disabled so this method
     * <b>must</b> be executed to make the changes permanent. This method
     * utilizes the commit function in the database connection object.
     */
    public void storeCases(Collection<CBRCase> cases){
        casebaseConnector.storeCases(cases);
    }

    /**
     * This method connects to the database without using the hibernate
     * configuration file. This allows the program to post SQL queries to
     * the database through this connection.
     */
    public void connectWithoutHibernate(){
        try {
            if(connection!=null)
                if(!connection.isClosed())
                    return;
            org.Warhammer.File.RedusedHibernateParser parser = 
                    new org.Warhammer.File.RedusedHibernateParser();
            Properties hibernateCfg = parser.parseHibernate(HIBERNATE_INIT_FILE);
            Class.forName(hibernateCfg.getProperty("Driver")).newInstance();
            Properties props = new Properties();
            props.put("user",hibernateCfg.get("Username"));
            props.put("password",hibernateCfg.get("Password"));
            connection = DriverManager.getConnection(
                    hibernateCfg.getProperty("URL"),
	 	    props);
            connection.setAutoCommit(false);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(SQLException sqle){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    /**
     * This method disconnects the no hibernate connection establieshed with the
     * connectNoHibernate() method.
     */
    public int disconnectNoHibernate(boolean shutdownDerby){
        try{
            if(connection!=null)
                connection.close();
            if(shutdownDerby)
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            return 1;
        }
        catch(SQLException sqle){
            if((sqle.getErrorCode() == 50000)&&
                    ("XJ015".equals(sqle.getSQLState()))){
                System.out.println("Derby shut down normally");
            }
            else{
                System.err.println("Derby did not shut down normally");
            }
         }
        return -1;
     }

    /**
     * This method is used to post SQL queries to the database using the
     * underlying no hibernate connection.
     * @param sql The string sql statement.
     * @return Null if a invalid sql were passed, or a ResultSet containing
     * the results of the query.
     */
    public ResultSet executeSQL(String sql,int statementType){
        try {
            Statement statement = connection.createStatement();
            switch(statementType){
                case SELECT_QUERY:
                    return statement.executeQuery(sql);
                case TABLE_QUERY:
                    statement.execute(sql);
                    return null;
                case UPDATE_QUERY:
                    statement.executeUpdate(sql);
                    return null;
                default:
                    return null;
            }
            
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public PreparedStatement prepareStatement(String sql) throws SQLException, NullPointerException{
        if(connection==null)
            throw new NullPointerException();
        return connection.prepareStatement(sql);
    }
    public void commit() throws SQLException, NullPointerException{
        if(connection==null)
            throw new NullPointerException();
        connection.commit();
    }
}