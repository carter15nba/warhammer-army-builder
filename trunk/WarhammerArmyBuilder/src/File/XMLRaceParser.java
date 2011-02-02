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
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import Warhammer.*;

/**
 * @author Glenn Rune Strandbåten
 * @verion 0.2
 */
public class XMLRaceParser extends DefaultHandler{

    private Race race;
    private String tempVal;
    private ArmyUnit unit = null;
    private UtilityUnit_old utilityUnit = null;
    private boolean inUnit = false;
    private boolean inUtilityUnit = false;

    /**
     * Method to parse a XML file representing a Warhammer race.
     * @param xmlFile File the XML file to be parsed.
     * @return The Race object resulting from parsing the XML file.
     */
    public Race parseDocument(java.io.File xmlFile) {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try{
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(xmlFile, this);
            return race;
        }
        catch(SAXException e){
            e.printStackTrace();
        }
        catch(ParserConfigurationException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method parses the start element tags in the XML file, and overrides
     * the startElement method in DefaultHandler.class.
     * @param uri
     * @param localName
     * @param qName The element name.
     * @param attributes The element attribute list.
     * @throws SAXException
     */
    @Override
    public void startElement(String uri,
            String localName,
            String qName,
            Attributes attributes)
            throws SAXException{
        tempVal = "";
        if(qName.equalsIgnoreCase(XML.RACE)){
            race = new Race();
        }
        else if(qName.equalsIgnoreCase(XML.UNIT)){
            unit = new ArmyUnit();
            race.addUnit(unit);
            inUnit = true;
            inUtilityUnit = false;
        }
        else if(qName.equalsIgnoreCase(XML.UTILITY_UNIT)){
            utilityUnit = new UtilityUnit_old();
            unit.addCrew(utilityUnit);
            inUnit = false;
            inUtilityUnit = true;
        }
    }

    /**
     * This method assignes the elements data to a string, and it overrides the
     * characters method in DefaultHandler.class.
     * @param ch The char[] array holding the element data.
     * @param start int The start position to read the element data from.
     * @param length int The stop position to stop reading the element data.
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch,start,length);
    }

    /**
     * This method reads all the child elements of the start elements found in
     * the startElements method. It overrides the endElement method in
     * DefaultHandler.class.
     * @param uri
     * @param localName
     * @param qName String The child element name.
     * @throws SAXException
     */
    @Override
    public void endElement(String uri,
            String localName,
            String qName)
            throws SAXException{
        if(qName.equalsIgnoreCase(XML.NAME)){
            if(inUnit)
                unit.setName(tempVal);
            else if(inUtilityUnit)
                utilityUnit.setName(tempVal);
            else
                race.assignRace(tempVal);
        }
        else if(qName.equalsIgnoreCase(XML.MOVEMENT_ALLOWANCE)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_MOVEMENT_ALLOVANCE,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_MOVEMENT_ALLOVANCE,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.WEAPON_SKILL)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_WEAPON_SKILL,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_WEAPON_SKILL,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.BALLISTIC_SKILL)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_BALLISTIC_SKILL,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_BALLISTIC_SKILL,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.STRENGTH)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_STRENGTH,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_STRENGTH,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.TOUGHNESS)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_TOUGHNESS,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_TOUGHNESS,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.WOUNDS)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_WOUNDS,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_WOUNDS,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.INITIATIVE)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_INITIATIVE,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_INITIATIVE,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.ATTACK)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_ATTACKS,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_ATTACKS,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.LEADERSHIP)){
            if(inUnit)
                unit.setCharacteristics(Unit_old.CHARACTHERISTIC_LEADERSHIP,
                    Integer.parseInt(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCharacteristics(Unit_old.CHARACTHERISTIC_LEADERSHIP,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase(XML.TYPE)){
            if(inUnit)
                unit.setCategory(parseType(tempVal));
            else if(inUtilityUnit)
                utilityUnit.setCategory(parseType(tempVal));
        }
    }

    /**
     * This method parses the Type string into the corresponding Category
     * integer value in the Unit class.
     * @param tempVal The string value to parse into an integer.
     * @return int representing one of the Unit.CATEGORY_ values or Unit.VALUE_NOT_FOUND if no valid parse were found.
     */
    private int parseType(String tempVal){
        if(tempVal.equalsIgnoreCase("Ca"))
            return Unit_old.CATEGORY_CAVALRY;
        else if(tempVal.equalsIgnoreCase("Ch"))
            return Unit_old.CATEGORY_CHARIOT;
        else if(tempVal.equalsIgnoreCase("In"))
            return Unit_old.CATEGORY_INFANTRY;
        else if(tempVal.equalsIgnoreCase("Mo"))
            return Unit_old.CATEGORY_MONSTER;
        else if(tempVal.equalsIgnoreCase("MB"))
            return Unit_old.CATEGORY_MONSTROUS_BEAST;
        else if(tempVal.equalsIgnoreCase("MC"))
            return Unit_old.CATEGORY_MONSTROUS_CAVALRY;
        else if(tempVal.equalsIgnoreCase("MI"))
            return Unit_old.CATEGORY_MONSTROUS_INFANTRY;
        else if(tempVal.equalsIgnoreCase("Sw"))
            return Unit_old.CATEGORY_SWARM;
        else if(tempVal.equalsIgnoreCase("Un"))
            return Unit_old.CATEGORY_UNIQUE_UNIT;
        else if(tempVal.equalsIgnoreCase("WB"))
            return Unit_old.CATEGORY_WAR_BEAST;
        else if(tempVal.equalsIgnoreCase("WM"))
            return Unit_old.CATEGORY_WAR_MACHINE;
        return Unit_old.VALUE_NOT_FOUND;
    }
}
