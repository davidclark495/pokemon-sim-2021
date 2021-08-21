package m_pokemon;

/**
 * Represents any special effects an attack might have.
 * Also represents any special conditions that might be affecting a Pokemon.
 * 
 * To-do: finalize a theory for the way this class interacts with pokemon and battles
 * 
 * @author davidclark
 * Date: 08/21/21
 */
public class Effect {

	public static final Effect BURN = new Effect(Status.BURN, 0.7, 0.4, 0.125);
	
	public enum Status{
		NONE, BURN, FREEZE, DRAIN;
	}

	// the Pokemon that used the Move with this as an Effect
	Pokemon user;
	// the Pokemon afflicted by this Effect
	Pokemon victim;

	// the name/kind of status effect
	Status status;

	// the probability of the victim being affected at all (only checked once)
	double inflictChance = 0.0;
	// the probability of the victim recovering at the beginning of their turn
	double recoveryChance = 0.0;
	// the probability of the victim not being able to attack on their turn
	double immobileChance = 0.0;

	// the amount of damage the victim takes at the start of each turn 
	// (as a proportion of max health)
	double percentDmgPerTurn = 0.0;

	// the amount of HP the user gets as a proportion of damage dealt each turn
	double percentHealthDrain = 0.0;

	// the amount the victim's stat increases (+numbers) or decreases (-numbers) 
	// (as a proportion of base stats)
	// (applied once)
	// e.g. atkMod = -0.2 --> victim's new atkStat = 0.8
	double percentAtkModifierVictim = 0.0;
	double percentDefModifierVictim = 0.0;

	// the amount the user's stat increases (+numbers) or decreases (-numbers) 
	// (as a proportion of base stats)
	// (applied once)
	double percentAtkModifierUser = 0.0;
	double percentDefModifierUser = 0.0;


	public Effect(Status status, double inflictChance, 
			double recoveryChance, double percentDmgPerTurn)
	{
		this.status = status;
		this.inflictChance = inflictChance;
		this.recoveryChance = recoveryChance;
		this.percentDmgPerTurn = percentDmgPerTurn;
	}




}
