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

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Glenn Rune Strandbåten
 * @version 0.2
 */
public class DatabaseManager {

    private static DatabaseManager instance = null;
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private String dbName = "warhammerDB";
    private Connection connection = null;
    private Statement statement = null;
    ArrayList statements = new ArrayList();
    PreparedStatement psInsert = null;
    PreparedStatement psUpdate = null;
    ResultSet rs = null;

    /**
     * Singleton class with private constructor. Use
     * DatabaseManager.getInstance() to aquire an instance of this class.
     */
    private DatabaseManager(){}

    /**
     * Method used to aquire an instance of this class. This method also creates
     * the instance if it is uninstantiated.
     * @return The DatabaseManager instanse.
     */
    public static DatabaseManager getInstance(){
        if(instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    /**
     * Method to connect to the database.
     */
    public void connect(){
        try{
            loadDriver();
            Properties props = new Properties();
            props.put("user","gamer");
            props.put("password","8844Qgpty");

            connection = DriverManager.getConnection(
                    protocol + dbName+";create=true",
                    props);

            System.out.println("Connected to database: "+dbName);

            statement = connection.createStatement();
            statements.add(statement);
            connection.setAutoCommit(false);
        }
        catch(SQLException sqle){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, sqle);
            printSQLException(sqle);
        }
    }

    /**
     * Method to shutdown the database connection and the derby engine.
     */
    public void disconnect(){
        try{
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }
        catch(SQLException sqle){
            if((sqle.getErrorCode() == 50000)&&
                    ("XJ015".equals(sqle.getSQLState()))){
                //Normal shutdown.
            }
            else{
                printSQLException(sqle);
            }
        }
    }

    /**
     * Method to print the SQLExcpetion.
     * @param e The SQLException to print.
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

    /**
     * This method loads the database driver and <b>must</b> be called before
     * any connection to the database is establised. It is called qutomatically
     * when the connect method is executed.
     */
    private void loadDriver(){
        try {
            Class.forName(driver).newInstance();
        }
        catch (ClassNotFoundException cnfe) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, cnfe);
        }
        catch(InstantiationException ie){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ie);
        }
        catch(IllegalAccessException iae){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, iae);
        }
    }


    /**
     * Method to create the tables in the database.
     * @throws SQLException
     */
    private void createDB() throws SQLException{
        if(connection==null||statement==null)
            return;
        String table = "create table unitCase(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, name varchar(50) NOT NULL, UNIQUE(ID))";
        statement.execute(table);
    }

    /**
     * Method to dropThe tables in this database and auto commit the changes.
     * @throws SQLException
     */
    public void dropDB() throws SQLException{
        if(connection==null||statement==null)
            return;
        String drop = "drop table unitCase";
        statement.execute(drop);
        connection.commit();
        
    }

    /**
     * Method to fill the database with initial data.
     * @throws SQLException
     */
    private void fillDB() throws SQLException{
        if(connection==null)
            return;
        psInsert = connection.prepareStatement("insert into unitCase(name) values(?)");
        statements.add(psInsert);
        psInsert.setString(1, "Paladin");
        psInsert.executeUpdate();
        psInsert.setString(1, "Berserker");
        psInsert.executeUpdate();
        psInsert.setString(1, "Grail Knight");
        psInsert.executeUpdate();
    }

    /**
     * Method to print the data from the database.
     * @throws SQLException
     */
    public void printDataFromDB() throws SQLException{
        if(connection==null||statement==null)
            return;
        rs = statement.executeQuery("SELECT ID, name FROM unitCase");
        System.out.println("Fetched from database:");
        while(rs.next()){
            int id = rs.getInt(1);
            String name = rs.getString(2);
            System.out.println("   ID: "+id+", name: "+name);
        }
    }

    /**
     * Method used to intialize the database by creating its tables and filling
     * them with data.
     */
    public void initDBFromScratch(){
        if(connection==null||statement==null)
            return;
        try{
            createDB();
            fillDB();
        }
        catch(SQLException sqle){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, sqle);
            printSQLException(sqle);
        }
    }

    /**
     * Method to commit the changes made to the database to store those
     * changes to file. The autoCommit feature is disabled so this method
     * <b>must</b> be executed to make the changes permanent. This method
     * utilizes the commit function in the database connection object.
     */
    public void commitChangesToDB(){
        try {
            if(connection==null)
                return;
            connection.commit();
        }
        catch (SQLException sqle) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, sqle);
            printSQLException(sqle);
        }
    }

    /**
     * This method executes a SQL query in the database and returns the results
     * of the query. Queries containing <i>drop</i> & <i>create</i> keywords are
     * not executed.
     * @param query The String representation of the SQL query to be executed.
     * @return null if the query is ommitted and if an exception occurs or the
     * resulting ResultSet containing the results from the query execution.
     *
     */
    public ResultSet performSQLQuery(String query){
        if(connection==null||statement==null)
            return null;
        if(query.contains("drop"))
            return null;
        if(query.contains("create"))
            return null;
        try {
            return statement.executeQuery(query);
        }
        catch (SQLException sqle) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, sqle);
            printSQLException(sqle);
            return null;
        }
    }
}