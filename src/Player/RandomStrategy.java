package Player;

import java.util.ArrayList;
import java.util.Random;

import Card.Card;
import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Player.Player.GamePhase;

public class RandomStrategy implements Strategy {
	/**
	 * The player to which this strategy belongs to
	 */
	public Player player;
	
	/**
	 * constructor for this class and sets the
	 * player data member
	 * @param player
	 */
	public RandomStrategy(Player player) {
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
		Territory territory = player.getRandomTerritory(player.assignedTerritories);
		System.out.println("\nPlayer " + player.getName() + " places " + reinforcements
				+ " in " + territory.name + " as this is selected randomly");
		System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies
				+ " armies");
		territory.numberOfArmies += reinforcements;
		System.out.println("Armies count after move: " + territory.name + ": " + territory.numberOfArmies
				+ " armies");
		
	}

	@Override
	public void fortification() {
		ArrayList<Territory> territories = new ArrayList<Territory>(player.assignedTerritories);
		Territory territory = player.getRandomTerritory(territories);
		
		while(!player.validAssignedCountry(territory.name)) {
			territories.remove(territory);
			if(territories.isEmpty()) {
				System.out.println("Player doesn't have any adjacent territories.");
				return;
			}
			territory = player.getRandomTerritory(territories);
		}
		
		Territory adjacentRandomTerritory = player.getRandomTerritory(territory.neighbours);
		
		int armiesToMove = new Random().nextInt(adjacentRandomTerritory.numberOfArmies);
		
		System.out.println("Player " + player.getName() + " moving " + armiesToMove + " armies from "
				+ adjacentRandomTerritory.name + " to " + territory.name);
		
		System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies
				+ " , " + adjacentRandomTerritory.name + " : " + adjacentRandomTerritory.numberOfArmies);
		
		territory.numberOfArmies += armiesToMove;
		adjacentRandomTerritory.numberOfArmies -= armiesToMove;
		
		System.out.println("Armies count after the move: " + territory.name + ": " + territory.numberOfArmies
				+ " , " + adjacentRandomTerritory.name + " : " + adjacentRandomTerritory.numberOfArmies);
		
	}

	@Override
	public void attack() {
		player.setCurrentGamePhase(GamePhase.FORTIFICATION);
	}

}
