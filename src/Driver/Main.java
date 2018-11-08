package Driver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.nio.file.Path;

import Map.Map;
import Map.Map.Territory;
import Player.Player;
import Player.Player.GamePhase;
import Views.MainView;
import Views.PhaseView;
import Views.WorldDominationView;

/**
* This class is the main driver class which
* interacts with the user and drives the program
* according to users input 
* 
* @author Mandeep Ahlawat
* @version 1.0
* @since   2018-09-27 
*/
public class Main {
	
	public static Map activeMap;
	public static ArrayList<String> userEnteredContinentLines;
	public static ArrayList<String> userEnteredTerritoryLines;
	public static ArrayList<Player> players;
	public static int totalInitialArmies;
	public static MainView mainView;
	
	/**
	* This method is used to validate the new map line
	* entered by the user.
	* 
	* @param isContinent A boolean value to denote if we
	* are editing a continent, if false then it means we are
	* editing a territory
	* 
	* @param newLine A String entered by the user to replace the old
	* line in map
	* 
	* @return True if new line is valid otherwise False
	*/
	public static boolean validateMapLine(boolean isContinent, String newLine) {
		if(isContinent) {
			String continentParts[] = newLine.split("=");
			if(continentParts.length != 2) {
				return false;
			}
			else {
				if(!continentParts[1].trim().matches("-?\\d+(\\.\\d+)?")) {
					return false;
				}
			}
		}
		else {
			String territoryParts[] = newLine.split(",");
			if(territoryParts.length < 5 ) {
				return false;
			}
			else {
				if(!territoryParts[1].trim().matches("-?\\d+(\\.\\d+)?") || !territoryParts[2].trim().matches("-?\\d+(\\.\\d+)?")) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	* This method lets user to build
	* a new map and then displays the full map to the user
	* on the screen
	* 
	*/
	public static void createNewMap() {
		String newMapText = "";
		newMapText += "[Map]\n"
				+ "image=world.bmp\n"
				+ "wrap=yes\n"
				+ "scroll=horizontal\n"
				+ "author=Your Name\n"
				+ "warn=yes";
		newMapText += "\n\n[Continents]\n" + mainView.addContinentView();
		newMapText += "\n\n[Territories]\n" + mainView.addTerritoryView();
		
		String fileName = mainView.fileNameToSaveView();
		
		try {  
            Writer w = new FileWriter(fileName);  
            w.write(newMapText);  
            w.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	/**
	* This method replaces the content of a line number in a
	* file with the new content
	* 
	* @param lineNumber The line number where we want to place the new content
	* @param newData The new content which we want to replace the old data with
	* @param filePath The path of the file to be edited.
	* @throws java.io.IOException in some circumstance 
	*/
	public static void setLineText(int lineNumber, String newData, String filePath) throws IOException {
	    Path path = Paths.get(filePath);
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(lineNumber - 1, newData);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	/**
	* This method lets user to edit an existing map based on the
	* line number selected and then displays the new map
	* 
	* @param filePath The path of the file to be edited, if the path is null
	* then user will be asked to input the file path.
	*/
	public static void editMap(String filePath) {
		if(filePath == null) {
			filePath = mainView.fileNameToEditOrLoadView();
		}
		
		try {
			
			int lineNumber = 1;
			int continentBegin = -1;
			int territoryBegin = -1;
			List<String> allLines = Files.readAllLines(Paths.get(filePath));
			for (String line : allLines) {
				System.out.println(lineNumber + ". " + line);
				if(line.equals("[Continents]")) {
					continentBegin = lineNumber;
				}
				if(line.equals("[Territories]")) {
					territoryBegin = lineNumber;
				}
				lineNumber++;
			}
		    			
			boolean editContinent = mainView.wantToEditContinentView();
			
		    int lineNumberToEdit;
		    while(true) {
			    lineNumberToEdit = mainView.lineNumberToEditView();
			    if(lineNumberToEdit < 1 || lineNumberToEdit >= lineNumber) {
			    	System.out.println("INVALID line number entered");
			    	continue;
			    }
			    else if(editContinent && (lineNumberToEdit <= continentBegin || lineNumberToEdit >= territoryBegin)) {
			    	System.out.println("INVALID continent line number entered");
			    	continue;
			    }	
			    else if(!editContinent && (lineNumberToEdit <= territoryBegin || lineNumberToEdit >= lineNumber)) {
			    	System.out.println("INVALID territory line number entered");
			    	continue;
			    }
			    else {
			    	break;
			    }
		    }
		    
		    String newLineText = mainView.newLineToReplaceWithView(editContinent);
		    
		    setLineText(lineNumberToEdit, newLineText, filePath);
		    allLines.set(lineNumberToEdit - 1, newLineText);
		    
		    while(mainView.wantToEditMorelinesView()) {
		    	editMap(filePath);
		    }
	    	populateUserEnteredContinentLines(allLines);
	    	populateUserEnteredTerritoryLines(allLines);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	* This method is used for populating the data
	* of userEnteredContinentLines array list
	* 
	* @param allLines A list of strings which contains
	* all the lines inside map file
	*/
	public static void populateUserEnteredContinentLines(List<String> allLines) {
		Integer continentLineStartIndex = null;
		Integer continentLineEndIndex = null;
		int lineCount = 0;
		for(String line : allLines) {
			if(line.matches("\\[Continents\\]")) {
				continentLineStartIndex = lineCount + 1;
			}
			if(line.matches("\\[Territories\\]")) {
				continentLineEndIndex = lineCount;
			}
			lineCount++;
		}
		
		if(continentLineStartIndex != null && continentLineEndIndex != null) {
			userEnteredContinentLines.addAll(allLines.subList(continentLineStartIndex, continentLineEndIndex));
		}
	}
	
	/**
	* This method is used for populating the data
	* of userEnteredTerritoryLines array list
	* 
	* @param allLines A list of strings which contains
	* all the lines inside map file
	*/
	public static void populateUserEnteredTerritoryLines(List<String> allLines) {
		Integer territoryLineStartIndex = null;
		int lineCount = 0;
		for(String line : allLines) {
			if(line.matches("\\[Territories\\]")) {
				territoryLineStartIndex = lineCount + 1;
			}
			lineCount++;
		}

		if(territoryLineStartIndex != null) {
			userEnteredTerritoryLines.addAll(allLines.subList(territoryLineStartIndex, allLines.size()));
		}
	}
	
	/**
	* This method loads the Map and displays its content.
	* 
	*/
	public static void loadMap(){
		String filePath = mainView.fileNameToEditOrLoadView();
		try {
			List<String> allLines = Files.readAllLines(Paths.get(filePath));
			for (String line : allLines) {
				System.out.println(line);
			}
			populateUserEnteredContinentLines(allLines);
			populateUserEnteredTerritoryLines(allLines);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	* This method asks user about map selections. Depending on the
	* choice entered user can create a new map, edit an existing map
	* or load an existing map
	* 
	*/
	public static void mapSelection() {
		int selectedOption = mainView.mapSelectionView();
		
		switch(selectedOption) {
			case 1:
				createNewMap();
				break;
			case 2:
				editMap(null);
				break;
			case 3:
				loadMap();
				if(mainView.wantToEditMapView()) {
					userEnteredContinentLines.clear();
					userEnteredTerritoryLines.clear();
					editMap(null);
				}
				break;
			default:
				System.out.println("Invalid Option.");
				mapSelection();
				break;
		}
		
	}
	
	/**
	 * This method Assigns the initial territories randomly
	 * to the players in the game.
	 */
	public static void assignInitialTerritories() {
		for (Territory territory : Main.activeMap.territories) {
			if (territory.owner == null) {
				Random rand = new Random();
				Player player = Main.players.get(rand.nextInt(Main.players.size()));
				territory.owner = player;
				player.assignedTerritories.add(territory);
			}
		}
	}
	
	/**
	 * This method defines Assigning the initial number of armies
	 *  to each player who gets in the game.
	 */
	public static void assignInitialArmies() {
		int playersSize = players.size();
		totalInitialArmies = 0;
		if (playersSize == 2) {
			totalInitialArmies = 80;
		} else if (playersSize == 3) {
			totalInitialArmies = 105;
		} else if (playersSize == 4) {
			totalInitialArmies = 120;
		} else if (playersSize == 5) {
			totalInitialArmies = 125;
		} else if (playersSize == 6) {
			totalInitialArmies = 120;
		}
		else {
			System.out.println("Number of players should be less than 6");
			System.exit(0);
		}
		
		for (int i = 0; i < playersSize; i++) {
			players.get(i).setInitialArmyCount(totalInitialArmies/playersSize);
		}
	}
	
		
	/**
	 * This method builds the map from the text lines
	 * which are read from the file selected to load
	 * the map.
	 */
	public static void buildMap() {
		userEnteredContinentLines.removeAll(Arrays.asList("", null));
		userEnteredTerritoryLines.removeAll(Arrays.asList("", null));
		
		for(String continentLines : userEnteredContinentLines) {
			String continentName = continentLines.split("=")[0].trim();
			int continentScore = Integer.parseInt(continentLines.split("=")[1].trim());
			
			if(!activeMap.addContinent(continentName, continentScore)) {
				System.out.println("Couldn't add continent because format is invalid"
						+ "as the continent name already exists");
				System.exit(0);
			}
			
		}
		
		for(String territoryLines : userEnteredTerritoryLines) {
			List<String> territoryLineArray = new ArrayList<String>(); 
			for(String territoryLine : territoryLines.split(",")) {
				territoryLineArray.add(territoryLine.trim());
			}
			
			Territory territory = Map.findTerritory(territoryLineArray.get(0));
			if(territory == null) {
				territory = new Territory(territoryLineArray.get(0));
			}
			territory.addNeighbours(String.join(",", territoryLineArray.subList(4, territoryLineArray.size())));
			
			if(!territory.assignContinent(territoryLineArray.get(3))) {
				System.out.println("Couldn't assign continent because format is invalid as"
						+ "the continent is already assigned to territory or doesn't exists.");
				System.exit(0);
			}
			
		}
	}
	
	/**
	 * This method is a part of startup phase and assign
	 * initial territories to players and gives them a set of
	 * initial armies and lets them place those armies in the
	 * assigned countries
	 */
	public static void assignTerritoriesAndArmies() {
		assignInitialTerritories();
		assignInitialArmies();
		int playersCount = players.size();
		
		if (mainView.wantToPlaceArmiesManually()) {
			boolean armiesLeftToPlace = true;
			while(armiesLeftToPlace) {
				for(int i = 0; i < playersCount; ++i) {
					players.get(i).placeArmies();
				}
				
				int playersCountWithNoArmiesLeft = 0;
				
				for(int i = 0; i < playersCount; ++i) {
					if(players.get(i).armiesLeft == 0) {
						playersCountWithNoArmiesLeft++;
					}
				}
				
				if(playersCountWithNoArmiesLeft == playersCount) {
					armiesLeftToPlace = false;
				}
			}
		}
		else {
			for(int i = 0; i < playersCount; ++i) {
				players.get(i).placeArmiesAutomatically();
			}
		}
	}
	
	public static void startUp() {
		mapSelection();
		buildMap();
		if(activeMap.validateMap()) {
			Main.players = new ArrayList<Player>();
			int playersCount = mainView.playerCountView();		
			
			for(int i = 0; i < playersCount; ++i) {
				Player player = new Player("Player" + i);
				PhaseView view = new PhaseView();
				player.addObserver(view);
				players.add(player);
			}
			
			assignTerritoriesAndArmies();
		}
		else {
			System.out.println("INVALID MAP!");
			startUp();
		}
	}
	
	/**
	* This is the main method which runs the program
	* 
	* @param args command line arguments
	*/
	public static void main(String[] args) {
		userEnteredContinentLines = new ArrayList<String>();
		userEnteredTerritoryLines = new ArrayList<String>();
		
		activeMap = new Map();
		WorldDominationView worldDominationView = new WorldDominationView();
		activeMap.addObserver(worldDominationView);
		
		mainView = new MainView();
		mainView.startupPhaseView();
		
		while(!activeMap.allTerritoriesOwnBySinglePlayer()) {
			for(Player player : players) {
				player.setCurrentGamePhase(GamePhase.REINFORCEMENT);	
			}
		}
		
	}
}
