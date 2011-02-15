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
import java.util.Set;
import jcolibri.cbrcore.Attribute;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class Case implements jcolibri.cbrcore.CaseComponent{

    public enum Races{Arcane_Creatures,Beastmen,Bretonnia,Dark_Elves,Daemons_of_Chaos,Dwarfs,Empire,High_Elves,Lizardmen,Ogre_Kingdoms,Orcs_and_Goblins,Skaven,Tomb_Kings,Vampire_Counts,Warriors_of_Chaos,Wood_Elves};

    private Set<Unit> units;
    private int armyPoints;
    private int caseID;
    private Races playerRace;
    private Races opponentRace;
    private String outcome;

    /**
     * @return the units
     */
    public Set<Unit> getUnits() {
        return units;
    }

    /**
     * @param units the units to set
     */
    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    /**
     * @return the armyPoints
     */
    public int getArmyPoints() {
        return armyPoints;
    }

    /**
     * @param armyPoints the armyPoints to set
     */
    public void setArmyPoints(int armyPoints) {
        this.armyPoints = armyPoints;
    }

    /**
     * @return the playerRace
     */
    public Races getPlayerRace() {
        return playerRace;
    }

    /**
     * @param playerRace the playerRace to set
     */
    public void setPlayerRace(Races playerRace) {
        this.playerRace = playerRace;
    }

    /**
     * @return the opponentRace
     */
    public Races getOpponentRace() {
        return opponentRace;
    }

    /**
     * @param opponentRace the opponentRace to set
     */
    public void setOpponentRace(Races opponentRace) {
        this.opponentRace = opponentRace;
    }

    /**
     * @return the outcome
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     * @param outcome the outcome to set
     */
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Attribute getIdAttribute() {
        return new Attribute("caseID", this.getClass());
    }

    /**
     * @return the ID
     */
    public int getCaseID() {
        return caseID;
    }

    /**
     * @param ID the ID to set
     */
    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    @Override
    public String toString(){
        String retString = "\n-----------\n"
                + "CaseID: "+getCaseID()+", race: "+getPlayerRace()+"\n"
                + "Points: "+getArmyPoints()+",  opponent race: "+getOpponentRace()+"\n"
                + "Outcome: "+getOutcome();
        if(units!=null)
            if(units.size()>0){
                Iterator<Unit> iterator = units.iterator();
                while(iterator.hasNext()){
                    org.Warhammer.Warhammer.Unit u = iterator.next();
                    retString+="\n  "+u.toString();
                }
        }
        return retString;
    }
    public int calculateCaseCost(){
        int totalCost=0;
        for (Unit unit : units) {
            totalCost+=unit.calculateTotalUnitCost();
        }
        return totalCost;
    }

}
