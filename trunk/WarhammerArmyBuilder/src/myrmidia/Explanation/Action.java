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
import myrmidia.Explanation.Resources.AdaptionRule;
import myrmidia.Enums.Messages;
import myrmidia.Enums.Actions;
import myrmidia.Warhammer.ArmyUnit;

/**
 * Class to keep record of one change that occured during the adaptation of
 * a case
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class Action implements Explanation{
    
    private Messages message;
    private List<Actions> adaptionRules;
    private ArmyUnit affectedArmyUnit;
    private boolean usedInExplanation;

    /**
     * Constructor used to set the action and message on instantiation
     * @param message String describing what action where performed
     * @param message Message describing why the action performed where neccessary
     */
    public Action(Messages message, ArmyUnit armyUnit){
        this.message = message;
        adaptionRules = new ArrayList<Actions>();
        this.affectedArmyUnit = armyUnit;
        usedInExplanation = false;
    }

    /**
     * @return the message
     */
    public Messages getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Messages message) {
        this.message = message;
    }

    public void addRule(Actions rule){
        if(!adaptionRules.contains(rule))
            adaptionRules.add(rule);
    }

    /**
     * @return the affectedArmyUnit
     */
    public ArmyUnit getAffectedArmyUnit() {
        return affectedArmyUnit;
    }

    /**
     * @param affectedArmyUnit the affectedArmyUnit to set
     */
    public void setAffectedArmyUnit(ArmyUnit affectedArmyUnit) {
        this.affectedArmyUnit = affectedArmyUnit;
    }

    @Override
    public boolean equals(Object o){
        if((o instanceof Action)){
            Action action = (Action) o;
            if((affectedArmyUnit.equals(action.affectedArmyUnit))&&message==action.message)
                return true;
        }
        return false;
    }

    public String generateExplanation() {
        AdaptionRule ar = AdaptionRule.getInstance();
        String ret = "  Error: "+message+", where the following action(s) taken:\n";
        for (Actions action : adaptionRules) {
            ret += "    "+ar.getRuleDescription(action)+"\n";
        }
        return ret;
    }

    /**
     * @return the usedInExplanation
     */
    public boolean isUsedInExplanation() {
        return usedInExplanation;
    }

    /**
     * @param usedInExplanation the usedInExplanation to set
     */
    public void setUsedInExplanation(boolean usedInExplanation) {
        this.usedInExplanation = usedInExplanation;
    }

}
