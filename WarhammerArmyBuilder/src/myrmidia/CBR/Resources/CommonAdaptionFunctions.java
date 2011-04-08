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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.Unit;
import java.util.Random;
import myrmidia.Util.CollectionControl;
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Util.Enums.ArmyType;
import myrmidia.Util.Enums.ItemType;
import myrmidia.Util.Enums.Races;
import myrmidia.Warhammer.Army;
import myrmidia.Warhammer.UtilityUnit;

/**
 * Class which contains many of the most common functions used while adapting
 * an army in addition to logic to help in the execution of those functions.
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class CommonAdaptionFunctions {
    private Set<Equipment> usedMagicalEquipment;
    private Set<Unit> usedUnits;
    private Set<Unit> reducedUnits;
    private UnitSimilarity unitSimilarity;
    private Random random;
    private int errorThreshold = 5;
    private int equipmentThreshold = 3;
    private int magicPointThreshold = 10;
    private double fullCommandPercentage = 0.8;
    private int minNumberMultiplier = 3;

    /**
     * Default constructor, initializes a new unit similarity object where all
     * weigths are set to 1.
     */
    public CommonAdaptionFunctions(){
        usedMagicalEquipment = new HashSet<Equipment>();
        usedUnits = new HashSet<Unit>();
        reducedUnits = new HashSet<Unit>();
        unitSimilarity = new UnitSimilarity();
        random = new Random();
    }

    /**
     * Constructor which references the allready preconfigured UnitSimilarity
     * object.
     * @param unitSimilarity  The preconfigured UnitSimilarity object
     */
    public CommonAdaptionFunctions(UnitSimilarity unitSimilarity){
        usedMagicalEquipment = new HashSet<Equipment>();
        usedUnits = new HashSet<Unit>();
        reducedUnits = new HashSet<Unit>();
        this.unitSimilarity = unitSimilarity;
        random = new Random();
    }

    /**
     * Method which clears the list of used magical equipment
     */
    public void resetUsedMagicalEquipment(){
        usedMagicalEquipment.clear();
    }

    /**
     * Method which clears the list of units which have been added to the army
     */
    public void resetUsedUnits(){
        usedUnits.clear();
    }
    /**
     * Method which clears the list of units which have been altered
     */
    public void resetReducedUnits(){
        reducedUnits.clear();
    }
    /**
     * Method which clears all the list of used objects
     */
    public void resetAll(){
        resetReducedUnits();
        resetUsedMagicalEquipment();
        resetUsedUnits();
    }

    /**
     * Method which finds the most similar unit for the supplied unit from
     * the list of available units. The list should be a subset of units
     * with only units of the same army type to narrow the search, this is
     * however not a requirement
     * @param oldUnit The Unit to find the most similar unit to
     * @param availableUnits The collection of available units
     * @return The most similar unit
     */
    public Unit findMostSimilarUnit(Unit oldUnit,
            ArrayList<Unit> availableUnits){
        Unit returnUnit = null;
        double mostSimilar = 0;
        for(Unit newUnit : availableUnits){
            try {
                double simil = unitSimilarity.compute(oldUnit, newUnit);
                if(simil>mostSimilar||mostSimilar==0){
                    mostSimilar = simil;
                    returnUnit = newUnit;
                }
            }
            catch (NoApplicableSimilarityFunctionException ex) {}
        }
        return returnUnit;
    }

    public Unit findLeastSimilarUnit(Set<ArmyUnit> existingUnits,
            ArrayList<Unit> availableUnits, boolean restrictUsedUnits){
        Unit returnUnit = null;
        double leastSimilar = 0;
        for(ArmyUnit armyUnit : existingUnits){
            Unit existingUnit = armyUnit.getUnit();
            for(Unit availableUnit : availableUnits){
                try {
                    if((restrictUsedUnits)&&
                            (unitIsInSet(usedUnits,availableUnit)))
                            continue;
                    double simil = unitSimilarity.compute(existingUnit,
                            availableUnit);
                    if(simil<leastSimilar||leastSimilar==0){
                        leastSimilar = simil;
                        returnUnit = availableUnit;
                    }
                }
                catch (NoApplicableSimilarityFunctionException ex) {}
            }
        }
        if(returnUnit!=null)
            usedUnits.add(returnUnit);
        return returnUnit;
    }

    /**
     * Method which finds the most similar army unit for the supplied unit from
     * the list of available units. 
     * @param oldUnit The Unit to find the most similar unit to
     * @param availableUnits The collection of available units
     * @return The most similar unit
     */
    public ArmyUnit findMostSimilarArmyUnit(ArmyUnit oldUnit,
            Set<ArmyUnit> availableUnits){
        ArmyUnit returnUnit = null;
        double mostSimilar = 0;
        for(ArmyUnit newArmyUnit : availableUnits){
            try {
                double simil = unitSimilarity.compute(oldUnit.getUnit(),
                        newArmyUnit.getUnit());
                if(simil>mostSimilar||mostSimilar==0){
                    mostSimilar = simil;
                    returnUnit = newArmyUnit;
                }
            }
            catch (NoApplicableSimilarityFunctionException ex) {}
        }
        return returnUnit;
    }

    /**
     * This method finds the most expensive army unit in the supplied army type
     * @param army The army to search through
     * @param aT The army type to find the most expensive unit in
     * @return The most expensive army unit
     */
    public ArmyUnit findMostExpensiveUnit(Army army, ArmyType aT,
            boolean restrictUsedUnits){
        ArmyUnit _return = null;
        int mostExpensive = 0;
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==aT){
                if((restrictUsedUnits)&&
                        (unitIsInSet(reducedUnits, unit)))
                        continue;
                int cost = army.calculateTotalUnitCost(armyUnit);
                if(cost>mostExpensive||mostExpensive==0){
                    mostExpensive = cost;
                    _return = armyUnit;
                    
                }
            }
        }
        if(_return!=null)
            reducedUnits.add(_return.getUnit());
        return _return;
    }

    /**
     * This method finds the least expensive army unit in the supplied army type
     * @param army The army to search through
     * @param aT The army type to find the least expensive unit in
     * @return The least expensive army unit
     */
    public ArmyUnit findLeastExpensiveUnit(Army army, ArmyType aT){
        ArmyUnit _return = null;
        int leastExpensive = -1;
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==aT){
                int cost = army.calculateTotalUnitCost(armyUnit);
                if(cost<leastExpensive||leastExpensive==-1){
                    leastExpensive = cost;
                    _return = armyUnit;
                }
            }
        }
        return _return;
    }

    /**
     * This method is used to randomly select if an utility unit should be
     * assigned to a character unit (lord/hero) and which random utility unit
     * should be added. Although character units are the intended units for
     * this function it is not a requirement.
     * If no utility unit can be assigned to this unit the method will exit
     * @param armyUnit The army unit to assign a utility unit to
     */
    public void assignUtilityUnit(ArmyUnit armyUnit){
        Set<UtilityUnit> selectedUnit = armyUnit.getUtility();
        Set<UtilityUnit> availableUnits = armyUnit.getUnit().getUtilityUnit();
        if(availableUnits.isEmpty())
            return;
        int giveUtilityUnit = random.nextInt(2);
        if(giveUtilityUnit==0)
            return;
        int nextUtilityUnit = random.nextInt(availableUnits.size());
        UtilityUnit nextUtility = (UtilityUnit) CollectionControl.getItemAt(
                availableUnits, nextUtilityUnit);
        selectedUnit.add(nextUtility);
        System.out.println("Added utility: "+nextUtility.getName()+
                ", to: "+armyUnit.getUnit().getName());
    }

    /**
     * This method is used to randomly select if an item should be
     * assigned to a character unit (lord/hero); randomly select the number of
     * items to purchase and randomly which items to purchase. This only
     * purchases items a unit can choose to spend points on (no magic points
     * or magic items are used/added in this function).
     *
     * This method is designed primarily for use in conjunction with character
     * units, but reqular units will also work (it will add e.g.: shields to
     * the unit if applicable)
     * If no utility unit can be assigned to this unit the method will exit
     * @param armyUnit The army unit to assign equipment/items to
     */
    public void assignReqularEquipment(ArmyUnit armyUnit){
        Set<Equipment> selectedEquipment = armyUnit.getEquipment();
        Set<Equipment> availableEquipment = armyUnit.getUnit().getEquipment();
        int errorCount = 0;
        if(availableEquipment.isEmpty())
            return;
        int giveEquipment = random.nextInt(2);
        if(giveEquipment==0)
            return;
        int threshold = (availableEquipment.size()/this.equipmentThreshold)+1;
        int numEquipment = random.nextInt(threshold)+1;
        for(int i = 0; i<numEquipment; i++){
            int next = random.nextInt(availableEquipment.size());
            Equipment nextEq = (Equipment) CollectionControl.getItemAt(
                    availableEquipment, next);
            if(errorCount>errorThreshold)
                return;
            if(equipmentIsInSet(selectedEquipment, nextEq)){
                errorCount++;
                i--;
                continue;
            }
            selectedEquipment.add(nextEq);
            System.out.println("Added equipment: "+nextEq.getName()+
                    ", to: "+armyUnit.getUnit().getName());
        }      
    }

    /**
     * This function is used to add magical items to character units. The
     * function randomly chooses if any should be purchased, how many and
     * which items to add if all requirements are met. (not have similar items,
     * not using to much points to buy magic items, the item is not used
     * previsouly by this or other characters in this army)
     * @param unit The army unit to add magic items to
     * @param magicEquipment The set with the applicable magical items
     */
    public void assignMagicEquipment(ArmyUnit unit,
            Set<Equipment> magicEquipment){
        if(magicEquipment.isEmpty())
            return;
        Set<Equipment> selectedEquipment = unit.getEquipment();
        int magicPoints = unit.getUnit().getMagicPoints();
        int usedPoints = 0;
        int errorCount = 0;
        int magicEquipmentSize = magicEquipment.size();
        for(int i=0; i<equipmentThreshold; i++){
            int next = random.nextInt(magicEquipmentSize);
            Equipment nextEq = (Equipment) CollectionControl.getItemAt(
                    magicEquipment, next);            
            if(errorCount>errorThreshold)
                break;
            //If the selected equipment is used by this unit before, used
            //by another unit or if you have similar equipment (e.g.: trying to
            //add armour when you allready have armour, try again.
            if(equipmentIsInSet(selectedEquipment, nextEq)||
                    equipmentIsInSet(usedMagicalEquipment, nextEq)||
                    haveSimilarEquipment(selectedEquipment, nextEq)){
                i--;
                errorCount++;
                continue;
            }
            //If the selected item is a battle standard and this unit cannot
            //be a battle standard bearer, try again.
            if(nextEq.getItemType()==ItemType.Standard&&
                    !unit.getUnit().canBeBattleStandardBearer()){
                i--;
                continue;
            }
            //If the cost of the new item will exceede the maximum available
            //points in which to purchase items, try again.
            if((usedPoints+nextEq.getCost())>magicPoints){
                i--;
                continue;
            }
            usedPoints += nextEq.getCost();
            selectedEquipment.add(nextEq);
            System.out.println("Added equipment: "+nextEq.getName()+
                    ", to: "+unit.getUnit().getName());
            if(usedPoints>=(magicPoints-magicPointThreshold))
                break;
        }
    }

    /**
     * Method to check if a similar item is already present in the unit.
     * used to remove the possibility of having e.g.: two armours.
     * @param unitEquipment The collection of the equipment present in the unit
     * @param newEquipment The equipment the unit want to purchase.
     * @return <ul><li>true - if the equipment can be purchased</li>
     * <li>false - if the equipment cannot be purchased.</li></ul>
     */
    public static boolean haveSimilarEquipment(Set<Equipment> unitEquipment,
            Equipment newEquipment){
        for (Equipment equipment : unitEquipment) {
            if(equipment.getItemType()==newEquipment.getItemType())
                return true;
            if(equipment.getItemType()==ItemType.Armour&&
                    newEquipment.getItemType()==ItemType.Magic_Armour)
                return true;
            if(equipment.getItemType()==ItemType.Magic_Armour&&
                    newEquipment.getItemType()==ItemType.Armour)
                return true;
        }
        return false;
    }

    /**
     * This method replaces the Collection.contains(Object o) which during
     * experiments functions as expected if you try to add obj1 to the list
     * twice, but not neccessaily if obj1==obj2 and you try to add both object
     * to the Collection.
     *
     * Method to check if the exact same item is already present in the
     * collection. The
     * function is used to check if a unit have the same equipment twice,
     * or if the magical item is allready acquired by another unit in the
     * army.     *
     * @param set The collection to check for the existence of the item
     * @param equipment The equipment the unit want to purchase.
     * @return <ul><li>true - if the equipment can be purchased</li>
     * <li>false - if the equipment cannot be purchased.</li></ul>
     */
    public static boolean equipmentIsInSet(Set<Equipment> set,
            Equipment equipment){
        for (Equipment eq : set) {
            if(eq.compareTo(equipment))
                return true;
        }
        return false;
    }

    /**
     * This method replaces the Collection.contains(Object o) which during
     * experiments functions as expected if you try to add obj1 to the list
     * twice, but not neccessaily if obj1==obj2 and you try to add both object
     * to the Collection.
     *
     * Method to check if the exact same unit is already present in the
     * collection.
     * @param set The collection to check for the existence of the unit
     * @param unit The unit to check if is in the list
     * @return <ul><li>true - if the unit is in the Collection</li>
     * <li>false - if the unit not is in the Collection</li></ul>
     */
    private static boolean unitIsInSet(Set<Unit> set, Unit unit) {
        for(Unit un : set){
            if(un.compareTo(unit))
                return true;
        }
        return false;
    }

    /**
     * Method to create a new character unit (lord/hero) with
     * items/magical items/utility unit. (The army unit id is not corrected)
     * and must be changed at an appropriate time before commited to the casebase)
     * @param army The base army for the new character
     * @param newCharacter The character unit to create a new army unit of
     * @return The newly created army unit.
     */
    public ArmyUnit createNewCharacter(Army army, Unit newCharacter){
        ArmyUnit newArmyUnit = new ArmyUnit();
        newArmyUnit.setUnit(newCharacter);
        newArmyUnit.setNumberOfUnits(1);
        Set<Equipment> magicalEquipment;
        //Dwarfs cannon use magical items, only the magic runes they
        //can inscribe on their equipment. And should then only get
        //access to those runes.
        if(army.getPlayerRace()==Races.Dwarfs)
            magicalEquipment = CreateObjectFromDB.getRaceEquipment(
                    newCharacter.getRace(),
                    newCharacter.getMagicPoints());
        else
            magicalEquipment = CreateObjectFromDB.getAllEquipment(
                    newCharacter.getRace(),
                    newCharacter.getMagicPoints());
        assignReqularEquipment(newArmyUnit);
        assignMagicEquipment(newArmyUnit, magicalEquipment);
        assignUtilityUnit(newArmyUnit);
        return newArmyUnit;
    }

    /**
     *
     * @param army
     * @param newCharacter
     * @return
     */
    public ArmyUnit createNewUnit(Army army, Unit newCharacter){
        ArmyUnit newArmyUnit = new ArmyUnit();
        newArmyUnit.setUnit(newCharacter);
        int numUnits = newCharacter.getMinNumber()*minNumberMultiplier;
        int maxNumber = newCharacter.getMaxNumber();
        //if the number of untis are more than the max number of units
        //set number of units to max number of units.
        if((numUnits>maxNumber)&&(maxNumber!=0))
            numUnits = maxNumber;
        if((newCharacter.isEligibleForFullCommand())&&
                (applyFullCommand(army,newArmyUnit)))
            newArmyUnit.giveUnitFullCommand();
        assignReqularEquipment(newArmyUnit);
        assignUtilityUnit(newArmyUnit);
        newArmyUnit.setNumberOfUnits(numUnits);
        return newArmyUnit;
    }

    /**
     * This method determines if the full command threshold have been reached
     * @param army -The army to check the full command threshold
     * @return <ul><li>true - if a new full command group can be created</li>
     * <li>false - if a nee full command group cannot be created</li></ul>
     */
    public boolean applyFullCommand(Army army, ArmyUnit armyUnit){
        int eligibleFullCommandUnits = 0;
        int unitsWithFullCommand = 0;
        for (ArmyUnit au : army.getArmyUnits()) {
            if(au.getUnit().isEligibleForFullCommand())
                eligibleFullCommandUnits++;
            if(au.haveFullCommand())
                unitsWithFullCommand++;
        }
        if(armyUnit!=null){
            int diff = remainingPointsAfterNewUnitCreation(army, armyUnit);
            if(diff<0)
                return false;
        }
        double fraction = (double) unitsWithFullCommand/eligibleFullCommandUnits;
        if(fraction<fullCommandPercentage)
            return true;
        return false;
    }

    /**
     * Method to check how many points are left or if the number of alowed points
     * are exceeded when the new unit is added to the list. A copy of the army
     * is created where the new unit is added and the cost is then calculated.
     * @param army The army to add the new unit to
     * @param armyUnit The new army unit to be added to the existing army
     * @return <ul><li></li>Positive integer if less than the alloted points are
     * spent<li></li>Negative integer if more than the alloted points are spent
     * </ul>
     */
    public int remainingPointsAfterNewUnitCreation(Army army, ArmyUnit armyUnit){
        Army copy = Army.copy(army);
        copy.getArmyUnits().add(armyUnit);
        int cost = copy.calculateCost();
        return (copy.getArmyPoints()-cost);
    }

}