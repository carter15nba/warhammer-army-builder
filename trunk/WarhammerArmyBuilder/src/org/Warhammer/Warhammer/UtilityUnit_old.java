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

package org.Warhammer.Warhammer;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class UtilityUnit_old extends Unit_old{
    /**
     * Method to set the name of the Crew unit. This method overrides the
     * setName method in the Unit super class.
     * @param name String the name of the Crew unit.
     */
    @Override
    public void setName(String name){
        if(name.startsWith("-"))
            super.setName(name);
        else
            super.setName("-"+name);
    }
}
