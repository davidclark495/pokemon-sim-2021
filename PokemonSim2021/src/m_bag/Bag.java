package m_bag;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Stores a TreeMap that associates Items w/ an Integer representing quantity in storage
 * (borrowed from PokemonSim2020, with limited modifications)
 * 
 * How to Use an Item:
 * 		an invoking class will get this Bag's list of items, 
 * 			then will save a chosen item and pass it back in as a parameter to, 
 * 			say, spendItem()
 * 
 * @author davidclark
 * Date: 08/22/21
 */
public class Bag{
	// all Items with an entry in the map will have a non-zero quantity
	// an Item is correlated w/ Integer, which represents the quantity in storage
	private TreeMap<Item, ItemData> items;
	
	public Bag() {
		items = new TreeMap<>();
	}
	
	/**
	 * 
	 * @return a standard-issue bag with some basic items
	 */
	public static Bag getBasicBag() {
		Bag tempBag = new Bag();
		tempBag.addItem(Pokeball.POKEBALL,    20);
		tempBag.addItem(Pokeball.ULTRABALL,    2);
		tempBag.addItem(HealItem.POTION,      10);
		tempBag.addItem(HealItem.SUPER_POTION, 2);
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
	 * Throws an IllegalArgumentException if "quantity" is negative.
	 * 
	 * @param item
	 */
	public void addItem(Item item, int quantity) {
		// throw if negative
		if(quantity < 0)
			throw new IllegalArgumentException("Cannot add a negative quantity.");
		
		// does the bag already contain "item"?
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
	 * and returns true. If the new count is 0 (or less), the item is removed 
	 * from the table.
	 * 
	 * If the bag does not contain the item, returns false.
	 *
	 * Does not trigger the effect of the item.
	 * 
	 * @param item The item to be used.
	 */
	public boolean spendItem(Item item) {
		ItemData iData = items.get(item);
		
		// does this Bag contain the item? if not, return false
		if(iData == null) 
			return false;
		
		iData.increaseQuantity(-1);				
		if(iData.quantity <= 0) {
			items.remove(item);
		}
		return true;
	}

	/**
	 * True if the Item exists in the bag.
	 * @param item
	 * @return
	 */
	public boolean hasItem(Item item) {
		return items.containsKey(item);
	}
	
	/**
	 * Returns the number of the specified item held in the bag.
	 * e.g., the bag has 5 ULTRABALLS, getQuantityOfItem(ULTRABALL) returns 5.
	 * @param item
	 * @return
	 */
	public int getItemQuantity(Item item) {
		if(!this.hasItem(item))
			return 0;
		else
			return (items.get(item)).quantity;
	}

	/**
	 * Return a copy of the bag's item-set.
	 * 
	 * @return
	 */
	public Set<Item> getItemSet() {
		TreeSet<Item> copyOfSet = new TreeSet<>(items.keySet());
		return copyOfSet;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	
	
	//////////////////////////////	

	///// Inner Class /////
	
	//////////////////////////////
	/**
	 * Contains an Item and an representing the quantity stored.
	 * Quantity will never be negative.
	 * 
	 * @author davidclark
	 */
	private class ItemData {

		private Item item;
		private int quantity;
		
		/**
		 * Creates an ItemData with the given Item and quantity.
		 * 
		 * If the quantity argument is less than 0, 
		 * the quantity will be set to 0.
		 * @param item
		 * @param quantity
		 */
		public ItemData(Item item, int quantity) {
			this.item = item;
			this.setQuantity(quantity);
		}
		
		
		
		///// getters and setters /////
		/**
		 * Gets the quantity of this ItemData.
		 * @return
		 */
		public int getQuantity() {
			return this.quantity;
		}
		
		/**
		 * Sets the quantity of this ItemData.
		 * 
		 * If the argument is less than 0, 
		 * this ItemData's quantity will stop at 0.
		 * @param quantity
		 */
		public void setQuantity(int newQuantity) {
			if(newQuantity >= 0)
				this.quantity = newQuantity;
			else
				this.quantity = 0;
		}
		
		/**
		 * Increases this ItemData's quantity by delta.
		 * May be used to decrease quantity when delta is a negative number.
		 * If quantity would be negative, quantity will stop at 0.
		 * 
		 * @param delta
		 */
		public void increaseQuantity(int delta) {
			this.setQuantity( getQuantity() + delta );
		}
		
		public Item getItem() {
			return item;
		}
		
	}

}