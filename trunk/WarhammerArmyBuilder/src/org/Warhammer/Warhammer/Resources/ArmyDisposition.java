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
import org.Warhammer.Warhammer.Unit;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class ArmyDisposition {

    private int characters;
    private int lords;
    private int heroes;
    private int core;
    private int special;
    private int rare;

    public ArmyDisposition(){
        resetCounts();
    }

    public final void resetCounts(){
        characters = 0;
        lords = 0;
        heroes = 0;
        core = 0;
        special = 0;
        rare = 0;
    }

    public boolean isFollowingDispositionRules(Army army, UnitDisposition disposition){
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
        if(characters>disposition.getMaxCharacters())
            return false;
        if(lords>disposition.getMaxLords())
            return false;
        if(heroes>disposition.getMaxHeroes())
            return false;
        if(core<disposition.getMinCore())
            return false;
        if(special<disposition.getMinSpecial()||special>disposition.getMaxSpecial())
            return false;
        if(rare<disposition.getMinRare()||rare>disposition.getMaxRare())
            return false;
        return true;
    }

}
