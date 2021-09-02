package m_activities;

import java.util.ArrayList;

import m_game.GameModel;

/**
 * Provides a way of returning options + a prompt/context for making a choice.
 * 
 * OptionBlocks chain together like so:
 * 
 * 		An OptionBlock 	--contains-*--> Option 
 * 		an Option 		--contains-1--> Result
 *		a Result 		--contains-1--> OptionBlock
 * 
 * @author davidclark
 * Date: 08/31/21
 */
public class OptionBlock{
	///// OptionBlock Functionality /////
	public String prompt;
	public ArrayList<Option> options;
	
	
	
	///// OptionBlock extended Functionality /////
	// These methods are useful for any classes extending OptionBlock.
	// They don't do much unless certain methods are overridden.
	/**
	 * Puts the OptionBlock into a usable state.
	 * Must be called before trying to access the prompt or options.
	 * 
	 * @param model
	 */
	public void initializeOptions(GameModel model) {
		loadPrompt();
		loadOptions(model);
	}
	/**
	 * This should be overridden, should set the block's prompt.
	 */
	protected void loadPrompt() { }
	
	/**
	 * This should be overridden, should set the block's options.
	 * The model is used  
	 * ...to generate dynamic options (e.g. Item lists) 
	 * ...to implement Results' update functions 
	 * @param model 
	 */
	protected void loadOptions(GameModel model) { }
	
	
	
	///// Parameters / Addit. Functionality /////
	// This allows Results to pass data into their child-OptionBlocks.
	// Important for context-sensitive OptBlocks, like deciding what to do w/ item x.
	// Should only be set for classes 
	private Object param;
	/**
	 * By default, accepts any object as a parameter.
	 * Should be overridden to add custom error-checking.
	 * @param param
	 */
	public void setParam(Object param) {
		this.param = param;
	}
	/**
	 * Returns the OptionBlock's parameter.
	 * @return
	 */
	public Object getParam() {
		return param;
	}
}