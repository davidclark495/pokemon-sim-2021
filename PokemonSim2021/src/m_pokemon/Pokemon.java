package m_pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Represents a pokemon, complete with name/type/stats/etc.
 * 
 * @author davidclark
 * Date: 08/18/21
 */
public class Pokemon {

	public static final int MAX_NUM_MOVES = 4;

	// The species' types, shouldn't change after construction
//	private Type[] types;
	private ArrayList<Type> types = new ArrayList<Type>();
	// The species' name, shouldn't change after construction
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
	//private ArrayList<EvolutionEntry> nextEvolution; // contains condit's + evolution


	///// Constructors /////

	/**
	 * Create a Pokemon with the given name, types, and base stats.
	 * Accepting an array for "types" is generally unsafe (or at least misleading)
	 * so this must stay private.
	 * The Pokemon will start at level 1.
	 * 
	 * @param name
	 * @param baseHP
	 * @param baseAtk
	 * @param baseDef
	 */
	private Pokemon(String name, Type[] types, 
			int baseHP, int baseAtk, int baseDef,
			int HPEVsDropped, int AtkEVsDropped, int DefEVsDropped) {
		if(name == null) {
			throw new IllegalArgumentException("A Pokemon's name cannot be null.");
		}

		this.name = name;
		this.types.add(types[0]);
		if(types.length == 2)
			this.types.add(types[1]);
		this.stats = new PersistentStatBlock(baseHP, baseAtk, baseDef, 1);
		this.rewardStats = new RewardStatBlock(HPEVsDropped, AtkEVsDropped, DefEVsDropped);
	}

	// PUBLIC: CREATE A POKEMON BASED ON STATS

	/**
	 * Create a Pokemon with a given name, type, base-stats, and drop-stats.
	 * The Pokemon will start at level 1.
	 * 
	 * @param name
	 * @param type
	 * @param baseHP
	 * @param baseAtk
	 * @param baseDef
	 * @param HPEVsDropped
	 * @param AtkEVsDropped
	 * @param DefEVsDropped
	 */
	public Pokemon(String name, Type type, 
			int baseHP, int baseAtk, int baseDef,
			int HPEVsDropped, int AtkEVsDropped, int DefEVsDropped
			) {
		// call the private Type[] constructor
		this(name, new Type[]{type}, 
				baseHP, baseAtk, baseDef,
				HPEVsDropped, AtkEVsDropped, DefEVsDropped);

		// confirm that type isn't null before finishing
		if(type == null) {
			throw new IllegalArgumentException("A Pokemon must have a non-null type.");
		}
	}

	/**
	 * Create a Pokemon with a given name, types, base-stats, and drop-stats.
	 * The Pokemon will start at level 1.
	 * 
	 * @param name
	 * @param type1
	 * @param type2
	 * @param baseHP
	 * @param baseAtk
	 * @param baseDef
	 * @param HPEVsDropped
	 * @param AtkEVsDropped
	 * @param DefEVsDropped
	 */
	public Pokemon(String name, Type type1, Type type2, 
			int baseHP, int baseAtk, int baseDef,
			int HPEVsDropped, int AtkEVsDropped, int DefEVsDropped) {

		// call the private Type[] constructor
		this(name, new Type[]{type1, type2}, 
				baseHP, baseAtk, baseDef, 
				HPEVsDropped, AtkEVsDropped, DefEVsDropped);

		// confirm that types aren't null before finishing
		if(type1 == null || type2 == null) {
			throw new IllegalArgumentException("A Pokemon must have a non-null type.");
		}
	}

	// PUBLIC: CREATE A POKEMON BASED ON AN EXISTING POKEMON

	/**
	 * Create a Pokemon with the same species-name, type(s), base-stats, drop-stats
	 * level, and move list.
	 * 
	 * Does NOT copy a Pokemon's EVs and nickname.
	 * 
	 * @param clone
	 */
	public Pokemon(Pokemon clone) {
		// copy the Pokemon's species-name, type(s), base-stats, and drop-stats
		this(clone.getName(), clone.types.toArray(new Type[0]),
				clone.stats.baseHP, clone.stats.baseAtk, clone.stats.baseDef,
				clone.rewardStats.hpEVsDropped, clone.rewardStats.atkEVsDropped, clone.rewardStats.defEVsDropped);

		// copy the Pokemon's level
		while(this.getLvl() < clone.getLvl())
			this.levelUp_NoCost();

		// copy the Pokemon's moves
		for(Move move : clone.getKnownMoves())
			this.teachMove(move);
	}

	/**
	 * Create a new Pokemon of the same species (name, type(s), base-stats, drop-stats)
	 * at the given level (different level, different moves).
	 * 
	 * @param template 
	 */
	public Pokemon(Pokemon template, int level) {
		// copy the Pokemon's species-name, type(s), base-stats, and drop-stats
		this(template.getName(), template.types.toArray(new Type[0]),
				template.stats.baseHP, template.stats.baseAtk, template.stats.baseDef, 
				template.rewardStats.hpEVsDropped, template.rewardStats.atkEVsDropped, template.rewardStats.defEVsDropped);

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
	 * Returns an array containing either 1 or 2 types.
	 * 
	 * @return the Pokemon's types
	 */
	public ArrayList<Type> getTypes() {
		
		return types;
	}

	/**
	 * Returns a map containing all types mapped to
	 * a damage multiplier (0.25x, 0.5x, 2x, 4x).
	 * Excludes 1x/neutral types.
	 * 
	 * @return the Pokemon's weaknesses
	 */
	private HashMap<Type, Double> getTypeMatchupMap() {
		HashMap<Type, Double> matchups = new HashMap<>();
		HashSet<Type> weaknesses2x = new HashSet<>();
		HashSet<Type> weaknesses4x = new HashSet<>();
		HashSet<Type> resistancesHalfX = new HashSet<>();
		HashSet<Type> resistancesQuarterX = new HashSet<>();

		weaknesses2x.addAll(types.get(0).getWeaknesses());
		resistancesHalfX.addAll(types.get(0).getResistances());

		if(types.size() == 2) {
			// weaknesses
			weaknesses2x.addAll(types.get(1).getWeaknesses());
			weaknesses2x.removeAll(types.get(0).getResistances());
			weaknesses2x.removeAll(types.get(1).getResistances());
			// 4x = intersection of weaknesses
			weaknesses4x.addAll(types.get(0).getWeaknesses());
			weaknesses4x.retainAll(types.get(1).getWeaknesses());
			weaknesses2x.removeAll(weaknesses4x);
			
			// resistances
			resistancesHalfX.addAll(types.get(1).getResistances());
			resistancesHalfX.removeAll(types.get(0).getWeaknesses());
			resistancesHalfX.removeAll(types.get(1).getWeaknesses());
			// Quarterx = intersection of resistances
			resistancesQuarterX.addAll(types.get(0).getResistances());
			resistancesQuarterX.retainAll(types.get(1).getResistances());
			resistancesHalfX.removeAll(resistancesQuarterX);
		}

		for(Type weak2x : weaknesses2x)
			matchups.put(weak2x, 2.0);
		for(Type weak4x : weaknesses4x)
			matchups.put(weak4x, 4.0);
		for(Type resistHalf : resistancesHalfX)
			matchups.put(resistHalf, 0.5);
		for(Type resistQuarter : resistancesQuarterX)
			matchups.put(resistQuarter, 0.25);
		return matchups;
	}

	/**
	 * Returns a set containing all types this Pokemon has a certain
	 * relationship to, i.e. the Pokemon is weak/resistant to by with a 
	 * "mult" damage multiplier (e.g. 2x, 0.25x).
	 * 
	 * @return types with the given relationship
	 */
	private HashSet<Type> getTypeMatchupsWithMultiplier(double mult) {
		HashSet<Type> outputTypes = new HashSet<>();

		for(Map.Entry<Type, Double> matchupEntry : getTypeMatchupMap().entrySet()) {
			Type type = matchupEntry.getKey();
			double multiplier = matchupEntry.getValue();

			if(multiplier == mult)
				outputTypes.add(type);
		}

		return outputTypes;
	}
	
	/**
	 * Returns a set containing all types this Pokemon is weak to.
	 * @return the Pokemon's weaknesses
	 */
	public HashSet<Type> getAllWeaknesses() {
		HashSet<Type> allWeaknesses = new HashSet<>();
		allWeaknesses.addAll( getTypeMatchupsWithMultiplier(2) );
		allWeaknesses.addAll( getTypeMatchupsWithMultiplier(4) );
		return allWeaknesses;
	}

	/**
	 * Returns a set containing all types this Pokemon is 2x weak to.
	 * @return the Pokemon's weaknesses
	 */
	public HashSet<Type> get2xWeaknesses() {
		HashSet<Type> weaknesses = new HashSet<>();
		weaknesses.addAll( getTypeMatchupsWithMultiplier(2) );
		return weaknesses;
	}

	/**
	 * Returns a set containing all types this Pokemon is 4x weak to.
	 * @return the Pokemon's weaknesses
	 */
	public HashSet<Type> get4xWeaknesses() {
		HashSet<Type> weaknesses = new HashSet<>();
		weaknesses.addAll( getTypeMatchupsWithMultiplier(4) );
		return weaknesses;
	}
	
	/**
	 * Returns a set containing all types this Pokemon resists.
	 * @return the Pokemon's resistances
	 */
	public HashSet<Type> getAllResistances() {
		HashSet<Type> allResistances = new HashSet<>();
		allResistances.addAll( getTypeMatchupsWithMultiplier(0.5) );
		allResistances.addAll( getTypeMatchupsWithMultiplier(0.25) );
		return allResistances;
	}
	
	/**
	 * Returns a set containing all types this Pokemon resists 
	 * (damage multiplier = 0.5x).
	 * 
	 * @return the Pokemon's resistances
	 */
	public HashSet<Type> getHalfResistances() {
		HashSet<Type> resistances = new HashSet<>();
		resistances.addAll( getTypeMatchupsWithMultiplier(0.5) );
		return resistances;
	}

	/**
	 * Returns a set containing all types this Pokemon resists 
	 * (damage multiplier = 0.25x).
	 * 
	 * @return the Pokemon's resistances
	 */
	public HashSet<Type> getQuarterResistances() {
		HashSet<Type> resistances = new HashSet<>();
		resistances.addAll( getTypeMatchupsWithMultiplier(0.25) );
		return resistances;
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
