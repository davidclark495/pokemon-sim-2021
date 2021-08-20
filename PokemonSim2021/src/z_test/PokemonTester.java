package z_test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import m_pokemon.Pokemon;

public class PokemonTester {

	@Test
	public void GetName() {
		String expectedName = "Shuckle";
		Pokemon pokemon = new Pokemon(expectedName);
		
		assertEquals(expectedName, pokemon.getName());
	}
	
}
