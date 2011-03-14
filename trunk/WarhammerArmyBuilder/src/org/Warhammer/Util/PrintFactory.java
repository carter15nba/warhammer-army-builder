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

    private static final String LINE="+-------------------------------------";
    /**
     * Method to print the supplied army unit to a structured list using
     * the standard System.out
     * @param armyUnits The set of army units to be printed
     * @param army The army the army units belong to.
     */
    public static void printArmyUnit(Set<ArmyUnit> armyUnits,Army army){
        ArmyUnit[] array = new ArmyUnit[armyUnits.size()];
        array = armyUnits.toArray(array);
        System.out.println(LINE);
        System.out.println("| Printing army unit");
        for (ArmyUnit armyUnit : array) {
            Unit unit = armyUnit.getUnit();
            System.out.println(LINE);
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
            if(army!=null)
                System.out.println("| Calculated unit cost: "+army.calculateTotalUnitCost(armyUnit));
        }
        System.out.println(LINE);
    }

    /**
     * Method to print the contents of the case in a structured list using
     * the standard System.out
     * @param _case The Case to be printed
     * @param printArmyUnit Boolean dictating if each unit should be printed or not
     */
    public static void printCase(Case _case,boolean printArmyUnit){
        System.out.println(LINE+"\n| "+_case.toString());
        System.out.println("| Calculated cost: "+_case.getArmy().calculateCost());
        if(printArmyUnit)
            printArmyUnit(_case.getArmy().getArmyUnits(),_case.getArmy());
        else
            System.out.println(LINE);

    }

    /**
     * Method to print the contents of the case in a structured list using the
     * standard System.out. This method will also print the calculated
     * similarity into that list.
     * @param _case The case to be printed
     * @param similarity double The calculated similarity value for the case.
     * @param printArmyUnit Boolean dictating if each unit should be printed or not
     */
    public static void printCase(Case _case,double similarity, boolean printArmyUnit){
        System.out.println(LINE+"\n| "+_case.toString());
        System.out.println("| Calculated cost: "+_case.getArmy().calculateCost());
        System.out.format("| Calculated similarity: %.5f%s%n", similarity*100,"%");
        if(printArmyUnit)
            printArmyUnit(_case.getArmy().getArmyUnits(),_case.getArmy());
        else
            System.out.println(LINE);

    }

}
