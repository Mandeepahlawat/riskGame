package GamePlay;

import java.util.Random;
import java.util.Scanner;

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
	}

	/* Determine the players turn to play next */
	private void nextPlayerToPlay() {
		if (PlayerPlaying == numOfPlayers)
			PlayerPlaying = 1;
		else
			PlayerPlaying++;
	}

	/* Assigning the initial territories randomly to the players */
	public void assignInitialTerritories() {
		int count = 1;
		for (Territory territory : Map.listOfAllTerritories) {
			if (territory.playerId == 0) {
				if (count <= numOfPlayers) {
					territory.playerId = count;
					count++;
				} else {
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
		System.out.println("Write 'm' to place armies manually or 'a' to place armies automatically");
		Scanner in = new Scanner(System.in);
		String userInput = null;
		userInput = in.nextLine();
		if (userInput.equalsIgnoreCase("m")) {
			boolean doneFlag = false;
			while (totalInitialArmies != 0) {
				System.out.println("Player " + PlayerPlaying + " has " + initialArmies[PlayerPlaying - 1]
						+ " armies left."
						+ "\nEnter name of the Country and number of armies to place in that country separated by comma.");

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
							initialArmies[PlayerPlaying - 1]--;
							doneFlag = true;
							break;
						}
					}
				} while (!doneFlag);
				nextPlayerToPlay();
				totalInitialArmies--;
			}
		} else if (userInput.equalsIgnoreCase("a")) {
			placeArmiesAutomatically();
		}
		in.close();
	}

	public void placeArmiesAutomatically() {
		Random rand = new Random();
		int totalArmies = totalInitialArmies;
		while (totalInitialArmies != 0) {
			for (Territory territory : Map.listOfAllTerritories) {
				if (initialArmies[territory.playerId - 1] != 0) {
					int armiesToAssign = rand.nextInt(totalArmies / Map.listOfAllTerritories.size()) + 1;
					if (armiesToAssign <= initialArmies[territory.playerId - 1]) {
						territory.numberOfArmies += armiesToAssign;
						initialArmies[territory.playerId - 1] -= armiesToAssign;
						totalInitialArmies -= armiesToAssign;
						System.out.println("Player " + territory.playerId + " placed " + armiesToAssign + " armies in "
								+ territory.name);
						System.out.println("Player " + territory.playerId + " has "
								+ initialArmies[territory.playerId - 1] + " armies left.");
					} else {
						armiesToAssign = initialArmies[territory.playerId - 1];
						territory.numberOfArmies += armiesToAssign;
						initialArmies[territory.playerId - 1] -= armiesToAssign;
						totalInitialArmies -= armiesToAssign;
						System.out.println("Player " + territory.playerId + " placed " + armiesToAssign + " armies in "
								+ territory.name);
						System.out.println("Player " + territory.playerId + " has "
								+ initialArmies[territory.playerId - 1] + " armies left.");
					}
				}
			}
		}
	}
}
