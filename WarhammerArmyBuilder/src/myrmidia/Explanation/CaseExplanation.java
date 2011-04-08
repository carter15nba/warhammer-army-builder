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
import myrmidia.Warhammer.Case.*;

/**
 *
 * @author Glenn Rune Strandbråten
 */
public class CaseExplanation implements Explanation{

    private int caseID;
    private Map<String,Double> similarities;
    private Map<String,Races> races;
    private Outcomes caseOutcome;
    private Outcomes queryOutcome;
    private int casePoints;
    private int queryPoints;

    public Outcomes getCaseOutcome() {
        return caseOutcome;
    }

    public void setCaseOutcome(Outcomes caseOutcome) {
        this.caseOutcome = caseOutcome;
    }

    public int getCasePoints() {
        return casePoints;
    }

    public Outcomes getQueryOutcome() {
        return queryOutcome;
    }

    public void setQueryOutcome(Outcomes queryOutcome) {
        this.queryOutcome = queryOutcome;
    }

    public void setCasePoints(int casePoints) {
        this.casePoints = casePoints;
    }

    public int getQueryPoints() {
        return queryPoints;
    }

    public void setQueryPoints(int queryPoints) {
        this.queryPoints = queryPoints;
    }

    public CaseExplanation(){
        similarities = new HashMap<String, Double>();
        races = new HashMap<String, Races>();
    }

    /**
     * Used keys are:
     * <ul><li>TotalSimilarity</li>
     * <li>PlayerRaceSimilarity</li>
     * <li>OpponentRaceSimilarity</li>
     * <li>ArmyPointSimilarity</li>
     * <li>ArmySimilarity</li>
     * <li>OutcomeSimilarity</li></ul>
     * @param key
     * @param value
     */
    public void setSimilarity(String key, double value){
        similarities.put(key, value);
    }

    /**
     * Used keys are:
     * <ul><li>TotalSimilarity</li>
     * <li>PlayerRaceSimilarity</li>
     * <li>OpponentRaceSimilarity</li>
     * <li>ArmyPointSimilarity</li>
     * <li>ArmySimilarity</li>
     * <li>OutcomeSimilarity</li></ul>
     * @param key
     * @return
     */
    public double getSimilarity(String key){
        Double value = similarities.get(key);
        if(value==null)
            return 0;
        return value.doubleValue();
    }

    /**
     * Used keys are:
     * <ul><li>QueryPlayerRace</li>
     * <li>CasePlayerRace</li>
     * <li>QueryOpponentRace</li>
     * <li>CaseOpponentRace</li></ul>
     * @param key
     * @param value
     */
    public void setRace(String key, Races value){
        races.put(key, value);
    }

    /**
     * Used keys are:
     * <ul><li>QueryPlayerRace</li>
     * <li>CasePlayerRace</li>
     * <li>QueryOpponentRace</li>
     * <li>CaseOpponentRace</li></ul>
     */
    public Races getRace(String key){
        return races.get(key);
    }

    public int getCaseID() {
        return caseID;
    }

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
        double opponentSim = getSimilarity("OpponentSimilarity");
        String ret = "Case #"+caseID+" have a similarity of "+getSimilarity("TotalSimilarity")+" with the query.\n"
                + "  Based on the weighted average of three components (ArmySimilarity, OpponentSimilarity and OutcomeSimilarity):\n"
                + "    Army Similarity = "+armySim+ " and have a weigth of "+armyWeight+"\n"
                + "      Army points("+queryPoints+") in query have a similarity of "+getSimilarity("ArmyPointSimilarity")+" with the army points("+casePoints+") in the case\n"
                + "      Query player race("+getRace("QueryPlayerRace")+") have a similarity of "+getSimilarity("PlayerRaceSimilarity")+ " with the player race("+getRace("CasePlayerRace")+") in the case\n"
                + "    Opponent Similarity = "+opponentSim+ " and have a weigth of "+opponentWeigth+"\n"
                + "      Query opponent race("+getRace("QueryOpponentRace")+") have a similarity of "+getSimilarity("OpponentRaceSimilarity")+ " with the opponent race("+getRace("CaseOpponentRace")+") in the case\n"
                + "    Outcome Similarity = "+outcomeSim+" and have a weigth of "+outcomeWeigth+"\n"
                + "      Query outcome("+queryOutcome+") have a similarity of "+getSimilarity("OutcomeSimilarity")+" with the outcome("+caseOutcome+") of the case\n"
                + "  This gives the similarity calculation: \n"
                + "    simlarity = (armySimilarity*armyWeigth+opponentSimilarity*opponentWeigth+outcomeSimilarity*outcomeWeigth)/(armyWeigth+opponentWeigth*outcomeWeigth)\n"
                + "    similarity = ("+armySim+"*"+armyWeight+"+"+opponentSim+"*"+opponentWeigth+"+"+outcomeSim+"*"+outcomeWeigth+")/("+armyWeight+"+"+opponentWeigth+"+"+outcomeWeigth+")\n"
                + "    similarity = ("+(armySim*armyWeight+opponentSim*opponentWeigth+outcomeSim*outcomeWeigth)+")/"+(armyWeight+opponentWeigth+outcomeWeigth)+"\n"
                + "    similarity = "+getSimilarity("TotalSimilarity");
        return ret;
    }
}
