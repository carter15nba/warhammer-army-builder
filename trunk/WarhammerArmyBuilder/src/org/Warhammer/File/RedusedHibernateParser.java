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

package org.Warhammer.File;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class RedusedHibernateParser extends DefaultHandler{
    private String tempVal;
    private enum property{driver,url,username,password,none};
    private property type;
    private Properties hibernateProperties;

    public RedusedHibernateParser(){
        hibernateProperties = new Properties();
        type = property.none;
    }
    public Properties parseHibernate(String path){
        try {

            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            URL file = jcolibri.util.FileIO.findFile(path);
            java.io.File hibernateFile = new java.io.File(file.toURI());
            parser.parse(hibernateFile, this);
            return hibernateProperties;
        }
        catch (IOException ex) {
            Logger.getLogger(RedusedHibernateParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(RedusedHibernateParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ParserConfigurationException ex) {
            Logger.getLogger(RedusedHibernateParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SAXException ex) {
            Logger.getLogger(RedusedHibernateParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    @Override
    public void startElement(String uri,
            String localName,
            String qName,
            Attributes attributes)
            throws SAXException{
        tempVal = "";
        if(qName.equalsIgnoreCase("property")){
            String attr = attributes.getValue("name");
            if(attr.equalsIgnoreCase("hibernate.connection.driver_class"))
                type=property.driver;
            else if(attr.equalsIgnoreCase("hibernate.connection.url"))
                type=property.url;
            else if(attr.equalsIgnoreCase("hibernate.connection.username"))
                type=property.username;
            else if(attr.equalsIgnoreCase("hibernate.connection.password"))
                type=property.password;
            else
                type=property.none;
        }
        else
            type=property.none;
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch,start,length);
    }
    @Override
    public void endElement(String uri,
            String localName,
            String qName)
            throws SAXException{
        switch(type){
            case driver:
                hibernateProperties.put("Driver", tempVal);
                break;
            case url:
                hibernateProperties.put("URL", tempVal);
                break;
            case username:
                hibernateProperties.put("Username", tempVal);
                break;
            case password:
                hibernateProperties.put("Password", tempVal);
                break;
            case none:
                break;
        }
    }
}
