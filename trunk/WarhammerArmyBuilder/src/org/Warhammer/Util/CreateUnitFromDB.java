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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.Warhammer.Database.DatabaseManager;
import org.Warhammer.Warhammer.Case.Races;
import org.Warhammer.Warhammer.Unit;
import org.Warhammer.Warhammer.Unit.armyType;
import org.Warhammer.Warhammer.Unit.unitType;

/**
 * Class to create a unit with all the unit data from the database
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class CreateUnitFromDB {

    /**
     * Method to create a unit with the core information from the database.
     * @param unitName String The name of the unit as it appears in the database.
     * @return <ul>
     * <li>null - if the queried unit name does not exist in the database.</li>
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
            while (result.next()){
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
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(CreateUnitFromDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return unit;
    }

}
