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
import java.sql.SQLException;
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
import Warhammer.RuleSet;
import Warhammer.Unit;
import java.sql.ResultSet;
import java.util.Collection;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
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
        retrieve(cbrq);
//        reuse();
//        revise();
//        retain();
    }

    public void postCycle() throws ExecutionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void retrieve(CBRQuery cbrq){
        NNConfig conf = simSim.getSimilarityConfig();
        conf.setDescriptionSimFunction(new Average());
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), cbrq, conf);
        System.out.println("Case similarity:");
        for (RetrievalResult retrievalResult : eval) {
            Case c = (Case) retrievalResult.get_case().getDescription();
            System.out.println("Case: "+c.getCaseID()+", "+c.getPlayerRace()+"::"+retrievalResult.getEval());
        }
        Collection<CBRCase> selectedcases = SelectCases.selectTopK(eval, 1);

        Warhammer.RuleSet set = new RuleSet();
        RuleSet.messages l = RuleSet.messages.FAIL;
        for (CBRCase cBRCase : selectedcases) {
//            System.out.println(cBRCase.toString());
            Case ca = (Case) cBRCase.getDescription();
            System.out.println(ca.toString());
            System.out.println("\nTOTAL COST: "+ca.calculateCaseCost());
            l = set.isFollowingArmyDispositionRules(ca, 2500);

        }
        if(l == RuleSet.messages.FAIL){
            RuleSet.messages[] r = set.getErrorCauses();
            for (RuleSet.messages object : r) {
                System.out.println(object);
            }
            
        }
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

    public static void main(String[] args) throws NoApplicableSimilarityFunctionException, SQLException {
        try {
            CBREngine cbrEngine = CBREngine.getInstance();
            cbrEngine.configure();
            cbrEngine.preCycle();

            CBRQuery q = new CBRQuery();
            Case comp = new Case();
            comp.setArmyPoints(2500);
            comp.setPlayerRace(Case.Races.Empire);
//            comp.setOpponentRace(Case.Races.Wood_Elves);
            q.setDescription(comp);
            System.out.println("***************\n  QUERY: "+comp.toString()+"\n***************");
            cbrEngine.cycle(q);
//                DatabaseManager dbm = DatabaseManager.getInstance();
//                dbm.connectWithoutHibernate();
//                ResultSet res = dbm.executeSQL("SELECT * FROM UNIT", DatabaseManager.SELECT_QUERY);
//                while(res.next()){
//                    System.out.println(res.getString("name"));
//                }



//            System.out.println("\n\n\n***************\n  PREPARING TEST UNIT SIMILARITY...\n***************");
//            Unit u1 = new Unit();
//            u1.setName("Trojan horse");
//            u1.setCost(120);
//            u1.setNumber(2);
//            u1.setArmyType(Unit.armyType.Rare);
//            u1.setUnitType(Unit.unitType.Un);
//
//            Unit u2 = new Unit();
//            u2.setName("Trojan horse");
//            u2.setCost(120);
//            u2.setNumber(1);
//            u2.setArmyType(Unit.armyType.Rare);
//            u2.setUnitType(Unit.unitType.Un);
//
//            Unit u3 = new Unit();
//            u3.setName("Swordsman");
//            u3.setCost(120);
//            u3.setNumber(10);
//            u3.setArmyType(Unit.armyType.Core);
//            u3.setUnitType(Unit.unitType.In);
//
//            UnitSimilarity us = new UnitSimilarity();
//            double c1 = us.compute(u1, u2);
//            double c2 = us.compute(u1, u3);
//            double c3 = us.compute(u2, u3);
//            System.out.println("sim  c1 (2xTrojan horse with 1xTrojan horse): "+c1);
//            System.out.println("sim  c2 (2xTrojan horse with 10xSwordsmen)  : "+c2);
//            System.out.println("sim  c3 (1xTrojan horse with 10xSwordsmen)  : "+c3);
//
//            double d = us.simUnitType(Unit.unitType.In, Unit.unitType.MI);
//            System.out.println(d);





            System.exit(0);
        }
        catch (ExecutionException ex) {
            Logger.getLogger(CBREngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(-1);
    }
}
