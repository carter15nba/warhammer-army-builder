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

/**
 * Class used to compute the local similarity Interval
 * @author Glenn Rune Strandbråten
 * @version 0.4
 */
public class Interval implements LocalSimilarityFunction{

    private double interval;

    /**
     * Default constructor
     * @param interval integer The interval to compute the Interval over.
     */
    public Interval(double interval){
        this.interval = interval;
    }

    /**
     *
     * @param caseObject The CaseObject
     * @param queryObject The QueryObject
     * @return double The computed local similarity, or 0 if caseObject and/or
     * queryObject is null
     * @throws jcolibri.exception.NoApplicableSimilarityFunctionException if caseObject
     * and/or queryObject not is an instance of Number.
     */
    public double compute(Object caseObject, Object queryObject) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof java.lang.Number)){
            System.out.println("case nan");
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        }
        if (!(queryObject instanceof java.lang.Number)){
            System.out.println("query nan");
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
        }

        Number caseNumber = (Number) caseObject;
        Number queryNumber = (Number) queryObject;
        double caseDouble = caseNumber.doubleValue();
        double queryDouble = queryNumber.doubleValue();

        //If wildcard value e.g.: I have no clue what I need
        if(queryDouble==0)
            return 1;
        //If wildcard value e.g.: I have no clue how many points I am comprised of.
        if(caseDouble==0)
            return 1;
        double simil = 1 - ((double) Math.abs(caseDouble - queryDouble) / interval);
        if(simil<0)
            return 0;
        return simil;
    }

    /**
     * Allways return true
     * @param caseObject any object
     * @param queryObject any object
     * @return true
     */
    public boolean isApplicable(Object caseObject, Object queryObject){
        return true;
    }
}