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

package org.Warhammer.Warhammer;

import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.Attribute;
import org.Warhammer.Warhammer.Equipment.itemType;

/**
 * Class representing an army
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class Army implements jcolibri.cbrcore.CaseComponent{
    private int ID;
    private Case.Races playerRace;
    private int armyPoints;
    private Set<ArmyUnit> armyUnits = new HashSet<ArmyUnit>();

    public Attribute getIdAttribute() {
        return new Attribute("ID", this.getClass());
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the playerRace
     */
    public Case.Races getPlayerRace() {
        return playerRace;
    }

    /**
     * @param playerRace the playerRace to set
     */
    public void setPlayerRace(Case.Races playerRace) {
        this.playerRace = playerRace;
    }

    /**
     * @return the armyPoints
     */
    public int getArmyPoints() {
        return armyPoints;
    }

    /**
     * @param armyPoints the armyPoints to set
     */
    public void setArmyPoints(int armyPoints) {
        this.armyPoints = armyPoints;
    }

    /**
     * @return the armyUnits
     */
    public Set<ArmyUnit> getArmyUnits() {
        return armyUnits;
    }

    /**
     * @param armyUnits the armyUnits to set
     */
    public void setArmyUnits(Set<ArmyUnit> armyUnits) {
        this.armyUnits = armyUnits;
    }

    /**
     * Method to calculate the total cost of the army by calculating the cost 
     * of each unit in the army (unit cost, equipment, mounts and crew).
     * @return integer The total army cost
     */
    public int calculateCost(){
        int totalCost = 0;
        for (ArmyUnit armyUnit : armyUnits) {
            totalCost+=calculateTotalUnitCost(armyUnit);
        }
        return totalCost;
    }

    /**
     * Method to calculate the total unit cost (unit, equipment, crew and mounts)
     *
     * @param armyUnit The armyUnit to be calculated
     * @return integer The calculated cost.
     */
    public int calculateTotalUnitCost(ArmyUnit armyUnit){
        int unitCost = 0;
        boolean singleEquipmentCost = false;
        unitCost += calculateUnitCost(armyUnit);
        for (UtilityUnit utilityUnit : armyUnit.getUtility()) {
            unitCost += utilityUnit.getCost();
                if(utilityUnit.getName().equalsIgnoreCase("Empire:Marksman")){
                    singleEquipmentCost = true;
                }
        }
        for(Equipment equipment : armyUnit.getEquipment()){
            if((equipment.getItemType()!=itemType.Armour)&&
                    equipment.getItemType()!=itemType.Weapon)
                unitCost += equipment.getCost();
            else{
                if(singleEquipmentCost)
                    unitCost += equipment.getCost();
                else
                    unitCost += armyUnit.getNumberOfUnits()*equipment.getCost();
            }
        }
        return unitCost;
    }
    /**
     * Method to calculate only the unit cost
     * cost_per_unit*number_of_units
     * @param army The ArmyUnit whose cost is to be calculated
     * @return integer the calculated unit cost.
     */
    public int calculateUnitCost(ArmyUnit army){
        Unit unit = army.getUnit();
        return army.getNumberOfUnits()*unit.getCost();
    }
}
