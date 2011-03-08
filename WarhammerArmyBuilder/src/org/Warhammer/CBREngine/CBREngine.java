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
    private SimilarityMeasure similarityMeasure;

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
        similarityMeasure = new SimilarityMeasure();
    }

    public CBRCaseBase preCycle() throws ExecutionException {
        caseBase.init(connector);
        return caseBase;
    }

    public void cycle(CBRQuery cbrq) throws ExecutionException {        
        Collection<RetrievalResult> retrievalResults = retrieve(cbrq);

        reuse(cbrq, retrievalResults);
//        revise();
//        retain();
    }

    public void postCycle() throws ExecutionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Collection<RetrievalResult> retrieve(CBRQuery cbrq){
        NNConfig conf = similarityMeasure.getSimilarityConfig();
        conf.setDescriptionSimFunction(new org.Warhammer.CBREngine.Resources.Average(caseBase.getCases(),cbrq));
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), cbrq, conf);
//        Print all cases
//        System.out.println("Case similarity:");
//        for (RetrievalResult retrievalResult : eval) {
//            Case _case = (Case) retrievalResult.get_case().getSolution();
//            org.Warhammer.Util.PrintFactory.printCase(_case,retrievalResult.getEval(),false);
//        }
        //TODO: user specified k neares cases.
        System.out.println("Retrieve phase done!");
        return SelectCases.selectTopKRR(eval, 2);
    }

    private void reuse(CBRQuery cbrq, Collection<RetrievalResult> retrievalResults){
        
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
                queryArmy.setArmyPoints(0);
                queryArmy.setPlayerRace(Case.Races.Empire);

                Set<ArmyUnit> armyUnitSet = new HashSet<ArmyUnit>();
                ArmyUnit armyUnit = new ArmyUnit();

                Unit unit = new Unit();
                unit.setName("Empire:Halberdiers");
                armyUnit.setUnit(unit);
                armyUnit.setNumberOfUnits(10);
                armyUnitSet.add(armyUnit);

                armyUnit = new ArmyUnit();
                unit = new Unit();
                unit.setName("Empire:Greatswords");
                armyUnit.setUnit(unit);
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
                //Create case 0
                Case _case = new Case();
                _case.setID(0);
                _case.setOpponent(Case.Races.Empire);
                _case.setOutcome(Case.Outcomes.Victory);
                //Create case 0 army
                Army army = new Army();
                army.setArmyPoints(2500);
                army.setID(0);
                army.setPlayerRace(Case.Races.Dwarfs);
                //Create case 0 army 0 units
                ArmyUnit a0 = new ArmyUnit();
                a0.setArmyID(0);
                a0.setID(0);
                a0.setNumberOfUnits(10);
                Unit u1 = new Unit();
                u1.setName("Jojomen");
                u1.setCost(1);
                a0.setUnit(u1);

                ArmyUnit a1 = new ArmyUnit();
                a1.setArmyID(0);
                a1.setID(0);
                a1.setNumberOfUnits(20);
                Unit u2 = new Unit();
                Equipment e1 = new Equipment();
                e1.setName("Roboman");
                e1.setCost(500);
                Set<Equipment> eqset = new HashSet<Equipment>();
                eqset.add(e1);
                u2.setName("Herculees");
                u2.setCost(1);
                a1.setUnit(u2);
                a1.setEquipment(eqset);
                Set<ArmyUnit> auset = new HashSet<ArmyUnit>();
                auset.add(a0);
                auset.add(a1);
                army.setArmyUnits(auset);
                _case.setArmy(army);

                System.out.println("Case");
                org.Warhammer.Util.PrintFactory.printCase(_case, true);


                //QUERY
                Case _case1 = new Case();
                _case1.setID(0);
                _case1.setOpponent(Case.Races.Empire);
                _case1.setOutcome(Case.Outcomes.Victory);
                //Create case 0 army
                Army army1 = new Army();
                army1.setArmyPoints(2500);
                army1.setID(0);
                army1.setPlayerRace(Case.Races.Dwarfs);
                //Create case 0 army 0 units
                ArmyUnit a3 = new ArmyUnit();
                a3.setArmyID(0);
                a3.setID(0);
                a3.setNumberOfUnits(10);
                Unit u3 = new Unit();
                u3.setName("Jojomen");
                u3.setCost(1);
                a3.setUnit(u3);

                ArmyUnit a4 = new ArmyUnit();
                a4.setArmyID(0);
                a4.setID(0);
                a4.setNumberOfUnits(20);
                Unit u4 = new Unit();
                Equipment e2 = new Equipment();
                e2.setName("Roboman");
                e2.setCost(500);
                Set<Equipment> eqset1 = new HashSet<Equipment>();
                eqset1.add(e1);
                u4.setName("Herculees");
                u4.setCost(1);
                a4.setUnit(u4);
                a4.setEquipment(eqset1);
                Set<ArmyUnit> auset1 = new HashSet<ArmyUnit>();
                auset1.add(a3);
                auset1.add(a4);
                army1.setArmyUnits(auset1);
                _case1.setArmy(army1);
                
                System.out.println("Query");
                 org.Warhammer.Util.PrintFactory.printCase(_case1, true);

                org.Warhammer.CBREngine.Resources.ArmySimilarity armySim
                        = new org.Warhammer.CBREngine.Resources.ArmySimilarity();
                double comp = armySim.compute(army, army1);
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
