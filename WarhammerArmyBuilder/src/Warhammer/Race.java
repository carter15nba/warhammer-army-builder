package Warhammer;

import java.util.ArrayList;



public abstract class Race {
	public static final int RACE_BRETONNIA = 10;
	public static final int RACE_DWARFS = 11;
	public static final int RACE_TOMB_KINGS = 12;
	public static final int RACE_EMPIRE = 13;
	public static final int RACE_HIGH_ELVES = 14;
	public static final int RACE_WOOD_ELVES = 15;
	public static final int RACE_LIZARDMEN = 16;
	public static final int RACE_OGRE_KINGDOMS = 17;
	public static final int RACE_DARK_ELVES = 18;
	public static final int RACE_SKAVEN = 19;
	public static final int RACE_BEASTMEN = 20;
	public static final int RACE_ORCS_AND_GOBLINS = 21;
	public static final int RACE_DAEMONS_OF_CHAOS = 22;
	public static final int RACE_VAMPIRE_COUNTS = 23;
	public static final int RACE_ARCANE_CREATURES = 24;
	public static final int RACE_WARRIORS_OF_CHAOS = 25;
	protected ArrayList<Unit> units;
	private String raceName;
	
	public Race(int race){
		units = new ArrayList<Unit>();
		switch(race){
		case RACE_BRETONNIA:
			raceName = "Bretonnia";
			break;
		case RACE_DWARFS:
			raceName = "Dwarfs";
			break;
		case RACE_TOMB_KINGS:
			raceName = "Tomb Kings";
			break;
		case RACE_EMPIRE:
			raceName = "Empire";
			break;
		case RACE_HIGH_ELVES:
			raceName = "High Elves";
			break;
		case RACE_WOOD_ELVES:
			raceName = "Wood Elves";
			break;
		case RACE_LIZARDMEN:
			raceName = "Lizardmen";
			break;
		case RACE_OGRE_KINGDOMS:
			raceName = "Ogre Kingdoms";
			break;
		case RACE_DARK_ELVES:
			raceName = "Dark Elves";
			break;
		case RACE_SKAVEN:
			raceName = "Skaven";
			break;
		case RACE_BEASTMEN:
			raceName = "Beastmen";
			break;
		case RACE_ORCS_AND_GOBLINS:
			raceName = "Orcs and Goblins";
			break;
		case RACE_DAEMONS_OF_CHAOS:
			raceName = "Daemons of Chaos";
			break;
		case RACE_VAMPIRE_COUNTS:
			raceName = "Vampire Counts";
			break;
		case RACE_ARCANE_CREATURES:
			raceName = "Arcane Creatures";
			break;
		case RACE_WARRIORS_OF_CHAOS:
			raceName = "Warriors of Chaos";
			break;
		default:
			raceName = "";
			break;
		}
	}
	public String toString(){
		String toString = raceName+"\n";
		for(Unit unit : units){
			toString += "\t"+unit.toString()+"\n";
		}
		
		return toString;
	}
	abstract void createArmy();
	abstract Unit getUnit(int index);
	
	public static void main(String[] args){
		Bretonnia b = new Bretonnia(Race.RACE_BRETONNIA);
		b.createArmy();
		System.out.println(b.toString());
	
	}
	
}
