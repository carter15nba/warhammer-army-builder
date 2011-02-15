/*
 *  Copyright (C) 2011 Glenn Rune
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

package org.Warhammer.Warhammer;

import java.util.ArrayList;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class ArmyUnit extends Unit_old{
    private ArrayList<UtilityUnit_old> utilityUnits;

    /**
     * Constructor to create a new ArmyUnit object and initialization of the
     * super class.
     */
    public ArmyUnit(){
        super();
        utilityUnits = null;
    }

    /**
     * Method to aquire the utilitUnit objects associated with this unit.
     * @return null if there is no crews associated with this unit, otherwise the ArrayList<UtilityUnit> containg the associated utilityUnits.
     */
    public ArrayList<UtilityUnit_old> getUtilityUnits(){
        return utilityUnits;
    }


    /**
     * Method that associates a new utilityUnit with this unit.
     * @param crew the UtilityUnit to associate with this unit.
     */
    public void addCrew(UtilityUnit_old utilityUnit){
        if(utilityUnits==null)
            utilityUnits = new ArrayList<UtilityUnit_old>();
        utilityUnits.add(utilityUnit);
    }
    
    @Override
    public String toString(){
        String toString = super.toString();
        if(utilityUnits!=null)
            for(UtilityUnit_old utilityUnit : utilityUnits){
                toString += "\n\t"+utilityUnit.toString();
            }
        return toString;
    }
}
