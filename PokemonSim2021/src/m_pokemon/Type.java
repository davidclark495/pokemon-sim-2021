package m_pokemon;

import java.util.HashSet;

/**
 * Represents a pokemon/move's Type.
 * 
 * @author davidclark
 * Date: 08/20/21
 */
public enum Type {

	// enum fields
	NORMAL, FIRE, WATER, GRASS, NotYetImplemented;

	private HashSet<Type> weaknesses = new HashSet<Type>();
	private HashSet<Type> resistances = new HashSet<Type>();

	// load Weaknesses
	static {
		// fire
		FIRE.weaknesses.add(WATER);
		// water
		WATER.weaknesses.add(GRASS);
		// grass
		GRASS.weaknesses.add(FIRE);
	}

	// load resistances
	static {
		// fire
		FIRE.resistances.add(FIRE);
		FIRE.resistances.add(GRASS);
		// water
		WATER.resistances.add(FIRE);
		WATER.resistances.add(WATER);
		// grass
		GRASS.resistances.add(WATER);
		GRASS.resistances.add(GRASS);
	}

	public HashSet<Type> getWeaknesses() {
		return this.weaknesses;
	}

	public HashSet<Type> getResistances() {
		return this.resistances;
	}

	public static HashSet<Type> getWeaknesses(Type t) {
		return t.weaknesses;
	}

	public static HashSet<Type> getResistances(Type t) {
		return t.resistances;
	}

	/**
	 * Returns True if the defending type (the invoking type)
	 * is weak to the attacking type (the one in the argument).
	 * 
	 * @param attacker
	 * @return
	 */
	public boolean isWeakTo(Type attacker) {
		return this.getWeaknesses().contains(attacker);
	}

	/**
	 * Returns True if the defending type (the invoking type)
	 * is resistant to the attacking type (the one in the argument).
	 * 
	 * @param attacker
	 * @return
	 */
	public boolean isResistantTo(Type attacker) {
		return this.getResistances().contains(attacker);
	}

	/**
	 * Returns a damage multiplier based on 
	 * @param defender
	 * @param attacker
	 * @return
	 */
	public static double calcDmgMultiplier(Type attacker, Type defender) {
		if(defender.isResistantTo(attacker))
			return 0.5;
		else if(defender.isWeakTo(attacker))
			return 2.0;
		else
			return 1.0;
	}

}
