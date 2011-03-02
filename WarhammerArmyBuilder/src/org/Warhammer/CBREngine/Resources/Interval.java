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

package org.Warhammer.CBREngine.Resources;

import jcolibri.exception.NoApplicableSimilarityFunctionException;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class Interval implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

    private double interval;

    public Interval(double interval){
        this.interval = interval;
    }


    public double compute(Object caseObject, Object queryObject) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((caseObject == null) || (queryObject == null))
            return 0;
        if (!(caseObject instanceof java.lang.Number))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof java.lang.Number))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        Number caseNumber = (Number) caseObject;
        Number queryNumber = (Number) queryObject;
        double caseDouble = caseNumber.doubleValue();
        double queryDouble = queryNumber.doubleValue();

        //If wildcard value e.g.: I have no clue what I need
        //TODO: Come up with wildcard calulation
        if(queryDouble==0)
            return 1;
        //If wildcard value e.g.: I have no clue how many points I am comprised of.
        //TODO: Come up with wildcard calculation
        if(caseDouble==0)
            return 1;
        return 1 - ((double) Math.abs(caseDouble - queryDouble) / interval);
    }

    public boolean isApplicable(Object caseObject, Object queryObject)
    {
        if((caseObject==null)&&(queryObject==null))
                return true;
        else if(caseObject==null)
                return queryObject instanceof Number;
        else if(queryObject==null)
                return caseObject instanceof Number;
        else
                return (caseObject instanceof Number)&&(queryObject instanceof Number);
    }

}
