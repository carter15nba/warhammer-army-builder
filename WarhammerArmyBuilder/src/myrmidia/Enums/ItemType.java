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

package myrmidia.Enums;

/**
 * Enum used to describe what type of item an item is
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public enum ItemType {
    /**
     * Enum value indicating the item is of type arcane items
     */
    Arcane_Items,
    /**
     * Enum value indicating the item is of type armour
     */
    Armour,
    /**
     * Enum value indicating the item is of type enchanted items
     */
    Enchanted_Items,
    /**
     * Enum value indicating the item is of type magic armour
     */
    Magic_Armour,
    /**
     * Enum value indicating the item is of type magic weapon
     */
    Magic_Weapon,
    /**
     * Enum value indicating the item is of type standard
     */
    Standard,
    /**
     * Enum value indicating the item is of type talisman
     */
    Talisman,
    /**
     * Enum value indicating that this is regared as an item but is used to
     * upgrade a unit (usually promotes a formation unit to a Musician or
     * Standard bearer, those upgrades are regarded as an item since if the
     * musician or standard bearer dies another unit in the formation may pick
     * up the item and use it)
     */
    Unit_Upgrade,
    /**
     * Enum value indicating the item is of type weapon
     */
    Weapon
}
