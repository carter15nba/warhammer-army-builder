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

import org.Warhammer.Warhammer.Case.Races;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Class to represent a unit
 * @author Glenn Rune Strandbåten
 * @version 0.5.1
 */
public class Unit extends CoreUnit{
    public enum unitType {Ca, Ch, In, Mo, MB, MC, MI, Sw, Un, WB, WM , _na};
    public enum armyType {Hero, Lord, Special, Rare, Core, _na};
    private String movement = "";
    private String weaponSkill = "";
    private String ballisticSkill = "";
    private String strength = "";
    private String toughness = "";
    private String wounds = "";
    private String initiative = "";
    private String attack = "";
    private String leadership = "";
    private unitType unitType;
    private armyType armyType;
    private Set<Equipment> equipment = new HashSet<Equipment>();
    private Set<UtilityUnit> utilityUnit = new HashSet<UtilityUnit>();
    private Races race;
    private Set<SpecialRules> specialRules = new HashSet<SpecialRules>();
    private int magicPoints;
    
    /**
     * @return the movement
     */
    public String getMovement() {
        return movement;
    }

    /**
     * @param movement the movement to set
     */
    public void setMovement(String movement) {
        this.movement = movement;
    }

    /**
     * @return the weaponSkill
     */
    public String getWeaponSkill() {
        return weaponSkill;
    }

    /**
     * @param weaponSkill the weaponSkill to set
     */
    public void setWeaponSkill(String weaponSkill) {
        this.weaponSkill = weaponSkill;
    }

    /**
     * @return the ballisticSkill
     */
    public String getBallisticSkill() {
        return ballisticSkill;
    }

    /**
     * @param ballisticSkill the ballisticSkill to set
     */
    public void setBallisticSkill(String ballisticSkill) {
        this.ballisticSkill = ballisticSkill;
    }

    /**
     * @return the strength
     */
    public String getStrength() {
        return strength;
    }

    /**
     * @param strength the strength to set
     */
    public void setStrength(String strength) {
        this.strength = strength;
    }

    /**
     * @return the toughness
     */
    public String getToughness() {
        return toughness;
    }

    /**
     * @param toughness the toughness to set
     */
    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    /**
     * @return the wounds
     */
    public String getWounds() {
        return wounds;
    }

    /**
     * @param wounds the wounds to set
     */
    public void setWounds(String wounds) {
        this.wounds = wounds;
    }

    /**
     * @return the initiative
     */
    public String getInitiative() {
        return initiative;
    }

    /**
     * @param initiative the initiative to set
     */
    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }

    /**
     * @return the attack
     */
    public String getAttack() {
        return attack;
    }

    /**
     * @param attack the attack to set
     */
    public void setAttack(String attack) {
        this.attack = attack;
    }

    /**
     * @return the leadership
     */
    public String getLeadership() {
        return leadership;
    }

    /**
     * @param leadership the leadership to set
     */
    public void setLeadership(String leadership) {
        this.leadership = leadership;
    }

    /**
     * @return the unitType

     */
    public unitType getUnitType() {
        return unitType;
    }

    /**
     * @param unitType the unitType to set
     */
    public void setUnitType(unitType unitType) {
        this.unitType = unitType;
    }

    /**
     * @return the armyType
     */
    public armyType getArmyType() {
        return armyType;
    }

    /**
     * @param armyType the armyType to set
     */
    public void setArmyType(armyType armyType) {
        this.armyType = armyType;
    }

    /**
     * @return the equipment
     */
    public Set<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the utilityUnit
     */
    public Set<UtilityUnit> getUtilityUnit() {
        return utilityUnit;
    }

    /**
     * @param utilityUnit the utilityUnit to set
     */
    public void setUtilityUnit(Set<UtilityUnit> utilityUnit) {
        this.utilityUnit = utilityUnit;
    }


    /**
     * @return the race
     */
    public Races getRace() {
        return race;
    }

    /**
     * @param race the race to set
     */
    public void setRace(Races race) {
        this.race = race;
    }

    /**
     * @return the specialRules
     */
    public Set<SpecialRules> getSpecialRules() {
        return specialRules;
    }

    /**
     * @param specialRules the specialRules to set
     */
    public void setSpecialRules(Set<SpecialRules> specialRules) {
        this.specialRules = specialRules;
    }
        /**
     * @return the magicPoints
     */
    public int getMagicPoints() {
        return magicPoints;
    }

    /**
     * @param magicPoints the magicPoints to set
     */
    public void setMagicPoints(int magicPoints) {
        this.magicPoints = magicPoints;
    }

    @Override
    public String toString(){

        String retString = super.toString()+
                "\n|   M="+movement
                + " WS="+weaponSkill
                + " BS="+ballisticSkill
                + " S="+strength
                + " T="+toughness
                + " I="+initiative
                + " W="+wounds
                + " A="+attack
                + " Ld="+leadership
                + " UnitType="+unitType
                + " ArmyType="+armyType;
        if(getEquipment().size()>0){
            Iterator<Equipment> iterator = getEquipment().iterator();
            while(iterator.hasNext()){
                retString += "\n|    "+iterator.next().toString();
            }
        }
        if(getUtilityUnit().size()>0){
            Iterator<UtilityUnit> iterator = getUtilityUnit().iterator();
            while(iterator.hasNext()){
                retString += "\n|    "+iterator.next().toString();
            }
        }
        if(getSpecialRules().size()>0){
            retString += "\n|    SpecialRules:";
            for (SpecialRules sr : specialRules) {
                if(sr!=null)
                    retString +=" "+sr.getRule()+",";
            }
        }

        return retString;

    }

    /**
     * Method to get the String representation of the desired characteristic. The
     * positions follow:
     * <table boder="1">
     *  <tr>
     *    <td>Movement</td>
     *  </tr>
     * <tr>
     *    <td>Weapon skill</td>
     *  </tr>
     * <tr>
     *    <td>Ballistic skill</td>
     *  </tr>
     * <tr>
     *    <td>Strength</td>
     *  </tr>
     * <tr>
     *    <td>Toughness</td>
     *  </tr>
     * <tr>
     *    <td>Wounds</td>
     *  </tr>
     * <tr>
     *    <td>Initiative</td>
     *  </tr>
     * <tr>
     *    <td>Attack</td>
     *  </tr>
     * <tr>
     *    <td>Leadership</td>
     *  </tr>
     * </table>
     * @param pos int The position of the characteristic
     * @return String The indicated string representation of the characteristic.
     */
    public String getCharactersitic(int pos){
        String[] stats = new String[]{movement,
            weaponSkill,
            ballisticSkill,
            strength,
            toughness,
            wounds,
            initiative,
            attack,
            leadership};
        return stats[pos];
    }

    /**
     * Method to get all the characteristics of the unit represented as doubles.
     * The positions follow:
     * <table boder="1">
     *  <tr>
     *    <td>Movement</td>
     *  </tr>
     * <tr>
     *    <td>Weapon skill</td>
     *  </tr>
     * <tr>
     *    <td>Ballistic skill</td>
     *  </tr>
     * <tr>
     *    <td>Strength</td>
     *  </tr>
     * <tr>
     *    <td>Toughness</td>
     *  </tr>
     * <tr>
     *    <td>Wounds</td>
     *  </tr>
     * <tr>
     *    <td>Initiative</td>
     *  </tr>
     * <tr>
     *    <td>Attack</td>
     *  </tr>
     * <tr>
     *    <td>Leadership</td>
     *  </tr>
     * </table>
     * @return double[] The array with the double representations of all the
     * unit characteristics. An index with value -1 could not be transformed
     * into a double and holds e.g.: a 2D6 value.
     */
    public double[] getCharacteristics(){
        String[] characteristics = new String[]{movement,
            weaponSkill,
            ballisticSkill,
            strength,
            toughness,
            wounds,
            initiative,
            attack,
            leadership};
        double[] _return = new double[9];
        int pos = 0;
        for (String characteristic : characteristics) {
            try{
                double parsed = Double.parseDouble(characteristic);
                _return[pos++] = parsed;
            }
            catch(NumberFormatException nfe){_return[pos++]=-1;}
        }
        return _return;
    }
}
