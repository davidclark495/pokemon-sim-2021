package m_bag;

/**
 * Represents a Pokeball of any kind.
 * (Borrowed from PokemonSim2020, with modifications.)
 * 
 * @author davidclark
 * Date: 08/22/21
 */
public class Pokeball extends Item{
	
	public static final Pokeball 
	POKEBALL = new Pokeball("Pokeball", 1),
	GREATBALL = new Pokeball("Greatball", 1.5),
	ULTRABALL = new Pokeball("Ultraball", 2);

	private double ballBonus;

	public Pokeball(String nm, double bonus) {
		super(nm);
		this.ballBonus = bonus;
	}
	
	public double getBallBonus() {
		return ballBonus;
	}
	
}
