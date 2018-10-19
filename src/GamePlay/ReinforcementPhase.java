package GamePlay;

import java.util.Scanner;

import Map.Map;
import Map.Map.Territory;

public class ReinforcementPhase {
	
	/*Conctructor*/
	public ReinforcementPhase() {
		
	}

	/*Calculate the number of reinforcements that the player gets in each turn*/
	public int calculateReinforcementArmies(int currentPlayerId) {
		int totalReinforcements = 0;
		
		//based on the number of territories controlled
		int territoriesOwned = 0;
		for(Territory territory : Map.listOfAllTerritories) {
			if(territory.playerId == currentPlayerId) {
				territoriesOwned++;
			}
		}
		totalReinforcements =  (int)(territoriesOwned / 3);
		if(totalReinforcements < 3)
			totalReinforcements = 3;
		
		//Number of continents controlled, if yes then gets as many armies as the value of the continent controlled
		for(Map continent : Map.listOfAllContinents) {
			int i = 0;
			for(Territory territory : continent.territories) {
				if(territory.playerId != currentPlayerId)
					break;
				i++;
			}
			if(i == continent.territories.size())
				totalReinforcements = totalReinforcements + continent.score;
		}
		return totalReinforcements;
	}
	
	
	/*Place Reinforcements in territories*/
	public void placeReinforcements(int reinforcements, int currentPlayerId) {
		Scanner in = new Scanner(System.in);
		String userInput = null;
		boolean doneFlag = false;
		while(reinforcements != 0) {
			System.out.println("Player " + currentPlayerId +"enter name of the Country to place an army in:");
			userInput = in.nextLine();
			do {
				for(Territory territory : Map.listOfAllTerritories) {
					if(territory.name.equalsIgnoreCase(userInput)) {
						if(territory.playerId != currentPlayerId) {
							System.out.println("Wrong country!!");
							//userInput = in.nextLine();
							break;
						}
						territory.numberOfArmies++;
						doneFlag = true;
						break;
					}
				}
			}while(!doneFlag);
			reinforcements--;
		}
		in.close();
	}
	
	
}
