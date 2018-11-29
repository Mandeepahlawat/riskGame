package Player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import Driver.Main;
import Map.Map;
import Map.Map.Territory;
/**
 * This is the JUnit Test cases for Aggressive player class. this implements all
 * the test related to the units within this class.
 *
 * @author  Arun
 * @version 1.0
 * @since   2018-11-28 
 */ 
public class Aggressive_Test {

	private Player player;
	/**
	 * test class testBefore that runs 
	 * before each methods  within this class
	 */
	@Before
	public void testBefore() {
		player = new Player("Player1");
		Main.players = new ArrayList<>();
		Main.cards=new ArrayList<>();
	}
	/**
	 * test class testCalculateReinforcementArmiesContinentCapture
	 * this test the calculateReinforcementArmies method
	 * condition if continent capture
	 */
	@Test
	public void testCalculateReinforcementArmiesContinentCapture() {
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
		Main.activeMap.continents.get(0).territories.get(0).owner=player;
		Main.activeMap.continents.get(1).territories.add(new Territory("India"));
		Main.activeMap.continents.get(2).territories.add(new Territory("pakistan"));
		Aggressive playerStrategy = new Aggressive(player);
		
		//Main.activeMap.continents.get(0).territories.get(0).
		System.out.println(playerStrategy.calculateReinforcementArmies());
		assertTrue(playerStrategy.calculateReinforcementArmies()==8); //change to handle the size 0 allotment (2+3=5 is added)
	}
	
	/**
	 * test class testCalculateReinforcementArmiesContinentCapture
	 * this test the calculateReinforcementArmies method
	 * condition if no continent capture
	 */
	@Test
	public void testCalculateReinforcementArmiesNoContinentCapture() {
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
		Main.activeMap.continents.get(1).territories.add(new Territory("India"));
		Main.activeMap.continents.get(2).territories.add(new Territory("pakistan"));
		//Main.activeMap.continents.get(0).territories.get(0).owner=player;
		Aggressive playerStrategy = new Aggressive(player);
		
		//Main.activeMap.continents.get(0).territories.get(0).
		System.out.println(playerStrategy.calculateReinforcementArmies());
		assertTrue(playerStrategy.calculateReinforcementArmies()==3); //change to handle the size 0 allotment (2+3=5 is added)
	}
	
	/**
	 * test class testPlaceReinforcements
	 * this test the placeReinforcements method 
	 */
	@Test
	public void testPlaceReinforcements() {
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.numberOfArmies=10;
		t2.owner=player;
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.add(t2);
		
		Aggressive playerStrategy = new Aggressive(player);
		playerStrategy.placeReinforcements(3);
		System.out.println(player.assignedTerritories.get(0).numberOfArmies);
		assertTrue(player.assignedTerritories.get(1).numberOfArmies==13); // altready 5 and now added 3
	}
		
	/**
	 * test class testFortification
	 * this test the random fortification method 
	 */
	@Test
	public void testFortification() {
		 
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.numberOfArmies=10;
		t2.owner=player;
		t1=new Territory("India"); t1.owner=player;
		t2.neighbours.add(t1);
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.add(t2);
		
		Aggressive playerStrategy = new Aggressive(player);
		
		System.out.println("start");
		int armiesBeforeMoveT1 = t1.numberOfArmies;
		int armiesBeforeMoveT2 = t2.numberOfArmies;
		playerStrategy.fortification();
		int armiesAfterMoveT1 = t1.numberOfArmies;
		int armiesAfterMoveT2 = t2.numberOfArmies;
		assertTrue((armiesBeforeMoveT1+armiesBeforeMoveT2)==(armiesAfterMoveT1+armiesAfterMoveT2));
	}
	  

	/**
	 * Test the Fortification method without own neighbor
	 */
	@Test
	public void testFortificationWithoutNeighbor() {
		 
		Territory t1 = new Territory("Africa");
		t1.numberOfArmies=5;
		t1.owner=player;
		
		Territory t2 = new Territory("Asia");
		t2.numberOfArmies=10;
		t2.owner=player;  
		
		player.assignedTerritories.add(t1);
		player.assignedTerritories.add(t2);
		
		Aggressive playerStrategy = new Aggressive(player);
		
		System.out.println("start");
		int armiesBeforeMoveT1 = t1.numberOfArmies;
		int armiesBeforeMoveT2 = t2.numberOfArmies;
		playerStrategy.fortification();
		int armiesAfterMoveT1 = t1.numberOfArmies;
		int armiesAfterMoveT2 = t2.numberOfArmies;
		assertTrue((armiesBeforeMoveT1+armiesBeforeMoveT2)==(armiesAfterMoveT1+armiesAfterMoveT2));
	}
	
}
