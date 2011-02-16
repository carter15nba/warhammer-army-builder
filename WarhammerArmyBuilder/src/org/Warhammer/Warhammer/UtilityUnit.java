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

/**
 *
 * @author Glenn Rune Strandbåten
 */
public class UtilityUnit extends CoreCase{
    public enum unitType {Ca, Ch, In, Mo, MB, MC, MI, Sw, Un, WB, WM ,_na,};
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

    @Override
    public String toString(){

        return "-Name: "+getName()+", cost: "+getCost()+
                "\n       M="+movement
                + " WS="+weaponSkill
                + " BS="+ballisticSkill
                + " S="+strength
                + " T="+toughness
                + " I="+initiative
                + " W="+wounds
                + " A="+attack
                + " Ld="+leadership
                + " UnitType="+unitType;
    }
}
