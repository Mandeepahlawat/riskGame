package Views;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Player.Player;

public class WorldDominationView implements Observer {
	
	public void countryNameWithContinentAndArmy(Territory territory, boolean neighbour) {
		if(neighbour) {
			System.out.print("\t"
					+ territory.name 
					+ " Armies : " + territory.numberOfArmies
					+ ", Continent: " + territory.continent.name);
		}
		else {
			System.out.print(territory.name 
					+ " Armies : " + territory.numberOfArmies
					+ ", Continent: " + territory.continent.name);
		}
	}
	
	/**
	 * this method displays the current status of map
	 * <ul>
	 * <li>Which player owns what country</li>
	 * <li>How many armies that player has in that country</li>
	 * <li>Which are the neighbouring territories of this country</li>
	 * </ul>
	 */
	public void display(Map activeMap) {
		for(Player player : Main.players) {
			int ownedTerritories = player.assignedTerritories.size();
			int totalTerritories = activeMap.territories.size();
			float percentTerritoriesOwn = (ownedTerritories * 100 / totalTerritories);
			System.out.println("=================="
					+ player.getName()
					+" INFORMATION ==================================");
			
			System.out.println(player.getName() + " owns " + percentTerritoriesOwn + 
					"% territories");
			System.out.println(player.getName() + " owns following countries: ");
			for(Territory territory : player.assignedTerritories) {
				countryNameWithContinentAndArmy(territory, false);
				System.out.println("");
				System.out.println("Neighbouring countries");
				for(Territory neighbour : territory.neighbours) {
					countryNameWithContinentAndArmy(neighbour, true);
					System.out.println("");
				}
			}
			
			System.out.println(player.getName() + " owns " + player.totalArmiesCount + 
					" total armies");
			
			ArrayList<Map> ownedContinents = player.ownedContinents();
			if(ownedContinents.size() > 0) {
				System.out.println(player.getName() + " owns following continents: ");
				for(Map continent : ownedContinents) {
					System.out.println(continent.name);
				}
			}
			else {
				System.out.println(player.getName() + " doesn't own any continents");
			}
			System.out.println("====================================================");
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Map currentMap = (Map) o;
		System.out.println("\n*********************** WORLD DOMINATION VIEW START *****************************\n");
		display(currentMap);
		System.out.println("\n*********************** WORLD DOMINATION VIEW ENDS *****************************\n");
	}

}
