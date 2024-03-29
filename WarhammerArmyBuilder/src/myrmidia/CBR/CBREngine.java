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

package myrmidia.CBR;

import java.sql.SQLException;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.ExecutionException;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.selection.SelectCases;
import myrmidia.Database.Connector;
import myrmidia.Util.*;
import myrmidia.CBR.Resources.*;
import myrmidia.Warhammer.Case;
import myrmidia.Warhammer.Army;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.Unit;
import myrmidia.Database.DatabaseManager;
import myrmidia.Explanation.ExplanationEngine;
import myrmidia.Enums.Outcomes;
import myrmidia.Enums.Races;
/**
 * Singleton class responsible for all the CBR related functionality.
 * @author Glenn Rune Strandbåten
 * @version 1.0
 */
public class CBREngine implements jcolibri.cbraplications.StandardCBRApplication{
    private Connector connector;
    private CBRCaseBase caseBase;
    private SimilarityMeasure similarityMeasure;
    private PrepareCase prepareCase;
    private SimilarityConfiguration simConfig;
    private ArmyChangeStatus[] armyChangeStatus;

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
        prepareCase = PrepareCase.getInstance();
        simConfig = SimilarityConfiguration.getInstance();
    }

    public CBRCaseBase preCycle() throws ExecutionException {
        caseBase.init(connector);
        return caseBase;
    }

    public void cycle(CBRQuery cbrq) throws ExecutionException {        
        Collection<RetrievalResult> retrievalResults = retrieve(cbrq);
        Collection<CBRCase> cbr = reuse(cbrq, retrievalResults);
//        revise();
//        retain(cbr);
    }

    public void postCycle() throws ExecutionException {
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.disconnect();
    }

    /**
     * Method used in the CBR cycle to retrieve the most similar cases from the
     * casebase.
     * @param cbrq The CBRQuery object
     * @return A collection with the retrieval results from the query.
     */
    public Collection<RetrievalResult> retrieve(CBRQuery cbrq){
        NNConfig conf = similarityMeasure.getSimilarityConfig();
        conf.setDescriptionSimFunction(new Average(caseBase.getCases()));
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), cbrq, conf);
        Collection<RetrievalResult> topKRR = SelectCases.selectTopKRR(eval, simConfig.getK());
        ExplanationEngine.getInstance().orderTransparencyExplanations(topKRR);
        return topKRR;
    }

    /**
     * Method used in the CBR cycle to reuse (adapt/change) the retrieved results
     * to fit the problem (query) case
     * @param cbrq The CBRQuery object
     * @param retrievalResults The results of the retrieval process.
     */
    public Collection<CBRCase> reuse(CBRQuery cbrq, Collection<RetrievalResult> retrievalResults){
        AdaptionEngine adaptionEngine = new AdaptionEngine();
        Collection<CBRCase> ncbr = new HashSet<CBRCase>();
        armyChangeStatus = new ArmyChangeStatus[retrievalResults.size()];
        int count = 0;
        for (RetrievalResult retrievalResult : retrievalResults) {
            Case _case = (Case) retrievalResult.get_case().getSolution();
            try{
                Case adaptedCase = adaptionEngine.adaptCase(_case, cbrq);
                boolean change = adaptionEngine.isArmyUnchanged();
                int caseID = adaptedCase.getID();
                System.out.println("CASEID: "+caseID+", CHANGE: "+change);
                armyChangeStatus[count++] = new ArmyChangeStatus(caseID, change);
                CBRCase cc = new CBRCase();
                cc.setDescription(retrievalResult.get_case().getDescription());
                cc.setSolution(adaptedCase);
                cc.setJustificationOfSolution(retrievalResult.get_case().getJustificationOfSolution());
                ncbr.add(cc);
            }
            catch(ConcurrentModificationException cme){}            
        }
        return ncbr;     
    }
    /**
     * Method used int the CBR cycle to revise the changes and evaluate the
     * performance of the revised case.
     */
    public void revise(){
        //Revise performed exclusivly in the ReviseUI as only user interaction
        //and no automated functionality is utilized.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method used in the CBR cycle to store relevant cases back into the casebase.
     */
    public void retain(Collection<CBRCase> cbrCase){
        for (CBRCase cBRCase : cbrCase) {
            Case solution = (Case) cBRCase.getSolution();
            ArmyChangeStatus acs = getArmyChangeStatus(cBRCase);
            if(!acs.isUnchanged())
                prepareCase.prepareFullCase(solution);
            else
                prepareCase.prepareCase(solution);
            acs.setCaseID(solution.getID());
            PrintFactory.printCase(solution, false,null);
        }
        connector.storeCases(cbrCase);
    }
    
    /**
     * Method to get the ArmyChangeStatus for the supplied case.
     * @param _case The case to get the status of
     * @return null if no mathcing status where found, or the ArmyChangeStatus 
     * object requested.
     */
    public ArmyChangeStatus getArmyChangeStatus(CBRCase _case){
        Case c = (Case) _case.getSolution();
        for(ArmyChangeStatus acs : armyChangeStatus){
            if(c.getID()==acs.getCaseID())
                return acs;
        }
        return null;
    }

    public static void main(String[] args) throws NoApplicableSimilarityFunctionException, SQLException {
        try {
            if(args.length==0){
                CBREngine cbrEngine = CBREngine.getInstance();
                cbrEngine.configure();
                cbrEngine.preCycle();

                CBRQuery cbrQuery = new CBRQuery();
                Case queryCase = new Case();
                queryCase.setOutcome(Outcomes.Victory);
                queryCase.setOpponent(Races.Wood_Elves);

                Army queryArmy = new Army();
                queryArmy.setArmyPoints(5000);
                queryArmy.setPlayerRace(Races.Dwarfs);

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
                queryArmy.setArmyUnits(armyUnitSet);
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
                PrintFactory.printCase(queryCase,true,null);

                cbrQuery.setDescription(queryCase);
                cbrEngine.cycle(cbrQuery);

                cbrEngine.postCycle();
                
            }
            System.exit(0);
        }
        catch (ExecutionException ex) {}
        System.exit(-1);
    }
}