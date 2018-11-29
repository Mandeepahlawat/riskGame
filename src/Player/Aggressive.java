package Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

import Card.Card;
import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Player.Player.GamePhase;
import Views.CardExchangeView;

public class Aggressive implements Strategy {
	/**
	 * The player to which this strategy belongs to
	 */
	public Player player;

	/**
	 * constructor for this class and sets the player data member
	 * 
	 * @param player
	 */
	public Aggressive(Player player) {
		this.player = player;
	}

	@Override
	public int calculateReinforcementArmies() {
		int totalReinforcements = 0;
		totalReinforcements = (int) (player.assignedTerritories.size() / 3);
		if (totalReinforcements < 3) {
			totalReinforcements = 3;
		}

		for (Map continent : Main.activeMap.continents) {
			int i = 0;
			for (Territory territory : continent.territories) {
				if (territory.owner != player) {
					break;
				}
				i++;
			}
			if (i == continent.territories.size()) {
				totalReinforcements = totalReinforcements + continent.score;
			}
		}
		if (player.canExchangeCards()) {
			totalReinforcements += Card.cardExchangeValue;
			ArrayList<Integer> cardIndexes = player.getCardIndexesToExchange();
			player.exchangeCards(cardIndexes.get(0), cardIndexes.get(1), cardIndexes.get(2));
		}

		System.out.println("Player " + player.getName() + " gets " + totalReinforcements + " reinforcement armies.");
		player.totalArmiesCount += totalReinforcements;
		return totalReinforcements;
	}

	@Override
	public void placeReinforcements(int reinforcements) {
		Territory territory = player.territoryWithMaxArmy(player.assignedTerritories);
		System.out.println("\nPlayer " + player.getName() + " places " + reinforcements + " in " + territory.name
				+ " as this has maximum armies");
		System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies + " armies");
		territory.numberOfArmies += reinforcements;
		System.out.println("Armies count after move: " + territory.name + ": " + territory.numberOfArmies + " armies");
	}

	@Override
	public void fortification() {
		ArrayList<Territory> territories = new ArrayList<Territory>(player.assignedTerritories);
		Territory territory = player.territoryWithMaxArmy(territories);

		while (!player.validAssignedCountry(territory.name)) {
			territories.remove(territory);
			if (territories.isEmpty()) {
				System.out.println("Player doesn't have any adjacent territories.");
				return;
			}
			territory = player.territoryWithMaxArmy(territories);
		}

		Territory adjacentTerritoryWithMaxArmy = player.territoryWithMaxArmy(territory.neighbours);

		int armiesToMove = adjacentTerritoryWithMaxArmy.numberOfArmies - 1;

		System.out.println("Player " + player.getName() + " moving " + armiesToMove + " armies from "
				+ adjacentTerritoryWithMaxArmy.name + " to " + territory.name);

		System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies + " , "
				+ adjacentTerritoryWithMaxArmy.name + " : " + adjacentTerritoryWithMaxArmy.numberOfArmies);

		territory.numberOfArmies += armiesToMove;
		adjacentTerritoryWithMaxArmy.numberOfArmies -= armiesToMove;

		System.out.println("Armies count after the move: " + territory.name + ": " + territory.numberOfArmies + " , "
				+ adjacentTerritoryWithMaxArmy.name + " : " + adjacentTerritoryWithMaxArmy.numberOfArmies);

	}

	@Override
	public void attack() {

		boolean attackDone = false; // if all territories are conquered or attack lost
		boolean gameCompleted = false; // if all territories are conquered
		boolean defenderLost = false;
		String attackFrom = "", attackAt = "", opponent = "", attacker = "";
		Scanner keyboard = new Scanner(System.in);

		HashSet<Territory> opponents;
		ArrayList<Territory> listOfTerritories = player.assignedTerritories;
		Territory strongest;

		while (true) {
			strongest = player.territoryWithMaxArmy(listOfTerritories);
			System.out.println("The strongest territory is " + strongest.name);

			opponents = player.getTerritoriesWithNeighboursToOthers(strongest);
			if (!opponents.isEmpty()) {
				attackFrom = strongest.name;
				break;
			}
			System.out.println("Skipping as it doesn't have any opponent");
			listOfTerritories.remove(strongest);
		}

		for (Territory countryBeingAttacked : opponents) {
			
			attackAt = countryBeingAttacked.name;
			boolean strongestHasArmy = true;

			while (!attackDone & strongestHasArmy) {
				boolean finishedAttackingThatTerritory = false; // finished attacking the present territory
				boolean allOutMode = true;

				while (!finishedAttackingThatTerritory) {
					Vector<Integer> armies = player.returnArmiesLeft(attackFrom, attackAt);
					System.out.println(attackFrom + ":" + armies.get(0) + " vs " + attackAt + ":" + armies.get(1));
					opponent = player.opponentPlayer(attackFrom, attackAt);
					attacker = player.attackingPlayer(attackFrom, attackAt);

					if (armies.get(0) == 1) {
						System.out.println("The attacker cannot proceed with the attack with just 1 army");
						finishedAttackingThatTerritory = true;
						attackDone = true;
						strongestHasArmy = false;
						break;
					} else if (armies.get(1) == 0) {
						System.out.println(attackFrom + " won the battle!");
						finishedAttackingThatTerritory = true;
						attackDone = true;
						defenderLost = true;
						break;
					} else {
						Vector<Integer> attackerDice, defenderDice;
						int attackerDiceCount = 0;
						int defenderDiceCount = 0;

						attackerDiceCount = player.calculateNumberOfDiceAllowed("attacker", attackFrom, attackAt,
								allOutMode);
						attackerDice = player.rollDice(attackerDiceCount);

						defenderDiceCount = player.calculateNumberOfDiceAllowed("defender", attackFrom, attackAt,
								allOutMode);
						defenderDice = player.rollDice(defenderDiceCount);

						while (!attackerDice.isEmpty() && !defenderDice.isEmpty()) {
							int attackerDiceValue = attackerDice.remove(attackerDice.size() - 1);
							int defenderDiceValue = defenderDice.remove(defenderDice.size() - 1);
							if (attackerDiceValue > defenderDiceValue) {
								player.reduceArmy("defender", attackFrom, attackAt);
								Map.findTerritory(attackAt).owner.totalArmiesCount--;
							} else {
								player.reduceArmy("attacker", attackFrom, attackAt);
								player.totalArmiesCount--;
							}
						}
					}
				}
			}
		}

		if (defenderLost) {
			for (Player player : Main.players) {
				// remove territory from the defeated player's list
				// add territory to the conquerer's list
				if (player.getName().equalsIgnoreCase(attacker)) {
					for (Territory territory : Main.activeMap.territories) {
						if (territory.name.equals(attackAt)) {
							player.addNewOwnedTerritory(territory);
						}
					}
					System.out.println("Enter the number of armies you would like to place in your new territory:");
					player.moveArmiesToNewTerritory(attackFrom, attackAt, keyboard.nextInt());
				}
			}
		}

		// if all territories are owned by a single user
		if (Main.activeMap.allTerritoriesOwnBySinglePlayer(false)) {
			gameCompleted = true;
			attackDone = true;
		}

		if (!gameCompleted) {
			player.setCurrentGamePhase(GamePhase.FORTIFICATION);
		} else {
			System.out.println("\nGame Completed!\n" + player.getName() + "wins!!");
		}
	}

}
