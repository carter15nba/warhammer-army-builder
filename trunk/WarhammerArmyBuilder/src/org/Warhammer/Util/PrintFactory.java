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
import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Case;
import org.Warhammer.Warhammer.Equipment;
import org.Warhammer.Warhammer.Unit;
import org.Warhammer.Warhammer.UtilityUnit;

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

    public static void printArmyUnit(Set<ArmyUnit> army,Army parent){
        ArmyUnit[] array = new ArmyUnit[army.size()];
        array = army.toArray(array);
        System.out.println("+-------------------------------------");
        System.out.println("| Printing army unit");
        for (ArmyUnit armyUnit : array) {
            Unit unit = armyUnit.getUnit();
            System.out.println("+-------------------------------------");
            System.out.println("| Unit: "+unit.getName()+", base cost: "+unit.getCost()+", unit type: "+unit.getUnitType()+", army type: "+unit.getArmyType());
            System.out.println("| Number of units: "+armyUnit.getNumberOfUnits());
            System.out.println("|   Equipment:");
            Equipment[] equip = new Equipment[armyUnit.getEquipment().size()];
            equip = armyUnit.getEquipment().toArray(equip);
            for (Equipment equipment : equip) {
                System.out.println("|    Name: "+equipment.getName()+", cost: "+equipment.getCost());
            }
            System.out.println("|   Utility:");
            UtilityUnit[] util = new UtilityUnit[armyUnit.getUtility().size()];
            util = armyUnit.getUtility().toArray(util);
            for (UtilityUnit utility : util) {
                System.out.println("|    Name: "+utility.getName()+", cost: "+utility.getCost());
            }
            if(parent!=null)
                System.out.println("| Calculated unit cost: "+parent.calculateTotalUnitCost(armyUnit));

        }
        System.out.println("+-------------------------------------");
    }
    
    public static void printCase(Case obj,boolean printArmyUnit){
        System.out.println("\n+-------------------------------------\n| "+obj.toString());
        System.out.println("| Calculated cost: "+obj.getArmy().calculateCost());
        if(printArmyUnit)
            printArmyUnit(obj.getArmy().getArmyUnits(),obj.getArmy());
        else
            System.out.println("+-------------------------------------");

    }
    public static void printCase(Case obj,double similarity, boolean printArmyUnit){
        System.out.println("\n+-------------------------------------\n| "+obj.toString());
        System.out.println("| Calculated cost: "+obj.getArmy().calculateCost());
        System.out.println("| Calculated similarity: "+similarity);
        if(printArmyUnit)
            printArmyUnit(obj.getArmy().getArmyUnits(),obj.getArmy());
        else
            System.out.println("+-------------------------------------");

    }

}
