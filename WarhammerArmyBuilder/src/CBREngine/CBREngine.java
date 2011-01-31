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

package CBREngine;

import Warhammer.UnitCase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.connector.DataBaseConnector;
import jcolibri.exception.ExecutionException;
import jcolibri.exception.InitializingException;
import jcolibri.util.FileIO;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.cbrcore.Connector;

/**
 *
 * @author Glenn Rune Strandbåten
 * @version 0.1
 */
public class CBREngine implements jcolibri.cbraplications.StandardCBRApplication{

    private static CBREngine instance = null;

    private CBREngine(){}
    public static CBREngine getInstance(){
        if(instance == null)
            instance = new CBREngine();
        return instance;
    }

    public void configure() throws ExecutionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public CBRCaseBase preCycle() throws ExecutionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cycle(CBRQuery cbrq) throws ExecutionException {
        retrieve();
        reuse();
        revise();
        retain();
    }

    public void postCycle() throws ExecutionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void retrieve(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void reuse(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void revise(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void retain(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws SQLException, InitializingException, IOException, ClassNotFoundException{
        Database.DatabaseManager dbm = Database.DatabaseManager.getInstance();
//        dbm.connectWithoutHibernate();
//        ResultSet res = dbm.executeSQL("select * from unit");
//
//        while(res.next()){
//            System.out.println("found: "+res.getString("NAME"));
//        }
//        dbm.disconnectNoHibernate();
        Connector con = dbm.connect();
////        CBRCase cbr = new CBRCase();
////        Warhammer.UnitCase uc = new UnitCase();
////        uc.setName("Bretonnian Lord");
////        uc.setArmyType("Lord");
////        uc.setAttack("4");
////        uc.setBallisticSkill("3");
////        uc.setUnitType("In");
////
////        cbr.setDescription(uc);
        
////        List<CBRCase> list = new ArrayList<CBRCase>();
////
////        list.add(cbr);
////        Collection<CBRCase> col = list;
////        con.storeCases(col);
////
          Collection<CBRCase> col = con.retrieveAllCases();
          for (CBRCase cBRCase : col) {
              System.out.println(cBRCase.toString());
        }
//
////          con.close();
////        dbm.disconnect();
//        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//        DataBaseConnector connector  = new DataBaseConnector();
//        connector.initFromXMLfile(FileIO.findFile("Database/databaseconfig.xml"));
//        CBRCaseBase cb = new LinealCaseBase();
//        cb.init(connector);
//        java.util.Collection<CBRCase> cases = cb.getCases();
//        for(CBRCase c: cases)
//            System.out.println(c);
       // Collection<CBRCase> col = connector.retrieveAllCases();
        //connector.
    }
}
