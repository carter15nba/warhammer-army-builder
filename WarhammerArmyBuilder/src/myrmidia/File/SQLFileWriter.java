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

package myrmidia.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to write SQL statements to a file. Most methods overwrite any existing
 * file by default so care should be used to ensure that any desired files are
 * kept. E.g: By ensuring that the content of the files you want to preserve
 * are written anew together with the new content. It is explicitly stated in
 * methods that <b>does not</b> overwrite the file that they do not overwrite
 * existing files.
 *
 * @author Glenn Rune Strandbråten
 * @version 0.4
 */
public class SQLFileWriter {

    /**
     * Method to write a SQL file with the units of a Warhammer race.
     * @param race String representing the Warhammer race, used to name the file.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void writeRaceUnitSQLFile(String race, ArrayList<String> sql){
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/race_units_" + race + ".sql");
        print(file, sql);
    }
    /**
     * Method to write a SQL file with the utility units of a Warhammer race.
     * @param race String representing the Warhammer race, used to name the file.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void writeUtilityUnitSQLFile(String race,ArrayList<String> sql){
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/utilityunits_"+race+".sql");
        print(file, sql);
    }
    /**
     * Method to write a SQL file with the unit and utility relations of a
     * Warhammer race.
     * @param race String representing the Warhammer race, used to name the file.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void write_Unit_UtilitySQLFile(String race,ArrayList<String> sql){
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/unit_utility_"+race+".sql");
        print(file, sql);

    }
    /**
     * Method to write a SQL file with special rules.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void writeRule(ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/specialRules.sql");
        print(file, sql);
        
    }

    /**
     * Method to write a SQL file with the unit and rule relations of a Warhammer
     * race.
     * @param race String representing the Warhammer race, used to name the file.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void write_unit_ruleSQLFile(String race,ArrayList<String> sql){
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/unit_rules_"+race+".sql");
        print(file, sql);
    }

    /**
     * Private method resposnible for all the file writing that occurs in this
     * class.
     * @param file The file to be written to.
     * @param sql The content to be written to file.
     */
    private static void print(java.io.File file, ArrayList<String> sql){
        PrintStream ps = null;
        try {
            OutputStream os = new FileOutputStream(file);
            ps = new PrintStream(os, true, "UTF-8");
            for (String string : sql) {
                String print = string+";";
                ps.println(print);
            }
        }
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SQLFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(SQLFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ps.close();
        }
    }

    /**
     * Method to write a SQL file with all the equipment.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void writeEquipmentSQLFile(ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/equipment.sql");
        print(file, sql);
    }
    /**
     * Method to write a SQL file with the unit and equipment relations of a
     * Warhammer race.
     * @param race String representing the Warhammer race, used to name the file.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void writeUnit_EquipmentSQLFile(String race,ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/unit_equipment_"+race+".sql");
        print(file, sql);
    }

    /**
     * Method to write a SQL file with the equipment and rule relations of a
     * Warhammer race.
     * @param sql ArrayList with the SQL statements to be written.
     */
    public static void write_eq_ruleSQLFile(ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/myrmidia/Database/Resources/eq_rules.sql");
        print(file, sql);
    }
    /**
     * Method to write a SQL file with an army of units, equipment, crews and mounts.
     * This method <b>does not</b> overwrite any existing files but create a new
     * numbered file.
     * @param race String representing the Warhammer race, used to name the file.
     * @param sql The ArrayList with the SQL statements to be written.
     */
    public static void write_armySQLFile(String race, ArrayList<String> sql) {
        int num = -1;
        java.io.File file;
        do{
            num++;
            file = new java.io.File("src/myrmidia/Database/Resources/army_"+race+"_"+num+".sql");
        }
        while(file.exists());
        print(file, sql);
    }
    /**
     * Method to write a SQL file with an case
     * This method <b>does not</b> overwrite any existing files but create a new
     * numbered file.
     * @param sql ArrayList with the SQL statements to be written.
     */
    public static void write_caseSQLFile(ArrayList<String> sql) {
        int num = -1;
        java.io.File file;
        do{
            num++;
            file = new java.io.File("src/myrmidia/Database/Resources/case_"+num+".sql");
        }
        while(file.exists());
        print(file, sql);
    }
}