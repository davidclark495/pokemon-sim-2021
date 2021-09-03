package m_bag;

/**
 * Represents an Item that can be used in/out of battle.
 * Will typically restore health, cure a status condition, etc.
 * 
 * @author davidclark
 * Date: 09/01/21
 */
public class HealItem extends Item{

	public static final HealItem 
	POTION = new HealItem("Potion", 20),
	SUPER_POTION = new HealItem("Super Potion", 80),
	HYPER_POTION = new HealItem("Hyper Potion", 200);
	
	// the max. amount of hp a pokemon gains when using this item
	private int hpRestored;
//	private boolean canRevive = false;
//	private Effect.Status statusCured = null;
	
	public HealItem(String nm, int hpRestored) {
		super(nm);
		this.hpRestored = hpRestored;
	}
	
	///// Accessors / Mutators /////
	
	public int getHPRestored() {
		return hpRestored;
	}
	
}
