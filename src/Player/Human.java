package Player;

import java.util.Scanner;
import java.util.Vector;

import Card.Card;
import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Player.Player.GamePhase;
import Views.CardExchangeView;

public class Human implements Strategy{
	/**
	 * The player to which this strategy belongs to
	 */
	public Player player;
	
	/**
	 * constructor for this class and sets the
	 * player data member
	 * @param player
	 */
	public Human(Player player) {
		this.player = player;
	}

	/**
	 *This method Calculate's the number of reinforcements 
	 *that the player gets in each turn of the game.
	 *
	 *@return int the number of reinforcement armies.
	 */
	@Override
	public int calculateReinforcementArmies() {
		int totalReinforcements = 0;
		totalReinforcements =  (int)(player.assignedTerritories.size() / 3);
		if(totalReinforcements < 3) {
			totalReinforcements = 3;
		}

		for(Map continent : Main.activeMap.continents) {
			int i = 0;
			for(Territory territory : continent.territories) {
				if(territory.owner != player) {
					break;
				}
				i++;
			}
			if(i == continent.territories.size()) {
				totalReinforcements = totalReinforcements + continent.score;
			}
		}
		
		if(player.canExchangeCards()) {
			player.cardExchangeViewOpen = true;
			CardExchangeView cardExchangeView = new CardExchangeView();
			player.addObserver(cardExchangeView);
			
			if(player.cards.size() == 5) {
				totalReinforcements += Card.cardExchangeValue;
				player.setChangeAndNotifyObservers();
			}
			else {
				Scanner keyboard = new Scanner(System.in);
				System.out.println("Do you want to exchange cards? Enter yes or no?");
				if(keyboard.nextLine().equalsIgnoreCase("yes")) {
					totalReinforcements += Card.cardExchangeValue;
					player.setChangeAndNotifyObservers();
				}
			}
			
			player.deleteObserver(cardExchangeView);
			player.cardExchangeViewOpen = false;
		}
		
		System.out.println("Player " + player.getName() + " gets " + totalReinforcements + " reinforcement armies.");
		player.totalArmiesCount += totalReinforcements;
		return totalReinforcements;
	}

	/**
	 * This method defines Place Reinforcements in territories which 
	 * also print's out player name,armies left in it.
	 * 
	 * @param reinforcements number of reinforcement armies the 
	 * player in the second parameter gets.
	 * 
	 */
	@Override
	public void placeReinforcements(int reinforcements) {
		Scanner keyboard = new Scanner(System.in);
		String userInput = null;
		
		while (reinforcements != 0) {
			System.out.println("\nPlayer " + player.getName() + " has " + reinforcements
					+ " armies left."
					+ "\nEnter name of the Country to place a reinforcement army:");
			
				userInput = keyboard.nextLine();
				
				for (Territory territory : Main.activeMap.territories) {
					if (territory.name.equalsIgnoreCase(userInput)) {
						if (territory.owner != player) {
							System.out.println("Wrong country! Try again!");
							break;
						}
						territory.numberOfArmies++;
						reinforcements--;
						break;
					}
				}
			}
		player.setCurrentGamePhase(GamePhase.ATTACK);
	}

	/**
	 * 
	 * fortification method to allow a player to move 
	 * one of his armies from one country he owns to 
	 * another that is adjacent to it.
	 * 
	 */
	@Override
	public void fortification() {
		System.out.println("Player " + player.getName());
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
					if (player.validAssignedCountry(fromCountry)) {
						do {
							System.out.println(
									"Enter the neighbouring country you would like to move the army to: (enter 'exit' to skip fortification)");
							toCountry = keyboard.nextLine();
							if (!toCountry.equalsIgnoreCase("exit")) {
								int armiesInFromCountry = 0;

								for (Territory territory : player.assignedTerritories) {
									if (territory.name.equals(fromCountry)) {
										armiesInFromCountry = territory.numberOfArmies;
									}
								}

								if (armiesInFromCountry == 0) {
									System.out.println("=================== bug ===============");
								}

								if (player.validNeighborCountry(fromCountry, toCountry)) {
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
									for (Territory territory : player.assignedTerritories) {
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
	 * The method will implement the attack phase.
	 * attackDone  if all territories are conquered or attack lost.
	 * gameCompleted  if all territories are conquered.
	 * 
	 */
	@Override
	public void attack() {
		boolean attackDone = false;		//if all territories are conquered or attack lost	
		boolean gameCompleted = false;		//if all territories are conquered
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Do you want to go ahead with the attack? Enter yes or no:");
		String answer = keyboard.nextLine();
		if(answer.equalsIgnoreCase("yes")) {
			//boolean doneFlag1 = false;
			do {
				System.out.println("Enter the country you want to attack from (Enter 'exit' to skip attack phase)");
				String attackFrom = keyboard.nextLine();
				if(attackFrom.equalsIgnoreCase("exit")) {
					attackDone = true;
					break;
				}
				if(player.canattackFromThisCountry(attackFrom)) {
					System.out.println("Enter the country you want to attack (Enter 'exit' to skip attack phase)");
					String attackAt = keyboard.nextLine();
					if(attackAt.equalsIgnoreCase("exit")) {
						attackDone = true;
						break;
					}
					if(player.validOpponentCountry(attackFrom, attackAt)) {
						boolean finishedAttackingThatTerritory = false;		//finished attacking the present territory
						System.out.println("Do you want to go ALLOUT?: Enter yes or no");
						String isAllOut = keyboard.nextLine();
						if(isAllOut.equalsIgnoreCase("no")) {
							 do {
								Vector<Integer> armies = player.returnArmiesLeft(attackFrom, attackAt);
								System.out.println(attackFrom + ":" + armies.get(0) +   " vs " + attackAt + ":" + armies.get(1));
								String opponent = player.opponentPlayer(attackFrom, attackAt);
								System.out.println("Choose Attacker's number of dice");
								Vector<Integer> attackerDice = player.rollDice(player.calculateNumberOfDiceAllowed("attacker", attackFrom, attackAt, "no"));
								System.out.println("Choose Defender's number of dice");
								Vector<Integer> defenderDice = player.rollDice(player.calculateNumberOfDiceAllowed("defender", attackFrom, attackAt, "no"));
								while(!attackerDice.isEmpty() && !defenderDice.isEmpty()) {
									int attackerDiceValue = attackerDice.remove(attackerDice.size() - 1);
									int defenderDiceValue = defenderDice.remove(defenderDice.size() - 1);
									if(attackerDiceValue > defenderDiceValue) {
										player.reduceArmy("defender", attackFrom, attackAt);
										//check if the opponent lost
										if(player.checkDefenderArmiesNumberZero(attackFrom, attackAt)) {
											System.out.println("Enter the number of armies you would like to place in your new territory:");
											player.moveArmiesToNewTerritory(attackFrom, attackAt, keyboard.nextInt());
											//remove this territory from the players list
											for(Player playerr : Main.players) {
												//VERIFY IF THE TERRITORY IS REMOVED ONLY FROM THE LOST DEFENDERS TERRITORIES LIST
												if(playerr.getName().equalsIgnoreCase(opponent)) {
													for(Territory territory : Main.activeMap.territories) {
														if(territory.name.equalsIgnoreCase(attackAt)) {
															player.assignedTerritories.remove(territory);
														}
													}
												}
											}
											finishedAttackingThatTerritory = true;
											//if all territories are owned by a single user
											if(Main.activeMap.allTerritoriesOwnBySinglePlayer(false)) {
												gameCompleted = true;
												attackDone = true;
											}
											break;
										}
									}
									else {
										player.reduceArmy("attacker", attackFrom, attackAt);
										//check if you lost
										for(Territory territory : player.assignedTerritories) {
											if(territory.name.equalsIgnoreCase(attackFrom)) {
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
							do {
								Vector<Integer> armies = player.returnArmiesLeft(attackFrom, attackAt);
								System.out.println(attackFrom + ":" + armies.get(0) +   " vs " + attackAt + ":" + armies.get(1));
								String opponent = player.opponentPlayer(attackFrom, attackAt);
								Vector<Integer> attackerDice = player.rollDice(player.calculateNumberOfDiceAllowed("attacker", attackFrom, attackAt, "yes"));
								Vector<Integer> defenderDice = player.rollDice(player.calculateNumberOfDiceAllowed("defender", attackFrom, attackAt, "yes"));
								while(!attackerDice.isEmpty() && !defenderDice.isEmpty()) {
									int attackerDiceValue = attackerDice.remove(attackerDice.size() - 1);
									int defenderDiceValue = defenderDice.remove(defenderDice.size() - 1);
									if(attackerDiceValue > defenderDiceValue) {
										player.reduceArmy("defender", attackFrom, attackAt);
										//check if the opponent lost
										if(player.checkDefenderArmiesNumberZero(attackFrom, attackAt)) {
											System.out.println("Enter the number of armies you would like to place in your new territory:");
											player.moveArmiesToNewTerritory(attackFrom, attackAt, keyboard.nextInt());
											//remove this territory from the players list
											for(Player playerr : Main.players) {
												//VERIFY IF THE TERRITORY IS REMOVED ONLY FROM THE LOST DEFENDERS TERRITORIES LIST
												if(playerr.getName().equalsIgnoreCase(opponent)) {
													for(Territory territory : Main.activeMap.territories) {
														if(territory.name.equalsIgnoreCase(attackAt)) {
															player.assignedTerritories.remove(territory);
														}
													}
												}
											}
											finishedAttackingThatTerritory = true;
											//if all territories are owned by a single user
											if(Main.activeMap.allTerritoriesOwnBySinglePlayer(false)) {
												gameCompleted = true;
												attackDone = true;
											}
											break;
										}
									}
									else {
										player.reduceArmy("attacker", attackFrom, attackAt);
										//check if you lost
										for(Territory territory : player.assignedTerritories) {
											if(territory.name.equalsIgnoreCase(attackFrom)) {
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
					}
					else {
						System.out.println("Enter a valid country you would like to attack");
					}
				}
				else {
					System.out.println("Enter a valid country you would like to attack from");
				}
				if(!gameCompleted && !attackDone) {
					System.out.println("Do you want to continue with the attack? Enter yes or no");
					String input = keyboard.nextLine();
					if(input.equalsIgnoreCase("no"))
						attackDone = true;
					if(input.equalsIgnoreCase("yes"))
						attackDone = false;
				}
			}while(!attackDone);
		}
		if(!gameCompleted) {
			player.setCurrentGamePhase(GamePhase.FORTIFICATION);
		}
	}

}
