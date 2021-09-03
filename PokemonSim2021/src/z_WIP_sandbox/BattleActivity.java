package z_WIP_sandbox;

import java.util.ArrayList;

import m_activities.Activity;
import m_activities.OptionBlock;
import m_activities.Option;
import m_activities.Result;
import m_bag.Bag;
import m_bag.HealItem;
import m_bag.Item;
import m_game.GameModel;
import m_player.Trainer;
import m_pokemon.Move;
import m_pokemon.Pokemon;

public class BattleActivity extends Activity{

	// internal model stuff:
	

	public BattleActivity(GameModel model) {
		super(model);

		startBlock = new StartBlock();
		_fightBlock = new _FightBlock();
		_bagBlock = new _BagBlock();
		_bag_itemBlock = new _Bag_ItemBlock();
		_teamBlock = new _TeamBlock();
		_team_pokemonBlock = new _Team_PokemonBlock();
		_team_pokemon_healBlock = new _Team_Pokemon_HealBlock();

		startBlock.initializeOptions(model);
		_fightBlock.initializeOptions(model);
		_bagBlock.initializeOptions(model);
		_bag_itemBlock.initializeOptions(model);
		_teamBlock.initializeOptions(model);
		_team_pokemonBlock.initializeOptions(model);
		_team_pokemon_healBlock.initializeOptions(model);

		//loadStartBlock();
		//load_fightBlock();
		//load_bagBlock();
		//load_bag_itemBlock();
		//load_teamBlock();
		//load_team_pokemonBlock();
		//load_team_pokemon_healBlock();
	}

	// load the initial options
	@Override
	public Result startActivity() {
		currentOptionBlock = startBlock;

		Result firstResult = new Result();
		//		firstResult.updater = (String s) => {
		//				return "";
		//		}

		return null;
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


	private StartBlock startBlock;
	private _FightBlock _fightBlock;
	private _BagBlock _bagBlock;
	private _Bag_ItemBlock _bag_itemBlock;
	private _TeamBlock _teamBlock;
	private _Team_PokemonBlock _team_pokemonBlock;
	private _Team_Pokemon_HealBlock _team_pokemon_healBlock;

	//////////////////////////////////////

	///// Inner Classes: Opt. Blocks /////

	//////////////////////////////////////

	// all blocks should be constructed after the model is received

	// startBlock
	// fixed option block	
	private class StartBlock extends OptionBlock{
		@Override
		protected void loadPrompt() {
			this.prompt = "What do you do?";
		}

		@Override
		protected void loadOptions(GameModel model) {
			this.options = new ArrayList<Option>();

			// opt 1 // FIGHT
			options.add(get_opt1_Fight(model));

			// opt 2 // BAG
			options.add(get_opt2_Bag(model));

			// opt 3 // TEAM
			options.add(get_opt3_Team(model));

			// opt 4 // RUN
			options.add(get_opt4_Run(model));
		}

		private Option get_opt1_Fight(GameModel model) {
			// opt 1  // FIGHT
			Option opt = new Option("1", "Fight", new Result());
			// opt 1 : result
			// opt 1 : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = String.format("You chose to fight."); 
				return narration;
			};
			// opt 1 : result.optionBlock
			res.nextOptBlock = _fightBlock;

			return opt;
		}

		private Option get_opt2_Bag(GameModel model) {
			// opt 2 // BAG
			Option opt = new Option("2", "Bag", new Result());
			// opt 2 : result
			{
				// opt 2 : result.modelUpdate
				Result res = opt.getResult();
				res.updater = (String selection) -> {
					// update

					// get narration
					String narration = String.format("You opened the bag.");
					return narration;
				};
				// opt 2 : result.optionBlock
				res.nextOptBlock = _bagBlock;
			}
			return opt;
		}

		private Option get_opt3_Team(GameModel model) {
			// opt 3 // TEAM
			Option opt = new Option("3", "Team", new Result());
			// opt 3 : result
			{
				// opt 3 : result.modelUpdate
				Result res = opt.getResult();
				res.updater = (String selection) -> {
					// update

					// get narration
					String narration = String.format("You check your team.");
					return narration;
				};
				// opt 3 : result.optionBlock
				res.nextOptBlock = _teamBlock;
			}
			return opt;
		}

		private Option get_opt4_Run(GameModel model) {
			// opt 4 // RUN
			Option opt = new Option("4", "Run", new Result());
			// opt 4 : result
			{
				// opt 4 : result.modelUpdate
				Result res = opt.getResult();
				res.updater = (String selection) -> {
					// update

					// get narration
					String narration = String.format("You got away safely.");
					return narration;
				};
				// opt 4 : result.end
				res.finalMessage = true;
			}
			return opt;
		}
	}

	// _fightBlock
	// dynamic option block
	private class _FightBlock extends OptionBlock{
		@Override
		protected void loadPrompt() {
			this.prompt = "What move do you use?";
		}

		@Override
		protected void loadOptions(GameModel model) {
			this.options = new ArrayList<Option>();

			options.addAll(get_AttackOptions(model));
			options.add(get_optBACK());
		}

		/**
		 * Returns a list of Options corresponding to all of the Player's leading
		 * Pokemon's moves. Includes a "BACK" Option.
		 * 
		 * @param model
		 * @return
		 */
		private ArrayList<Option> get_AttackOptions(GameModel model){
			ArrayList<Option> allMoveOptions = new ArrayList<>();

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
				allMoveOptions.add(opt);
			}
			return allMoveOptions;
		}

		/**
		 * Returns an Option without narration.
		 * Redirects to the "Start" OptionBlock.
		 * 
		 * @return
		 */
		private Option get_optBACK() {
			// opt BACK // 
			Option opt = new Option("BACK", "return to the previous menu", new Result());
			// opt BACK : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = startBlock;

			return opt;
		}
	}

	// _bagBlock
	// dynamic option block
	private class _BagBlock extends OptionBlock{
		@Override
		protected void loadPrompt() {
			this.prompt = "Choose an item to inspect.";
		}

		@Override
		protected void loadOptions(GameModel model) {
			this.options = new ArrayList<Option>();

			this.options.addAll(get_ItemOptions(model));
			this.options.add(get_optBACK());
		}

		/**
		 * Returns a list of Options corresponding to all of the Player's bag's items. 
		 * Includes a "BACK" Option.
		 * 
		 * @param model
		 * @return
		 */
		private ArrayList<Option> get_ItemOptions(GameModel model){
			// the ArrayList to return
			ArrayList<Option> allItemOptions = new ArrayList<>();

			Bag bag = model.getPlayer().getBag();

			// the Item's id (for selection purposes)
			int id = 1;
			for(Item item : bag.getItemSet()) {
				// opt 'id' // Move 'id'
				Option opt = new Option(""+id, item.getName(), new Result());
				// opt 'id' : result
				Result res = opt.getResult();
				res.updater = (String selection) -> {
					// remember the selected item
					res.nextOptBlock.setParam(item);

					// update

					// get narration
					String narration = String.format("You selected %s.", 
							item.getName());
					return narration;
				};
				// opt 'id' : result.optionBlock
				res.nextOptBlock = _bag_itemBlock;

				// add option to all options
				allItemOptions.add(opt);

				// increment the id
				id++;
			}
			return allItemOptions;
		}

		/**
		 * Returns an Option without narration.
		 * Redirects to the "Start" OptionBlock.
		 * 
		 * @return
		 */
		private Option get_optBACK() {
			// opt BACK // 
			Option opt = new Option("BACK", "return to the previous menu", new Result());
			// opt BACK : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = startBlock;

			return opt;
		}
	}

	// _bag_itemBlock
	// fixed options, dynamic subject 
	// pre: the invoking result must pass an Item into this block's param
	private class _Bag_ItemBlock extends OptionBlock{
		@Override
		protected void loadPrompt(){
			this.prompt = "What will you do with this item?";
		}

		@Override
		protected void loadOptions(GameModel model){
			options = new ArrayList<Option>();

			options.add(get_opt1_Use(model));
			options.add(get_optBACK());
		}

		private Option get_opt1_Use(GameModel model){
			// opt 1  // USE
			Option opt = new Option("1", "Use", new Result());
			// opt 1 : result
			// opt 1 : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// recall the selected item
				Item selectedItem = ((Item)(_bag_itemBlock.getParam()));

				// update

				// get narration
				String narration = String.format("You used the %s.", 
						selectedItem.getName()); 
				return narration;
			};
			// opt 1 : result.optionBlock
			res.nextOptBlock = startBlock;
			return opt;
		}

		private Option get_optBACK(){
			// opt BACK // 
			Option optBack = new Option("BACK", "return to the previous menu", new Result());
			// opt BACK : result
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
			return optBack;
		}

		/**
		 * This OptionBlock's param should only be an Item.
		 */
		@Override
		public void setParam(Object param) {
			if(param instanceof Item)
				super.setParam(param);
		}
	}

	// _teamBlock
	// dynamic option block
	private class _TeamBlock extends OptionBlock{
		@Override
		protected void loadPrompt() {
			this.prompt = "Choose a pokemon to inspect.";
		}

		@Override
		protected void loadOptions(GameModel model) { 
			this.options = new ArrayList<Option>();

			options.addAll(get_PokemonOptions(model));
			options.add(get_optBACK());
		}

		private ArrayList<Option> get_PokemonOptions(GameModel model){
			// the ArrayList to return
			ArrayList<Option> allPokemonOptions = new ArrayList<>();

			// shortcut vars
			Trainer trainer = model.getPlayer().getTrainer();

			// the Pokemon's id (for selection purposes)
			int id = 1;
			for(Pokemon poke : trainer.getParty()) {
				// opt 'id' // Pokemon 'id'
				Option opt = new Option(""+id, poke.getName(), new Result());
				// opt 'id' : result
				Result res = opt.getResult();
				res.updater = (String selection) -> {
					// remember the selected Pokemon
					res.nextOptBlock.setParam(poke);

					// update

					// get narration
					String narration = String.format("You selected %s.", 
							poke.getName());
					return narration;
				};
				// opt 'id' : result.optionBlock
				res.nextOptBlock = _team_pokemonBlock;

				// add option to all options
				allPokemonOptions.add(opt);

				// increment the id
				id++;
			}
			return allPokemonOptions;
		}

		private Option get_optBACK(){
			// opt BACK // 
			Option optBack = new Option("BACK", "return to the previous menu", new Result());
			// opt BACK : result
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

			return optBack;
		}
	}

	// _team_pokemonBlock
	// fixed options, dynamic subject 
	// pre: the invoking result must pass a Pokemon into this block's param
	private class _Team_PokemonBlock extends OptionBlock{
		@Override
		protected void loadPrompt() {
			this.prompt = "What will you do with this Pokemon?";
		}

		@Override
		protected void loadOptions(GameModel model) { 
			this.options = new ArrayList<Option>();

			options.add(get_opt1_SWAP());
			options.add(get_opt2_HEAL());
			options.add(get_optBACK());
		}

		private Option get_opt1_SWAP() {
			// opt 1  // SWAP
			Option opt = new Option("1", "Swap to Front", new Result());
			// opt 1 : result
			// opt 1 : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// recall the selected pokemon param
				Pokemon selectedPoke = (Pokemon)(this.getParam());
				// update

				// get narration
				String narration = String.format("You swapped %s to the front.", 
						selectedPoke.getNickname()); 

				return narration;
			};
			// opt 1 : result.optionBlock
			res.nextOptBlock = startBlock;

			return opt;
		}

		private Option get_opt2_HEAL() {
			// opt 2 // HEAL
			Option opt = new Option("2", "Heal", new Result());
			// opt 2 : result
			// opt 2 : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// pass this OptBlock's param forward 
				res.nextOptBlock.setParam(this.getParam());

				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt 2 : result.optionBlock
			res.nextOptBlock = _team_pokemon_healBlock;

			return opt;
		}

		private Option get_optBACK() {
			// opt BACK // 
			Option opt = new Option("BACK", "return to the previous menu", new Result());
			// opt BACK : result
			// opt BACK : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = _teamBlock;

			return opt;
		}

		/**
		 * This OptionBlock's param should only be a Pokemon.
		 */
		@Override
		public void setParam(Object param) {
			if(param instanceof Pokemon)
				super.setParam(param);
		}

	}

	// _team_pokemon_healBlock
	// dynamic options, dynamic subject 
	// pre: the invoking result must pass a Pokemon into this block's param
	private class _Team_Pokemon_HealBlock extends OptionBlock{

		@Override
		protected void loadPrompt() {
			this.prompt = "Which heal item will you use?";
		}

		@Override
		protected void loadOptions(GameModel model) {
			this.options = new ArrayList<Option>();
			
			options.addAll(get_HealItemOptions(model));
			options.add(get_optBACK());
		}

		private ArrayList<Option> get_HealItemOptions(GameModel model){
			// the arraylist to return
			ArrayList<Option> allHealItemOpts = new ArrayList<>();

			// short-cut variables
			Bag bag = model.getPlayer().getBag();

			// iterate through all items in the bag, create corresponding Options
			int id = 1;
			for(Item item : bag.getItemSet()) {
				if(!(item instanceof HealItem))
					continue;
				
				// opt 'id' // Item 'id'
				Option opt = new Option(""+id, item.getName(), new Result());
				// opt 'id' : result
				Result res = opt.getResult();
				res.updater = (String selection) -> {
					// recall the selected pokemon
					Pokemon selectedPoke = (Pokemon)(this.getParam());

					// update

					// get narration
					String narration = String.format("You used the %s on %s.", 
							item.getName(), selectedPoke.getNickname());

					return narration;
				};
				// opt 'id' : result.optionBlock
				res.nextOptBlock = startBlock;

				// add option to all options
				allHealItemOpts.add(opt);

				// increment the id
				id++;
			}
			return allHealItemOpts;
		}

		private Option get_optBACK() {
			// opt BACK // 
			Option opt = new Option("BACK", "return to the previous menu", new Result());
			// opt BACK : result
			// opt BACK : result.modelUpdate
			Result res = opt.getResult();
			res.updater = (String selection) -> {
				// update

				// get narration
				String narration = null;
				return narration;
			};
			// opt BACK : result.optionBlock
			res.nextOptBlock = _team_pokemonBlock;

			return opt;
		}


		/**
		 * This OptionBlock's param should only be a Pokemon.
		 */
		@Override
		public void setParam(Object param) {
			if(param instanceof Pokemon)
				super.setParam(param);
		}

	}


}