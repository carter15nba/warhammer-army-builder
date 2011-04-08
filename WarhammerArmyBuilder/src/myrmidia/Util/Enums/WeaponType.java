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

package myrmidia.Util.Enums;

/**
 * Enum used to describe what the primary weapon type of a unit is (without any
 * purchases)
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public enum WeaponType {
    /**
     * Enum value indicating that the unit uses the weapon type Great weapon.
     * Great weapon are large heavy weapons which strike hard at the enemy,
     * but they are slow and allways affected by the Allways Strike Last special
     * rule
     */
    Great_weapon,
    /**
     * Enum value indicating that the unit uses the weapon type Long Weapons.
     * Long Weapons are usually hand held weapons (not thrown) and examples
     * of long weapons are Helbard, Spear and Pike
     */
    Long_weapon,
    /**
     * Enum value indicating that the unit uses the weapon type Melee.
     * Melee weapons are any hand held weapons which are used to fight in
     * close quarters e.g.: Swords and Knifes. All hand held close quarter
     * weapons are essentially Melee weapons, but they are categorized as
     * Long weapon or Great weapon if applicable.
     */
    Melee,
    /**
     * Enum value indicating that the unit uses the weapon type Ranged.
     * Ranged weapons are all weapons which are either thrown or, shoots
     * an object at a enemy e,g.: Javelins, Bows and Pistols
     */
    Ranged
}
