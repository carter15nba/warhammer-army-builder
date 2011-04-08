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

import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import myrmidia.Explanation.CaseExplanation;
import myrmidia.Explanation.ExplanationEngine;
import myrmidia.Util.Enums.Mode;
import myrmidia.Util.Enums.Races;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */

/**
 * This function returns 1 if both individuals are equal, otherwise returns 0
 */
public class Equal implements LocalSimilarityFunction {

    private Mode mode;
    public Equal(Mode m){
        mode = m;
    }
    /**
     * Applies the similarity function.
     * @param o1 Object.
     * @param o2 Object.
     * @return the result of apply the similarity function.
     */
    public double compute(Object o1, Object o2) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((o1 == null) || (o2 == null)){
            return 0;
        }
        double value = o1.equals(o2) ? 1 : 0;
        ExplanationEngine explEngine = ExplanationEngine.getInstance();
        CaseExplanation caseExpl= explEngine.getCurrentCaseExplanation();
        switch(mode){
            case Opponent:
                caseExpl.setRace("QueryOpponentRace", (Races)o2);
                caseExpl.setRace("CaseOpponentRace", (Races)o1);
                caseExpl.setSimilarity("OpponentRaceSimilarity", value);
                break;
            case Player:
                caseExpl.setRace("CasePlayerRace", (Races)o2);
                caseExpl.setRace("QueryPlayerRace", (Races)o1);
                caseExpl.setSimilarity("PlayerRaceSimilarity", value);
                break;
        }
        return value;
    }

    /** 
     * Applicable to any class
     */
    public boolean isApplicable(Object o1, Object o2) {
        return true;
    }
}
