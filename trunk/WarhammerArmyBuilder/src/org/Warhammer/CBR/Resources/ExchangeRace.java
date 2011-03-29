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
import java.util.logging.Level;
import java.util.logging.Logger;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.Util.CreateObjectFromDB;
import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Unit;
import org.Warhammer.Warhammer.Unit.armyType;

/**
 * Class used to change all the units in an army to another race, by replacing
 * each unit in the old army with the most similar unit from the new race.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class ExchangeRace {

    private UnitSimilarity unitSimil;
    private ArrayList<Unit> mostSimilarUnits;

    /**
     * Default constructor
     * Set all unit simililarity weigths to 1.
     */
    public ExchangeRace(){
        unitSimil = new UnitSimilarity();
        mostSimilarUnits = new ArrayList<Unit>();
    }

    /**
     * Constructor wich sets the unit similarity weigths as requested.
     * @param charWeigth
     * @param unitTypeWeigth
     * @param armyTypeWeigth
     * @param costWeigth
     * @param weaponWeigth
     */
    public ExchangeRace(double charWeigth, double unitTypeWeigth,
            double armyTypeWeigth, double costWeigth, double weaponWeigth){
        unitSimil = new UnitSimilarity(charWeigth, unitTypeWeigth,
                armyTypeWeigth, costWeigth, weaponWeigth);
    }

    public Army adaptRace(Army army){
        Army adaptedRaceArmy = Army.copy(army);
        mostSimilarUnits.clear();
        adaptedRaceArmy.setArmyUnits(adaptedArmy(army));
        return adaptedRaceArmy;
    }

    public Set<ArmyUnit> adaptedArmy(Army army){
        HashSet<ArmyUnit> newArmy = new HashSet<ArmyUnit>();
        newArmy.addAll(exchageLords(army));
        newArmy.addAll(exchageHeroes(army));
        newArmy.addAll(exchageCore(army));
        newArmy.addAll(exchageSpecial(army));
        newArmy.addAll(exchageRare(army));
        return newArmy;
    }

    public Set<ArmyUnit> exchageLords(Army army){
        HashSet<ArmyUnit> lords = new HashSet<ArmyUnit>();
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Lord);
        for(ArmyUnit armyUnit : army.getArmyUnits()){

        }

        return lords;
    }
    public Set<ArmyUnit> exchageHeroes(Army army){
        HashSet<ArmyUnit> heroes = new HashSet<ArmyUnit>();
        ArrayList<Unit> units = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                army.getPlayerRace(), armyType.Hero);
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
                if(simil>similarity&&!mostSimilarUnits.contains(raceUnit)){
                    similarity = simil;
                    mostSimilarUnit = raceUnit;
                }
            }
            catch (NoApplicableSimilarityFunctionException ex) {}
        }
        //This is only true if all raceUnits have allready been used.
        if(mostSimilarUnit!=null){
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
}
