package m_bag;

/**
 * The parent class for any Item that can be stored in a Bag.
 * (Borrowed from PokemonSim2020, with modifications.)
 * 
 * @author davidclark
 * Date: 08/22/21
 */
public abstract class Item implements Comparable<Item>{

	private String name;
	
	public Item(String nm) {
		this.name = nm;
	}
	
	///// Accessors /////
	public String getName() {
		return name;
	}
	
	/**
	 * compares items based on their names
	 */
	@Override
	public int compareTo(Item other) {
		return this.name.compareTo(other.name);
	}
}
