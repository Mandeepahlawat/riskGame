package Player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Card.*;
import Card.Card.CardType;

public class Player_Test {

	private Player player;

	@Before
	public void testBefore() {
		player = new Player("Player1");
		Main.players = new ArrayList<>();
	}

	@Test
	public void testGetName() {
		assertTrue(player.getName().equals("Player1"));
	}

	@Test
	public void testSetInitialArmyCount() {
		player.setInitialArmyCount(3);
		assertTrue(player.armiesLeft == 3 && player.totalArmiesCount == 3);
	}

	@Test
	public void testAssignedTerritoryNamesWithArmies() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		/*
		 * Main.players.add(player); Territory t1 = new Territory("Asia");
		 * Main.activeMap.territories.add(t1); Main.assignInitialTerritories();
		 * System.out.println("testATWA: "+player.assignedTerritoryNamesWithArmies()+" "
		 * +player.assignedTerritories);
		 */// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player2");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// player.assignedTerritories.get(0).numberOfArmies=5;
		System.out.println(player.assignedTerritoryNamesWithArmies());
		assertTrue(player.assignedTerritories.get(0).numberOfArmies == 5
				&& player.assignedTerritories.get(0).name.equals("Africa"));
	}

	@Test
	public void testPlaceArmiesAutomatically() {// adding only 1 player as its random function so we cant capture the
		// exact player name
		player.setInitialArmyCount(3);
		Main.players.add(player);
		/*
		 * player = new Player("Player2"); Main.players.add(player);
		 */
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// player.assignedTerritoryNamesWithArmies();
		player.placeArmiesAutomatically();
		// System.out.println(t1.numberOfArmies);
		assertTrue(t1.numberOfArmies == 8);// 5+3(since only one player all armies assinged here)
	}

	@Test
	public void testValidNeighborCountryOwned() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");
		t1.neighbours.get(0).owner = player;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertTrue(player.validNeighborCountry("Africa", "Jon"));
	}

	@Test
	public void testValidNeighborCountryNotOwned() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");
		t1.neighbours.get(0).owner = player;
		// t1.owner=player;// to add owner to neighbours add thru get list
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertFalse(player.validNeighborCountry("Africa", "tan"));
	}

	@Test
	public void testValidAssignedCountryOwned() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertTrue(player.validAssignedCountry("Africa"));
	}

	@Test
	public void testValidAssignedCountryNotOwned() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertFalse(player.validAssignedCountry("Asia"));
	}

	@Test
	public void testValidOpponentCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// valid opponent if owner != player and neighbour
		assertTrue(player.validOpponentCountry("Africa", "tan"));
	}

	@Test
	public void testInValidOpponentCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// valid opponent if owner != player and neighbour
		assertFalse(player.validOpponentCountry("Africa", "Jon"));
	}

	@Test
	public void testOpponentPlayer() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");
		t1.neighbours.get(1).owner = player2;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// valid opponent if owner != player and neighbour
		assertTrue(player.opponentPlayer("Africa", "tan").equals("Player2"));
	}

	@Test
	public void testCanAttackFromThisCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// valid opponent if owner != player and neighbour
		assertTrue(player.canAttackFromThisCountry("Africa"));
	}

	@Test
	public void testCannotAttackFromThisCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		// t1.owner=player;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// valid opponent if owner != player and neighbour
		assertFalse(player.canAttackFromThisCountry("Africa"));
	}

	/*
	 * @Test user interacted public void testAttack() { fail("Not yet implemented");
	 * }
	 */

	/*
	 * @Test public void testFortification() { fail("Not yet implemented"); }
	 */

	@Test
	public void testExchangeCards() {// check this after the change is pushed
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		System.out.println("before: " + player.cards.size());
		// player.exchangeCards(1,2,3);
		// System.out.println("After: "+player.cards.size());
		// valid opponent if owner != player and neighbour
		// assertFalse(player.publicCanAttackFromThisCountryForJUnitTest("Africa"));
	}

	@Test
	public void testCanExchangeCardsFalse() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		Card c1 = new Card(t1);
		player.cards.add(c1);
		System.out.println("testExchangeCards: " + player.canExchangeCards());
		assertFalse(player.canExchangeCards());
		// valid opponent if owner != player and neighbour
		// assertFalse(player.publicCanAttackFromThisCountryForJUnitTest("Africa"));
	}

	@Test
	public void testCanExchangeCardsWithCardSize5() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		Card c4 = new Card(t1);
		Card c5 = new Card(t1);
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		player.cards.add(c4);
		player.cards.add(c5);
		System.out.println("testExchangeCards: " + player.canExchangeCards());
		assertTrue(player.canExchangeCards());
		// valid opponent if owner != player and neighbour
		// assertFalse(player.publicCanAttackFromThisCountryForJUnitTest("Africa"));
	}

	@Test
	public void testCanExchangeCardsWithCardType3() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		c1.type = CardType.ARTILLERY;
		c2.type = CardType.CAVALRY;
		c3.type = CardType.INFANTRY;
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		System.out.println("testExchangeCards: " + player.canExchangeCards());
		assertTrue(player.canExchangeCards());
		// valid opponent if owner != player and neighbour
		// assertFalse(player.publicCanAttackFromThisCountryForJUnitTest("Africa"));
	}

	@Test
	public void testCanExchangeCardsWithSameCardType3() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		c1.type = CardType.ARTILLERY;
		c2.type = CardType.ARTILLERY;
		c3.type = CardType.INFANTRY;
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		// System.out.println("testExchangeCards: "+player.canExchangeCards());
		assertFalse(player.canExchangeCards());
	}

	@Test
	public void testValidCardIndexesToExchange() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		c1.type = CardType.ARTILLERY;
		c2.type = CardType.CAVALRY;
		c3.type = CardType.INFANTRY;
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		// System.out.println("before: "+player.cards.size());
		assertTrue(player.validCardIndexesToExchange(1, 2, 3));
	}

	@Test
	public void testInValidCardIndexesToExchange() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		// removed this becas its assigned in randim so giving only ione player and
		// seeing
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2;
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		c1.type = CardType.ARTILLERY;// invalid if two card of same type is exchanging
		c2.type = CardType.ARTILLERY;
		c3.type = CardType.INFANTRY;
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		// System.out.println("before: "+player.cards.size());
		assertFalse(player.validCardIndexesToExchange(1, 2, 3));
	}

	@Test
	public void testOwnedContinents() {
		Main.activeMap = new Map();
		Main.userEnteredContinentLines = new ArrayList<String>(
				Arrays.asList("North America=5", "Mexico=2", "Africa=3", "Asia=7", ""));
		Main.userEnteredTerritoryLines = new ArrayList<String>(Arrays.asList(
				"Japan,322,104,North America,Kamchatka,Mongolia", "Ural,241,68,Asia,Siberia,China,Afghanistan,Ukraine",
				"Arab,241,68,Asia,Siberia,China,Afghanistan,Ukraine"));
		Main.buildMap();
		System.out.println("Continent:" + Main.userEnteredContinentLines);
		System.out.println("conti" + Main.activeMap.continents);
		System.out.println("terri" + Main.activeMap.continents.get(0).territories.get(0).name);
		ArrayList<Map> maplist = player.ownedContinents();
		System.out.println(maplist.get(0).name+" "+maplist.get(1).name+" "+maplist.size());
		System.out.println(player.ownedContinents());
		assertTrue(maplist.get(0).name.equals("Mexico"));//is it a random assignment of owner?
	}

	 

}
