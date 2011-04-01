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
import org.Warhammer.Util.CreateObjectFromDB;
import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Unit;
import org.Warhammer.Warhammer.Unit.armyType;

/**
 * Class used to change all the units in an army to another race, by replacing
 * each unit in the old army with the most similar unit from the new race.
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class ExchangeRace {

    private CommonAdaptionFunctions cAF;

    /**
     * Default constructor
     */
    public ExchangeRace(){
        cAF = new CommonAdaptionFunctions(new UnitSimilarity());
    }

    /**
     * Constructor which references the allready preconfigured UnitSimilarity
     * object.
     * @param unitSimilarity The preconfigured UnitSimilarity object
     */
    public ExchangeRace(UnitSimilarity unitSimilarity){
       cAF = new CommonAdaptionFunctions(unitSimilarity);
    }

    /**
     * Constructor which references the allready preconfigured
     * CommonAdaptionFunction object. The UnitSimilarity object is collected
     * from the CommonAdaptionFunction object
     * @param caf The preconfigured CommmonAdaptionFnctions object
     */
    public ExchangeRace(CommonAdaptionFunctions caf){
        cAF = caf;
    }

    /**
     * Method which initializes the adaption of all units in the army with
     * the most similar units in the target army.
     * @param army The army to be adapted. (must have the taget race specified
     * as the army race)
     * @return The adapted/exchanged army
     */
    public Army adaptRace(Army army){
        cAF.resetUsedMagicalEquipment();
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
     * @param oldArmy The Army to change the hero units of
     * @return A set with the new hero units.
     */
    public Set<ArmyUnit> exchageLords(Army oldArmy){
        HashSet<ArmyUnit> lords = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> applicableUnits = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                oldArmy.getPlayerRace(), armyType.Lord);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==armyType.Lord){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = cAF.createNewCharacter(oldArmy, mostSimilarUnit);
                lords.add(newArmyUnit);
                System.out.println("exchanged: "+oldUnit.getName()+
                        " with: "+mostSimilarUnit.getName());
            }
        }
        return lords;
    }

    /**
     * This method exchanges all hero units in the army with the most similar
     * hero units from the new race. Utility units and equipment are randomly
     * purchased.
     * @param oldArmy The Army to change the hero units of
     * @return A set with the new hero units.
     */
    public Set<ArmyUnit> exchageHeroes(Army oldArmy){
        HashSet<ArmyUnit> heroes = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> applicableUnits = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                oldArmy.getPlayerRace(), armyType.Hero);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==armyType.Hero){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = cAF.createNewCharacter(oldArmy, mostSimilarUnit);
                heroes.add(newArmyUnit);
                System.out.println("exchanged: "+oldUnit.getName()+
                        " with: "+mostSimilarUnit.getName());
            }
        }
        return heroes;
    }

    /**
     * This method exchanges all core units in the army with the most similar
     * core units from the new race. If the old units had full command, and
     * the new unit is eligible for full command, then the new unit is given full
     * command.
     * @param oldArmy The Army to change the core units of
     * @return A set with the new core units. 
     */
    public Set<ArmyUnit> exchageCore(Army oldArmy){
        HashSet<ArmyUnit> core = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> applicableUnits = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                oldArmy.getPlayerRace(), armyType.Core);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==armyType.Core){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(oldArmyUnit.getNumberOfUnits());
                if(oldArmyUnit.haveFullCommand()&&mostSimilarUnit.isEligibleForFullCommand())
                    newArmyUnit.giveUnitFullCommand();
                core.add(newArmyUnit);
                System.out.println("exchanged: "+oldUnit.getName()
                        +" with: "+mostSimilarUnit.getName()+
                        ", gave full command: "+newArmyUnit.haveFullCommand());
            }
        }
        return core;
    }

    /**
     * This method exchanges all special units in the army with the most similar
     * special units from the new race. If the old units had full command, and
     * the new unit is eligible for full command, then the new unit is given full
     * command.
     * @param oldArmy The Army to change the special units of
     * @return A set with the new special units.
     */
    public Set<ArmyUnit> exchageSpecial(Army oldArmy){
        HashSet<ArmyUnit> special = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> applicableUnits = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                oldArmy.getPlayerRace(), armyType.Special);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==armyType.Special){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(oldArmyUnit.getNumberOfUnits());
                if(oldArmyUnit.haveFullCommand()&&mostSimilarUnit.isEligibleForFullCommand())
                    newArmyUnit.giveUnitFullCommand();
                special.add(newArmyUnit);
                System.out.println("exchanged: "+oldUnit.getName()+" with: "+
                        mostSimilarUnit.getName()+
                        ", gave full command: "+newArmyUnit.haveFullCommand());
            }
        }
        return special;
    }

    /**
     * This method exchanges all rare units in the army with the most similar
     * rare units from the new race.
     * @param oldArmy The Army to change the rare units of
     * @return A set with the new rare units.
     */
    public Set<ArmyUnit> exchageRare(Army oldArmy){
        HashSet<ArmyUnit> rare = new HashSet<ArmyUnit>();
        Unit mostSimilarUnit = null;
        ArrayList<Unit> applicableUnits = CreateObjectFromDB.findRaceAndArmyTypeUnits(
                oldArmy.getPlayerRace(), armyType.Rare);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==armyType.Rare){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(oldArmyUnit.getNumberOfUnits());
                rare.add(newArmyUnit);
                System.out.println("exchanged: "+oldUnit.getName()+
                        " with: "+mostSimilarUnit.getName());
            }
        }
        return rare;
    }
}