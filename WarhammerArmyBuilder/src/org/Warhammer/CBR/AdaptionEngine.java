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

package org.Warhammer.CBR;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.CBR.Resources.UnitSimilarity;
import org.Warhammer.Util.CollectionControl;
import org.Warhammer.Util.CreateObjectFromDB;
import org.Warhammer.Warhammer.*;
import org.Warhammer.Warhammer.Resources.Causes;
import org.Warhammer.Warhammer.Resources.ErrorManager.Messages;
import org.Warhammer.Warhammer.Unit.armyType;

/**
 * Class to perform the case adaption
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class AdaptionEngine {

    private double fullCommandPercentage = 0.8;

    /**
     * Method to adapt a case to fit the query.
     * @param _case The case to be adapted
     * @param query The query to adapt the case over.
     * @return Case - The adapted case
     */
    public Case adaptCase(Case _case, CBRQuery query){
        Case adaptedCase = naiveAdaption(_case, query);
        return refineAdaption(adaptedCase, query);
    }

    /**
     * Method to perfor a naïve preliminary case adaption. The player race,
     * army points, and army is changed to reflect the querty.
     * @param _case The case to be adapted
     * @param query The query to adapt the case over
     * @return Case - The adapted case
     */
    private Case naiveAdaption(Case _case, CBRQuery query){
        Case adaptedCase = Case.copy(_case);
        Case queryCase = (Case) query.getDescription();
        adaptedCase.setOpponent(queryCase.getOpponent());
        adaptedCase.setOutcome(Case.Outcomes.Unknown);
        adaptedCase.setArmy(adaptArmyFromQuery(_case, queryCase));
        return adaptedCase;
    }

    /**
     * Method to refine the results of the naïve adaption by changeing the case
     * to adhere to the rules of the army creation process.
     * @param adaptedCase The results of the naïve adaption
     * @param query The query to adapt the case over
     * @return Case - The adapted case
     */
    private Case refineAdaption(Case adaptedCase, CBRQuery query){
        adaptedCase.setArmy(refineArmy(adaptedCase.getArmy()));
        //TODO: whatever goes here
        return adaptedCase;
    }

    /**
     * This method changes the army to contain all the units requested in the
     * query, this methodd take no responsibility for that the adapted army
     * adheres to the rules of the army creation process and is part of the naïve
     * adaption.
     * @param _case The case to adapt
     * @param queryCase The query to adapt the case over
     * @return Army - The adapted army.
     */
    private Army adaptArmyFromQuery(Case _case, Case queryCase){
        Army adaptedArmy = Army.copy(_case.getArmy());
        adaptedArmy.setArmyPoints(queryCase.getArmy().getArmyPoints());
        Set<ArmyUnit> queryUnits = queryCase.getArmy().getArmyUnits();
        boolean[] isAdapted = new boolean[queryUnits.size()];
        int pos=0;
        //Find and replace units in the case with query units
        //(Adapt present units e.g.: queried units already present in the case).
        for(ArmyUnit queryUnit : queryUnits){
            Unit query = queryUnit.getUnit();
            for(ArmyUnit adaptedUnit : adaptedArmy.getArmyUnits()){
                Unit adapted = adaptedUnit.getUnit();
                if(query.getName().equals(adapted.getName())){
                    adaptedUnit.setNumberOfUnits(queryUnit.getNumberOfUnits());
                    //Replace the equipment/utility if and only if the queried
                    //unit have specified equipment/utility, otherwise keep the
                    //case equipment/utility.
                    if(!queryUnit.getEquipment().isEmpty())
                        adaptedUnit.setEquipment(queryUnit.getEquipment());
                    if(!queryUnit.getUtility().isEmpty())
                        adaptedUnit.setUtility(queryUnit.getUtility());
                    isAdapted[pos] = true;
                }
            }
            pos++;
        }
        //If units in query not adapted, find possible replacement candidates
        //and perform the adaption.
        pos = 0;
        for(ArmyUnit queryUnit : queryUnits){
            if(isAdapted[pos++])
                continue;
            Unit query = queryUnit.getUnit();
            UnitSimilarity unitSimilarity = new UnitSimilarity();
            ArrayList<Double> similarity = new ArrayList<Double>();
            try{
                //Calculate the similarity between all units in the army and the
                //current query unit.
                for (ArmyUnit adaptedUnit : adaptedArmy.getArmyUnits()) {
                    boolean calculate = true;
                    //Do not calculate the similarity on any unit that is
                    //present in the query.
                    for(ArmyUnit isQuery : queryUnits){
                        if(adaptedUnit.getUnit().getName().equals(isQuery.getUnit().getName())){
                            calculate = false;
                            break;
                        }
                    }
                    double sim = 0;
                    if(calculate)
                        sim = unitSimilarity.compute(query, adaptedUnit.getUnit());
                    similarity.add(sim);
                }
                //Find most similar
                int index = -1;
                double highest = -1;
                for(int i = 0; i < similarity.size(); i++){
                    double sim = similarity.get(i);
                    if(highest<sim){
                        highest = sim;
                        index = i;
                    }
                }
                //Adapt most similar unit to be the requested unit
                ArmyUnit armyUnit = (ArmyUnit) CollectionControl.getItemAt(adaptedArmy.getArmyUnits(), index);
                armyUnit.setUnit(query);
                armyUnit.setUtility(queryUnit.getUtility());
                armyUnit.setEquipment(queryUnit.getEquipment());
                armyUnit.setNumberOfUnits(queryUnit.getNumberOfUnits());
            }
            catch(NoApplicableSimilarityFunctionException e){}
        }
        return adaptedArmy;
    }

    /**
     * This method is responsible for refining the army and ensures that all
     * the rules are followed. This method will change, remove or add units
     * to the army to ensure that the rules are followed. The results of this
     * method is the final adapated case returned from the adaption engine. 
     * @param army - The army to refine
     * @return Army - The refined army.
     */
    private Army refineArmy(Army army){
        RuleSet rule = new RuleSet();
        Messages[] messages = rule.isFollowingArmyDispositionRules(army);
        ArrayList<Unit> units;
        while(messages[0]!=Messages.OK){
            //Only check the first error each loop iteration as one change may
            //affect other error messages.
            Messages message = messages[0];
            Causes[] causes;
            for (Messages msg : messages) {
                System.out.println("Error message: "+msg);
                System.out.println(rule.calculateTotalCost());
            }
            switch(message){
                case TOO_FEW_CORE_GROUPS:
                    units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                        army.getPlayerRace(), armyType.Core);
                    addUnitGroup(army, units, armyType.Core);
                    break;
                case TOO_FEW_CORE_POINTS:
                    units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                        army.getPlayerRace(), armyType.Core);
                    addUnitGroup(army, units, armyType.Core);
                    break;
                case TOO_FEW_POINTS_TOTAL:
                    //TODO: Exchange method to one that are more flexible and
                    //not only adds core units.
                    break;
                case TOO_FEW_UNITS_IN_GROUP:
                    causes = rule.getCauses(message);
                    increaseGroupUnits(army,causes);
                    break;
                case TOO_MANY_DUPLIACTE_SPECIAL_UNITS:
                    causes = rule.getCauses(message);
                    decreaseDuplicates(army,causes);
                    break;
                case TOO_MANY_DUPLICATE_RARE_UNITS:
                    causes = rule.getCauses(message);
                    decreaseDuplicates(army,causes);
                    break;
                case TOO_MANY_HEROES:
                    removeOneCharacter(army, armyType.Hero);
                    break;
                case TOO_MANY_HERO_POINTS:
                    reduceCharacterPoints(army, armyType.Hero, messages);
                    break;
                case TOO_MANY_LORDS:
                    removeOneCharacter(army, armyType.Lord);
                    break;
                case TOO_MANY_LORD_POINTS:
                    reduceCharacterPoints(army, armyType.Hero, messages);
                    break;
                case TOO_MANY_POINTS_TOTAL:

                    break;
                case TOO_MANY_RARE_GROUPS:
                    removeGroup(army, armyType.Rare);
                    break;
                case TOO_MANY_RARE_POINTS:
                    causes = rule.getCauses(message);
                    reduceSpecialOrRareGroupPoints(army, armyType.Rare, 
                            messages, message, causes);
                    break;
                case TOO_MANY_SPECIAL_GROUPS:
                    removeGroup(army, armyType.Special);
                    break;
                case TOO_MANY_SPECIAL_POINTS:
                    causes = rule.getCauses();
                    reduceSpecialOrRareGroupPoints(army, armyType.Special,
                            messages, message, causes);
                    break;
                case TOO_MANY_UNITS_IN_GROUP:
                    causes = rule.getCauses();
                    reduceNumberOfUnitsInGroup(army, causes);
                    break;
                case NO_ARMY_GENERAL:
                    break;
                case WRONG_RACE:
                    break;
                default:
                    continue;
                
            }
            //TODO: Make sure that the while-loop can be broken.
            messages = rule.isFollowingArmyDispositionRules(army);
        }
        return army;
    }

    /**
     * This method adds a new unit group to the army, the new unit will be
     * placed in a full command group if full command is available and if the
     * full command threshold is not reached
     * @param army - The army to add a new unit group to
     * @param availableUnits - The list of available units (based on race and
     * army type)
     * @param armyType - The army type for the unit group to be added
     */
    private void addUnitGroup(Army army, ArrayList<Unit> availableUnits, 
            armyType aT){
        UnitSimilarity unitSim = new UnitSimilarity();
        Set<ArmyUnit> armyUnits = army.getArmyUnits();
        double sim = 1;
        int pos=0;
        int armyId=0;
        //Find the unit which is least similar to the core units already in the
        //army, this is probably a unit you could have need of.

        for (ArmyUnit armyUnit : armyUnits) {
            Unit unit = armyUnit.getUnit();
            armyId = armyUnit.getArmyID();
            if(unit.getArmyType()==aT){
                for(int i = 0; i < availableUnits.size(); i++){
                    try {
                        double tmp = unitSim.compute(unit, availableUnits.get(i));
                        if(tmp<sim){
                            sim=tmp;
                            pos=i;
                        }
                    }
                    catch (NoApplicableSimilarityFunctionException ex) {}
                }
            }
        }
        Unit newUnit = availableUnits.get(pos);
        ArmyUnit newArmyUnit = new ArmyUnit();
        newArmyUnit.setUnit(newUnit);
        newArmyUnit.setArmyID(armyId);
        int numUnits = newUnit.getMinNumber()*2;
        //if the number of untis are more than the max number of units
        //decrement by 1.
        while(numUnits>newUnit.getMaxNumber()&&newUnit.getMaxNumber()!=0)
            numUnits--;
        newArmyUnit.setNumberOfUnits(numUnits);
        if(applyFullCommand(army)){
            Set<Equipment> eq = CreateObjectFromDB.getUnitEquipment(newUnit.getName());
            Set<UtilityUnit> ut = CreateObjectFromDB.getUtilityUnits(newUnit.getName());
            Set<Equipment> newEq = new HashSet<Equipment>();
            Set<UtilityUnit> newUt = new HashSet<UtilityUnit>();
            for (UtilityUnit utilityUnit : ut) {
                if(utilityUnit.isPromotionUnit())
                    newUt.add(utilityUnit);
            }
            for (Equipment equipment : eq) {
                String name = equipment.getName();
                if(name.contains("Musician"))
                    newEq.add(equipment);
                if(name.contains("Standard bearer"))
                    newEq.add(equipment);
            }
            newArmyUnit.setEquipment(newEq);
            newArmyUnit.setUtility(newUt);
        }
        System.out.println("Adding new "+aT+" group: "+newUnit.getName()+
                ", num units: "+newArmyUnit.getNumberOfUnits()+
                ", full command: "+newArmyUnit.haveFullCommand());
        armyUnits.add(newArmyUnit);
    }

    /**
     * This method determines if the full command threshold have been reached
     * @param army -The army to check the full command threshold
     * @return <ul><li>true - if a new full command group can be created</li>
     * <li>false - if a nee full command group cannot be created</li></ul>
     */
    private boolean applyFullCommand(Army army){
        int eligibleFullCommandUnits = 0;
        int unitsWithFullCommand = 0;
        for (ArmyUnit au : army.getArmyUnits()) {
            if(au.getUnit().isEligibleForFullCommand())
                eligibleFullCommandUnits++;
            if(au.haveFullCommand())
                unitsWithFullCommand++;
        }
        double fraction = (double) unitsWithFullCommand/eligibleFullCommandUnits;
        if(fraction<fullCommandPercentage)
            return true;
        return false;
    }

    /**
     * Method to increase the number of units in a group. (used when too few
     * units is present)
     * @param army - The army which contains the group with the low number of
     * units
     * @param causes - The causes for the error, contains information of which
     * group contains to few units.
     */
    private void increaseGroupUnits(Army army, Causes[] causes) {
        for(Causes cause : causes){
            Unit causeUnit = cause.getUnit();
            Set<ArmyUnit> armyUnits = army.getArmyUnits();
            for(ArmyUnit armyUnit : armyUnits){
                if(armyUnit.getUnit().getName().contentEquals(causeUnit.getName())){
                    if(armyUnit.getNumberOfUnits()<causeUnit.getMinNumber()){
                        armyUnit.setNumberOfUnits(causeUnit.getMinNumber());
                        System.out.println("Increasing group units: "+
                                armyUnit.getUnit().getName());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Method to decrease the number of duplicate special/rare units by one.
     * @param army The army to alter
     * @param causes The causes for the army to be altered.
     */
    private void decreaseDuplicates(Army army, Causes[] causes) {
        boolean decreased = false;
        for(Causes cause : causes){
            Unit causeUnit = cause.getUnit();
            Set<ArmyUnit> armyUnits = army.getArmyUnits();
            for(ArmyUnit armyUnit : armyUnits){
                if(armyUnit.getUnit().getName().contentEquals(causeUnit.getName())){
                    armyUnits.remove(armyUnit);
                    System.out.println("Decreasing duplicate unit: "+
                            armyUnit.getUnit().getName());
                    decreased = true;
                    break;
                }
            }
            if(decreased)
                break;
        }
    }

    private void removeOneCharacter(Army army, armyType aT) {
        ArmyUnit removeUnit=null;
        int totalCost=0;
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==aT){
               if(totalCost==0)
                   removeUnit = armyUnit;
               if(army.calculateTotalUnitCost(armyUnit)<totalCost)
                   removeUnit = armyUnit;
            }
        }
        if(removeUnit!=null){
            System.out.println("removing "+aT+": "+removeUnit.getUnit().getName());
            army.getArmyUnits().remove(removeUnit);
        }
    }

    private void reduceCharacterPoints(Army army, armyType aT, Messages[] messages) {
        Messages hero = Messages.OK;
        Messages lord = Messages.OK;
        boolean heroAndOrLord = false;
        //Check if there are too many lords and/or heroes and remove one
        //hero and/or lord if there is.
        for (Messages message : messages) {
            switch(message){
                case TOO_MANY_LORDS:
                    lord = Messages.TOO_MANY_LORDS;
                    heroAndOrLord = true;
                    break;
                case TOO_MANY_HEROES:
                    hero = Messages.TOO_MANY_HEROES;
                    heroAndOrLord=true;
                    break;
            }
        }
        if(hero==Messages.TOO_MANY_HEROES)
            removeOneCharacter(army, aT.Hero);
        if(lord==Messages.TOO_MANY_LORDS)
            removeOneCharacter(army, aT.Lord);
        //If there were a hero or lord too many do not perform any other adaption.
        if(heroAndOrLord)
            return;
        //Remove the cheapest equipment or unit from the lord/hero wich have
        //spent the most points. 
        int cost = 0;
        ArmyUnit mostExpensiveUnit = null;
        //Find the most expensive unit
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            if(armyUnit.getUnit().getArmyType()==aT){
                int tmpCost = army.calculateTotalUnitCost(armyUnit);
                if(tmpCost>cost)
                    mostExpensiveUnit = armyUnit;
            }
        }
        if(mostExpensiveUnit!=null){
            Equipment eq = null;
            UtilityUnit ut = null;
            int eCost=0;
            int uCost=0;
            for(Equipment equipment : mostExpensiveUnit.getEquipment()){
                if(equipment.getCost()<eCost||eCost==0){
                    eCost = equipment.getCost();
                    eq = equipment;
                }

            }
            for(UtilityUnit utility : mostExpensiveUnit.getUtility()){
                if(utility.getCost()<uCost||uCost==0){
                    uCost = utility.getCost();
                    ut = utility;
                }
            }
            System.out.println("Removing cheapest util/eq from: "+
                    mostExpensiveUnit.getUnit().getName());
            if(eCost<uCost&&eq!=null)
                mostExpensiveUnit.getEquipment().remove(eq);
            else if(uCost<eCost&&ut!=null)
                mostExpensiveUnit.getUtility().remove(ut);
            else if(eq==null&&ut!=null)
                mostExpensiveUnit.getUtility().remove(ut);
            else if(ut==null&&eq!=null)
                mostExpensiveUnit.getEquipment().remove(eq);
            
        }
    }

    private void removeGroup(Army army, armyType aT) {
        int cost=0;
        ArmyUnit removeUnit=null;
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==aT){
                int unitCost = army.calculateTotalUnitCost(armyUnit);
                if(unitCost<cost||cost==0){
                    cost = unitCost;
                    removeUnit = armyUnit;
                }
            }
        }
        System.out.println("Removing: "+removeUnit.getUnit().getName());
        army.getArmyUnits().remove(removeUnit);
    }

    private void reduceSpecialOrRareGroupPoints(Army army, armyType aT,
            Messages[] messages, Messages message, Causes[] causes){
        Messages special = Messages.OK;
        Messages rare = Messages.OK;
        Messages duplicateR = Messages.OK;
        Messages duplicateS = Messages.OK;
        //Check if there is another reason for the special/rare groups to have
        //too many poinst
        for (Messages msg : messages) {
            switch(msg){
                case TOO_MANY_SPECIAL_GROUPS:
                    special = Messages.TOO_MANY_SPECIAL_GROUPS;
                    break;
                case TOO_MANY_RARE_GROUPS:
                    rare = Messages.TOO_MANY_RARE_GROUPS;
                    break;
                case TOO_MANY_DUPLIACTE_SPECIAL_UNITS:
                    duplicateS = Messages.TOO_MANY_DUPLIACTE_SPECIAL_UNITS;
                    break;
                case TOO_MANY_DUPLICATE_RARE_UNITS:
                    duplicateR = Messages.TOO_MANY_DUPLICATE_RARE_UNITS;
                    break;
            }
        }
        if(duplicateR!=Messages.OK||duplicateS!=Messages.OK){
            decreaseDuplicates(army,causes);
        }
        //if one or both of too many duplicate unit groups are present, the error
        //may have been fixed by removing the duplicates.
        if(duplicateR!=duplicateS)
            return;
        if(special!=Messages.OK)
            removeGroup(army, armyType.Special);
        if(rare!=Messages.OK)
            removeGroup(army, armyType.Rare);
        //If one or both of the rare and special had too many groups to adhere
        //to the rules, the error might be fixed.
        if(rare!=special)
            return;
        //If neither of the above reductions helped solve the problem, remove
        //the special or rare unit which costs the least alternatively reduce
        //the number of units in the group.
        //TODO: Implement rest of method
        throw new UnsupportedOperationException("Not yet implemented exception");
    }

    private void reduceNumberOfUnitsInGroup(Army army, Causes[] causes) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}