package z_test;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import m_player.Player;
import m_player.Trainer;
import m_pokemon.Pokemon;
import m_pokemon.Type;

public class TrainerTester {

	/**
	 * Show that the player has a trainer, 
	 * the trainer can be accessed / modified.
	 */
	@Test
	public void PlayerTrainerLink() {
		// set up
		Player player = new Player("Calem");
		Trainer trainer = player.getTrainer();

		// test
		String trainerName = player.getTrainer().getName();;
		assertEquals(trainer.getName(), trainerName);
	}
	
	@Test
	public void TrainerPokemonLink() {
		// set up
		Trainer trainer = new Trainer("May");
		Pokemon pokemon = new Pokemon("Tangela", Type.NotYetImplemented, 0,0,0,0,0,0);
		
		trainer.addPokemonToParty(pokemon);
		
		// test
		String pokeName = trainer.getFirstPokemon().getName();
		assertEquals(pokemon.getName(), pokeName);
	}

	/**
	 * Show that 
	 */
	@Test
	public void PlayerTrainerPokemonLink(){
		// set up
		Player player = new Player("Lucas");
		Trainer trainer = player.getTrainer();
		Pokemon pokemon = new Pokemon("Pikachu", Type.NotYetImplemented, 0,0,0,0,0,0);

		trainer.addPokemonToParty(pokemon);

		// test
		String pokeName = player.getTrainer().getFirstPokemon().getName();
		assertEquals(pokemon.getName(), pokeName);
	}
}
