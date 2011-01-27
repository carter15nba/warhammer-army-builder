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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import jcolibri.cbrcore.CBRCase;
import jcolibri.connector.DataBaseConnector;
import jcolibri.exception.InitializingException;
import jcolibri.util.FileIO;
import jcolibri.cbrcore.Connector;

/**
 *
 * @author Glenn Rune Strandbåten
 * @version 0.2
 */
public class DatabaseManager {

    private String HIBERNATE_INIT_FILE = "Database/databaseconfig.xml";
    private Connection connection = null;
    private Statement statement = null;
    private Connector casebaseConnector = null;

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
     *
     * @return null if
     */
    public Connector connect(){
        try {

            casebaseConnector = new DataBaseConnector();
            URL fileURL = FileIO.findFile(HIBERNATE_INIT_FILE);
            casebaseConnector.initFromXMLfile(fileURL);
            return casebaseConnector;
        } catch (InitializingException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Connector getConnector(){
        return casebaseConnector;
    }

    /**
     * Method to shutdown the database connection and the derby engine.
     */
    public void disconnect(){
        casebaseConnector.close();
        casebaseConnector = null;
    }

    /**
     * Method to create the tables in the database.
     * @throws SQLException
     */
    private void createDB() throws SQLException{
        if(connection==null||statement==null)
            return;
        String table;
//        String table = "create table case(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, "
//                + "race varchar(50) NOT NULL, "
//                + ""
//                + "UNIQUE(ID))";
//        statement.execute(table);
        table = "create table available_units(caseID int NOT NULL, unitID int NOT NULL)";
        statement.execute(table);
        table = "create table units(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                + "NAME VARCHAR(60),"
                + "TYPE VARCHAR(2),"
                + "MOVEMENT VARCHAR(4),"
                + "WEAPONSKILL VARCHAR(4),"
                + "BALLISTICSKILL VARCHAR(4),"
                + "STRENGTH VARCHAR(4),"
                + "TOUGHNESS VARCHAR(4),"
                + "WOUNDS VARCHAR(4),"
                + "INITIATIVE VARCHAR(4),"
                + "ATTACK VARCHAR(4),"
                + "LEADERSHIP VARCHAR(4),"
                + "UNIQUE(ID))";
        statement.execute(table);
        table = "create table unit_weapons(unitID int NOT NULL, weaponID int NOT NULL)";
        statement.execute(table);
        table = "create table weapon(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                + "NAME VARCHAR(60),"
                + "UNIQUE(ID))";
    }

    /**
     * Method used to intialize the database by creating its tables and filling
     * them with data.
     */
    public void initDBFromScratch(){
        throw new UnsupportedOperationException("Not supported yet.");
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
}