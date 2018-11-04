package Views;

import java.util.Scanner;

import Driver.Main;
import Map.Map;

public class MainView {
	
public Scanner keyboard;
	
	public String fileNameToSaveView() {
		System.out.println("Enter the file name");
		return keyboard.nextLine();
	}
	
	public String fileNameToEditOrLoadView() {
		System.out.println("Enter absolute path of the Map file to edit or load.");
		return keyboard.nextLine();
	}
	
	public boolean wantToEditContinentView() {
		System.out.println("Do you want to edit a continent? Yes or No?");
		return keyboard.nextLine().equalsIgnoreCase("Yes");
	}
	
	public int lineNumberToEditView() {
		System.out.println("Enter the correct line number to edit");
		return Integer.parseInt(keyboard.nextLine());
	}
	
	public String newLineToReplaceWithView(boolean editContinent) {
		System.out.println("Enter the line you want to replace it with");
	    
	    if(editContinent) {
	    	System.out.println("Format of the line to edit a continent should be:\n"
	    			+ "Continent_Name=Continent_Score");
	    }
	    else {
	    	System.out.println("Format of the line to edit a Territory should be:\n"
	    			+ "Territory_Name, X-cord, Y-cord, Continent_Name, Adjacent Territories");
	    }
	    
	    String newLineText = keyboard.nextLine();
	    
	    while(!Main.validateMapLine(editContinent, newLineText)) {
	    	System.out.println("Please enter a valid format as described earlier to edit the line");
	    	newLineText = keyboard.nextLine();
	    }
	    
	    return newLineText;
	}
	
	public boolean wantToEditMorelinesView() {
		System.out.println("Want to edit more lines? \nEnter Yes to edit more lines");
		return keyboard.nextLine().equalsIgnoreCase("Yes");
	}
	
	public boolean wantToEditMapView() {
		System.out.println("\nDo you want to edit this map? Answer in Yes or No.");
		return keyboard.nextLine().equalsIgnoreCase("Yes");
	}
	
	/**
	* This method is used to add new Continents to the Map
	* 
	* @return A string output containing lines of new Continents
	*/
	public String addContinentView() {
		System.out.println("Enter a list of continents (Enter 'exit' to stop adding continents)");
	    System.out.println("Format of the line to add continents should be:\n"
	    			+ "Continent Name=Continent Score");
	    
	    String inputFileText = "";
    	String inputLine;
	    
	    while(keyboard.hasNextLine()) {
	    	inputLine = keyboard.nextLine();
	    	if(inputLine.equals("exit")) {
	    		break;
	    	}
	    	while(!Main.validateMapLine(true, inputLine)) {
	    		System.out.println("Enter a correct format for adding a Continent");
	    		inputLine = keyboard.nextLine();
	    	}
	    	Main.userEnteredContinentLines.add(inputLine);
	    	inputFileText += inputLine + "\n";
	    }
	    return inputFileText;
	}
	
	/**
	* This method is used to add new Territory to the Map
	* 
	* @return A string output containing lines of new Territories
	*/
	public String addTerritoryView() {		
		System.out.println("Enter a list of Territories (Enter 'exit' to stop adding territories)");
		System.out.println("Format of the line to add a Territory should be:\n"
    			+ "Territory Name, X-cord, Y-cord, Continent Name, Adjacent Territories");;

    	String inputFileText = "";
    	String inputLine;
    	
	    while(keyboard.hasNextLine()) {
	    	inputLine = keyboard.nextLine();
	    	if(inputLine.equals("exit")) {
	    		break;
	    	}
	    	while(!Main.validateMapLine(false, inputLine)) {
	    		System.out.println("Enter a correct format for adding a Territory");
	    		inputLine = keyboard.nextLine();
	    	}
	    	Main.userEnteredTerritoryLines.add(inputLine);
	    	inputFileText += inputLine + "\n"; 
	    }
	    return inputFileText;
	}
	
	public boolean wantToPlaceArmiesManually() {
		System.out.println("Write 'm' to place armies manually or 'a' to place armies automatically");
		return keyboard.nextLine().equalsIgnoreCase("m");
	}
	
	public MainView() {
		this.keyboard = new Scanner(System.in);
	}
	
	public int mapSelectionView() {
		System.out.println("Select one of the following options: \n"
				+ "1. Create a new Map. \n"
				+ "2. Edit a Map. \n"
				+ "3. Load a previous Map.");
		return Integer.parseInt(keyboard.nextLine().trim());
	}
	
	/**
	 * This method contains the startup phase of
	 * the game which includes map selection,
	 * building a map, if the map is valid then
	 * assigning countries to players randomly and
	 * letting the user place armies in the assigned countries
	 * 
	 */
	public void startupPhaseView() {	
		System.out.println("\n***********************STARTUP PHASE BEGINS*****************************\n");
		Main.startUp();
		System.out.println("\n***********************STARTUP PHASE ENDS*****************************\n");
	}
	
	public int playerCountView() {
		int playersCount;
		do {
			System.out.println("\nEnter the number of players"
					+ "\n(Note: the value should less than " + Map.listOfAllTerritories.size() + " i.e. the number of territories");
			playersCount = Integer.parseInt(keyboard.nextLine());
		} while(playersCount >= Main.activeMap.territories.size());
		return playersCount;
	}
}
