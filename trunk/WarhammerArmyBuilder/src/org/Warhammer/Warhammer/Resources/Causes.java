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

package org.Warhammer.Warhammer.Resources;

import org.Warhammer.Warhammer.RuleSet.Messages;
import org.Warhammer.Warhammer.Unit;

/**
 * Class to hold the error causes and the units responsible for causing the
 * errors.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class Causes {

    private Unit unit;
    private Messages cause;

    /**
     * Default constructor
     */
    public Causes(){
        unit = null;
        cause = Messages.OK;
    }
    /**
     * Constructor which sets the error cause and the error
     * @param unit The unit causing the error
     * @param cause The error being caused.
     */
    public Causes(Unit unit, Messages cause){
        this.unit = unit;
        this.cause = cause;
    }
    /**
     * Method to set the Unit casuing the error
     * @return The unit causing the error
     */
    public Unit getUnit(){
        return unit;
    }
    /**
     * Method to set the Unit casuing the error
     * @param unit The unit causing the error
     */
    public void setUnit(Unit unit){
        this.unit = unit;
    }

    /**
     * Method to set the error being caused
     * @return The error being caused
     */
    public Messages getCause(){
       return cause;
    }
    /**
     * Method to set the error being caused
     * @param The error being caused
     */
    public void setCause(Messages cause){
        this.cause = cause;
    }

}
