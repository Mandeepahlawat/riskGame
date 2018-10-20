package GamePlay;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import Map.Map;
import Map.Map.Territory;

public class StartupPhase {

	private int numOfPlayers = 0; // The number of players who will be playing
	private int PlayerPlaying = 0; // to store the current players who is playing
	private int initialArmies[]; // Initial number of armies at the start of the game
	private int totalInitialArmies = 0;

	/* Constructor */
	public StartupPhase(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		initialArmies = new int[numOfPlayers];
		assignInitialTerritories();
		assignInitialArmies();
		Random rand = new Random();
		this.PlayerPlaying = rand.nextInt(numOfPlayers) + 1;
		display();
		placeArmies();
	}

	/* Determine the players turn to play next */
	private void nextPlayerToPlay() {
		if (PlayerPlaying == numOfPlayers)
			PlayerPlaying = 1;
		else
			PlayerPlaying++;
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
				System.out.println("\n");
			}
		}
	}

	/* Assigning the initial territories randomly to the players */
	public void assignInitialTerritories() {
		int count = 1;
		for (Territory territory : Map.listOfAllTerritories) {
			if (territory.playerId == 0) {
				if(count <= numOfPlayers) {
					territory.playerId = count;
					count++;
				}
				else {
					Random rand = new Random();
					territory.playerId = rand.nextInt(numOfPlayers) + 1;
				}
			}
		}
	}

	/* Assigning the initial number of armies each player gets */
	public void assignInitialArmies() {
		if (numOfPlayers == 2) {
			for (int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 40;
			totalInitialArmies = 80;
		} else if (numOfPlayers == 3) {
			for (int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 35;
			totalInitialArmies = 105;
		} else if (numOfPlayers == 4) {
			for (int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 30;
			totalInitialArmies = 120;
		} else if (numOfPlayers == 5) {
			for (int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 25;
			totalInitialArmies = 125;
		} else if (numOfPlayers == 6) {
			for (int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 20;
			totalInitialArmies = 120;
		}
	}

	/* Initial placement of the armies */
	public void placeArmies() {
		Scanner in = new Scanner(System.in);
		String userInput = null;
		boolean doneFlag = false;
		while (totalInitialArmies != 0) {
			System.out.println("Player " + PlayerPlaying + "enter name of the Country to place an army in:");

			do {
				userInput = in.nextLine();
				for (Territory territory : Map.listOfAllTerritories) {
					if (territory.name.equalsIgnoreCase(userInput)) {
						if (territory.playerId != PlayerPlaying) {
							System.out.println("Wrong country! Try again!");
							// userInput = in.nextLine();
							break;
						}
						territory.numberOfArmies++;
						initialArmies[PlayerPlaying]--;
						doneFlag = true;
						break;
					}
				}
			} while (!doneFlag);
			nextPlayerToPlay();
			totalInitialArmies--;
		}
		in.close();
	}

}
