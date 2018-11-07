package Card;

import java.util.Random;

import Map.Map.Territory;
import Player.Player;

/**
 * 
 * This class is the Card class and it has CardType 
 * in which three different types of army cards are  
 * are mentioned.
 * @author Mandeep Ahlawat
 * @version 1.0
 * @since 2018/10/27
 *
 */

public class Card {
	public enum CardType {
		INFANTRY, CAVALRY, ARTILLERY;
		
		public static CardType getRandomCard() {
	        Random random = new Random();
	        return values()[random.nextInt(values().length)];
	    }
	}
	
	public CardType type;
	public static int cardExchangeValue = 5;
	public Territory territory;
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
