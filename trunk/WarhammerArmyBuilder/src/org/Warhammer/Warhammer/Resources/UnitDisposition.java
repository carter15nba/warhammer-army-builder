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

package org.Warhammer.Warhammer.Resources;

/**
 * Class to create the disposition rules for each race. How many lord/heros
 * special units, rare units and core units that is required/alowed based on
 * the available army point. Use one of the getRace methods.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class UnitDisposition {
    private int maxCharacters;
    private int maxHeroes;
    private int maxLords;
    private int minCore;
    private int minSpecial;
    private int maxSpecial;
    private int minRare;
    private int maxRare;

    /**
     * Default constructor, use UnitDispositionFactory to create a disposition
     * object.
     * @param maxCharacters int The max number of Lords + Heroes the army can
     * have
     * @param maxLords int The max number of Lords the army can have
     * @param maxHeroes int The max number of Heroes the army can have
     * @param minCore int The min number of core units the army must have
     * @param minSpecial int The min number of special units the army must have
     * @param maxSpecial int The max number of special units the army can have
     * @param minRare int The min number of rare units the army must have
     * @param maxRare int The max number of rare units the army can have
     */
    public UnitDisposition(int maxCharacters, int maxLords, int maxHeroes,
            int minCore, int minSpecial, int maxSpecial, int minRare, int maxRare){
        this.maxHeroes = maxHeroes;
        this.maxLords = maxLords;
        this.maxRare = maxRare;
        this.maxSpecial = maxSpecial;
        this.minCore = minCore;
        this.minRare = minRare;
        this.minSpecial = minSpecial;
        this.maxCharacters = maxCharacters;
    }

    /**
     * @return the maxCharacters
     */
    public int getMaxCharacters() {
        return maxCharacters;
    }

    /**
     * @return the maxHeroes
     */
    public int getMaxHeroes() {
        return maxHeroes;
    }

    /**
     * @return the maxLords
     */
    public int getMaxLords() {
        return maxLords;
    }

    /**
     * @return the minCore
     */
    public int getMinCore() {
        return minCore;
    }

    /**
     * @return the minSpecial
     */
    public int getMinSpecial() {
        return minSpecial;
    }

    /**
     * @return the maxSpecial
     */
    public int getMaxSpecial() {
        return maxSpecial;
    }

    /**
     * @return the minRare
     */
    public int getMinRare() {
        return minRare;
    }

    /**
     * @return the maxRare
     */
    public int getMaxRare() {
        return maxRare;
    }


    @Override
    public String toString(){
        return "Characters Lords Heroes\n"
                + maxCharacters+"          "+maxLords+"     "+maxHeroes+"\n"
                + "Core Special Rare\n"
                +minCore+"    "+minSpecial+"-"+maxSpecial+"     "+minRare+"-"+maxRare;
                
    }
}
