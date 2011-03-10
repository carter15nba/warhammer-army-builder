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

import org.Warhammer.Warhammer.Unit.armyType;
import org.Warhammer.Warhammer.Unit.unitType;
import jcolibri.exception.NoApplicableSimilarityFunctionException;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version
 * @deprecated Use ArmySimilarity
 */
public class UnitSimilarity implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if(caseObject==null||queryObject==null)
            return 0;
        if(!(caseObject instanceof org.Warhammer.Warhammer.Unit))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if(!(queryObject instanceof org.Warhammer.Warhammer.Unit))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

        double sim = 0;
        org.Warhammer.Warhammer.Unit cU = (org.Warhammer.Warhammer.Unit) caseObject;
        org.Warhammer.Warhammer.Unit qU = (org.Warhammer.Warhammer.Unit) queryObject;
        sim += simText(cU.getName(), qU.getName());
       // sim += simNumber(cU.getNumber(), qU.getNumber(), 5);
        //TODO FIX uncomment.
        sim += simArmyType(cU.getArmyType(), qU.getArmyType());
        sim += simUnitType(cU.getUnitType(), qU.getUnitType());

        return sim/6;
    }

    public boolean isApplicable(Object caseObject, Object queryObject) {
        return true;
    }

    private double simText(String caseObj, String queryObj) throws NoApplicableSimilarityFunctionException{
        jcolibri.method.retrieve.NNretrieval.similarity.local.Equal e =
                new jcolibri.method.retrieve.NNretrieval.similarity.local.Equal();
        return e.compute(caseObj, queryObj);
    }
    private double simNumber(Number caseObj, Number queryObj, int interval) throws NoApplicableSimilarityFunctionException{
        Interval i = new Interval(interval);
        return i.compute(caseObj, queryObj);
    }
    public double simArmyType(org.Warhammer.Warhammer.Unit.armyType caseObj, org.Warhammer.Warhammer.Unit.armyType queryObj) throws NoApplicableSimilarityFunctionException{
        jcolibri.method.retrieve.NNretrieval.similarity.local.EnumDistance ed =
                new jcolibri.method.retrieve.NNretrieval.similarity.local.EnumDistance();
        return ed.compute(caseObj, queryObj);
    }
    public double simUnitType(org.Warhammer.Warhammer.Unit.unitType caseObj, org.Warhammer.Warhammer.Unit.unitType queryObj) throws NoApplicableSimilarityFunctionException{
//        jcolibri.method.retrieve.NNretrieval.similarity.local.EnumDistance ed =
//                new jcolibri.method.retrieve.NNretrieval.similarity.local.EnumDistance();
//        return ed.compute(caseObj, queryObj);
        if(caseObj==queryObj)
            return 1;
        else if((caseObj==unitType.In && queryObj==unitType.MI)||(caseObj==unitType.MI && queryObj==unitType.In))
            return 0.3;
        return 0;
    }
}
