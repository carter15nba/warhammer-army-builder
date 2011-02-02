/*
 *  Copyright (C) 2011 Glenn Rune Strandbåten
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

package Warhammer;

import javax.persistence.DiscriminatorValue;
import jcolibri.cbrcore.Attribute;
import org.hibernate.annotations.Entity;

/**
 *
 * @author Glenn Rune Strandbåten
 * @version 0.1
 */
public class Equipment implements jcolibri.cbrcore.CaseComponent{

    private int equipmentID;
    private int cost;
    private String name;
    private String equipmentType;

    /**
     * @return the equipmentType
     */
    public String getEquipmentType() {
        return equipmentType;
    }
    /**
     * The type of equiment e.g.: Sword, Shield, Spear, Armour,
     * Two-handed sword etc.
     * @param equipmentType the equipmentType to set
     */
    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Attribute getIdAttribute() {
        return new Attribute("equipmentID", this.getClass());
    }

    /**
     * @return the equpimentID
     */
    public int getEquipmentID() {
        return equipmentID;
    }

    /**
     * @param equpimentID the equpimentID to set
     */
    public void setEquipmentID(int equpimentID) {
        this.equipmentID = equpimentID;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "*Name: "+name+", type: "+equipmentType+", cost: "+cost;
    }
}
