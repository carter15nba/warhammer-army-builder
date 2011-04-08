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

import jcolibri.exception.NoApplicableSimilarityFunctionException;
import myrmidia.Explanation.CaseExplanation;
import myrmidia.Explanation.ExplanationEngine;
import myrmidia.Warhammer.Army;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import myrmidia.Util.Enums.Mode;

/**
 * Class to calculate the similarity mesaure between two armies.
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class ArmySimilarity implements LocalSimilarityFunction{

    private double armyUnitWeigth;
    private double pointWeigth;
    private double accumulatedWeigth;
    private double playerRaceWeigth;
    private int interval;
    private ExplanationEngine explEngine = ExplanationEngine.getInstance();

    public ArmySimilarity(){
        armyUnitWeigth = 1;
        pointWeigth = 1;
        playerRaceWeigth = 1;
        accumulatedWeigth = 3;
        interval = 500;

    }

    public ArmySimilarity(double playerRaceWeigth, double armyUnitWeigth, double pointWeigth,int interval){
        this.armyUnitWeigth = armyUnitWeigth;
        this.pointWeigth = pointWeigth;
        this.playerRaceWeigth = playerRaceWeigth;
        this.accumulatedWeigth = this.armyUnitWeigth + this.pointWeigth + this.playerRaceWeigth;
        this.interval = interval;
    }


    /**
     * Method to compute the similarity of two Army objects.
     * @param caseObject The Army objecct found in the case.
     * @param queryObject The Army object found in the query.
     * @return double similarity value between [0..1]
     * @throws NoApplicableSimilarityFunctionException if the case object and/or
     * the query object is not an instance of the org.Warhammer.Warhammer.Army
     * class.
     */
    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if(caseObject==null||queryObject==null)
            return 0;
        if(!(caseObject instanceof Army))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if(!(queryObject instanceof Army))
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        Army caseArmy = (Army) caseObject;
        Army queryArmy = (Army) queryObject;

        //Calculate the similarity and record similarities
        double armyRaceSimil = new Equal(Mode.Player).compute(caseArmy.getPlayerRace(), queryArmy.getPlayerRace());
        double armyPointSimilarityValue = computeArmyPointSimilarity(caseArmy.getArmyPoints(), queryArmy.getArmyPoints());

        
        //Calculate the similarities of the army units available in the query/case
        ArmyUnitSimilarity armyUnitSimilarity = new ArmyUnitSimilarity();
        double armyUnitSimilarityValue = armyUnitSimilarity.compute(caseArmy.getArmyUnits(), queryArmy.getArmyUnits());

        //Calculate the army similarity
        double armySimilarity = ((armyRaceSimil*playerRaceWeigth)+(armyPointSimilarityValue*pointWeigth)+(armyUnitSimilarityValue*armyUnitWeigth))/
                accumulatedWeigth;

        //Setup the explantion component for this army(case)
        CaseExplanation caseExplanation = new CaseExplanation();
        explEngine.addCaseExplanation(caseExplanation);
        caseExplanation.setCasePoints(caseArmy.getArmyPoints());
        caseExplanation.setQueryPoints(queryArmy.getArmyPoints());
        caseExplanation.setSimilarity("ArmySimilarity", armySimilarity);
        caseExplanation.setSimilarity("ArmyPointSimilarity", armyPointSimilarityValue);
        return armySimilarity;
    }

    public double computeArmyPointSimilarity(int casePoints, int queryPoints){
        try {
            Interval armyPointsSimilarity = new Interval(interval);
            return armyPointsSimilarity.compute(casePoints, queryPoints);
        }
        catch (NoApplicableSimilarityFunctionException ex) {return 0;}
    }

    public int getInterval(){
        return interval;
    }

    /**
     * Method to check if the two objects are applicable, allways return true
     * as the check if performed in the compute method.
     * @param caseObject The case object
     * @param queryObject The query object
     * @return true
     */
    public boolean isApplicable(Object caseObject, Object queryObject) {
        return true;
    }
}