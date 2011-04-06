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

import myrmidia.Warhammer.Resources.ErrorManager.Messages;

/**
 * Class to keep record of one change that occured during the adaptation of
 * a case
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class Action {
    
    private String action;
    private Messages reason;

    /**
     * Default constructor
     */
    public Action(){}

    /**
     * Constructor used to set the action and message on instantiation
     * @param action String describing what action where performed
     * @param message Message describing why the action performed where neccessary
     */
    public Action(String action, Messages message){
        this.action = action;
        this.reason = message;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the reason
     */
    public Messages getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Messages reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Based on this message: "+reason+"\n"
                + "where this action taken: "+action;
    }
}
