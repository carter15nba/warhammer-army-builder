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

package Warhammer;

import java.util.Set;
import jcolibri.cbrcore.Attribute;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class Case implements jcolibri.cbrcore.CaseComponent{

    private Set<Unit> units;
    private Set<Integer> count;
    private int armyPoints;
    private int caseID;
    private String playerRace;
    private String opponentRace;
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
     * @return the count
     */
    public Set<Integer> getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Set<Integer> count) {
        this.count = count;
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
    public String getPlayerRace() {
        return playerRace;
    }

    /**
     * @param playerRace the playerRace to set
     */
    public void setPlayerRace(String playerRace) {
        this.playerRace = playerRace;
    }

    /**
     * @return the opponentRace
     */
    public String getOpponentRace() {
        return opponentRace;
    }

    /**
     * @param opponentRace the opponentRace to set
     */
    public void setOpponentRace(String opponentRace) {
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

}
