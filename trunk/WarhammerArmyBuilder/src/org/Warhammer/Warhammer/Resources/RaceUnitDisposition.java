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

import org.Warhammer.Warhammer.Case.Races;

/**
 * Class to create the disposition rules for each race. How many lord/heros
 * special units, rare units and core units that is required/alowed based on
 * the available army point. Use one of the getRace methods.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class RaceUnitDisposition {
    private int maxCharacters;
    private int maxHeroes;
    private int maxLords;
    private int minCore;
    private int minSpecial;
    private int maxSpecial;
    private int minRare;
    private int maxRare;

    /**
     * Default constructor, must be triggered through one of the getRace methods.
     * @param maxCharacters int The max number of Lords + Heroes the army can have
     * @param maxLords int The max number of Lords the army can have
     * @param maxHeroes int The max number of Heroes the army can have
     * @param minCore int The min number of core units the army must have
     * @param minSpecial int The min number of special units the army must have
     * @param maxSpecial int The max number of special units the army can have
     * @param minRare int The min number of rare units the army must have
     * @param maxRare int The max number of rare units the army can have
     */
    private RaceUnitDisposition(int maxCharacters, int maxLords, int maxHeroes,
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
     * Method that creates a great army (5000 points or more)
     * @param incrementCharacters int The number that the characters increment
     * with per 1000 additional points
     * @param incrementLords int The number the lords increment with per 1000
     * additional points
     * @param incrementHeroes int The number the heroes increment with per 1000
     * additional points
     * @param incrementCore int The number the core units increment with per
     * 1000 additional points
     * @param incrementSpecialMin int The number the special min units increment
     * with per 1000 additional points
     * @param incrementSpecialMax int The number the special max units increment
     * with per 1000 additional points
     * @param incrementRareMin int The number the rare min units increment with
     * per 1000 additional points
     * @param incrementRareMax int The number the rare max units increment with
     * per 1000 additional points
     * @param race Race The race of the army
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition greatArmy(int incrementCharacters,
            int incrementLords, int incrementHeroes, int incrementCore,
            int incrementSpecialMin, int incrementSpecialMax,
            int incrementRareMin, int incrementRareMax, Races race, int armyPoints){
        RaceUnitDisposition _return = getRaceArmy(race,4000);
        int multiplier = 0;
        int nextPointIterator=4000;
        do{
            multiplier++;
            nextPointIterator += 1000;
        }
        while(nextPointIterator<armyPoints);
        _return.maxCharacters += incrementCharacters*multiplier;
        _return.maxHeroes += incrementHeroes*multiplier;
        _return.maxLords += incrementLords*multiplier;
        _return.minCore += incrementCore*multiplier;
        _return.maxRare += incrementRareMax*multiplier;
        _return.minRare += incrementRareMin*multiplier;
        _return.maxSpecial += incrementSpecialMax*multiplier;
        _return.minSpecial += incrementSpecialMin*multiplier;
        return _return;
    }

    /**
     * Method to aquire the requested army dispositon for the specified points
     * @param race Race The race of the army to get the disposition for
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    public RaceUnitDisposition getRaceArmy(Races race,int armyPoints){
        RaceUnitDisposition _return = null;
        switch(race){
            case Beastmen:
                _return = getBeastmen(armyPoints);
                break;
            case Bretonnia:
                _return = getBretonnia(armyPoints);
                break;
            case Daemons_of_Chaos:
                _return = getDaemonsOfChaos(armyPoints);
                break;
            case Dark_Elves:
                _return = getDarkElves(armyPoints);
                break;
            case Dwarfs:
                _return = getDwarfs(armyPoints);
                break;
            case Empire:
                _return = getEmpire(armyPoints);
                break;
            case High_Elves:
                _return = getHighElves(armyPoints);
                break;
            case Lizardmen:
                _return = getLizardmen(armyPoints);
                break;
            case Ogre_Kingdoms:
                _return = getOgreKingdoms(armyPoints);
                break;
            case Orcs_and_Goblins:
                _return = getOrcsAndGoblins(armyPoints);
                break;
            case Skaven:
                _return = getSkaven(armyPoints);
                break;
            case Tomb_Kings:
                _return = getTombKings(armyPoints);
                break;
            case Vampire_Counts:
                _return = getVampireCounts(armyPoints);
                break;
            case Warriors_of_Chaos:
                _return = getWarriorsOfChaos(armyPoints);
                break;
            case Wood_Elves:
                _return = getWoodElves(armyPoints);
                break;
        }
        return _return;
    }

    /**
     * Method to get the disposition for beastmen
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getBeastmen(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for wood elves
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getWoodElves(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for warriors of chaos
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getWarriorsOfChaos(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for vampire counts
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getVampireCounts(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for tomb kings
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getTombKings(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for skaven
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getSkaven(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for bretonnia
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getBretonnia(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for daemons of chaos
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getDaemonsOfChaos(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for dark elves
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getDarkElves(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for dwarfs
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getDwarfs(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for empire
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getEmpire(int armyPoints) {
        if(armyPoints<2000)
            return new RaceUnitDisposition(3,0,3,2,0,3,0,1);
        if(armyPoints>=2000 && armyPoints<3000)
            return new RaceUnitDisposition(4,1,4,3,0,4,0,2);
        if(armyPoints>=3000 && armyPoints<4000)
            return new RaceUnitDisposition(6,2,6,4,0,5,0,3);
        if(armyPoints>=4000 && armyPoints<5000)
            return new RaceUnitDisposition(8,3,8,5,0,6,0,4);
        return greatArmy(2,1,2,1,0,1,0,1,Races.Empire,armyPoints);
    }

    /**
     * Method to get the disposition for lizardmen
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getLizardmen(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for ogre kingdoms
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getOgreKingdoms(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for orcs and goblins
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getOrcsAndGoblins(int armyPoints) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for high elves
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private RaceUnitDisposition getHighElves(int armyPoints){
        if(armyPoints<2000)
            return new RaceUnitDisposition(3,0,3,1,0,5,0,2);
        if(armyPoints>=2000 && armyPoints<3000)
            return new RaceUnitDisposition(4,1,4,2,0,6,0,4);
        if(armyPoints>=3000 && armyPoints<armyPoints)
            return new RaceUnitDisposition(6,2,6,3,0,7,0,6);
        if(armyPoints>=armyPoints && armyPoints<5000)
            return new RaceUnitDisposition(8,3,8,4,0,8,0,8);
        return greatArmy(2,1,2,1,0,1,0,2,Races.High_Elves,armyPoints);
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
}
