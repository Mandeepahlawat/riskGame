package Card;

import java.util.Random;

import Map.Map.Territory;
import Player.Player;

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
	
	public Card(Territory territory) {
		this.type = CardType.getRandomCard();
		this.territory = territory;
		this.owner = territory.owner;
	}
	
	@Override
	public String toString() {
		return this.type.name();
	}
}
