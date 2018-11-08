package Views;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import Card.Card;
import Player.Player;

public class CardExchangeView implements Observer{
	
	public static void cardExchangeSelection(Player currentPlayer) {
		System.out.println("You currently have following cards with you.");
		int i = 1;
		for(Card card : currentPlayer.cards) {
			System.out.println(i + ". " + card);
			++i;
		}
		System.out.println("Please select cards to exchange from the following cards"
				+ "\n The card numbers should be comma seperated");
		
		Scanner keyboard = new Scanner(System.in);
		String cardNumbers = keyboard.nextLine();
		
		String cardnums[] = cardNumbers.split(",");
		
		while(!currentPlayer.validCardIndexesToExchange(Integer.parseInt(cardnums[0]),
				Integer.parseInt(cardnums[1]), Integer.parseInt(cardnums[3]))) {
			System.out.println("You can only exchange cards of different types or all cards of same type");
			cardExchangeSelection(currentPlayer);
		}
		currentPlayer.exchangeCards(Integer.parseInt(cardnums[0]), Integer.parseInt(cardnums[1]),
				Integer.parseInt(cardnums[3]));
	}

	@Override
	public void update(Observable o, Object arg) {
		Player currentPlayer = (Player) o;
		System.out.println("\n*********************** CARD EXCHANGE VIEW START *****************************\n");
		cardExchangeSelection(currentPlayer);
		System.out.println("\n*********************** CARD EXCHANGE VIEW ENDS *****************************\n");
	}

}
