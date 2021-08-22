package m_game;

import java.util.ArrayList;
import java.util.Stack;

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
		player = new Player(",,,");
		Trainer trainer = player.getTrainer();
		Pokemon starterPokemon = PokemonList.getPokemon("Eevee");
		starterPokemon.teachMove(MoveList.getMove("Tackle"));
		trainer.addPokemonToParty(starterPokemon);
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
