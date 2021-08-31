package m_player;

import m_bag.Bag;

/**
 * Represents a player, tracks their Trainer/Bag/Box/other things not shared by NPCs.
 * 
 * @author davidclark
 * Date: 08/18/21
 */
public class Player {

	private Trainer trainer;
	private Bag bag;
	
	public Player(String trainerName) {
		trainer = new Trainer(trainerName);
		bag = new Bag();
	}
	
	///// Accessors /////
	/**
	 * 
	 * @return the Player's trainer
	 */
	public Trainer getTrainer() {
		return trainer;
	}

	/**
	 * 
	 * @return the Player's bag
	 */
	public Bag getBag() {
		return bag;
	}
}
