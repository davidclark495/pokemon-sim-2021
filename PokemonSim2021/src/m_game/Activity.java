package m_game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an activity (e.g. fighting, shopping, traveling) that the player can 
 * engage in.
 * Provides methods for getting options, making a selection, and getting results.
 * 
 * @author davidclark
 * Date: 08/20/21
 */
public abstract class Activity {
	
	public Activity(GameModel model) {
		
	}
	
	public abstract Result startActivity();
	
	//public abstract HashMap<Option, String> getOptions();
	public abstract OptionBlock getOptions();
	
	public abstract String selectOption(Option selection);
	
	
	
	
	
	///// Inner Class /////
	
	/**
	 * Represents an option that the player can select.
	 * Has a description, an ID, and a result.
	 * 
	 * @author davidclark
	 */
	public class Option {
		private String id;
		private String description;
		private Result result;

		public Option(String id, String description, Result result) {
			this.id = id;
			this.description = description;
			this.result = result;
		}
		
		
		///// Accessors /////
		public String getID() {
			return id;
		}
		public String getDescription() {
			return description;
		}
		public Result getResult() {
			return result;
		}
		
		///// Mutators /////
		public void setResult(Result result) {
			this.result = result;
		}
	}
	
	/**
	 * Contains a method for updating the model/getting narration.
	 * May contain display information.
	 * 
	 * Contains sequencing / game-flow information.
	 * May contain another activity, which should be completed before resuming.
	 * May contain another OptionBlock.
	 * May trigger the end of the Activity.
	 * 
	 * @author davidclark
	 */
	public class Result {

		// defaults to null
		public OptionBlock nextOptBlock = null;		
		// may be null
		public Activity nextActivity = null;
		// True if this result is the end of the activity
		public boolean finalMessage = false;

		//
		public Updater updater; 
	}
	
	/**
	 * used to update the model in a Result
	 * 
	 * @author davidclark
	 *
	 */
	public interface Updater{
		/**
		 * Narration markup style:
		 *  e.g. 
		 *  "Charmander used flamethrower.\n
		 *  <delay : short> 
		 *  Pikachu took 10 damage.
		 *  <delay : short>
		 *  Pikachu fainted. Charmander wins!
		 *  <delay : long>
		 *  Charmander gained 30 exp.
		 *  <wait-for-input:any>"
		 */
		// update the model and return a bit of narration
		// may return null
		String updateModel(String input);
	}

	/**
	 * provides a way of returning options + a prompt/context for making a choice
	 * 
	 * @author davidclark
	 *
	 */
	public class OptionBlock{
		public String prompt;
		public ArrayList<Option> options;
	}
	
}
