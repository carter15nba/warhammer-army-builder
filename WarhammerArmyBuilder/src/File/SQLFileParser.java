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

package File;

import Database.DatabaseManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class SQLFileParser {


    public void parseSQLFile(String sqlPath){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(sqlPath)));
            System.out.println("reading:");
            int i=0;
            System.out.println("sqlFile: "+sqlPath);
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
                        System.out.println("Found: "+sql);
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
    private void executeSQL(ArrayList<String> sqls){
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();
        for (String sql : sqls) {
            dbm.executeSQL(sql,DatabaseManager.UPDATE_QUERY);
        }
    }
    public static ArrayList<String> parseRaceUnitsSQL(java.io.File file){
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
                    System.out.println(string);
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
