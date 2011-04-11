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

package myrmidia.Warhammer.Resources;

import java.util.ArrayList;
import myrmidia.Util.Enums.Messages;

/**
 * Class to manage the errors and their causes when verifying the army
 * rule set for the given army.
 * @author Glenn Rune Strandbråten
 * @version 0.4
 */
public class ErrorManager{
    private ArrayList<Messages> errors;
    private ArrayList<Causes> causes;

    /**
     * Default constructor
     */
    public ErrorManager(){
        errors = new ArrayList<Messages>();
        causes = new ArrayList<Causes>();
    }
    
    /**
     * Method to add an error to the error list if and only if the
     * error is not already present.
     * @param m The error message to add to the list.
     */
    public void addError(Messages m){
        if(!errors.contains(m)){
            errors.add(m);
        }
    }
    /**
     * Method to add an cause to the causes list, this add the cause regardless
     * if the error allready have another cause.
     * @param cause The Casue of the error to add to the list.
     */
    public void addCause(Causes cause){
        causes.add(cause);
    }
    /**
     * Method to get all the errors found in the case.
     * @return The array with the errors found, or a single entry array
     * with and OK message if no errors were found.
     */
    public Messages[] getErrors(){
        if(errors.isEmpty())
            return new Messages[]{Messages.OK};
        Messages[] array = new Messages[errors.size()];
        return errors.toArray(array);
    }
    /**
     * Method to get all the error causes found in the case.
     * @return The array with the causes or null if no causes where found.
     */
    public Causes[] getCauses(){
        if(causes.isEmpty())
            return new Causes[0];
        Causes[] array = new Causes[causes.size()];
        return causes.toArray(array);
    }

    /**
     * Method to get all the causes for the specified error messaqe.
     * @param causedBy The Message that caused the error
     * @return Array with the Causes of the specified error.
     */
    public Causes[] getCauses(Messages causedBy){
        ArrayList<Causes> tmpList = new ArrayList<Causes>();
        for (Causes cause : causes) {
            Messages m = cause.getCause();
            if(m==causedBy)
                tmpList.add(cause);
        }
        Causes[] ret = new Causes[tmpList.size()];
        return tmpList.toArray(ret);
    }

    /**
     * Method to reset the error and causes lists.
     */
    public void resetErrors(){
        errors.clear();
        causes.clear();
    }
}