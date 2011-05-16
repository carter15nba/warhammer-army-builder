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
package myrmidia.CBR.Resources;

/**
 * Class used to store if a case have been adapted or not.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class ArmyChangeStatus {
    private boolean changed;
    private int caseID;
    
    /**
     * Default constructor
     * @param caseID int The ID of the case whose change status is stored
     * @param changed boolean The change status of the case
     */
    public ArmyChangeStatus(int caseID, boolean changed){
        this.caseID = caseID;
        this.changed = changed;
    }
    
    /**
     * Method to aquire of the case is unchanged
     * @return The change status
     */
    public boolean isUnchanged(){
        return changed;
    }
    
    /**
     * Method to aquire the case id
     * @return The case id
     */
    public int getCaseID(){
        return caseID;
    }
    
    /**
     * Method to set the caseID
     * @param caseID int The caseID to set
     */
    public void setCaseID(int caseID){
        this.caseID = caseID;
    }
}
