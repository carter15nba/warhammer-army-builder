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

package org.Warhammer.Warhammer.Resources;

import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Resources.ErrorManager.Messages;
import org.Warhammer.Warhammer.Unit;

/**
 * Class to check if the army follows the disposition rules of its race.
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class ArmyDisposition {

    private int characters;
    private int lords;
    private int heroes;
    private int core;
    private int special;
    private int rare;
    private ErrorManager errorManager;
    private UnitDisposition unitDisposition;

    /**
     * Default constructor
     * @param errMan The ErrorManager object to use for reporting errors,
     */
    public ArmyDisposition(ErrorManager errMan){
        resetCounts();
        this.errorManager = errMan;
    }

    /**
     * Final method that resets the integer counts indicating if an army is
     * following the rules in each category.
     */
    public final void resetCounts(){
        characters = 0;
        lords = 0;
        heroes = 0;
        core = 0;
        special = 0;
        rare = 0;
    }

    /**
     * Method that validates if the army is following the unit disposition rules,
     * e.g.: The number of lords/heroes and their sum is within the rules for
     * the designated army size.
     * @param army The Army object to validate
     * @param disposition The UnitDisposition object holding the race unit dispositon rules.
     * @return <ul><li>true - if all the rules are followed</li>
     * <li>false - if any of the rules are broken</li></ul>
     */
    public boolean isFollowingDispositionRules(Army army, UnitDisposition disposition){
        this.unitDisposition = disposition;
        boolean addedError = false;
        resetCounts();
        for (ArmyUnit armyUnit : army.getArmyUnits()) {
            Unit unit = armyUnit.getUnit();
            switch(unit.getArmyType()){
                case Core:
                    core++;
                    break;
                case Hero:
                    heroes++;
                    break;
                case Lord:
                    lords++;
                    break;
                case Rare:
                    rare++;
                    break;
                case Special:
                    special++;
                    break;
            }
        }
        characters = lords + heroes;

        if(characters>unitDisposition.getMaxCharacters()){
            errorManager.addError(Messages.TOO_MANY_CHARACTERS);
            addedError = true;
        }
        if(lords>unitDisposition.getMaxLords()){
            errorManager.addError(Messages.TOO_MANY_LORDS);
            addedError = true;
        }
        if(heroes>unitDisposition.getMaxHeroes()){
            errorManager.addError(Messages.TOO_MANY_HEROES);
            addedError = true;
        }
        if(core<unitDisposition.getMinCore()){
            errorManager.addError(Messages.TOO_FEW_CORE_GROUPS);
            addedError = true;
        }
        if(special<unitDisposition.getMinSpecial()||special>unitDisposition.getMaxSpecial()){
            errorManager.addError(Messages.TOO_MANY_SPECIAL_GROUPS);
            addedError = true;
        }
        if(rare<unitDisposition.getMinRare()||rare>unitDisposition.getMaxRare()){
            errorManager.addError(Messages.TOO_MANY_RARE_GROUPS);
            addedError = true;
        }
        if(addedError)
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "| Characters: ["+characters+"<="+unitDisposition.getMaxCharacters()+"]\n"
                + "| Lords: ["+lords+"<="+unitDisposition.getMaxLords()+"]\n"
                + "| Heroes: ["+heroes+"<="+unitDisposition.getMaxHeroes()+"]\n"
                + "| Core: ["+core+">="+unitDisposition.getMinCore()+"]\n"
                + "| Special: ["+unitDisposition.getMinSpecial()+"<="+special+"<="+unitDisposition.getMaxSpecial()+"]\n"
                + "| Rare: ["+unitDisposition.getMinRare()+"<="+rare+"<="+unitDisposition.getMaxRare()+"]";
    }
}
