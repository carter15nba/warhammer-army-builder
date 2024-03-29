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

package myrmidia.Database;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CaseComponent;
import org.hibernate.SessionFactory;
import jcolibri.connector.DataBaseConnector.*;
import jcolibri.exception.InitializingException;
import jcolibri.util.FileIO;
import myrmidia.CBR.CBREngine;
import myrmidia.CBR.Resources.ArmyChangeStatus;
import myrmidia.Enums.Outcomes;
import myrmidia.Util.CreateObjectFromDB;
import myrmidia.Warhammer.Army;
import myrmidia.Warhammer.ArmyUnit;
import myrmidia.Warhammer.Case;
import myrmidia.Warhammer.Equipment;
import myrmidia.Warhammer.UtilityUnit;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;

/**
 * Overrides most of the methods in the super class with the exact same
 * method, to be able to aquire one additional feature without impair original
 * functionality.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class Connector extends jcolibri.connector.DataBaseConnector{

    private SessionFactory sessionFactory;
    private String descriptionClassName;
    private String solutionClassName;
    private String justOfSolutionClassName;
    private String resultClassName;
    private static final String ARMIES_QUERY =
        "insert into ARMIES(ID,PLAYER_RACE,ARMY_POINTS) values (?,?,?)";
    private static final String ARMY_UNITS_QUERY = 
        "insert into ARMY_UNIT(ID,ARMY_ID,UNIT_NAME,NUM_UNITS) values(?,?,?,?)";
    private static final String ARMY_UNIT_UT_QUERY =
        "insert into ARMY_UNIT_UTILITY(ARMY_UNIT_ID,UTILITY_ID)values(?,?)";
    private static final String ARMY_UNIT_EQ_QUERY =
        "insert into ARMY_UNIT_EQUIPMENT(ARMY_UNIT_ID,EQUIPMENT_ID)values(?,?)";
    private static final String CASE_QUERY =
        "insert into CASES(ID,ARMY_ID,OPPONENT_RACE,OUTCOME) values(?,?,?,?)";
    private static final String CASE_UPDATE_OUTCOME_QUERY =
            "UPDATE CASES SET OUTCOME=(?) WHERE ID=(?)";
    private static final String DELETE_CASE_QUERY =
            "DELETE FROM CASES WHERE ID=(?)";
    private static final String DELETE_ARMY_QUERY = 
            "DELETE FROM ARMIES WHERE ID=(?)";
    private static final String DELETE_ARMY_UNIT_QUERY =
            "DELETE FROM ARMY_UNIT WHERE ARMY_ID=(?)";
    private static final String DELETE_ARMY_UNIT_EQ_QUERY =
            "DELETE FROM ARMY_UNIT_EQUIPMENT WHERE ARMY_UNIT_ID=(?)";
    private static final String DELETE_ARMY_UNIT_UT_QUERY =
            "DELETE FROM ARMY_UNIT_UTILITY WHERE ARMY_UNIT_ID=(?)";

    /**
     * Default constructor - initializes the super class
     */
    public Connector(){
        super();
    }

    /**
     * Method to aquire a session from the connection
     * @return A Session object
     */
    public Session getSession(){
        return sessionFactory.openSession();
    }    

    /**
     * This mehtod is used to override a hard-coded parameter in the
     * hibernate.cfg.xml file to give a the application a limited altered
     * configuration. Used to permit hibernate to create non-existing tables.
     * @param file URL the url path to the hibernate.cfg.xml file
     * @throws InitializingException
     */
    public void initOverwriteFromXMLFile(URL file) throws InitializingException{
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse( file.openStream() );

            String hcf = document.getElementsByTagName("HibernateConfigFile").item(0).getTextContent();

            String descriptionMapFile = document.getElementsByTagName("DescriptionMappingFile").item(0).getTextContent();
            descriptionClassName = document.getElementsByTagName("DescriptionClassName").item(0).getTextContent();

            Configuration hbconfig = new Configuration();
            hbconfig.configure(FileIO.findFile(hcf));
            hbconfig.addURL(FileIO.findFile(descriptionMapFile));

            try{
                String solutionMapFile = document.getElementsByTagName("SolutionMappingFile").item(0).getTextContent();
                solutionClassName = document.getElementsByTagName("SolutionClassName").item(0).getTextContent();
                hbconfig.addResource(solutionMapFile);
            }
            catch(Exception e) {
                LogFactory.getLog(this.getClass()).info("Case does not have solution");
            }

            try{
                String justOfSolutionMapFile = document.getElementsByTagName("JustificationOfSolutionMappingFile").item(0).getTextContent();
                justOfSolutionClassName = document.getElementsByTagName("JustificationOfSolutionClassName").item(0).getTextContent();
                hbconfig.addResource(justOfSolutionMapFile);
            }
            catch(Exception e) {
                LogFactory.getLog(this.getClass()).info("Case does not have justification of the solution");
            }

            try{
                String resultMapFile = document.getElementsByTagName("ResultMappingFile").item(0).getTextContent();
                resultClassName = document.getElementsByTagName("ResultClassName").item(0).getTextContent();
                hbconfig.addResource(resultMapFile);
            }
            catch(Exception e){
                LogFactory.getLog(this.getClass()).info("Case does not have result");
            }
            hbconfig.setProperty("hibernate.hbm2ddl.auto", "update");
            String currentProperty = hbconfig.getProperty("hibernate.connection.url");
            currentProperty += ";create=true";
            hbconfig.setProperty("hibernate.connection.url", currentProperty);
            sessionFactory = hbconfig.buildSessionFactory();
        }
        catch (Throwable ex) {
            throw new InitializingException(ex);
        }
    }


    /* (non-Javadoc)
     * @see jcolibri.cbrcore.Connector#initFromXMLfile(java.io.File)
     */
    @Override
    public void initFromXMLfile(URL file) throws InitializingException {
        try {         
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse( file.openStream() );

            String hcf = document.getElementsByTagName("HibernateConfigFile").item(0).getTextContent();

            String descriptionMapFile = document.getElementsByTagName("DescriptionMappingFile").item(0).getTextContent();
            descriptionClassName = document.getElementsByTagName("DescriptionClassName").item(0).getTextContent();

            Configuration hbconfig = new Configuration();
            hbconfig.configure(FileIO.findFile(hcf));
            hbconfig.addURL(FileIO.findFile(descriptionMapFile));

            try{
                String solutionMapFile = document.getElementsByTagName("SolutionMappingFile").item(0).getTextContent();
                solutionClassName = document.getElementsByTagName("SolutionClassName").item(0).getTextContent();
                hbconfig.addResource(solutionMapFile);
            }
            catch(Exception e) {
                LogFactory.getLog(this.getClass()).info("Case does not have solution");
            }

            try{
                String justOfSolutionMapFile = document.getElementsByTagName("JustificationOfSolutionMappingFile").item(0).getTextContent();
                justOfSolutionClassName = document.getElementsByTagName("JustificationOfSolutionClassName").item(0).getTextContent();
                hbconfig.addResource(justOfSolutionMapFile);
            }
            catch(Exception e) {
                LogFactory.getLog(this.getClass()).info("Case does not have justification of the solution");
            }

            try{
                String resultMapFile = document.getElementsByTagName("ResultMappingFile").item(0).getTextContent();
                resultClassName = document.getElementsByTagName("ResultClassName").item(0).getTextContent();
                hbconfig.addResource(resultMapFile);
            }
            catch(Exception e){
                LogFactory.getLog(this.getClass()).info("Case does not have result");
            }
            sessionFactory = hbconfig.buildSessionFactory();
        }
        catch (Throwable ex) {
            throw new InitializingException(ex);
        }
    }

    /* (non-Javadoc)
     * @see jcolibri.cbrcore.Connector#retrieveAllCases()
     */
    @Override
    public Collection<CBRCase> retrieveAllCases(){
        java.util.ArrayList<CBRCase> res = new java.util.ArrayList<CBRCase>();
        try {
            Session session;
            Transaction transaction;

            List descList = null;
            HashMap<Object, CaseComponent> solList = null;
            HashMap<Object, CaseComponent> justSolList = null;
            HashMap<Object, CaseComponent> resList = null;

            if(solutionClassName != null) {
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();

                solList = new HashMap<Object, CaseComponent>();
                List l = session.createQuery("select cc from " + solutionClassName + " cc where cc.outcome <> 'Unknown'").list();

                transaction.commit();
                session.close();

                for(Iterator iter = l.iterator(); iter.hasNext();)
                {
                    CaseComponent cc = (CaseComponent)iter.next();
                    solList.put(cc.getIdAttribute().getValue(cc), cc);
                }
            }
            if(justOfSolutionClassName != null) {
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();

                justSolList = new HashMap<Object, CaseComponent>();
                List l = session.createQuery("select cc from " + justOfSolutionClassName + " cc where cc.outcome <> 'Unknown'").list();
                transaction.commit();
                session.close();

                for(Iterator iter = l.iterator(); iter.hasNext();) {
                    CaseComponent cc = (CaseComponent)iter.next();
                    justSolList.put(cc.getIdAttribute().getValue(cc), cc);
                }
            }
            if(resultClassName != null) {
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();

                resList = new HashMap<Object, CaseComponent>();
                List l = session.createQuery("select cc from " + resultClassName + " cc where cc.outcome <> 'Unknown'").list();
                transaction.commit();
                session.close();

                for(Iterator iter = l.iterator(); iter.hasNext();) {
                        CaseComponent cc = (CaseComponent)iter.next();
                        resList.put(cc.getIdAttribute().getValue(cc), cc);
                }
            }

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            descList = session.createQuery("select cc from " + descriptionClassName + " cc where cc.outcome <> 'Unknown'").list();
            transaction.commit();
            session.close();

            for(Iterator iter = descList.iterator(); iter.hasNext();) {
                CBRCase _case = new CBRCase();
                CaseComponent desc = (CaseComponent)iter.next();
                _case.setDescription(desc);

                if(solutionClassName != null) {
                    CaseComponent cc = solList.get(desc.getIdAttribute().getValue(desc));
                    if(cc != null)
                        _case.setSolution(cc);
                }
                if(justOfSolutionClassName != null) {
                    CaseComponent cc = justSolList.get(desc.getIdAttribute().getValue(desc));
                    if(cc != null)
                        _case.setJustificationOfSolution(cc);
                }
                if(resultClassName != null) {
                    CaseComponent cc = resList.get(desc.getIdAttribute().getValue(desc));
                    if(cc != null)
                        _case.setResult(cc);
                }

                res.add(_case);
            }
        }
        catch (Exception e) {
            LogFactory.getLog(this.getClass()).error(e);
        }
        LogFactory.getLog(this.getClass()).info(res.size()+
                " cases read from the database.");
        return res;
    }

    /* (non-Javadoc)
     * @see jcolibri.cbrcore.Connector#storeCases(java.util.Collection)
     */
    @Override
    public void storeCases(Collection<CBRCase> cases) {
        CBREngine engine = CBREngine.getInstance();
        for(CBRCase c: cases)
        {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ArmyChangeStatus changeStatus = engine.getArmyChangeStatus(c);
            if(c.getSolution()!= null && !changeStatus.isUnchanged()){
                saveFullCase(session, c.getSolution());
                transaction.commit();
            }
            else if(c.getSolution()!= null && changeStatus.isUnchanged()){
                saveCase(session, c.getSolution());
            }
            session.close();
        }
        LogFactory.getLog(this.getClass()).info(cases.size()+
                " cases stored into the database.");
    }

    /**
     * Method which overrides the default hibernate saving scheme. This method
     * forces the connection to save only those parts of the case which is
     * neccessary in the neccessay order, by taking the saving power away from
     * hibernate and executing regular SQL's. <strong>NOTE:</strong> This method
     * saves the entire case (case, army and equipment/utility)
     * @param session The session with an open connection to the database
     * @param description The CaseComponent to be saved to the database.
     */
    private void saveFullCase(Session session, CaseComponent description) {
        System.out.println("SAVE FULL CASE");
        Case _case = (Case) description;
        Army army = _case.getArmy();
        //Create the army entry
        session.createSQLQuery(ARMIES_QUERY)
                .setInteger(0, army.getID())
                .setString(1, army.getPlayerRace().toString())
                .setInteger(2, army.getArmyPoints())
                .executeUpdate();

        //Create the army unit entries (with equipment and utility units)
        Set<ArmyUnit> armyUnits = army.getArmyUnits();
        for (ArmyUnit armyUnit : armyUnits) {
            int id = armyUnit.getID();
            session.createSQLQuery(ARMY_UNITS_QUERY)
                    .setInteger(0, id)
                    .setInteger(1, armyUnit.getArmyID())
                    .setString(2, armyUnit.getUnit().getName())
                    .setInteger(3, armyUnit.getNumberOfUnits())
                    .executeUpdate();
            //Create the utility unit entries
            Set<UtilityUnit> utSet = armyUnit.getUtility();
            for (UtilityUnit uu : utSet) {
                session.createSQLQuery(ARMY_UNIT_UT_QUERY)
                        .setInteger(0, id)
                        .setInteger(1, uu.getID())
                        .executeUpdate();
            }
            //Create the equipment entries
            Set<Equipment> eqSet = armyUnit.getEquipment();
            for (Equipment eq : eqSet) {
                session.createSQLQuery(ARMY_UNIT_EQ_QUERY)
                        .setInteger(0, id)
                        .setInteger(1, eq.getID())
                        .executeUpdate();
            }

        }
        //Create the case associated with this army
        session.createSQLQuery(CASE_QUERY)
                .setInteger(0, _case.getID())
                .setInteger(1, army.getID())
                .setString(2, _case.getOpponent().toString())
                .setString(3, _case.getOutcome().toString())
                .executeUpdate();
    }
    
    /**
     * Method which overrides the default hibernate saving scheme. This method
     * forces the connection to save only those parts of the case which is
     * neccessary in the neccessay order, by taking the saving power away from
     * hibernate and executing regular SQL's. <strong>NOTE:</strong> This method
     * saves only te case. The army and equipment/utility is allready in the 
     * database.
     * @param session The session with an open connection to the database
     * @param description The CaseComponent to be saved to the database.
     */
    private void saveCase(Session session, CaseComponent description){
        System.out.println("SAVE CASE ONLY");
        Case _case = (Case) description;
        Army army = _case.getArmy();
        session.createSQLQuery(CASE_QUERY)
                .setInteger(0, _case.getID())
                .setInteger(1, army.getID())
                .setString(2, _case.getOpponent().toString())
                .setString(3, _case.getOutcome().toString())
                .executeUpdate();
    }

    /**
     * Method to update the outcome of a case
     * @param caseID int The case ID of the case to be updated
     * @param newOutcome Outcome The outcome to update the case with
     */
    public void updateOutcome(int caseID, Outcomes newOutcome){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(CASE_UPDATE_OUTCOME_QUERY)
                .setString(0, newOutcome.toString())
                .setInteger(1, caseID)
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    /**
     * Method to delete a case with the given case ID from the database. The
     * method is intelligent and will delete the entire army
     * (army units, unit equipment and utility units) from the database if this
     * is the single case in the casebase utilizing this army.
     * @param caseID int The ID of the case to be deleted
     */
    public void deleteCase(int caseID){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Case _case = CreateObjectFromDB.getCaseByID(caseID);
        List<Case> caseList = CreateObjectFromDB.getCasesWithArmyID(
                _case.getArmy().getID());
        boolean deleteArmy = true;
        if(caseList.size()>1)
            deleteArmy=false;
        //Ensure that the army only is deleted if it not is used in other case
        //Also ensure that the deletion is done in the correct order to prevent
        //foreign key constraint violations
        if(deleteArmy){
            Army army = _case.getArmy();
            Set<ArmyUnit> units = army.getArmyUnits();
            //Delete all the equipment and utility units for all the units
            //in the case
            for (ArmyUnit armyUnit : units) {
                session.createSQLQuery(DELETE_ARMY_UNIT_EQ_QUERY)
                        .setInteger(0, armyUnit.getID())
                        .executeUpdate();
                session.createSQLQuery(DELETE_ARMY_UNIT_UT_QUERY)
                        .setInteger(0, armyUnit.getID())
                        .executeUpdate();
            }
            //Delete all units associated with the army
            session.createSQLQuery(DELETE_ARMY_UNIT_QUERY)
                    .setInteger(0, army.getID())
                    .executeUpdate();
        }
        //Delete the case
        session.createSQLQuery(DELETE_CASE_QUERY)
                .setInteger(0, caseID)
                .executeUpdate();
        //If applicable delete the last remains of the army
        if(deleteArmy){
            Army army = _case.getArmy();
            session.createSQLQuery(DELETE_ARMY_QUERY)
                    .setInteger(0, army.getID())
                    .executeUpdate();
        }
        transaction.commit();
        session.close();
    }
}
