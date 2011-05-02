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

package myrmidia.CBR.Resources;

/**
 * Singleton class used to hold the configuration data for the similarity
 * calculations.  All weigths and number of retrieved cases (k) are stored here.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class SimilarityConfiguration {

    private double armyWeigth;
    private double opponentWeigth;
    private double outcomeWeigth;
    private double playerRaceWeigth;
    private double armyPointWeight;
    private double armyUnitWeigth;
    private int armyPointInterval;
    private int k;


    /**
     * Default constructor. Use SimilarityConfiguration.getInstace to
     * aquire the instance of this class.
     */
    private SimilarityConfiguration(){setDefaultValues();}

    /**
     * Method to aquire the singleton instance of this class.
     * @return SimilarityConfiguraion The singleton instance of this class.
     */
    public static SimilarityConfiguration getInstance(){
        return SimilarityConfigurationHolder.INSTANCE;
    }

    private static class SimilarityConfigurationHolder{
        private static final SimilarityConfiguration INSTANCE =
                new SimilarityConfiguration();
    }

    /**
     * @return the armyWeigth
     */
    public double getArmyWeigth() {
        return armyWeigth;
    }

    /**
     * @param armyWeigth the armyWeigth to set
     */
    public void setArmyWeigth(double armyWeigth) {
        this.armyWeigth = armyWeigth;
    }

    /**
     * @return the opponentWeigth
     */
    public double getOpponentWeigth() {
        return opponentWeigth;
    }

    /**
     * @param opponentWeigth the opponentWeigth to set
     */
    public void setOpponentWeigth(double opponentWeigth) {
        this.opponentWeigth = opponentWeigth;
    }

    /**
     * @return the outcomeWeigth
     */
    public double getOutcomeWeigth() {
        return outcomeWeigth;
    }

    /**
     * @param outcomeWeigth the outcomeWeigth to set
     */
    public void setOutcomeWeigth(double outcomeWeigth) {
        this.outcomeWeigth = outcomeWeigth;
    }

    /**
     * @return the playerRaceWeigth
     */
    public double getPlayerRaceWeigth() {
        return playerRaceWeigth;
    }

    /**
     * @param playerRaceWeigth the playerRaceWeigth to set
     */
    public void setPlayerRaceWeigth(double playerRaceWeigth) {
        this.playerRaceWeigth = playerRaceWeigth;
    }

    /**
     * @return the armyPointWeight
     */
    public double getArmyPointWeight() {
        return armyPointWeight;
    }

    /**
     * @param armyPointWeight the armyPointWeight to set
     */
    public void setArmyPointWeight(double armyPointWeight) {
        this.armyPointWeight = armyPointWeight;
    }

    /**
     * @return the armyUnitWeigth
     */
    public double getArmyUnitWeigth() {
        return armyUnitWeigth;
    }

    /**
     * @param armyUnitWeigth the armyUnitWeigth to set
     */
    public void setArmyUnitWeigth(double armyUnitWeigth) {
        this.armyUnitWeigth = armyUnitWeigth;
    }

    /**
     * @return the armyPointInterval
     */
    public int getArmyPointInterval() {
        return armyPointInterval;
    }

    /**
     * @param armyPointInterval the armyPointInterval to set
     */
    public void setArmyPointInterval(int armyPointInterval) {
        this.armyPointInterval = armyPointInterval;
    }

    /**
     * @return the k
     */
    public int getK() {
        return k;
    }

    /**
     * @param k the armyPointInterval to set
     */
    public void setK(int k) {
        this.k = k;
    }

    /**
     * Method which sets all the attributes of this class in one go. All
     * weigths must be between: [0..1]. Any weight set outside this range will
     * be automatically altered to fir within the range. Values greater than 1
     * is set to 1, values less than 0 is set to 0.
     * @param opponentWeigth double The weigth of the opponent similarity
     * @param outcomeWeight double The weigth of the outcome similarity
     * @param playerRaceWeigth double The weight of the player race similarity
     * @param armyWeigth double The weight of the army similarity
     * @param armyPointWeigth double The weight of the army points similarity
     * @param armyUnitWeigth double The weight of the army unit similarity
     * @param armyPointInterval int The interval in which the army point
     * similarity is valid
     * @param k int The number of kNN cases to be retrieved
     */
    public void setConfiguration(double opponentWeigth, double outcomeWeight,
            double playerRaceWeigth,double armyWeigth, double armyPointWeigth,
            double armyUnitWeigth, int armyPointInterval, int k){
        this.armyPointWeight = (armyPointWeigth<0) ? 0 : armyPointWeigth;
        this.armyPointWeight = (armyPointWeigth>1) ? 1 : armyPointWeigth;
        this.armyUnitWeigth = (armyUnitWeigth<0) ? 0 : armyUnitWeigth;
        this.armyUnitWeigth = (armyUnitWeigth>1) ? 1 : armyUnitWeigth;
        this.armyWeigth = (armyWeigth<0) ? 0 : armyWeigth;
        this.armyWeigth = (armyWeigth>1) ? 1 : armyWeigth;
        this.opponentWeigth = (opponentWeigth<0) ? 0 : opponentWeigth;
        this.opponentWeigth = (opponentWeigth>1) ? 1 : opponentWeigth;
        this.outcomeWeigth = (outcomeWeight<0) ? 0 : outcomeWeight;
        this.outcomeWeigth = (outcomeWeight>1) ? 1 : outcomeWeight;
        this.playerRaceWeigth = (playerRaceWeigth<0) ? 0 : playerRaceWeigth;
        this.playerRaceWeigth = (playerRaceWeigth>1) ? 1 : playerRaceWeigth;
        this.armyPointInterval = armyPointInterval;
        this.k = k;
    }

    /**
     * Method to reset the configuration back to the default values
     */
    public final void setDefaultValues(){
        armyPointWeight = 1;
        armyUnitWeigth = 1;
        armyWeigth = 1;
        opponentWeigth = 1;
        outcomeWeigth = 1;
        playerRaceWeigth = 1;
        armyPointInterval = 500;
        k = 3;
    }

}
