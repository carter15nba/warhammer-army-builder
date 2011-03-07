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

package org.Warhammer.CBREngine;

import org.Warhammer.Database.DatabaseManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.ExecutionException;
import jcolibri.cbrcore.Connector;
import org.Warhammer.CBREngine.Resources.*;
import org.Warhammer.Warhammer.Case;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.selection.SelectCases;
import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Equipment;
import org.Warhammer.Warhammer.Unit;
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
            Case c = (Case) retrievalResult.get_case().getSolution();
            if(c.getID()==5)
                org.Warhammer.Util.PrintFactory.printCase(c,retrievalResult.getEval(),true);
            else
                org.Warhammer.Util.PrintFactory.printCase(c,retrievalResult.getEval(),false);
        }
        Collection<CBRCase> selectedcases = SelectCases.selectTopK(eval, 1);

//        org.Warhammer.Warhammer.RuleSet set = new RuleSet();
//        RuleSet.messages l = RuleSet.messages.FAIL;
//        for (CBRCase cBRCase : selectedcases) {
//
//            System.out.println(cBRCase.toString());
//            Case ca = (Case) cBRCase.getDescription();
//            System.out.println(ca.toString());
////            System.out.println("\nTOTAL COST: "+ca.calculateCaseCost());
////            l = set.isFollowingArmyDispositionRules(ca, 2500);
//
//        }
//        if(l == RuleSet.messages.FAIL){
//            RuleSet.messages[] r = set.getErrorCauses();
//            for (RuleSet.messages object : r) {
//                System.out.println(object);
//            }
//
//        }
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
            if(args.length==0){
                CBREngine cbrEngine = CBREngine.getInstance();
                cbrEngine.configure();
                cbrEngine.preCycle();

                CBRQuery cbrQuery = new CBRQuery();
                Case queryCase = new Case();
                queryCase.setOutcome(Case.Outcomes.Victory);
                queryCase.setOpponent(Case.Races.Wood_Elves);

                Army queryArmy = new Army();
                queryArmy.setArmyPoints(2500);
                queryArmy.setPlayerRace(Case.Races.Empire);

                Set<ArmyUnit> armyUnitSet = new HashSet<ArmyUnit>();
                ArmyUnit armyUnit = new ArmyUnit();

                Unit unit = new Unit();
                unit.setName("Empire:Halberdiers");
                armyUnit.setUnitName(unit);
                armyUnit.setNumberOfUnits(10);
                armyUnitSet.add(armyUnit);

                armyUnit = new ArmyUnit();
                unit = new Unit();
                unit.setName("Empire:Greatswords");
                armyUnit.setUnitName(unit);
                armyUnit.setNumberOfUnits(19);
                armyUnitSet.add(armyUnit);
                queryArmy.setArmyUnits(armyUnitSet);
                queryCase.setArmy(queryArmy);

                Set<Equipment> sEq = new HashSet<Equipment>();
                Equipment eq = new Equipment();
                eq.setName("Musician");
                sEq.add(eq);
                unit.setEquipment(sEq);

                System.out.println("Query Case");
                org.Warhammer.Util.PrintFactory.printCase(queryCase,false);

                cbrQuery.setDescription(queryCase);
                cbrEngine.cycle(cbrQuery);
            }
            else if(args[0].contentEquals("sqlPrint")){
                DatabaseManager dbm = DatabaseManager.getInstance();
                dbm.connectWithoutHibernate();
                ResultSet res = dbm.executeSQL("SELECT * FROM UNIT order by NAME ASC", DatabaseManager.SELECT_QUERY);
                
                while(res.next()){
                    System.out.println(res.getString("name"));
                }
                dbm.disconnectNoHibernate(true);
            }
            else if(args[0].contentEquals("simTest")){
                Set<ArmyUnit> cases = new HashSet<ArmyUnit>();
                Set<ArmyUnit> query = new HashSet<ArmyUnit>();
                //CASE
                ArmyUnit armyUnit = new ArmyUnit();
                Unit unit = new Unit();
                unit.setName("Hangunners");
                armyUnit.setNumberOfUnits(10);
                armyUnit.setUnitName(unit);
                cases.add(armyUnit);

                Set<Equipment> eq = new HashSet<Equipment>();
                Equipment e = new Equipment();
                e.setName("Musician");
                eq.add(e);
                e = new Equipment();
                e.setName("Standard bearer");
                eq.add(e);
                unit.setEquipment(eq);

                armyUnit = new ArmyUnit();
                unit = new Unit();
                unit.setName("Crossbowmen");
                armyUnit.setNumberOfUnits(50);
                armyUnit.setUnitName(unit);
                cases.add(armyUnit);

                armyUnit = new ArmyUnit();
                cases.add(armyUnit);
                unit = new Unit();
                unit.setName("Greatswords");
                armyUnit.setNumberOfUnits(15);
                armyUnit.setUnitName(unit);
                cases.add(armyUnit);

                Army a1 = new Army();
                a1.setArmyUnits(cases);
                a1.setArmyPoints(2000);




                //QUERY
                armyUnit = new ArmyUnit();
                unit = new Unit();
                unit.setName("Hangunners");

                eq = new HashSet<Equipment>();
                e = new Equipment();
                e.setName("Repeater rifle");
                eq.add(e);
                e = new Equipment();
                e.setName("Musician");
                eq.add(e);
                e = new Equipment();
                e.setName("Standard bearer");
                eq.add(e);
                unit.setEquipment(eq);


                armyUnit.setNumberOfUnits(10);
                armyUnit.setUnitName(unit);
                query.add(armyUnit);

                armyUnit = new ArmyUnit();
                unit = new Unit();
                unit.setName("Crossbowmen");
                armyUnit.setNumberOfUnits(50);
                armyUnit.setUnitName(unit);
                query.add(armyUnit);
//
                armyUnit = new ArmyUnit();
                unit = new Unit();
                unit.setName("Karl Franz");
                armyUnit.setNumberOfUnits(1);
                armyUnit.setUnitName(unit);
                query.add(armyUnit);

                armyUnit = new ArmyUnit();
                unit = new Unit();
                unit.setName("Hans Jaeger");
                armyUnit.setNumberOfUnits(1);
                armyUnit.setUnitName(unit);
                query.add(armyUnit);

                Army a2 = new Army();
                a2.setArmyPoints(2000);
                a2.setArmyUnits(query);

                org.Warhammer.CBREngine.Resources.ArmySimilarity armySim
                        = new org.Warhammer.CBREngine.Resources.ArmySimilarity();
                double comp = armySim.compute(a1, a2);
                System.out.println("computed similarity: "+comp);

            }
            System.exit(0);
        }
        catch (ExecutionException ex) {
            Logger.getLogger(CBREngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(-1);
    }
}
