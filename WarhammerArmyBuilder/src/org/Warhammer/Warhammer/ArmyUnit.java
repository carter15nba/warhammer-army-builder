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

import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.Attribute;

/**
 * Class representing a army unit. A army unit is the unit and associated
 * equipment, mounts and crew that is used in a particular case.
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class ArmyUnit implements jcolibri.cbrcore.CaseComponent{
    private int ID;
    private int armyID;
    private int numberOfUnits;
    private Unit unit;
    private Set<Equipment> equipment = new HashSet<Equipment>();
    private Set<UtilityUnit> utility = new HashSet<UtilityUnit>();

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
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * @return the equipmentID
     */
    public Set<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipmentID to set
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
     * @param utility the utility to set
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

    /**
     * Method that determines if the unit have full command and return the
     * results
     * @return <ul><li>true - if the unit have full command</li>
     * <li>false - id the unit does not have full command</li></ul>
     */
    public boolean haveFullCommand(){
        boolean stdb = false;
        boolean music = false;
        boolean promo = false;
        for (Equipment eq : equipment) {
            if(eq.getName().contains("Standard bearer"))
                stdb = true;
            if(eq.getName().contains("Musician"))
                music = true;
        }
        for(UtilityUnit util : utility){
            if(util.isPromotionUnit())
                promo = true;
        }
        if(stdb&&music&&promo)
            return true;
        return false;
    }
}
