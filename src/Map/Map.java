package Map;

import java.util.ArrayList;

/**
* This class is used to create a full Map,
* a map has a list of continents and territories.
* A continent is just a sub Map which is part of the overall
* map.
* 
* @author Mandeep Ahlawat
* @version 1.0
* @since   2018-09-30 
*/
public class Map {
	public ArrayList<Map> continents; // this will be used for the main map
	public ArrayList<Territory> territories; // this will be used for continents
	public int score;
	public String name;
	public Map parentMap;
	public static ArrayList<Territory> listOfAllTerritories;
	public static ArrayList<Map> listOfAllContinents;
	
	/**
	* This method is used to find a Territory.
	* 
	* @param territoryName A String value of the territory name
	* @return Territory if territory is found then returns territory
	* otherwise returns null
	*/
	public static Territory findTerritory(String territoryName) {
		for(Territory territory : listOfAllTerritories) {
			if(territory.name.equals(territoryName)) {
				return territory;
			}
		}
		return null;
	}
	
	/**
	* This method is used to find a Continent.
	* 
	* @param continentName A String value of the continent name
	* @return Continent if continent is found then returns continent
	* otherwise returns null
	*/
	public static Map findContinent(String continentName) {
		for(Map continent : listOfAllContinents) {
			if(continent.name.equals(continentName)) {
				return continent;
			}
		}
		return null;
	}
	
	/**
	* This is the default constructor of the Map class and will be used to
	* create the main game play map
	*/
	public Map() {
		this.territories = new ArrayList<Territory>();
		this.continents = new ArrayList<Map>();
		this.parentMap = null; // as this is the parent map
		listOfAllTerritories = new ArrayList<Territory>();
		listOfAllContinents = new ArrayList<Map>();
	}
	
	/**
	* This method is a constructor of the Map class which will be
	* mainly used to create a continent inside a map
	* 
	* @param continentName A String value of the continent name
	* @param continentScore A integer value of the continent score
	*/
	public Map(String continentName, int continentScore) {
		this.name = continentName;
		this.score = continentScore;
		
		this.territories = new ArrayList<Territory>();
		this.continents = null; // a continent can't have continents inside it.
		
		listOfAllContinents.add(this);
	}
	
	/**
	* This method is used to add a continent to the Map.
	* 
	* @param continentName A String value of the continent name
	* @param continentScore A integer value of the continent score
	* @return true if the continent is not already created, otherwise returns
	* false as a continent with the same already exists
	*/
	public boolean addContinent(String continentName, int continentScore) {
		if(findContinent(continentName) == null) {
			Map continent = new Map(continentName, continentScore);
			continent.parentMap = this;
			this.continents.add(continent);
			return true;
		}
		return false;
	}
	
	/**
	* This method is used to build the final version of the Map
	* by joining all the continents.
	*/
	public void connectContinents() {
		this.territories.addAll(listOfAllTerritories);
	}
	
	/**
	* This inner class is used to create a Map.
	* It adds the behavior of territories to the map,
	* where each territory has a name, belongs to a continent
	* and has a list of neighbours
	* 
	*/
	public static class Territory {
		public String name;
		public Map continent;
		public ArrayList<Territory> neighbours;
		
		/**
		* This method is the constructor of the Territory class
		* 
		* @param name A String value of the territory name
		*/
		public Territory(String name) {
			this.name = name;
			this.continent = null;
			this.neighbours = new ArrayList<Territory>();
			listOfAllTerritories.add(this);
		}
		
		/**
		* This method is used to add neigbours of a territory.
		* 
		* @param neighbourList A String value where each neigbouring
		* territory is separated by a comma (',')
		*/
		public void addNeighbours(String neighbourList) {
			String neigboursNameArray[] = neighbourList.split(",");
			for(String neighbourName : neigboursNameArray) {
				Territory territory = null;
				if((territory = findTerritory(neighbourName)) != null) {
					this.neighbours.add(territory);
				}
				else {
					territory = new Territory(neighbourName);
					this.neighbours.add(territory);
				}
			}
		}
		
		/**
		* This method is used to assign continent to a territory.
		* 
		* @param continentName A String value of the continent name
		* @return true if the continent is assigned otherwise returns
		* false
		*/
		public boolean assignContinent(String continentName) {
			if(this.continent == null) {
				Map continent = null;
				if((continent = findContinent(continentName)) != null) {
					this.continent = continent;
					continent.territories.add(this);
					return true;
				}
			}
			return false;
		}
	}
}
