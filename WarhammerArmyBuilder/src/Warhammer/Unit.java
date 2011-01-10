package Warhammer;

public class Unit {
	public static final int VALUE_NOT_SET = 0;
	public static final int VALUE_NOT_FOUND = 404;
	public static final int CHARACTHERISTIC_ATTACKS = 10;
	public static final int CHARACTHERISTIC_BALLISTIC_SKILL = 11;
	public static final int CHARACTHERISTIC_INITIATIVE = 12;
	public static final int CHARACTHERISTIC_LEADERSHIP = 13;
	public static final int CHARACTHERISTIC_MOVEMENT_ALLOVANCE = 14;
	public static final int CHARACTHERISTIC_STRENGTH = 15;
	public static final int CHARACTHERISTIC_TOUGHNESS = 16;
	public static final int CHARACTHERISTIC_WOUNDS = 17;
	public static final int CHARACTHERISTIC_WEAPON_SKILL = 18;
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
	public Unit(String name){
		unitName = name;
	}
	public Unit(String name, 
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
	public void setCategory(int category){
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
	public int getCategory(){
		return unitCategory;
	}
	public String getUnitName(){
		return unitName;
	}
	public void setCharacteristics(int movement,
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
	public String toString(){
		String type;
		switch(unitCategory){
		case CATEGORY_CAVALRY:
			type = "Ca";
			break;
		case CATEGORY_CHARIOT:
			type = "Ch";
			break;
		case CATEGORY_INFANTRY:
			type = "In";
			break;
		case CATEGORY_MONSTER:
			type = "Mo";
			break;
		case CATEGORY_MONSTROUS_BEAST:
			type = "MB";
			break;
		case CATEGORY_MONSTROUS_CAVALRY:
			type = "MC";
			break;
		case CATEGORY_MONSTROUS_INFANTRY:
			type = "MI";
			break;
		case CATEGORY_SWARM:
			type = "Sw";
			break;
		case CATEGORY_UNIQUE_UNIT:
			type = "Un";
			break;
		case CATEGORY_WAR_BEAST:
			type = "WB";
			break;
		case CATEGORY_WAR_MACHINE:
			type = "WM";
			break;
		default:
			type = "-";
			break;
		}	
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
}
