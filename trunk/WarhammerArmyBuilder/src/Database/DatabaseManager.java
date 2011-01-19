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
 */
public class DatabaseManager {

    private static DatabaseManager instance = null;
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private String dbName = "warhammerDB";
    private Connection connection = null;
    private Statement s = null;
    private DatabaseManager(){}

    public static DatabaseManager getInstance(){
        if(instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public void connect(){
     
            loadDriver();

            ArrayList statements = new ArrayList();
            PreparedStatement psInsert = null;
            PreparedStatement psUpdate = null;
            ResultSet rs = null;
            try{
                Properties props = new Properties();
                props.put("user","gamer");
                props.put("password","8844Qgpty");

                connection = DriverManager.getConnection(
                        protocol + dbName /*+ ";create=true"*/,
                        props);

                connection = DriverManager.getConnection(protocol + "warhammerDB;create=true", props);

                System.out.println("Connected to and created database: "+dbName);

                s = connection.createStatement();
                statements.add(s);

                connection.setAutoCommit(false);

                //createDB();
                System.out.println("Created DB, preparing to add data to DB.");
                psInsert = connection.prepareStatement("insert into unitCase values(?, ?)");
                statements.add(psInsert);
                psInsert.setInt(1, 0);
                psInsert.setString(2, "Paladin");
                psInsert.executeUpdate();
                psInsert.setInt(1, 1);
                psInsert.setString(2, "Berserker");
                psInsert.executeUpdate();
                psInsert.setInt(1, 2);
                psInsert.setString(2, "Grail Knight");
                psInsert.executeUpdate();

                rs = s.executeQuery("SELECT ID, name FROM unitCase");
                System.out.println("Fetched from database");
                while(rs.next()){
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    System.out.println("ID: "+id+", name: "+name);
                }
                connection.commit();
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }





    }
    public void disconnect(){
        try{
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }
        catch(SQLException sqle){
            if((sqle.getErrorCode() == 50000)&&
                    ("XJ015".equals(sqle.getSQLState()))){
                System.out.println("Derby shut down normally");
            }
            else{
                System.err.println("Derby did not shut down normally");
                printSQLException(sqle);
            }
        }
    }

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
            e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

    private void loadDriver(){
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException cnfe) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, cnfe);
        }
        catch(InstantiationException ie){

        }
        catch(IllegalAccessException iae){

        }
    }
    private void createDB() throws SQLException{
        if(s!=null){
            String table = "create table unitCase(ID int NOT NULL , name varchar(50) NOT NULL, UNIQUE(ID))";
            s.execute(table);
        }
    }


}
