/*
 *  Copyright (C) 2011 Glenn Rune
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

import java.util.HashSet;
import java.util.Set;
import jcolibri.cbrcore.Attribute;
import myrmidia.UI.Resources.CheckListItem;
import myrmidia.UI.Resources.UnitModel;
import myrmidia.Util.CreateObjectFromDB;

/**
 * Class representing a army unit. A army unit is the unit and associated
 * equipment, mounts and crew that is used in a particular case.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class ArmyUnit implements jcolibri.cbrcore.CaseComponent{
    private static final int EQ = 100;
    private static final int UT = 200;
    private int ID;
    private int armyID;
    private int numberOfUnits;
    private Unit unit;
    private int fullCommandCost = 0;
    private Set<Equipment> equipment = new HashSet<Equipment>();
    private Set<UtilityUnit> utility = new HashSet<UtilityUnit>();

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
     * @return the armyID
     */
    public int getArmyID() {
        return armyID;
    }

    /**
     * @param armyID the armyID to set
     */
    public void setArmyID(int armyID) {
        this.armyID = armyID;
    }
    
    /**
     * @return the unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * @return the equipmentID
     */
    public Set<Equipment> getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipmentID to set
     */
    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the utilityID
     */
    public Set<UtilityUnit> getUtility() {
        return utility;
    }

    /**
     * @param utility the utility to set
     */
    public void setUtility(Set<UtilityUnit> utility) {
        this.utility = utility;
    }

    /**
     * @return the numberOfUnits
     */
    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    /**
     * @param numberOfUnits the numberOfUnits to set
     */
    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    /**
     * Method that determines if the unit have full command and return the
     * results
     * @return <ul><li>true - if the unit have full command</li>
     * <li>false - id the unit does not have full command</li></ul>
     */
    public boolean haveFullCommand(){
        if(!unit.isEligibleForFullCommand())
            return false;
        boolean standard = false;
        boolean musician = false;
        boolean promotion = false;
        fullCommandCost=0;
        for (Equipment eq : equipment) {
            if(eq.getName().contains("Standard bearer")){
                standard = true;
                fullCommandCost+=eq.getCost();
            }
            if(eq.getName().contains("Musician")){
                musician = true;
                fullCommandCost+=eq.getCost();
            }
        }
        for(UtilityUnit util : utility){
            if(util.isPromotionUnit()){
                promotion = true;
                fullCommandCost+=util.getCost();
            }
        }
        if(standard&&musician&&promotion)
            return true;
        fullCommandCost=0;
        return false;
    }

    /**
     * Method to get the full command cost of this unit. If the cost is 0 it
     * will assume that the cost is not yet calculated and calculate it before
     * returning.
     * @return <ul><li>0 - if the unit does not have full command</li>
     * <li>int value with the full command cost</li></ul>
     */
    public int getFullCommandCost() {
        if(fullCommandCost==0)
            haveFullCommand();
        return fullCommandCost;
    }

    /**
     * Method which removes full command from this unit. Musician, standard
     * bearer and the promoted unit is removed.
     */
    public void removeFullCommand(){
        for (Equipment eq : equipment) {
            if(eq.getName().contains("Standard bearer"))
                equipment.remove(eq);
            if(eq.getName().contains("Musician"))
                equipment.remove(eq);
        }
        for(UtilityUnit util : utility){
            if(util.isPromotionUnit())
                utility.remove(util);
        }
    }

    /**
     * Method which assigns full command to the unit if the unit is
     * eligible for full command and does not allready have full command.
     */
    public void giveUnitFullCommand(){
        if(unit.isEligibleForFullCommand()&&!haveFullCommand()){
            removeFullCommand();
            for(Equipment eq : unit.getEquipment()){
                if(eq.getName().contains("Musician"))
                    equipment.add(eq);
                if(eq.getName().contains("Standard bearer"))
                    equipment.add(eq);
            }
            for(UtilityUnit util : unit.getUtilityUnit()){
                if(util.isPromotionUnit())
                    utility.add(util);
            }
        }
    }

    /**
     * Method to check if the unit is a battle standard bearer (BSB).
     * @return <ul><li>true - if the unit is a BSB</li>
     * <li>false - if the unit not is a BSB</li></ul>
     */
    public boolean isBattleStandardBearer(){
        if(unit.canBeBattleStandardBearer()){
            for(Equipment eq : equipment){
                if(eq.getName().equalsIgnoreCase("Battle standard bearer"))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Method to parse the units UnitModel in order to aquire the equipment/
     * utility units from the UnitModel. This will replace all existing 
     * items with the ones selected in the model. Only a unit model belonging
     * to the unit will be parsed.
     * @param model The UnitModel to be parsed and belonging to the army unit
     */
    public void parseUnitModel(UnitModel model){
        if(unit.getName().contains(model.getName())){
            equipment.clear();
            utility.clear();
            if(model.isEmpty())
                return;
            parseCheckListItem(model.getEquipment(), EQ);
            parseCheckListItem(model.getMagic(), EQ);
            parseCheckListItem(model.getPromotion(), UT);
            parseCheckListItem(model.getUtility(), UT);
        }
    }
    
    /**
     * Method which parses the CheckListItem arrays inside the UnitModel
     * @param items The CheckListItem array to be parsed
     * @param mode int The mode determines if he items in the CheckListItem array
     * denotes Equipment or UtilitUnits, use the private static final int EQ/UT
     */
    private void parseCheckListItem(CheckListItem[] items, int mode){
        for (CheckListItem cli : items) {
            if(!cli.isSelected())
                continue;
            int index = cli.toString().indexOf(")");
            String name = cli.toString().substring(index+1);
            int cost = Integer.parseInt(cli.toString().substring(1,index));
            switch(mode){
                case EQ:
                    equipment.add(CreateObjectFromDB.createEquipment(name,cost));
                    break;
                case UT:
                    name = unit.getRace().toString()+":"+name;
                    utility.add(CreateObjectFromDB.createUtilityUnit(name,cost));
                    break;
            }
        }
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof ArmyUnit){
            ArmyUnit au = (ArmyUnit) o;
            if(ID==au.ID)
                return true;
        }
        return false;
    }
}