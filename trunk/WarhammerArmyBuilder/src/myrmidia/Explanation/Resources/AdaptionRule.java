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

package myrmidia.Explanation.Resources;

import java.util.HashMap;
import myrmidia.Enums.Actions;

/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class AdaptionRule {

    private HashMap<Actions,String> rd;
    
    private AdaptionRule(){initDescriptions();}
    public static AdaptionRule getInstance(){
        return AdaptionRuleHolder.INSTANCE;
    }
    private static class AdaptionRuleHolder{
        private static final AdaptionRule INSTANCE = new AdaptionRule();
    }
    public String getRuleDescription(Actions key){
        return key.toString()+": "+rd.get(key);
    }
    private void initDescriptions(){
        rd = new HashMap<Actions, String>();
        rd.put(Actions.Added_Character, "Added a character to increase the point usage");
        rd.put(Actions.Added_Core_Group, "Added a new core group to increase the point usage");
        rd.put(Actions.Added_Core_Group_Few, "Added a new core group since there were too few core groups");
        rd.put(Actions.Added_Equipment, "Added equipment to increase point usage and unit efficiency");
        rd.put(Actions.Added_Full_Command, "Added full command to the unit to increase point usage and unit efficiency");
        rd.put(Actions.Added_General, "Added a general since there were no general in the army");
        rd.put(Actions.Added_Random_Group, "Added a random group to increase point usage. There were no specified reasons for the lack of points in the army, so a random group seems like a good idea.");
        rd.put(Actions.Added_Rare_Group, "Added a rare group/unit to the army to increase point usage");
        rd.put(Actions.Added_Special_Group, "Added a special group/unit to the army to increase point usage");
        rd.put(Actions.Added_Utility, "Added a utility unit to increase point usage and unit efficiency");
        rd.put(Actions.Decreased_Unit_Cost, "Decreased unit cost by:");
        rd.put(Actions.Decreased_Unit_Size, "Deceased formation size to bring it below the maximum formation size limit, or to decrease point usage");
        rd.put(Actions.Increased_Unit_Size, "Increased the unit size, since there were too few units in the formation to meet the minimum size requirement");
        rd.put(Actions.Least_Expensive_Unit, "Found the least expensive unit/formation with the desired army type in order to remove it. The removal of the least expensive unit will have the least impact on the army point usage");
        rd.put(Actions.Least_Similar_Unit, "Found the least similar unit to the units/formations already in the army with the given army type. To add the least similar unit is probably a good idea, in order to improve the army");
        rd.put(Actions.Most_Expensive_Unit, "Found the most expensive unit/formation with the desired army type. In order to reduce the cost, by reducing the formation size or remove equipment/utility unit ");
        rd.put(Actions.Most_Similar_Unit, "Found the most similar unit to a unit in the army with the given army type. This is done to find the \"best\" candidated to exchange a unit with");
        rd.put(Actions.Removed_Character, "Removed a character to reduce the total points used");
        rd.put(Actions.Removed_Core_Group, "Removed a core group to reduce the total points used");
        rd.put(Actions.Removed_Duplicate, "Removed a special/rare duplicate formation, since there were too many duplicate formations of that unit");
        rd.put(Actions.Removed_Equipment, "Removed equipment from the unit to reduce the total points used");
        rd.put(Actions.Removed_Full_Command, "Removed full command from a unit to reduce the total points used");
        rd.put(Actions.Removed_Random_Group, "Removed a random group to reduce the total points used");
        rd.put(Actions.Removed_Rare_Group, "Removed a rare group to reduce the total points used, or to reduce the number of rare points; since the used rare points exceeded the available rare points");
        rd.put(Actions.Removed_Special_Group, "Removed a special group to reduce the total points used, or to reduce the number of special points; since the used special points exceeded the available special points");
        rd.put(Actions.Removed_Utility, "Removed a utility unit from the unit in order to reduce the total points used");
    }
}