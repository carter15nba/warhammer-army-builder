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

package myrmidia.UI.Resources;

/**
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class UnitModel {

    private String name;
    private int row;
    private CheckListItem[] utility;
    private CheckListItem[] equipment;
    private CheckListItem[] magic;
    private CheckListItem[] promotion;

    /** Default constructor */
    public UnitModel(){
        name = "";
        row = -1;
        utility = new CheckListItem[0];
        equipment = new CheckListItem[0];
        magic = new CheckListItem[0];
        promotion = new CheckListItem[1];
        promotion[0] = new CheckListItem("test", true);

    }

    /**
     * Constructor initializes the object with the supplied parameters
     * @param unitName String - the name of the unit
     * @param row int - The row in the table
     */
    public UnitModel(String unitName, int row){
        this.name = unitName;
        this.row = row;
        utility = new CheckListItem[0];
        equipment = new CheckListItem[0];
        magic = new CheckListItem[0];
        promotion = new CheckListItem[1];
        promotion[0] = new CheckListItem("test", true);
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
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the utility
     */
    public CheckListItem[] getUtility() {
        return utility;
    }

    /**
     * @param utility the utility to set
     */
    public void setUtility(CheckListItem[] utility) {
        this.utility = utility;
    }

    /**
     * @return the equipment
     */
    public CheckListItem[] getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setEquipment(CheckListItem[] equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the magic
     */
    public CheckListItem[] getMagic() {
        return magic;
    }

    /**
     * @param magic the magic to set
     */
    public void setMagic(CheckListItem[] magic) {
        this.magic = magic;
    }

    /**
     * @return the promotion
     */
    public CheckListItem[] getPromotion() {
        return promotion;
    }

    /**
     * @param magic the promotion to set
     */
    public void setPromotion(CheckListItem[] promotion) {
        this.promotion = promotion;
    }

}