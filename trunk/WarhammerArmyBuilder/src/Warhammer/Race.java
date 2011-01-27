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


/**
 * @author Glenn Rune Strandbråten
 * @version 0.3
 */
public class Race {
	protected ArrayList<ArmyUnit> units;
	private String raceName;

        /**
         * Constructor which create a new race object.
         */
        public Race(){
            units = new ArrayList<ArmyUnit>();
            assignRace("");
        }

        /**
         * Constructor which creates a new race object with the supplied name.
         * @param race String The race name.
         */
	public Race(String race){
		units = new ArrayList<ArmyUnit>();
                assignRace(race);
	}

        /**
         * Method to assign the name of the race.
         * @param race String The race name.
         */
        public final void assignRace(String race){
            raceName = race;
        }

        @Override
	public String toString(){
		String toString = raceName+"\n";
		for(Unit_old unit : units){
			toString += "\t"+unit.toString()+"\n";
		}
		
		return toString;
	}

        /**
         * Method to aquire the race name.
         * @return String with the race name.
         */
        public String getRaceName(){
            return raceName;
        }

        /**
         * Method to add a unit to the race.
         * @param unit ArmyUnit the unit to be added to the race.
         */
        public void addUnit(ArmyUnit unit){
            units.add(unit);
        }

        /**
         * Method to aquire all the units associated with this race.
         * @return ArrayList<ArmyList> the list holding all the units.
         */
        public ArrayList<ArmyUnit> getUnits(){
            return units;
        }
}
