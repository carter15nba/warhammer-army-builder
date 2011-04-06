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
 *
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class CheckListItem {
    private String label;
    private boolean isSelected = false;
    public CheckListItem(String label){
        this.label = label;
    }
    public CheckListItem(String label, boolean selected){
        this.label = label;
        isSelected = selected;
    }
    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean selected){
        isSelected = selected;
    }
    @Override
    public String toString(){
        return label;
    }

}
