package Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import Views.CardExchangeView;
import Driver.Main;
import Card.Card;
import Card.Card.CardType;
import Map.Map;
import Map.Map.Territory;

/**
* This class is used to create players,
* and has all the functionality related to
* a player like reinforcement, attacking and
* fortification
* 
* @author Mandeep Ahlawat
* @version 1.0
* @since   2018-10-27 
*/
public class Player extends Observable {
	public enum GamePhase {
		REINFORCEMENT, ATTACK, FORTIFICATION;	
	}
	
	public ArrayList<Card> cards;
	public ArrayList<Territory> assignedTerritories;
	/**
	 * Number of armies assigned to a player during startup phase.
	 */
	private int initialArmyCount;
	public int armiesLeft;
	/**
	 * Total number of armies a player contains.
	 */
	public int totalArmiesCount;
	private String name;
	private int id;
	private static int idCounter = 0;
	public GamePhase currentGamePhase;
	public boolean cardExchangeViewOpen;
	
	/**
	* This method is the constructor of the Player class
	* 
	* @param name A String value of the player name
	*/
	public Player(String name) {
		idCounter++;
		this.name = name;
		this.id = idCounter;
		this.assignedTerritories = new ArrayList<Territory>();
		this.cards = new ArrayList<Card>();
		this.cardExchangeViewOpen = false;
	}
	
	/**
	* This is a getter method, which gives the name of the player
	* 
	* @return name of the player
	* 
	*/
	public String getName() {
		return this.name;
	}
	
	/**
	* This is a setter method, which sets the
	* initial army count and the number of armies left
	* for each player
	* 
	* @param armyCount int value of the number of armies
	* 
	*/
	public void setInitialArmyCount(int armyCount) {
		initialArmyCount = armyCount;
		armiesLeft = armyCount;
		totalArmiesCount = armyCount;
	}
	
	/**
	* This is a helper method which gives the number of
	* countries a player has with the number of armies in that
	* country
	* 
	* @return String value with a specific format.
	* 
	*/
	public String assignedTerritoryNamesWithArmies() {
		String territoriesWithArmies = "";
		for(Territory territory : assignedTerritories) {
			territoriesWithArmies += territory.name + " has armies: " + territory.numberOfArmies + "\n";
		}
		return territoriesWithArmies;
	}
	
	/**
	 * This method defines Initial placement of the armies
	 * and also prints the player and the name of the country 
	 * where they want to place a army 
	 * 
	 */
	public void placeArmies() {
		Scanner keyboard = new Scanner(System.in);
		String userInput = null;
		System.out.println("Player " + name + " has " + armiesLeft
				+ " armies left."
				+ "\nCountries assigned to him are: "
				+ assignedTerritoryNamesWithArmies()
				+ "\nEnter name of the Country to place an army:");
		
		userInput = keyboard.nextLine();
		boolean wrongCountryName = true;
		for (Territory territory : assignedTerritories) {
			if (territory.name.equalsIgnoreCase(userInput)) {
				territory.numberOfArmies++;
				armiesLeft--;
				wrongCountryName = false;
				break;
			}
		}
		if(wrongCountryName) {
			System.out.println("Wrong Country, please select the country which is assigned to you.");
			placeArmies();
		}
	}
	
	/**
	 * This method defines in a helper method to place
	 * number of armies for each player automatically
	 * instead of going in a round robin fashion
	 * 
	 */
	public void placeArmiesAutomatically() {
		Random rand = new Random();
		if(initialArmyCount < assignedTerritories.size()) {
			System.out.println("Can't have initial army less than assigned countries as we need"
					+ "to assign at least 1 army to each country");
			System.exit(1);
		}
		for (Territory territory : assignedTerritories) {
			territory.numberOfArmies++;
			armiesLeft--;
			System.out.println("Player " + name + " placed " + 1 + " army in "
					+ territory.name + ". Total armies in this territory are: "
							+ territory.numberOfArmies);
		}
		while(armiesLeft != 0) {
			Territory territory = assignedTerritories.get(rand.nextInt(assignedTerritories.size()));
			territory.numberOfArmies++;
			armiesLeft--;
			System.out.println("Player " + name + " placed " + 1 + " army in "
					+ territory.name + ". Total armies in this territory are: "
					+ territory.numberOfArmies);
		}
	}
	
	/**
	 *This method Calculate's the number of reinforcements 
	 *that the player gets in each turn of the game.
	 *
	 *@return int the number of reinforcement armies
	 */
	public int calculateReinforcementArmies() {
		int totalReinforcements = 0;
		totalReinforcements =  (int)(assignedTerritories.size() / 3);
		if(totalReinforcements < 3) {
			totalReinforcements = 3;
		}

		for(Map continent : Main.activeMap.continents) {
			int i = 0;
			for(Territory territory : continent.territories) {
				if(territory.owner != this) {
					break;
				}
				i++;
			}
			if(i == continent.territories.size()) {
				totalReinforcements = totalReinforcements + continent.score;
			}
		}
		
		if(canExchangeCards()) {
			this.cardExchangeViewOpen = true;
			CardExchangeView cardExchangeView = new CardExchangeView();
			this.addObserver(cardExchangeView);
			
			if(cards.size() == 5) {
				totalReinforcements += Card.cardExchangeValue;
				setChanged();
				notifyObservers();
//				cardExchangeSelection();
			}
			else {
				Scanner keyboard = new Scanner(System.in);
				System.out.println("Do you want to exchange cards? Enter yes or no?");
				if(keyboard.nextLine().equalsIgnoreCase("yes")) {
					totalReinforcements += Card.cardExchangeValue;
					setChanged();
					notifyObservers();
				}
			}
			
			this.deleteObserver(cardExchangeView);
			this.cardExchangeViewOpen = false;
		}
		
		System.out.println("Player " + name + " gets " + totalReinforcements + " reinforcement armies.");
		totalArmiesCount += totalReinforcements;
		return totalReinforcements;
	}
	
	/**
	 * This method defines Place Reinforcements in territories
	 * 
	 * @param reinforcements number of reinforcement armies the player in the second parameter gets
	 * 
	 */
	public void placeReinforcements(int reinforcements) {
		Scanner keyboard = new Scanner(System.in);
		String userInput = null;
		
		while (reinforcements != 0) {
			System.out.println("\nPlayer " + name + " has " + reinforcements
					+ " armies left."
					+ "\nEnter name of the Country to place a reinforcement army:");
			
				userInput = keyboard.nextLine();
				
				for (Territory territory : Main.activeMap.territories) {
					if (territory.name.equalsIgnoreCase(userInput)) {
						if (territory.owner != this) {
							System.out.println("Wrong country! Try again!");
							break;
						}
						territory.numberOfArmies++;
						reinforcements--;
						break;
					}
				}
			}
		setCurrentGamePhase(GamePhase.ATTACK);
	}
	
	public void setCurrentGamePhase(GamePhase currentGamePhase) {
		this.currentGamePhase = currentGamePhase;
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * To check if the players owns the country and
	 * 
	 * @param country is a string value in which 
	 * the name of the country is mentioned 
	 * 
	 * @return true if the country belongs to the player 
	 * 
	 * @return false if the country doesn't belongs to player
	 */
	private boolean validAssignedCountry(String country) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(country)){
				for(Territory neighbour : territory.neighbours) {
					if(neighbour.owner == this) {
						return true;
					}
				}				
			}
		}
		return false;
	}
	
	/**
	 * To check if the neighbor is owned by the current player
	 * 
	 * @param currentPlayerId is a integer value which gives the present 
	 * ID of the player
	 * 
	 * @param fromCountry is a string value in which a player can mention 
	 * to move a army from a country
	 *  
	 * @param toCountry  is a string value in which a player can mention 
	 * to move a army from a country to another country
	 * 
	 * @return true if the given condition satifies.
	 * 
	 * @return false if the given condition doesn't satifies.
	 */
	private boolean validNeighborCountry(String fromCountry, String toCountry) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(fromCountry)) {
				for(Territory neighbor : territory.neighbours) {
					if(neighbor.name.equalsIgnoreCase(toCountry) && neighbor.owner == this)
						return true;
				}
			}
		}
		return false;
	}
	
	
	/**VALID OPPONENET COUNTRY**/
	private boolean validOpponentCountry(String fromCountry, String toCountry) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(fromCountry)) {
				for(Territory neighbor : territory.neighbours) {
					if(neighbor.name.equalsIgnoreCase(toCountry) && neighbor.owner != this)
						return true;
				}
			}
		}
		return false;
	}
	
	/**FETCH OPPONENT PLAYER ID**/
	private String opponentPlayer(String fromCountry, String toCountry) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(fromCountry)) {
				for(Territory neighbor : territory.neighbours) {
					if(neighbor.name.equalsIgnoreCase(toCountry))
						return neighbor.owner.name;
				}
			}
		}
		return null;
	}
	
	/**TO CHECK IF WE CAN ATTACK FROM THIS COUNTRY HERE**/
	private boolean canAttackFromThisCountry(String country) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(country)){
				if(territory.numberOfArmies > 1)
					return true;			
			}
		}
		return false;
	}
	
	/**TO CHECK IF WE CAN ATTACK FROM THIS COUNTRY HERE**/
	private int calculateNumberOfDiceAllowed(String status, String attackerCounter, String defenderCountry) {
		Scanner keyboard = new Scanner(System.in);
		int input = 0;
		if(status.equalsIgnoreCase("attacker")) {
			int numberOfArmies = 0;
			for(Territory territory : assignedTerritories) {
				if(territory.name.equalsIgnoreCase(attackerCounter)){
					numberOfArmies = territory.numberOfArmies;			
				}
			}
			if(numberOfArmies == 2)
				return 1;
			else if(numberOfArmies == 3){
				System.out.println("Choose if you would like to roll 1 or 2 Dice:");
				while(input == 0) {
					input = keyboard.nextInt();
					if(input != 1 || input != 2) {
						System.out.println("You can only choose between 1 or 2 Dice:");
						input = 0;
					}
				}
			}
			else {
				System.out.println("Choose if you would like to roll 1 or 2 or 3 Dice:");
				while(input == 0) {
					input = keyboard.nextInt();
					if(input != 1 || input != 2 || input != 3) {
						System.out.println("You can only choose between 1 or 2 or 3 Dice:");
						input = 0;
					}
				}
			}
		}
		else {
			int numberOfArmies = 0;
			for(Territory territory : assignedTerritories) {
				if(territory.name.equalsIgnoreCase(attackerCounter)) {
					for(Territory neighbor : territory.neighbours) {
						if(neighbor.name.equalsIgnoreCase(defenderCountry))
							numberOfArmies = neighbor.numberOfArmies;
					}
				}
			}
			if(numberOfArmies == 2)
				return 1;
			else {
				System.out.println("Choose if you would like to roll 1 or 2 Dice:");
				while(input == 0) {
					input = keyboard.nextInt();
					if(input != 1 || input != 2) {
						System.out.println("You can only choose between 1 or 2 Dice:");
						input = 0;
					}
				}
			}
		}
		return input;
	}
	
	/**IMPLEMENTING THE ATTACK PHASE**/
	public void attack() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Do you want to go ahead with the attack? Enter y for yes and n for no:");
		String answer = keyboard.nextLine();
		if(answer.equalsIgnoreCase("y")) {
			System.out.println("Enter the country you want to attack from");
			String attackfrom = keyboard.nextLine();
			if(canAttackFromThisCountry(attackfrom)) {
				System.out.println("Enter the country you want to attack");
				String attackat = keyboard.nextLine();
				keyboard.close();
				if(validOpponentCountry(attackfrom, attackat)) {
					String opponent = opponentPlayer(attackfrom, attackat);
					int attackerDice = calculateNumberOfDiceAllowed("attacker", attackfrom, attackat);
					int defenderDice = calculateNumberOfDiceAllowed("defender", attackfrom, attackat);
				}
			}	
		}
		setCurrentGamePhase(GamePhase.FORTIFICATION);
	}
	
	/**
	 * fortification method to allow a player to move one of 
	 * his armies from one country he owns to another that 
	 * is adjacent to it
	 * 
	 */
	public void fortification() {
		System.out.println("Player " + name);
		String fromCountry = null;
		String toCountry = null;
		boolean doneFlag1 = false;
		boolean doneFlag2 = false;
		Scanner keyboard = new Scanner(System.in);
		
		do {
			System.out.println("Enter the country you would like to move an army from:");
			fromCountry = keyboard.nextLine();
			if(validAssignedCountry(fromCountry)) {	
				do {
					System.out.println("Enter the neighbouring country you would like to move the army to:");
					toCountry = keyboard.nextLine();
					
					int armiesInFromCountry = 0;
					
					for(Territory territory : assignedTerritories) {
						if(territory.name.equals(fromCountry)) {
							armiesInFromCountry = territory.numberOfArmies;
						}
					}
					
					if(armiesInFromCountry == 0) {
						System.out.println("=================== bug ===============");
					}
					
					if(validNeighborCountry(fromCountry, toCountry)) {
						int armiesToMove = 0;
						System.out.println("Enter the number of armies to move."
								+ "\nNote the number of armies to move must be less than"
								+ " number of armies in the from country as every country must have"
								+ " at least 1 army");
						armiesToMove = Integer.parseInt(keyboard.nextLine());
						while(armiesToMove <= 0 || armiesToMove >= armiesInFromCountry) {
							System.out.println("Wrong number of Armies!"
									+ "\nNumber of armies should be less than: " + armiesInFromCountry);
							armiesToMove = Integer.parseInt(keyboard.nextLine());
						}
						for(Territory territory : assignedTerritories) {
							if(territory.name.equals(fromCountry)) {
								territory.numberOfArmies -= armiesToMove;
								for(Territory neighbour : territory.neighbours) {
									if(neighbour.name.equalsIgnoreCase(toCountry)) {
										neighbour.numberOfArmies += armiesToMove;
										break;
									}
								}
								break;
							}
						}
						doneFlag2 = true;
					}
					else {
						System.out.println("Enter a valid Neighbour that you own.");
					}
				}while(!doneFlag2);
				doneFlag1 = true;
			}
			else {
				System.out.println("Wrong Country!!"
						+ "\nEnter a country you own that is having at least one neighbour that you own too!!");
			}
		}while(!doneFlag1);
//		setCurrentGamePhase(GamePhase.REINFORCEMENT);
	}
	
//	public void cardExchangeSelection() {
//		System.out.println("You currently have following cards with you.");
//		int i = 0;
//		for(Card card : cards) {
//			System.out.println(i + ". " + card);
//		}
//		System.out.println("Please select cards to exchange from the following cards"
//				+ "\n The card numbers should be comma seperated");
//		
//		Scanner keyboard = new Scanner(System.in);
//		String cardNumbers = keyboard.nextLine();
//		
//		String cardnums[] = cardNumbers.split(",");
//		
//		while(!validCardIndexesToExchange(Integer.parseInt(cardnums[0]), Integer.parseInt(cardnums[1]),
//				Integer.parseInt(cardnums[3]))) {
//			System.out.println("You can only exchange cards of different types or all cards of same type");
//			cardExchangeSelection();
//		}
//		exchangeCards(Integer.parseInt(cardnums[0]), Integer.parseInt(cardnums[1]),
//				Integer.parseInt(cardnums[3]));
//	}
	
	public void exchangeCards(int cardIndex1, int cardIndex2, int cardIndex3) {
		cards.remove(cardIndex1 - 1);
		cards.remove(cardIndex2 - 1);
		cards.remove(cardIndex3 - 1);
		Card.cardExchangeValue += 5;
	}
	
	public boolean validCardIndexesToExchange(int cardIndex1, int cardIndex2, int cardIndex3) {
		CardType cardType1 = cards.get(cardIndex1 - 1).type;
		CardType cardType2 = cards.get(cardIndex2 - 1).type;
		CardType cardType3 = cards.get(cardIndex3 - 1).type;
		
		if(cardType1 != cardType2) {
			if(cardType2 != cardType3 && cardType1 != cardType3) {
				return true;
			}
		}
		
		if(cardType1 == cardType2 && cardType2 == cardType3) {
			return true;
		}
		
		return false;
	}
	
	public boolean canExchangeCards() {
		if(cards.size() == 5) {
			return true;
		}
		
		ArrayList<CardType> cardTypes = new ArrayList<CardType>();
		for(Card card : cards) {
			if(!cardTypes.contains(card.type)) {
				cardTypes.add(card.type);
			}
		}
		
		if(cardTypes.size() == 3) {
			return true;
		}
		
		return false;
	}
}
