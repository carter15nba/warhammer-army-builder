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
import org.Warhammer.Warhammer.Army;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class ArmySimilarity implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
         if(caseObject==null||queryObject==null)
            return 0;
        if(!(caseObject instanceof Army))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if(!(queryObject instanceof Army))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
         Army caseArmy = (Army) caseObject;
         Army queryArmy = (Army) queryObject;

         if(caseArmy.getPlayerRace()!=queryArmy.getPlayerRace())
             return 0;
//         Interval interval = new Interval(500);
//         double pointSim = interval.compute(caseArmy.getArmyPoints(), queryArmy.getArmyPoints());
         if(queryArmy.getArmyUnits()!=null){
             ArmyUnitSimilarity armyUnitSimilarity = new ArmyUnitSimilarity();
             double unitSim = armyUnitSimilarity.compute(caseArmy.getArmyUnits(), queryArmy.getArmyUnits());
             System.out.println("unit sim: "+unitSim);
             return unitSim;
             //Average of the army point similarity and the unit similarity
//             pointSim = (pointSim+unitSim)/2;
         }
//         return pointSim;
            return 0;
    }

    public boolean isApplicable(Object caseObject, Object queryObject) {
        return true;
    }


}
