package GamePlay;

import java.util.Stack;

import Map.Map;
import Map.Map.Territory;

/**
 * This class drives the entire gamePlay package
 */
public class gameDriver {
	
	private int numOfPlayers;
	private StartupPhase startupPhaseObj;
	private ReinforcementPhase reinforcementPhaseObj;
	private FortificationPhase fortificationPhaseObj;
	
	/**
	 * Constructor gameDriver
	 * 
	 * @param numOfPlayers number of players 
	 * 
	 */
	public gameDriver(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	/**
	 * this function calls all the phases of the game for all the players
	 * in order
	 * <ul>
	 * <li>StartUp Phase</li>
	 * <li>Reinforcement Phase</li>
	 * <li>Fortification Phase</li>
	 * </ul>
	 */
	public void play() {
		System.out.println("\n***********************STARTUP PHASE BEGINS*****************************\n");
		startupPhaseObj = new StartupPhase(numOfPlayers);
		startupPhaseObj.placeArmies();
		System.out.println("\n***********************STARTUP PHASE ENDS*****************************\n");
		System.out.println("\n***********************REINFORCEMENT PHASE BEGINS*****************************\n");		reinforcementPhaseObj = new ReinforcementPhase();
		for(int i = 1; i<=numOfPlayers; i++) {
			int reinforcementArmies = reinforcementPhaseObj.calculateReinforcementArmies(i);
			display();
			reinforcementPhaseObj.placeReinforcements(reinforcementArmies, i);
		}
		System.out.println("\n***********************REINFORCEMENT PHASE ENDS*****************************\n");		
		System.out.println("\n***********************FORTIFICATION PHASE BEGINS*****************************\n");
		fortificationPhaseObj = new FortificationPhase();
		java.util.Scanner keyboard = new java.util.Scanner(System.in);
		for(int i = 1; i<=numOfPlayers; i++) {
			while(true) {
				fortificationPhaseObj.fortification(i);
				System.out.println("Enter 'finish' when you are done, or 'c' to continue");
				if(keyboard.nextLine().equalsIgnoreCase("finish")) {
					break;
				}
			}
		}
		System.out.println("\n***********************FORTIFICATION PHASE ENDS*****************************\n");
	}

	/**
	 * this function displays the current status of map
	 * <ul>
	 * <li>Which player owns what country</li>
	 * <li>How many armies that player has in that country</li>
	 * <li>Which are the neighbouring territories of this country</li>
	 * </ul>
	 */
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