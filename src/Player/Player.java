package Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

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
	/**
	 * The game Phase that can be used by a player in the game
	 * @author shreyas
	 *
	 */
	public enum GamePhase {
		/**
		 * This is a REINFORCEMENT phase in the game.
		 */
		REINFORCEMENT,
		/**
		 * This is a ATTACK phase in the game.
		 */
		ATTACK,
		/**
		 * This is a FORTIFICATION phase in the game.
		 */
		FORTIFICATION;	
	}
	/**
	 * public ArrayList<Card> cards list of cards
	 */
	public ArrayList<Card> cards;
	/**
	 * public ArrayList<Territory> assignedTerritories list of territories
	 */
	public ArrayList<Territory> assignedTerritories;
	/**
	 * Number of armies assigned to a player during startup phase.
	 */
	private int initialArmyCount;
	/**
	 * Number of armies left for a player in integer value.
	 */
	public int armiesLeft;
	/**
	 * Total number of armies a player contains.
	 */
	public int totalArmiesCount;
	/**
	 * private Name of the player in string type
	 */
	private String name;
	/**
	 * private Players's id in a integer type
	 */
	private int id;
	/**
	 * id counter which is intialized to zero
	 */
	private static int idCounter = 0;
	/**
	 * public GamePhase currentGamePhase will take one 
	 * of the phases mentioned in GamePhase
	 */
	public GamePhase currentGamePhase;
	/**
	 * public boolean cardExchangeViewOpen will take true
	 * or false value for the variable if the player wants
	 * to open card exchange view.
	 */
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
	 * This method defines Place Reinforcements in territories which 
	 * also print's out player name,armies left in it.
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
	
	/**
	 * The method setCurrentGamePhase will set current 
	 * game phase according to the given phase value
	 * in main function 
	 * 
	 * @param currentGamePhase will have one phase out 
	 * of three phases mentioned in GamePhase as a input
	 * from the main function
	 */
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
	public boolean validAssignedCountry(String country) {
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
	 * @return true if the given condition satisfies.
	 * 
	 * @return false if the given condition doesn't satisfies.
	 */
	public boolean validNeighborCountry(String fromCountry, String toCountry) {
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
	
	/**
	 * The private method will have valid opponent's country 
	 * which will be used in attack phase
	 * 
	 * @param fromCountry will have player present
	 * country as a string value
	 * 
	 * @param toCountry is a string value in which 
	 * player mention's the name of the country to move
	 * 
	 * @return true if the given conditions pass 
	 * otherwise it will return false
	 */
	public boolean validOpponentCountry(String fromCountry, String toCountry) {
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
	
	/**
	 * The private method opponentPlayer will return name of 
	 * the player who is present in neighbor country.  
	 * 
	 * @param fromCountry will have player present
	 * country as a string value.
	 * 
	 * @param toCountry is a string value in which 
	 * player mention's the name of the country to 
	 * check neighbour.
	 * 
	 * @return name of the player in neighbour country 
	 * only if it passes the conditions if not it will 
	 * return null value.
	 */
	public String opponentPlayer(String fromCountry, String toCountry) {
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
	
	/**
	 * The private method to check if a player can attack
	 * from this country which is present here.
	 * 
	 * @param country will have player's current country 
	 * value in string type
	 * 
	 * @return true if the given conditions passes 
	 * otherwise it will return false
	 */
	public boolean canAttackFromThisCountry(String country) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(country)){
				if(territory.numberOfArmies > 1)
					return true;			
			}
		}
		return false;
	}
	

	/**
	 * The private method will calculate the number 
	 * of dice for attacker or defender depending on 
	 * the number of armies that player has,will 
	 * decide whether to roll 1,2 or 3 dice.
	 * 
	 * @param status string value will have attacker 
	 * or defender in it.
	 * 
	 * @param attackerCounter
	 * 
	 * @param defenderCountry
	 * 
	 * @return
	 */
	public int calculateNumberOfDiceAllowed(String status, String attackerCounter, String defenderCountry) {
		Scanner keyboard = new Scanner(System.in);
		int input = 0;
		if(status.equalsIgnoreCase("attacker")) {
			int numberOfArmies = 0;
			System.out.println("Do you want to go ALL-OUT? Enter y for yes and n for no:");
			String isAllOut = keyboard.nextLine();
			if(isAllOut.equalsIgnoreCase("n")) {
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
				return 3;
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
	
	/**VALUE ON DICE AFTER ROLLING**/
	public Vector<Integer> rollDice(int numberOfDice){
		Vector<Integer> diceValues = new Vector(numberOfDice);
		Random r = new Random();
		while(numberOfDice != 0) {
			diceValues.addElement(r.nextInt((6 - 1) + 1) + 1);
			numberOfDice--;
		}
		Collections.sort(diceValues);
		return diceValues;
	}
	
	/**REDUCING ONE ARMY IN THE LOSING PLAYERS TERRITORY**/
	public void reduceArmy(String losingPlayer, String attackerCounter, String defenderCountry) {
		if(losingPlayer.equalsIgnoreCase("attacker")) {
			for(Territory territory : assignedTerritories) {
				if(territory.name.equalsIgnoreCase(attackerCounter)) {
					territory.numberOfArmies--;
				}
			}
		}
		else {
			for(Territory territory : assignedTerritories) {
				if(territory.name.equalsIgnoreCase(attackerCounter)) {
					for(Territory neighbor : territory.neighbours) {
						if(neighbor.name.equalsIgnoreCase(defenderCountry))
							neighbor.numberOfArmies--;
					}
				}
			}
		}		
	}
	
	/**CHECK IF THE NUMBER OF ARMIES OF THE DEFENDER IS ZERO**/
	public boolean checkDefenderArmiesNumberZero(String attackerCounter, String defenderCountry) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(attackerCounter)) {
				for(Territory neighbor : territory.neighbours) {
					if(neighbor.name.equalsIgnoreCase(defenderCountry))
						if(neighbor.numberOfArmies == 0)
							return true;
				}
			}
		}
		return false;
	}
	
	/**MOVE ARMIES TO NEW TERRITORY CONQUERED IF DEFENDER LOOSES**/
	public void moveArmiesToNewTerritory(String attackerCounter, String defenderCountry, int numberOfArmiesToMove) {
		for(Territory territory : assignedTerritories) {
			if(territory.name.equalsIgnoreCase(attackerCounter)) {
				//leave atleast one army behind
				while(numberOfArmiesToMove >= territory.numberOfArmies) {
					Scanner input = new Scanner(System.in);
					System.out.println("Enter a valid number of troops:");
					numberOfArmiesToMove = input.nextInt();
				}
				territory.numberOfArmies = territory.numberOfArmies - numberOfArmiesToMove;
				for(Territory neighbor : territory.neighbours) {
					if(neighbor.name.equalsIgnoreCase(defenderCountry)) {
						neighbor.numberOfArmies = numberOfArmiesToMove;
						assignedTerritories.add(neighbor);
					}
				}
			}
		}
	}
	
	/**IMPLEMENTING THE ATTACK PHASE**/
	public void attack() {
		boolean attackDone = false;		//if all territories are conquered or attack lost	
		boolean gameCompleted = false;		//if all territories are conquered
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Do you want to go ahead with the attack? Enter y for yes and n for no:");
		String answer = keyboard.nextLine();
		if(answer.equalsIgnoreCase("y")) {
			//boolean doneFlag1 = false;
			do {
				System.out.println("Enter the country you want to attack from");
				String attackfrom = keyboard.nextLine();
				if(canAttackFromThisCountry(attackfrom)) {
					System.out.println("Enter the country you want to attack");
					String attackat = keyboard.nextLine();
					keyboard.close();
					if(validOpponentCountry(attackfrom, attackat)) {
						boolean finishedAttackingThatTerritory = false;		//finished attacking the present territory
						 do {
							String opponent = opponentPlayer(attackfrom, attackat);
							Vector<Integer> attackerDice = rollDice(calculateNumberOfDiceAllowed("attacker", attackfrom, attackat));
							Vector<Integer> defenderDice = rollDice(calculateNumberOfDiceAllowed("defender", attackfrom, attackat));
							while(!attackerDice.isEmpty() && !defenderDice.isEmpty()) {
								int attackerDiceValue = attackerDice.remove(attackerDice.size() - 1);
								int defenderDiceValue = defenderDice.remove(defenderDice.size() - 1);
								if(attackerDiceValue > defenderDiceValue) {
									reduceArmy("defender", attackfrom, attackat);
									//check if the opponent lost
									if(checkDefenderArmiesNumberZero(attackfrom, attackat)) {
										System.out.println("Enter the number of armies you would like to place in your new territory:");
										moveArmiesToNewTerritory(attackfrom, attackat, keyboard.nextInt());
										//remove this territory from the players list
										for(Player player : Main.players) {
											//VERIFY IF THE TERRITORY IS REMOVED ONLY FROM THE LOST DEFENDERS TERRITORIES LIST
											if(player.name.equalsIgnoreCase(opponent)) {
												for(Territory territory : Main.activeMap.territories) {
													player.assignedTerritories.remove(territory);
												}
											}
										}
										finishedAttackingThatTerritory = true;
										//if all territories are owned by a single user
										if(Main.activeMap.allTerritoriesOwnBySinglePlayer()) {
											gameCompleted = true;
											attackDone = true;
										}
										break;
									}
								}
								else {
									reduceArmy("attacker", attackfrom, attackat);
									//check if you lost
									for(Territory territory : assignedTerritories) {
										if(territory.name.equalsIgnoreCase(attackfrom)) {
											if(territory.numberOfArmies == 0) {
												territory.numberOfArmies = 1;
												attackDone = true;
												finishedAttackingThatTerritory = true;
												break;
											}
										}
									}
									if(attackDone)	//to get out of while loop
										break;
								}
							}
						} while(!finishedAttackingThatTerritory);
					}
					else {
						System.out.println("Enter a valid country you would like to attack");
					}
				}
				else {
					System.out.println("Enter a valid country you would like to attack from");
				}
				if(!gameCompleted) {
					System.out.println("Do you want to continue with the attack? Enter y if yes or n if no");
					String input = keyboard.nextLine();
					if(input.equalsIgnoreCase("n"))
						attackDone = true;
				}
			}while(!attackDone);
		}
		if(!gameCompleted) {
			setCurrentGamePhase(GamePhase.FORTIFICATION);
		}
	}
	
	/**
	 * fortification method to allow a player to move one of his armies from one
	 * country he owns to another that is adjacent to it
	 * 
	 */
	public void fortification() {
		System.out.println("Player " + name);
		String fromCountry = null;
		String toCountry = null;
		boolean doneFlag1 = false;
		boolean doneFlag2 = false;
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Do you want to skip fortification? Enter yes or no.");
		String userInput = keyboard.nextLine();
		if (!userInput.equalsIgnoreCase("yes")) {
			do {
				System.out.println(
						"Enter the country you would like to move an army from: (enter 'exit' to skip fortification)");
				fromCountry = keyboard.nextLine();
				if (!fromCountry.equalsIgnoreCase("exit")) {
					if (validAssignedCountry(fromCountry)) {
						do {
							System.out.println(
									"Enter the neighbouring country you would like to move the army to: (enter 'exit' to skip fortification)");
							toCountry = keyboard.nextLine();
							if (!toCountry.equalsIgnoreCase("exit")) {
								int armiesInFromCountry = 0;

								for (Territory territory : assignedTerritories) {
									if (territory.name.equals(fromCountry)) {
										armiesInFromCountry = territory.numberOfArmies;
									}
								}

								if (armiesInFromCountry == 0) {
									System.out.println("=================== bug ===============");
								}

								if (validNeighborCountry(fromCountry, toCountry)) {
									int armiesToMove = 0;
									System.out.println("Enter the number of armies to move."
											+ "\nNote the number of armies to move must be less than"
											+ " number of armies in the from country as every country must have"
											+ " at least 1 army");
									armiesToMove = Integer.parseInt(keyboard.nextLine());
									while (armiesToMove <= 0 || armiesToMove >= armiesInFromCountry) {
										System.out.println("Wrong number of Armies!"
												+ "\nNumber of armies should be less than: " + armiesInFromCountry);
										armiesToMove = Integer.parseInt(keyboard.nextLine());
									}
									for (Territory territory : assignedTerritories) {
										if (territory.name.equals(fromCountry)) {
											territory.numberOfArmies -= armiesToMove;
											for (Territory neighbour : territory.neighbours) {
												if (neighbour.name.equalsIgnoreCase(toCountry)) {
													neighbour.numberOfArmies += armiesToMove;
													break;
												}
											}
											break;
										}
									}
									doneFlag2 = true;
								} else {
									System.out.println("Enter a valid Neighbour that you own.");
								}
							} else {
								doneFlag1 = true;
								doneFlag2 = true;
							}
						} while (!doneFlag2);
						doneFlag1 = true;
					} else {
						System.out.println("Wrong Country!!"
								+ "\nEnter a country you own that is having at least one neighbour that you own too!!");
					}
				} else {
					doneFlag1 = true;
					doneFlag2 = true;
				}
			} while (!doneFlag1);
		}
	}
	
	/**
	 * The method exchangeCards 
	 * 
	 * @param cardIndex1
	 * @param cardIndex2
	 * @param cardIndex3
	 */
	public void exchangeCards(int cardIndex1, int cardIndex2, int cardIndex3) {
		cards.remove(cardIndex1 - 1);
		cards.remove(cardIndex2 - 1);
		cards.remove(cardIndex3 - 1);
		Card.cardExchangeValue += 5;
	}
	
	/**
	 * 
	 * @param cardIndex1
	 * @param cardIndex2
	 * @param cardIndex3
	 * @return  true if the given condition satifies.
	 * 
	 * @return false if the given condition doesn't satifies.
	 */
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
	
	/**
	 * 
	 * @return true if the given condition satifies.
	 * 
	 * @return false if the given condition doesn't satifies.
	 */
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
	
	public ArrayList<Map> ownedContinents() {
		ArrayList<Map> ownedContinents = new ArrayList<Map>();
		for(Map continent : Main.activeMap.continents) {
			ArrayList<Player> territoryOwners = new ArrayList<Player>();
			boolean ownContinent = true;
			for(Territory territory : continent.territories) {
				if(territory.owner == this) {
					if(territoryOwners.isEmpty()) {
						territoryOwners.add(territory.owner);
					}
					else {
						if(!territoryOwners.contains(territory.owner)) {
							ownContinent = false;
							break;
						}
					}
				}
				else {
					ownContinent = false;
					break;
				}
			}
			if(ownContinent) {
				ownedContinents.add(continent);
			}
		}
		return ownedContinents;
	}
}