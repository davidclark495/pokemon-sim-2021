package m_activities;

import java.util.ArrayList;

import m_game.GameModel;

/**
 * Represents an activity (e.g. fighting, shopping, traveling) that the player can 
 * engage in.
 * 
 * Provides methods for getting options, making a selection, and getting results.
 * 
 * Classes that extend this should define a set of OptionBlocks that control the
 * flow of the Activity.
 * 
 * @author davidclark
 * Date: 08/20/21
 */
public abstract class Activity {

	// tracks the state of the world (player/trainer/pokemon)
	protected GameModel model;
	// tracks the state of the activity
	protected OptionBlock currentOptionBlock;
	// true when the Activity has sent a result that ends the activity
	protected boolean finalResultSent = false;

	public Activity(GameModel model) {
		this.model = model;
	}

	/**
	 * Implementations of this method should set "currentOptionBlock" to something
	 * and should optionally return a result.
	 * 
	 * @return 
	 */
	public abstract Result startActivity();

	/**
	 * Returns the current set of options (an OptionBlock) determined by
	 * the current state of the activity.
	 * @return
	 */
	public OptionBlock getOptions() {
		return currentOptionBlock;
	}

	/**
	 * Accept an Option and use the Result to 
	 * update the model and return a String with narration.
	 * 
	 * @param selection 
	 */
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

	/**
	 * 
	 * @return true when the Activity has sent a result 
	 */
	public boolean isFinalResultSent() {
		return finalResultSent;
	}
}
