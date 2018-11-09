package Card;

import java.util.Random;

import Map.Map.Territory;
import Player.Player;

/**
 * 
 * This class is the Card class and it has CardType 
 * in which three different types of army cards are  
 * are mentioned.
 * 
 * @author Mandeep Ahlawat
 * @version 1.0
 * @since 2018/10/27
 *
 */

public class Card {
	public enum CardType {
		/**
		 * It is a branch of a army that engages 
		 * in military combat on foot.
		 */
		INFANTRY,
		/**
		 * It is a branch of army that engages in
		 * fight mounted on horseback.
		 */
		CAVALRY,
		/**
		 * It is branch of army that engages in fight 
		 * with havy military weapons. 
		 */
		ARTILLERY;
		
		/**
		 * Static method to create a object 
		 * The name of the method is CardType 
		 * to get a random card
		 * 
		 * @return a values which is defined in the method.
		 */
		public static CardType getRandomCard() {
	        Random random = new Random();
	        return values()[random.nextInt(values().length)];
	    }
	}
	/**
	 * Type of the card
	 */
	public CardType type;
	/**
	 * cardExchangeValue is intialized to 5
	 */
	public static int cardExchangeValue = 5;
	/**
	 * territory in the game
	 */
	public Territory territory;
	/**
	 * particular owner in the game for 
	 * the territory
	 */
	public Player owner;
	
	/**
	* This is the default constructor of the Card class and will be used to
	* create the cardtype and territory 
	* 
	* @param territory the new territory 
	*/
	public Card(Territory territory) {
		this.type = CardType.getRandomCard();
		this.territory = territory;
		this.owner = territory.owner;
	}
	
	/**
	 * @return the string name type  
	 */
	@Override
	public String toString() {
		return this.type.name();
	}
}
