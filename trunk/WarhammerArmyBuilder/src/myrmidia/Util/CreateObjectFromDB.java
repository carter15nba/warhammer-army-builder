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

package myrmidia.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.naming.ldap.HasControls;
import myrmidia.Database.Connector;
import myrmidia.Database.DatabaseManager;
import myrmidia.Enums.ArmyType;
import myrmidia.Enums.Races;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.Unit;
import myrmidia.Warhammer.Unit.*;
import org.hibernate.Session;

/**
 * Class to create a unit with all the unit data from the database
 * @author Glenn Rune Strandbråten
 * @version 0.5
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
        Session session = getSession();
        List ret = session.getNamedQuery("Unit.getUnit")
                .setString("name", unitName)
                .list();
        Unit unit = (Unit) ret.get(0);
        session.close();
        return unit;
    }
    
    /**
     * Method that aquire a list of units based on the race and army type.
     * @param race The race of the units
     * @param aT The ArmyType of the units
     * @return <ul><li>An empty ArrayList if an exception occurs</li>
     * <li>ArrayList with the units matching the requirements</li></ul>
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Unit> findRaceAndArmyTypeUnits(Races race ,ArmyType aT){
        Session session = getSession();
        ArrayList<Unit> units;
        units = (ArrayList<Unit>) session.getNamedQuery("Unit.getRaceAndArmyUnits")
                .setString("race", race.toString())
                .setString("aT", aT.toString())
                .list();
        session.close();
        return units;
    }

    /**
     * Method which gets all the equipment a unit can equip based on which
     * race and the available magic points. (common and race spesific items)
     * @param unitRace - The race of the unit
     * @param magicPoints - The number of points the unit can spend on magical items
     * @return A hash set with the equipment the unit can purchase
     */
    @SuppressWarnings("unchecked")
    public static Set<Equipment> getAllEquipment(Races unitRace, int magicPoints){
        Session session  = getSession();
        List<Equipment> equipment;
        equipment = (List<Equipment>) session.getNamedQuery("Equipment.all")
                .setString("race", unitRace.toString())
                .setInteger("cost", magicPoints)
                .list();
        session.close();
        return new HashSet<Equipment>(equipment);
    }

    /**
     * Method which gets all the equipment a unit can equip based on which
     * race and the available magic points. (race spesific items only)
     * @param unitRace - The race of the unit
     * @param magicPoints - The number of points the unit can spend on magical items
     * @return A hash set with the equipment the unit can purchase
     */
    @SuppressWarnings("unchecked")
    public static Set<Equipment> getRaceEquipment(Races unitRace, int magicPoints){
        Session session = getSession();
        List<Equipment> equipment;
        equipment = (List<Equipment>) session.getNamedQuery("Equipment.Race")
                .setString("race", unitRace.toString())
                .setInteger("cost", magicPoints)
                .list();
        session.close();
        return new HashSet<Equipment>(equipment);
    }

    /**
     * Method which aquires all eligible battle standards (used by
     * BSB's) for the supplied race. No point limitation is present when
     * creating a BSB.
     * @param unitRace Races The race of the army to get the BSB
     * @return The collection of eligible standards
     */
    @SuppressWarnings("unchecked")
    public static Set<Equipment> getBattleStandards(Races unitRace){
        Session session = getSession();
        String namedQuery;
        List<Equipment> standards;
        if(unitRace==Races.Dwarfs)
            namedQuery  = "Equipment.BSBRace";
        else
            namedQuery = "Equipment.BSBAll";
        standards = (List<Equipment>)session.getNamedQuery(namedQuery)
                    .setString("race", "Race:"+unitRace.toString())
                    .list();
        session.close();
        return new HashSet<Equipment>(standards);

    }
    @SuppressWarnings("unchecked")
    public static List<Unit> getRaceUnits(Races race){
        Session session = getSession();
        List<Unit> unit = (List<Unit>) session.getNamedQuery("Unit.getRaceUnits")
                .setString("race", race.toString())
                .list();
        return unit;
    }

    /**
     * Method which connects to the database and return a session
     * in which to access the database
     * @return A database session
     */
    private static Session getSession(){
        DatabaseManager dbm = DatabaseManager.getInstance();
        Connector conn = dbm.connect();
        return conn.getSession();
    }
}