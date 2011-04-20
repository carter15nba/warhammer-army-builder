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

package myrmidia.Exceptions;

/**
 * Exception thrown when trying to access non-existent explanations
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class NoExplanationException extends Exception{

    /**
     * Constructs a NoExplanationException with the ID of the case to be explained
     * @param caseID int The caseID of the case to be explained
     */
    public NoExplanationException(int caseID) {
        super("No case found with caseID: "+caseID);
    }

    /**
     * Constructs a NoExplanationException with the detail message
     * @param message
     */
    public NoExplanationException(String message){
        super(message);
    }

    /**
     * Constructs a NoExplanationException with the caseID and reference to
     * the missing Explanation class and the class trying to access the
     * non-existen explanation
     * @param caseID
     * @param acessingClass The class of the object trying to access the explanation
     * @param missinExplanation The missing explanation class
     */
    public NoExplanationException(int caseID, Class acessingClass,
            Class missinExplanation) {
        super("No explanation of "+missinExplanation+
                ", where found in "+acessingClass+
                " with the caseID: "+caseID);
    }
}
