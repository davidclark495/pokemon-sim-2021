package z_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import m_pokemon.Move;
import m_pokemon.MoveList;
import m_pokemon.Pokemon;
import m_pokemon.Type;

/**
 * Contains methods that test the Pokemon class.
 * 
 * @author davidclark
 * Date: 08/20/21
 */
public class PokemonTester {

	/**
	 * The constructor should throw an Exception if passed "null" as it's type.
	 */
	@Test
	public void constructor_NullType() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Pokemon("missingno", null) ;
		});
	}

	@Test
	public void getName() {
		// set up
		String expectedName = "Shuckle";
		Pokemon pokemon = new Pokemon(expectedName);

		// test
		assertEquals(expectedName, pokemon.getName());
	}

	/**
	 * When the Pokemon's nickname is null, 
	 * getNickname() should return the Pokemon's name.
	 */
	@Test
	public void getNickname_Null() {
		// set up
		String expectedName = "Espurr";
		Pokemon pokemon = new Pokemon(expectedName);

		// test
		assertEquals(expectedName, pokemon.getNickname());
	}

	/**
	 * When the Pokemon's nickname is not null, 
	 * getNickname() should return the Pokemon's nickname.
	 */
	@Test
	public void getNickname_NotNull() {
		// set up
		Pokemon pokemon = new Pokemon("Espurr");

		String expectedNickname = "Eesp";
		pokemon.setNickname(expectedNickname);

		// test
		assertEquals(expectedNickname, pokemon.getNickname());
	}

	@Test
	public void getType() {
		// set up
		Type expectedType = Type.GRASS;
		Pokemon pokemon = new Pokemon("Carnivine", expectedType);

		// test
		assertEquals(expectedType, pokemon.getType());
	}

	@Test
	public void teachMove_Success() {
		// set up
		Move m1 = MoveList.getMove("Bubble");
		Move m2 = MoveList.getMove("Water Gun");
		Move m3 = MoveList.getMove("Water Pulse");
		Move m4 = MoveList.getMove("Spark");

		ArrayList<Move> expectedMoves = new ArrayList<Move>();
		expectedMoves.add(m1);
		expectedMoves.add(m2);
		expectedMoves.add(m3);
		expectedMoves.add(m4);

		Pokemon pokemon = new Pokemon("Chinchou");
		boolean result1 = pokemon.teachMove(m1);
		boolean result2 = pokemon.teachMove(m2);
		boolean result3 = pokemon.teachMove(m3);
		boolean result4 = pokemon.teachMove(m4);

		// test: teachMove() returned the correct results
		assertTrue(result1);
		assertTrue(result2);
		assertTrue(result3);
		assertTrue(result4);

		// test: teachMove() created the correct moveset
		assertEquals(expectedMoves, pokemon.getKnownMoves());
	}

	/**
	 * Teaching a Pokemon a move that it already knows should return false,
	 * and should not change its list of known moves.
	 */
	@Test
	public void teachMove_Fail_DuplicateMove() {
		// set up
		Pokemon pokemon = new Pokemon("Chinchou");
		Move move = MoveList.getMove("Bubble");

		ArrayList<Move> expectedMoves = new ArrayList<Move>();
		expectedMoves.add(move);

		boolean firstTry = pokemon.teachMove(move);
		boolean secondTry = pokemon.teachMove(move);

		// test: teachMove() returned the correct results
		assertTrue(firstTry);
		assertFalse(secondTry);

		// test: teachMove() didn't allow the duplicate to be added 
		assertEquals(expectedMoves, pokemon.getKnownMoves());
	}

	@Test
	public void teachMove_Fail_TooManyMoves() {
		// set up
		Move m1 = MoveList.getMove("Bubble");
		Move m2 = MoveList.getMove("Water Gun");
		Move m3 = MoveList.getMove("Water Pulse");
		Move m4 = MoveList.getMove("Spark");
		Move m5 = MoveList.getMove("Confusion");

		ArrayList<Move> expectedMoves = new ArrayList<Move>();
		expectedMoves.add(m1);
		expectedMoves.add(m2);
		expectedMoves.add(m3);
		expectedMoves.add(m4);
		// m5 should not be added

		Pokemon pokemon = new Pokemon("Chinchou");
		pokemon.teachMove(m1);
		pokemon.teachMove(m2);
		pokemon.teachMove(m3);
		pokemon.teachMove(m4);
		boolean result5 = pokemon.teachMove(m5);

		// test: teachMove() returned the correct result
		assertFalse(result5);

		// test: teachMove() didn't add the fifth move
		assertEquals(expectedMoves, pokemon.getKnownMoves());
	}

	@Test
	public void forgetMove() {
		// set up
		Pokemon pokemon = new Pokemon("Chinchou");
		Move m1 = MoveList.getMove("Bubble");
		Move m2 = MoveList.getMove("Water Gun");
		Move m3 = MoveList.getMove("Water Pulse");
		Move m4 = MoveList.getMove("Spark");

		ArrayList<Move> expectedMoves = new ArrayList<Move>();
		expectedMoves.add(m1);
		expectedMoves.add(m2);
		expectedMoves.add(m3);
		expectedMoves.add(m4);

		pokemon.teachMove(m1);
		pokemon.teachMove(m2);
		pokemon.teachMove(m3);
		pokemon.teachMove(m4);

		// test: correct starting point
		assertEquals(expectedMoves, pokemon.getKnownMoves());

		// more set up + test
		expectedMoves.remove(m2);
		pokemon.forgetMove(m2);
		assertEquals(expectedMoves, pokemon.getKnownMoves());

		// forget all moves + test
		expectedMoves.remove(m4);
		expectedMoves.remove(m1);
		expectedMoves.remove(m3);
		pokemon.forgetMove(m4);
		pokemon.forgetMove(m1);
		pokemon.forgetMove(m3);
		assertEquals(expectedMoves, pokemon.getKnownMoves());

		// forget an unknown move + test (shouldn't change anything)
		pokemon.forgetMove(MoveList.getMove("Flame Wheel"));
		assertEquals(expectedMoves, pokemon.getKnownMoves());
	}

	/**
	 * A Pokemon should be able to learn a full set of moves, 
	 * then forget moves,
	 * then learn moves again.
	 * Repeat ad infinitum (or any arbitrary number of times).
	 */
	@Test
	public void forgetMove_teachMove_Loop() {
		// set up
		Pokemon pokemon = new Pokemon("Chinchou");
		Move m1 = MoveList.getMove("Bubble");
		Move m2 = MoveList.getMove("Water Gun");
		Move m3 = MoveList.getMove("Water Pulse");
		Move m4 = MoveList.getMove("Spark");

		for(int i = 0; i < 100; i++) {			
			assertTrue(pokemon.teachMove(m1));
			assertTrue(pokemon.teachMove(m2));
			assertTrue(pokemon.teachMove(m3));
			assertTrue(pokemon.teachMove(m4));

			pokemon.forgetMove(m1);
			pokemon.forgetMove(m2);
			pokemon.forgetMove(m3);
			pokemon.forgetMove(m4);
		}

		assertTrue(pokemon.getKnownMoves().isEmpty());
	}



}
