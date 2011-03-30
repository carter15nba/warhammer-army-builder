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

package org.Warhammer.CBR.Resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.Util.CollectionControl;
import org.Warhammer.Util.CreateObjectFromDB;
import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Equipment;
import org.Warhammer.Warhammer.Equipment.itemType;
import org.Warhammer.Warhammer.Unit;
import org.Warhammer.Warhammer.Unit.armyType;
import org.Warhammer.Warhammer.UtilityUnit;

/**
 * Class used to change all the units in an army to another race, by replacing
 * each unit in the old army with the most similar unit from the new race.
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class ExchangeRace {

    private UnitSimilarity unitSimil;
    private ArrayList<Unit> mostSimilarUnits;
    private ArrayList<Equipment> usedEquipment;

    /**
     * Default constructor
     * Set all unit simililarity weigths to 1.
     */
    public ExchangeRace(){
        unitSimil = new UnitSimilarity();
        mostSimilarUnits = new ArrayList<Unit>();
        usedEquipment = new ArrayList<Equipment>();
    }

    /**
     * Constructor wich sets the unit similarity weigths as requested.
     * @param charWeigth The weigth of the characteristics similarity
     * @param unitTypeWeigth The weigth of the unit type similarity
     * @param armyTypeWeigth The weigth of the army type similarity
     * @param costWeigth The weigth of the cost similarity
     * @param weaponWeigth The weigth of the weapon similarity
     */
    public ExchangeRace(double charWeigth, double unitTypeWeigth,
            double armyTypeWeigth, double costWeigth, double weaponWeigth){
        unitSimil = new UnitSimilarity(charWeigth, unitTypeWeigth,
                armyTypeWeigth, costWeigth, weaponWeigth);
    }

    /**
     * Method which initializes the adaption of all units in the army with
     * the most similar units in the target army.
     * @param army The army to be adapted. (must have the taget race specified
     * as the army race)
     * @return The adapted/exchanged army
     */
    public Army adaptRace(Army army){
        mostSimilarUnits.clear();
        usedEquipment.clear();
        Army adaptedRaceArmy = Army.copy(army);
        adaptedRaceArmy.setArmyUnits(exchangeAllUnits(army));
        return adaptedRaceArmy;
    }

    /**
     * Method which exchanges all units in the army with the most similar units
     * in the target race army list.
     * @param army The army to adapt/exchange
     * @return The collection of adapted/exchanged army units.
     */
    private Set<ArmyUnit> exchangeAllUnits(Army army){
        HashSet<ArmyUnit> newArmy = new HashSet<ArmyUnit>();
        newArmy.addAll(exchageLords(army));
        newArmy.addAll(exchageHeroes(army));
        newArmy.addAll(exchageCore(army));
        newArmy.addAll(exchageSpecial(army));
        newArmy.addAll(exchageRare(army));
        return newArmy;
    }

    /**
     * This method exchanges all lord units in the army with the most similar
     * lord units from the new race. Utility units and equipment are randomly
     * purchased.
     * @param army The Army to change the hero units of
     * @return A set with the new hero units.
     */
    public Set<ArmyUnit> exchageLords(Army army){
        HashSet<ArmyUnit> lords = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Lord);
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==armyType.Lord){
                mostSimilarUnit = findMostSimilarUnit(unit, units);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(1);
                Set<Equipment> possibleEquipment =
                        CreateObjectFromDB.getEquipment(mostSimilarUnit.getRace(),
                        mostSimilarUnit.getMagicPoints());
                System.out.println("exchanged: "+armyUnit.getUnit().getName()+
                        " with: "+mostSimilarUnit.getName());
                assignEquipment(newArmyUnit, possibleEquipment);
                assignUtility(newArmyUnit);
                lords.add(newArmyUnit);
            }
        }
        return lords;
    }

    /**
     * This method exchanges all hero units in the army with the most similar
     * hero units from the new race. Utility units and equipment are randomly
     * purchased.
     * @param army The Army to change the hero units of
     * @return A set with the new hero units.
     */
    public Set<ArmyUnit> exchageHeroes(Army army){
        HashSet<ArmyUnit> heroes = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Hero);
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==armyType.Hero){
                mostSimilarUnit = findMostSimilarUnit(unit, units);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(1);
                Set<Equipment> possibleEquipment =
                        CreateObjectFromDB.getEquipment(mostSimilarUnit.getRace(),
                        mostSimilarUnit.getMagicPoints());
                System.out.println("exchanged: "+armyUnit.getUnit().getName()+
                        " with: "+mostSimilarUnit.getName());
                assignEquipment(newArmyUnit, possibleEquipment);
                assignUtility(newArmyUnit);
                heroes.add(newArmyUnit);
            }
        }
        return heroes;
    }

    /**
     * This method exchanges all core units in the army with the most similar
     * core units from the new race. If the old units had full command, and
     * the new unit is eligible for full command, then the new unit is given full
     * command.
     * @param army The Army to change the core units of
     * @return A set with the new core units. 
     */
    public Set<ArmyUnit> exchageCore(Army army){
        HashSet<ArmyUnit> core = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Core);
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==armyType.Core){
                mostSimilarUnit = findMostSimilarUnit(unit, units);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                if(armyUnit.haveFullCommand()&&mostSimilarUnit.isEligibleForFullCommand())
                    newArmyUnit.giveUnitFullCommand();
                newArmyUnit.setNumberOfUnits(armyUnit.getNumberOfUnits());
                core.add(newArmyUnit);
                System.out.println("exchanged: "+armyUnit.getUnit().getName()+" with: "+mostSimilarUnit.getName()+" and gave full command: "+armyUnit.haveFullCommand());
            }
        }
        return core;
    }

    /**
     * This method exchanges all special units in the army with the most similar
     * special units from the new race. If the old units had full command, and
     * the new unit is eligible for full command, then the new unit is given full
     * command.
     * @param army The Army to change the special units of
     * @return A set with the new special units.
     */
    public Set<ArmyUnit> exchageSpecial(Army army){
        HashSet<ArmyUnit> special = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Special);
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==armyType.Special){
                mostSimilarUnit = findMostSimilarUnit(unit, units);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                if(armyUnit.haveFullCommand()&&mostSimilarUnit.isEligibleForFullCommand())
                    newArmyUnit.giveUnitFullCommand();
                newArmyUnit.setNumberOfUnits(armyUnit.getNumberOfUnits());
                special.add(newArmyUnit);
                System.out.println("exchanged: "+armyUnit.getUnit().getName()+" with: "+mostSimilarUnit.getName()+" and gave full command: "+armyUnit.haveFullCommand());
            }
        }
        return special;
    }

    /**
     * This method exchanges all rare units in the army with the most similar
     * rare units from the new race.
     * @param army The Army to change the rare units of
     * @return A set with the new rare units.
     */
    public Set<ArmyUnit> exchageRare(Army army){
        HashSet<ArmyUnit> rare = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Rare);
        for(ArmyUnit armyUnit : army.getArmyUnits()){
            Unit unit = armyUnit.getUnit();
            if(unit.getArmyType()==armyType.Rare){
                mostSimilarUnit = findMostSimilarUnit(unit, units);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(armyUnit.getNumberOfUnits());
                rare.add(newArmyUnit);
                System.out.println("exchanged: "+armyUnit.getUnit().getName()+" with: "+mostSimilarUnit.getName());
            }
        }
        return rare;
    }

    /**
     * This method finds the most similar unit from the list of available units
     * to the supplied unit, unless the most similar unit is already used, then
     * a the next most similart unit is chosen.
     * @param unit - The unit to be exhanged
     * @param raceUnits - The list of available units
     * @return - The most similar unit
     */
    private Unit findMostSimilarUnit(Unit unit, ArrayList<Unit> raceUnits){
        Unit mostSimilarUnit = null;
        double similarity = 0;
        boolean add = true;
        for(Unit raceUnit: raceUnits){
            try {
                double simil = unitSimil.compute(unit, raceUnit);
                if((simil>similarity)&&(!usedUnit(raceUnit))){
                    similarity = simil;
                    mostSimilarUnit = raceUnit;
                }
            }
            catch (NoApplicableSimilarityFunctionException ex) {}
        }
        //This is only true if all raceUnits have allready been used.
        if(mostSimilarUnit==null){
            add = false;
            similarity = 0;
            for(Unit raceUnit : raceUnits){
                try {
                    double simil = unitSimil.compute(unit, raceUnit);
                    if(simil>similarity){
                        similarity = simil;
                        mostSimilarUnit = raceUnit;
                    }
                }
                catch (NoApplicableSimilarityFunctionException ex) {}
            }
        }
        if(add)
            mostSimilarUnits.add(mostSimilarUnit);
        return mostSimilarUnit;
    }

    /**
     * Method to assign a utility unit to a hero/lord aka mount
     * @param armyUnit The army unit to assign the utility unit to
     */
    private void assignUtility(ArmyUnit armyUnit) {
        java.util.Random random = new java.util.Random();
        int assignUtil = random.nextInt(2);
        if(assignUtil==1){
            Set<UtilityUnit> utility = armyUnit.getUnit().getUtilityUnit();
            if(utility.isEmpty())
                return;
            int utilNum = random.nextInt(utility.size());
            Set<UtilityUnit> newUtility = new HashSet<UtilityUnit>();
            UtilityUnit utilUnit = (UtilityUnit)CollectionControl.getItemAt(
                    utility, utilNum);
            newUtility.add(utilUnit);
            armyUnit.setUtility(newUtility);
            System.out.println("Added: "+utilUnit.getName()+", to: "+
                    armyUnit.getUnit().getName());
        }
    }

    /**
     * Method which assigns equipment to a lord/hero unit
     * @param armyUnit The lord/hero army unit to get the assigned equipment
     * @param purchasableEquipment The collection of equipment the unit can
     * purchase
     */
    private void assignEquipment(ArmyUnit armyUnit, 
            Set<Equipment> purchasableEquipment) {
        java.util.Random random = new java.util.Random();
        Set<Equipment> unitEquipment = armyUnit.getUnit().getEquipment();
        Set<Equipment> newUnitEquipment = new HashSet<Equipment>();
        //Add unit spesific equipment (no magic points)
        int uSpesific = 1;
        if(unitEquipment.size()>1)
            uSpesific = unitEquipment.size()/2;
        int uSpesificNum = random.nextInt(uSpesific)+1;
        if(unitEquipment.isEmpty())
            uSpesificNum = 0;
        for(int i = 0 ; i < uSpesificNum; i++){
            int nextEq = random.nextInt(unitEquipment.size());
            Equipment eq = (Equipment)CollectionControl.getItemAt(unitEquipment,
                    nextEq);
            boolean success = newUnitEquipment.add(eq);
            //The adding will only fail if the equipment have been added in a
            //previous iteration
            if(!success)
                i--;
        }
        //Add magical items.
        //TODO: Logic for battle standard bearers.
        int numberOfMagicalItems = random.nextInt(3)+1;
        int magicPoints = armyUnit.getUnit().getMagicPoints();
        int usedMagicPoints = 0;
        for(int i = 0; i<numberOfMagicalItems; i++){
            if(purchasableEquipment.isEmpty())
                return;
            int nextEq = random.nextInt(purchasableEquipment.size());
            Equipment eq = (Equipment)CollectionControl.getItemAt(
                    purchasableEquipment, nextEq);
            if(!usedEquipment.contains(eq)){
                if((eq.getCost()+usedMagicPoints)<=magicPoints){
                    if(!haveSimilarEquipment(newUnitEquipment, eq)){
                        usedEquipment.add(eq);
                        newUnitEquipment.add(eq);
                        usedMagicPoints += eq.getCost();
                        System.out.println("Added: "+eq.getName()+", to: "+
                                armyUnit.getUnit().getName());
                    }
                }
            }
        }
        armyUnit.setEquipment(newUnitEquipment);
    }

    /**
     * Method to check if a similar magic item is already present in the unit.
     * used to remove the possibility of having e.g.: two armours.
     * @param sEq The collection of the equipment present in the unit
     * @param eq The equipment the unit want to purchase.
     * @return <ul><li>true - if the equipment can be purchased</li>
     * <li>false - if the equipment cannot be purchased.</li></ul>
     */
    private boolean haveSimilarEquipment(Set<Equipment> sEq, Equipment eq){
        for (Equipment equipment : sEq) {
            if(equipment.getItemType()==eq.getItemType())
                return true;
            if(equipment.getItemType()==itemType.Armour&&
                    eq.getItemType()==itemType.Magic_Armour)
                return true;
            if(equipment.getItemType()==itemType.Magic_Armour&&
                    eq.getItemType()==itemType.Armour)
                return true;
        }
        return false;
    }

    /**
     * Method to check if the unit have allready been used as the most
     * similar unit. 
     * @param newUnit The unit to check if is used
     * @return <ul><li>true - if the unit is used</li>
     * <li>false - if the unit is not used</li></ul>
     */
    public boolean usedUnit(Unit newUnit) {
        for (Unit unit : mostSimilarUnits) {
            if(unit.getName().equals(newUnit.getName()))
                return true;
        }
        return false;
    }
}