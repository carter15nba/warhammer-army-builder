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

package myrmidia.CBR.Resources;

import java.util.Set;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Unit;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.UtilityUnit;

/**
 * Class to compute the similarity between two ArmyUnit objects
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class ArmyUnitSimilarity implements LocalSimilarityFunction{

    private int foundUnits;

    /**
     * Compute the similarity between the two supplied Set objects. Each
     * object can cosist of 0, 1 or more ArmyUnits and the similarity is based
     * on if the same unit is present in both list, but in different numbers,
     * equipment or utility units.
     * @param caseObject Set containing the ArmyUnits found in the case.
     * @param queryObject Set containing the ArmyUnits found int the query.
     * @return 0 if one or both input parameters are null or the similarity
     * between the inputs.
     * @throws NoApplicableSimilarityFunctionException if one or both input
     * parameters are not an instance of Set.
     *
     */
    @SuppressWarnings("unchecked")
    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof Set))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof Set))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
        Set caseSet = (Set<ArmyUnit>) caseObject;
        Set querySet = (Set<ArmyUnit>) queryObject;
        int querriedUnits = querySet.size();
        foundUnits=0;
        double numberSim = 0;
        double equipmentSim = 0;
        double utilitySim = 0;
        int notQueriedEquipmet=0;
        int notQueriedUtility = 0;
        if(querySet.isEmpty()){
            return 1;
        }
        for (Object object : querySet) {
            ArmyUnit queryArmyUnit = (ArmyUnit) object;
            Unit queryUnit = queryArmyUnit.getUnit();
            ArmyUnit caseArmyUnit = findSimilarUnit(caseSet, queryUnit);
            if(caseArmyUnit==null){
                continue;
            }
            //TODO: User specified interval.
            Interval interval = new Interval(5);
            numberSim += interval.compute(caseArmyUnit.getNumberOfUnits(), queryArmyUnit.getNumberOfUnits());
            equipmentSim += computeEquipmentSimilarity(caseArmyUnit.getEquipment(), queryArmyUnit.getEquipment());
            utilitySim += computeUtilitySimilarity(caseArmyUnit.getUtility(), queryArmyUnit.getUtility());
            if(!queryArmyUnit.getEquipment().isEmpty())
                notQueriedEquipmet++;
            if(!queryArmyUnit.getUtility().isEmpty())
               notQueriedUtility++;
        }
        double similarity = 0;
        double unitFractionSimilarity=0;
        if(foundUnits==0){
            return 0;
        }
        unitFractionSimilarity = (foundUnits/querriedUnits);
        numberSim = numberSim/foundUnits;
        int denominator=4;
        if(notQueriedEquipmet==0)
            denominator--;
        if(notQueriedUtility==0)
            denominator--;
        similarity = (double)((unitFractionSimilarity + numberSim + equipmentSim + utilitySim)/denominator);
        return similarity;
    }

    /** Applicable to java.util.Set */
    public boolean isApplicable(Object o1, Object o2) {
        if(!(o1 instanceof Set)||!(o2 instanceof Set))
            return false;
        return true;
    }

    /**
     * This method searches the case for the existence of the query unit.
     * @param caseObject A set with ArmyUnits found in the case base
     * @param queryObject The query unit
     * @return null if no match is found or the ArmyUnit matching the query.
     */
    private ArmyUnit findSimilarUnit(Set<ArmyUnit> caseObject, Unit queryObject){
        for (ArmyUnit caseArmyUnit : caseObject) {
            Unit caseUnit = caseArmyUnit.getUnit();
            if(caseUnit.getName().equalsIgnoreCase(queryObject.getName())){
                foundUnits++;
                return caseArmyUnit;
            }
        }
        return null;
    }

    /**
     * Computes the fraction of: (found_equipment/queried_equipment)
     * Method to compute the equipmentsimilarity measure between the found
     * case and the query case. If no equipment is presented in the query case
     * will the method return a similarity measure of 0.
     * @param caseEquipment Set with the equipment present in the Unit in the case.
     * @param queryEquipment Set with the equipment present in the Unit in the query.
     * @return 0 if the query have no equipment otherwise the similarity measure.
     */
    private double computeEquipmentSimilarity(Set<Equipment> caseEquipment, Set<Equipment> queryEquipment){
        int eqNumber = queryEquipment.size();
        if(eqNumber==0)
            return 0;
        int foundEq=0;
        for (Equipment queryEq : queryEquipment){
            for (Equipment caseEq : caseEquipment){
                if(queryEq.getName().equalsIgnoreCase(caseEq.getName())){
                    foundEq++;
                }
            }
        }
        return (double) foundEq/eqNumber;        
    }

    /**
     * Computes the fraction of: (found_utility/queried_utility)
     * Method to compute the utility unit similarity measure between the found
     * case and the query case. If no utility unit is presented in the query case
     * will the method return a similarity measure of 0.
     * @param caseUtility Set with utility units present in the Unit in the case.
     * @param queryUtility Set with the utility units present inthe Unit it the query.
     * @return 0 if the query have no utility units otherwise the similarity measure.
     */
    private double computeUtilitySimilarity(Set<UtilityUnit> caseUtility, Set<UtilityUnit> queryUtility){
        int utNumber = queryUtility.size();
        if(utNumber==0)
            return 0;
        int foundUt = 0;
        for (UtilityUnit queryUt : queryUtility) {
            for (UtilityUnit caseUt : caseUtility) {
                if(queryUt.getName().equalsIgnoreCase(caseUt.getName())){
                    foundUt++;
                }
            }
        }
        return (double) foundUt/utNumber;
    }
}