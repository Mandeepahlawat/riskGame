package GamePlay;

import java.util.Stack;

import Map.Map;
import Map.Map.Territory;

public class gameDriver {

	private int numOfPlayers;
	private StartupPhase StartupPhaseObj;
	private ReinforcementPhase ReinforcementPhaseObj;

	public gameDriver(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public void play() {
		StartupPhaseObj = new StartupPhase(numOfPlayers);
		StartupPhaseObj.placeArmies();
		ReinforcementPhaseObj = new ReinforcementPhase();
		for(int i = 1; i<=numOfPlayers; i++) {
			int reinforcementArmies = ReinforcementPhaseObj.calculateReinforcementArmies(i);
			display();
			ReinforcementPhaseObj.placeReinforcements(reinforcementArmies, i);
		}
	}

	// USING THE DFS IMPLEMENTED BY MEHAK DISPLAY THE ADJACENT TERRITORIES SEPERATED
	// BY A '=' also
	// depicting the player controlling it, with the number of armies and the
	// continent that it is in
	public void display() {

		for (Territory terr : Map.listOfAllTerritories) {
			terr.visited = false;
		}

		Stack<Territory> stack = new Stack<>();
		stack.push(Map.listOfAllTerritories.get(0));
		while (!stack.isEmpty()) {
			Territory territoryBeingVisited = stack.pop();
			if (territoryBeingVisited.visited == false) {
				territoryBeingVisited.visited = true;

				System.out.println(
						"\n" + territoryBeingVisited.name + " is owned by Player " + territoryBeingVisited.playerId);
				System.out.println("Player " + territoryBeingVisited.playerId + " has "
						+ territoryBeingVisited.numberOfArmies + " armies in this territory");
				System.out.println("This territory is connected to the following territoris: ");
				for (Territory neighbour : territoryBeingVisited.neighbours) {
					if (!neighbour.visited) {
						stack.push(neighbour);
					}
					System.out.print(" -> " + neighbour.name);
				}
				System.out.println();
			}
		}
	}
}