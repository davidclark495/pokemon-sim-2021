package m_pokemon;

import java.util.ArrayList;

/**
 * Represents a pokemon/move's Type.
 * 
 * @author davidclark
 * Date: 08/20/21
 */
public enum Type {

	// enum fields
	NORMAL, FIRE, WATER, GRASS;

	private ArrayList<Type> weaknesses;
	private ArrayList<Type> resistances;
	
	private void loadWeaknesses() {
		// fire
		FIRE.weaknesses.add(WATER);
		// water
		WATER.weaknesses.add(GRASS);
		// grass
		GRASS.weaknesses.add(FIRE);
	}

	private void loadResistances() {
		// fire
		FIRE.resistances.add(GRASS);
		// water
		WATER.resistances.add(FIRE);
		// grass
		GRASS.resistances.add(WATER);
	}

	public ArrayList<Type> getWeaknesses(Type t) {
		return t.weaknesses;
	}
	
	public ArrayList<Type> getResistances(Type t) {
		return t.resistances;
	}
	
//	public static double getDmgMultiplier() {
//		return 0;
//	}
	
}
