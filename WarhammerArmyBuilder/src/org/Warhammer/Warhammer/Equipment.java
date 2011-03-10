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

import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.Attribute;

/**
 *
 * @author Glenn Rune Strandbåten
 * @version 0.3
 */
public class Equipment implements jcolibri.cbrcore.CaseComponent{

    public enum itemType{Armour, Weapon, Magic_Weapon, Magic_Armour, Talisman, Standard, Arcane_Items, Enchanted_Items, Unit_Upgrade};
    private int ID;
    private int cost;
    private int range;
    private String name;
    private itemType itemType;
    private String usableBy;
    private String modifier;
    private Set<SpecialRules> rules = new HashSet<SpecialRules>();
    private boolean defaultItem;    

    public Attribute getIdAttribute() {
        return new Attribute("ID", this.getClass());
    }

    /**
     * @return the equpimentID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the equpimentID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the itemType
     */
    public itemType getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(itemType itemType) {
        this.itemType = itemType;
    }

    /**
     * @return the usableBy
     */
    public String getUsableBy() {
        return usableBy;
    }

    /**
     * @param usableBy the usableBy to set
     */
    public void setUsableBy(String usableBy) {
        this.usableBy = usableBy;
    }

    /**
     * @return the benefit
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * @param benefit the benefit to set
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

        /**
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * @return the rules
     */
    public Set<SpecialRules> getRules() {
        return rules;
    }

    /**
     * @param rules the rules to set
     */
    public void setRules(Set<SpecialRules> rules) {
        this.rules = rules;
    }

    @Override
    public String toString(){
        String rule="";
        for (SpecialRules specialRules : rules) {
            rule += " "+specialRules.getRule()+",";
        }
        return "Name: "+name+", type: "+itemType+", modifier: "+modifier+", rules: "+rule;
    }

    /**
     * @return the isDefaultItem
     */
    public boolean isDefaultItem() {
        return defaultItem;
    }

    /**
     * @param defaultItem the isDefaultItem to set
     */
    public void setDefaultItem(boolean defaultItem) {
        this.defaultItem = defaultItem;
    }
}
