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

package myrmidia.CBR;

import java.util.ArrayList;
import java.util.Set;
import jcolibri.cbrcore.CBRQuery;
import myrmidia.CBR.Resources.CommonAdaptionFunctions;
import myrmidia.CBR.Resources.ExchangeRace;
import myrmidia.CBR.Resources.UnitSimilarity;
import myrmidia.Util.CollectionControl;
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Warhammer.*;
import myrmidia.Warhammer.Resources.Causes;
import myrmidia.Util.Enums.*;

/**
 * Class to perform the case adaption
 * @author Glenn Rune Strandbråten
 * @version 0.5
 */
public class AdaptionEngine {
    private CommonAdaptionFunctions cAF;
    private int lastDifference = 0;

    /**
     * Default constructor
     */
    public AdaptionEngine(){
        cAF = new CommonAdaptionFunctions(new UnitSimilarity());
    }

    /**
     * Construct which sets the UnitSimilarity object to be used
     * @param unitSimilarity The unit similarity object to be used
     */
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
        cAF.resetAll();
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
        adaptedCase.setOutcome(Outcomes.Unknown);
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
                        army.getPlayerRace(), ArmyType.Core);
                    System.err.println(message);
                    addUnitGroup(army, units);
                    break;
                case TOO_FEW_POINTS_TOTAL:
                    System.err.println(message);
                    increasePointUsage(army, messages, rule);
                    break;
                case TOO_FEW_UNITS_IN_GROUP:
                    causes = rule.getCauses(message);
                    System.err.println(message);
                    increaseGroupUnits(army,causes);
                    break;
                case TOO_MANY_DUPLIACTE_SPECIAL_UNITS:
                    causes = rule.getCauses(message);
                    System.err.println(message);
                    decreaseDuplicates(army,causes);
                    break;
                case TOO_MANY_DUPLICATE_RARE_UNITS:
                    causes = rule.getCauses(message);
                    System.err.println(message);
                    decreaseDuplicates(army,causes);
                    break;
                case TOO_MANY_HERO_POINTS:
                    System.err.println(message);
                    reduceCharacterPoints(army, ArmyType.Hero, rule);
                    break;
                case TOO_MANY_LORD_POINTS:
                    System.err.println(message);
                    reduceCharacterPoints(army, ArmyType.Lord, rule);
                    break;
                case TOO_MANY_POINTS_TOTAL:
                    System.err.println(message);
                    reducePointsTotal(army, messages, rule);
                    break;
                case TOO_MANY_RARE_POINTS:
                    System.err.println(message);
                    reduceSpecialOrRareGroupPoints(army, ArmyType.Rare,
                            messages, message, rule);
                    break;
                case TOO_MANY_SPECIAL_POINTS:
                    System.err.println(message);
                    reduceSpecialOrRareGroupPoints(army, ArmyType.Special,
                            messages, message, rule);
                    break;
                case TOO_MANY_UNITS_IN_GROUP:
                    causes = rule.getCauses();
                    System.err.println(message);
                    reduceNumberOfUnitsInGroup(army, causes);
                    break;
                case NO_ARMY_GENERAL:
                    System.err.println(message);
                    addGeneral(army);
                    break;
                case TOO_FEW_GROUPS:
                    System.err.println(message);
                    increasePointUsage(army, messages, rule);
                    break;
                case WRONG_RACE:
                    ExchangeRace exRace = new ExchangeRace();
                    System.err.println(message);
                    army = exRace.adaptRace(army);
                    break;
                //If an unknown or untreatable error message is encountered.
                default:
                    return army;
            }
            messages = rule.isFollowingArmyDispositionRules(army);
        }
        System.err.println(messages[0]);
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
        if(leastSimilarUnit==null){
            leastSimilarUnit = cAF.findLeastSimilarUnit(armyUnits, availableUnits, false);
        }

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

    /**
     * Method which removes one character unit from the army. The unit with the
     * lowest total cost is removed.
     * @param army Army The army to remove the character from
     * @param aT ArmyType The army type of the character to be removed (Lord/Hero)
     */
    private void removeOneCharacter(Army army, ArmyType aT) {
        ArmyUnit removeUnit=null;
        int removeUnitCost=0;
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==aT){
                int calculatedCost = army.calculateTotalUnitCost(armyUnit);
                if(removeUnitCost==0)
                    removeUnit = armyUnit;
                if(calculatedCost<removeUnitCost){
                    removeUnit = armyUnit;
                    removeUnitCost = calculatedCost;
                }
            }
        }
        if(removeUnit!=null){
            System.out.println("removing "+aT+": "+removeUnit.getUnit().getName());
            army.getArmyUnits().remove(removeUnit);
        }
    }

    /**
     * Method used to reduce the number of points characters spend.
     * @param army Army The army to reduce the character expenses in
     * @param aT ArmyType The army type of the character unit that is costing to much.
     * @param rs RuleSet The rule set used to calculate the over spending
     */
    private void reduceCharacterPoints(Army army, ArmyType aT, RuleSet rs) {
        int diff=0;
        if(aT==ArmyType.Lord)
            diff = Math.abs(rs.getLordPointDifference());
        if(aT==ArmyType.Hero)
            diff = Math.abs(rs.getHeroPointDifference());
        //If the difference in used points are more than 100, remove the cheapest
        //character
        System.err.println("Hero/Lord difference: "+diff);
        if(diff>50){
            removeOneCharacter(army, aT);
            return;
        }
        //Find the most expensive lord/hero unit.
        ArmyUnit mostExpensiveUnit = cAF.findMostExpensiveUnit(army, aT, true);
        if(mostExpensiveUnit==null)
            mostExpensiveUnit = cAF.findMostExpensiveUnit(army, aT, false);
        //Find the equipment/utility cost which by itself bring the difference
        //the closest to zero
        Set<Equipment> equipment = mostExpensiveUnit.getEquipment();
        Set<UtilityUnit> utility = mostExpensiveUnit.getUtility();
        int eqDiff = 10000;
        int utDiff = 10000;
        Equipment closestEq = null;
        UtilityUnit closestUt = null;
        for (Equipment eq : equipment) {
            int cost = eq.getCost();
            int zeroDiff = diff-cost;
            if(zeroDiff<eqDiff){
                eqDiff = zeroDiff;
                closestEq = eq;
            }
        }
        for (UtilityUnit ut : utility) {
            int cost = ut.getCost();
            int zeroDiff = diff-cost;
            if(zeroDiff<utDiff){
                utDiff = zeroDiff;
                closestUt = ut;
            }
        }
        if(lastDifference==diff){
            removeOneCharacter(army, aT);
        }
        lastDifference = diff;
        if(closestEq==null&&closestUt==null)
            return;
        if(eqDiff<utDiff){
            equipment.remove(closestEq);
        }
        else{
            utility.remove(closestUt);
        }
    }

    /**
     * This method removed one formation/group from the army with the indicated
     * army type. The least expensive unit formation is removed.
     * @param army Army The army wo remove the group form
     * @param aT ArmyType The army type of the group to be removed
     */
    private void removeGroup(Army army, ArmyType aT) {
        ArmyUnit leastExpensiveUnit = cAF.findLeastExpensiveUnit(army, aT);
        if(leastExpensiveUnit!=null){
            System.out.println("Removing: "+leastExpensiveUnit.getUnit().getName());
            army.getArmyUnits().remove(leastExpensiveUnit);
        }
    }

    /**
     * Method used to reduce the special or rare group points used
     * @param army Army The army to reduce the points in
     * @param aT ArmyType The army type of the (Special/Rare) group to be reduced
     * @param messages Array with the messages generated by the rule set
     * @param message Message The message that caused the rule to be executed
     * @param rs RuleSet The rule set generating the error message
     */
    private void reduceSpecialOrRareGroupPoints(Army army, ArmyType aT,
            Messages[] messages, Messages message, RuleSet rs){
        //Check if there is another reason for the special/rare groups to have
        //too many poinst
        for (Messages msg : messages) {
            switch(msg){
                case TOO_MANY_DUPLIACTE_SPECIAL_UNITS:
                    decreaseDuplicates(army,rs.getCauses());
                    return;
                case TOO_MANY_DUPLICATE_RARE_UNITS:
                    decreaseDuplicates(army,rs.getCauses());
                    return;
            }
        }
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
                        if(totalCost<Math.abs(diff)){
                            found = true;
                            break;
                        }
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

    /**
     * Method which tries to reduce the overall number of points in the army
     * to go below the limit.
     * @param army Army The army to reduce the points in
     * @param messages Array with the messages generated by the ruleset
     * @param rs RuleSet The rule set generating the error messages
     */
    private void reducePointsTotal(Army army, Messages[] messages, RuleSet rs) {
        //Check if either of the army type groups have too many points
        //allocated to them.
        for (Messages message : messages) {
            switch(message){
                case TOO_MANY_HERO_POINTS:
                    reduceCharacterPoints(army, ArmyType.Hero, rs);
                    System.out.println("too many hero");
                    return;
                case TOO_MANY_LORD_POINTS:
                    reduceCharacterPoints(army, ArmyType.Lord, rs);
                    System.out.println("too many lord");
                    return;
                case TOO_MANY_RARE_POINTS:
                    reduceSpecialOrRareGroupPoints(army, ArmyType.Rare,
                            messages, message, rs);
                    System.out.println("reduce rare");
                    return;
                case TOO_MANY_SPECIAL_POINTS:
                    reduceSpecialOrRareGroupPoints(army, ArmyType.Special,
                            messages, message, rs);
                    System.out.println("reduce special");
                    return;
            }
        }
        int totalDiff = Math.abs(rs.getTotalPointDifference());

        if(totalDiff<=20){
            ArmyType aT = ArmyType.Core;
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
        int numberOfCharacters = cAF.getNumberOfCharacters(army);
        if(totalDiff>20&&totalDiff<300||numberOfCharacters<=1){
            java.util.Random random = new java.util.Random();
            int r = random.nextInt(3);
            if(r==0)
                removeGroup(army, ArmyType.Core);
            if(r==1)
                removeGroup(army, ArmyType.Special);
            if(r==2)
                removeGroup(army, ArmyType.Rare);
        }
        if(totalDiff>=300){
            java.util.Random random = new java.util.Random();
            int r = random.nextInt(2);
            if(r==0)
                removeOneCharacter(army, ArmyType.Hero);
            if(r==1)
                removeOneCharacter(army, ArmyType.Lord);
        }
    }

    /**
     * Method which adds a general unit (Lord) to the army
     * @param army Army The army to have a general unit added to it
     */
    private void addGeneral(Army army) {
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), ArmyType.Lord);
        java.util.Random random = new java.util.Random();
        int generalIndex = random.nextInt(units.size());
        Unit general = (Unit) CollectionControl.getItemAt(units, generalIndex);
        ArmyUnit newArmyUnit = cAF.createNewCharacter(army, general);
        army.getArmyUnits().add(newArmyUnit);
    }

    /**
     * Method which tries to increase the point usage in the army
     * @param army Army The army to increase the points in
     * @param messages Array with the messages generate by the ruleset
     * @param rule RuleSet The rule set generating the messages
     */
    private void increasePointUsage(Army army, Messages[] messages, RuleSet rule) {
        ArrayList<Unit> units;
        //Check if any messages that influence the too few total points message
        //is present and address those first.
        for (Messages message : messages) {
            switch(message){
                case TOO_FEW_CORE_POINTS:
                    units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                        army.getPlayerRace(), ArmyType.Core);
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
            if(cAF.applyFullCommand(army,null)){
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
                ArmyType uAT = unit.getArmyType();
                if(uAT!=ArmyType.Hero||uAT!=ArmyType.Lord){
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

    /**
     * Method which is used to add a random group to the army
     * @param army Army The army to add the new group into
     * @param rule RuleSet The rule set used to verify the legality of the army
     */
    private void addRandomGroup(Army army, RuleSet rule){
        ArrayList<Unit> units;
        java.util.Random random = new java.util.Random();
        int newFormation = random.nextInt(5);
        int rareDifference = rule.getRarePointDifference();
        int specialDifference = rule.getSpecialPointDifference();
        int heroDifference = rule.getHeroPointDifference();
        int lordDifference = rule.getLordPointDifference();
        ArmyType aT=ArmyType.Core;
        if(newFormation==0&&lordDifference>199){
            addGeneral(army);
            return;
        }
        else if(newFormation==1&&heroDifference>149){
            addGeneral(army);
            return;
        }
        else if(newFormation==2)
            aT=ArmyType.Core;
        else if(newFormation==3&&specialDifference>99)
            aT=ArmyType.Special;
        else if(newFormation==4&&rareDifference>99)
            aT=ArmyType.Rare;
        units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                    army.getPlayerRace(), aT);
        addUnitGroup(army, units);
    }
}