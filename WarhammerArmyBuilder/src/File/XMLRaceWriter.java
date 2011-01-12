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

package File;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

import Warhammer.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author Glenn Rune
 */
public class XMLRaceWriter {
    public static final String TAB = "    ";
    private static final String[] attributes = new String[]{"Movement",
    "WeaponSkill",
    "BallisticSkill",
    "Strength",
    "Toughness",
    "Wounds",
    "Initiative",
    "Attack",
    "Leadership"};
    
    public void createDocument(Race race, String author){
        try {
            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            XMLStreamWriter xtw = null;
            xtw = xof.createXMLStreamWriter(
                    new FileWriter(race.getRaceName()+".xml"));
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
    private void writeStartOfDocument(XMLStreamWriter xtw,
            Race race,
            String author)
            throws XMLStreamException{
        xtw.writeStartDocument("utf-8","1.0");
        xtw.writeCharacters("\n\n");
        xtw.writeComment("\n    Document   : "+race.getRaceName()+".xml\n"+
                TAB+"Created on : "+now()+"\n"+TAB+"Author"+
                TAB+" : "+author+"\n"+TAB+"Description:\n"+
                TAB+TAB+"This document contains data associated with a " +
                "Warhammer Race, its units\n"+TAB+TAB+"and the units "+
                "attributes. These data are used by the\n"+TAB+TAB+
                "WarhammerArmyBuilder software to populate the armies and "+
                "aquire the\n"+TAB+TAB+"neccessary information.\n");
        xtw.writeCharacters("\n\n");
        xtw.setPrefix("", "http://www.w3schools.com");
        xtw.writeStartElement("http://www.w3schools.com", "Race");
        xtw.writeAttribute("xmlns", "http://www.w3schools.com");
        //xtw.writeCharacters("\n");
        xtw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        //xtw.writeCharacters("\n");
        xtw.writeAttribute("\nxsi:schemaLocation", "http://www.w3schools.com Warhammer.xsd");
        xtw.writeCharacters("\n"+TAB);
        xtw.writeStartElement("RaceName");
        xtw.writeCharacters(race.getRaceName());
        xtw.writeEndElement();
    }
    private void writeUnits(XMLStreamWriter xtw, Race race)
            throws XMLStreamException{
        ArrayList<ArmyUnit> units = race.getUnits();
        for(ArmyUnit unit : units){
            xtw.writeCharacters("\n"+TAB);
            xtw.writeStartElement("Unit");
            xtw.writeCharacters("\n"+TAB+TAB);
            xtw.writeStartElement("UnitName");
            xtw.writeCharacters(unit.getUnitName());
            xtw.writeEndElement();
            for(int i = 0 ; i < attributes.length ; i++){
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeStartElement(attributes[i]);
                xtw.writeCharacters(String.valueOf(unit.getCharacteristic(i)));
                xtw.writeEndElement();
            }
            xtw.writeCharacters("\n"+TAB+TAB);
            xtw.writeStartElement("Type");
            xtw.writeCharacters(unit.getStringCategory());
            xtw.writeEndElement();
            writeMounts(xtw,unit);
            writeCrews(xtw, unit);
            xtw.writeCharacters("\n"+TAB);
            xtw.writeEndElement();
        }

    }
    private void writeMounts(XMLStreamWriter xtw, ArmyUnit unit)
            throws XMLStreamException{
        ArrayList<Mount> mounts = unit.getMount();
        if(mounts!=null){
            for(Mount mount : mounts){
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeStartElement("Mount");
                xtw.writeCharacters("\n"+TAB+TAB+TAB);
                xtw.writeStartElement("MountName");
                xtw.writeCharacters(mount.getUnitName());
                xtw.writeEndElement();
                for(int i = 0 ; i < attributes.length ; i++){
                    xtw.writeCharacters("\n"+TAB+TAB+TAB);
                    xtw.writeStartElement(attributes[i]);
                    xtw.writeCharacters(String.valueOf(mount.getCharacteristic(i)));
                    xtw.writeEndElement();
                }
                xtw.writeCharacters("\n"+TAB+TAB+TAB);
                xtw.writeStartElement("Type");
                xtw.writeCharacters(mount.getStringCategory());
                xtw.writeEndElement();
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeEndElement();
            }
        }
    }
    private void writeCrews(XMLStreamWriter xtw, ArmyUnit unit)
            throws XMLStreamException{
                ArrayList<Crew> crews = unit.getCrew();
        if(crews!=null){
            for(Crew crew : crews){
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeStartElement("Crew");
                xtw.writeCharacters("\n"+TAB+TAB+TAB);
                xtw.writeStartElement("CrewName");
                xtw.writeCharacters(crew.getUnitName());
                xtw.writeEndElement();
                for(int i = 0 ; i < attributes.length ; i++){
                    xtw.writeCharacters("\n"+TAB+TAB+TAB);
                    xtw.writeStartElement(attributes[i]);
                    xtw.writeCharacters(String.valueOf(crew.getCharacteristic(i)));
                    xtw.writeEndElement();
                }
                xtw.writeCharacters("\n"+TAB+TAB+TAB);
                xtw.writeStartElement("Type");
                xtw.writeCharacters(crew.getStringCategory());
                xtw.writeEndElement();
                xtw.writeCharacters("\n"+TAB+TAB);
                xtw.writeEndElement();
            }
        }

    }
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMMMMMMM yyyy, HH:mm");
        return sdf.format(cal.getTime());

  }
}
