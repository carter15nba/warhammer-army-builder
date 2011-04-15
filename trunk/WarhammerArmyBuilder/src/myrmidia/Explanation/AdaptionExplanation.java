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
 *
 * @author Glenn Rune Strandbråten
 * @version 0.2
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

    public Action getCurrentAction(){
        return currentAction;
    }
  
    public String generateExplanation() {
        System.out.println("------------------------------------------\n");
        System.out.println("Justification expl case: "+caseID+"\n");
        for (Action action : actions) {
            action.generateExplanation();
        }
        return "";
    }

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
}