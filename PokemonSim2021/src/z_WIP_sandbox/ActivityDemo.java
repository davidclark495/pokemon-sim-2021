package z_WIP_sandbox;

import java.util.ArrayList;
import java.util.Scanner;

import m_activities.Option;
import m_activities.OptionBlock;
import m_game.*;
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
		BattleActivity battleAct = new BattleActivity(model); 



		// begin game
		battleAct.startActivity();	

		// begin activity loop

		while(!battleAct.isFinalResultSent()) {


			m_activities.OptionBlock currentOptionBlock = battleAct.getOptions();

			// make a selection
			Option selectedOption = null; 
			while(selectedOption == null) {
				// view
				displayOptions(currentOptionBlock);
				String choice = reader.next();
				//String choice = reader.next();
				System.out.println();

				// controller.parseChoice()
				selectedOption = parseChoice(choice, currentOptionBlock.options);

				if(selectedOption == null) {
					System.out.println("<-- Option not found. --> \n"
							+ "<-- Please choose one of the labeled options. -->\n");
				}
			}

			String narration = battleAct.selectOption(selectedOption);

			displayNarration(narration);
		}


	}


	//////////////////////////////

	///// VC Helper Methods /////

	//////////////////////////////


	/**
	 * View
	 * 
	 * Print the prompt
	 * and all of the options' IDs and descriptions in some sensible way
	 * @param options
	 */
	private static void displayOptions(OptionBlock optionBlock) {
		// print divider
		{
			String divider = "---------------";
			System.out.println(divider);
		}
		// print prompt
		{
			String message = optionBlock.prompt;
			System.out.println(message);
		}
		// print options
		for(Option opt : optionBlock.options) {
			String message = String.format("%s - %s", 
					opt.getID(), opt.getDescription());
			System.out.println(message);
		}
		// print option-receiver text
		// e.g. 
		// your choice: 
		System.out.println();
		System.out.print("Your choice: ");
	}

	/**
	 * View
	 * 
	 * Print any narration/String
	 * (should be coming from a result)
	 * 
	 * @param options
	 */
	private static void displayNarration(String narration) {
		// if narration is null, don't display anything
		if(narration == null)
			return;

		// print short divider
		{
			String divider = "- - - - -\n";
			System.out.println(divider);
		}
		// print message
		String message = narration;
		System.out.println(message + "\n");
	}

	/**
	 * View
	 * 
	 * Print a bunch of newline characters
	 */
	private static void clearConsole() {
		for(int i = 0; i < 20; i++)
			System.out.println();
	}

	/**
	 * Take an input string and see if it matches one of the options' ID variables.
	 * If a match is found, return the matching option.
	 * Otherwise return null.
	 * 
	 * Ignores case.
	 * 
	 * @param choice A string that may represent one of the options' IDs
	 * @param options A list of options
	 */
	private static Option parseChoice(String choice, ArrayList<Option> options) {
		// hopefully find the match
		for(Option opt : options) {
			if(choice.equalsIgnoreCase(opt.getID())) {
				return opt;
			}
		}
		// no match was found
		return null;
	}


	///// Misc. /////

	// wip
	// handle activity/model events that require text input
	private class ActivityResponder implements BattleActivity.Responder{

		Scanner reader = new Scanner(System.in);

		@Override
		public void giveInput(String input) {
			// TODO Auto-generated method stub

		}

	}




}
