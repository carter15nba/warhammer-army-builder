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

package CBREngine.Resources;

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


    public double compute(Object o1, Object o2) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((o1 == null) || (o2 == null))
                return 0;
        if (!(o1 instanceof java.lang.Number))
                throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
        if (!(o2 instanceof java.lang.Number))
                throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());

        Number i1 = (Number) o1;
        Number i2 = (Number) o2;

        double v1 = i1.doubleValue();
        double v2 = i2.doubleValue();

        if(v1==-1)
            return 1 - ((double) Math.abs(v2) / interval);

        return 1 - ((double) Math.abs(v1 - v2) / interval);
    }

    public boolean isApplicable(Object o1, Object o2)
    {
        if((o1==null)&&(o2==null))
                return true;
        else if(o1==null)
                return o2 instanceof Number;
        else if(o2==null)
                return o1 instanceof Number;
        else
                return (o1 instanceof Number)&&(o2 instanceof Number);
    }

}
