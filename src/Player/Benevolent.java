package Player;

public class Benevolent implements Strategy {
	/**
	 * The player to which this strategy belongs to
	 */
	public Player player;
	
	/**
	 * constructor for this class and sets the
	 * player data member
	 * @param player
	 */
	public Benevolent(Player player) {
		this.player = player;
	}
	
	@Override
	public int calculateReinforcementArmies() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void placeReinforcements(int reinforcements) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fortification() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

}
