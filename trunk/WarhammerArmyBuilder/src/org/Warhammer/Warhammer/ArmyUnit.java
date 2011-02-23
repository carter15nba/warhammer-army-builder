/*
 *  Copyright (C) 2011 Glenn Rune
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

import java.util.Set;
import jcolibri.cbrcore.Attribute;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class ArmyUnit implements jcolibri.cbrcore.CaseComponent{
    private int ID;
    private int armyID;
    private int numberOfUnits;
    private String unitName;
    private Set<Equipment> equipment;
    private Set<UtilityUnit> utility;

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
     * @return the armyID
     */
    public int getArmyID() {
        return armyID;
    }

    /**
     * @param armyID the armyID to set
     */
    public void setArmyID(int armyID) {
        this.armyID = armyID;
    }
    /**
     * @return the unit
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the equipmentID
     */
    public Set<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * @param equipmentID the equipmentID to set
     */
    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the utilityID
     */
    public Set<UtilityUnit> getUtility() {
        return utility;
    }

    /**
     * @param utilityID the utilityID to set
     */
    public void setUtility(Set<UtilityUnit> utility) {
        this.utility = utility;
    }

    /**
     * @return the numberOfUnits
     */
    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    /**
     * @param numberOfUnits the numberOfUnits to set
     */
    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }


}
