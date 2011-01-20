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

import java.sql.SQLException;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.ExecutionException;

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

    public static void main(String[] args) throws SQLException{
        Database.DatabaseManager dbm = Database.DatabaseManager.getInstance();
        dbm.connect();
        //dbm.initDBFromScratch();
        dbm.printDataFromDB();
        //dbm.dropDB();
        dbm.commitChangesToDB();

        dbm.disconnect();
    }
}
