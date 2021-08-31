package m_bag;

import java.util.Set;
import java.util.TreeMap;

/**
 * Stores a TreeMap that associates Items w/ an Integer representing quantity in storage
 * (borrowed from PokemonSim2020, with limited modifications)
 * 
 * @author davidclark
 * Date: 08/22/21
 */
public class Bag{
	private TreeMap<Item, ItemData> items;// Item is correlated w/ Integer, which represents the quantity in storage.

	public Bag() {
		items = new TreeMap<>();
	}
	
	/**
	 * 
	 * @return a standard-issue bag with some basic items
	 */
	public static Bag getBasicBag() {
		Bag tempBag = new Bag();
		tempBag.addItem(Pokeball.POKEBALL, 20);
		tempBag.addItem(Pokeball.ULTRABALL, 2);
		return tempBag;
	}

	/**
	 * Adds the specified Item to the bag.
	 * If the bag already contains the item, it will lookup the current quantity and increment it.
	 * If the bag does not contain the item, it will add the item to the map w/ quantity of 1.
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		addItem(item, 1);
	}

	/**
	 * Adds the specified Item to the bag; repeats as many times as specified.
	 * If the bag already contains the item, it will lookup the current quantity and increment it.
	 * If the bag does not contain the item, it will add the item to the map w/ quantity of 1.
	 * 
	 * @param item
	 */
	public void addItem(Item item, int quantity) {
		ItemData iData = items.get(item);
		if(iData != null) {
			iData.increaseQuantity(quantity);				
		}
		else {
			items.put(item, new ItemData(item, quantity));
		}
	}

	/**
	 * If the bag contains the specified item, it decrements the count for it.
	 * Does not trigger the effect of the item.
	 * If the new count is 0 (or less), the item is removed from the table.
	 * @param item The item to be used.
	 */
	public Item spendItem(Item item) {
		ItemData iData = items.get(item);
		
		if(iData == null) {
			return null;
		}
		
		iData.increaseQuantity(-1);				
		if(iData.quantity <= 0) {
			items.remove(item);
		}
		return iData.getItem();

	}

	public boolean hasItem(Item item) {
		return items.containsKey(item);
	}

	public Set<Item> getItemSet() {
		return items.keySet();
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	
	
	//////////////////////////////	

	///// Inner Class /////
	
	//////////////////////////////
	
	private class ItemData {

		Item item;
		int quantity;
		
		public ItemData(Item item) {
			this(item, 1);
		}
		public ItemData(Item item, int quantity) {
			this.item = item;
			this.quantity = quantity;
		}
		
		// getters and setters //
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public void increaseQuantity(int delta) {
			this.quantity += delta;
		}
		public Item getItem() {
			return item;
		}
		
		
		

	}

}