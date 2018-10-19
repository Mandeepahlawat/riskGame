package GamePlay;
import java.util.Random;
import java.util.Scanner;

import Map.Map;
import Map.Map.Territory;

public class StartupPhase {
	
	private	int numOfPlayers = 0;		//The number of players who will be playing
	private int PlayerPlaying = 0;		//to store the current players who is playing
	private int initialArmies[];		//Initial number of armies at the start of the game
	private int totalInitialArmies = 0;
	
	/*Constructor*/
	public StartupPhase(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		initialArmies = new int[numOfPlayers];
		assignInitialArmies();
		Random rand = new Random();
		this.PlayerPlaying = rand.nextInt(numOfPlayers) + 1;
	}
	
	/*Determine the players turn to play next*/
	private void nextPlayerToPlay() {
		if(PlayerPlaying == numOfPlayers)
			PlayerPlaying = 1;
		else
			PlayerPlaying++;
	}
	
	//USING THE DFS IMPLEMENTED BY MEHAK DISPLAY THE ADJACENT TERRITORIES SEPERATED BY A '=' also 
	//depicting the player controlling it, with the number of armies and the continent that it is in
	
	/*Assigning the initial territories randomly to the players*/
	public void assignInitialTerritories() {
		for(Territory territory : Map.listOfAllTerritories) {
			if(territory.playerId == 0) {
				Random rand = new Random();
				territory.playerId = rand.nextInt(numOfPlayers) + 1;
			}
		}
	}
	
	/*Assigning the initial number of armies each player gets*/
	public void assignInitialArmies() {
		if(numOfPlayers == 2) {
			for(int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 40;
			totalInitialArmies = 80;
		}
		else if(numOfPlayers == 3) {
			for(int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 35;
			totalInitialArmies = 105;
		}
		else if(numOfPlayers == 4) {
			for(int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 30;
			totalInitialArmies = 120;
		}
		else if(numOfPlayers == 5) {
			for(int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 25;
			totalInitialArmies = 125;
		}
		else if(numOfPlayers == 6) {
			for(int i = 0; i < numOfPlayers; i++)
				initialArmies[i] = 20;
			totalInitialArmies = 120;
		}
	}
	
	
	/*Initial placement of the armies*/
	public void placeArmies() {
		Scanner in = new Scanner(System.in);
		String userInput = null;
		boolean doneFlag = false;
		while(totalInitialArmies != 0) {
			System.out.println("Player " + PlayerPlaying +"enter name of the Country to place an army in:");
			userInput = in.nextLine();
			do {
				for(Territory territory : Map.listOfAllTerritories) {
					if(territory.name.equalsIgnoreCase(userInput)) {
						if(territory.playerId != PlayerPlaying) {
							System.out.println("Wrong country!!");
							//userInput = in.nextLine();
							break;
						}
						territory.numberOfArmies++;
						initialArmies[PlayerPlaying]--;
						doneFlag = true;
						break;
					}
				}
			}while(!doneFlag);
			nextPlayerToPlay();
			totalInitialArmies--;
		}
		in.close();
	}
	
	
}
