package m_activities;


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
 * Date: 08/31/21
 */
public class Result {

	///// Manage Program Flow /////
	// these properties are used to control what the game will do next
	
	// defaults to null
	public OptionBlock nextOptBlock = null;
	
	// defaults to null
	public Activity nextActivity = null;
	
	// True if this result is the end of the activity
	public boolean finalMessage = false;

	
	///// Update Game /////
	// these properties are used to change the state of the game 
	// and to communicate what changed
	
	// 
	public Updater updater; 
	
	
	
	///////////////////////
	
	///// Inner Class /////
	
	///////////////////////
	
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
}

