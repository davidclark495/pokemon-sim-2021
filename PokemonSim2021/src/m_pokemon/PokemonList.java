package m_pokemon;

import java.util.HashMap;


/**
 * A static class that other classes can get pre-fab Pokemon objects from.
 * Establishes consistency across Pokemon in the project.
 * 
 * To-do: should read Pokemon data from a file
 * 
 * @author davidclark
 * Date: 08/21/21
 */
public class PokemonList {

	///// Lookup Feature /////

	private static HashMap<String, Pokemon> allPokemon = new HashMap<String, Pokemon>();

	/**
	 * Return a copy of one of the registered Pokemon.
	 * 
	 * @param pokemonName
	 * @return
	 */
	public static Pokemon getPokemon(String pokemonName) {
		return new Pokemon(allPokemon.get(pokemonName));
	}

	///// List /////

	private static void addPokemonToMap(Pokemon poke) {
		allPokemon.put(poke.getName(), poke);
	}

	// NORMAL
	private static Pokemon eevee = new Pokemon("Eevee", Type.NORMAL, 0,0,0,0,0,0);
	// FIRE
	private static Pokemon charmander = new Pokemon("Charmander", Type.FIRE, 0,0,0,0,0,0);
	// WATER
	// GRASS
	// OTHER
	
	static {
		// NORMAL
		addPokemonToMap(eevee);
		// FIRE
		addPokemonToMap(charmander);
		// WATER
		// GRASS
		// OTHER
	}

	///// List: XML parser /////
}
