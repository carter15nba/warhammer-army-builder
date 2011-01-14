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

package File;

import File.Resources.XML;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;

import Warhammer.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class XMLRaceWriter{
    public static final String TAB = "    ";
    private static final String[] attributes = new String[]{XML.MOVEMENT_ALLOWANCE,
    XML.WEAPON_SKILL,
    XML.BALLISTIC_SKILL,
    XML.STRENGTH,
    XML.TOUGHNESS,
    XML.WOUNDS,
    XML.INITIATIVE,
    XML.ATTACK,
    XML.LEADERSHIP};

    /**
     * This method writes a XML document representing one race and its units.
     * The file name is determined by the name of the race to be created and
     * any existing file will be overwritten.
     * @param race The race object holding all the information to be written to the XML.
     * @param author A String representation of the person creating the XML.
     */
    public void createDocument(Race race, String author){
        try {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter xtw = null;
            xtw = xof.createXMLStreamWriter(
                    new FileWriter(XML.DEFAULT_XML_LOCATION+"/"+race.getRaceName()+".xml"));
            writeStartOfDocument(xtw, race, author);
            writeUnits(xtw, race);
            xtw.writeCharacters("\n");
            xtw.writeEndElement();
            xtw.flush();
            xtw.close();

        }
        catch (XMLStreamException ex) {
            Logger.getLogger(XMLRaceWriter.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (IOException ex) {
            Logger.getLogger(XMLRaceWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method setup the XML document with the correct comments,
     * root element, namespace and reference to the validating xsd file.
     * @param xtw The XMLStreamWriter object used to write data to the XML.
     * @param race The race object whose data is being written to the XML.
     * @param author A String representing the author who created the XML.
     * @throws XMLStreamException
     */
    private void writeStartOfDocument(XMLStreamWriter xtw,
            Race race,
            String author)
            throws XMLStreamException{
        xtw.writeStartDocument("utf-8","1.0");
        xtw.writeCharacters("\n\n");
        xtw.writeComment("\n"+TAB+"Document   : "+race.getRaceName()+".xml\n"+
                TAB+"Created on : "+getDateAndTime()+"\n"+TAB+"Author"+
                TAB+" : "+author+"\n"+TAB+"Description:\n"+
                TAB+TAB+"This document contains data associated with a " +
                "Warhammer Race, its units\n"+TAB+TAB+"and the units "+
                "attributes. These data are used by the\n"+TAB+TAB+
                "WarhammerArmyBuilder software to populate the armies and "+
                "aquire the\n"+TAB+TAB+"neccessary information.\n");
        xtw.writeCharacters("\n\n");
        xtw.setPrefix("", "http://www.w3schools.com");
        xtw.writeStartElement("http://www.w3schools.com", XML.RACE);
        xtw.writeAttribute("xmlns", "http://www.w3schools.com");
        xtw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        xtw.writeAttribute("\nxsi:schemaLocation", "http://www.w3schools.com Warhammer.xsd");
        xtw.writeCharacters("\n"+TAB);
        xtw.writeStartElement(XML.NAME);
        xtw.writeCharacters(race.getRaceName());
        xtw.writeEndElement();
    }

    /**
     * This method writes all the units associated with the race, thorugh calls
     * to other dedicated methods is also the mount- and crew-data written for
     * each unit.
     * @param xtw The XMLStreanWriter object used to write data to the XML.
     * @param race The Race whose unit data is being written to the XML.
     * @throws XMLStreamException
     */
    private void writeUnits(XMLStreamWriter xtw, Race race)
            throws XMLStreamException{
        ArrayList<ArmyUnit> units = race.getUnits();
        for(ArmyUnit unit : units){
            xtw.writeCharacters("\n"+TAB);
            xtw.writeStartElement(XML.UNIT);
            xtw.writeCharacters("\n"+TAB+TAB);
            xtw.writeStartElement(XML.NAME);
            xtw.writeCharacters(unit.getUnitName());
            xtw.writeEndElement();
            for(int i = 0 ; i < attributes.length ; i++){
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeStartElement(attributes[i]);
                xtw.writeCharacters(String.valueOf(unit.getCharacteristic(i)));
                xtw.writeEndElement();
            }
            xtw.writeCharacters("\n"+TAB+TAB);
            xtw.writeStartElement(XML.TYPE);
            xtw.writeCharacters(unit.getStringCategory());
            xtw.writeEndElement();
            writeUtilityUnits(xtw, unit);
            xtw.writeCharacters("\n"+TAB);
            xtw.writeEndElement();
        }

    }

    /**
     * This method creates all the UtilityUnit sub-elements associated with the
     * current unit. If no utilityUnits exist the method exists.
     * @param xtw The XMLStreamWriter object used to write data to the XML.
     * @param unit The current unit in the Race object whose utilityUnit data is being written.
     * @throws XMLStreamException
     */
    private void writeUtilityUnits(XMLStreamWriter xtw, ArmyUnit unit)
            throws XMLStreamException{
                ArrayList<UtilityUnit> utilityUnits = unit.getUtilityUnits();
        if(utilityUnits!=null){
            for(UtilityUnit utilityUnit : utilityUnits){
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeStartElement(XML.UTILITY_UNIT);
                xtw.writeCharacters("\n"+TAB+TAB+TAB);
                xtw.writeStartElement(XML.NAME);
                xtw.writeCharacters(utilityUnit.getUnitName());
                xtw.writeEndElement();
                for(int i = 0 ; i < attributes.length ; i++){
                    xtw.writeCharacters("\n"+TAB+TAB+TAB);
                    xtw.writeStartElement(attributes[i]);
                    xtw.writeCharacters(String.valueOf(utilityUnit.getCharacteristic(i)));
                    xtw.writeEndElement();
                }
                xtw.writeCharacters("\n"+TAB+TAB+TAB);
                xtw.writeStartElement(XML.TYPE);
                xtw.writeCharacters(utilityUnit.getStringCategory());
                xtw.writeEndElement();
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeEndElement();
            }
        }
    }

    /**
     * This method aquires the current Date and Time from the system represented
     * as a string. The format of the string is: dd. MMMMMMMMM yyyy, HH:mm
     * which outputs e.g.: 13. january 2011, 09:10
     * @return String The current date and time.
     */
    public static String getDateAndTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMMMMMMM yyyy, HH:mm");
        return sdf.format(cal.getTime());

  }
}
