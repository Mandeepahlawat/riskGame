package Player;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Driver.Main;
import Map.Map;
import Map.Map.Territory;

public class Player_Test {

	private Player player;
	@Before
	public void testBefore() {
		player=new Player("Player1");
		Main.players = new ArrayList<>();
	}
	
	@Test
	public void testGetName() {
		assertTrue(player.getName().equals("Player1"));
	}

	@Test
	public void testSetInitialArmyCount() {
		player.setInitialArmyCount(3);
		assertTrue(player.armiesLeft==3&&player.totalArmiesCount==3);
	}

	@Test
	public void testAssignedTerritoryNamesWithArmies() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		/*Main.players.add(player);
		Territory t1 = new Territory("Asia"); 
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		System.out.println("testATWA: "+player.assignedTerritoryNamesWithArmies()+" "+player.assignedTerritories);
		*///removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player2");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		//player.assignedTerritories.get(0).numberOfArmies=5;
		System.out.println(player.assignedTerritoryNamesWithArmies()); 
		assertTrue(player.assignedTerritories.get(0).numberOfArmies==5&&player.assignedTerritories.get(0).name.equals("Africa"));
	}

	

	@Test
	public void testPlaceArmiesAutomatically() {//adding only 1 player as its random function so we cant capture the exact player name
		player.setInitialArmyCount(3);
		Main.players.add(player);
		/*player = new Player("Player2");
		Main.players.add(player);*/
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		//player.assignedTerritoryNamesWithArmies();
		player.placeArmiesAutomatically();
		//System.out.println(t1.numberOfArmies);
		assertTrue(t1.numberOfArmies==8);//5+3(since only one player all armies assinged here)
	}

	 
	@Test
	public void testValidNeighborCountryOwned() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");
		t1.neighbours.get(0).owner=player;
		//t1.owner=player;
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 
		assertTrue(player.publicValidNeighborCountryForJUnitTest("Africa","Jon"));
	}
	
	@Test
	public void testValidNeighborCountryNotOwned() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");
		t1.neighbours.get(0).owner=player;
		//t1.owner=player;// to add owner to neighbours add thru get list
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 
		assertFalse(player.publicValidNeighborCountryForJUnitTest("Africa","tan"));
	}
	
	@Test
	public void testValidAssignedCountryOwned() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");//add neighbour and add player to owner of neighbour and finally create assignedterritories list since player is assighes
		t1.neighbours.get(0).owner=player;
		//t1.owner=player;
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 
		assertTrue(player.publicValidAssignedCountryForJUnitTest("Africa"));
	}
	
	@Test
	public void testValidAssignedCountryNotOwned() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");//add neighbour and add player to owner of neighbour and finally create assignedterritories list since player is assighes
		t1.neighbours.get(0).owner=player;
		//t1.owner=player;
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 
		assertFalse(player.publicValidAssignedCountryForJUnitTest("Asia"));
	}
	
	@Test
	public void testValidOpponentCountry() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");//add neighbour and add player to owner of neighbour and finally create assignedterritories list since player is assighes
		t1.neighbours.get(0).owner=player;
		//t1.owner=player;
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 //valid opponent if owner != player and neighbour
		assertTrue(player.publicValidOpponentCountryForJUnitTest("Africa","tan"));
	}
	
	@Test
	public void testInValidOpponentCountry() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");//add neighbour and add player to owner of neighbour and finally create assignedterritories list since player is assighes
		t1.neighbours.get(0).owner=player;
		//t1.owner=player;
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 //valid opponent if owner != player and neighbour
		assertFalse(player.publicValidOpponentCountryForJUnitTest("Africa","Jon"));
	}
	
	
	@Test
	public void testOpponentPlayer() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");//add neighbour and add player to owner of neighbour and finally create assignedterritories list since player is assighes
		t1.neighbours.get(0).owner=player;
		Player player2 = new Player("Player2");
		t1.neighbours.get(1).owner=player2;
		//t1.owner=player;
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 //valid opponent if owner != player and neighbour
		assertTrue(player.publicOpponentPlayerForJUnitTest("Africa","tan").equals("Player2"));
	}
	 
	@Test
	public void testCanAttackFromThisCountry() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");//add neighbour and add player to owner of neighbour and finally create assignedterritories list since player is assighes
		t1.neighbours.get(0).owner=player;
		Player player2 = new Player("Player2");//assigning owner to neighbour
		t1.neighbours.get(1).owner=player2;
		//t1.owner=player;
		t1.numberOfArmies=5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 //valid opponent if owner != player and neighbour
		assertTrue(player.publicCanAttackFromThisCountryForJUnitTest("Africa"));
	}
	
	@Test
	public void testCannotAttackFromThisCountry() {
		Map.listOfAllTerritories=new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories=new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		//removed this becas its assigned in randim so giving only ione player and seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");//add neighbour and add player to owner of neighbour and finally create assignedterritories list since player is assighes
		t1.neighbours.get(0).owner=player;
		Player player2 = new Player("Player2");//assigning owner to neighbour
		t1.neighbours.get(1).owner=player2;
		//t1.owner=player;
		t1.numberOfArmies=1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		 //valid opponent if owner != player and neighbour
		assertFalse(player.publicCanAttackFromThisCountryForJUnitTest("Africa"));
	}
	
	/*@Test user interacted
	public void testAttack() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testFortification() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testExchangeCards() {
		fail("Not yet implemented");
	}

	@Test
	public void testValidCardIndexesToExchange() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanExchangeCards() {
		fail("Not yet implemented");
	}

	@Test
	public void testOwnedContinents() {
		fail("Not yet implemented");
	}

	@Test
	public void testObservable() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddObserver() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteObserver() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyObservers() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyObserversObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteObservers() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetChanged() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearChanged() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasChanged() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountObservers() {
		fail("Not yet implemented");
	}

}
