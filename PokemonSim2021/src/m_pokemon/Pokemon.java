package m_pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a pokemon, complete with name/type/stats/etc.
 * 
 * @author davidclark
 * Date: 08/18/21
 */
public class Pokemon {

	public static final int MAX_NUM_MOVES = 4;

	// The species' type
	private Type type;
	// The species' name
	private String name;

	// The individual's nickname
	private String nickname;

	// The Pokemon's list of attacks/moves
	private ArrayList<Move> knownMoves = new ArrayList<Move>();

	// stats related info
	private PersistentStatBlock stats;
	private RewardStatBlock rewardStats;
	private InBattleTempStatBlock battleStats;

	//
	private ArrayList<Effect> activeEffects;

	// level-up rewards (moves, evolutions)
	//private HashMap<Integer, Move> movesToLearn;
	//private HashMap<EvolCondit, Pokemon> nextEvolution;


	///// Constructors /////

	/**
	 * DEPRECATED - not viable for game
	 * 
	 * Create a Pokemon with a given species' name
	 * @param name
	 */
	public Pokemon(String name) {
		this.name = name;
	}

	/**
	 * DEPRECATED - not viable for game
	 * 
	 * Create a Pokemon with a given name and type.
	 * 
	 * @param name
	 * @param type must not be null
	 * @throws IllegalArgumentException if type is null
	 */
	public Pokemon(String name, Type type) {
		this(name, type, 0, 0, 0);

		this.name = name;
		this.type = type;
	}

	/**
	 * Create a Pokemon with a given name, type, and base stats.
	 * The Pokemon will start at level 1.
	 * 
	 * @param name
	 * @param type
	 * @param baseHP
	 * @param baseAtk
	 * @param baseDef
	 */
	public Pokemon(String name, Type type, int baseHP, int baseAtk, int baseDef) {
		if(name == null) {
			throw new IllegalArgumentException("A Pokemon's name cannot be null.");
		}
		if(type == null) {
			throw new IllegalArgumentException("A Pokemon's type cannot be null.");
		}

		this.name = name;
		this.type = type;
		this.stats = new PersistentStatBlock(baseHP, baseAtk, baseDef, 1);
		this.rewardStats = new RewardStatBlock(0, 0, 0);
	}

	/**
	 * Create a Pokemon with the same species-name, type, base stats, 
	 * level, and move list.
	 * 
	 * Does NOT copy a Pokemon's EVs and nickname.
	 * 
	 * @param clone
	 */
	public Pokemon(Pokemon clone) {
		// copy the Pokemon's species-name, type, and base stats
		this(clone.getName(), clone.getType(), 
				clone.stats.baseHP, clone.stats.baseAtk, clone.stats.baseDef);

		// copy the Pokemon's level
		while(this.getLvl() < clone.getLvl())
			this.levelUp_NoCost();

		// copy the Pokemon's moves
		for(Move move : clone.getKnownMoves())
			this.teachMove(move);
	}

	/**
	 * Create a new Pokemon of the same species (name, type, base stats)
	 * at the given level (different level, different moves).
	 * 
	 * @param template 
	 */
	public Pokemon(Pokemon template, int level) {
		// copy the Pokemon's species-name, type, and base stats
		this(template.getName(), template.getType(), 
				template.stats.baseHP, template.stats.baseAtk, template.stats.baseDef);

		// get to the desired level
		while(this.getLvl() < level)
			this.levelUp_NoCost();
	}

	


	///// Accessors /////

	/**
	 * 
	 * @return the Pokemon's species name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns an identifier for this individual Pokemon.
	 * Returns the Pokemon's nickname, if it exists.
	 * Otherwise, returns its name.
	 * 
	 * @return the Pokemon's nickname (if it exists); otherwise returns its name
	 */
	public String getNickname() {
		if(nickname != null) 
			return nickname;
		else
			return name;
	}

	/**
	 * 
	 * @return the Pokemon's type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns true if the Pokemon is fainted / has 0 HP.
	 * @return
	 */
	public boolean isFainted() {
		return stats.getHP() == 0;
	}



	///// Mutators /////

	/**
	 * Sets the Pokemon's nickname, no restrictions.
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Decreases this Pokemon's health by the amount given. 
	 * HP won't drop below 0.
	 * 
	 * @param hpLost
	 */
	public void loseHP(int hpLost) {
		this.stats.loseHP(hpLost);
	}



	/////////////////

	///// Moves /////

	/////////////////

	/**
	 * Adds the given move to the Pokemon's list of known moves.
	 * Will fail (the move will not be added + will return false) if...
	 * - the Pokemon already knows the max. number of moves
	 * - the Pokemon already knows a move with the same name
	 * 
	 * @param move
	 * @return True if the move was successfully taught, False otherwise
	 */
	public boolean teachMove(Move moveToLearn) {
		// check: Pokemon knows too many moves ?
		if(knownMoves.size() >= MAX_NUM_MOVES) {
			return false;			
		}

		// check: Pokemon already knows this move ?
		if(knownMoves.contains(moveToLearn)) {
			return false;			
		}

		// passed all checks --> success
		knownMoves.add(moveToLearn);
		return true;
	}

	/**
	 * Removes the specified move from the Pokemon's list of known moves.
	 * 
	 * @param move
	 */
	public void forgetMove(Move moveToForget) {
		knownMoves.remove(moveToForget);
	}

	/**
	 * Return an ArrayList of Moves this Pokemon can use.
	 * @return
	 */
	public ArrayList<Move> getKnownMoves(){
		return knownMoves;
	}




	///////////////////////////

	///// Stats + Battles /////

	///////////////////////////

	///// Accessors & Mutators /////
	// persistent stats
	public int getHP() {
		return stats.getHP();
	}
	public int getMaxHP() {
		return stats.getHP();
	}
	public int getAtk() {
		return stats.getAtk();
	}
	public int getDef() {
		return stats.getDef();
	}
	public int getLvl() {
		return stats.getLvl();
	}
	public int getXP() {
		return stats.getXP();
	}
	public int getNextLevelXP() {
		return stats.getNextLevelXP();
	}

	/**
	 * Returns true if the Pokemon has enough XP to level up.
	 * @return
	 */
	public boolean isReadyToLevelUp() {
		return stats.isReadyToLevelUp();
	}

	/**
	 * Spend XP and level-up the Pokemon.
	 * The Pokemon's stats will increase.
	 */
	public void levelUp() {
		// improve stats
		stats.levelUp();

		// learn a move?
		// evolve?
	}

	/**
	 * Level-up the Pokemon without gaining or losing XP.
	 * The Pokemon's stats will increase.
	 * 
	 * (Used by constructors / .getClone())
	 */
	public void levelUp_NoCost() {
		// improve stats
		stats.levelUp_NoCost();

		// learn a move?
		// evolve?
	}

	/**
	 * Increase this Pokemon's XP by the given amount.
	 * 
	 * @param amtXP
	 * @throws IllegalArgumentException if amtXP is negative
	 */	
	public void gainXP(int amtXP) {
		// check: amtXP is negative
		if(amtXP < 0)
			throw new IllegalArgumentException("A Pokemon can't gain negative XP.");

		// increase XP //
		this.stats.gainXP(amtXP);
	}

	/**
	 * Increase this Pokemon's EV's using values obtained from the given 
	 * RewardStatBlock.
	 * @param rewardBlock
	 */
	public void gainEVs(RewardStatBlock rewardBlock) {
		this.stats.hpEVsGained 	+= rewardBlock.getHPEVsDropped();
		this.stats.atkEVsGained += rewardBlock.getAtkEVsDropped();
		this.stats.defEVsGained += rewardBlock.getDefEVsDropped();
	}


	// temp / battle stats 
	public void recalculateBattleStats() {
		battleStats = new InBattleTempStatBlock(this.stats, this.activeEffects);
	}
	public int getTempAtk() {
		return battleStats.getTempAtk();
	}
	public int getTempDef() {
		return battleStats.getTempDef();
	}

	// reward stats
	public RewardStatBlock getRewardBlock() {
		return this.rewardStats;
	}


	//////////////////////////////////

	///// Inner Class: StatBlocks /////

	//////////////////////////////////

	/**
	 * Tracks this Pokemon's persistent stats (and stats used to calculate them).
	 * - base stats
	 *   : stats shared by all pokemon of a given species
	 *   : affect how the pokemon's other stats will change between levels
	 *   
	 * - persistent stats
	 *   : the individual's current stats at the current level 
	 *   : (these shouldn't go down)
	 *   : these increase when the pokemon levels-up, 
	 *     and the increase may be less than a full point
	 *   
	 * - battle reward stats
	 *   : gains... used to calculate exp. gained, stat increases, etc.
	 *   
	 * Persistent values are stored as doubles to allow gradual accumulation. 
	 * For all other purposes, integers are preferable, so use the getters (please).
	 *   
	 * @author davidclark
	 * Date: 08/22/21
	 */
	private class PersistentStatBlock {

		// base stats
		private int baseHP, baseAtk, baseDef;

		// persistent stats
		private double maxHP, atk, def;
		private int hp;
		private int level=1, xp=0, nextLevelXP=10;

		// battle reward stats : gains
		private int hpEVsGained=0, atkEVsGained=0, defEVsGained=0;

		/**
		 * Initialize the StatBlock with the provided base stats 
		 * and derive persistent stats
		 * @param baseHP
		 * @param baseAtk
		 * @param baseDef
		 * @param level
		 */
		public PersistentStatBlock(int baseHP, int baseAtk, int baseDef, int level){
			this.baseHP = baseHP;
			this.baseAtk = baseAtk;
			this.baseDef = baseDef;

			this.level = 1;
			this.maxHP = 10;
			this.refillHP();
			this.atk = 5;
			this.def = 5;

			while(this.level < level) {
				levelUp_NoCost();
			}
		}


		///// Misc. Functionality /////
		/**
		 * Increase the Pokemon's level and adjust stats.
		 * - xp is spent 
		 * - nextLevelXP is increased
		 * - persistent stats are increased w/ regard to base stats
		 * - persistent stats are increased w/ regard to EVs gained
		 */
		public void levelUp() {
			//// adjust level/exp. stats ////
			level++;
			xp -= nextLevelXP;
			nextLevelXP *= 1.4;

			//// adjust persistent stats ////

			// improve w/ level 
			maxHP += baseHP/50.0;
			hp += baseHP/50.0;
			atk += baseAtk/50.0;
			def += baseDef/50.0;

			// improve w/ EVs (+ reset EVs gained)
			maxHP += hpEVsGained/100.0;
			hp += hpEVsGained/100.0;
			atk += atkEVsGained/100.0;
			def += defEVsGained/100.0;
			hpEVsGained = 0;
			atkEVsGained = 0;
			defEVsGained = 0;
		}

		/**
		 * Increase the Pokemon's level and adjust stats WITHOUT spending XP.
		 * Should be called for non-XP related level-ups.
		 */
		public void levelUp_NoCost() {
			xp += nextLevelXP;
			levelUp();
		}

		/**
		 * Increase XP by the provided amount. 
		 * Does NOT automatically trigger a level-up.
		 * 
		 * @param amtGained
		 */
		public void gainXP(int amtGained) {
			xp += amtGained;
		}

		/**
		 * Returns true if the Pokemon has enough XP to level-up.
		 * @return
		 */
		public boolean isReadyToLevelUp() {
			return xp > nextLevelXP;
		}


		///// Accessors /////
		public int getHP() {
			return (int)hp;
		}

		public int getMaxHP() {
			return (int)maxHP;
		}

		public int getAtk() {
			return (int)atk;
		}

		public int getDef() {
			return (int)def;
		}

		public int getLvl() {
			return level;
		}

		public int getXP() {
			return xp;
		}

		public int getNextLevelXP() {
			return nextLevelXP;
		}

		///// Mutators /////
		/**
		 * Sets hp to maxHP.
		 */
		public void refillHP() {
			hp = getMaxHP();
		}

		/**
		 * Increase HP by the value given.
		 * HP will not exceed MaxHP; 
		 * excessively large values will result in a full heal.
		 * 
		 * Throws an IllegalArgumentException if hpRestored is negative.
		 * 
		 * @param amtRestored
		 */
		public void restoreHP(int hpRestored) {
			if(hpRestored < 0)
				throw new IllegalArgumentException("'HP restored' must not be negative.");

			int hpAfterHeal = getHP() + hpRestored;

			// if hpAfterHeal is too high, just refill hp
			if(hpAfterHeal > getMaxHP())
				this.refillHP();
			else
				hp = hpAfterHeal;
		}

		/**
		 * Decrease HP by the value given.
		 * HP will not fall below 0; 
		 * excessively high losses will just reduce HP to 0.
		 * 
		 * Throws an IllegalArgumentException if hpLost is negative.
		 * 
		 */
		public void loseHP(int hpLost) {
			if(hpLost < 0)
				throw new IllegalArgumentException("'HP lost' must not be negative.");

			int hpAfterLoss = getHP() - hpLost;

			// if hpAfterHeal is too low, just set hp = 0
			if(hpAfterLoss < 0)
				hp = 0;
			else
				hp = hpAfterLoss;
		}


	}

	/**
	 * MAYBE THIS SHOULD BE IN AN ACTIVITY?
	 *  benefits:
	 *  	
	 *  costs: 
	 *  	
	 * 
	 * Holds a Pokemon's temporary/effective stats
	 *   : these should be reset/recalculated at the start of battles
	 *     and when the pokemon gets a new Effect
	 *   : determined by persistent stats + any ongoing Effects
	 * @author davidclark
	 * Date: 09/02/21
	 */
	private class InBattleTempStatBlock{
		// temp/effective stats
		private int tempAtk, tempDef;
		private double accuracy, evasion;

		public InBattleTempStatBlock(PersistentStatBlock stats,
				ArrayList<Effect> activeEffects) {
			tempAtk = stats.getAtk();
			tempDef = stats.getDef();
			accuracy = 1.0;
			evasion = 1.0;

			for(Effect effect : activeEffects) {				
				processEffect(effect);
			}
		}

		private void processEffect(Effect effect) {

		}

		///// Accessors /////
		public int getTempAtk() {
			return tempAtk;
		}
		public int getTempDef() {
			return tempDef;
		}
	}


	/**
	 * Holds a Pokemon's battle reward stats
	 *   : drops... these matter to the pokemon that faints this one
	 *   
	 * @author davidclark
	 * Date: 09/02/21
	 */
	private class RewardStatBlock{
		// battle reward stats : drops
		private int hpEVsDropped, atkEVsDropped, defEVsDropped;
		//private int baseXPDropped;
		// xpDropped should be a function, something like this:
		// 		this.level / attacker.level * baseXPDropped

		public RewardStatBlock(int hpEVs, int atkEVs, int defEVs) {
			this.hpEVsDropped = hpEVs;
			this.atkEVsDropped = atkEVs;
			this.defEVsDropped = defEVs;
		}

		public int getHPEVsDropped() {
			return hpEVsDropped;
		}
		public int getAtkEVsDropped() {
			return atkEVsDropped;
		}
		public int getDefEVsDropped() {
			return defEVsDropped;
		}
	}

}
