package Player;

import java.util.ArrayList;

import Card.Card;
import Driver.Main;
import Map.Map.Territory;
import Player.Player.GamePhase;

public class Cheater implements Strategy {
	/**
	 * The player to which this strategy belongs to
	 */
	public Player player;
	
	/**
	 * constructor for this class and sets the
	 * player data member
	 * @param player
	 */
	public Cheater(Player player) {
		this.player = player;
	}
	
	@Override
	public int calculateReinforcementArmies() {
		int totalReinforcements = 0;
		for(Territory territory : player.assignedTerritories) {
			totalReinforcements += (territory.numberOfArmies * 2);
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
		System.out.println("\nPlayer " + player.getName() + " doubles the number of armies in each"
				+ "owned country");
		for(Territory territory : player.assignedTerritories) {
			System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies
					+ " armies");
			territory.numberOfArmies += territory.numberOfArmies;
			System.out.println("Armies count after move: " + territory.name + ": " + territory.numberOfArmies
					+ " armies");
		}
	}

	@Override
	public void fortification() {
		System.out.println("\nPlayer " + player.getName() + " doubles the number of armies in each"
				+ " owned country that has neighbouring country with other player");
		for(Territory territory : player.getTerritoriesWithNeighboursToOthers()) {
			System.out.println("Armies count before move: " + territory.name + ": " + territory.numberOfArmies
					+ " armies");
			territory.numberOfArmies += territory.numberOfArmies;
			System.out.println("Armies count after move: " + territory.name + ": " + territory.numberOfArmies
					+ " armies");
		}
	}

	@Override
	public void attack() {
		ArrayList<Territory> assignedTerritoriesBeforeAttack = new ArrayList<Territory>(player.assignedTerritories);
		for(Territory territory : assignedTerritoriesBeforeAttack) {
			for(Territory neighbour : player.getTerritoriesWithNeighboursToOthers(territory)) {
				System.out.println(territory.name + " attacks " + neighbour.name);
				System.out.println("Player " + player.getName() + " won " + neighbour.name);
				player.addNewOwnedTerritory(neighbour);
			}
		}
		if(Main.activeMap.allTerritoriesOwnBySinglePlayer(false)) {
			System.out.println("Game finished");
			System.exit(0);
		}
		else {
			player.setCurrentGamePhase(GamePhase.FORTIFICATION);
		}
	}
	
	

}
