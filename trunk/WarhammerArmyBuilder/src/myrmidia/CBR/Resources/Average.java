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

import java.util.Collection;
import jcolibri.cbrcore.CBRCase;
import jcolibri.method.retrieve.NNretrieval.similarity.StandardGlobalSimilarityFunction;
import myrmidia.Explanation.CaseExplanation;
import myrmidia.Explanation.ExplanationEngine;
import myrmidia.Warhammer.Case;

/**
 * Class to compute the global average of all the local similarity functions.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class Average extends StandardGlobalSimilarityFunction {

    private int numCalled;
    private Case[] cases;
    private ExplanationEngine explEngine;

    /**
     * Default constructor
     * @param cases The collection of cases found in the case base
     */
    public Average(Collection<CBRCase> cases) {
        this.explEngine = ExplanationEngine.getInstance();
        this.cases = new Case[cases.size()];
        int count = 0;
        numCalled = 0;
        for (CBRCase _case : cases) {
            Case desc = (Case) _case.getDescription();
            this.cases[count++] = desc;
        }
    }

    /**
     * Method used to compute the global average of the local similarity functions.
     * @param values double array with the local similarity calculation results.
     * @param weigths double array with the weigths of the local similarity calculations.
     * @param iValue integer with the number of elements in the arrays.
     * @return double with the global similarity value.
     */
    public double computeSimilarity(double[] values, double[] weigths, int iValue){
        double acum = 0;
        double weigthsAcum = 0;
        for(int i=0; i<iValue; i++){
            acum += values[i] * weigths[i];
            weigthsAcum += weigths[i];
        }
        double weigthedAverage = acum/weigthsAcum;
        CaseExplanation caseExpl = explEngine.getCurrentCaseExplanation();
        caseExpl.setCaseID(cases[numCalled].getID());
        caseExpl.setSimilarity("TotalSimilarity", weigthedAverage);
        numCalled++;
        return weigthedAverage;
    }
}