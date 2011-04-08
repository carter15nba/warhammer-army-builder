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
 * Enum used to describe which mode the Equal similarity calculation should
 * use to update the explanation component.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public enum Mode {
    /**
     * Enum value indicating that the Opponent explanation component should be set
     */
    Opponent,
    /**
     * Enum value indicating that the Player explanation component should be set
     */
    Player
}
