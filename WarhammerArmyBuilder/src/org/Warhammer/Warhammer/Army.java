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

import java.util.Iterator;
import java.util.Set;
import jcolibri.cbrcore.Attribute;
import org.Warhammer.Warhammer.Equipment.itemType;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0,1
 */
public class Army implements jcolibri.cbrcore.CaseComponent{
    private int ID;
    private Case.Races playerRace;
    private int armyPoints;
    private Set<ArmyUnit> armyUnits;

    public Attribute getIdAttribute() {
        return new Attribute("ID", this.getClass());
    }

    /**
     * @return the armyID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param armyID the armyID to set
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
    public int calculateCost(){
        Iterator armyIt = armyUnits.iterator();
        int totalCost = 0;
        while(armyIt.hasNext()){
            ArmyUnit next = (ArmyUnit) armyIt.next();
            int numberOfUnits = next.getNumberOfUnits();
            int baseUnitCost = next.getUnitName().getCost();
            totalCost += baseUnitCost*numberOfUnits;
            int unitCost  = baseUnitCost*numberOfUnits;
            int utilCost = 0;
            int eqCost = 0;
            Iterator utilIt = next.getUtility().iterator();
            while(utilIt.hasNext()){
                UtilityUnit util = (UtilityUnit) utilIt.next();
                totalCost += util.getCost();
                utilCost += util.getCost();
                System.out.println("Utilcost:"+util.getCost());
            }
            Iterator equipIt = next.getEquipment().iterator();
            while(equipIt.hasNext()){
                Equipment equip = (Equipment) equipIt.next();
                if((equip.getItemType()!=itemType.Armour)&&
                        equip.getItemType()!=itemType.Weapon){
                    totalCost += equip.getCost();
                    eqCost += equip.getCost();
                    System.out.println("other: "+equip.getName()+equip.getCost());
                }
                else{
                    totalCost += numberOfUnits*equip.getCost();
                    eqCost+=numberOfUnits*equip.getCost();
                    System.out.println("weapon/armour: "+equip.getName()+equip.getCost());
                }
            }
            System.out.println("UnitName: "+next.getUnitName().getName()+"batalion cost: "+unitCost+", total unit cost: "+(unitCost+utilCost+eqCost));
        }
        return totalCost;
    }
}
