package Player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import Driver.Main;
import Map.Map;
import Map.Map.Territory;
import Card.*;
import Card.Card.CardType;
/**
 * This is the JUnit Test cases for Player class. this implements all
 * the test related to the units within this class.
 *
 * @author Arun
 * @version 1.0
 * @since   2018-10-28 
 */ 
public class Player_Test {

	private Player player;
	/**
	* This method runs before each test case functions
	* 
	*/
	@Before
	public void testBefore() {
		player = new Player("Player1");
		Main.players = new ArrayList<>();
		Main.cards=new ArrayList<>();
	}

	/**
	* This method is used to test function that return name of player
	* 
	* return name of the player 
	*/
	@Test
	public void testGetName() {
		assertTrue(player.getName().equals("Player1"));
	}
	/**
	* This method is used to test function that sets the initial army
	* count
	* 
	* this is a void return so we assert the variable being set 
	*/
	@Test
	public void testSetInitialArmyCount() {
		player.setInitialArmyCount(3);
		assertTrue(player.armiesLeft == 3 && player.totalArmiesCount == 3);
	}
	/**
	* This method is used to test function that assigns territory name with armies
	* count
	* 
	* this is a void return so we assert the variable being set 
	*/
	@Test
	public void testAssignedTerritoryNamesWithArmies() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		player = new Player("Player2");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories(); 
		System.out.println(player.assignedTerritoryNamesWithArmies());
		assertTrue(player.assignedTerritories.get(0).numberOfArmies == 5
				&& player.assignedTerritories.get(0).name.equals("Africa"));
	}
	/**
	* This method is used to test function that assigns territory 
	* name with armies automatically
	* 
	* this is a void return so we assert the variable being set 
	*/
	@Test
	public void testPlaceArmiesAutomatically() { 
		player.setInitialArmyCount(3);
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();
		// player.assignedTerritoryNamesWithArmies();
		player.placeArmiesAutomatically();
		// System.out.println(t1.numberOfArmies);
		assertTrue(t1.numberOfArmies == 8);// 5+3(since only one player all armies assinged here)
	}
	/**
	* This method is used to test function that validates the 
	* owned neighbour countries
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testValidNeighborCountryOwned() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan");
		t1.neighbours.get(0).owner = player; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertTrue(player.validNeighborCountry("Africa", "India"));
	}
	/**
	* This method is used to test function that validates the 
	* Neighbor countries that is not owned
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testValidNeighborCountryNotOwned() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan");
		t1.neighbours.get(0).owner = player; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertFalse(player.validNeighborCountry("Africa", "Pakistan"));
	}
	/**
	* This method is used to test function that validates the 
	* country assigned
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testValidAssignedCountryOwned() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
		t1.neighbours.get(0).owner = player; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertTrue(player.validAssignedCountry("Africa"));
	}
	/**
	* This method is used to test function that validates the 
	* country not assigned
	* 
	* return True if result is valid
	* 
	* return False if result is invalid 
	*/
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
		t1.addNeighbours("India,Pakistan");// add neighbour and add player to owner of neighbour and finally create
		// assignedterritories list since player is assighes
		t1.neighbours.get(0).owner = player;
		// t1.owner=player;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories();

		assertFalse(player.validAssignedCountry("Asia"));
	}
	/**
	* This method is used to test function that validates the 
	* opponent countries
	* 
	* return True if result is valid
	* 
	* return False if result is invalid 
	*/
	@Test
	public void testValidOpponentCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
		t1.neighbours.get(0).owner = player; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories(); 
		assertTrue(player.validOpponentCountry("Africa", "Pakistan"));
	}
	/**
	* This method is used to test function that validates the 
	* countries that are not opponent
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testInValidOpponentCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
		t1.neighbours.get(0).owner = player; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories(); 
		assertFalse(player.validOpponentCountry("Africa", "India"));
	}
	/**
	* This method is used to test function that returns the opponent player
	* 
	* return String name of the player 
	*/
	@Test
	public void testOpponentPlayer() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");
		t1.neighbours.get(1).owner = player2; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories(); 
		assertTrue(player.opponentPlayer("Africa", "Pakistan").equals("Player2"));
	}
	/**
	* This method is used to test function that validates the 
	* if the attack can be initiated from a country passed as parm
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testCanAttackFromThisCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories(); 
		assertTrue(player.canAttackFromThisCountry("Africa"));
	}
	/**
	* This method is used to test function that validates the 
	* if the attack can be initiated from a country passed as parm
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testCannotAttackFromThisCountry() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
		t1.neighbours.get(0).owner = player;
		Player player2 = new Player("Player2");// assigning owner to neighbour
		t1.neighbours.get(1).owner = player2; 
		t1.numberOfArmies = 1;
		Main.activeMap.territories.add(t1);
		Main.assignInitialTerritories(); 
		assertFalse(player.canAttackFromThisCountry("Africa"));
	}
	/**
	* This method is used to test function that exchanges the card
	* 
	* this is a void return so we test by getting the value of variable being set
	*/
	@Test
	public void testExchangeCards() {// check this after the change is pushed
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("Jon,tan"); 
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
		int beforeSize = player.cards.size();
		player.exchangeCards(0,1,2);
		int afterSize = player.cards.size();
		System.out.println("After: "+player.cards.size());
		assertTrue(beforeSize==3&&afterSize==0); 
	}
	/**
	* This method is used to test function that checks if 
	* card could be exchanged
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testCanExchangeCardsFalse() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
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
	}

	/**
	* This method is used to test function that checks if 
	* card could be exchanged when number of card is 5
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testCanExchangeCardsWithCardSize5() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
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
	}

	/**
	* This method is used to test function that checks if 
	* card could be exchanged when number of different card type is 3
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testCanExchangeCardsWithCardType3() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
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
	}

	/**
	* This method is used to test function that checks if 
	* card could be exchanged when number of same card type is 3
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testCanExchangeCardsWithSameCardType3() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>();
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan");// add neighbour and add player to owner of neighbour and finally create
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
	/**
	* This method is used to test function that checks if 
	* card indexes are valid
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testValidCardIndexesToExchange() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
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
		assertTrue(player.validCardIndexesToExchange(0,1,2));
	}

	/**
	* This method is used to test function that checks if 
	* card indexes are invalid
	* 
	* return True if result is valid
	* 
	* return False if result is invalid
	*/
	@Test
	public void testInValidCardIndexesToExchange() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		player = new Player("Player1");
		Main.players.add(player);
		Territory t1 = new Territory("Africa");
		t1.addNeighbours("India,Pakistan"); 
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
		assertFalse(player.validCardIndexesToExchange(0,1,2));
	}
	/**
	* This method is used to test function that returns the list of continents owned
	* 
	* return Arraylist of type Map list of continents
	* 
	* return False if result is invalid
	*/
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
	/**
	* This method is used to test function that returns the list of continents owned
	* 
	* return True if result is invalid
	*/
	@Test
	public void testcheckDefenderArmiesNumberZero() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		
		player = new Player("Player2");
		Main.players.add(player);
		Territory t1 = new Territory("Asia");
		t1.addNeighbours("India,Pakistan"); 
		t1.neighbours.get(0).numberOfArmies = 0; 
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		
		Main.assignInitialTerritories();
		assertTrue(player.checkDefenderArmiesNumberZero("Asia", "India"));
	}
	/**
	* This method is used to test function that returns the list of continents owned
	* 
	* return True if result is invalid
	*/
	@Test
	public void testcheckDefenderArmiesNumberNotZero() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		
		player = new Player("Player2");
		Main.players.add(player);
		Territory t1 = new Territory("Asia");
		t1.addNeighbours("India,Pakistan");  
		t1.neighbours.get(0).numberOfArmies=4;
		t1.numberOfArmies = 5;
		Main.activeMap.territories.add(t1);
		
		Main.assignInitialTerritories();
		assertFalse(player.checkDefenderArmiesNumberZero("Asia", "India"));
	}
	/**
	* This method is used to test function that returns dice values
	* 
	*/
	@Test
	public void testRollDice() {
		Map.listOfAllTerritories = new ArrayList<Territory>();
		Main.activeMap = new Map();
		Main.activeMap.territories = new ArrayList<Territory>();
		Main.players = new ArrayList<Player>(); 
		
		player = new Player("Player2");
		Main.players.add(player);
		Territory t1 = new Territory("Asia");
		System.out.println("roll"+player.rollDice(2));
		assertNotNull(player.rollDice(2));
	}
	
	/**
	 * method testGetRandomTerritory
	 * 
	 * to test getRandomTerritory method in the Player class
	 */
	@Test
	public void testGetRandomTerritory() {
		Territory t1 = new Territory("Africa");
		player.assignedTerritories.add(t1);
		assertTrue(player.getRandomTerritory(player.assignedTerritories).name.equals("Africa"));//since randome its tested using single value
	}
	
	/**
	 * this method test the return of attacking player name
	 */
	@Test
	public void testAttackingPlayer() {
		Territory t1 = new Territory("Africa");
		t1.owner=player;
		Territory t2 = new Territory("Asia");
		t2.owner=new Player("Player2");
		player.assignedTerritories.add(t1);
		player.assignedTerritories.add(t2);
		assertTrue(player.attackingPlayer("Africa", "Asia").equals("Player1"));
	}
	
	/**
	 * This method test the reduce army method for a player
	 * for an attacker
	 */
	@Test
	public void testReduceArmyForAttacker() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.owner=new Player("Player2");
		t2.numberOfArmies=5;
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).neighbours.add(t2);
		System.out.println("Before reduce army "+ t1.numberOfArmies);
		player.reduceArmy("attacker", "Africa", "Asia");
		assertTrue(t1.numberOfArmies==4 && t2.numberOfArmies==5);//reduced by 1
	}
	
	/**
	 * This method test the reduce army method for a player
	 * for a Defender
	 */
	@Test
	public void testReduceArmyForDefender() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.owner=new Player("Player2");
		t2.numberOfArmies=5;
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).neighbours.add(t2);
		System.out.println("Before reduce army "+ t1.numberOfArmies);
		player.reduceArmy("defender", "Africa", "Asia");
		assertTrue(t1.numberOfArmies==5 && t2.numberOfArmies==4);//reduced by 1
	}
	
	/**
	 * this method is to test the number of armies 
	 * within attacker and defender countries
	 */
	@Test
	public void testReturnArmiesLeft() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.owner=new Player("Player2");
		t2.numberOfArmies=10;
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).neighbours.add(t2);
		
		Vector<Integer> armies=player.returnArmiesLeft("Africa", "Asia");
		assertTrue(armies.get(0).equals(5) && armies.get(1).equals(10)); //0 is attacker 1 is defender
	}
	
	/**
	 * this method is to test the method 
	 * that return territory with max army 
	 */
	@Test
	public void testTerritoryWithMaxArmy() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.numberOfArmies=10;
		t2.owner=player;
		
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.add(t2);
		
		Territory maxTerritory = player.territoryWithMaxArmy(player.assignedTerritories);
		assertTrue(maxTerritory.name.equals("Asia")); //0 is attacker 1 is defender
	}
	
	/**
	 * this method is to test the method 
	 * that return territory with minimum army 
	 */
	@Test
	public void testTerritoryWithMinArmy() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.numberOfArmies=10;
		t2.owner=player;
		
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.add(t2);
		
		Territory maxTerritory = player.territoryWithMinArmy(player.assignedTerritories);
		assertTrue(maxTerritory.name.equals("Africa")); //0 is attacker 1 is defender
	}
	
	/**
	 * this method is to test the method 
	 * that return territory having neighbor owner by other player
	 */
	@Test
	public void testGetTerritoriesWithNeighboursToOthers() {
		boolean territoryFound=false;
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.numberOfArmies=10;
		t2.owner=new Player("Player2");
		
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).neighbours.add(t2);
		
		
		t1 = new Territory("India");
		t1.owner=player;
		t2 = new Territory("Pakistan");
		t2.owner=player;
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).neighbours.add(t2);
		
		HashSet<Territory> territoryNeighborOther = player.getTerritoriesWithNeighboursToOthers(); 
		Iterator<Territory> it = territoryNeighborOther.iterator();
		while(it.hasNext()) {
			if(it.next().name.equals("Africa")) {
				territoryFound=true;
			}
		}
		assertTrue(territoryFound); //0 is attacker 1 is defender
	}
	
	/**
	 * this method is to test the method 
	 * that return empty hashset when territory dont have neighbor with other player
	 */
	@Test
	public void testFailGetTerritoriesWithNeighboursToOthers() { 
		
		Territory t1 = new Territory("India");
		t1.owner=player;
		Territory t2 = new Territory("Pakistan");
		t2.owner=player;
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).neighbours.add(t2);
		
		HashSet<Territory> territoryNeighborOther = player.getTerritoriesWithNeighboursToOthers(); 
		
		assertTrue(territoryNeighborOther.isEmpty()); //0 is attacker 1 is defender
	}
	
	/**
	 * this method test neighbor territories
	 */
	@Test
	public void testGetTerritoriesWithNeighboursToOthersSpecific() {
		boolean territoryFound=false;
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.numberOfArmies=10;
		t2.owner=new Player("Player2");
		
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).neighbours.add(t2);
		
		Territory searchTerritory = t1;
		
		t1 = new Territory("India");
		t1.owner=player;
		t2 = new Territory("Pakistan");
		t2.owner=player;
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(1).neighbours.add(t2);
		
		HashSet<Territory> territoryNeighborOther = player.getTerritoriesWithNeighboursToOthers(searchTerritory); 
		Iterator<Territory> it = territoryNeighborOther.iterator();
		while(it.hasNext()) {
			if(it.next().name.equals("Asia")) {
				territoryFound=true;
			}
		}
		assertTrue(territoryFound); //0 is attacker 1 is defender
	}
	
	/**
	 * this method tests the newly added territory owner
	 */
	@Test
	public void testAddNewOwnedTerritoryOwner() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		player.totalArmiesCount=10;
		Player player2 = new Player("Player2");
		player2.totalArmiesCount=10;
		player2.addNewOwnedTerritory(t1);
		System.out.println(t1.numberOfArmies+" "+t1.owner.getName());
		assertEquals("Player2", t1.owner.getName());
	}
	/**
	 *
	 * this method tests the newly added armies to new territory owner
	 *
	 */
	@Test
	public void testAddNewOwnedTerritoryTotalArmies() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		player.totalArmiesCount=10;
		Player player2 = new Player("Player2");
		player2.totalArmiesCount=10;
		player2.addNewOwnedTerritory(t1);
		System.out.println(t1.numberOfArmies+" "+t1.owner.getName());
		assertEquals(15, player2.totalArmiesCount);
	}
	
	/**
	 * this method test the eliminated players card being empty
	 */
	@Test
	public void testGotEliminated() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		player.totalArmiesCount=10;
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		c1.type = CardType.ARTILLERY;// invalid if two card of same type is exchanging
		c2.type = CardType.ARTILLERY;
		c3.type = CardType.INFANTRY;
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		Player player2 = new Player("Player2");
		player2.totalArmiesCount=10;
		player.gotEliminated(player2);
		assertTrue(player.cards.isEmpty());
	}
	/**
	 * this method test card indexeds to be exchanged
	 */
	@Test
	public void testGetCardIndexesToExchangeWith3Cards() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		player.totalArmiesCount=10;
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		c1.type = CardType.ARTILLERY;// invalid if two card of same type is exchanging
		c2.type = CardType.ARTILLERY;
		c3.type = CardType.INFANTRY;
		player.cards.add(c1);
		player.cards.add(c2);
		player.cards.add(c3);
		 
		ArrayList<Integer> indexes = player.getCardIndexesToExchange();
		assertTrue(indexes.equals(new ArrayList<Integer>(Arrays.asList(0,1,2))));
	}
	/**
	 * this method test card indexeds to be exchanged
	 */
	@Test
	public void testGetCardIndexesToExchangeWithout3Cards() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		player.totalArmiesCount=10;
		Card c1 = new Card(t1);
		Card c2 = new Card(t1);
		Card c3 = new Card(t1);
		c1.type = CardType.ARTILLERY;// invalid if two card of same type is exchanging 
		c3.type = CardType.INFANTRY;
		player.cards.add(c1); 
		player.cards.add(c3); 
		 
		ArrayList<Integer> indexes = player.getCardIndexesToExchange();
		assertTrue(indexes.equals(new ArrayList<Integer>(Arrays.asList(0,1))));
	}
}
