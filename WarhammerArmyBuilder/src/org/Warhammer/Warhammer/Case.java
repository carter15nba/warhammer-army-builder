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

import java.util.Iterator;
import jcolibri.cbrcore.Attribute;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0,5
 */
public class Case implements jcolibri.cbrcore.CaseComponent{

    public enum Races{Arcane_Creatures,Beastmen,Bretonnia,Dark_Elves,Daemons_of_Chaos,Dwarfs,Empire,High_Elves,Lizardmen,Ogre_Kingdoms,Orcs_and_Goblins,Skaven,Tomb_Kings,Vampire_Counts,Warriors_of_Chaos,Wood_Elves};
    public enum Outcomes{Defeat,Draw,Victory};
    private int ID;
    private Army armyID;
    private Races opponent;
    private Outcomes outcome;
    
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
    public Army getArmyID() {
        return armyID;
    }

    /**
     * @param armyID the armyID to set
     */
    public void setArmyID(Army armyID) {
        this.armyID = armyID;
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
        return "Case ID:"+ID+
                ", Player race: "+
                armyID.getPlayerRace()+
                ", Army Points: "+armyID.getArmyPoints()+
                ", Opponet race: "+opponent+
                ", Outcome: "+outcome;

    }

}
