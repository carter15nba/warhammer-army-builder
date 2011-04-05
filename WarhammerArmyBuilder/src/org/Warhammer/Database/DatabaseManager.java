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
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.InitializingException;
import jcolibri.util.FileIO;
import org.Warhammer.Database.Connector;

/**
 * Singleton class responsible for the connections and executions of
 * statements to the database.
 * @author Glenn Rune Strandbåten
 * @version 0.4
 */
public class DatabaseManager {

    private static final String HIBERNATE_CONFIG_FILE =
            "org/Warhammer/Database/databaseconfig.xml";
    private static final String HIBERNATE_INIT_FILE =
            "org/Warhammer/Database/hibernate.cfg.xml";
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
     * org.warhammer.database.connector object if connection were established.
     */
    public Connector connect(){
        try {
            if(casebaseConnector!=null){
                return casebaseConnector;
            }
            casebaseConnector = new Connector();
            URL fileURL = FileIO.findFile(HIBERNATE_CONFIG_FILE);
            casebaseConnector.initFromXMLfile(fileURL);
            return casebaseConnector;
            
        } catch (InitializingException ex) {}
        return null;
    }

    /**
     * Special method to connect to the database by overwriting a properity
     * in the hibernate.cfg.xml file. The DataBaseManager are not responsible
     * for the future use of the created connection as no reference to it is
     * kept. 
     * 
     * @return null if the connection could not be established, the
     * org.warhammer.database.connector object if connection were established.
     */
    public Connector overwrittenHibernateConnect(){
        try {
            Connector connector = new Connector();
            URL fileURL = FileIO.findFile(HIBERNATE_CONFIG_FILE);
            connector.initOverwriteFromXMLFile(fileURL);
            return connector;
        }
        catch (InitializingException ex) {}
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
     * @deprecated use the hibernate connection, create a session and take
     * advantage of the named queries instead
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
        catch (InstantiationException ex) {}
        catch (IllegalAccessException ex) {}
        catch (ClassNotFoundException ex) {}
        catch(SQLException sqle){}
    }

    /**
     * This method disconnects the no hibernate connection establieshed with the
     * connectNoHibernate() method.
     * @deprecated use the hibernate connection, create a session and take
     * advantage of the named queries instead
     */
    public void disconnectNoHibernate(boolean shutdownDerby){
        try{
            if(connection!=null)
                connection.close();
            if(shutdownDerby)
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
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
     }

    /**
     * This method is used to post SQL queries to the database using the
     * underlying no hibernate connection.
     * @deprecated use the hibernate connection, create a session and take
     * advantage of the named queries instead
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
        catch (SQLException ex) {}
        return null;
    }

    /**
     * Method to get a sql statement which can be used to execute a sql
     * insert/update/table query
     * @deprecated  use the hibernate connection, create a session and take
     * advantage of the named queries instead
     * @return <ul><li>null - if there is no connection or an exception occured</li>
     * <li>The requested statement</li></ul>
     */
    public Statement getStatement(){
        try {
            return connection.createStatement();
        }
        catch (SQLException ex) {}
        return null;
    }

    /**
     * Method used to commit(store) the databases changes permanently
     * @deprecated use the hibernate connection, create a session and take
     * advantage of the named queries instead
     * @throws SQLException
     * @throws NullPointerException if the connection is not established
     */
    public void commit() throws SQLException, NullPointerException{
        if(connection==null)
            throw new NullPointerException();
        connection.commit();
    }
}