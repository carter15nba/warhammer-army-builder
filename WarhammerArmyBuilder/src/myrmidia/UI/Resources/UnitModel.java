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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import myrmidia.Enums.Races;
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.Unit;
import myrmidia.Warhammer.UtilityUnit;

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
    private boolean battleStandardBearer;

    /** Default constructor */
    public UnitModel(){
        name = "";
        row = -1;
        utility = new CheckListItem[0];
        equipment = new CheckListItem[0];
        magic = new CheckListItem[0];
        promotion = new CheckListItem[0];
        battleStandardBearer = false;
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
        promotion = new CheckListItem[0];
        battleStandardBearer = false;
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
     * @param promotion the promotion to set
     */
    public void setPromotion(CheckListItem[] promotion) {
        this.promotion = promotion;
    }

    public static UnitModel parseUnitModelFromUnit(Unit unit){
        UnitModel model = new UnitModel();
        String name = unit.getName().split(":")[1];
        model.setName(name);
        model.setEquipment(parseEquipment(unit.getEquipment()));
        model.setPromotion(parsePromotion(unit.getUtilityUnit()));
        model.setUtility(parseUtility(unit.getUtilityUnit()));
        model.setMagic(parseMagic(unit));
        return model;
    }

    public static CheckListItem[] parseEquipment(Set<Equipment> eq){
        List<CheckListItem> items = new ArrayList<CheckListItem>();
        for (Equipment e : eq) {
            if(e.isDefaultItem())
                continue;
            String name = e.getName();
            int cost = e.getCost();
            String title = "("+cost+")"+name;
            CheckListItem cli = new CheckListItem(title);
            items.add(cli);
        }
        return items.toArray(new CheckListItem[items.size()]);
    }

    public static CheckListItem[] parseUtility(Set<UtilityUnit> ut){
        List<CheckListItem> items = new ArrayList<CheckListItem>();
        for (UtilityUnit u : ut) {
            if(u.isPromotionUnit()||u.isRequired())
                continue;
            String name = u.getName().split(":")[1];
            int cost = u.getCost();
            String title = "("+cost+")"+name;
            CheckListItem cli = new CheckListItem(title);
            items.add(cli);
        }
        return items.toArray(new CheckListItem[items.size()]);
    }

    public static CheckListItem[] parsePromotion(Set<UtilityUnit> ut){
        List<CheckListItem> items = new ArrayList<CheckListItem>();
        for (UtilityUnit u : ut) {
            if(!u.isPromotionUnit()||u.isRequired())
                continue;
            String name = u.getName().split(":")[1];
            int cost = u.getCost();
            String title = "("+cost+")"+name;
            CheckListItem cli = new CheckListItem(title);
            items.add(cli);
        }
        return items.toArray(new CheckListItem[items.size()]);
    }

    public static CheckListItem[] parseMagic(Unit unit) {
        Set<Equipment> eq;
        if(unit.getRace()==Races.Dwarfs){
            eq = CreateObjectFromDB.getRaceEquipment(
                    unit.getRace(), unit.getMagicPoints());
        }
        else{
            eq = CreateObjectFromDB.getAllEquipment(
                    unit.getRace(), unit.getMagicPoints());
        }
        CheckListItem[] items = new CheckListItem[eq.size()];
        int count=0;
        for (Equipment e : eq) {
            String name = e.getName();
            int cost = e.getCost();
            String title = "("+cost+")"+name;
            items[count++] = new CheckListItem(title);
        }
        return items;
    }
    
    public static UnitModel parseArmyUnit(ArmyUnit armyUnit){
        UnitModel model = new UnitModel();
        model.setEquipment(UnitModel.parseEquipment(armyUnit.getEquipment()));
        model.setUtility(UnitModel.parseUtility(armyUnit.getUtility()));
        model.setPromotion(UnitModel.parsePromotion(armyUnit.getUtility()));
        return model;
    }

    private void toggleSelectedOn(){
        for (CheckListItem cli : utility) {
            cli.setSelected(true);
        }
        for (CheckListItem cli : equipment) {
            cli.setSelected(true);
        }
        for (CheckListItem cli : promotion) {
            cli.setSelected(true);
        }
    }

    public boolean isEmpty() {
        if((utility.length==0)&&
                (equipment.length==0)&&
                (promotion.length==0)&&
                (magic.length==0))
            return true;
        return false;
    }

    /**
     * @return the battleStandardBearer
     */
    public boolean isBattleStandardBearer() {
        return battleStandardBearer;
    }

    /**
     * @param battleStandardBearer the battleStandardBearer to set
     */
    public void setBattleStandardBearer(boolean battleStandardBearer) {
        this.battleStandardBearer = battleStandardBearer;
    }

    @Override
    public String toString(){
        return "Unit name: "+name+", index:"+row+", BSB: "+battleStandardBearer+"\n"
                + "  Equipment: "+equipment.length+"\n"
                + "  Utility: "+utility.length+"\n"
                + "  Promotion: "+promotion.length+"\n"
                + "  Magic: "+magic.length;
    }
}