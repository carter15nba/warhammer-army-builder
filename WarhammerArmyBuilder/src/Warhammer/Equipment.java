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

/**
 *
 * @author Glenn Rune Strandbåten
 * @version 0.1
 */
public class Equipment extends CoreCase{
    
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
}
