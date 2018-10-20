package GamePlay;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import GamePlay.*;
import Map.Map;
import Map.Map.Territory;

public class StartupPhase_Test {

	private StartupPhase startup;
	private Map map;
	private Territory territories;
	/**
	 * setup the test environmental variable for the junit test execution
	 */
	@Before
	public void testBefore(){
		Map.listOfAllTerritories = new ArrayList<>();
		Map.listOfAllContinents = new ArrayList<>();
	//	Territory.jUnitTestOn=true;
		startup = new StartupPhase(3);
		territories = new Territory("Japan");
	//	StartupPhase.jUnitTestOn=true;
		
	}  

	/**
	 * test the assign initial territory method in startup phase
	 */
	@Test
	public void testAssignInitialTerritories() {
		startup.assignInitialTerritories(); 
		assertTrue(territories.playerId<=3);
	}
	/**
	 * test the assign initial armies method in startup phase incase for 3 players
	 */
	@Test
	public void testAssignInitialArmiesFor3Players() {
		startup.assignInitialArmies();
		assertTrue(startup.jUnitTestReturnInitialArmies()==35 && startup.jUnitTestReturntotalInitialArmies()==105);
	}

	/**
	 * test the assign initial armies method in startup phase incase for 2 players
	 */
	@Test
	public void testAssignInitialArmiesFor2Players() {
		startup = new StartupPhase(2);
		startup.assignInitialArmies();
		assertTrue(startup.jUnitTestReturnInitialArmies()==40 && startup.jUnitTestReturntotalInitialArmies()==80);
	}
	/**
	 * test the assign initial armies method in startup phase incase for 4 players
	 */
	@Test
	public void testAssignInitialArmiesFor4Players() {
		startup = new StartupPhase(4);
		startup.assignInitialArmies(); 
		assertTrue(startup.jUnitTestReturnInitialArmies()==30 && startup.jUnitTestReturntotalInitialArmies()==120);
	}
	/**
	 * test the assign initial armies method in startup phase incase for 5 players
	 */
	@Test
	public void testAssignInitialArmiesFor5Players() {
		startup = new StartupPhase(5);
		startup.assignInitialArmies(); 
		assertTrue(startup.jUnitTestReturnInitialArmies()==25 && startup.jUnitTestReturntotalInitialArmies()==125);
	}
	/**
	 * test the assign initial armies method in startup phase incase for 6 players
	 */
	@Test
	public void testAssignInitialArmiesFor6Players() {
		startup = new StartupPhase(6);
		startup.assignInitialArmies();
		assertTrue(startup.jUnitTestReturnInitialArmies()==20 && startup.jUnitTestReturntotalInitialArmies()==120);
	}
	
	/* 
	//User interface
	@Test
	public void testPlaceArmies() {
		fail("Not yet implemented");
	}*/

}
