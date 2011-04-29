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
 * Class to store the selections for equipment and utility units for each
 * unit in an army. This class is used exclusivly in the createSQLUI to help
 * create the SQLs that fills the database. One CaseStorage object represents
 * the equipment/utility units of one ArmyUnit
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class CaseStorage {
    private CheckListItem[] equipment;
    private CheckListItem[] utility;

    /**
     * Method to aquire the units equipment represented as a CheckListItem array
     * @return The CheckListItem array with the units equipment
     */
    public CheckListItem[] getEquipment(){
        return equipment;
    }

    /**
     * Method to aquire the units utility units represented as a CheckListItem array
     * @return The CheckListItem array with the units utility units
     */
    public CheckListItem[] getUtility(){
        return utility;
    }

    /**
     * Method to set the units equipment represented as a CheckListItem array
     * @param eq The CheckListItem array with the units equipment
     */
    public void setEquipment(CheckListItem[] eq){
        equipment = eq;
    }
    /**
     * Method to set the units utility units represented as a CheckListItem array
     * @param ut The CheckListItem array with the units utility units
     */
    public void setUtility(CheckListItem[] ut){
        utility = ut;
    }
}
