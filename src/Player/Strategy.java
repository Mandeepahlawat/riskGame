package Player;
/**
 * The classes what should implement this interface
 * should define attack, reinforcement
 * and fotify methods
 * 
 * @author mandeepahlawat
 *
 */
public interface Strategy {
	/**
	 * Calculation of reinforcement armies,
	 * which will vary depending on the strategy
	 * @return int value of the reinforcement armies
	 */
	public int calculateReinforcementArmies();
	/**
	 * Placement of reinforcement armies,
	 * which will vary depending on the strategy
	 * 
	 * @param reinforcements - number of reinforcements
	 * to be place
	 */
	public void placeReinforcements(int reinforcements);
	/**
	 * Fortification phase, whose implementation will
	 * vary depending on the strategy
	 */
	public void fortification();
	/**
	 * Attack phase, whose implementation will
	 * vary depending on the strategy
	 */
	public void attack();
}
