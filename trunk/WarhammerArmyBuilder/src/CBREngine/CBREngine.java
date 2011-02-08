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

import Database.DatabaseManager;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.ExecutionException;
import jcolibri.cbrcore.Connector;
import CBREngine.Resources.*;
import Warhammer.Case;
import Warhammer.CoreCase;
import Warhammer.Race;
import Warhammer.Unit;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Set;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.selection.SelectCases;
/**
 *
 * @author Glenn Rune Strandbåten
 * @version 0.1
 */
public class CBREngine implements jcolibri.cbraplications.StandardCBRApplication{

    private Connector connector;
    private CBRCaseBase caseBase;
    private SimpleSimilarityMeasure simSim;
    private boolean debug = false;

    private CBREngine(){}
    public static CBREngine getInstance(){
        return CBREngineHolder.INSTANCE;
    }
    private static class CBREngineHolder{
        private static final CBREngine INSTANCE = new CBREngine();
    }

    public void configure() throws ExecutionException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        connector = dbm.connect();
        caseBase = new LinealCaseBase();
        simSim = new SimpleSimilarityMeasure();
    }

    public CBRCaseBase preCycle() throws ExecutionException {
        caseBase.init(connector);
        java.util.Collection<CBRCase> cases = caseBase.getCases();
        if(debug)
            for (CBRCase c : cases) {
                System.out.println(c);
            }
        return caseBase;
    }

    public void cycle(CBRQuery cbrq) throws ExecutionException {

        NNConfig conf = simSim.getSimilarityConfig();
        conf.setDescriptionSimFunction(new Average());
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), cbrq, conf);
        for (RetrievalResult retrievalResult : eval) {
            System.out.println(retrievalResult.getEval());
        }
        Collection<CBRCase> selectedcases = SelectCases.selectTopK(eval, 1);
        for (CBRCase cBRCase : selectedcases) {
            System.out.println(cBRCase.toString());

        }
//        retrieve();
//        reuse();
//        revise();
//        retain();
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

    public static void main(String[] args) {
        try {
            CBREngine cbrEngine = CBREngine.getInstance();
            cbrEngine.configure();
            cbrEngine.preCycle();

            CBRQuery q = new CBRQuery();
            Case comp = new Case();
            comp.setArmyPoints(2000);
            comp.setPlayerRace(Case.Races.Empire);
            comp.setOpponentRace(Case.Races.Dark_Elves);
   
            q.setDescription(comp);

            cbrEngine.cycle(q);




            System.exit(0);
        }
        catch (ExecutionException ex) {
            Logger.getLogger(CBREngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(-1);
    }
}
