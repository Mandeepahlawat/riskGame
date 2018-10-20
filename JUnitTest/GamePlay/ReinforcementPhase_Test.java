package GamePlay;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import GamePlay.*;
import Map.Map;
import Map.Map.Territory;

public class ReinforcementPhase_Test {
 
	private Map map;
	private Territory territories;
	private ReinforcementPhase reinforcement;
	  
	/**
	 * testBefore - setsup all the environment variables for the test execution
	 */
	@Before
	public void testBefore(){
		Map.listOfAllTerritories = new ArrayList<>();
		Map.listOfAllContinents = new ArrayList<>();
		//Territory.jUnitTestOn=true;
		map = new Map("Asia",4);
		reinforcement=new ReinforcementPhase(); 
	}
	
	/**
	 * calculate the reinforcement armies if the continent is captured
	 */
	@Test
	public void testCalculateReinforcementArmiesForContinentCapture() { 
		territories = new Territory("Japan"); territories.playerId=3; map.territories.add(territories); 
		territories = new Territory("Canada"); territories.playerId=3; map.territories.add(territories); 
		territories = new Territory("India"); territories.playerId=3; map.territories.add(territories); 
		map.territories.add(territories); 
		assertTrue(reinforcement.calculateReinforcementArmies(3)==7); 
	}
	
	/**
	 * calculate the reinforcement armies if the territory is captured
	 */
	@Test
	public void testCalculateReinforcementArmiesForTerritoryCapture() { 
		territories = new Territory("Japan"); territories.playerId=4; map.territories.add(territories); 
		territories = new Territory("Canada"); territories.playerId=3; map.territories.add(territories); 
		territories = new Territory("India"); territories.playerId=3; map.territories.add(territories); 
		map.territories.add(territories);  
		assertTrue(reinforcement.calculateReinforcementArmies(3)==3); 
	}
	/**
	 * calculate the reinforcement armies if no territory is captured
	 */
	@Test
	public void testCalculateReinforcementArmiesForNoCapture() { 
		territories = new Territory("Japan"); territories.playerId=4; map.territories.add(territories); 
		territories = new Territory("Canada"); territories.playerId=3; map.territories.add(territories); 
		territories = new Territory("India"); territories.playerId=3; map.territories.add(territories); 
		map.territories.add(territories);  
		assertTrue(reinforcement.calculateReinforcementArmies(1)==3); 
	}

	/*@Test
	public void testPlaceReinforcements() {
		fail("Not yet implemented");
	}*/

}

