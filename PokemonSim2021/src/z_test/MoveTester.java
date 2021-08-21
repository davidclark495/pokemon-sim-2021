package z_test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import m_pokemon.*;

/**
 * Contains tests for the Move class.
 * 
 * @author davidclark
 * Date: 08/21/21
 */
public class MoveTester {

	/**
	 * A Move's accuracy must be between 0.0 and 1.0, inclusive.
	 */
	@Test
	public void constructor_AccuracyRestricted() {
		Move move;
		
		// accuracy is too high
		move = new Move("Flamethrower", Type.FIRE, 20, 80, 27.05);
		assertEquals(1.0, move.getAccuracy());
		
		// accuracy is too low
		move = new Move("Flamethrower", Type.FIRE, 20, 80, -0.7);
		assertEquals(0.0, move.getAccuracy());
	}
	
	/**
	 * A Move created with the clone-constructor should have all the same parameters
	 * that the original had.
	 */
	@Test
	public void constructor_Clone() {
		// set up
		Move waterGun = new Move("Water Gun", Type.WATER, 25, 40, 1.0);
		
		Move clone = new Move(waterGun);
		
		// test
		assertEquals("Water Gun", clone.getName());
		assertEquals(Type.WATER, clone.getType());
		assertEquals(25, clone.getMaxPP());
		assertEquals(25, clone.getPP());
		assertEquals(40, clone.getBaseDmg());
		assertEquals(1.0, clone.getAccuracy());
		assertEquals(null, clone.getEffect());
	}
	
	/**
	 * A Move created with the clone-constructor should have pp == maxPP.
	 */
	@Test
	public void constructor_Clone_PP() {
		// set up
		int maxPP = 25;
		Move waterGun = new Move("Water Gun", Type.WATER, maxPP, 40, 1.0);
		
		// decrease the original's pp
		waterGun.usePP();
		assertNotEquals(waterGun.getPP(), maxPP);
		
		Move clone = new Move(waterGun);
		
		// test: show that the clone's pp is 100%
		assertEquals(maxPP, clone.getPP());
	}
	
	@Test
	public void getName() {
		// set up
		String expectedName = "Tackle";
		Move move = new Move(expectedName, Type.NORMAL, 35, 40, 1.0);
		
		// test
		assertEquals(expectedName, move.getName());
	}
	
	@Test
	public void getType() {
		Type expectedType = Type.NORMAL; 
		Move move = new Move("Tackle", expectedType, 35, 40, 1.0);
		
		// test
		assertEquals(expectedType, move.getType());
	}
	
	@Test
	public void getMaxPP() {
		int expectedMaxPP = 35;
		Move move = new Move("Tackle", Type.NORMAL, expectedMaxPP, 40, 1.0);

		// test
		assertEquals(expectedMaxPP, move.getMaxPP());
	}
	
	@Test
	public void getPP() {
		Move move = new Move("Tackle", Type.NORMAL, 35, 40, 1.0);

		// test
		assertEquals(35, move.getPP());
	}
	
	@Test
	public void getBaseDmg() {
		int expectedBaseDmg = 40;
		Move move = new Move("Tackle", Type.NORMAL, 35, expectedBaseDmg, 1.0);

		// test
		assertEquals(expectedBaseDmg, move.getBaseDmg());
	}
	
	@Test
	public void getAccuracy() {
		double expectedAccuracy = 0.6;
		Move move = new Move("Tackle", Type.NORMAL, 35, 40, expectedAccuracy);

		// test
		assertEquals(expectedAccuracy, move.getAccuracy());
	}
	
	/**
	 * Two moves should be equal if they have the same name.
	 * Ideally, all moves of a given name should be identical.
	 */
	@Test
	public void equals_Equal() {
		// set up
		String sharedName = "Hydro Pump";
		Move move1 = new Move(sharedName, Type.WATER, 5, 110, 0.8);
		Move move2 = new Move(sharedName, Type.FIRE, 900, 12, 0.3);

		// test
		assertTrue(move1.equals(move2));
		assertTrue(move2.equals(move1));
	}
	
	@Test
	public void equals_Unequal() {
		// set up
		Move move1 = new Move("Hydro Pump", Type.WATER, 5, 110, 0.8);
		Move move2 = new Move("Withdraw", Type.WATER, 40, 0, 1.0);

		// test: unequal moves
		assertFalse(move1.equals(move2));
		assertFalse(move2.equals(move1));
		
		// test: unequal objects
		assertFalse(move1.equals(new Object()));
	}
	
	@Test
	public void usePP() {
		// set up
		int maxPP = 35;
		Move move = new Move("Absorb", Type.GRASS, maxPP, 20, 1.0);
		
		// use the move until the PP is 0
		int timesUsed = 0;
		while(move.usePP()) {
			timesUsed++;		
		}
		
		// test: the move was used the correct number of times
		assertEquals(maxPP, timesUsed);
		// test: the move's pp is now 0
		assertEquals(0, move.getPP());
	}
	
	@Test
	public void fullRestorePP() {
		// set up
		int maxPP = 35;
		Move move = new Move("Absorb", Type.GRASS, maxPP, 20, 1.0);
		
		// use the move until the PP is 0
		int timesUsed = 0;
		while(move.usePP()) {
			timesUsed++;		
		}
		
		move.fullRestorePP();
		
		// test: the move has full pp again
		assertEquals(maxPP, move.getPP());
		// test: the move can be used again
		assertTrue(move.usePP());
	}
	
	
	///// MoveList Tests /////
	
	@Test
	public void getMove_MoveExists() {
		// set up
		String moveName = "Vine Whip";
		// only the name param. matters for this test
		Move expectedMove = new Move(moveName, Type.GRASS, -1, -1, -1);
		
		// test
		assertEquals(expectedMove, MoveList.getMove(moveName));
	}
	
}
