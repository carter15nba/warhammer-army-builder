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

package org.Warhammer.CBREngine.Resources;

import java.util.Iterator;
import java.util.Set;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Unit;
import org.Warhammer.Warhammer.Equipment;
import org.Warhammer.Warhammer.UtilityUnit;

/**
 * Class to compute the similarity between two ArmyUnit objects
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class ArmyUnitSimilarity implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

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
     */
    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null)){
            return 0;
        }
        if (!(caseObject instanceof Set))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof Set))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
        Set caseSet = (Set<ArmyUnit>) caseObject;
        Set querySet = (Set<ArmyUnit>) queryObject;

        foundUnits=0;
        Iterator it = querySet.iterator();
        double numberSim = 0;
        double equipmentSim = 0;
        double utilitySim = 0;
        //Iterate through the query set.
        while(it.hasNext()){
            ArmyUnit queryArmyUnit = (ArmyUnit) it.next();
            Unit queryUnit = queryArmyUnit.getUnitName();
            //Find similar army unit
            ArmyUnit caseArmyUnit = (ArmyUnit) findSimilarUnit(caseSet, queryUnit);
            if(caseArmyUnit==null){
                numberSim-=1;
                continue;
            }
            //TODO: User specified number of units interval
            Interval interval = new Interval(10);
            numberSim += interval.compute(caseArmyUnit.getNumberOfUnits(), queryArmyUnit.getNumberOfUnits());
            equipmentSim += computeEquipmentSimilarity(caseArmyUnit.getEquipment(), queryArmyUnit.getEquipment());
            utilitySim += computeUtilitySimilarity(caseArmyUnit.getUtility(), queryArmyUnit.getUtility());
        }
        if(foundUnits==0)
            return numberSim;
        else if((equipmentSim==0)&&(utilitySim==0))
            return numberSim/foundUnits;
        if(equipmentSim!=0)
            numberSim = (double) numberSim*equipmentSim;
        if(utilitySim!=0)
            numberSim = (double) numberSim*utilitySim;
        return (double)numberSim/foundUnits;
    }

    /**
     * Allways returns true, as the applicability check is performed in the
     * compute method.
     * @param caseObject
     * @param queryObject
     * @return true
     */
    public boolean isApplicable(Object caseObject, Object queryObject) {
        return true;
    }

    /**
     * This method searches the case for the existence of the query unit.
     * @param caseObject
     * @param queryObject
     * @return null if no match is found or the ArmyUnit matching the query.
     */
    private ArmyUnit findSimilarUnit(Set<ArmyUnit> caseObject, Unit queryObject){
        Iterator it = caseObject.iterator();
        while(it.hasNext()){
            ArmyUnit caseArmyUnit = (ArmyUnit) it.next();

            Unit caseUnit = caseArmyUnit.getUnitName();
            if(caseUnit.getName().contentEquals(queryObject.getName())){
                foundUnits++;
                return caseArmyUnit;
            }
        }
        return null;
    }

    /**
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
        Iterator queryIt = queryEquipment.iterator();
        int foundEq=0;
        while(queryIt.hasNext()){
            Iterator caseIt = caseEquipment.iterator();
            Equipment queryEq = (Equipment) queryIt.next();
            while(caseIt.hasNext()){
                Equipment caseEq = (Equipment) caseIt.next();
                if(queryEq.getName().equalsIgnoreCase(caseEq.getName())){
                   foundEq++;
                   break;
                }
            }
        }
        return (double) foundEq/eqNumber;
        
    }

    /**
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
        Iterator queryIt = queryUtility.iterator();
        int foundUt = 0;
        while(queryIt.hasNext()){
            Iterator caseIt = caseUtility.iterator();
            UtilityUnit queryUt = (UtilityUnit) queryIt.next();
            while(caseIt.hasNext()){
                UtilityUnit caseUt = (UtilityUnit) caseIt.next();
                if(queryUt.getName().equalsIgnoreCase(caseUt.getName())){
                    foundUt++;
                    break;
                }
            }
        }
        return (double) foundUt/utNumber;
    }
}