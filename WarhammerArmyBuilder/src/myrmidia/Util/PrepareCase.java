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

import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import myrmidia.Database.Connector;
import myrmidia.Database.DatabaseManager;
import myrmidia.Warhammer.Army;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Case;
import org.hibernate.Session;

/**
 * Singleton class: prepares a case by iterating though the case and assigning
 * new valid ID's on all required objects. This class should be thread safe
 * as a mutex semaphore is used both during class creation and during
 * preparataion to avoid anyone accessing/changing the internal memory.
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class PrepareCase {

    private static final int FETCH_ID = -10000;
    private final Semaphore MUTEX = new Semaphore(1);
    private int nextCaseID;
    private int nextArmyID;
    private int nextArmyUnitID;

    /**
     * Default private constructor
     * Use PrepareCase.getInstance() to aquire an instance of this object.
     */
    private PrepareCase(){
        nextArmyID = FETCH_ID;
        nextArmyUnitID = FETCH_ID;
        nextCaseID = FETCH_ID;
        initializePrepareCase();
    }
    /**
     * Method used to aquire the singleton instance of the PrepareCase.
     * @return the PrepareCase singleton object reference.
     */
    public static PrepareCase getInstance(){
        return PrepareCaseHolder.INSTANCE;
    }
    private static class PrepareCaseHolder{
        private static final PrepareCase INSTANCE = new PrepareCase();
    }

    /**
     * Method which initializes the object and aquires the latest ID's from
     * the database. 
     */
    private void initializePrepareCase(){
        try {
            MUTEX.acquire();
        }
        catch (InterruptedException ex) {return;}
        DatabaseManager dbm = DatabaseManager.getInstance();
        Connector connector = dbm.connect();
        Session session;
        if(nextCaseID==FETCH_ID){
            session = connector.getSession();
            List l = session.getNamedQuery("Case.LastId")
                    .setMaxResults(1).list();
            nextCaseID = (Integer) l.get(0);
            nextCaseID++;
            session.close();
        }
        if(nextArmyID==FETCH_ID){
            session = connector.getSession();
            List l = session.getNamedQuery("Army.LastId")
                    .setMaxResults(1).list();
            nextArmyID = (Integer) l.get(0);
            nextArmyID++;
            session.close();
        }
        if(nextArmyUnitID==FETCH_ID){
            session = connector.getSession();
            List l = session.getNamedQuery("ArmyUnit.LastId")
                    .setMaxResults(1).list();
            nextArmyUnitID = (Integer) l.get(0);
            nextArmyUnitID++;
            session.close();
        }
        MUTEX.release();
    }

    /**
     * Method which prepares the case by giving the case a new unique ID,
     * and a new unique ID for the Army and ArmyUnits
     * @param _case The case object to prepare
     */
    public void prepareCase(Case _case){
        try {
            MUTEX.acquire();
        }
        catch (InterruptedException ex) {return;}
        _case.setID(nextCaseID);
        prepareArmy(_case.getArmy());
        nextCaseID++;
        MUTEX.release();

    }

    /**
     * Method which prepares the Army by giving it a new ID, and gives
     * a new ID for all ArmyUnits.
     * @param army The army object to prepare
     */
    private void prepareArmy(Army army){
        army.setID(nextArmyID);
        prepareArmyUnits(army.getArmyUnits());
        nextArmyID++;
    }

    /**
     * Method which prepares the ArmyUnit by giving it a new ID
     * @param armyUnits The army unit object to prepare
     */
    private void prepareArmyUnits(Set<ArmyUnit> armyUnits) {
        for (ArmyUnit armyUnit : armyUnits) {
            armyUnit.setArmyID(nextArmyID);
            armyUnit.setID(nextArmyUnitID++);
        }
    }
}