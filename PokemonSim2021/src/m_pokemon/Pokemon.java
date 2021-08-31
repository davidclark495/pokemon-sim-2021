package m_pokemon;

import java.util.ArrayList;

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
	// The species' base stats
	// should be Final
	/**
	 * hp
	 * atk
	 * def
	 */
	private StatBlock baseStats;
	// The individual's persistent stats
	// Can be leveled-up, never go down
	/**
	 * hp
	 * atk
	 * def
	 */
	private StatBlock stats;
	
	// The individual's battle-related temporary stats
	// calculated based on circumstances such as Effects
	private StatBlock battleStats;
	
	//
	private int maxHP;
	private int level;
	private ArrayList<Effect> activeEffects;
	
	



	/**
	 * Create a Pokemon with a given species' name
	 * @param name
	 */
	public Pokemon(String name) {
		this.name = name;
	}

	/**
	 * Create a Pokemon with a given name and type.
	 * 
	 * @param name
	 * @param type must not be null
	 * @throws IllegalArgumentException if type is null
	 */
	public Pokemon(String name, Type type) {
		if(type == null) {
			throw new IllegalArgumentException();
		}

		this.name = name;
		this.type = type;
	}
	
	/**
	 * Create a clone of the given Pokemon.
	 * 
	 * @param clone
	 */
	public Pokemon(Pokemon clone) {
		this.name = clone.name;
		this.type = clone.type;
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




	///// Mutators /////

	/**
	 * Sets the Pokemon's nickname, no restrictions.
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}




	///// Moves /////

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

	public ArrayList<Move> getKnownMoves(){
		return knownMoves;
	}
	
	
	
	//////////////////////////////
	
	///// Inner Class /////

	//////////////////////////////

	/**
	 * Tracks all of this Pokemon's stats across four main categories.
	 * - base stats
	 *   : stats shared by all pokemon of a given species
	 * - persistent stats
	 *   : the individual's current stats at the current level 
	 *   : (these shouldn't go down)
	 *   : these increase when the pokemon levels-up, 
	 *     and the increase may be less than a full point
	 * - temporary/effective stats
	 *   : these should be reset/recalculated at the start of battles
	 *     and when the pokemon gets a new Effect
	 *   : determined by persistent stats + any ongoing Effects
	 * - battle reward stats
	 *   : drops... these matter to the pokemon that faints this one
	 *   : gains... used to calculate exp. gained, stat increases, etc.
	 * @author davidclark
	 * Date: 08/22/21
	 */
	private class StatBlock {
		
		// base stats
		private int baseHP, baseAtk, baseDef;
		// persistent stats
		private double maxHP, hp;
		private double atk, def;
		private int level, xp, nextLevelXP;
		// temp/effective stats
		private int tempAtk, tempDef;
		private double accuracy, evasion;
		// battle reward stats : gains
		private int hpEVsGained, atkEVsGained, defEVsGained;
		// battle reward stats : drops
		private int hpEVsDropped, atkEVsDropped, defEVsDropped;
		private int expReward;
		
		/**
		 * Initialize the StatBlock with the provided base stats 
		 * and derive persistent stats
		 * @param baseHP
		 * @param baseAtk
		 * @param baseDef
		 * @param level
		 */
		public StatBlock(int baseHP, int baseAtk, int baseDef, int level){
			this.baseHP = baseHP;
			this.baseAtk = baseAtk;
			this.baseDef = baseDef;
			
			this.level = 1;
			this.hp = 10;
			this.atk = 5;
			this.def = 5;
			
			while(level < level) {
				levelUp();
			}
		}
		
		///// Misc. Functionality /////
		/**
		 * Increase the Pokemon's level and adjust stats.
		 * - xp is reset to 0
		 * - nextLevelXP is increased
		 * - persistent stats are increased w/ regard to base stats
		 * - persistent stats are increased w/ regard to EVs gained
		 */
		public void levelUp() {
			//// adjust level/exp. stats ////
			level++;
			xp = 0;
			nextLevelXP *= 1.2;
			
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
	}
	
}
