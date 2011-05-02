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

package myrmidia.Warhammer;

import jcolibri.cbrcore.Attribute;
import myrmidia.Enums.Outcomes;
import myrmidia.Enums.Races;

/**
 * Class representing a CBR case.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class Case implements jcolibri.cbrcore.CaseComponent{
    private int ID;
    private Army army = new Army();
    private Races opponent;
    private Outcomes outcome;

    /**
     * Static method to create an exact copy of the supplied case object.
     * The resulting object will behave as if it was created with a constructor
     * and filled with the data of the old case. A call to change the content
     * in the old or new object will not change the same data in the other object.
     * @param copy Case the object to be copied.
     * @return Case the resulting object from the copy process.
     */
    public static Case copy(Case copy){
        Case _case = new Case();
        _case.ID = copy.ID;
        _case.army = copy.army;
        _case.opponent = copy.opponent;
        _case.outcome = copy.outcome;
        return _case;
    }

    public Attribute getIdAttribute() {
        return new Attribute("ID", this.getClass());
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the armyID
     */
    public Army getArmy() {
        return army;
    }

    /**
     * @param army the armyID to set
     */
    public void setArmy(Army army) {
        this.army = army;
    }

    /**
     * @return the opponent
     */
    public Races getOpponent() {
        return opponent;
    }

    /**
     * @param opponent the opponent to set
     */
    public void setOpponent(Races opponent) {
        this.opponent = opponent;
    }

    /**
     * @return the outcome
     */
    public Outcomes getOutcome() {
        return outcome;
    }

    /**
     * @param outcome the outcome to set
     */
    public void setOutcome(Outcomes outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString(){
        return "Case ID: "+ID+
                ", Army ID: "+army.getID()+
                ", Player race: "+
                army.getPlayerRace()+
                ", Army Points: "+army.getArmyPoints()+
                ", Opponent race: "+opponent+
                ", Outcome: "+outcome;
    }
}