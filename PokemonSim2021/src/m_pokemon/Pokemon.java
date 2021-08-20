package m_pokemon;

/**
 * Represents a pokemon, complete with name/type/stats/etc.
 * 
 * @author davidclark
 * Date: 08/18/21
 */
public class Pokemon {

	public static final int MAX_NUM_MOVES = 4;
	
	// The Pokemon's type
	private Type type1;
	// The species name
	private String name;
	// The individual's nickname
	private String nickname;
	// The Pokemon's list of attacks/moves
	private Move[] moves = new Move[MAX_NUM_MOVES];
	
	/**
	 * Create a Pokemon with a given species' name
	 * @param name
	 */
	public Pokemon(String name) {
		this.name = name;
	}
	
	
	
	///// Accessors /////
	
	/**
	 * 
	 * @return the Pokemon's species name
	 */
	public String getName() {
		return name;
	}
	
	
	
	///// Mutators /////
	
	
	
	
	
}
