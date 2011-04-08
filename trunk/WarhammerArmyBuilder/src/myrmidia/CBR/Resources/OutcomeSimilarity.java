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
import myrmidia.Util.Enums.Outcomes;


/**
 * This function returns the similarity of two enum values as the their distance
 * sim(x,y)=|ord(x) - ord(y)|
 *
 * @author Juan A. Recio-García
 */
public class OutcomeSimilarity implements LocalSimilarityFunction {

    /**
     * Applies the similarity function.
     *
     * @param caseOutcome StringEnum or String
     * @param queryOutcome StringEnum or String
     * @return the result of apply the similarity function.
     */
    public double compute(Object caseOutcome, Object queryOutcome) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((caseOutcome == null) || (queryOutcome == null))
            return 0;
        if(!(caseOutcome instanceof Outcomes))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseOutcome.getClass());
        if(!(queryOutcome instanceof Outcomes))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryOutcome.getClass());
        Outcomes caseEnum = (Outcomes) caseOutcome;
        Outcomes queryEnum = (Outcomes) queryOutcome;
        double value=0;
        if(caseEnum==queryEnum)
            value=1;
        else if(caseEnum==Outcomes.Defeat)
            value = 0;
        else if(caseEnum==Outcomes.Draw&&queryEnum==Outcomes.Victory||
                caseEnum==Outcomes.Victory&&queryEnum==Outcomes.Draw)
            value = 0.5;
        ExplanationEngine explEngine = ExplanationEngine.getInstance();
        CaseExplanation caseExpl = explEngine.getCurrentCaseExplanation();
        caseExpl.setSimilarity("OutcomeSimilarity", value);
        caseExpl.setCaseOutcome(caseEnum);
        caseExpl.setQueryOutcome(queryEnum);
        return value;
    }

    /** Applicable to Enum */
    public boolean isApplicable(Object o1, Object o2) {
        return true;
    }

}

