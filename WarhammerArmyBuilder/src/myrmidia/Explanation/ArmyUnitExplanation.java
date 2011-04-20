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

import java.util.HashMap;
import myrmidia.CBR.Resources.SimilarityConfiguration;

/**
 * Class used to keep track of the similarities and reasoning behind
 * the case selection on the ArmyUnit layer.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class ArmyUnitExplanation implements Explanation{

    private boolean emptyQuery;
    private boolean noEquipment;
    private boolean noUtility;
    private boolean noFoundUnits;
    private HashMap<String, Double> similarities;
    private int denominator;

    /** Default constuctor */
    public ArmyUnitExplanation(){
        similarities = new HashMap<String, Double>();
        emptyQuery = false;
        noEquipment = false;
        noUtility = false;
        noFoundUnits = false;
    }

    /**
     * Method used to set the similarity value with the supplied key and value
     * Used keys:
     * <ul><li>ArmyUnitSimilarity</li>
     * <li>UnitFraction</li>
     * <li>NumberFraction</li>
     * <li>EquipmentFraction</li>
     * <li>UtilityFraction</li></ul>
     * @param key String The key set the value of
     * @param value double The value to set for the key
     */
    public void setSimilarity(String key, double value){
        similarities.put(key, value);
    }

    /**
     * Method used to aquire the value of the supplied key
     * Used keys:
     * <ul><li>ArmyUnitSimilarity</li>
     * <li>UnitFraction</li>
     * <li>NumberFraction</li>
     * <li>EquipmentFraction</li>
     * <li>UtilityFraction</li></ul>
     * @param key String the key to aquire the value from
     */
    public double getSimilarity(String key){
        Double value = similarities.get(key);
        if(value==null)
            return 0;
        return value.doubleValue();
    }

    /**
     * @return the emptyQuery
     */
    public boolean isEmptyQuery() {
        return emptyQuery;
    }

    /**
     * @param emptyQuery the emptyQuery to set
     */
    public void setEmptyQuery(boolean emptyQuery) {
        this.emptyQuery = emptyQuery;
    }

    /**
     * @param denominator the denominator to set
     */
    public void setDenominator(int denominator){
        this.denominator = denominator;
    }
    
    /**
     * @return the denominator
     */
    public int getDenominator(){
        return denominator;
    }

    /**
     * @return the noEquipment
     */
    public boolean isNoEquipment() {
        return noEquipment;
    }

    /**
     * @param noEquipment the noEquipment to set
     */
    public void setNoEquipment(boolean noEquipment) {
        this.noEquipment = noEquipment;
    }

    /**
     * @return the noUtility
     */
    public boolean isNoUtility() {
        return noUtility;
    }

    /**
     * @param noUtility the noUtility to set
     */
    public void setNoUtility(boolean noUtility) {
        this.noUtility = noUtility;
    }

    /**
     * @return the noFoundUnits
     */
    public boolean isNoFoundUnits() {
        return noFoundUnits;
    }

    /**
     * @param noFoundUnits the noFoundUnits to set
     */
    public void setNoFoundUnits(boolean noFoundUnits) {
        this.noFoundUnits = noFoundUnits;
    }

    public String generateExplanation() {
        if(emptyQuery)
            return "      ArmyUnit Similarity = 1\n"
                    + "        The query were empty so the assumed ArmyUnit similaity is: 1\n";
        if(isNoFoundUnits())
            return "      ArmyUnit Similarity = 0\n"
                    + "        Found no units from the query in the case\n";

        SimilarityConfiguration simConf = SimilarityConfiguration.getInstance();
        String denom="";
        double sim = getSimilarity("ArmyUnitSimilarity");
        double uFrac = getSimilarity("UnitFraction");
        double nFrac = getSimilarity("NumberFraction");
        double eFrac = getSimilarity("EquipmentFraction");
        double uuFrac = getSimilarity("UtilityFraction");

        if(noEquipment&&noUtility)
            denom = ", the denominator were reduced to 2 since no equipment or utility units existed in the query";
        else if(noEquipment)
            denom = ", the denominator were reduced to 3 since no equipment existed in the query";
        else if(noUtility)
            denom = ", the denominator were reduced to 3 since no utility units existed in the query";
        return "      ArmyUnit Similarity = "+sim+" and have a weight of "+simConf.getArmyUnitWeigth()+"\n"
             + "        Based on the average of four components UnitFraction, NumberFraction, EquipmentFraction and UtilityFraction\n"
             + "          UnitFraction = "+uFrac+", which is the number of found query formations/groups divided by the number of queried formations/groups\n"
             + "          NumberFraction = "+nFrac+", which is the total number of queried units found divided by the total number of individual units queried (e.g.: One formation may consist of 20 individal units)\n"
             + "          EquipmentFraction = "+eFrac+", which is the total number of queried equipment found divided by the total number of queried equipment\n"
             + "          UtilityFraction = "+uuFrac+", which is the total number of queried utility units found divided by the total number of queried utility units\n"
             + "          The denominator = "+denominator+denom+"\n"
             + "          This gives a ArmyUnitSimilarity of: "+(uFrac+eFrac+nFrac+uuFrac)+"/"+denominator +" = "+ sim+"\n"
                ;
    }
}
