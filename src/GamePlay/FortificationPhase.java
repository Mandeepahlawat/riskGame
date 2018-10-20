package GamePlay;

import java.util.Scanner;
import Map.Map;
import Map.Map.Territory;

/**
 * This class FortificationPhase consists a Constructor
 * FortificationPhase() mentioned below
 * @author bharath,shreyas 
 * @version 1.0
 * @since 19/10/2018
 *  
 *
 */

public class FortificationPhase {
	
	public FortificationPhase() {
		
	}
	/**
	 * fortification method to allow a player to move one of 
	 * his armies from one country he owns to another that 
	 * is adjacent to it
	 * 
	 * 
	 * @param currentPlayerId is a integer value which gives the present 
	 * ID of the player
	 * 
	 */
	public void fortification(int currentPlayerId) {
		String fromCountry = null;
		String toCountry = null;
		boolean doneFlag1 = false;
		boolean doneFlag2 = false;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the country you would like to move an army from:");
		fromCountry = in.nextLine();
		do {
			if(validEntry(currentPlayerId, fromCountry)) {
				System.out.println("Enter the neighbouring country you would like to move the army to:");
				toCountry = in.nextLine();
				do {
					if(validNeighborCountry(currentPlayerId, fromCountry, toCountry)) {
						for(Territory territory : Map.listOfAllTerritories) {
							if(territory.name.equals(fromCountry)) {
								territory.numberOfArmies--;
								for(Territory neighbour : territory.neighbours) {
									if(neighbour.name.equalsIgnoreCase(toCountry)) {
										neighbour.numberOfArmies++;
										break;
									}
								}
								break;
							}
						}
						doneFlag2 = true;
					}
					else {
						System.out.println("Enter a valid Neighbour..");
					}
				}while(!doneFlag2);
				doneFlag1 = true;
			}
			else {
				System.out.println("Enter a country you own!!");
			}
		}while(!doneFlag1);
		in.close();
	}
	
	/**
	 * To check if the players owns the country and
	 * the number of armies is greater than 1
	 * 
	 * @param currentPlayerId is a integer value which gives the present 
	 * ID of the player
	 * 
	 * @param country is a string value in which 
	 * the name of the country is mentioned 
	 * 
	 * @return true if the country belongs to the player 
	 * 
	 * @return false if the country doesn't belongs to player
	 */

	private boolean validEntry(int currentPlayerId, String country) {
		for(Territory territory : Map.listOfAllTerritories) {
			if(territory.name.equalsIgnoreCase(country) && territory.playerId == currentPlayerId) {
				if(territory.numberOfArmies > 1)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * To check if the neighbor is owned by the current player
	 * 
	 * @param currentPlayerId is a integer value which gives the present 
	 * ID of the player
	 * 
	 * @param fromCountry is a string value in which a player can mention 
	 * to move a army from a country
	 *  
	 * @param toCountry  is a string value in which a player can mention 
	 * to move a army from a country to another country
	 * 
	 * @return true if the given condition satifies.
	 * 
	 * @return false if the given condition doesn't satifies.
	 */
	
	private boolean validNeighborCountry(int currentPlayerId, String fromCountry, String toCountry) {
		for(Territory territory : Map.listOfAllTerritories) {
			if(territory.name.equals(fromCountry)) {
				for(Territory neighbor : territory.neighbours) {
					if(neighbor.name.equalsIgnoreCase(toCountry) && neighbor.playerId == currentPlayerId)
						return true;
				}
			}
		}
		return false;
	}
	
}
