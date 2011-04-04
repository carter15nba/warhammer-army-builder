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

package org.Warhammer.CBR;

import org.Warhammer.Database.DatabaseManager;
import java.sql.SQLException;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.ExecutionException;
//import jcolibri.cbrcore.Connector;
import org.Warhammer.CBR.Resources.*;
import org.Warhammer.Warhammer.Case;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.selection.SelectCases;
import org.Warhammer.Database.Connector;
import org.Warhammer.Util.*;
import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Equipment;
import org.Warhammer.Warhammer.Unit;
import org.hibernate.Session;
/**
 * Singleton class responsible for all the CBR related functionality.
 * @author Glenn Rune Strandbåten
 * @version 0.2.1
 */
public class CBREngine implements jcolibri.cbraplications.StandardCBRApplication{
    //TODO: REMOVE ALL System.out.println() used for testing from all classes.
    //TODO: REMOVE ALL TODO'S (if they are completed/redundant/no longer valid)
    //TODO: Promote all(most) classes to version 1.0 at launch
    private Connector connector;
    private CBRCaseBase caseBase;
    private SimilarityMeasure similarityMeasure;

    /**
     * Default private constructor
     * Use CBREngine.getInstance() to aquire an instance of this object.
     */
    private CBREngine(){}
    /**
     * Method used to aquire the singleton instance of the CBREngine.
     * @return the CBR engine singleton object reference.
     */
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
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.disconnect();
        dbm.disconnectNoHibernate(true);
    }

    /**
     * Method used in the CBR cycle to retrieve the most similar cases from the
     * casebase.
     * @param cbrq The CBRQuery object
     * @return A collection with the retrieval results from the query.
     */
    private Collection<RetrievalResult> retrieve(CBRQuery cbrq){
        NNConfig conf = similarityMeasure.getSimilarityConfig();
        conf.setDescriptionSimFunction(new org.Warhammer.CBR.Resources.Average(caseBase.getCases(),cbrq));
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), cbrq, conf);
//        Print all cases
//        for (RetrievalResult retrievalResult : eval) {
//            Case _case = (Case) retrievalResult.get_case().getSolution();
//            PrintFactory.printCase(_case,retrievalResult.getEval(),false);
//        }
        //TODO: user specified k neares cases.
        return SelectCases.selectTopKRR(eval, 5);
    }

    /**
     * Method used in the CBR cycle to reuse (adapt/change) the retrieved results
     * to fit the problem (query) case
     * @param cbrq The CBRQuery object
     * @param retrievalResults The results of the retrieval process.
     */
    private void reuse(CBRQuery cbrq, Collection<RetrievalResult> retrievalResults){
        AdaptionEngine adaptionEngine = new AdaptionEngine();


        Collection<CBRCase> ncbr = new HashSet<CBRCase>();
        for (RetrievalResult retrievalResult : retrievalResults) {
            Case _case = (Case) retrievalResult.get_case().getSolution();
            Case adaptedCase = adaptionEngine.adaptCase(_case, cbrq);
//            CBRCase cc = new CBRCase();
//            cc.setDescription(retrievalResult.get_case().getDescription());
//            cc.setSolution(adaptedCase);
//            cc.setJustificationOfSolution(retrievalResult.get_case().getJustificationOfSolution());
//            ncbr.add(cc);
        }
        //connector.storeCases(ncbr);
      
    }
    /**
     * Method used int the CBR cycle to revise the changes and evaluate the
     * performance of the revised case.
     */
    private void revise(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method used in the CBR cycle to store relevant cases back into the casebase.
     */
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

                Unit unit = CreateObjectFromDB.createUnitFromDB("Empire:Halberdiers");
                armyUnit.setUnit(unit);
                armyUnit.setNumberOfUnits(10);
                armyUnitSet.add(armyUnit);

                armyUnit = new ArmyUnit();
                unit = CreateObjectFromDB.createUnitFromDB("Empire:Greatswords");
                armyUnit.setUnit(unit);
                armyUnit.setNumberOfUnits(19);
                armyUnitSet.add(armyUnit);
//                queryArmy.setArmyUnits(armyUnitSet);
                Set<Equipment> sEq = new HashSet<Equipment>();
                Equipment eq = new Equipment();
                eq.setName("Musician");
                sEq.add(eq);
                unit.setEquipment(sEq);

                armyUnit = new ArmyUnit();
                unit = CreateObjectFromDB.createUnitFromDB("Empire:Steam Tank");
                armyUnit.setNumberOfUnits(1);
                armyUnit.setUnit(unit);
                armyUnitSet.add(armyUnit);
                queryCase.setArmy(queryArmy);

                System.out.println("Query Case");
                PrintFactory.printCase(queryCase,true);

                cbrQuery.setDescription(queryCase);
                cbrEngine.cycle(cbrQuery);

                cbrEngine.postCycle();
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
            else if(args[0].equalsIgnoreCase("unitSimTest")){
                UnitSimilarity us = new UnitSimilarity(1,1,1,1,1,1);
                Unit unit1 = CreateObjectFromDB.createUnitFromDB("Empire:Halberdiers");
                Unit unit2 = CreateObjectFromDB.createUnitFromDB("High_Elves:Spearmen");
                double sim = us.compute(unit1,unit2);
                PrintFactory.printUnit(unit1);
                PrintFactory.printUnit(unit2);
                System.out.println("Simil: "+sim);
            }
            System.exit(0);
        }
        catch (ExecutionException ex) {}
        System.exit(-1);
    }
}