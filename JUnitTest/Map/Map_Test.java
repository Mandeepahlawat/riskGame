package Map;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Map.Map.*;

 
public class Map_Test {

	private Territory actualTerritory;
	private Map map;
	private Map mapForContinent;
	/**
	 * The testBefore is setting up of predetermined values as part of the Junit testcase
	 * 
	 */
	@Before
	public void testBefore() {
		Map.listOfAllTerritories = new ArrayList<>();
		Map.listOfAllContinents = new ArrayList<>();
		actualTerritory =new Territory("Japan");
		map=new Map();
		map.addContinent("North America",5);
		mapForContinent=new Map("Africa", 13);
	}
	/**
	* This method is used to test the Map.findTerritory operation
	*
	* @return the instance of the territory found 
	*/
	@Test
	public void testFindTerritory() { 
		//actualTerritory=Map.findTerritory("Japan"); //Territory constructor is already adding it
		assertEquals("Japan", actualTerritory.name);
	}
	/**
	* This method is used to test the Map.findTerritory operation
	*
	* @return the null as its instance is not found 
	*/
	@Test
	public void testFindTerritoryNull() { 
		actualTerritory=Map.findTerritory("India");
		assertNull(actualTerritory);
	}
	/**
	* This method is used to test the Map.findContinent operation
	*
	* @return the instance of the Continent found 
	*/
	@Test
	public void testFindContinent() { 
		Map mapContinent=Map.findContinent("North America"); 
		assertEquals("North America", mapContinent.name);
	}
	/**
	* This method is used to test the Map.findContinent operation
	*
	* @return null if instance of the Continent not found 
	*/
	@Test
	public void testFindContinentAsNull() {   
		assertNull(Map.findContinent("Asia"));
	}
	
	/*@Test
	public void testMap() {
		fail("Not yet implemented");
	}*/
	/**
	* This is a Map constructor that accept the parm Continent name and the score and list it in the continent Arraylist to be connected
	* it tests if the returned Continent name equated the expected value
	* 
	* @return the instance of newly created continent and score 
	*/
	@Test
	public void testMapStringIntNewContinentName() {
		assertEquals("Africa", mapForContinent.name);
	}

	/**
	* This is a Map constructor that accept the parm Continent name and the score and list it in the continent Arraylist to be connected
	* it tests if the returned Continent score equated the expected value
	* 
	* @return the instance of newly created continent and score 
	*/
	@Test
	public void testMapStringIntNewContinentScore() {
		assertEquals(13, mapForContinent.score);
	}
	/**
	* This is to add the continent with its score. since we have set this value in the @before its not re initiated here again
	* it tests if the returned Continent name equated the expected value
	* 
	* @return the instance of newly created continent and score 
	*/
	@Test
	public void testAddContinentName() {
		assertEquals("North America", map.continents.get(0).name);
	}
	/**
	* This is to add the continent with its score. since we have set this value in the @before its not re initiated here again
	* it tests if the returned Continent score equated the expected value
	* 
	* @return the instance of newly created continent and score 
	*/
	@Test
	public void testAddContinentScore() {
		//map.addContinent("North America",5);
		assertEquals(5, map.continents.get(0).score);
	}
	
	/*@Test
	public void testConnectContinents() {
		fail("Not yet implemented");
	}*/

}
