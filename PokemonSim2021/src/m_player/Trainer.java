package m_player;

import java.util.ArrayList;
import m_pokemon.*;

/**
 * Represents a Trainer with a Pokemon team (either an NPC or player).
 * 
 * @author davidclark
 * Date: 08/18/21
 */
public class Trainer {

	private static final int MAX_PARTY_SIZE = 6;
	
	private String name;
	private ArrayList<Pokemon> party = new ArrayList<Pokemon>();
	
	/**
	 * Creates a Trainer with the given name.
	 * @param name
	 */
	public Trainer(String name) {
		this.name = name;
	}
	
	
	///// Misc. Functionality /////
	
	/**
	 * If the player has space in their party, 
	 * adds the pokemon to their party and returns true.
	 * Otherwise, returns false.
	 * @param poke the Pokemon being added
	 * @return true if successful, false otherwise
	 */
	public boolean addPokemonToParty(Pokemon poke) {
		if(party.size() < MAX_PARTY_SIZE) {
			party.add(poke);
			return true;
		}
		
		return false;
	}
	
	
	
	///// Accessors /////
	/**
	 * Returns the Trainer's name.
	 * @return the Trainer's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the first Pokemon in the Trainer's party.
	 * (This is the actual Pokemon, not a copy or read-only entity.)
	 * @return the first Pokemon in the Trainer's party
	 */
	public Pokemon getFirstPokemon() {
		return party.get(0);
	}
	
	/**
	 * Returns an ArrayList of all Pokemon in the Trainer's party.
	 * @return
	 */
	public ArrayList<Pokemon> getParty(){
		return party;
	}
	
	
	
	///// Mutators ///// 
	
	
}
