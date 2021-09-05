package z_test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import m_pokemon.Type;

public class TypeTester {

	
	@Test
	public void getWeaknesses_Fire() {
		// set up
		HashSet<Type> expectedWeaknesses = new HashSet<Type>();
		expectedWeaknesses.add(Type.WATER);
		
		// test
		assertEquals(expectedWeaknesses, Type.FIRE.getWeaknesses());
		assertEquals(expectedWeaknesses, Type.getWeaknesses(Type.FIRE));
	}
	
	@Test
	public void getResistances_Fire() {
		// set up
		HashSet<Type> expectedResistances = new HashSet<Type>();
		expectedResistances.add(Type.FIRE);
		expectedResistances.add(Type.GRASS);
		
		// ordered comparison
		assertEquals(expectedResistances, Type.FIRE.getResistances());
		assertEquals(expectedResistances, Type.getResistances(Type.FIRE));
	}
	
	@Test
	public void isWeakTo_Fire() {
		assertTrue(Type.FIRE.isWeakTo(Type.WATER));
		assertFalse(Type.FIRE.isWeakTo(Type.GRASS));
		assertFalse(Type.FIRE.isWeakTo(Type.NORMAL));
	}
	
	@Test
	public void isResistantTo_Fire() {
		assertTrue(Type.FIRE.isResistantTo(Type.GRASS));
		assertFalse(Type.FIRE.isResistantTo(Type.WATER));
		assertFalse(Type.FIRE.isResistantTo(Type.NORMAL));
	}
	
	


}
