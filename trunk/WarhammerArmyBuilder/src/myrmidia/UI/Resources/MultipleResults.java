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

package myrmidia.UI.Resources;

/**
 * Interface to be implemented in all GUI classes which can display multiple
 * dataset through user selection. E.g.: A user database GUI where the personal
 * information of a user is displayed and the operator toggles between the users.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public interface MultipleResults {
    /**
     * Interface method to be called when the user indicates that the previous
     * item in the result set should be displayed;
     */
    public void displayPreviousResult();
    /**
     * Interface mehtod to be called when the user indicated that the next item
     * in the result set should be displayed.
     */
    public void displayNextResult();
    /**
     * Interface method to be called when a result from the result set should be
     * displayed
     */
    public void displayResult();
    /**
     * Interface method to be called when the result set contains data that
     * should be displayed in a table and the display result where called.
     */
    public void populateUnitsTable();
}
