package Player;

import java.util.ArrayList;

import Card.Card;
import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Player.Player.GamePhase;

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
		Territory territory = player.territoryWithMinArmy(player.assignedTerritories);
		System.out.println("\nPlayer " + player.getName() + " places " + reinforcements
				+ " in " + territory.name + " as this has minimum armies");
		System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies
				+ " armies");
		territory.numberOfArmies += reinforcements;
		System.out.println("Armies count after move: " + territory.name + ": " + territory.numberOfArmies
				+ " armies");
		
	}

	@Override
	public void fortification() {
		ArrayList<Territory> territories = new ArrayList<Territory>(player.assignedTerritories);
		Territory territory = player.territoryWithMinArmy(territories);
		
		while(!player.validAssignedCountry(territory.name)) {
			territories.remove(territory);
			if(territories.isEmpty()) {
				System.out.println("Player doesn't have any adjacent territories. Please run the program again.");
				System.exit(0);
			}
			territory = player.territoryWithMinArmy(territories);
		}
		
		territories.remove(territory);
		Territory adjacentTerritoryWithMaxArmy = player.territoryWithMaxArmy(territories);
		
		int armiesToMove = adjacentTerritoryWithMaxArmy.numberOfArmies - 1;
		
		System.out.println("Player " + player.getName() + " moving " + armiesToMove + " armies from "
				+ adjacentTerritoryWithMaxArmy.name + " to " + territory.name);
		
		System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies
				+ " , " + adjacentTerritoryWithMaxArmy.name + " : " + adjacentTerritoryWithMaxArmy.numberOfArmies);
		
		territory.numberOfArmies += armiesToMove;
		adjacentTerritoryWithMaxArmy.numberOfArmies -= armiesToMove;
		
		System.out.println("Armies count after the move: " + territory.name + ": " + territory.numberOfArmies
				+ " , " + adjacentTerritoryWithMaxArmy.name + " : " + adjacentTerritoryWithMaxArmy.numberOfArmies);
		
	}

	@Override
	public void attack() {
		player.setCurrentGamePhase(GamePhase.FORTIFICATION);
	}

}
