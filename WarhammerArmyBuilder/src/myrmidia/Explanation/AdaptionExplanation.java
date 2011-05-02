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

package myrmidia.Explanation;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for keeping track of the adaption process and
 * generate an explanation (Justification) for this process.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class AdaptionExplanation implements Explanation{

    private int caseID;
    private List<Action> actions;
    private Action currentAction=null;
    private int actionPos;

    public AdaptionExplanation(){this.actions = new ArrayList<Action>();}
    public AdaptionExplanation(int caseID){
        this.caseID = caseID;
        this.actions = new ArrayList<Action>();
    }


    /**
     * @return the caseID
     */
    public int getCaseID() {
        return caseID;
    }

    /**
     * @param caseID the caseID to set
     */
    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    /**
     * Method to add a new Action to the action list. If the Action allready
     * exists in the list the existing object is returned and the new one
     * discared.
     * @param action The Action to set
     * @return The added or existing Action object
     */
    public Action addAction(Action action){
        if(actionExists(action)){
            currentAction = actions.get(actionPos);
        }
        else{
            actions.add(action);
            currentAction = action;
        }
        return currentAction;
    }

    /**
     * Method which aquires the current active Action. The active action is
     * determined by the latest Action added to the list
     * @return The current Action
     */
    public Action getCurrentAction(){
        return currentAction;
    }

    /**
     * Method to check if the action exists in the list
     * @param action The Action to check for its existence
     * @return <ul><li>true - if the Action exists</li>
     * <li>false - if the action does not exist</li></ul>
     */
    private boolean actionExists(Action action) {
        int pos = 0;
        for (Action act : actions) {
            if(act.equals(action)){
                actionPos = pos;
                return true;
            }
            pos++;
        }
        return false;
    }

    public String generateExplanation() {
        String ret = "";
        for (Action action : actions) {
            ret += "The unit/formation: "+action.getAffectedArmyUnit().getUnit().getName()+", where changed based on:\n" +
            action.generateExplanation();

        }
        return ret;
    }
}