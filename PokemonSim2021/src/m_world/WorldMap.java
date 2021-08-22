package m_world;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Tracks all locations in the world.
 * Associates each location with a list of nearby locations.
 * 
 * @author davidclark
 * Date: 08/20/21
 */
public class WorldMap {

	private HashMap<Location, NearbyLocationList> map;
	
	public WorldMap() {
		loadMap();
	}
	
	private void loadMap() {
		map = new HashMap<Location, NearbyLocationList>();
	}
	
	
	/**
	 * Tracks a list of known, accessible locations.
	 * Also, tracks a list of hidden, unlockable locations.
	 * 
	 * @author davidclark
	 * Date: 08/20/21
	 */
	private class NearbyLocationList {
		ArrayList<Location> knownPaths;
		ArrayList<Location> hiddenPaths;
	}
	
}
