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

import myrmidia.Warhammer.Unit.armyType;
import myrmidia.Warhammer.Unit.unitType;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Threshold;
import myrmidia.Warhammer.Unit;
import myrmidia.Warhammer.Unit.weaponType;

/**
 * Class to calculate the unit similarity (all characteristics)
 * Used during adaptation.
 * @author Glenn Rune Strandbråten
 * @version 0.5
 */
public class UnitSimilarity implements jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction{

    private Unit caseUnit;
    private Unit queryUnit;
    private double characteristicsWeigth;
    private double unitTypeWeigth;
    private double armyTypeWeigth;
    private double costWeigth;
    private double weaponWeigth;
    private double totalWeigth;
    private double magicWeigth;

    /**
     * Default constructor sets all similarity weigths to 1
     */
    public UnitSimilarity(){
        characteristicsWeigth = 1;
        unitTypeWeigth = 1;
        armyTypeWeigth = 1;
        costWeigth = 1;
        weaponWeigth = 1;
        magicWeigth = 1;
        totalWeigth = characteristicsWeigth + unitTypeWeigth + armyTypeWeigth + 
                costWeigth + weaponWeigth + magicWeigth;
    }

    /**
     * Constructor sets all the similarity weigths as requested
     * All weigths must be in the range 0 to 1 [0,1], all weigths outside this
     * range is automatically adjusted.
     * @param charWeigth The weigth of the characteristics similarity
     * @param unitTypeWeigth The weigth of the unit type similarity
     * @param armyTypeWeigth The weigth of the army type similarity
     * @param costWeigth The weigth of the cost similarity
     * @param weaponWeigth The weigth of the weapon similarity
     * @param magicWeight The weight of the magic similarity
     */
    public UnitSimilarity(double charWeigth, double unitTypeWeigth, 
            double armyTypeWeigth, double costWeigth,double weaponWeigth,
            double magicWeigth){
        this.characteristicsWeigth = (charWeigth < 0) ? 0 : charWeigth;
        this.characteristicsWeigth = (charWeigth > 1) ? 1 : charWeigth;
        this.armyTypeWeigth = (unitTypeWeigth < 0) ? 0 : unitTypeWeigth;
        this.armyTypeWeigth = (unitTypeWeigth > 1) ? 1 : unitTypeWeigth;
        this.costWeigth = (costWeigth < 0) ? 0 : costWeigth;
        this.costWeigth = (costWeigth > 1) ? 1 : costWeigth;
        this.unitTypeWeigth = (unitTypeWeigth < 0) ? 0 : unitTypeWeigth;
        this.unitTypeWeigth = (unitTypeWeigth > 1) ? 1 : unitTypeWeigth;
        this.weaponWeigth = (weaponWeigth < 0) ? 0 : weaponWeigth;
        this.weaponWeigth = (weaponWeigth > 1) ? 1 : weaponWeigth;
        this.magicWeigth = (magicWeigth < 0) ? 0 : magicWeigth;
        this.magicWeigth = (magicWeigth > 1) ? 1 : magicWeigth;
        totalWeigth = characteristicsWeigth + this.unitTypeWeigth +
                this.armyTypeWeigth + this.costWeigth + this.weaponWeigth +
                this.magicWeigth;
    }

    /**
     * Computes the similarity between the two supplied units. This similarity
     * is based on the: unit characteristics, army type and unit type.
     * @param caseObject Unit The case unit
     * @param queryObject Unit The query unit
     * @return double in the range of: [0:1];
     * @throws NoApplicableSimilarityFunctionException if the supplied objects
     * not are an instance of org.Warhammer.Warhammer.Unit.class
     */
    public double compute(Object caseObject, Object queryObject)
            throws NoApplicableSimilarityFunctionException {
        if(caseObject==null||queryObject==null)
            return 0;
        if(!(caseObject instanceof Unit))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(
                    this.getClass(), caseObject.getClass());
        if(!(queryObject instanceof Unit))
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(
                    this.getClass(), queryObject.getClass());
        caseUnit = (Unit) caseObject;
        queryUnit = (Unit) queryObject;

        double[] caseValues = caseUnit.getCharacteristics();
        double[] queryValues = queryUnit.getCharacteristics();

        Threshold costSimilarity = new Threshold(1);

        double characterisitcs = computeCharacteristicsSimilarity(caseValues, queryValues);
        double unitTypeSimilarity = computeUnitTypeSimilarity(caseUnit.getUnitType(),queryUnit.getUnitType());
        double armyTypeSimilarity = computeArmyTypeSimilarity(caseUnit.getArmyType(), queryUnit.getArmyType());
        double costSimilarityValue = costSimilarity.compute(caseUnit.getCost(), queryUnit.getCost());
        double weaponSimilarity = computeWeaponSimilarity(caseUnit.getWeaponType(), queryUnit.getWeaponType());
        double magicSimilarity=0;
        if(caseUnit.isMagician()==queryUnit.isMagician())
            magicSimilarity = 1;
        double similarity = ((characterisitcs*characteristicsWeigth)+
                (unitTypeSimilarity*unitTypeWeigth)+
                (armyTypeSimilarity*armyTypeWeigth)+
                (costSimilarityValue*costWeigth)+
                (weaponSimilarity*weaponWeigth)+
                (magicSimilarity*magicWeigth))/
                (totalWeigth);
        return similarity;
    }
    
    /**
     * Allways return true
     * @param caseObject Any object
     * @param queryObject Any object
     * @return true
     */
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
    private double computeCharacteristicsSimilarity(double[] caseValues, double[] queryValues)
            throws NoApplicableSimilarityFunctionException{
        Threshold characteristicsInterval = new Threshold(2); //TODO: User selected threshold value?
        double characterisitcs = 0 ;
        for(int i = 0 ; i < 9 ; i++){
            double caseValue = caseValues[i];
            double queryValue = queryValues[i];
            if((caseValue!=-1)||(queryValue!=-1))
                characterisitcs += characteristicsInterval.compute((int)caseValue, (int)queryValue);
            else{
                jcolibri.method.retrieve.NNretrieval.similarity.local.Equal equal 
                        = new jcolibri.method.retrieve.NNretrieval.similarity.local.Equal();
                characterisitcs +=
                        equal.compute(caseUnit.getCharactersitic(i), queryUnit.getCharactersitic(i));
            }
        }
        return characterisitcs/9;
    }

    /**
     * Method that return the similarity between unit type. (if applicable).
     * @param caseType unitType The unit type of the case unit.
     * @param queryType unitType The unit type of the query unit.
     * @return double in the range: [0:1]
     */
    private double computeUnitTypeSimilarity(unitType caseType, unitType queryType){
        if(caseType==queryType)
            return 1;
        if(caseType==unitType.Ca){
            if(queryType==unitType.Ch)
                return 0.75;
            if(queryType==unitType.MC)
                return 0.65;
        }
        if(caseType==unitType.Ch){
            if(queryType==unitType.Ca)
                return 0.75;
            if(queryType==unitType.MC)
                return 0.50;
        }
        if(caseType==unitType.In){
            if(queryType==unitType.MI)
                return 0.65;
        }
        if(caseType==unitType.MB){
            if(queryType==unitType.MC)
                return 0.5;
            if(queryType==unitType.MI)
                return 0.65;
            if(queryType==unitType.Mo)
                return 0.8;
        }
        if(caseType==unitType.MC){
            if(queryType==unitType.Ca)
                return 0.65;
            if(queryType==unitType.Ch)
                return 0.5;
            if(queryType==unitType.MB)
                return 0.5;
            if(queryType==unitType.MI)
                return 0.5;
            if(queryType==unitType.Mo)
                return 0.8;
        }
        if(caseType==unitType.MI){
            if(queryType==unitType.In)
                return 0.65;
            if(queryType==unitType.MB)
                return 0.5;
            if(queryType==unitType.MC)
                return 0.5;
            if(queryType==unitType.Mo)
                return 0.8;
        }
        if(caseType==unitType.Mo){
            if(queryType==unitType.MB)
                return 0.8;
            if(queryType==unitType.MC)
                return 0.8;
            if(queryType==unitType.MI)
                return 0.8;
        }
        if(caseType==unitType.Un){
            if(queryType==unitType.WB)
                return 0.45;
            if(queryType==unitType.WM)
                return 0.45;
        }
        if(caseType==unitType.WB){
            if(queryType==unitType.Un)
                return 0.45;
            if(queryType==unitType.WM)
                return 0.35;
        }
        if(caseType==unitType.WM){
            if(queryType==unitType.Un)
                return 0.45;
            if(queryType==unitType.WB)
                return 0.35;
        }
        return 0;
    }

    /**
     * Method that return the similarity between army type. (if applicable).
     * @param caseArmy armyType The army type of the case unit.
     * @param queryArmy armyType The amry type of the query unit.
     * @return double in the range: [0:1]
     */
    private double computeArmyTypeSimilarity(armyType caseArmy, armyType queryArmy){
        if(caseArmy==queryArmy)
            return 1;
        if(caseArmy==armyType.Lord||queryArmy==armyType.Lord){
            if(caseArmy==armyType.Hero)
                return 0.75;
            if(queryArmy==armyType.Hero)
                return 0.75;
        }
        if(caseArmy==armyType.Hero||queryArmy==armyType.Hero){
            if(caseArmy==armyType.Lord)
                return 0.75;
            if(queryArmy==armyType.Lord)
                return 0.75;
        }
        if(caseArmy==armyType.Rare||queryArmy==armyType.Rare){
            if(caseArmy==armyType.Special)
                return 0.50;
            if(queryArmy==armyType.Special)
                return 0.50;
        }
        if(caseArmy==armyType.Special||queryArmy==armyType.Special){
            if(caseArmy==armyType.Rare)
                return 0.50;
            if(queryArmy==armyType.Rare)
                return 0.50;
        }
        return 0;
    }

    /**
     * Method that return the similarity between weapon type. (if applicable).
     * @param caseWeapon weaponType The weapon type of the case unit.
     * @param queryWeapon weaponType The weapon type of the query unit.
     * @return double in the range: [0:1]
     */
    private double computeWeaponSimilarity(weaponType caseWeapon, weaponType queryWeapon){
        if(caseWeapon==queryWeapon)
            return 1;
        if(caseWeapon==weaponType.Mele){
            if(queryWeapon==weaponType.Great_weapon)
                return 0.5;
            if(queryWeapon==weaponType.Long_weapon)
                return 0.3;
        }
        if(caseWeapon==weaponType.Great_weapon){
            if(queryWeapon==weaponType.Mele)
                return 0.5;
            if(queryWeapon==weaponType.Long_weapon)
                return 0.55;
        }
        if(caseWeapon==weaponType.Long_weapon){
            if(queryWeapon==weaponType.Mele)
                return 0.3;
            if(queryWeapon==weaponType.Great_weapon)
                return 0.55;
        }
        return 0;
    }
}