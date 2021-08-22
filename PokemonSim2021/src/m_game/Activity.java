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
	public abstract ArrayList<Option> getOptions();
	
	public abstract void selectOption(Option selection);
	
	public abstract Result getResult();
	
	
	
	
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
	 * Contains text and other display information.
	 * May contain another activity, which should be completed before resuming.
	 * 
	 * @author davidclark
	 */
	public class Result {

		//
		public Runnable modelUpdate; 
		/**
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
		public String narration;
		// may be null
		public Activity nextActivity;
		
		// True if this result is the end of the activity
		public boolean finalMessage = false;
	}
	
	public interface Runnable{
		void run(String input);
	}

	public class OptionBlock{
		ArrayList<Option> options;
	}
	
}
