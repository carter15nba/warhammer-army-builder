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
 * Enum used to describe which error messages were encoutered during the army
 * rule validation
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public enum Messages {
    /**
     * Enum that indicates that the army have no general. A general can be
     * any Character unit (Lord/Hero) unless that character is also the BSB
     */
    NO_ARMY_GENERAL,
    /**
     * Enum that indicates that no errors were encoutered during the army
     * validation
     */
    OK,
    /**
     * Enum indicating that a rare unit have been duplicated into too many
     * formations. There can only exist a set number of formations/groups
     * with the same rare unit.
     */
    TOO_MANY_DUPLICATE_RARE_UNITS,
    /**
     * Enum indicating that a special unit have been duplicated into too many
     * formations. There can only exist a set number of formations/groups
     * with the same special unit.
     */
    TOO_MANY_DUPLIACTE_SPECIAL_UNITS,
    /**
     * Enum indicating that there is used to few core points for the army to
     * validate. At least 25% of the points must be used on core units.
     */
    TOO_FEW_CORE_POINTS,
    /**
     * Enum indicating that there is too few formations/groups in the army. A
     * army must have at least 3 formations in addition to the general.
     */
    TOO_FEW_GROUPS,
    /**
     * Enum indicating that there have been used too few points to create the
     * army.
     */
    TOO_FEW_POINTS_TOTAL,
    /**
     * Enum indicating that the formation size is lower than the minimum formation
     * size limit.
     */
    TOO_FEW_UNITS_IN_GROUP,
    /**
     * Enum indicating that there are used too many hero points in the army.
     * Heroes can collectivly use up to 25% of the available army points
     */
    TOO_MANY_HERO_POINTS,
    /**
     * Enum indicating that there are used too many lord points in the army.
     * Lords can collectivly use up to 25% of the available army points
     */
    TOO_MANY_LORD_POINTS,
    /**
     * Enum indicating that there have been used to0 many points to create
     * the army
     */
    TOO_MANY_POINTS_TOTAL,
    /**
     * Enum indicating that there have been used too many points on rare units.
     * Rare units can only use up to 25% of the available army points
     */
    TOO_MANY_RARE_POINTS,
    /**
     * Enum indicating that there have been used too many points on special units.
     * Special units can only use up to 25% of the available army points
     */
    TOO_MANY_SPECIAL_POINTS,
    /**
     * Enum indicating that the number of units in the formatin exceedes the
     * maximum number of units in the formation
     */
    TOO_MANY_UNITS_IN_GROUP,
    /**
     * Enum indicating that the case/unit belongs to an incorrect race based on
     * the query race
     */
    WRONG_RACE
}