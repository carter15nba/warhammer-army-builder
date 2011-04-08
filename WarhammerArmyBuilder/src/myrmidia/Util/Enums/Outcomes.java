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
 * Enum used to describe the outcome of a case (i.e.: How the battle with
 * the case army against the case opponent went)
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public enum Outcomes {
    /**
     * Enum value indicating that the battle outcome were a defeat
     */
    Defeat,
    /**
     * Enum value indicating that the battle outcome were a draw
     */
    Draw,
    /**
     * Enum value indicating that the battle outcome is unknown (used to
     * inform the system that this case have not yet been verified by the user)
     */
    Unknown,
    /**
     * Enum value indicating that the battle outcome were a victory
     */
    Victory
}
