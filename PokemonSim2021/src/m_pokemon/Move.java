package m_pokemon;

/**
 * Represents a Move used/known by a Pokemon.
 * 
 * @author davidclark
 * Date: 08/21/21
 */
public class Move {

	private final String name;
	private Type type;
	private int maxPP, pp;
	private int baseDmg;
	private double accuracy;
	private Effect effect;

	/*
	 * deprecated
	 */
//	public Move(String name) {
//		this.name = name;
//	}

	public Move(String name, Type type, int maxPP, int baseDmg, double accuracy) {
		this(name, type, maxPP, baseDmg, accuracy, null);
	}

	// base constructor
	public Move(String name, Type type, int maxPP, int baseDmg, double accuracy, Effect effect) {
		this.name = name;
		this.type = type;
		this.maxPP = maxPP;
		this.pp = maxPP;
		this.baseDmg = baseDmg;
		
		// restrict accuracy to the range [0.0, 1.0]
		if(0.0 <= accuracy && accuracy <= 1.0)
			this.accuracy = accuracy;
		else if(accuracy < 0.0)
			this.accuracy = 0;
		else if(accuracy > 1.0)
			this.accuracy = 1.0;
		
		this.effect = effect;
	}

	public Move(Move clone) {
		this(clone.name, clone.type, clone.maxPP, clone.baseDmg, clone.accuracy, clone.effect);
	}



	///// Misc. Functionality ///// 

	public void fullRestorePP() {
		pp = maxPP;
	}

	/**
	 * Checks if the Move can be used (has at least 1 pp).
	 * If it can, consumes 1 pp and returns True.
	 * Otherwise, just returns False.
	 * 
	 * @return True if this Move has at least 1 pp, False otherwise 
	 */
	public boolean usePP() {
		if(pp > 0) {
			pp--;
			return true;
		} else
			return false;
	}



	///// Accessors /////

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public int getMaxPP() {
		return maxPP;
	}
	
	public int getPP() {
		return pp;
	}
	
	public int getBaseDmg() {
		return baseDmg;
	}
	
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * @return this Move's Effect (or null, as necessary)
	 */
	public Effect getEffect() {
		return effect;
	}



	///// Mutators /////



	///// Other /////

	/**
	 * Two moves are equal if the have the same name.
	 * @return True if the other object is a Move with the same name.
	 */
	public boolean equals(Object other) {
		// is other a Move?
		if(other instanceof Move) {
			// is other's name == my name?
			String otherName = ((Move)other).getName();
			if(otherName.equals(this.name))
				return true;
		}

		return false;
	}
}
