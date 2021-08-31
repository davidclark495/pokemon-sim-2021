package z_WIP_sandbox;

import java.util.ArrayList;

import m_bag.Bag;
import m_bag.Item;
import m_game.Activity;
import m_game.GameModel;
import m_player.Trainer;
import m_pokemon.Move;
import m_pokemon.Pokemon;

public class BattleActivity extends Activity{

	// ACTIVITY 
	// tracks the state of the world (player/trainer/pokemon)
	private GameModel model;
	// ACTIVITY 
	// tracks the state of the activity
	private OptionBlock currentOptionBlock;
	// ACTIVITY 
	private boolean finalResultSent;


	// BATTLE ACTIVITY this
	// internal model stuff, specific to this activity
	
	// short-cuts, can be derived from the base / ACTIVITY model
	//private Player player;
	//private Trainer trainer;
	
	// needed for dynamic menus
	private Item selectedItem = null;
	private Pokemon selectedPokemon = null;


	public BattleActivity(GameModel model) {
		super(model);
		this.model = model;

		loadStartBlock();
		load_fightBlock();
		load_bagBlock();
		load_bag_itemBlock();
		load_teamBlock();
		load_team_pokemonBlock();
		load_team_pokemon_healBlock();
	}




	//////////////////////////////

	///// @Override Methods /////

	//////////////////////////////


	// load the initial options
	@Override
	public Result startActivity() {
		currentOptionBlock = startBlock;
		return null;
	}

	// return the current OptionBlock
	@Override
	public OptionBlock getOptions() {
		return currentOptionBlock;
	}

	/**
	 * Accept an Option and use the Result to 
	 * update the model and return a String with narration.
	 * 
	 * @param selection 
	 */
	@Override
	public String selectOption(Option selection) {
		// potential error: "selection" isn't from "currentOptionBlock"

		Result result = selection.getResult();

		currentOptionBlock = result.nextOptBlock;

		// update the model,
		// and save the resulting message
		String narration;
		narration = result.updater.updateModel(null);

		// if this was the final result, record that
		this.finalResultSent = result.finalMessage;

		return narration;
	}

	// Should be Override
	public boolean isFinalResultSent() {
		return finalResultSent;
	}




	//////////////////////////////

	///// event stuff /////

	//////////////////////////////


	// use to get text input (i.e. not Option based) during model activities
	private ArrayList<Responder> allResponders;

	public interface Responder {
		void giveInput(String input);
	}

	public void addListener(Responder responder) {
		allResponders.add(responder);
	}
	/// end event stuff ///




	//////////////////////////////

	///// list of needed OptionBlock objects /////

	//////////////////////////////


	OptionBlock startBlock = new OptionBlock();
	OptionBlock _fightBlock = new OptionBlock();
	OptionBlock _bagBlock = new OptionBlock();
	OptionBlock _bag_itemBlock = new OptionBlock();
	OptionBlock _teamBlock = new OptionBlock();
	OptionBlock _team_pokemonBlock = new OptionBlock();
	OptionBlock _team_pokemon_healBlock = new OptionBlock();


	// all blocks should be loaded after the model is received

	// startBlock
	// fixed option block
	private void loadStartBlock() {
		/// define prompt ///
		startBlock.prompt = "What do you do?";

		/// define options ///
		startBlock.options = new ArrayList<Option>();

		// opt 1  // FIGHT
		Option opt1 = new Option("1", "Fight", new Result());
		// opt 1 : result
		{
			// opt 1 : result.modelUpdate
			Result res = opt1.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You chose to fight."); 
				return narration;
			};
			// opt 1 : result.optionBlock
			res.nextOptBlock = _fightBlock;
		}

		// opt 2 // BAG
		Option opt2 = new Option("2", "Bag", new Result());
		// opt 2 : result
		{
			// opt 2 : result.modelUpdate
			Result res = opt2.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You opened the bag.");
				return narration;
			};
			// opt 2 : result.optionBlock
			res.nextOptBlock = _bagBlock;
		}

		// opt 3 // TEAM
		Option opt3 = new Option("3", "Team", new Result());
		// opt 3 : result
		{
			// opt 3 : result.modelUpdate
			Result res = opt3.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You check your team.");
				return narration;
			};
			// opt 3 : result.optionBlock
			res.nextOptBlock = _teamBlock;
		}

		// opt 4 // RUN
		Option opt4 = new Option("4", "Run", new Result());
		// opt 4 : result
		{
			// opt 4 : result.modelUpdate
			Result res = opt4.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You got away safely.");
				return narration;
			};
			// opt 4 : result.optionBlock
			res.nextOptBlock = null;
			// opt 4 : result.activity
			res.nextActivity = null;
			// opt 4 : result.end
			res.finalMessage = true;
		}

		startBlock.options.add(opt1);
		startBlock.options.add(opt2);
		startBlock.options.add(opt3);
		startBlock.options.add(opt4);
	}

	// _fightBlock
	// dynamic option block
	private void load_fightBlock(){
		/// define prompt ///
		_fightBlock.prompt = "What move do you use?";

		/// define options ///
		_fightBlock.options = new ArrayList<Option>();


		Pokemon playerPoke = model.getPlayer().getTrainer().getFirstPokemon();

		int i = 1;
		for(Move move : playerPoke.getKnownMoves()) {
			// opt 'i' // Move 'i'
			Option opt = new Option(""+i, move.getName(), new Result());
			// opt 'i' : result
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = "";
				narration += String.format("%s used %s.\n", 
						playerPoke.getNickname(), move.getName()); 
				narration += String.format("The enemy %s used %s.", 
						"Gible", "Rollout"); 

				return narration;
			};
			// opt 'i' : result.optionBlock
			res.nextOptBlock = startBlock;

			// add option to all options
			_fightBlock.options.add(opt);
		}

		// opt BACK // 
		Option optBack = new Option("BACK", "return to the previous menu", new Result());
		// opt BACK : result
		{
			// opt BACK : result.modelUpdate
			Result res = optBack.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = startBlock;
		}
		_fightBlock.options.add(optBack);
	}

	// _bagBlock
	// dynamic option block
	private void load_bagBlock(){
		/// define prompt ///
		_bagBlock.prompt = "Choose an item to inspect.";

		/// define options ///
		_bagBlock.options = new ArrayList<Option>();


		Bag bag = model.getPlayer().getBag();

		// the Item's id (for selection purposes)
		int id = 1;
		for(Item item : bag.getItemSet()) {
			// opt 'id' // Move 'id'
			Option opt = new Option(""+id, item.getName(), new Result());
			// opt 'id' : result
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update
				this.selectedItem = item;

				// get narration
				String narration = String.format("You selected %s.", 
						selectedItem.getName());
				return narration;
			};
			// opt 'id' : result.optionBlock
			res.nextOptBlock = _bag_itemBlock;

			// add option to all options
			_bagBlock.options.add(opt);

			// increment the id
			id++;
		}

		// opt BACK // 
		Option optBack = new Option("BACK", "return to the previous menu", new Result());
		// opt BACK : result
		{
			// opt BACK : result.modelUpdate
			Result res = optBack.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = startBlock;
		}
		_bagBlock.options.add(optBack);
	}

	// _bag_itemBlock
	// fixed options, dynamic subject 
	private void load_bag_itemBlock() {
		/// define prompt ///
		_bag_itemBlock.prompt = "What will you do with this item?";

		/// define options ///
		_bag_itemBlock.options = new ArrayList<Option>();

		// opt 1  // SWAP
		Option opt1 = new Option("1", "Use", new Result());
		// opt 1 : result
		{
			// opt 1 : result.modelUpdate
			Result res = opt1.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You used the %s.", 
						this.selectedItem.getName()); 
				return narration;
			};
			// opt 1 : result.optionBlock
			res.nextOptBlock = startBlock;
		}

		// opt BACK // 
		Option optBack = new Option("BACK", "return to the previous menu", new Result());
		// opt BACK : result
		{
			// opt BACK : result.modelUpdate
			Result res = optBack.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = _bagBlock;
		}

		_bag_itemBlock.options.add(opt1);
		_bag_itemBlock.options.add(optBack);
	}

	// _teamBlock
	// dynamic option block
	private void load_teamBlock(){
		/// define prompt ///
		_teamBlock.prompt = "Choose a pokemon to inspect.";

		/// define options ///
		_teamBlock.options = new ArrayList<Option>();

		Trainer trainer = model.getPlayer().getTrainer();

		// the Item's id (for selection purposes)
		int id = 1;
		for(Pokemon poke : trainer.getParty()) {
			// opt 'id' // Move 'id'
			Option opt = new Option(""+id, poke.getName(), new Result());
			// opt 'id' : result
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update
				this.selectedPokemon = poke;

				// get narration
				String narration = String.format("You selected %s.", 
						selectedPokemon.getName());
				return narration;
			};
			// opt 'id' : result.optionBlock
			res.nextOptBlock = _team_pokemonBlock;

			// add option to all options
			_teamBlock.options.add(opt);

			// increment the id
			id++;
		}

		// opt BACK // 
		Option optBack = new Option("BACK", "return to the previous menu", new Result());
		// opt BACK : result
		{
			// opt BACK : result.modelUpdate
			Result res = optBack.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = startBlock;
		}
		_teamBlock.options.add(optBack);
	}

	// _team_pokemonBlock
	// fixed options, dynamic subject 
	//   (i.e. needs parameters or a dedicated field in the Activity's model)
	private void load_team_pokemonBlock() {
		/// define prompt ///
		_team_pokemonBlock.prompt = "What will you do with this Pokemon?";

		/// define options ///
		_team_pokemonBlock.options = new ArrayList<Option>();

		// opt 1  // SWAP
		Option opt1 = new Option("1", "Swap to Front", new Result());
		// opt 1 : result
		{
			// opt 1 : result.modelUpdate
			Result res = opt1.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You swapped %s to the front.", 
						selectedPokemon.getNickname()); 
				
				return narration;
			};
			// opt 1 : result.optionBlock
			res.nextOptBlock = startBlock;
		}

		// opt 2 // HEAL
		Option opt2 = new Option("2", "Heal", new Result());
		// opt 2 : result
		{
			// opt 2 : result.modelUpdate
			Result res = opt2.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt 2 : result.optionBlock
			res.nextOptBlock = _team_pokemon_healBlock;
		}

		// opt BACK // 
		Option optBack = new Option("BACK", "return to the previous menu", new Result());
		// opt BACK : result
		{
			// opt BACK : result.modelUpdate
			Result res = optBack.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = _teamBlock;
		}

		_team_pokemonBlock.options.add(opt1);
		_team_pokemonBlock.options.add(opt2);
		_team_pokemonBlock.options.add(optBack);
	}

	// _team_pokemon_healBlock
	// dynamic options, dynamic subject 
	//   (i.e. needs parameters or a dedicated field in the Activity's model)
	private void load_team_pokemon_healBlock() {
		/// define prompt ///
		_team_pokemon_healBlock.prompt = "Which heal item will you use?";

		/// define options ///
		_team_pokemon_healBlock.options = new ArrayList<Option>();


		Bag bag = model.getPlayer().getBag();

		int id = 1;
		for(Item item : bag.getItemSet()) {
			// opt 'id' // Move 'id'
			Option opt = new Option(""+id, item.getName(), new Result());
			// opt 'id' : result
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You used the %s on %s.", 
						item.getName(), selectedPokemon.getNickname());

				return narration;
			};
			// opt 'id' : result.optionBlock
			res.nextOptBlock = startBlock;

			// add option to all options
			_team_pokemon_healBlock.options.add(opt);

			// increment the id
			id++;
		}
		// opt BACK // 
		Option optBack = new Option("BACK", "return to the previous menu", new Result());
		// opt BACK : result
		{
			// opt BACK : result.modelUpdate
			Result res = optBack.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = startBlock;
		}
		_team_pokemon_healBlock.options.add(optBack);
	}


}