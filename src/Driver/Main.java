package Driver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import java.nio.file.Path;

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
				if(!continentParts[1].matches("-?\\d+(\\.\\d+)?")) {
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
				if(!territoryParts[1].matches("-?\\d+(\\.\\d+)?") || !territoryParts[2].matches("-?\\d+(\\.\\d+)?")) {
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
	* @param keyboard A Scanner class object which is used
	* to take user input for building the map
	*/
	public static void createNewMap(Scanner keyboard) {
		
	}
	
	/**
	* This method replaces the content of a line number in a
	* file with the new content
	* 
	* @param lineNumber The line number where we want to place the new content
	* @param newData The new content which we want to replace the old data with
	* @param filePath The path of the file to be edited.
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
	* @param keyboard A Scanner class object which is used
	* to take user input
	* @param filePath The path of the file to be edited, if the path is null
	* then user will be asked to input the file path.
	*/
	public static void editMap(Scanner keyboard, String filePath) {
		if(filePath == null)
		{
			System.out.println("Enter absolute path of the Map file to edit.");
			filePath = keyboard.nextLine();
		}
		
		try
		{
			
			int lineNumber = 1;
			List<String> allLines = Files.readAllLines(Paths.get(filePath));
			for (String line : allLines) {
				System.out.println(lineNumber + "." + line);
				lineNumber++;
			}
		    
			System.out.println("Do you want to edit a continent? Yes or No?");			
			boolean editContinent = keyboard.nextLine().equals("Yes");
			
		    System.out.println("Enter the correct line number to edit");
		    int lineNumberToEdit = Integer.parseInt(keyboard.nextLine());
		    while(lineNumberToEdit < 1 || lineNumberToEdit >= lineNumber)
		    {
		    	System.out.println("Enter the correct line number to edit");
			    lineNumberToEdit = Integer.parseInt(keyboard.nextLine());
		    }
		    
		    System.out.println("Enter the line you want to replace it with");
		    
		    if(editContinent) {
		    	System.out.println("Format of the line to edit a continent should be:\n"
		    			+ "Continent Name=Continent Score");
		    }
		    else {
		    	System.out.println("Format of the line to edit a Territory should be:\n"
		    			+ "Territory Name, X-cord, Y-cord, Continent Name, Adjacent Territories");
		    }
		    
		    String newLineText = keyboard.nextLine();
		    
		    while(!validateMapLine(editContinent, newLineText)) {
		    	System.out.println("Please enter a valid format as described earlier to edit the line");
		    	newLineText = keyboard.nextLine();
		    }
		    
		    setLineText(lineNumberToEdit, newLineText, filePath);
		    
		    System.out.print("Want to edit more lines? \nEnter Yes to edit more lines");
		    while(keyboard.nextLine().equals("Yes"))
		    {
		    	editMap(keyboard, filePath);
		    }
		    
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	* This method loads the Map and displays its content.
	* 
	* @param keyboard A Scanner class object which is used
	* to take user input
	*/
	public static void loadMap(Scanner keyboard){
		System.out.println("Enter absolute path of the Map file to load.");
		String filePath = keyboard.nextLine();
		try
		{
			List<String> allLines = Files.readAllLines(Paths.get(filePath));
			for (String line : allLines) {
				System.out.println(line);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	* This method asks user about map selections. Depending on the
	* choice entered user can create a new map, edit an existing map
	* or load an existing map
	* 
	* @param keyboard A Scanner class object which is used
	* to take user input
	*/
	public static void mapSelection(Scanner keyboard) {
		System.out.println("Select one of the following options: \n"
				+ "1. Create a new Map. \n"
				+ "2. Edit a Map. \n"
				+ "3. Load a previous Map.");
		
		int selectedOption = Integer.parseInt(keyboard.nextLine());
		
		switch(selectedOption) 
		{
			case 1:
				createNewMap(keyboard);
				break;
			case 2:
				editMap(keyboard, null);
				break;
			case 3:
				loadMap(keyboard);
				break;
			default:
				System.out.println("Invalid Option.");
				mapSelection(keyboard);
				break;
		}
		
	}
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		mapSelection(keyboard);
		
		System.out.println("\nEnter the number of players");
		int playersCount = Integer.parseInt(keyboard.nextLine());
		
		keyboard.close();
	}
}
