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
 * Factory class to create objectes of the UnitDisposition class.
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class UnitDispositionFactory {

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
    private static UnitDisposition greatArmy(int incrementCharacters,
            int incrementLords, int incrementHeroes, int incrementCore,
            int incrementSpecialMin, int incrementSpecialMax,
            int incrementRareMin, int incrementRareMax, Races race, int armyPoints){
        UnitDisposition tmp = getRaceArmy(race,4000);
        int multiplier = 0;
        int nextPointIterator=4000;
        do{
            multiplier++;
            nextPointIterator += 1000;
        }
        while(nextPointIterator<armyPoints);
        int maxCharacters = tmp.getMaxCharacters();
        int maxHeroes = tmp.getMaxHeroes();
        int maxLords = tmp.getMaxLords();
        int minCore = tmp.getMinCore();
        int minRare = tmp.getMinRare();
        int maxRare = tmp.getMaxRare();
        int minSpecial = tmp.getMinSpecial();
        int maxSpecial = tmp.getMaxSpecial();
        maxCharacters += incrementCharacters*multiplier;
        maxHeroes += incrementHeroes*multiplier;
        maxLords += incrementLords*multiplier;
        minCore += incrementCore*multiplier;
        maxRare += incrementRareMax*multiplier;
        minRare += incrementRareMin*multiplier;
        maxSpecial += incrementSpecialMax*multiplier;
        minSpecial += incrementSpecialMin*multiplier;
        return new UnitDisposition(maxCharacters, maxLords, maxHeroes,
                minCore, minSpecial, maxSpecial, minRare, maxRare);
    }

    /**
     * Method to aquire the requested army dispositon for the specified points
     * @param race Race The race of the army to get the disposition for
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    public static UnitDisposition getRaceArmy(Races race,int armyPoints){
        UnitDisposition _return = null;
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
    private static UnitDisposition getBeastmen(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(6,2,6,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Beastmen,armyPoints);
    }

    /**
     * Method to get the disposition for wood elves
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getWoodElves(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(6,2,6,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Wood_Elves,armyPoints);
    }

    /**
     * Method to get the disposition for warriors of chaos
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getWarriorsOfChaos(int armyPoints) {
        if(armyPoints<2000){
            //return new UnitDisposition();
        }
        if(armyPoints>=2000 && armyPoints<3000){
            //return new UnitDisposition();
        }
        if(armyPoints>=3000 && armyPoints<4000){
            //return new UnitDisposition();
        }
        if(armyPoints>=4000 && armyPoints<5000){
            //return new UnitDisposition();
        }
        //return greatArmy();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for vampire counts
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getVampireCounts(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(6,2,6,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Vampire_Counts,armyPoints);
    }

    /**
     * Method to get the disposition for tomb kings
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getTombKings(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(6,2,6,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Tomb_Kings,armyPoints);
    }

    /**
     * Method to get the disposition for skaven
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getSkaven(int armyPoints) {
        if(armyPoints<2000){
            //return new UnitDisposition();
        }
        if(armyPoints>=2000 && armyPoints<3000){
            //return new UnitDisposition();
        }
        if(armyPoints>=3000 && armyPoints<4000){
            //return new UnitDisposition();
        }
        if(armyPoints>=4000 && armyPoints<5000){
            //return new UnitDisposition();
        }
        //return greatArmy();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for bretonnia
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getBretonnia(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(4,0,4,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(5,1,5,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(7,2,7,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(9,3,9,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Bretonnia,armyPoints);
    }

    /**
     * Method to get the disposition for daemons of chaos
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getDaemonsOfChaos(int armyPoints) {
        if(armyPoints<2000){
            //return new UnitDisposition();
        }
        if(armyPoints>=2000 && armyPoints<3000){
            //return new UnitDisposition();
        }
        if(armyPoints>=3000 && armyPoints<4000){
            //return new UnitDisposition();
        }
        if(armyPoints>=4000 && armyPoints<5000){
            //return new UnitDisposition();
        }
        //return greatArmy();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for dark elves
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getDarkElves(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(6,2,6,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Dark_Elves,armyPoints);
    }

    /**
     * Method to get the disposition for dwarfs
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getDwarfs(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(8,3,8,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Dwarfs,armyPoints);
    }

    /**
     * Method to get the disposition for empire
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getEmpire(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(6,2,6,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Empire,armyPoints);
    }

    /**
     * Method to get the disposition for lizardmen
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getLizardmen(int armyPoints) {
        if(armyPoints<2000){
            //return new UnitDisposition();
        }
        if(armyPoints>=2000 && armyPoints<3000){
            //return new UnitDisposition();
        }
        if(armyPoints>=3000 && armyPoints<4000){
            //return new UnitDisposition();
        }
        if(armyPoints>=4000 && armyPoints<5000){
            //return new UnitDisposition();
        }
        //return greatArmy();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for ogre kingdoms
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getOgreKingdoms(int armyPoints) {
        if(armyPoints<2000){
            //return new UnitDisposition();
        }
        if(armyPoints>=2000 && armyPoints<3000){
            //return new UnitDisposition();
        }
        if(armyPoints>=3000 && armyPoints<4000){
            //return new UnitDisposition();
        }
        if(armyPoints>=4000 && armyPoints<5000){
            //return new UnitDisposition();
        }
        //return greatArmy();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Method to get the disposition for orcs and goblins
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getOrcsAndGoblins(int armyPoints) {
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,2,0,3,0,1);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,3,0,4,0,2);
        }
        if(armyPoints>=3000 && armyPoints<4000){
            return new UnitDisposition(6,2,6,4,0,5,0,3);
        }
        if(armyPoints>=4000 && armyPoints<5000){
            return new UnitDisposition(8,3,8,5,0,6,0,4);
        }
        return greatArmy(2,1,2,1,0,1,0,1,Races.Orcs_and_Goblins,armyPoints);
    }

    /**
     * Method to get the disposition for high elves
     * @param armyPoints int The army points
     * @return RaceUnitDispositon The requested army disposition
     */
    private static UnitDisposition getHighElves(int armyPoints){
        if(armyPoints<2000){
            return new UnitDisposition(3,0,3,1,0,5,0,2);
        }
        if(armyPoints>=2000 && armyPoints<3000){
            return new UnitDisposition(4,1,4,2,0,6,0,4);
        }
        if(armyPoints>=3000 && armyPoints<armyPoints){
            return new UnitDisposition(6,2,6,3,0,7,0,6);
        }
        if(armyPoints>=armyPoints && armyPoints<5000){
            return new UnitDisposition(8,3,8,4,0,8,0,8);
        }
        return greatArmy(2,1,2,1,0,1,0,2,Races.High_Elves,armyPoints);
    }
}