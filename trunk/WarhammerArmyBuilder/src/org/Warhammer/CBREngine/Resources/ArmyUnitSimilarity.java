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

import java.util.Iterator;
import java.util.Set;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.Warhammer.ArmyUnit;
import org.Warhammer.Warhammer.Unit;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class ArmyUnitSimilarity implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

    private int foundUnits;
    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
         if ((caseObject == null) || (queryObject == null))
            return 0;
         if (!(caseObject instanceof Set))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if (!(queryObject instanceof Set))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
        Set caseSet = (Set<ArmyUnit>) caseObject;
        Set querySet = (Set<ArmyUnit>) queryObject;

        foundUnits=0;
        Iterator it = querySet.iterator();
        double numberSim = 0;
        while(it.hasNext()){
            ArmyUnit queryArmyUnit = (ArmyUnit) it.next();
            Unit queryUnit = queryArmyUnit.getUnitName();
            ArmyUnit caseArmyUnit = (ArmyUnit) findSimilar(caseSet, queryUnit);            
            if(caseArmyUnit==null){
                numberSim-=1;
                continue;
            }
            Interval interval = new Interval(10);
            numberSim += interval.compute(caseArmyUnit.getNumberOfUnits(), queryArmyUnit.getNumberOfUnits());
        }
        if(foundUnits==0)
            return numberSim;
        else
            return numberSim/foundUnits;
    }

    public boolean isApplicable(Object caseObject, Object queryObject) {
        return true;
    }
    private ArmyUnit findSimilar(Set<ArmyUnit> caseObject, Unit queryObject){
        Iterator it = caseObject.iterator();
        while(it.hasNext()){
            ArmyUnit caseArmyUnit = (ArmyUnit) it.next();
            Unit caseUnit = caseArmyUnit.getUnitName();
            if(caseUnit.getName().contentEquals(queryObject.getName())){
                foundUnits++;
                return caseArmyUnit;
            }

        }
        return null;
    }

}
