package Player;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Driver.Main;
import Map.Map;
import Map.Map.Territory;

public class RandomStrategy_Test {
	private Player player;
	
	@Before
	public void testBefore() {
		player = new Player("Player1");
		Main.players = new ArrayList<>();
	}
	
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
		Strategy playerStrategy = new RandomStrategy(player);
		
		//Main.activeMap.continents.get(0).territories.get(0).
		System.out.println(playerStrategy.calculateReinforcementArmies());
		assertTrue(playerStrategy.calculateReinforcementArmies()==8); //change to handle the size 0 allotment (2+3=5 is added)
	}
	
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
		//Main.activeMap.continents.get(0).territories.get(0).owner=player;
		Strategy playerStrategy = new RandomStrategy(player);
		
		//Main.activeMap.continents.get(0).territories.get(0).
		System.out.println(playerStrategy.calculateReinforcementArmies());
		assertTrue(playerStrategy.calculateReinforcementArmies()==3); //change to handle the size 0 allotment (2+3=5 is added)
	}
	
	@Test
	public void testPlaceReinforcements() {
		Territory t1 = new Territory("Africa");
		player.assignedTerritories.add(t1);
		player.assignedTerritories.get(0).numberOfArmies=5;
		Strategy playerStrategy = new RandomStrategy(player);
		playerStrategy.placeReinforcements(3);
		System.out.println(player.assignedTerritories.get(0).numberOfArmies);
		assertTrue(player.assignedTerritories.get(0).numberOfArmies==8); // altready 5 and now added 3
	}
		
}
