package m_game;

import java.util.ArrayList;
import java.util.Stack;

import m_activities.Activity;
import m_bag.Bag;
import m_bag.Item;
import m_player.Player;
import m_player.Trainer;
import m_pokemon.MoveList;
import m_pokemon.Pokemon;
import m_pokemon.PokemonList;
import m_pokemon.Type;

/**
 * Tracks the state of game. 
 * Switches between different activities (i.e. fights, settings, travel).
 * The core of the game.
 * 
 * @author davidclark
 * Date: 08/18/21
 */
public class GameModel {

	// The Player's stand-in, used throughout
	Player player;
	// The list of all on-going activities
	Stack<Activity> activityStack;
	
	public GameModel() {
		/// set up the player ///
		player = new Player("...");

		// set up the player's bag
		Bag bag = player.getBag();
		for(Item item : Bag.getBasicBag().getItemSet()) {
			bag.addItem(item);
		}
		
		// set up the trainer
		Trainer trainer = player.getTrainer();
		
		// set up the trainer's party
		Pokemon poke1 = PokemonList.getPokemon("Eevee");
		poke1.teachMove(MoveList.getMove("Tackle"));
		trainer.addPokemonToParty(poke1);
		
		Pokemon poke2 = PokemonList.getPokemon("Charmander");
		poke2.teachMove(MoveList.getMove("Ember"));
		poke2.teachMove(MoveList.getMove("Flame Wheel"));
		trainer.addPokemonToParty(poke2);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
