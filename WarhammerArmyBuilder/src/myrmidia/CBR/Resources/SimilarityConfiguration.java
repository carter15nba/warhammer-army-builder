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
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class SimilarityConfiguration {

    //TODO: Add more weights if someone missed
    private double armyWeigth;
    private double opponentWeigth;
    private double outcomeWeigth;
    private double playerRaceWeigth;
    private double armyPointWeight;
    private double armyUnitWeigth;
    private int armyPointInterval;
    private int k;


    private SimilarityConfiguration(){}
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

    public void setConfiguration(double opponentWeigth, double outcomeWeight,
            double playerRaceWeigth,double armyWeigth, double armyPointWeigth,
            double armyUnitWeigth, int armyPointInterval, int k){
        this.armyPointWeight = armyPointWeigth;
        this.armyUnitWeigth = armyUnitWeigth;
        this.armyWeigth = armyWeigth;
        this.opponentWeigth = opponentWeigth;
        this.outcomeWeigth = outcomeWeight;
        this.playerRaceWeigth = playerRaceWeigth;
        this.armyPointInterval = armyPointInterval;
        this.k = k;
    }

}
