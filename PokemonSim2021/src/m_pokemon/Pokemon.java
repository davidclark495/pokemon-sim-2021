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

	// The Pokemon's type
	private Type type;
	// The species name
	private String name;
	// The individual's nickname
	private String nickname;

	// The Pokemon's list of attacks/moves
	private ArrayList<Move> knownMoves = new ArrayList<Move>();



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


}
