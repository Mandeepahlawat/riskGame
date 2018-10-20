package GamePlay;

import java.util.Scanner;
import Map.Map;
import Map.Map.Territory;

public class FortificationPhase {
	
	/*Constructor*/
	public FortificationPhase() {
		
	}
	
	/*Fortification method to allow a player to move one of his armies from one country he owns to another that is adjacent to it*/
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
	
	/*To check if the players owns the country and the number of armies is greater than 1*/
	private boolean validEntry(int currentPlayerId, String country) {
		for(Territory territory : Map.listOfAllTerritories) {
			if(territory.name.equalsIgnoreCase(country) && territory.playerId == currentPlayerId) {
				if(territory.numberOfArmies > 1)
					return true;
			}
		}
		return false;
	}
	
	
	/*To check if the neighbor is owned by the current player*/
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
