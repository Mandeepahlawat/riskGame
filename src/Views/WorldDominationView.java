package Views;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import Driver.Main;
import Map.Map.Territory;

public class WorldDominationView implements Observer {
	/**
	 * this method displays the current status of map
	 * <ul>
	 * <li>Which player owns what country</li>
	 * <li>How many armies that player has in that country</li>
	 * <li>Which are the neighbouring territories of this country</li>
	 * </ul>
	 */
	public void display() {
		for (Territory terr : Main.activeMap.territories) {
			terr.visited = false;
		}

		Stack<Territory> stack = new Stack<>();
		stack.push(Main.activeMap.territories.get(0));
		while (!stack.isEmpty()) {
			Territory territoryBeingVisited = stack.pop();
			if (territoryBeingVisited.visited == false) {
				territoryBeingVisited.visited = true;

				System.out.println("\n" + territoryBeingVisited.name
						+ " is owned by Player " + territoryBeingVisited.owner.getName());
				
				System.out.println("Player " + territoryBeingVisited.owner.getName()
				+ " has " + territoryBeingVisited.numberOfArmies
				+ " armies in this territory");
				
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
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("\n*********************** WORLD DOMINATION VIEW START *****************************\n");
		display();
		System.out.println("\n*********************** WORLD DOMINATION VIEW ENDS *****************************\n");
	}

}
