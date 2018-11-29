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
 * This is the JUnit Test cases for Human player class. this implements all
 * the test related to the units within this class.
 *
 * @author  Arun
 * @version 1.0
 * @since   2018-11-28 
 */ 
public class Human_Test {

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
		Human playerStrategy = new Human(player);
		
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
		Human playerStrategy = new Human(player);
		
		//Main.activeMap.continents.get(0).territories.get(0).
		System.out.println(playerStrategy.calculateReinforcementArmies());
		assertTrue(playerStrategy.calculateReinforcementArmies()==3); //change to handle the size 0 allotment (2+3=5 is added)
	}
	
	 
}
