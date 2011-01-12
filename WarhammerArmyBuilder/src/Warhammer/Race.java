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

package Warhammer;

import java.util.ArrayList;



public class Race {
//	public static final int RACE_BRETONNIA = 10;
//	public static final int RACE_DWARFS = 11;
//	public static final int RACE_TOMB_KINGS = 12;
//	public static final int RACE_EMPIRE = 13;
//	public static final int RACE_HIGH_ELVES = 14;
//	public static final int RACE_WOOD_ELVES = 15;
//	public static final int RACE_LIZARDMEN = 16;
//	public static final int RACE_OGRE_KINGDOMS = 17;
//	public static final int RACE_DARK_ELVES = 18;
//	public static final int RACE_SKAVEN = 19;
//	public static final int RACE_BEASTMEN = 20;
//	public static final int RACE_ORCS_AND_GOBLINS = 21;
//	public static final int RACE_DAEMONS_OF_CHAOS = 22;
//	public static final int RACE_VAMPIRE_COUNTS = 23;
//	public static final int RACE_ARCANE_CREATURES = 24;
//	public static final int RACE_WARRIORS_OF_CHAOS = 25;
	protected ArrayList<ArmyUnit> units;
	private String raceName;

        /**
         * Constructor which create a new
         * @param race Integer representing the race object to be created. Use one of the Race_ attributes.
         */
        public Race(){units = new ArrayList<ArmyUnit>();}

	public Race(String race){
		units = new ArrayList<ArmyUnit>();
//                assignRace(race);
                raceName = race;
	}

        public final void assignRace(String race){
            raceName = race;
//            switch(race){
//		case RACE_BRETONNIA:
//			raceName = "Bretonnia";
//			break;
//		case RACE_DWARFS:
//			raceName = "Dwarfs";
//			break;
//		case RACE_TOMB_KINGS:
//			raceName = "Tomb Kings";
//			break;
//		case RACE_EMPIRE:
//			raceName = "Empire";
//			break;
//		case RACE_HIGH_ELVES:
//			raceName = "High Elves";
//			break;
//		case RACE_WOOD_ELVES:
//			raceName = "Wood Elves";
//			break;
//		case RACE_LIZARDMEN:
//			raceName = "Lizardmen";
//			break;
//		case RACE_OGRE_KINGDOMS:
//			raceName = "Ogre Kingdoms";
//			break;
//		case RACE_DARK_ELVES:
//			raceName = "Dark Elves";
//			break;
//		case RACE_SKAVEN:
//			raceName = "Skaven";
//			break;
//		case RACE_BEASTMEN:
//			raceName = "Beastmen";
//			break;
//		case RACE_ORCS_AND_GOBLINS:
//			raceName = "Orcs and Goblins";
//			break;
//		case RACE_DAEMONS_OF_CHAOS:
//			raceName = "Daemons of Chaos";
//			break;
//		case RACE_VAMPIRE_COUNTS:
//			raceName = "Vampire Counts";
//			break;
//		case RACE_ARCANE_CREATURES:
//			raceName = "Arcane Creatures";
//			break;
//		case RACE_WARRIORS_OF_CHAOS:
//			raceName = "Warriors of Chaos";
//			break;
//		default:
//			raceName = "";
//			break;
//            }
        }

        @Override
	public String toString(){
		String toString = raceName+"\n";
		for(Unit unit : units){
			toString += "\t"+unit.toString()+"\n";
		}
		
		return toString;
	}

        /**
         * Method used to create the specific armies units.
         */
	public void createArmy(){}

        /**
         * Method to aquire the specified unit from the army.
         * @param index Integer holding the index of the requested unit.
         * @return A Unit object if the index exists, null otherwise.
         */
	public Unit getUnit(int index){return null;}

        public void addUnit(ArmyUnit unit){
            units.add(unit);
        }

        public ArrayList<ArmyUnit> getUnits(){
            return units;
        }

	public static void main(String[] args){
           //TODO: Move main method to appropriate location.
//		Bretonnia b = new Bretonnia(Race.RACE_BRETONNIA);
//		b.createArmy();
//		System.out.println(b.toString());
                File.XMLRaceParser t = new File.XMLRaceParser();
                Race r = t.parseDocument(new java.io.File("Resources\\XML\\Bretonnia.xml"));
                System.out.println(r.toString());
	}
	
}
