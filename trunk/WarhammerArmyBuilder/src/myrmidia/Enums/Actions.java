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
 * Enum holding the list of available actions to take during adaption.
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public enum Actions {
    /**
     * Enum indicating that a character were added to the army
     */
    Added_Character,
    /**
     * Enum indicating that a new core group were added to the army
     */
    Added_Core_Group,
    /**
     * Enum indicating that a new core group were added since there were too
     * few core groups to constitute an army
     */
    Added_Core_Group_Few,
    /**
     * Enum indicating that equipment were added to a unit/group
     */
    Added_Equipment,
    /**
     * Enum indicating that full command were given to a group
     */
    Added_Full_Command,
    /**
     * Enum indicating that a general were added to the army. Any character can
     * be a general and this is only meant to convey the message that a
     * character were added to the army since there were none.
     */
    Added_General,
    /**
     * Enum indicating that a random group were added to the army
     */
    Added_Random_Group,
    /**
     * Enum indicating that a rare group were added to the army
     */
    Added_Rare_Group,
    /**
     * Enum indicating that a special group were added to the army
     */
    Added_Special_Group,
    /**
     * Enum indicating that a utility unit were added to the army
     */
    Added_Utility,
    /**
     * Enum indicating that a unit/group cost were decreased
     */
    Decreased_Unit_Cost,
    /**
     * Enum indicating that a group size were reduced
     */
    Decreased_Unit_Size,
    /**
     * Enum indicating that a group size were increased
     */
    Increased_Unit_Size,
    /**
     * Enum indicating that the least expensive unit were found
     */
    Least_Expensive_Unit,
    /**
     * Enum indicating that the least similary unit were found
     */
    Least_Similar_Unit,
    /**
     * Enum indicating that the most expensive unit were found
     */
    Most_Expensive_Unit,
    /**
     * Enum indicating that the most similar unit were found
     */
    Most_Similar_Unit,
    /**
     * Enum indicating that a character were removed
     */
    Removed_Character,
    /**
     * Enum indicating that a core group were removed
     */
    Removed_Core_Group,
    /**
     * Enum indicating that a duplicate group were removed
     */
    Removed_Duplicate,
    /**
     * Enum indicating that equipment were removed from a unit/group
     */
    Removed_Equipment,
    /**
     * Enum indicating that full command were removed
     */
    Removed_Full_Command,
    /**
     * Enum indicating that a random group were removed
     */
    Removed_Random_Group,
    /**
     * Enum indicating that a rare group were removed
     */
    Removed_Rare_Group,
    /**
     * Enum indicating that a special group were removed
     */
    Removed_Special_Group,
    /**
     * Enum indicating that a utility unit were removed
     */
    Removed_Utility,
}