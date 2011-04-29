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
 * Class which is used to represent items in a JList. By having the isSelected()
 * function will any JList with the CheckBoxListRenderer, render these items
 * as a JCheckBox with the results of the toString() function as its text.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class CheckListItem {
    private String label;
    private boolean isSelected = false;

    /**
     * Initializes the object with the given label
     * @param label String the label to assign this object
     */
    public CheckListItem(String label){
        this.label = label;
    }

    /**
     * Initializes the object with the given label and selection status
     * @param label String the label to assign this object
     * @param selected boolean The selection status to set this object
     */
    public CheckListItem(String label, boolean selected){
        this.label = label;
        isSelected = selected;
    }

    /**
     * Method to check if this item is selected
     * @return <ul><li>true - if this item is selected</li>
     * <li>false - if this item not is selected</li></ul>
     */
    public boolean isSelected(){
        return isSelected;
    }

    /**
     * Method to set the selection status of this object
     * @param selected boolean the selection status to set
     */
    public void setSelected(boolean selected){
        isSelected = selected;
    }
    
    @Override
    public String toString(){
        return label;
    }
}