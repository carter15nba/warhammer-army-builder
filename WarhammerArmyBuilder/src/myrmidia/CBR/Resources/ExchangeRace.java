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
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Enums.ArmyType;
import myrmidia.Explanation.ExchangeRaceExplanation;
import myrmidia.Explanation.ExplanationEngine;
import myrmidia.Explanation.Resources.Exchange;
import myrmidia.Util.CollectionControl;
import myrmidia.Warhammer.Army;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Unit;

/**
 * Class used to change all the units in an army to another race, by replacing
 * each unit in the old army with the most similar unit from the new race.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class ExchangeRace {

    private CommonAdaptionFunctions cAF;
    private ExchangeRaceExplanation currentExplanation;
    private int caseID;

    /**
     * Default constructor
     * @param caseID int The ID of the case to be adapted
     */
    public ExchangeRace(int caseID){
        cAF = new CommonAdaptionFunctions(new UnitSimilarity());
        this.caseID = caseID;
    }

    /**
     * Constructor which references the allready preconfigured UnitSimilarity
     * object.
     * @param caseID int The ID of the case to be adapted
     * @param unitSimilarity The preconfigured UnitSimilarity object
     */
    public ExchangeRace(int caseID, UnitSimilarity unitSimilarity){
       cAF = new CommonAdaptionFunctions(unitSimilarity);
       this.caseID = caseID;
    }

    /**
     * Constructor which references the allready preconfigured
     * CommonAdaptionFunction object. The UnitSimilarity object is collected
     * from the CommonAdaptionFunction object
     * @param caseID int The ID of the case to be adapted
     * @param caf The preconfigured CommmonAdaptionFnctions object
     */
    public ExchangeRace(int caseID, CommonAdaptionFunctions caf){
        cAF = caf;
        this.caseID = caseID;
    }

    /**
     * Method which initializes the adaption of all units in the army with
     * the most similar units in the target army.
     * @param army The army to be adapted. (must have the taget race specified
     * as the army race)
     * @return The adapted/exchanged army
     */
    public Army adaptRace(Army army){
        ArmyUnit au = (ArmyUnit)CollectionControl.getItemAt(army.getArmyUnits(), 0);

        currentExplanation = new ExchangeRaceExplanation(caseID,
                au.getUnit().getRace(),
                army.getPlayerRace());
        ExplanationEngine.getInstance().addExchangeRaceExplanation(currentExplanation);
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
                oldArmy.getPlayerRace(), ArmyType.Lord);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==ArmyType.Lord){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = cAF.createNewCharacter(oldArmy, mostSimilarUnit);
                lords.add(newArmyUnit);
                currentExplanation.addExchange(new Exchange(oldUnit.getName(),
                        mostSimilarUnit.getName(),
                        cAF.getLastMostSimilarUnitSimilarity()));
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
                oldArmy.getPlayerRace(), ArmyType.Hero);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==ArmyType.Hero){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = cAF.createNewCharacter(oldArmy, mostSimilarUnit);
                heroes.add(newArmyUnit);
                currentExplanation.addExchange(new Exchange(oldUnit.getName(),
                        mostSimilarUnit.getName(),
                        cAF.getLastMostSimilarUnitSimilarity()));
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
                oldArmy.getPlayerRace(), ArmyType.Core);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==ArmyType.Core){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(oldArmyUnit.getNumberOfUnits());
                if(oldArmyUnit.haveFullCommand()&&mostSimilarUnit.isEligibleForFullCommand())
                    newArmyUnit.giveUnitFullCommand();
                core.add(newArmyUnit);
                currentExplanation.addExchange(new Exchange(oldUnit.getName(),
                        mostSimilarUnit.getName(),
                        cAF.getLastMostSimilarUnitSimilarity()));
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
                oldArmy.getPlayerRace(), ArmyType.Special);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==ArmyType.Special){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(oldArmyUnit.getNumberOfUnits());
                if(oldArmyUnit.haveFullCommand()&&mostSimilarUnit.isEligibleForFullCommand())
                    newArmyUnit.giveUnitFullCommand();
                special.add(newArmyUnit);
                currentExplanation.addExchange(new Exchange(oldUnit.getName(),
                        mostSimilarUnit.getName(),
                        cAF.getLastMostSimilarUnitSimilarity()));
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
                oldArmy.getPlayerRace(), ArmyType.Rare);
        for(ArmyUnit oldArmyUnit : oldArmy.getArmyUnits()){
            Unit oldUnit = oldArmyUnit.getUnit();
            if(oldUnit.getArmyType()==ArmyType.Rare){
                mostSimilarUnit = cAF.findMostSimilarUnit(oldUnit, applicableUnits);
                ArmyUnit newArmyUnit = new ArmyUnit();
                newArmyUnit.setUnit(mostSimilarUnit);
                newArmyUnit.setNumberOfUnits(oldArmyUnit.getNumberOfUnits());
                rare.add(newArmyUnit);
                currentExplanation.addExchange(new Exchange(oldUnit.getName(),
                        mostSimilarUnit.getName(),
                        cAF.getLastMostSimilarUnitSimilarity()));
            }
        }
        return rare;
    }
}