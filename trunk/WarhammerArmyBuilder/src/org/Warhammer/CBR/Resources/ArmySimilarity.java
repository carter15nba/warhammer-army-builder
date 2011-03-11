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

import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.Warhammer.Army;

/**
 * Class to calculate the similarity mesaure between two armies.
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class ArmySimilarity implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

    /**
     * Method to compute the similarity of two Army objects.
     * @param caseObject The Army objecct found in the case.
     * @param queryObject The Army object found in the query.
     * @return double with the similarity of the two army objects or 0 if no
     * similarity were found
     * @throws NoApplicableSimilarityFunctionException if the case object and/or
     * the query object is not an instance of the org.Warhammer.Warhammer.Army
     * class.
     */
    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if(caseObject==null||queryObject==null)
            return 0;
        if(!(caseObject instanceof Army))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if(!(queryObject instanceof Army))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
         Army caseArmy = (Army) caseObject;
         Army queryArmy = (Army) queryObject;

        if(caseArmy.getPlayerRace()!=queryArmy.getPlayerRace()){
             return 0;
        }
        if(queryArmy.getArmyUnits()!=null){
            ArmyUnitSimilarity armyUnitSimilarity = new ArmyUnitSimilarity();
            return armyUnitSimilarity.compute(caseArmy.getArmyUnits(), queryArmy.getArmyUnits());
        }
        return 0;
    }

    /**
     * Method to check if the two objects are applicable, allways return true
     * as the check if performed in the compute method.
     * @param caseObject The case object
     * @param queryObject The query object
     * @return true
     */
    public boolean isApplicable(Object caseObject, Object queryObject) {
        return true;
    }


}
