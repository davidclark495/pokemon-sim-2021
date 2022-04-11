package m_pokemon;

import java.util.HashSet;

/**
 * Contains methods for common battle-related operations.
 * 
 * @author davidclark
 * Date: 09/03/21
 */
public class PokemonBattleExtensions {

	/**
	 * VERY UNFINISHED
	 * MAYBE THIS SHOULD BE HANDLED BY AN ACTIVITY ?
	 * 	benefits: 
	 * 		would allow the activity to collect narration
	 * 		would have access to more environmental factors
	 * 		would remove the need for PokemonBattleExtensions.java
	 * 		could guarantee an up-to-date "BattleStats" block exists
	 * 	costs:
	 * 		still needs access to StatBlocks
	 * 		would clutter-up the Activity
	 * 		would have to be shared between WildActivity, TrainerActivity
	 * 
	 * Calculates how much damage (hp loss) the defending Pokemon would take,
	 * given the parameters, then changes the defender's hp accordingly.
	 * 
	 * @param defender The pokemon getting hit
	 * @param attacker The pokemon that used the move
	 * @param move The move that was used
	 * @return a string of narration describing what happened.
	 */
	public static String processAttack(Pokemon defender, Pokemon attacker, Move move) {
		// calculate STAB multiplier
		double stabMult = 1;
		if(attacker.getTypes().contains(move.getType()))
			stabMult = 1.5;

		// calculate type multiplier, i.e. weak, resist, or normal
		// (in other words, this pokemon's relationship to the move's type)
		double typeMult = 1;
		if(defender.getAllWeaknesses().contains(move.getType()))
			typeMult *= 2;
		if(defender.getAllResistances().contains(move.getType()))
			typeMult *= 0.5;

		// calculate final damage
		double dmg = move.getBaseDmg() / 50.0;		// move-related multipliers
		dmg *= stabMult * typeMult;					// type-related multipliers
		dmg *= attacker.getTempAtk() / 
				defender.getTempDef(); 				// stat-related multipliers
		dmg *= attacker.getLvl() * 2.0 / 5.0; 		// level-related multipliers

		// take damage
		defender.loseHP((int)dmg);

		// return narration
		return "";
	}

	/**
	 * VERY UNFINISHED
	 * MAYBE THIS SHOULD BE HANDLED BY AN ACTIVITY ?
	 * 	benefits: 
	 * 		would allow the activity to collect narration
	 * 		would centralize the process
	 * 	costs:
	 * 		would clutter-up the Activity
	 * 		would have to be shared between WildActivity, TrainerActivity
	 * 
	 * Rewards the victorious pokemon with EVs dropped by the other pokemon, 
	 * then increases this pokemon's exp, potentially causing it to level up, 
	 * possibly multiple times.
	 * 
	 * 
	 * @param defeated
	 */
	public String processVictory(Pokemon victor, Pokemon defeated) {	
		String narration = "";
		narration += String.format("%s won! ", 
				victor.getNickname());

		// update: give the victor EVs
		victor.gainEVs(defeated.getRewardBlock());

		// update: give the victor XP
		int xpGained = defeated.getLvl() * 10;
		victor.gainXP(xpGained);
		
		narration += String.format("%s gained %d experience.\n", 
				victor.getNickname(), victor.getNickname(), xpGained);


		// update: potentially level-up
		while(victor.isReadyToLevelUp()) {
			// ...save these for narration
			int prevLvl = victor.getLvl();
			int prevXP = victor.getXP();
			int prevNextLevelXP = victor.getNextLevelXP();
			int prevHP = victor.getHP();
			int prevMaxHP = victor.getMaxHP();
			int prevAtk = victor.getAtk();
			int prevDef = victor.getDef();
			
			victor.levelUp();
			
			narration += String.format("%s leveled up!\n", 
					victor.getNickname());		
			narration += String.format("Level:	%d -> %d\n", prevLvl, victor.getLvl());
			narration += String.format("Exp: 	%d/%d -> %d/%d\n", prevXP, prevNextLevelXP, victor.getXP(), victor.getNextLevelXP());
			narration += String.format("HP: 	%d/%d -> %d/%d\n", prevHP, prevMaxHP, victor.getHP(), victor.getMaxHP());
			narration += String.format("ATK: 	%d -> %d\n", prevAtk, victor.getAtk());
			narration += String.format("DEF: 	%d -> %d\n", prevDef, victor.getDef());
		}

		return narration;
	}



}
