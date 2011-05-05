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

import java.util.HashSet;
import java.util.Set;
import myrmidia.Enums.Races;
import myrmidia.Explanation.Resources.Exchange;

/**
 * Class holding all the information regarding to the exchange of units
 * from one race to another. 
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class ExchangeRaceExplanation implements Explanation{

    private int caseID;
    private Races originalRace;
    private Races replacementRace;
    private Set<Exchange> exchanged;

    /** Default constructor */
    public ExchangeRaceExplanation(){
        exchanged = new HashSet<Exchange>();
    }

    /**
     * Constructor - initializes the object with the supplied parameters
     * @param caseID int The ID of the case to be explained
     * @param originalRace Races enum with the race of the original player race
     * @param replacementRace Race enum with the race of the new player race
     */
    public ExchangeRaceExplanation(int caseID, Races originalRace,
            Races replacementRace){
        this.caseID = caseID;
        this.originalRace = originalRace;
        this.replacementRace = replacementRace;
        exchanged = new HashSet<Exchange>();
    }

    /**
     * @param caseID the caseID to set
     */
    public void setCaseID(int caseID){
        this.caseID = caseID;
    }

    /**
     * @return the caseID
     */
    public int getCaseID(){
        return caseID;
    }

    /**
     * @return the originalRace
     */
    public Races getOriginalRace() {
        return originalRace;
    }

    /**
     * @param originalRace the originalRace to set
     */
    public void setOriginalRace(Races originalRace) {
        this.originalRace = originalRace;
    }

    /**
     * @return the replacementRace
     */
    public Races getReplacementRace() {
        return replacementRace;
    }

    /**
     * @param replacementRace the replacementRace to set
     */
    public void setReplacementRace(Races replacementRace) {
        this.replacementRace = replacementRace;
    }

    /**
     * This method adds an exchange object containing the details surrounding
     * the exchange of two units.
     * @param exchange The exchange object to add
     */
    public void addExchange(Exchange exchange){
        exchanged.add(exchange);
    }

    public String generateExplanation() {
        String ret = "The army race were changed from "+originalRace+" to "+
                replacementRace+", since the case army did not fit the query "
                + "army\nAll unist were exchanged with the most similar "
                + "corresponing unit in the new race.\nThe most similar unit "
                + "is determined based on unit characteristics; similarity "
                + "tables between Army-, Unit, and Weapon types; the units base "
                + "cost; and if the unit is a magician or not.\n";
        for (Exchange exchange : exchanged) {
            ret += "  "+exchange.generateExplanation();
        }
        return ret+="\n";
    }
}
