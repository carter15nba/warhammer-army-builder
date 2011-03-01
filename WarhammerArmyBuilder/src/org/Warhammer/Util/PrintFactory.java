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

package org.Warhammer.Util;

import java.util.Set;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Case;
import org.Warhammer.Warhammer.Unit;

/**
 * PrintFactory will contain static methods to print jobs
 * which are of a higher complexity than a few print line
 * commands, or print jobs which will occur multiple times.
 * All methods will provide a fully formated easy to read
 * print of the supplied data.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class PrintFactory {

    public static void printArmyUnit(Set<ArmyUnit> army){
        ArmyUnit[] array = new ArmyUnit[army.size()];
        array = army.toArray(array);
        System.out.println("+-------------------------------------");
        System.out.println("| Printing army unit");
        for (ArmyUnit armyUnit : array) {
            Unit unit = armyUnit.getUnitName();
            System.out.println("+-------------------------------------");
            System.out.println("| "+unit.toString());
        }
        System.out.println("+-------------------------------------");
    }
    
    public static void printCase(Case obj,boolean printArmyUnit){
        System.out.println("\n+-------------------------------------\n| "+obj.toString());
        System.out.println("| Calculated cost: "+obj.getArmyID().calculateCost());
        if(printArmyUnit)
            printArmyUnit(obj.getArmyID().getArmyUnits());
        else
            System.out.println("+-------------------------------------");

    }

}
