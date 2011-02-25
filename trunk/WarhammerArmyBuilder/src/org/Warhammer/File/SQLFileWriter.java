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

package org.Warhammer.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class SQLFileWriter {

    public static void writeRaceUnitSQLFile(String race, ArrayList<String> sql){
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/race_units_" + race + ".sql");
        print(file, sql);
    }
    
    public static void writeUtilityUnitSQLFile(String race,ArrayList<String> sql){
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/utilityunits_"+race+".sql");
        print(file, sql);
    }

    public static void write_Unit_UtilitySQLFile(String race,ArrayList<String> sql){
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/unit_utility_"+race+".sql");
        print(file, sql);

    }

    public static void writeRule(ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/specialRules.sql");
        print(file, sql);
        
    }

    public static void write_unit_ruleSQLFile(String race,ArrayList<String> sql){
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/unit_rules_"+race+".sql");
        print(file, sql);
    }

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

    public static void writeEquipmentSQLFile(ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/equipment.sql");
        print(file, sql);
    }
    public static void writeUnit_EquipmentSQLFile(String race,ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/unit_equipment_"+race+".sql");
        print(file, sql);
    }

    public static void write_eq_ruleSQLFile(ArrayList<String> sql) {
        java.io.File file = new java.io.File("src/org/Warhammer/Database/Resources/eq_rules.sql");
        print(file, sql);
    }

    public static void write_armySQLFile(String race, ArrayList<String> sql) {
        int num = -1;
        java.io.File file;
        do{
            num++;
            file = new java.io.File("src/org/Warhammer/Database/Resources/army_"+race+"_"+num+".sql");
        }
        while(file.exists());
        print(file, sql);
    }

    public static void write_caseSQLFile(ArrayList<String> sql) {
        int num = -1;
        java.io.File file;
        do{
            num++;
            file = new java.io.File("src/org/Warhammer/Database/Resources/case_"+num+".sql");
        }
        while(file.exists());
        print(file, sql);
    }
}
