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

package Warhammer;

import java.util.ArrayList;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.2
 */
public class ArmyUnit extends Unit{
    private ArrayList<Mount> mounts;
    private ArrayList<Crew> crews;

    /**
     * Constructor to create a new ArmyUnit object and initialization of the
     * super class.
     */
    public ArmyUnit(){
        super();
        mounts = null;
        crews = null;
    }

    /**
     * Method to aquire the crew objects associated with this unit.
     * @return null if there is no crews associated with this unit, otherwise the ArrayList<Crew> containg the associated crews.
     */
    public ArrayList<Crew> getCrew(){
        return crews;
    }

    /**
     * Method to aquire the mount objects associated with this unit.
     * @return null if there is no mounts associated with this unit, otherwise the ArrayList<Mount> containg the associated mounts.
     */
    public ArrayList<Mount> getMount(){
        return mounts;
    }

    /**
     * Method that associates a new crew with this unit.
     * @param crew the Crew to associate with this unit.
     */
    public void addCrew(Crew crew){
        if(crews==null)
            crews = new ArrayList<Crew>();
        crews.add(crew);
    }

    /**
     * Method that associates a new mount unit with this unit.
     * @param mount the Mount to associate with this unit.
     */
    public void addMount(Mount mount){
        if(mounts==null)
            mounts = new ArrayList<Mount>();
        mounts.add(mount);
    }
    
    @Override
    public String toString(){
        String toString = super.toString();
        if(mounts!=null)
            for(Mount mount : mounts){
                toString += "\n\t"+mount.toString();
            }
        if(crews!=null)
            for(Crew crew : crews){
                toString += "\n\t"+crew.toString();
            }
        return toString;
    }
}
