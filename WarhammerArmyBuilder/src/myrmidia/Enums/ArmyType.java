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
 * Enum used to describe which army type a unit belongs to
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public enum ArmyType {
    /**
     * Enum value indicating the unit to be a core unit
     */
    Core,
    /**
     * Enum value indicating the unit to be a hero character unit
     */
    Hero,
    /**
     * Enum value indicating the unit to be a lord character unit
     */
    Lord,
    /**
     * Enum value indicating the unit to be a rare unit
     */
    Rare,
    /**
     * Enum value indicating the unit to be a special unit
     */
    Special,
    /**
     * Enum value indicating the unit have no associated army type
     */
    _na
}
