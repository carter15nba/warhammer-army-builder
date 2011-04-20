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
import java.util.Map;
import myrmidia.CBR.Resources.SimilarityConfiguration;
import myrmidia.Enums.Outcomes;
import myrmidia.Enums.Races;

/**
 * Class responsible for keeping track of the reasoning behind the selection
 * of a case by the CBR engine. All similaritites and associated values are
 * stored or accessible for this class. The data is used to create the
 * Justification explanations of this application.
 * @author Glenn Rune Strandbråten
 * @version 0,4
 */
public class CaseExplanation implements Explanation{

    private int caseID;
    private Map<String,Double> similarities;
    private Map<String,Races> races;
    private Outcomes caseOutcome;
    private Outcomes queryOutcome;
    private int casePoints;
    private int queryPoints;
    private ArmyUnitExplanation armyUnitExplanation;

    /** Default constructor */
    public CaseExplanation(){
        similarities = new HashMap<String, Double>();
        races = new HashMap<String, Races>();
        armyUnitExplanation = new ArmyUnitExplanation();
    }

    /**
     * Method to aquire the ArmyUnitExplanation component linked to this
     * case explanation.
     * @return ArmyUnitExplanation The ArmyUnitExplanation object
     */
    public ArmyUnitExplanation getArmyUnitExplanation(){
        return armyUnitExplanation;
    }

    /**
     * Method to aquire the outcome of the case
     * @return Outcome The outcome
     */
    public Outcomes getCaseOutcome() {
        return caseOutcome;
    }

    /**
     * Method to set the case outcome
     * @param caseOutcome The case outcome to set
     */
    public void setCaseOutcome(Outcomes caseOutcome) {
        this.caseOutcome = caseOutcome;
    }

    /**
     * Method to get the army points for this case
     * @return int The army points
     */
    public int getCasePoints() {
        return casePoints;
    }

    /**
     * Method to get the outecome of the query
     * @return Outcome The query outcome
     */
    public Outcomes getQueryOutcome() {
        return queryOutcome;
    }

    /**
     * Method to set the outcome of the query
     * @param queryOutcome Outcome the query outcome to set
     */
    public void setQueryOutcome(Outcomes queryOutcome) {
        this.queryOutcome = queryOutcome;
    }

    /**
     * Method to set the case army points
     * @param casePoints int The case army points to set
     */
    public void setCasePoints(int casePoints) {
        this.casePoints = casePoints;
    }

    /**
     * Method to get the query army points
     * @return int the query army points
     */
    public int getQueryPoints() {
        return queryPoints;
    }

    /**
     * Metod to set the query army points
     * @param queryPoints int The query army points to set
     */
    public void setQueryPoints(int queryPoints) {
        this.queryPoints = queryPoints;
    }

    /**
     * Method to set the similarity of a component in the case/query
     * similarity relation. Use one of the specified keys to set the desired
     * data.
     * Used keys are:
     * <ul><li>TotalSimilarity</li>
     * <li>PlayerRaceSimilarity</li>
     * <li>OpponentRaceSimilarity</li>
     * <li>ArmyPointSimilarity</li>
     * <li>ArmySimilarity</li>
     * <li>OutcomeSimilarity</li></ul>
     * @param key String the key of the data to be set
     * @param value double The similarity to be set
     */
    public void setSimilarity(String key, double value){
        similarities.put(key, value);
    }

    /**
     * Method to aquire the similarity of a component in the case/query
     * similarity relation. Use one of the specified keys to get the desired
     * data value.
     * Used keys are:
     * <ul><li>TotalSimilarity</li>
     * <li>PlayerRaceSimilarity</li>
     * <li>OpponentRaceSimilarity</li>
     * <li>ArmyPointSimilarity</li>
     * <li>ArmySimilarity</li>
     * <li>OutcomeSimilarity</li></ul>
     * @param key String the key to the data value to get
     * @return <ul><li>0 - if the key is unknown, or the value not set</li>
     * <li>double in the range of [0..1] with the similarity value requested</li></ul>
     */
    public double getSimilarity(String key){
        Double value = similarities.get(key);
        if(value==null)
            return 0;
        return value.doubleValue();
    }

    /**
     * Method used to set the race value of the specified key
     * Used keys are:
     * <ul><li>QueryPlayerRace</li>
     * <li>CasePlayerRace</li>
     * <li>QueryOpponentRace</li>
     * <li>CaseOpponentRace</li></ul>
     * @param key String the key of the value to set
     * @param value Races the value to be set
     */
    public void setRace(String key, Races value){
        races.put(key, value);
    }

    /**
     * Method used to aquire the race of the specified key
     * Used keys are:
     * <ul><li>QueryPlayerRace</li>
     * <li>CasePlayerRace</li>
     * <li>QueryOpponentRace</li>
     * <li>CaseOpponentRace</li></ul>
     * @return Races the race of the specified key or null if the key
     * is invalid or not set
     */
    public Races getRace(String key){
        return races.get(key);
    }

    /**
     * Method used to get the ID of the case this explanation is connected to
     * @return int The case ID
     */
    public int getCaseID() {
        return caseID;
    }

    /**
     * Method used to set the id of the Case this explanation is connected to
     * @param caseID int the case id to be set
     */
    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public String generateExplanation() {
        SimilarityConfiguration simConf = SimilarityConfiguration.getInstance();
        double armyWeight = simConf.getArmyWeigth();
        double opponentWeigth = simConf.getOpponentWeigth();
        double outcomeWeigth = simConf.getOutcomeWeigth();
        double armySim = getSimilarity("ArmySimilarity");
        double outcomeSim = getSimilarity("OutcomeSimilarity");
        double opponentSim = getSimilarity("OpponentRaceSimilarity");
        String pointsExpl = "";
        if((queryPoints==0)||(casePoints==0))
            pointsExpl = ", since the wildcard value 0 were used in one or both of the army point values";
        String ret = "Case #"+caseID+" have a similarity of "+getSimilarity("TotalSimilarity")+" with the query.\n"
                + "  Based on the weighted average of three components (ArmySimilarity, OpponentSimilarity and OutcomeSimilarity):\n"
                + "    Army Similarity = "+armySim+ " and have a weigth of "+armyWeight+". Army similarity is based on the weighted average of: PlayerRaceSimilarity, ArmyPointSimilarity and ArmyUnitSimilarity\n"
                + "      Army points("+queryPoints+") in query have a similarity of "+getSimilarity("ArmyPointSimilarity")+" with the army points("+casePoints+") in the case, and have a weight of "+simConf.getArmyPointWeight()+pointsExpl+"\n"
                + "      Query player race("+getRace("QueryPlayerRace")+") have a similarity of "+getSimilarity("PlayerRaceSimilarity")+ " with the player race("+getRace("CasePlayerRace")+") in the case, and have a weight of "+simConf.getPlayerRaceWeigth()+"\n"
                + armyUnitExplanation.generateExplanation()
                + "    Opponent Similarity = "+opponentSim+ " and have a weigth of "+opponentWeigth+"\n"
                + "      Query opponent race("+getRace("QueryOpponentRace")+") have a similarity of "+getSimilarity("OpponentRaceSimilarity")+ " with the opponent race("+getRace("CaseOpponentRace")+") in the case\n"
                + "    Outcome Similarity = "+outcomeSim+" and have a weigth of "+outcomeWeigth+"\n"
                + "      Query outcome("+queryOutcome+") have a similarity of "+getSimilarity("OutcomeSimilarity")+" with the outcome("+caseOutcome+") of the case\n"
                + "  This gives the similarity calculation:\n"
                + "    similarity = (armySimilarity*armyWeigth+opponentSimilarity*opponentWeigth+outcomeSimilarity*outcomeWeigth)/(armyWeigth+opponentWeigth*outcomeWeigth)\n"
                + "    similarity = ("+armySim+"*"+armyWeight+"+"+opponentSim+"*"+opponentWeigth+"+"+outcomeSim+"*"+outcomeWeigth+")/("+armyWeight+"+"+opponentWeigth+"+"+outcomeWeigth+")\n"
                + "    similarity = ("+(armySim*armyWeight+opponentSim*opponentWeigth+outcomeSim*outcomeWeigth)+")/"+(armyWeight+opponentWeigth+outcomeWeigth)+"\n"
                + "    similarity = "+getSimilarity("TotalSimilarity");
        return ret;
    }
}
