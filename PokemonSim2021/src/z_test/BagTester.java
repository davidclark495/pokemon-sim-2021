package z_test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import m_bag.Bag;
import m_bag.HealItem;
import m_bag.Item;
import m_bag.Pokeball;

/**
 * Tests the Bag class.
 * 
 * HealItems are used as generic Items for testing purposes.
 * 
 * @author davidclark
 * Date: 09/02/21
 */
public class BagTester {

	/**
	 * Show that a newly constructed bag is empty.
	 */
	@Test
	public void isEmpty() {
		Bag bag = new Bag();

		assertTrue(bag.isEmpty());
	}

	/**
	 * Show that bag.hasItem(item) is false... 
	 * for an empty bag
	 * with an arbitrary item.
	 */
	@Test
	public void hasItem_emptyBag() {
		Bag bag = new Bag();

		Item none_in_bag_item = new HealItem("Test Item", 0);

		assertFalse(bag.hasItem(none_in_bag_item));
	}

	/**
	 * Show that bag.hasItem(item) is true...
	 * with an item that has been added to the bag.
	 */
	public void hasItem_nonEmptyBag() {
		Bag bag = new Bag();

		Item item = new HealItem("Test Item", 0);
		bag.addItem(item);

		assertTrue(bag.hasItem(item));
	}

	/**
	 * Show that the quantity of an arbitrary item is 0 
	 * in an empty bag.
	 */
	@Test
	public void getItemQuantity_emptyBag() {
		Bag bag = new Bag();

		Item none_in_bag_item = new HealItem("Test Item", 0);

		assertEquals(0, bag.getItemQuantity(none_in_bag_item));
	}

	/**
	 * Show that the quantity of an item is 1
	 * after adding it to the bag.
	 */
	@Test
	public void getItemQuantity_one() {
		Bag bag = new Bag();

		Item item = new HealItem("Test Item", 0);
		bag.addItem(item);

		assertEquals(1, bag.getItemQuantity(item));
	}

	/**
	 * Show that if a bag.addItem(item) is called, bag.hasItem(item) must be true
	 * 
	 * HealItems are used as a generic item for testing purposes.
	 */
	@Test
	public void addItem() {
		Bag bag = new Bag();

		Item item = new HealItem("Test Item", 0);
		bag.addItem(item);

		assertTrue(bag.hasItem(item));
		assertEquals(1, bag.getItemQuantity(item));
	}

	/**
	 * Show that if a bag.addItem(item, number) is called with some positive value, 
	 * the bag will contain "number" as the quantity of the item.
	 */
	@Test
	public void addItem_overload_quantity() {
		Bag bag = new Bag();

		Item item = new HealItem("Test Item", 0);
		bag.addItem(item, 10);

		assertTrue(bag.hasItem(item));
		assertEquals(10, bag.getItemQuantity(item));
	}

	/**
	 * Show that if bag.addItem() is called several times for the same item,
	 * the quantity at the end will be the sum of all the .addItem quantities.
	 */
	@Test
	public void addItem_addSameRepeatedly() {
		Bag bag = new Bag();

		Item item = new HealItem("Test Item", 0);

		bag.addItem(item, 30);
		bag.addItem(item, 20);
		bag.addItem(item, 50);

		assertEquals(100, bag.getItemQuantity(item));
	}

	/**
	 * Show that a bag can track several Items (and their quantities).
	 */
	@Test
	public void addItem_multipleItems() {
		Bag bag = new Bag();

		Item item1 = new HealItem("Test Item #1", 0);
		Item item2 = new HealItem("Test Item #2", 0);
		Item item3 = new Pokeball("Test Item #3", 0);
		Item item4 = new Pokeball("Test Item #4", 0);

		bag.addItem(item1, 10);
		bag.addItem(item2,  2);
		bag.addItem(item3,  7);
		bag.addItem(item4, 26);

		assertEquals(10, bag.getItemQuantity(item1));
		assertEquals(2,  bag.getItemQuantity(item2));
		assertEquals(7,  bag.getItemQuantity(item3));
		assertEquals(26, bag.getItemQuantity(item4));
	}
	
	/**
	 * Show that a bag will throw an IllegalArgumentException if
	 * addItem() is passed a negative quantity.
	 */
	@Test
	public void addItem_negativeQuantity() {
		Bag bag = new Bag();
		Item item = new HealItem("Test Item", 0);

		assertThrows(IllegalArgumentException.class, () -> {
			bag.addItem(item, -1);
		});
	}

//	/**
//	 * Show that bag.spendItem(item) returns false when no such item is in the bag.
//	 */
//	@Test
//	public void spendItem_fail() {
//		Bag bag = new Bag();
//
//		Item item = new HealItem("Not-in-bag Item", 0);
//		assertFalse(bag.spendItem(item));
//	}

	/**
	 * Show that bag.spendItem(item) consumes the item, 
	 * i.e. it decrements the stored quantity.
	 */
	@Test
	public void spendItem_spendOne() {
		Bag bag = new Bag();
		Item item = new HealItem("Test Item", 0);

		bag.addItem(item, 10);

		bag.spendItem(item);

		// the bag should still have 9 of "Test Item"
		assertTrue(bag.hasItem(item));
		assertEquals(9, bag.getItemQuantity(item));
	}

	/**
	 * Show that if the bag spends the last of an item,
	 * that item will be removed from the bag.
	 */
	@Test
	public void spendItem_lastItem_1() {
		Bag bag = new Bag();
		Item item = new HealItem("Test Item", 0);

		// test: the bag gains (then loses) one "Test Item"
		bag.addItem(item);

		bag.spendItem(item);

		assertFalse(bag.hasItem(item));
		assertEquals(0, bag.getItemQuantity(item));

		// test: the bag gains (then loses) many "Test Items"
		bag.addItem(item, 100);

		while(bag.hasItem(item))
			bag.spendItem(item);

		assertEquals(0, bag.getItemQuantity(item));
	}

	
	/**
	 * Show that the bag can return a set of all of its items.
	 */
	@Test
	public void getItemSet() {
		Bag bag = new Bag();

		Item item1 = new HealItem("Test Item #1", 0);
		Item item2 = new HealItem("Test Item #2", 0);
		Item item3 = new Pokeball("Test Item #3", 0);
		Item item4 = new Pokeball("Test Item #4", 0);

		bag.addItem(item1, 10);
		bag.addItem(item2,  2);
		bag.addItem(item3,  7);
		bag.addItem(item4, 26);
		
		// get all the items, then remove them from the bag
		// if the bag is empty, it worked
		Set<Item> allItems = bag.getItemSet();
		
		for(Item item : allItems) {
			while(bag.hasItem(item))
				bag.spendItem(item);
		}
		
		assertTrue(bag.isEmpty());
	}



}
