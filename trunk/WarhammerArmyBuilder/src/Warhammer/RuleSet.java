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

package Warhammer;

/**
 *
 * @author Glenn Rune Strandbåten
 */
public class RuleSet {

    private int totalCost;
    private int coreCost;
    private int specialCost;
    private int rareCost;
    private int heroCost;
    private int lordCost;
    private double LIMIT_LORDS = 0.25;
    private double LIMIT_HEROES = 0.25;
    private double LIMIT_CORE = 0.50;
    private double LIMIT_SPECIAL = 0.50;
    private double LIMIT_RARE = 0.25;
    public boolean followArmyDispositionRules(){
        resetCosts();
        return false;
    }
    public void resetCosts(){
        coreCost = specialCost = rareCost = lordCost = heroCost = 0;
        calculateTotalCost();
    }
    public void calculateTotalCost(){
        totalCost = coreCost + specialCost + rareCost + lordCost + heroCost;
    }
}
