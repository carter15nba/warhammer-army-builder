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

import org.Warhammer.Database.DatabaseManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to parse SQL statements from a file.
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class SQLFileParser {

    /**
     * Method to parse the SQL file specified by the file path This method will
     * also execute the parsed SQLs
     * @param sqlPath The file path to the sql file
     */
    public void parseSQLFile(String sqlPath){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(sqlPath)));
            int i=0;
            ArrayList<String> statements = new ArrayList<String>();
            String sql="";
            boolean comment = false;
            while((i=reader.read())!=-1){
                if(i=='-'){
                    comment=true;
                }
                if((i=='\n')||(i=='\r')){
                    comment=false;
                    continue;
                }
                if(!comment){
                    if(';'==i){
                        sql+=" ";
                        statements.add(sql);
                        sql="";
                    }
                    else{
                        sql+=(char)i;
                    }
                }
            }
            executeSQL(statements);
        }
        catch (IOException ex) {
            Logger.getLogger(SQLFileParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Method used to qxecute the parsed SQL statements.
     * @param sqls ArrayList with string representing the parsed SQL statements.
     */
    private void executeSQL(ArrayList<String> sqls){
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();
        for (String sql : sqls) {
            dbm.executeSQL(sql,DatabaseManager.UPDATE_QUERY);
        }
    }
    /**
     * This method is used to parse and return the SQL statements found in
     * the specified file. Each statement (separated by a colon [;]) is parsed
     * in its entirety and stored in a new position in the ArrayList. Separate
     * parser must be implemented if information is to be extracted from the
     * statements.
     * @param file File The file with the sql statements to be parsed.
     * @return The arraylist with the SQL statements represented as strings or
     * null if an exception occured.
     */
    public static ArrayList<String> parseSQL(java.io.File file){
         try {
            FileInputStream reader = new FileInputStream(file);
            int i=0;
            ArrayList<String> statements = new ArrayList<String>();
            String sql="";
            while((i=reader.read())!=-1){
                if((i=='\n')||(i=='\r')){
                    continue;
                }
                sql+=(char)i;
            }
            String[] s = sql.split(";");
             for (String string : s) {
                 if(!string.contentEquals("\n")){
                    statements.add(string);
                 }
             }
            return statements;
        }
        catch (IOException ex) {
            Logger.getLogger(SQLFileParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
