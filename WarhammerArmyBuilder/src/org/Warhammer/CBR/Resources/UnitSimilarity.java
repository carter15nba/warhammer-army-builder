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

import org.Warhammer.Warhammer.Unit.armyType;
import org.Warhammer.Warhammer.Unit.unitType;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.Warhammer.Unit;

/**
 * Class to calculate the unit similarity (all characteristics)
 * @author Glenn Rune Strandbråten
 * @version 0.5
 */
public class UnitSimilarity implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

    private Unit caseUnit;
    private Unit queryUnit;
    
    public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if(caseObject==null||queryObject==null)
            return 0;
        if(!(caseObject instanceof Unit))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        if(!(queryObject instanceof Unit))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
        caseUnit = (Unit) caseObject;
        queryUnit = (Unit) queryObject;

        double[] caseValues = caseUnit.getCharacteristics();
        double[] queryValues = queryUnit.getCharacteristics();
        
        double characterisitcs = computeCharacteristicsSimilarity(caseValues, queryValues);
        double unitTypeSimilarity = computeUnitTypeSimilarity(unitType.Ca, unitType.Ca);
        double armyTypeSimilarity = computeArmyTypeSimilarity(armyType.Hero, armyType.Hero);

        double similarity = (characterisitcs+unitTypeSimilarity+armyTypeSimilarity)/3;
        return similarity;
    }
    public boolean isApplicable(Object caseObject, Object queryObject) {
        return true;
    }

    /**
     * Method that returns the average of the unit charcteristics similarity.
     * @param caseValues double[] the case unit characterisitcs
     * @param queryValues double[] the query unit characteristics.
     * @return int The average of the similarty
     * @throws NoApplicableSimilarityFunctionException
     */
    public double computeCharacteristicsSimilarity(double[] caseValues, double[] queryValues)
            throws NoApplicableSimilarityFunctionException{
        Interval characteristicsInterval = new Interval(1);
        double characterisitcs = 0 ;
        for(int i = 0 ; i < 9 ; i++){
            double caseValue = caseValues[i];
            double queryValue = queryValues[i];
            if((caseValue!=-1)||(queryValue!=-1))
                characterisitcs += characteristicsInterval.compute(caseValue, queryValue);
            else{
                jcolibri.method.retrieve.NNretrieval.similarity.local.Equal equal 
                        = new jcolibri.method.retrieve.NNretrieval.similarity.local.Equal();
                characterisitcs +=
                        equal.compute(caseUnit.getCharactersitic(i), queryUnit.getCharactersitic(i));
            }
        }
        return characterisitcs/9;
    }

    public double computeUnitTypeSimilarity(unitType caseType, unitType queryType){

        return 0;
    }

    public double computeArmyTypeSimilarity(armyType caseArmy, armyType queryArmy){

        return 0;
    }


}
