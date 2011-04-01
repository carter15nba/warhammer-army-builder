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
import java.util.Set;
import jcolibri.cbrcore.CBRQuery;
import org.Warhammer.CBR.Resources.CommonAdaptionFunctions;
import org.Warhammer.CBR.Resources.ExchangeRace;
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



    private CommonAdaptionFunctions cAF;

    public AdaptionEngine(){
        cAF = new CommonAdaptionFunctions(new UnitSimilarity());
    }
    public AdaptionEngine(UnitSimilarity unitSimilarity){
        cAF = new CommonAdaptionFunctions(unitSimilarity);
    }

    /**
     * Method to adapt a case to fit the query.
     * @param _case The case to be adapted
     * @param query The query to adapt the case over.
     * @return Case - The adapted case
     */
    public Case adaptCase(Case _case, CBRQuery query){
        cAF.resetUsedMagicalEquipment();
        Case adaptedCase = naiveAdaption(_case, query);
        return refineAdaption(adaptedCase);
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
        adaptedCase.getArmy().setPlayerRace(queryCase.getArmy().getPlayerRace());
        adaptedCase.setArmy(adaptArmyFromQuery(_case, queryCase));
        return adaptedCase;
    }

    /**
     * Method to refine the results of the naïve adaption by changeing the case
     * to adhere to the rules of the army creation process.
     * @param adaptedCase The results of the naïve adaption
     * @return Case - The adapted case
     */
    private Case refineAdaption(Case adaptedCase){
        Army refinedArmy = refineArmy(adaptedCase.getArmy());
        adaptedCase.setArmy(refinedArmy);
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
                }
            }
        }
        //If units in query not adapted, find possible replacement candidates
        //and perform the adaption.
        Set<ArmyUnit> armyUnits = adaptedArmy.getArmyUnits();
        ArmyUnit mostSimilarUnit = null;
        for(ArmyUnit queryUnit : queryUnits){
            if(!armyUnits.contains(queryUnit)){
                mostSimilarUnit = cAF.findMostSimilarArmyUnit(queryUnit, armyUnits);
                mostSimilarUnit.setUnit(queryUnit.getUnit());
                mostSimilarUnit.setNumberOfUnits(queryUnit.getNumberOfUnits());
                mostSimilarUnit.setEquipment(queryUnit.getEquipment());
                mostSimilarUnit.setUtility(queryUnit.getUtility());
            }
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
            switch(message){
                case TOO_FEW_CORE_POINTS:
                    units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                        army.getPlayerRace(), armyType.Core);
                    addUnitGroup(army, units);
                    break;
                case TOO_FEW_POINTS_TOTAL:
                    increasePointUsage(army, messages, rule);
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
                case TOO_MANY_HERO_POINTS:
                    reduceCharacterPoints(army, armyType.Hero, rule);
                    break;
                case TOO_MANY_LORD_POINTS:
                    reduceCharacterPoints(army, armyType.Lord, rule);
                    break;
                case TOO_MANY_POINTS_TOTAL:
                    reducePointsTotal(army, messages, rule);
                    break;
                case TOO_MANY_RARE_POINTS:
                    reduceSpecialOrRareGroupPoints(army, armyType.Rare, 
                            messages, message, rule);
                    break;
                case TOO_MANY_SPECIAL_POINTS:
                    reduceSpecialOrRareGroupPoints(army, armyType.Special,
                            messages, message, rule);
                    break;
                case TOO_MANY_UNITS_IN_GROUP:
                    causes = rule.getCauses();
                    reduceNumberOfUnitsInGroup(army, causes);
                    break;
                case NO_ARMY_GENERAL:
                    addGeneral(army);
                    break;
                case TOO_FEW_GROUPS:
                    increasePointUsage(army, messages, rule);
                    break;
                case WRONG_RACE:
                    ExchangeRace exRace = new ExchangeRace();
                    army = exRace.adaptRace(army);
                    break;
                //If an unknown or untreatable error message is encountered.
                default:
                    return army;
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
     */
    private void addUnitGroup(Army army, ArrayList<Unit> availableUnits){
        Set<ArmyUnit> armyUnits = army.getArmyUnits();
        //Find the unit which is least similar to the core units already in the
        //army, this is probably a unit you could have need of.
        Unit leastSimilarUnit = cAF.findLeastSimilarUnit(armyUnits, availableUnits, true);
        if(leastSimilarUnit==null)
            leastSimilarUnit = cAF.findLeastSimilarUnit(armyUnits, availableUnits, false);
        ArmyUnit newArmyUnit = cAF.createNewUnit(army, leastSimilarUnit);
        armyUnits.add(newArmyUnit);
        System.out.println("Adding new: "+leastSimilarUnit.getArmyType()+
                " group: "+leastSimilarUnit.getName()+", gave full command: "+
                newArmyUnit.haveFullCommand());
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

    //TODO: START HERE AND CONTINUE DOWN THE CLASS FILE TO UPDATE IT WITH NEW IMPORVED FUNCTIONS AND THE COMMONADAPTATIONFUNCTIONS CLASS
    private void reduceCharacterPoints(Army army, armyType aT, RuleSet rs) {
        int diff=0;
        if(aT==armyType.Lord)
            diff = rs.getLordPointDifference();
        if(aT==armyType.Hero)
            diff = rs.getHeroPointDifference();
        //If the difference in used points are more than 100, remove the cheapest
        //character
        if(Math.abs(diff)>100){
            removeOneCharacter(army, aT);
            return;
        }
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
            if(eCost<uCost){
                mostExpensiveUnit.getUtility().remove(ut);
                System.out.println("Removing cheapest util from: "+
                    mostExpensiveUnit.getUnit().getName());
            }
            else {
                boolean s = mostExpensiveUnit.getEquipment().remove(eq);
                System.out.println("Removing cheapest eq ("+eq.getName()+") from: "+
                    mostExpensiveUnit.getUnit().getName()+", success:"+s);
            }            
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
        if(removeUnit!=null){
            System.out.println("Removing: "+removeUnit.getUnit().getName());
            army.getArmyUnits().remove(removeUnit);
        }
    }

    private void reduceSpecialOrRareGroupPoints(Army army, armyType aT,
            Messages[] messages, Messages message, RuleSet rs){
        Messages special = Messages.OK;
        Messages rare = Messages.OK;
        Messages duplicateR = Messages.OK;
        Messages duplicateS = Messages.OK;
        Causes[] causes = rs.getCauses();
        //Check if there is another reason for the special/rare groups to have
        //too many poinst
        for (Messages msg : messages) {
            switch(msg){
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
        int diff=0;
        if(message==Messages.TOO_MANY_RARE_POINTS)
            diff = rs.getRarePointDifference();
        if(message==Messages.TOO_MANY_SPECIAL_POINTS)
            diff = rs.getSpecialPointDifference();
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==aT){
                //If the unit have full command, and the removal of full
                //command is sufficient (but not to large a point reduction)
                //remove the full command from the unit.
                if(armyUnit.haveFullCommand()){
                    int fcc = armyUnit.getFullCommandCost();
                    if(fcc==Math.abs(diff)||
                            ((fcc>Math.abs(diff))&&(fcc<Math.abs(diff)+10))){
                        armyUnit.removeFullCommand();
                        System.out.println("Removing full command from: "+
                                unit.getName());
                        return;
                    }
                }
                else{
                    int numU = armyUnit.getNumberOfUnits();
                    int oldU = numU;
                    int minU = unit.getMinNumber();
                    //Check if the reduction in group unit is sufficient to
                    //remove the error
                    boolean found = false;
                    while(numU>minU){
                        numU--;
                        armyUnit.setNumberOfUnits(numU);
                        int totalCost = army.calculateTotalUnitCost(armyUnit);
                        if(totalCost<Math.abs(diff))
                            found = true;
                    }
                    if(found){
                        System.out.println("Reduced number of units in "+
                                unit.getName()+" group with: "+(oldU-numU));
                        return;
                    }
                    else
                        armyUnit.setNumberOfUnits(oldU);
                }
            }
        }
        //If nothing of the above works, find the unit with the
        //lowest cost and remove it completly.
        removeGroup(army, aT);
    }

    /**
     * Method which reduces the number of units in a group if the unit group
     * have more units than is legal. The unit group size is set to the
     * max number of allowed units.
     * @param army The Army which may contain unit group that may have to many units
     * @param causes The array of causes that may contain a cause which tells
     * that the unit group have to many units.
     */
    private void reduceNumberOfUnitsInGroup(Army army, Causes[] causes) {
        for (Causes cause : causes) {
            Messages msg = cause.getCause();
            if(msg==Messages.TOO_MANY_UNITS_IN_GROUP){
                for (ArmyUnit armyUnit : army.getArmyUnits()) {
                    Unit unit = armyUnit.getUnit();
                    if(unit.getName().contentEquals(cause.getUnit().getName())){
                        int maxN = unit.getMaxNumber();
                        int numU = armyUnit.getNumberOfUnits();
                        if(numU>maxN&&maxN!=0){
                            armyUnit.setNumberOfUnits(maxN);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void reducePointsTotal(Army army, Messages[] messages, RuleSet rs) {
        //Check if either of the army type groups have too many points
        //allocated to them.
        for (Messages message : messages) {
            switch(message){
                case TOO_MANY_HERO_POINTS:
                    reduceCharacterPoints(army, armyType.Hero, rs);
                    System.out.println("too many hero");
                    return;
                case TOO_MANY_LORD_POINTS:
                    reduceCharacterPoints(army, armyType.Lord, rs);
                    System.out.println("too many lord");
                    return;
                case TOO_MANY_RARE_POINTS:
                    reduceSpecialOrRareGroupPoints(army, armyType.Rare,
                            messages, message, rs);
                    System.out.println("reduce rare");
                    return;
                case TOO_MANY_SPECIAL_POINTS:
                    reduceSpecialOrRareGroupPoints(army, armyType.Special,
                            messages, message, rs);
                    System.out.println("reduce special");
                    return;
            }
        }
        int totalDiff = Math.abs(rs.getTotalPointDifference());

        if(totalDiff<=20){
            armyType aT = armyType.Core;
            for(ArmyUnit armyUnit : army.getArmyUnits()){
                Unit unit = armyUnit.getUnit();
                if(unit.getArmyType()==aT){
                    int numUnits = armyUnit.getNumberOfUnits();
                    if(numUnits>unit.getMinNumber()){
                        armyUnit.setNumberOfUnits(numUnits-1);
                        rs.isFollowingArmyDispositionRules(army);
                        if(rs.getTotalPointDifference()>0)
                            return;
                    }
                }
            }
        }
        if(totalDiff>20&&totalDiff<300){
            java.util.Random random = new java.util.Random();
            int r = random.nextInt(3);
            if(r==0)
                removeGroup(army, armyType.Core);
            if(r==1)
                removeGroup(army, armyType.Special);
            if(r==2)
                removeGroup(army, armyType.Rare);
        }
        if(totalDiff>=300){
            java.util.Random random = new java.util.Random();
            int r = random.nextInt(2);
            if(r==0)
                removeOneCharacter(army, armyType.Hero);
            if(r==1)
                removeOneCharacter(army, armyType.Lord);
        }
    }

    private void addGeneral(Army army) {
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Lord);
        java.util.Random random = new java.util.Random();
        int generalIndex = random.nextInt(units.size());
        Unit general = (Unit) CollectionControl.getItemAt(units, generalIndex);
        ArmyUnit newArmyUnit = cAF.createNewCharacter(army, general);
        army.getArmyUnits().add(newArmyUnit);
    }

    private void increasePointUsage(Army army, Messages[] messages, RuleSet rule) {
        ArrayList<Unit> units;
        //Check if any messages that influence the too few total points message
        //is present and address those first.
        for (Messages message : messages) {
            switch(message){
                case TOO_FEW_CORE_POINTS:
                    units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                        army.getPlayerRace(), armyType.Core);
                    addUnitGroup(army, units);
                    return;
                case TOO_FEW_GROUPS:
                    addRandomGroup(army, rule);
                    return;
                case TOO_FEW_UNITS_IN_GROUP:
                    Causes[] causes = rule.getCauses(message);
                    increaseGroupUnits(army,causes);
                    return;
                case NO_ARMY_GENERAL:
                    addGeneral(army);
                    return;
            }
        }
        int totalPointDifference = Math.abs(rule.getTotalPointDifference());
        //if the difference in used points is less than 150 point, increase
        //the unit sizes where applicable (give full command) instead of
        //generating a new unit group/formation
        if(totalPointDifference<150){
            if(cAF.applyFullCommand(army)){
                for(ArmyUnit armyUnit : army.getArmyUnits()){
                    Unit unit = armyUnit.getUnit();
                    if(unit.isEligibleForFullCommand()&&!armyUnit.haveFullCommand())
                        armyUnit.giveUnitFullCommand();
                }
                return;
            }
            while(true){
                java.util.Random random = new java.util.Random();
                int next = random.nextInt(army.getArmyUnits().size());
                ArmyUnit armyUnit = (ArmyUnit) CollectionControl.getItemAt(
                        army.getArmyUnits(), next);
                Unit unit = armyUnit.getUnit();
                armyType uAT = unit.getArmyType();
                if(uAT!=armyType.Hero||uAT!=armyType.Lord){
                    int numUnits = armyUnit.getNumberOfUnits();
                    if(numUnits<unit.getMaxNumber()||unit.getMaxNumber()==0){
                        armyUnit.setNumberOfUnits(numUnits+5);
                        break;
                    }
                }
            }
        }
        else{
            addRandomGroup(army, rule);
        }
    }
    private void addRandomGroup(Army army, RuleSet rule){
        ArrayList<Unit> units;
        java.util.Random random = new java.util.Random();
        int newFormation = random.nextInt(5);
        int rareDifference = rule.getRarePointDifference();
        int specialDifference = rule.getSpecialPointDifference();
        int heroDifference = rule.getHeroPointDifference();
        int lordDifference = rule.getLordPointDifference();
        armyType aT=armyType.Core;
        if(newFormation==0&&lordDifference>199){
            addGeneral(army);
            return;
        }
        else if(newFormation==1&&heroDifference>149){
            addGeneral(army);
            return;
        }
        else if(newFormation==2)
            aT=armyType.Core;
        else if(newFormation==3&&specialDifference>99)
            aT=armyType.Special;
        else if(newFormation==4&&rareDifference>99)
            aT=armyType.Rare;
        units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                    army.getPlayerRace(), aT);
        addUnitGroup(army, units);
    }
}
