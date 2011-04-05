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

package org.Warhammer.Database;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CaseComponent;
import org.hibernate.SessionFactory;
import jcolibri.connector.DataBaseConnector.*;
import jcolibri.exception.InitializingException;
import jcolibri.util.FileIO;
import org.Warhammer.Warhammer.Case;
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
 * @version 0.2
 */
public class Connector extends jcolibri.connector.DataBaseConnector{

    private SessionFactory sessionFactory;
    private String descriptionClassName;
    private String solutionClassName;
    private String justOfSolutionClassName;
    private String resultClassName;
    public Connector(){
        super();
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }    


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
        for(CBRCase c: cases)
        {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            saveCase(session, c.getDescription());
            transaction.commit();
            session.close();

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if(c.getSolution()!= null)
                saveCase(session, c.getSolution());
            transaction.commit();
            session.close();

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if(c.getJustificationOfSolution() != null)
                saveCase(session, c.getJustificationOfSolution());
            transaction.commit();
            session.close();

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if(c.getResult() != null)
                saveCase(session, c.getResult());
                transaction.commit();
                session.close();
        }
        LogFactory.getLog(this.getClass()).info(cases.size()+
                " cases stored into the database.");
    }

    public boolean isConnected(){
        return sessionFactory.getCurrentSession().isConnected();
    }

    private void saveCase(Session session, CaseComponent description) {
       Case _case = (Case) description;
       session.save(_case.getArmy());
       session.save(_case);
    }
}
