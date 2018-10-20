package GamePlay;

import java.util.Scanner;
import Map.Map;
import Map.Map.Territory;

/**
 * This class ReinforcementPhase consists a
 *  Constructor ReinforcementPhase()  
 * 
 * @author bharath,shreyas 
 * @version 1.0
 * @since 19/10/2018
 * 
 *
 */

public class ReinforcementPhase {
	
	public ReinforcementPhase() {
		
	}

	/**
	 *This method Calculate's the number of reinforcements 
	 *that the player gets in each turn of the game.
	 *
	 *@param currentPlayerId is a integer value which gives the present 
	 * ID of the player in the game.
	 *
	 */
	public int calculateReinforcementArmies(int currentPlayerId) {
		int totalReinforcements = 0;
		int territoriesOwned = 0;
		for(Territory territory : Map.listOfAllTerritories) {
			if(territory.playerId == currentPlayerId) {
				territoriesOwned++;
			}
		}
		totalReinforcements =  (int)(territoriesOwned / 3);
		if(totalReinforcements < 3)
			totalReinforcements = 3;
		
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
	
	/**
	 * This method defines Place Reinforcements in territories
	 * 
	 * @param reinforcements
	 * 
	 * @param currentPlayerId is a integer value which gives the present 
	 * ID of the player in the game.
	 * 
	 */
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
