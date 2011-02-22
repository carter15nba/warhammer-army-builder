/*
 *  Copyright (C) 2011 Glenn Rune Strandbåten
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

package org.Warhammer.Warhammer;

import org.Warhammer.Warhammer.Unit.armyType;
import java.util.ArrayList;
import java.util.Set;

//TODO FIX ENTIRE RULESET CLASS

/**
 *
 * @author Glenn Rune Strandbåten
 */
public class RuleSet {

    public enum messages{OK, FAIL, TOO_FEW_CORE_POINTS, TOO_MANY_SPECIAL_POINTS, TOO_MANY_RARE_POINTS, TOO_MANY_HERO_POINTS, TOO_MANY_LORD_POINTS, TOO_MANY_DUPLIACTE_SPECIAL_UNITS, TOO_MANY_DUPLICATE_RARE_UNITS};
    private int totalCost;
    private int coreCost;
    private int specialCost;
    private int rareCost;
    private int heroCost;
    private int lordCost;
    private int armyPoints;
    private double LIMIT_LORDS_MAX = 0.25;
    private double LIMIT_HEROES_MAX = 0.25;
    private double LIMIT_CORE_MIN = 0.25;
    private double LIMIT_SPECIAL_MAX = 0.50;
    private double LIMIT_RARE_MAX = 0.25;
    private messages[] errors;
    private int errorCount;
    private DuplicateUnits duplicate;

    public RuleSet(){
        errors = new messages[7];
        errorCount = 0;
    }

    public messages isFollowingArmyDispositionRules(Case obj, int armyPoints){
        this.armyPoints = armyPoints;
        resetCosts();
        calculatePointsUsage(obj);
        calculateTotalCost();
        return verifyLegalPointDistribution();
    }
    public void resetCosts(){
        totalCost = coreCost = specialCost = rareCost = lordCost = heroCost = 0;
    }
    public int calculateTotalCost(){
        totalCost = coreCost + specialCost + rareCost + lordCost + heroCost;
        return totalCost;
    }
    public messages verifyLegalPointDistribution(){
        double[][] dist = calculatePointDistribution();
        if(duplicate!=null)
            errorCount=duplicate.getAddedErrors();
        else
            errorCount=0;
        if(dist[0][0]>dist[1][0]){
            errors[errorCount] = messages.TOO_FEW_CORE_POINTS;
            errorCount++;
        }
        for(int i = 1 ; i < 5 ; i++){
            if(dist[0][i]<dist[1][i]){
                if(i==1)
                    errors[errorCount] = messages.TOO_MANY_SPECIAL_POINTS;
                else if(i == 2)
                    errors[errorCount] = messages.TOO_MANY_RARE_POINTS;
                else if(i == 3)
                    errors[errorCount] = messages.TOO_MANY_HERO_POINTS;
                else if(i == 4)
                    errors[errorCount] = messages.TOO_MANY_LORD_POINTS;
                errorCount++;
            }
        }
        if(errorCount==0)
            return messages.OK;
        else
            return messages.FAIL;
    }

    public double[][] calculatePointDistribution(){
        double legal[][] = new double[2][];
        legal[0] = calculateAllowedPointDistribution();
        legal[1] = new double[]{coreCost,specialCost,rareCost,heroCost,lordCost};
        return legal;
    }
    public double[] calculateAllowedPointDistribution(){
        double cCost=0;
        double sCost=0;
        double rCost=0;
        double hCost=0;
        double lCost=0;
        cCost = armyPoints*LIMIT_CORE_MIN;
        sCost = armyPoints*LIMIT_SPECIAL_MAX;
        rCost = armyPoints*LIMIT_RARE_MAX;
        hCost = armyPoints*LIMIT_HEROES_MAX;
        lCost = armyPoints*LIMIT_LORDS_MAX;
        return new double[]{cCost,sCost,rCost,hCost,lCost};
    }
    public static void main(String[] args){
        RuleSet r = new RuleSet();
        messages[] errorCauses = r.getErrorCauses();
        for (messages object : errorCauses) {
            System.out.println(object);
        }
    }
    private void calculatePointsUsage(Case caseObj){
//        duplicate = new DuplicateUnits();
//        Set<Unit> units = caseObj.getUnits();
//        for (Unit u : units) {
//            int cost = u.calculateTotalUnitCost();
//            armyType armyType = u.getArmyType();
//            switch(armyType){
//                case Core:
//                    coreCost += cost;
//                   break;
//                case Hero:
//                    heroCost += cost;
//                    break;
//                case Lord:
//                    lordCost += cost;
//                    break;
//                case Rare:
//                    rareCost += cost;
//                    duplicate.checkUnit(u);
//                    break;
//                case Special:
//                    specialCost += cost;
//                    duplicate.checkUnit(u);
//                    break;
//            }
//        }
    }
    public messages[] getErrorCauses(){
        if(errorCount==0)
            return null;
        messages ret[] = new messages[errorCount];
        System.arraycopy(errors, 0, ret, 0, errorCount);
        return ret;
    }

    private class DuplicateUnits{
        private int rareDuplicates;
        private int specialDuplicates;
        private int addedErrors;
        public DuplicateUnits(){
            addedErrors = 0;
            if(armyPoints>=3000){
                rareDuplicates = 4;
                specialDuplicates = 6;
            }
            else{
                rareDuplicates = 2;
                specialDuplicates = 3;
            }
            //TODO: Add logic to make it possible to have the desired number of regiments (not units) e.g: a greatswords regiment must have at the least 5 units, but only three such regiments may exist
        }
        public void checkUnit(Unit u){
//            armyType aT =  u.getArmyType();
//            if(aT==armyType.Rare){
//                if(u.getNumber()>rareDuplicates){
//                    System.out.println("Name:"+u.getName()+", number: "+u.getNumber());
//                    addError(messages.TOO_MANY_DUPLICATE_RARE_UNITS);
//                }
//            }
//            else if(aT==armyType.Special){
//                if(u.getNumber()>specialDuplicates){
//                    System.out.println("Name:"+u.getName()+", number: "+u.getNumber());
//                    addError(messages.TOO_MANY_DUPLIACTE_SPECIAL_UNITS);
//                }
//            }
        }
        public void addError(messages m){
//            boolean exist = false;
//            for (int i = 0; i < errorCount; i++) {
//                if(errors[i]==m){
//                    exist = true;
//                    break;
//                }
//            }
//            if(!exist){
//                errors[errorCount] = m;
//                errorCount++;
//                addedErrors++;
//            }
        }
        public int getAddedErrors(){
            return addedErrors;
        }
    }
}
