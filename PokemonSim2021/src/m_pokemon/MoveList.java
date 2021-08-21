package m_pokemon;

import java.util.HashMap;

/**
 * A static class that other classes can get pre-fab Move objects from.
 * Establishes consistency across Moves in the project.
 * 
 * To-do: should read Move data from a file
 * 
 * @author davidclark
 * Date: 08/21/21
 */
public class MoveList {

	///// Lookup Feature /////

	private static HashMap<String, Move> allMoves = new HashMap<String, Move>();

	public static Move getMove(String moveName) {
		return new Move(allMoves.get(moveName));
	}

	///// List /////

	private static void addMoveToMap(Move move) {
		allMoves.put(move.getName(), move);
	}

	// NORMAL
	private static Move tackle = new Move("Tackle", Type.NORMAL, 35, 40, 1.0);
	// FIRE
	private static Move ember = new Move("Ember", Type.FIRE, 25, 40, 1.0);
	private static Move flameWheel = new Move("Flame Wheel", Type.FIRE, -1, -1, -1);
	// WATER
	private static Move bubble = new Move("Bubble", Type.WATER, 30, 40, 1.0);
	private static Move waterGun = new Move("Water Gun", Type.WATER, -1, -1, -1);
	private static Move waterPulse = new Move("Water Pulse", Type.WATER, -1, -1, -1);
	// GRASS
	private static Move vinewhip = new Move("Vine Whip", Type.GRASS, 30, 45, 1.0);
	// OTHER
	private static Move spark = new Move("Spark", Type.NotYetImplemented, -1, -1, -1);
	private static Move confusion = new Move("Confusion", Type.NotYetImplemented, -1, -1, -1);

	static {
		// NORMAL
		addMoveToMap(tackle);
		// FIRE
		addMoveToMap(ember);
		// WATER
		addMoveToMap(bubble);
		// GRASS
		addMoveToMap(vinewhip);
		// OTHER

		// NOT FINISHED, NEED STATS
		addMoveToMap(flameWheel);
		addMoveToMap(waterGun);
		addMoveToMap(waterPulse);
		addMoveToMap(spark);
		addMoveToMap(confusion);
	}

}
