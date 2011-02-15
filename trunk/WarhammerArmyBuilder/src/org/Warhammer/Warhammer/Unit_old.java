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

package org.Warhammer.Warhammer;

/**
 * @author Glenn Rune Strandbråten
 * @version 0.3.1
 */
public class Unit_old{
	public static final int VALUE_NOT_SET = -1;
	public static final int VALUE_NOT_FOUND = 404;
	public static final int CHARACTHERISTIC_ATTACKS = 7;
	public static final int CHARACTHERISTIC_BALLISTIC_SKILL = 2;
	public static final int CHARACTHERISTIC_INITIATIVE = 6;
	public static final int CHARACTHERISTIC_LEADERSHIP = 8;
	public static final int CHARACTHERISTIC_MOVEMENT_ALLOVANCE = 0;
	public static final int CHARACTHERISTIC_STRENGTH = 3;
	public static final int CHARACTHERISTIC_TOUGHNESS = 4;
	public static final int CHARACTHERISTIC_WOUNDS = 5;
	public static final int CHARACTHERISTIC_WEAPON_SKILL = 1;
	public static final int CATEGORY_CAVALRY = 20;
	public static final int CATEGORY_CHARIOT = 21;
	public static final int CATEGORY_INFANTRY = 22;
	public static final int CATEGORY_MONSTER = 23;
	public static final int CATEGORY_MONSTROUS_BEAST = 24;
	public static final int CATEGORY_MONSTROUS_CAVALRY = 25;
	public static final int CATEGORY_MONSTROUS_INFANTRY = 26;
	public static final int CATEGORY_SWARM = 27;
	public static final int CATEGORY_UNIQUE_UNIT = 28;
	public static final int CATEGORY_WAR_BEAST = 29;
	public static final int CATEGORY_WAR_MACHINE = 30;
	private String unitName;
	private int charAttack = VALUE_NOT_SET;
	private int charBallisticSkill = VALUE_NOT_SET;
	private int charInitiative = VALUE_NOT_SET;
	private int charLeadership = VALUE_NOT_SET;
	private int charMovementAllowance = VALUE_NOT_SET;
	private int charStrength = VALUE_NOT_SET;
	private int charToughness = VALUE_NOT_SET;
	private int charWounds = VALUE_NOT_SET;
	private int charWeaponSkill = VALUE_NOT_SET;
	private int unitCategory = VALUE_NOT_SET;

        /**
         * Creates a new unit object
         */
	public Unit_old(){}

        /**
         * Method to assign a name to the unit.
         * @param name String the name to be assigned to the unit.
         */
        public void setName(String name){
            unitName = name;
        }

        /**
         * Creates a new unit object with the given unit name,
         * and associated charactheristics.
         * @param name The unit name
         * @param category An integer identifying the class of the unit. Use one of the CATEGORY_ attributes.
         * @param movement An integer representing the movement allowance of the unit.
         * @param weaponSkill An integer representing the weapon skill of the unit.
         * @param ballisticSkill An integer representing the ballistics skill of the unit.
         * @param strength An integer representing the strength of the unit.
         * @param toughness An integer representing the toughness of the unit.
         * @param wounds An integer representing the wounds of the unit.
         * @param initiative An integer representing the initiative of the unit.
         * @param attack An integer representing the attack of the unit.
         * @param leadership An integer representing the leadership of the unit.
         */
	public Unit_old(String name,
			int category, 
			int movement,
			int weaponSkill,
			int ballisticSkill,
			int strength,
			int toughness,
			int wounds,
			int initiative,
			int attack,
			int leadership){
		unitName = name;
		setCategory(category);
		setCharacteristics(movement, 
				weaponSkill, 
				ballisticSkill, 
				strength, 
				toughness, 
				wounds, 
				initiative, 
				attack, 
				leadership);
	}

        /**
         * Method to set the unit category.
         * @param category Integer representing the category to be set. Use one of the CATEGORY_ attributes.
         */
	public final void setCategory(int category){
		switch(category){
		case CATEGORY_CAVALRY:
			unitCategory = category;
			break;
		case CATEGORY_CHARIOT:
			unitCategory = category;
			break;
		case CATEGORY_INFANTRY:
			unitCategory = category;
			break;
		case CATEGORY_MONSTER:
			unitCategory = category;
			break;
		case CATEGORY_MONSTROUS_BEAST:
			unitCategory = category;
			break;
		case CATEGORY_MONSTROUS_CAVALRY:
			unitCategory = category;
			break;
		case CATEGORY_MONSTROUS_INFANTRY:
			unitCategory = category;
			break;
		case CATEGORY_SWARM:
			unitCategory = category;
			break;
		case CATEGORY_UNIQUE_UNIT:
			unitCategory = category;
			break;
		case CATEGORY_WAR_BEAST:
			unitCategory = category;
			break;
		case CATEGORY_WAR_MACHINE:
			unitCategory = category;
			break;
		default:
			unitCategory = VALUE_NOT_SET;
			break;
		}		
	}

        /**
         * Method to aquire the unit category.
         * @return -1 if no category is set, otherwise it returns an integer equal to one of the CATEGORY_ attributes.
         */
	public int getCategory(){
		return unitCategory;
	}

        /**
         * Method to aquire the unit name.
         * @return String with the units name.
         */
	public String getUnitName(){
		return unitName;
	}

        /**
         * Method to set/update all the unit characteristics at once.
         * @param movement Integer representing the movement allowance value.
         * @param weaponSkill Integer representing the weapon skill value.
         * @param ballisticSkill Integer representing the ballistic skill value.
         * @param strength Integer representing the strength value.
         * @param toughness Integer representing the toughness value.
         * @param wounds Integer representing the wounds value.
         * @param initiative Integer representing the initiative value.
         * @param attack Integer representing the attack value.
         * @param leadership Integer representing the leadership value.
         */
	public final void setCharacteristics(int movement,
			int weaponSkill,
			int ballisticSkill,
			int strength,
			int toughness,
			int wounds,
			int initiative,
			int attack,
			int leadership){
		charMovementAllowance = movement;
		charWeaponSkill = weaponSkill;
		charBallisticSkill = ballisticSkill;
		charStrength = strength;
		charToughness = toughness;
		charWounds = wounds;
		charInitiative = initiative;
		charAttack = attack;
		charLeadership = leadership;
		
	}

        /**
         * Method to set/update a specific unit characterisitc with the supplied value.
         * @param characteristic Integer identifying the characteristic to set/update. Use one of the CHARACTERISTIC_ attributes.
         * @param value Integer with the value to be assigned to the characteristic.
         */
	public void setCharacteristics(int characteristic, int value){
            switch(characteristic){
		case CHARACTHERISTIC_ATTACKS:
			charAttack = value;
			break;
		case CHARACTHERISTIC_BALLISTIC_SKILL:
			charBallisticSkill = value;
			break;
		case CHARACTHERISTIC_INITIATIVE:
			charInitiative = value;
			break;
		case CHARACTHERISTIC_LEADERSHIP:
			charLeadership = value;
			break;
		case CHARACTHERISTIC_MOVEMENT_ALLOVANCE:
			charMovementAllowance = value;
			break;
		case CHARACTHERISTIC_STRENGTH:
			charStrength = value;
			break;
		case CHARACTHERISTIC_TOUGHNESS:
			charToughness = value;
			break;
		case CHARACTHERISTIC_WEAPON_SKILL:
			charWeaponSkill = value;
			break;
		case CHARACTHERISTIC_WOUNDS:
			charWounds = value;
			break;
            }
	}

        /**
         * Method to aquire the specified characteristic value.
         * @param characteristic Integer identifying the characteristic to aquire. Use one of the CHARACTERISTIC_ attributes.
         * @return 404 if the selected characteristic were not found, -1 if the characteristic is not set or the actual value of the characteristic.
         */
	public int getCharacteristic(int characteristic){
            switch(characteristic){
		case CHARACTHERISTIC_ATTACKS:
			return charAttack;
		case CHARACTHERISTIC_BALLISTIC_SKILL:
			return charBallisticSkill;
		case CHARACTHERISTIC_INITIATIVE:
			return charInitiative;
		case CHARACTHERISTIC_LEADERSHIP:
			return charLeadership;
		case CHARACTHERISTIC_MOVEMENT_ALLOVANCE:
			return charMovementAllowance;
		case CHARACTHERISTIC_STRENGTH:
			return charStrength;
		case CHARACTHERISTIC_TOUGHNESS:
			return charToughness;
		case CHARACTHERISTIC_WEAPON_SKILL:
			return charWeaponSkill;
		case CHARACTHERISTIC_WOUNDS:
			return charWounds;
		default:
			return VALUE_NOT_FOUND;
            }
	}


        @Override
        /**
         * Method to get a textural representation of the data associated with this unit.
         */
	public String toString(){
            String type = getStringCategory();
            return unitName+
                " - [M="+charMovementAllowance+
                ", WS="+charWeaponSkill+
                ", BS="+charBallisticSkill+
                ", S="+charStrength+
                ", T="+charToughness+
                ", W="+charWounds+
                ", I="+charInitiative+
                ", A="+charAttack+
                ", Ld="+charLeadership+
                ", Type="+type+"]";
	}

        /**
         * Method to set the category from string
         * @param category String the category represented as a string.
         */
        public void setCategory(String category){
            if(category.equalsIgnoreCase("Ca"))
                unitCategory = CATEGORY_CAVALRY;
            else if(category.equalsIgnoreCase("Ch"))
                unitCategory = CATEGORY_CHARIOT;
            else if(category.equalsIgnoreCase("In"))
                unitCategory = CATEGORY_INFANTRY;
            else if(category.equalsIgnoreCase("Mo"))
                unitCategory = CATEGORY_MONSTER;
            else if(category.equalsIgnoreCase("MB"))
                unitCategory = CATEGORY_MONSTROUS_BEAST;
            else if(category.equalsIgnoreCase("MC"))
                unitCategory = CATEGORY_MONSTROUS_CAVALRY;
            else if(category.equalsIgnoreCase("MI"))
                unitCategory = CATEGORY_MONSTROUS_INFANTRY;
            else if(category.equalsIgnoreCase("Sw"))
                unitCategory = CATEGORY_SWARM;
            else if(category.equalsIgnoreCase("Un"))
                unitCategory = CATEGORY_UNIQUE_UNIT;
            else if(category.equalsIgnoreCase("WB"))
                unitCategory = CATEGORY_WAR_BEAST;
            else if(category.equalsIgnoreCase("WM"))
                unitCategory = CATEGORY_WAR_MACHINE;
            else
                unitCategory = VALUE_NOT_FOUND;
        }

        /**
         * Method to get the category represented as a string.
         * @return The string representation of the category.
         */
        public String getStringCategory(){
            switch(unitCategory){
		case CATEGORY_CAVALRY:
			return "Ca";
		case CATEGORY_CHARIOT:
			return "Ch";
		case CATEGORY_INFANTRY:
			return "In";
		case CATEGORY_MONSTER:
			return "Mo";
		case CATEGORY_MONSTROUS_BEAST:
			return "MB";
		case CATEGORY_MONSTROUS_CAVALRY:
			return "MC";
		case CATEGORY_MONSTROUS_INFANTRY:
			return "MI";
		case CATEGORY_SWARM:
			return "Sw";
		case CATEGORY_UNIQUE_UNIT:
			return "Un";
		case CATEGORY_WAR_BEAST:
			return "WB";
		case CATEGORY_WAR_MACHINE:
			return "WM";
		default:
			return "-";
            }
        }

        /**
         * Method to get the unit data represented as it appears in the
         * table in the user interface.
         * @return Object[] with the unit data.
         */
        public Object[] getTableObject(){
            return new Object[]{unitName,
                charMovementAllowance,
                charWeaponSkill,
                charBallisticSkill,
                charStrength,
                charToughness,
                charWounds,
                charInitiative,
                charAttack,
                charLeadership,
                getStringCategory()
            };
        }
}
