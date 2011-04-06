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

package myrmidia.Warhammer;

import jcolibri.cbrcore.Attribute;

/**
 * The core unit class, this class is the super class for both the
 * unit and utility units.
 * @author Glenn Rune Strandbåten
 * @version 0.1
 */
public class CoreUnit implements jcolibri.cbrcore.CaseComponent{

    private int ID;
    private String name;
    private int cost;
    private int minNumber;
    private int maxNumber;

    public Attribute getIdAttribute() {
        return new Attribute("ID", this.getClass());
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
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

    @Override
    public String toString(){
        return "| ID: "+ID+", name: "+name+"," + "cost: "+cost;
    }

    /**
     * @return the number
     */
    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * @param maxNumber the number to set
     */
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * @return the minNumber
     */
    public int getMinNumber() {
        return minNumber;
    }

    /**
     * @param minNumber the minNumber to set
     */
    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }
}