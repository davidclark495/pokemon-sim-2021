package m_player;

/**
 * Represents a player, tracks their Trainer/Bag/Box/other things not shared by NPCs.
 * 
 * @author davidclark
 * Date: 08/18/21
 */
public class Player {

	private Trainer trainer;
	
	public Player(String trainerName) {
		trainer = new Trainer(trainerName);
	}
	
	///// Accessors /////
	/**
	 * 
	 * @return the Player's trainer
	 */
	public Trainer getTrainer() {
		return trainer;
	}
}
