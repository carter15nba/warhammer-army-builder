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

import java.util.ArrayList;
import org.Warhammer.Warhammer.Unit.armyType;
import java.util.Set;
import org.Warhammer.Database.DatabaseManager;
import org.Warhammer.Warhammer.Resources.ArmyDisposition;
import org.Warhammer.Warhammer.Resources.Causes;
import org.Warhammer.Warhammer.Resources.ErrorManager;
import org.Warhammer.Warhammer.Resources.ErrorManager.Messages;
import org.Warhammer.Warhammer.Resources.UnitDispositionFactory;

//TODO: Assign utility unit number if units == 0, to mean that all units in the group gets the promotion at points/model
/**
 * Class to verify if the rules governing the creation of an army are adhered
 * @author Glenn Rune Strandbåten
 * @version 0.5
 */
public class RuleSet {

    private int totalCost;
    private int coreCost;
    private int specialCost;
    private int rareCost;
    private int heroCost;
    private int lordCost;
    private int armyPoints;
    private int threshold;
    private int rareDuplicates;
    private int specialDuplicates;
    private double LIMIT_LORDS_MAX = 0.25;
    private double LIMIT_HEROES_MAX = 0.25;
    private double LIMIT_CORE_MIN = 0.25;
    private double LIMIT_SPECIAL_MAX = 0.50;
    private double LIMIT_RARE_MAX = 0.25;

    private ArrayList<String> unitNames;
    private ArrayList<Integer> unitNumber;
    private ErrorManager errorManager;
    private ArmyDisposition armyDisposition;

    /**
     * Default constructor (sets threshold value of 25)
     */
    public RuleSet(){
        threshold = 25;
        errorManager = new ErrorManager();
        armyDisposition = new ArmyDisposition(errorManager);
        unitNames = new ArrayList<String>();
        unitNumber = new ArrayList<Integer>();
    }
    /**
     * Constructor
     * @param threshold int The threshold value
     */
    public RuleSet(int threshold){
        this.threshold = threshold;
        errorManager = new ErrorManager();
        armyDisposition = new ArmyDisposition(errorManager);
    }

    /**
     * Method that initializes (resets) the resources which is required to check
     * if an army is following its disposition rules.
     * @param army
     */
    private void initResources(Army army){
        if(armyPoints==0){
            this.armyPoints = army.calculateCost();
        }
        else
            this.armyPoints = army.getArmyPoints();
        errorManager.resetErrors();
        resetCosts();
        calculateNumberOfDuplicates();
        unitNames.clear();
        unitNumber.clear();
    }

    /**
     * This method is verifies that the created army follows the army
     * disposition rules, e.g.: that no more than the allowed number of
     * points are used on lords/heroes and core units etc. The method
     * returns an array containing all (if any) error that have been found
     * during the verification. If no errors is found an array with a single OK
     * message is returned.
     * @param army The Army object to be verified
     * @return Messages[] array with all the errors found in the army disposition,
     * single entry array with an OK message if no errors where found.
     */
    public Messages[] isFollowingArmyDispositionRules(Army army){
        initResources(army);
        calculatePointsUsage(army);
        calculateTotalCost();
        verifyLegalPointDistribution(); 
        boolean b = armyDisposition.isFollowingDispositionRules(army,
                UnitDispositionFactory.getRaceArmy(army.getPlayerRace(), armyPoints));
        if(b){
            System.out.println("+--------------");
            System.out.println(toString());
            System.out.println("+--------------");

        }
        if(totalCost>armyPoints)
            errorManager.addError(Messages.TOO_MANY_POINTS_TOTAL);
        else if(totalCost<(armyPoints-threshold))
            errorManager.addError(Messages.TOO_FEW_POINTS_TOTAL);
        return getErrors();
    }

    /**
     * Method to reset all the individual- and total cost values back to 0.
     */
    public void resetCosts(){
        totalCost = coreCost = specialCost = rareCost = lordCost = heroCost = 0;
    }

    /**
     * Method to calculate the different unit group costs. The total
     * cost is both stored in the object and returned to the caller.
     * @return Integer with the total cost.
     */
    public int calculateTotalCost(){
        totalCost = coreCost + specialCost + rareCost + lordCost + heroCost;
        return totalCost;
    }

    /**
     * Method to verify if any of the groups have used too much/few points
     * when creating the army. If any group violates the rules an appropriate
     * error message is added to the error list.
     */
    public void verifyLegalPointDistribution(){
        double[][] dist = calculatePointDistribution();
        if(dist[0][0]>dist[1][0]){
            errorManager.addError(Messages.TOO_FEW_CORE_POINTS);
        }
        for(int i = 1 ; i < 5 ; i++){
            if(dist[0][i]<dist[1][i]){
                if(i==1)
                    errorManager.addError(Messages.TOO_MANY_SPECIAL_POINTS);
                else if(i == 2)
                    errorManager.addError(Messages.TOO_MANY_RARE_POINTS);
                else if(i == 3)
                    errorManager.addError(Messages.TOO_MANY_HERO_POINTS);
                else if(i == 4)
                    errorManager.addError(Messages.TOO_MANY_LORD_POINTS);
            }
        }
    }

    /**
     * Method to aquire the point distributions and point usage. This method returns
     * the following matrix:
     * <table border="1">
     * <tr>
     * <td>Min core points</td>
     * <td>Max special points</td>
     * <td>Max rare points</td>
     * <td>Max hero points</td>
     * <td>Max lord points</td>
     * </tr>
     * <tr>
     * <td>Used core points</td>
     * <td>Used special points</td>
     * <td>Used rare points</td>
     * <td>Used hero points</td>
     * <td>Used lord points</td>
     * </tr>
     * </table>
     * @return The above matrix represented as double values.
     */
    public double[][] calculatePointDistribution(){
        double legal[][] = new double[2][];
        legal[0] = calculateAllowedPointDistribution();
        legal[1] = new double[]{coreCost,specialCost,rareCost,heroCost,lordCost};
        return legal;
    }

    /**
     * This method calculates the allowed points distribution based on the
     * available army points. The method returns the following double list;
     * <table border="1">
     * <tr>
     * <td>Min core points</td>
     * <td>Max special points</td>
     * <td>Max rare points</td>
     * <td>Max hero points</td>
     * <td>Max lord points</td>
     * </tr>
     * </table>
     * @return The abowe double list.
     */
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

    /**
     * Method to calculate the points used by the different groups present
     * in the army.
     * @param army The Army to be calculated.
     */
    private void calculatePointsUsage(Army army){
        Set<ArmyUnit> armyUnits = army.getArmyUnits();
        for (ArmyUnit u : armyUnits) {
            int cost = army.calculateTotalUnitCost(u);
            Unit unit = u.getUnit();
            armyType armyType = unit.getArmyType();
            switch(armyType){
                case Core:
                    coreCost += cost;
                   break;
                case Hero:
                    heroCost += cost;
                    break;
                case Lord:
                    lordCost += cost;
                    break;
                case Rare:
                    rareCost += cost;
                    break;
                case Special:
                    specialCost += cost;
                    break;
            }
            checkUnit(unit,u.getNumberOfUnits());
        }
    }

    /**
     * Method to aquire the errors encountered while verifying the case.
     *
     * @return array containg all the encoutered errors or a single element array
     * with an OK message if no errors were found.
     */
    public Messages[] getErrors(){
        return errorManager.getErrors();
    }

    /**
     * Method to aquire the causes for the errors encountered while verifying the case.
     * The case objects holds the error message and the unit that caused the error message,
     * and these causes should be resolved before any errors regarding the points usage are
     * resolved since these may indirectly affect the points usage.
     * @return an arry with the cause objects or null if no causes were found.
     */
    public Causes[] getCauses(){
        return errorManager.getCauses();
    }

    /**
     * This method calculates the amount of rare and special unit duplicates
     * that can be present on the battlefield. Based on the the available
     * army points set in the main object (outer class).
     */
    public final void calculateNumberOfDuplicates(){
        if(armyPoints>=3000){
            rareDuplicates = 4;
            specialDuplicates = 6;
        }
        else{
            rareDuplicates = 2;
            specialDuplicates = 3;
        }
    }

    /**
     * Method used to check if the unit follows the disposition rules. This
     * includes if it have too few or too many units in the unit group and
     * if the special and rare duplicates are within bounds.  This method
     * adds appropriate errors to the error list when errors are found.
     * @param unit The unit to be checked
     * @param numberOfUnits The number of units in the group.
     */
    public void checkUnit(Unit unit, int numberOfUnits){
        armyType aT =  unit.getArmyType();
        int min = unit.getMinNumber();
        int max = unit.getMaxNumber();
        if((numberOfUnits>max)&&(max!=0)){
            errorManager.addError(Messages.TOO_MANY_UNITS_IN_GROUP);
            errorManager.addCause(new Causes(unit, Messages.TOO_MANY_UNITS_IN_GROUP));
        }
        if(numberOfUnits<min){
            errorManager.addError(Messages.TOO_FEW_UNITS_IN_GROUP);
            errorManager.addCause(new Causes(unit, Messages.TOO_FEW_UNITS_IN_GROUP));
        }
        if(aT==armyType.Rare||aT==armyType.Special){
            boolean contains = unitNames.contains(unit.getName());
            if(contains){
                int idx = unitNames.indexOf(unit.getName());
                unitNumber.set(idx, (unitNumber.get(idx)+1));
                verifyDuplicateUnits(unit,idx);
            }
            else{
                unitNames.add(unit.getName());
                unitNumber.add(1);
            }
        }
    }
    /**
     * This private method is used by the checkUnit method to verify if
     * no more than the alloted number of rare and duplicate units are
     * used in the creation of the army.
     * @param unit The unit to check
     * @param index The index in the list where the number of unit groups
     * resides.
     */
    private void verifyDuplicateUnits(Unit unit,int index){
        armyType aT =  unit.getArmyType();
        int number = unitNumber.get(index);
        switch(aT){
            case Special:
                if(number>specialDuplicates){
                    errorManager.addError(Messages.TOO_MANY_DUPLIACTE_SPECIAL_UNITS);
                    errorManager.addCause(new Causes(unit, Messages.TOO_MANY_DUPLIACTE_SPECIAL_UNITS));
                }
                break;
            case Rare:
                if(number>rareDuplicates){
                    errorManager.addError(Messages.TOO_MANY_DUPLICATE_RARE_UNITS);
                    errorManager.addCause(new Causes(unit, Messages.TOO_MANY_DUPLICATE_RARE_UNITS));
                }
                break;
        }
    }
    @Override
    public String toString(){
        //TODO: complete toString()
        return armyDisposition.toString();
    }
}
