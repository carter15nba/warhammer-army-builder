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

package org.Warhammer.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.Warhammer.Database.DatabaseManager;
import org.Warhammer.Warhammer.Case.Races;
import org.Warhammer.Warhammer.Equipment;
import org.Warhammer.Warhammer.Equipment.itemType;
import org.Warhammer.Warhammer.Unit;
import org.Warhammer.Warhammer.Unit.*;
import org.Warhammer.Warhammer.UtilityUnit;

/**
 * Class to create a unit with all the unit data from the database
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class CreateObjectFromDB {

    /**
     * Method to create a unit with the core information from the database.
     * @param unitName String The name of the unit as it appears in the database.
     * @return <ul>
     * <li>Unit - an empty Unit object if the queried unit name does not exist
     * in the database.</li>
     * <li>Unit - the Unit object with the units core data</li>
     * </ul>
     */
    public static Unit createUnitFromDB(String unitName){
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();
        ResultSet result = dbm.executeSQL("SELECT * FROM UNIT WHERE NAME='"+
                unitName+"'", DatabaseManager.SELECT_QUERY);
        Unit unit = new Unit();
        unit.setName(unitName);
        try {
            result.next();
            unit.setName(result.getString("NAME"));
            unit.setArmyType(armyType.valueOf(result.getString("ARMYTYPE")));
            unit.setUnitType(unitType.valueOf(result.getString("UNITTYPE")));
            unit.setAttack(result.getString("ATTACK"));
            unit.setBallisticSkill(result.getString("BALLISTICSKILL"));
            unit.setCost(result.getInt("COST"));
            unit.setInitiative(result.getString("INITIATIVE"));
            unit.setLeadership(result.getString("LEADERSHIP"));
            unit.setMagicPoints(result.getInt("MAGICPOINTS"));
            unit.setMovement(result.getString("MOVEMENT"));
            unit.setStrength(result.getString("STRENGTH"));
            unit.setToughness(result.getString("TOUGHNESS"));
            unit.setWeaponSkill(result.getString("WEAPONSKILL"));
            unit.setWounds(result.getString("WOUNDS"));
            unit.setMaxNumber(result.getInt("MAXUNITS"));
            unit.setMinNumber(result.getInt("MINUNITS"));
            unit.setRace(Races.valueOf(result.getString("RACE")));
            unit.setWeaponType(weaponType.valueOf(result.getString("WEAPONTYPE")));
        }
        catch (SQLException ex) {}
        unit.setEquipment(getUnitEquipment(unitName));
        unit.setUtilityUnit(getUtilityUnits(unitName));
        return unit;
    }
    
    /**
     * Method that aquire a list of units based on the race and army type.
     * @param race The race of the units
     * @param aT The ArmyType of the units
     * @return <ul><li>An empty ArrayList if an exception occurs</li>
     * <li>ArrayList with the units matching the requirements</li></ul>
     */
    public static ArrayList<Unit> findRaceAndArmyTypeUnits(Races race ,armyType aT){
        ArrayList<Unit> units = new ArrayList<Unit>();
        try {
            String query = "SELECT UNIT.NAME FROM UNIT WHERE UNIT.RACE='"
                    +race+"' AND UNIT.ARMYTYPE='"+aT+"'";
            DatabaseManager dbm = DatabaseManager.getInstance();
            dbm.connectWithoutHibernate();
            ResultSet res = dbm.executeSQL(query, DatabaseManager.SELECT_QUERY);            
            while (res.next()) {
                String name = res.getString("NAME");
                Unit unit = CreateObjectFromDB.createUnitFromDB(name);
                units.add(unit);
            }
        }
        catch (SQLException ex) {}
        return units;
    }

    /**
     * Method to aquire a Set with the equipment that a unit can have.
     * @param unitName Sting the unit name
     * @return <ul><li>An empty set if an exception occurs</li>
     * <li>Set with the equipment matching the requirements</li></ul>
     */
    public static Set<Equipment> getUnitEquipment(String unitName){
        Set<Equipment> eqSet = new HashSet<Equipment>();
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();
        String query = "SELECT EQUIPMENT.ID, EQUIPMENT.COST, EQUIPMENT.NAME, "
                + "EQUIPMENT.RANGE, EQUIPMENT.MODIFIER, EQUIPMENT.USABLEBY, "
                + "EQUIPMENT.ITEMTYPE,EQUIPMENT.DEFAULTEQ "
                + "FROM EQUIPMENT, UNIT_EQUIPMENT, UNIT "
                + "WHERE EQUIPMENT.ID=UNIT_EQUIPMENT.EQUIPMENT_ID AND "
                + "UNIT_EQUIPMENT.NAME = UNIT.NAME AND "
                + "UNIT.NAME='"+unitName+"'";
        ResultSet res = dbm.executeSQL(query, DatabaseManager.SELECT_QUERY);

        try {
            while (res.next()) {
                Equipment eq = new Equipment();
                eq.setCost(res.getInt("COST"));
                eq.setDefaultItem(res.getBoolean("DEFAULTEQ"));
                eq.setID(res.getInt("ID"));
                eq.setItemType(itemType.valueOf(res.getString("ITEMTYPE")));
                eq.setModifier(res.getString("MODIFIER"));
                eq.setName(res.getString("NAME"));
                eq.setUsableBy(res.getString("USABLEBY"));
                eqSet.add(eq);
            }
        }
        catch (SQLException ex) {}
        return eqSet;
    }

    /**
     * Method to aquire a Set with the utility units that a unit can have.
     * @param unitName Sting the unit name
     * @return <ul><li>An empty set if an exception occurs</li>
     * <li>Set with the utility units matching the requirements</li></ul>
     */
    public static Set<UtilityUnit> getUtilityUnits(String unitName){
        Set<UtilityUnit> utSet = new HashSet<UtilityUnit>();
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();
        String query = "SELECT UTILITYUNIT.ID, UTILITYUNIT.COST, UTILITYUNIT.NAME, "
                + "UTILITYUNIT.MINUNITS, UTILITYUNIT.ATTACK, "
                + "UTILITYUNIT.BALLISTICSKILL, UTILITYUNIT.INITIATIVE, "
                + "UTILITYUNIT.LEADERSHIP, UTILITYUNIT.MOVEMENT, "
                + "UTILITYUNIT.STRENGTH, UTILITYUNIT.TOUGHNESS, UTILITYUNIT.UNITTYPE,"
                + "UTILITYUNIT.WEAPONSKILL, UTILITYUNIT.WOUNDS, UTILITYUNIT.REQUIRED,"
                + "UTILITYUNIT.PROMOTIONUNIT "
                + "FROM UTILITYUNIT, UNIT_UTILITY, UNIT "
                + "WHERE UTILITYUNIT.ID=UNIT_UTILITY.UTILID AND "
                + "UNIT_UTILITY.NAME = UNIT.NAME AND "
                + "UNIT.NAME='"+unitName+"'";
        ResultSet res = dbm.executeSQL(query, DatabaseManager.SELECT_QUERY);
        try {
            while (res.next()) {
                UtilityUnit ut = new UtilityUnit();
                ut.setAttack(res.getString("ATTACK"));
                ut.setBallisticSkill(res.getString("BALLISTICSKILL"));
                ut.setCost(res.getInt("COST"));
                ut.setID(res.getInt("ID"));
                ut.setInitiative(res.getString("INITIATIVE"));
                ut.setMinNumber(res.getInt("MINUNITS"));
                ut.setMovement(res.getString("MOVEMENT"));
                ut.setName(res.getString("NAME"));
                ut.setPromotionUnit(res.getBoolean("PROMOTIONUNIT"));
                ut.setRequired(res.getBoolean("REQUIRED"));
                ut.setStrength(res.getString("STRENGTH"));
                ut.setToughness(res.getString("TOUGHNESS"));
                ut.setUnitType(unitType.valueOf(res.getString("UNITTYPE")));
                ut.setWeaponSkill(res.getString("WEAPONSKILL"));
                ut.setWounds(res.getString("WOUNDS"));
                utSet.add(ut);
            }
            return  utSet;
        }
        catch (SQLException ex) {}
        return null;
    }

    /**
     * Method which gets all the equipment a unit can equip based on which
     * race and the available magic points. (common and race spesific items)
     * @param unitRace - The race of the unit
     * @param magicPoints - The number of points the unit can spend on magical items
     * @return A hash set with the equipment the unit can purchase
     */
    public static Set<Equipment> getAllEquipment(Races unitRace, int magicPoints){
        Set<Equipment> eqSet = new HashSet<Equipment>();
        String query = "SELECT * FROM EQUIPMENT "
                + "WHERE EQUIPMENT.COST <= "+magicPoints
                + " AND EQUIPMENT.COST <> 0"
                + " AND (EQUIPMENT.USABLEBY='Race:"+unitRace+"'"
                + " OR EQUIPMENT.USABLEBY='All')"
                + " AND EQUIPMENT.ITEMTYPE <> 'Weapon'"
                + " AND EQUIPMENT.ITEMTYPE <> 'Armour'"
                + " AND EQUIPMENT.ITEMTYPE <> 'Unit_Upgrade'";
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();
        ResultSet res = dbm.executeSQL(query, DatabaseManager.SELECT_QUERY);
        try {
            while (res.next()) {
                Equipment eq = new Equipment();
                eq.setCost(res.getInt("COST"));
                eq.setDefaultItem(res.getBoolean("DEFAULTEQ"));
                eq.setID(res.getInt("ID"));
                eq.setItemType(itemType.valueOf(res.getString("ITEMTYPE")));
                eq.setModifier(res.getString("MODIFIER"));
                eq.setName(res.getString("NAME"));
                eq.setUsableBy(res.getString("USABLEBY"));
                eqSet.add(eq);
            }
        }
        catch (SQLException ex) {}
        return eqSet;
    }

        /**
     * Method which gets all the equipment a unit can equip based on which
     * race and the available magic points. (race spesific items only)
     * @param unitRace - The race of the unit
     * @param magicPoints - The number of points the unit can spend on magical items
     * @return A hash set with the equipment the unit can purchase
     */
    public static Set<Equipment> getRaceEquipment(Races unitRace, int magicPoints){
        Set<Equipment> eqSet = new HashSet<Equipment>();
        String query = "SELECT * FROM EQUIPMENT "
                + "WHERE EQUIPMENT.COST <= "+magicPoints
                + " AND EQUIPMENT.COST <> 0"
                + " AND EQUIPMENT.USABLEBY='Race:"+unitRace+"'"
                + " AND EQUIPMENT.ITEMTYPE <> 'Weapon'"
                + " AND EQUIPMENT.ITEMTYPE <> 'Armour'"
                + " AND EQUIPMENT.ITEMTYPE <> 'Unit_Upgrade'";
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.connectWithoutHibernate();
        ResultSet res = dbm.executeSQL(query, DatabaseManager.SELECT_QUERY);
        try {
            while (res.next()) {
                Equipment eq = new Equipment();
                eq.setCost(res.getInt("COST"));
                eq.setDefaultItem(res.getBoolean("DEFAULTEQ"));
                eq.setID(res.getInt("ID"));
                eq.setItemType(itemType.valueOf(res.getString("ITEMTYPE")));
                eq.setModifier(res.getString("MODIFIER"));
                eq.setName(res.getString("NAME"));
                eq.setUsableBy(res.getString("USABLEBY"));
                eqSet.add(eq);
            }
        }
        catch (SQLException ex) {}
        return eqSet;
    }
}