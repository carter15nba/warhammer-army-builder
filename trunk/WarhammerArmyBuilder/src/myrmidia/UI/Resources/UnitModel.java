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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import myrmidia.Enums.Races;
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.Unit;
import myrmidia.Warhammer.UtilityUnit;

/**
 * Class to help display the army units selected and available equipment,
 * utility units, promotion units and magic items. This class builds on the
 * same principle as CaseStorage, but is more defined and better suited to
 * display items based on their category.
 * @author Glenn Rune Strandbråten
 * @version 1.0
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

    /**
     * Method to parse a UnitModel from a Unit, this method finds all the
     * equipment a unit can have or purchase, but does not know what the unit
     * allready may have (have purchased).
     * @param unit The Unit to be parsed
     * @return The UnitModel parsed from the Unit
     */
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

    /**
     * Method to parse equipment into a CheckListItem array
     * @param eq The equipment set to be parsed
     * @return The parsed CheckListItem array
     */
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

    /**
     * Method to parse utility units into a CheckListItem array
     * @param ut The utility units to be parsed
     * @return The parsed CheckListItem array
     */
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

    /**
     * Method to parse promotion units into a CheckListItem array
     * @param ut The set with utility units containing promotion units to be parsed
     * @return The parsed CheckListItem array
     */
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

    /**
     * Method to parse the unit into a CheckListItem array with the magical items
     * the unit can purchase
     * @param unit The unit to be parsed
     * @return The parsed CheckListItem array
     */
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

    /**
     * Method to parse a ArmyUnit into a UnitModel, this method parses
     * what equipment the ArmyUnit does have (upgraded and purchased), but
     * ignores what the ArmyUnit could have.
     * @param armyUnit The ArmyUnit to be parsed
     * @return The UnitModel parsed from the ArmyUnit
     */
    public static UnitModel parseUnitModelFromArmyUnit(ArmyUnit armyUnit){
        UnitModel model = new UnitModel();
        model.setName(armyUnit.getUnit().getName().split(":")[1]);
        model.setEquipment(parseEquipment(armyUnit.getEquipment()));
        model.setUtility(parseUtility(armyUnit.getUtility()));
        model.setPromotion(parsePromotion(armyUnit.getUtility()));
        return model;
    }

    /**
     * This method parse a ArmyUnit into a UnitModel, this method finds what
     * the ArmyUnit have purchased and what the ArmyUnit can purchase.
     * @param armyUnit The ArmyUnit to be parsed
     * @return The UnitModel parsed from the ArmyUnit
     */
    public static UnitModel parseUnitModelFromArmyUnitForRevise(ArmyUnit armyUnit){
        UnitModel model = new UnitModel();
        model.setName(armyUnit.getUnit().getName().split(":")[1]);
        model.setEquipment(parseSelectedEquipment(armyUnit));
        model.setUtility(parseSelectedUtility(armyUnit));
        model.setPromotion(parseSelectedPromotion(armyUnit));
        model.setMagic(parseSelectedMagic(armyUnit));
        for(CheckListItem cli: model.getEquipment()){
            if(cli.toString().equalsIgnoreCase("Battle standard bearer")
                    && cli.isSelected())
                model.battleStandardBearer = true;
        }
        return model;
    }

    /**
     * This method parses the equipment the ArmyUnit can have and the equipment
     * the ArmyUnit have into a CheckListItem array
     * @param armyUnit The ArnyUnit to be parsed
     * @return The parsed ChechListItem array
     */
    public static CheckListItem[] parseSelectedEquipment(ArmyUnit armyUnit){
        Unit unit = armyUnit.getUnit();
        CheckListItem[] cli = parseEquipment(unit.getEquipment());
        findSelected(cli,armyUnit.getEquipment());
        return cli;
    }

    /**
     * This method parses the utility units the ArmyUnit can have and the
     * utility units the ArmyUnit have into a CheckListItem array
     * @param armyUnit The ArnyUnit to be parsed
     * @return The parsed ChechListItem array
     */
    public static CheckListItem[] parseSelectedUtility(ArmyUnit armyUnit){
        Unit unit = armyUnit.getUnit();
        CheckListItem[] cli = parseUtility(unit.getUtilityUnit());
        findSelected(cli,armyUnit.getUtility());
        return cli;
    }

    /**
     * This method parses the promotion units the ArmyUnit can have and the
     * promotion units the ArmyUnit have into a CheckListItem array
     * @param armyUnit The ArnyUnit to be parsed
     * @return The parsed ChechListItem array
     */
    public static CheckListItem[] parseSelectedPromotion(ArmyUnit armyUnit){
        Unit unit = armyUnit.getUnit();
        CheckListItem[] cli = parsePromotion(unit.getUtilityUnit());
        findSelected(cli,armyUnit.getUtility());
        return cli;
    }

    /**
     * This method parses the magic items the ArmyUnit can have and the magic
     * items the ArmyUnit have into a CheckListItem array
     * @param armyUnit The ArnyUnit to be parsed
     * @return The parsed ChechListItem array
     */
    public static CheckListItem[] parseSelectedMagic(ArmyUnit armyUnit){
        Unit unit = armyUnit.getUnit();
        CheckListItem[] cli = parseMagic(unit);
        findSelected(cli,armyUnit.getEquipment());
        return cli;
    }

    /**
     * This method finds the items the unit have and marks them as selected in
     * the items CheckListItem object
     * @param checkListItem The array of CheckListItems to find if are selected
     * @param col The collection of items the unit can select
     */
    private static void findSelected(CheckListItem[] checkListItem, Collection col){
        for (CheckListItem cli : checkListItem) {
            for (Object o : col) {
                if(o instanceof Equipment){
                    Equipment e = (Equipment) o;
                    if(cli.toString().endsWith(e.getName()))
                        cli.setSelected(true);
                }
                if(o instanceof UtilityUnit){
                    UtilityUnit u = (UtilityUnit) o;
                    if(cli.toString().endsWith(u.getName().split(":")[1]))
                        cli.setSelected(true);
                }
            }
        }
    }

    /**
     * Method to aquire if the UnitModel is empty, if all CheckListItem arrays
     * are empty, this method returns true.
     * @return true if all CheckListItem arrays are empty, false otherwise
     */
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