package m_world;

import m_game.Activity;

/**
 * Represents an overworld location.
 * Has a name and associated Activity.
 * 
 * @author davidclark
 * Date: 08/20/21
 */
public abstract class Location {

	private enum LocationType {WILD, WILD_WITH_TRAINER, POKECENTER, POKESTORE, PLOT};
	
	private LocationType locType;
	private String name;
	private Activity localActivity;
	
	
}
