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

package myrmidia.Warhammer;

import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.Attribute;
import myrmidia.Enums.ArmyType;
import myrmidia.Enums.ItemType;
import myrmidia.Enums.Races;

/**
 * Class representing an army
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class Army implements jcolibri.cbrcore.CaseComponent{

    private int ID;
    private Races playerRace;
    private int armyPoints;
    private Set<ArmyUnit> armyUnits = new HashSet<ArmyUnit>();

    /**
     * Static method to create an exact copy of the supplied Army object.
     * The resulting object will behave as if it was created with a constructor
     * and filled with the data of the old case. A call to change the content
     * in the old or new object will not change the same data in the other object.
     * @param copy Army the object to be copied.
     * @return Army the resulting object from the copy process.
     */
    public static Army copy(Army copy) {
        Army army = new Army();
        army.ID = copy.ID;
        army.playerRace = copy.playerRace;
        army.armyPoints = copy.armyPoints;
        army.armyUnits = copy.armyUnits;
        return army;
    }

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
    public Races getPlayerRace() {
        return playerRace;
    }

    /**
     * @param playerRace the playerRace to set
     */
    public void setPlayerRace(Races playerRace) {
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
            //If the utility unit is a promotoion unit and min number of units is 0
            //then all units in the group gets the promotion at promotion cost/unit
            if((utilityUnit.getMinNumber()==0)&&(utilityUnit.isPromotionUnit()))
                unitCost += utilityUnit.getCost()*armyUnit.getNumberOfUnits();
            else
                unitCost += utilityUnit.getCost();
            //TODO: Find a better more universal method to assign single equipment cost
            if(utilityUnit.getName().equalsIgnoreCase("Empire:Marksman")){
                singleEquipmentCost = true;
            }
        }
        for(Equipment equipment : armyUnit.getEquipment()){
            if((equipment.getItemType()!=ItemType.Armour)&&
                    equipment.getItemType()!=ItemType.Weapon)
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

    /**
     * Method which checks if a unique unit is present in the army
     * @param armyUnits The list of army units in the army
     * @param armyUnit The army unit to check.
     * @return <ul><li>true - if the unique unit is present in the army</li>
     * <li>false - if the unique unit not is present in the army, or if the unit
     * not is a unique unit</li></ul>
     */
    public static boolean doesArmyContainUniqueUnit(Set<ArmyUnit> armyUnits, ArmyUnit armyUnit){
        Unit unit = armyUnit.getUnit();
        if(unit.isUniqueUnit()) {
            for (ArmyUnit au : armyUnits) {
                Unit auu = au.getUnit();
                if(unit.getName().equals(auu.getName()))
                    return true;
            }
        }
        return false;
    }

    /**
     * Method which checks if the army contain at least one BSB
     * @param armyUnits The collection of army units belonging to the army
     * to check if have a BSB
     * @return <ul><li>true - if at least one unit in the army is a BSB</li>
     * <li>false - if no units in the army is a BSB, or if no units in the
     * army can be a BSB</li></ul>
     */
    public static boolean doesArmyHaveBattleStandardBearer(Set<ArmyUnit> armyUnits){
        for (ArmyUnit au : armyUnits) {
            if(au.isBattleStandardBearer())
                return true;
        }
        return false;
    }

    /**
     * Method to check if the army does contain a general. An army must have a
     * general in order to be a army. All lords and heros may be a general, unless
     * it is a BSB
     * @param armyUnits The collection of units in the army
     * @return <ul><li>true - if there is at least one unit in the army which
     * qualifies to be a general</li>
     * <li>false - if no units in the army qualifies to be a general</li></ul>
     */
    public static boolean haveGeneral(Set<ArmyUnit> armyUnits){
        for (ArmyUnit armyUnit : armyUnits) {
            Unit unit = armyUnit.getUnit();
            ArmyType at = unit.getArmyType();
            if(at==ArmyType.Lord||at==ArmyType.Hero){
                if(!armyUnit.isBattleStandardBearer())
                    return true;
            }
        }
        return false;
    }
}