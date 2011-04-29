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

import java.util.ArrayList;
import java.util.List;

/**
 * Controler class for the UnitModels of a Army, one UnitModelControler object
 * must be instanciated for each army in the user interface.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class UnitModelControler {
    private List<UnitModel> models;

    /** Default constructor */
    public UnitModelControler(){
        models = new ArrayList<UnitModel>();
        models.add(new UnitModel());
    }

    /**
     * Method to remove the UnitModel at the specified index, when a UnitModel
     * is removed is all subsequent unit models updated with a new row to keep
     * the UnitModels synchronized with the unit table in the UI.
     * @param index int The index of the UnitModel to remove
     */
    public void removeUnitModel(int index){
        if((index<0)||(index>models.size()-1))
            return;
        models.remove(index);
        for(int i = index; i<models.size(); i++){
            models.get(i).setRow(i);
        }
    }

    /**
     * Method to get the UnitModel at the specified index
     * @param index int The index of the UnitModel
     * @return The UnitModel at the specified index, or null if the index where
     * out of bounds.
     */
    public UnitModel getUnitModel(int index){
        if((index<0)||(index>models.size()-1))
            return null;
        return models.get(index);
    }

    /**
     * Add a UnitModel to the list
     * @param unitModel The UnitModel to be added
     */
    public void addUnitModel(UnitModel unitModel){
        models.add(unitModel);
    }

    /**
     * Adds a UnitModel to the list at the specified index, if there is fewer
     * UnitModels in the list than the index requires new dummy UnitModels are
     * placed as placeholders until they are replaced by the actual UnitModels.
     * @param index int The index to add the UnitModel
     * @param unitModel The UnitModel to be added
     */
    public void addUnitModel(int index, UnitModel unitModel){
        if(models.size()<index)
            placeDummyObjects(index);
        models.add(index, unitModel);
        for(int i=index+1; i<models.size(); i++){
            models.get(i).setRow(i);
        }
    }

    /**
     * Method to replace the UnitModel at the specified location with a new
     * UnitModel. If the index is out of bounds dummy UnitModels are created.
     * @param index int the index to add the UnitModel
     * @param unitModel The UnitModel to be added
     */
    public void replaceUnitModel(int index, UnitModel unitModel){
        if(models.size()<index)
            placeDummyObjects(index);
        models.add(index, unitModel);
        models.remove(index+1);
    }

    /**
     * Method to clear all UnitModels from the list
     */
    public void clear(){
        models.clear();
    }

    /**
     * Method to create dummy UnitModels. Dummy UnitModels are created from
     * (including) the current number of UnitModels in the list, to (excluding)
     * the supplied index.
     * @param index int The index (excluding) to stop the creation of Dummy
     * UnitModels at
     */
    public void placeDummyObjects(int index) {
        for(int i=models.size(); i<index;i++){
            models.add(new UnitModel());
        }
    }
}