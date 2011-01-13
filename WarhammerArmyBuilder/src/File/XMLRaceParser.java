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

import java.io.File;
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
    private Mount mount = null;
    private Crew crew = null;
    private boolean inUnit = false;
    private boolean inMount = false;
    private boolean inCrew = false;

    /**
     * Method to parse a XML file representing a Warhammer race.
     * @param xmlFile File the XML file to be parsed.
     * @return The Race object resulting from parsing the XML file.
     */
    public Race parseDocument(File xmlFile) {
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
        if(qName.equalsIgnoreCase("Race")){
            race = new Race();
        }
        else if(qName.equalsIgnoreCase("Unit")){
            unit = new ArmyUnit();
            race.addUnit(unit);
            inUnit = true;
            inMount = false;
            inCrew = false;
        }
        else if(qName.equalsIgnoreCase("Mount")){
            mount = new Mount();
            unit.addMount(mount);
            inUnit = false;
            inMount = true;
            inCrew = false;
        }
        else if(qName.equalsIgnoreCase("Crew")){
            crew = new Crew();
            unit.addCrew(crew);
            inUnit = false;
            inMount = false;
            inCrew = true;
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
        if(qName.equalsIgnoreCase("RaceName")){
            race.assignRace(tempVal);
        }
        else if(qName.equalsIgnoreCase("UnitName")){
            unit.setName(tempVal);
        }
        else if(qName.equalsIgnoreCase("CrewName")){
            crew.setName(tempVal);
        }
        else if(qName.equalsIgnoreCase("MountName")){
            mount.setName(tempVal);
        }
        else if(qName.equalsIgnoreCase("Movement")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_MOVEMENT_ALLOVANCE,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_MOVEMENT_ALLOVANCE,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_MOVEMENT_ALLOVANCE,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("WeaponSkill")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_WEAPON_SKILL,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_WEAPON_SKILL,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_WEAPON_SKILL,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("BallisticSkill")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_BALLISTIC_SKILL,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_BALLISTIC_SKILL,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_BALLISTIC_SKILL,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("Strength")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_STRENGTH,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_STRENGTH,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_STRENGTH,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("Toughness")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_TOUGHNESS,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_TOUGHNESS,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_TOUGHNESS,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("Wounds")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_WOUNDS,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_WOUNDS,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_WOUNDS,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("Initiative")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_INITIATIVE,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_INITIATIVE,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_INITIATIVE,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("Attack")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_ATTACKS,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_ATTACKS,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_ATTACKS,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("Leadership")){
            if(inUnit)
                unit.setCharacteristics(Unit.CHARACTHERISTIC_LEADERSHIP,
                    Integer.parseInt(tempVal));
            else if(inCrew)
                crew.setCharacteristics(Unit.CHARACTHERISTIC_LEADERSHIP,
                    Integer.parseInt(tempVal));
            else if(inMount)
                mount.setCharacteristics(Unit.CHARACTHERISTIC_LEADERSHIP,
                    Integer.parseInt(tempVal));
        }
        else if(qName.equalsIgnoreCase("TYPE")){
            if(inUnit)
                unit.setCategory(parseType(tempVal));
            else if(inCrew)
                crew.setCategory(parseType(tempVal));
            else  if(inMount)
                mount.setCategory(parseType(tempVal));
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
            return Unit.CATEGORY_CAVALRY;
        else if(tempVal.equalsIgnoreCase("Ch"))
            return Unit.CATEGORY_CHARIOT;
        else if(tempVal.equalsIgnoreCase("In"))
            return Unit.CATEGORY_INFANTRY;
        else if(tempVal.equalsIgnoreCase("Mo"))
            return Unit.CATEGORY_MONSTER;
        else if(tempVal.equalsIgnoreCase("MB"))
            return Unit.CATEGORY_MONSTROUS_BEAST;
        else if(tempVal.equalsIgnoreCase("MC"))
            return Unit.CATEGORY_MONSTROUS_CAVALRY;
        else if(tempVal.equalsIgnoreCase("MI"))
            return Unit.CATEGORY_MONSTROUS_INFANTRY;
        else if(tempVal.equalsIgnoreCase("Sw"))
            return Unit.CATEGORY_SWARM;
        else if(tempVal.equalsIgnoreCase("Un"))
            return Unit.CATEGORY_UNIQUE_UNIT;
        else if(tempVal.equalsIgnoreCase("WB"))
            return Unit.CATEGORY_WAR_BEAST;
        else if(tempVal.equalsIgnoreCase("WM"))
            return Unit.CATEGORY_WAR_MACHINE;
        return Unit.VALUE_NOT_FOUND;
    }
}