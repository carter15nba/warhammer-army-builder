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

package myrmidia.Explanation.Resources;

import myrmidia.Explanation.Explanation;

/**
 * Class which stores the information neccessary explain what changes were
 * made during the race exchange process
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class Exchange implements Explanation{

    private String originalUnit;
    private String replacementUnit;
    private double similarity;

    /**
     * Constructor
     * @param originalUnit String the name of the origivnal unit
     * @param replacementUnit String the name of the unit that replaced the 
     * original unit
     * @param similarity Double The similarity between the original and the 
     * replacement units
     */
    public Exchange(String originalUnit, String replacementUnit, double similarity){
        this.originalUnit = originalUnit;
        this.replacementUnit = replacementUnit;
        this.similarity = similarity;
    }
    /**
     * @return the originalUnit
     */
    public String getOriginalUnit() {
        return originalUnit;
    }

    /**
     * @param originalUnit the originalUnit to set
     */
    public void setOriginalUnit(String originalUnit) {
        this.originalUnit = originalUnit;
    }

    /**
     * @return the replacementUnit
     */
    public String getReplacementUnit() {
        return replacementUnit;
    }

    /**
     * @param replacementUnit the replacementUnit to set
     */
    public void setReplacementUnit(String replacementUnit) {
        this.replacementUnit = replacementUnit;
    }

    /**
     * @return the similarity
     */
    public double getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity the similarity to set
     */
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public String generateExplanation() {
        return originalUnit+" where exchanged with: "+replacementUnit
                + ". The similarity between them is: "+similarity+"\n";
    }
}