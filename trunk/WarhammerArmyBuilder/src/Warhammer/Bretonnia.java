package Warhammer;


public class Bretonnia extends Race {

	public Bretonnia(int race) {
		super(race);
		// TODO Auto-generated constructor stub
	}

	@Override
	void createArmy() {
		units.add(new Unit("Battle Pilgrim",Unit.CATEGORY_INFANTRY,4,2,2,3,3,1,3,1,8));
		units.add(new Unit("Bretonnian Lord",Unit.CATEGORY_INFANTRY,4,6,3,4,4,3,6,4,9));
		units.add(new Unit("Damsel",Unit.CATEGORY_INFANTRY,4,3,3,3,3,2,3,1,7));
		units.add(new Unit("Fay Enchantress",Unit.CATEGORY_MONSTROUS_CAVALRY,5,4,4,3,3,3,5,1,9));
		units.add(new Unit("-Silvaron",Unit.VALUE_NOT_SET,10,5,0,4,4,3,5,2,8));
		units.add(new Unit("Field Trebuchet",Unit.CATEGORY_WAR_MACHINE,0,0,0,0,7,4,0,0,0));
		units.add(new Unit("-Pesant Crew",Unit.VALUE_NOT_SET,4,2,2,3,3,1,3,1,5));
		units.add(new Unit("Grail Knight",Unit.CATEGORY_CAVALRY,4,2,3,4,3,1,5,2,8));
		units.add(new Unit("-Warhorse",Unit.VALUE_NOT_SET,8,3,0,3,3,1,3,1,5));
		units.add(new Unit("Grail Reliquae",Unit.CATEGORY_INFANTRY,4,2,2,3,3,6,3,4,8));
		units.add(new Unit("Green Knight",Unit.CATEGORY_CAVALRY,4,7,3,4,4,3,6,4,9));
		units.add(new Unit("-Shadow Steed",Unit.VALUE_NOT_SET,8,4,0,4,3,1,4,1,5));
		units.add(new Unit("Hippogryph",Unit.CATEGORY_MONSTER,8,4,0,5,5,4,4,4,8));
	}

	@Override
	Unit getUnit(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
