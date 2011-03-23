/*
 *  Copyright (C) 2011 Glenn Rune Strandbråten
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

package org.Warhammer.Util;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import org.Warhammer.Database.DatabaseManager;
import org.Warhammer.File.SQLFileParser;

/**
 * Warning DO NOT USE! This class will delete the database and recreate it with
 * the default dataset.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class RecreateDatabase {

    private DatabaseManager dbm;
    private File[] files;

    /**
     * Method which loads all the sql files within the application.
     */
    private void loadFiles(){
        File f = new File("src/org/Warhammer/Database/Resources");
        System.out.println(f.isDirectory());
        System.out.println(f.getName());
        files = f.listFiles();
    }

    /**
     * Method which executes the sql files specifief by the prefix
     * @param prefix String file name prefix
     */
    private void executeFiles(String prefix){
        ArrayList<String> statements = new ArrayList<String>();
        for (File file : files) {
            if(file.getName().startsWith(prefix)){
                System.out.println(file.getName());
                statements = SQLFileParser.parseSQL(file);
                for(String string : statements){
                    System.out.println(string);
                    dbm.executeSQL(string, DatabaseManager.UPDATE_QUERY);
                }
            }
        }
    }

    /**
     * Method which recreates the entire database, Delete all tables,
     * recreate the tables and fills the tables with data.
     */
    public void recreateDatabase(){
        loadFiles();
        dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();

        deleteDatabase(); //Delete db.
        commit();
        dbm.connect();
        insertRecords(); //Insert the data into the db
        commit();
        dbm.disconnect();
        dbm.disconnectNoHibernate(false);
    }

    /**
     * Method which calls the drop tables sql file
     */
    private void deleteDatabase(){
        executeFiles("SQL_DROP_TABLES");
    }

    /**
     * Method which inserts the data in the required order.
     */
    private void insertRecords(){
        insertUnit();
        insertUtility();
        insertEquipment();
        insertRules();
        insertUnit_Utility();
        insertUnit_Equipment();
        insertUnit_Rule();
        insertEquipment_Rule();
        insertArmies();
        insertCases();
    }

    /**
     * Method which calls the race_units sql files
     */
    private void insertUnit(){
        executeFiles("race_units_");
    }

    /**
     * Method which calls the utilityunits sql files
     */
    private void insertUtility() {
        executeFiles("utilityunits_");
    }

    /**
     * Method which calls the equipment sql file
     */
    private void insertEquipment() {
        executeFiles("equipment");
    }

    /**
     * Method which calls the special rules sql file
     */
    private void insertRules() {
        executeFiles("specialRules");
    }

    /**
     * Method which calls the unit_utility sql files
     */
    private void insertUnit_Utility() {
        executeFiles("unit_utility_");
    }

    /**
     * Method which calls the unit_equipment sql files
     */
    private void insertUnit_Equipment() {
        executeFiles("unit_equipment_");
    }

    /**
     * Method which calls the unit_rules sql files
     */
    private void insertUnit_Rule() {
        executeFiles("unit_rules_");
    }

    /**
     * Method which calls the eq_rule sql file
     */
    private void insertEquipment_Rule() {
        executeFiles("eq_rules");
    }

    /**
     * Method which calls the army_ sql files
     */
    private void insertArmies() {
        executeFiles("army_");
    }

    /**
     * Method which calls the case_ sql files
     */
    private void insertCases() {
        executeFiles("case_");
    }

    /**
     * Method which commits the changes to the database.
     */
    private void commit(){
        try {
            dbm.commit();
        }
        catch (SQLException ex) {}
        catch (NullPointerException ex) {}
    }
}
