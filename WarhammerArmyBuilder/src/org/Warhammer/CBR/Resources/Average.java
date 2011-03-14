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

package org.Warhammer.CBR.Resources;

import java.util.Collection;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.method.retrieve.NNretrieval.similarity.StandardGlobalSimilarityFunction;
import org.Warhammer.Warhammer.Case;

/**
 * Class to compute the global average of all the local similarity functions.
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class Average extends StandardGlobalSimilarityFunction {

    private int numCalled;
    private Case.Races playerRace;
    private Case[] cases;

    /**
     * Default constructor
     * @param cases The collection of cases found in the case base
     * @param cbrq The CBR query
     */
    public Average(Collection<CBRCase> cases, CBRQuery cbrq) {
        this.cases = new Case[cases.size()];
        Case query = (Case) cbrq.getDescription();
        playerRace = query.getArmy().getPlayerRace();
        int count = 0;
        for (CBRCase _case : cases) {
            this.cases[count++] = (Case) _case.getDescription();
        }
    }

    /**
     * Method used to compute the global average of the local similarity functions.
     * @param values double array with the local similarity calculation results.
     * @param weigths double array with the weigths of the local similarity calculations.
     * @param ivalue integer with the number of elements in the arrays.
     * @return double with the global similarity value.
     */
    public double computeSimilarity(double[] values, double[] weigths, int iValue){
        double acum = 0;
        double weigthsAcum = 0;
        double weigthAdjust = 1;
        //Check if the query player race is equal to the case player race, if
        //not punish the similarity by increasing the weigths up 50%.
        if(playerRace!=cases[numCalled++].getArmy().getPlayerRace()){
            weigthAdjust = 1.5;
            //return 0;
        }
        for(int i=0; i<iValue; i++){
            acum += values[i] * weigths[i];
            weigthsAcum += weigthAdjust * weigths[i];
        }
        return acum/weigthsAcum;
    }
}
