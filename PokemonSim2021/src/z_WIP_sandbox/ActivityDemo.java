package z_WIP_sandbox;

import java.util.ArrayList;
import java.util.Scanner;

import m_game.*;
import m_game.Activity.Option;
import m_pokemon.Pokemon;

/**
 * Simulates what the MVC Activity loop might look like.
 * The ActivityDemo class is a stand-in for the VC components, 
 * and the BattleActivity class is a component of the model, simplified.
 * 
 * @author davidclark
 * Date: 08/21/21
 */
public class ActivityDemo {

	// VC instance variables
	public static GameModel model = new GameModel();

	public static void main(String[] args) {
		// input set up
		Scanner reader = new Scanner(System.in);

		// model (activity) set up
		ExampleActivity battleAct = new ExampleActivity(model); 

		// begin game
		battleAct.startActivity();	


		ArrayList<Option> options = battleAct.getOptions();

		boolean validOptionSelected = false; 
		do {
			// view
			displayOptions(options);
			String choice = reader.next();
			
			// controller.parseChoice()
			validOptionSelected = parseChoice(choice, options, battleAct);
		} while(!validOptionSelected);

	}

	/**
	 * Print all of the options IDs and descriptions in some sensible way
	 * @param options
	 */
	private static void displayOptions(ArrayList<Option> options) {
		for(Option opt : options) {
			String message = String.format("%s - %s", 
					opt.getID(), opt.getDescription());
			System.out.println(message);
		}
	}

	/**
	 * Take an input string and see if it matches one of the options' ID variables.
	 * If a match is found, notify the Activity of the choice and return true.
	 * Otherwise return false so that a new choice can be made.
	 * 
	 * @param choice A string that may represent one of the options' IDs
	 * @param options A list of options
	 * @param activity The activity to notify if a match/choice is found
	 */
	private static boolean parseChoice(String choice, ArrayList<Option> options, Activity activity) {
		for(Option opt : options) {
			if(choice.equals(opt.getID())) {
				activity.selectOption(opt);
				return true;
			}
		}
		return false;
	}

	private class ActivityResponder implements ExampleActivity.Responder{

		Scanner reader = new Scanner(System.in);

		@Override
		public void giveInput(String input) {
			// TODO Auto-generated method stub

		}

	}



	private static class ExampleActivity extends Activity{

		// tracks the state of the world (player/trainer/pokemon)
		private GameModel model;

		// tracks the state of the activity
		ArrayList<Option> currentOptions;

		// 
		Result currentResult;



		public ExampleActivity(GameModel model) {
			super(model);
			this.model = model;
		}






		// load the initial options
		@Override
		public Result startActivity() {
			currentOptions = new ArrayList<Option>();

			// opt 1
			Option opt1 = new Option("1", "Fight", new Result());
			Result res1 = opt1.getResult();
			res1.modelUpdate = (String selection) -> {
				Pokemon poke = model.getPlayer().getTrainer().getFirstPokemon();
				poke.setNickname("...oooooOOOooooo...spooooky");
			};
			res1.narration = String.format("%s's nickname was changed. ", 
					model.getPlayer().getTrainer().getFirstPokemon().getNickname());
			// opt 2
			Option opt2 = new Option("2", "Bag", new Result());
			// opt 3
			Option opt3 = new Option("3", "Team", new Result());
			// opt 4
			Option opt4 = new Option("4", "Run", new Result());

			currentOptions.add(opt1);
			currentOptions.add(opt2);
			currentOptions.add(opt3);
			currentOptions.add(opt4);

			return null;
		}

		@Override
		public ArrayList<Option> getOptions() {
			return currentOptions;
		}

		@Override
		public void selectOption(Option selection) {
			Result result = selection.getResult();

			// update the model as needed
			result.modelUpdate.run(null);
		}

		@Override
		public Result getResult() {
			return currentResult;
		}



		// event stuff
		private ArrayList<Responder> allResponders;

		public interface Responder {
			void giveInput(String input);
		}

		public void addListener(Responder responder) {
			allResponders.add(responder);
		}

	}
}
