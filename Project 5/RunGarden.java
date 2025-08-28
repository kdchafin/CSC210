/* File: RunGarden.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This program simulates a garden by generating 
 * 			5x5 plots for plant objects to grow. All plots are 
 * 			held together in the Garden object, which is used as
 * 			a call for plot manipulation (ie. removal of plant, 
 * 			grow plant...)
 * 
 *          To run the program, compile the 8 java files, then use the following
 *          line to run "java RunGarden *infile*"
 *          
 *          The infile should have 1 command per line, and use the commands
 *          available in the garden class.
 */
package com.gradescope.garden;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
 * This class is responsible for reading and parsing commands
 * from the user entered infile
 */
public class RunGarden {

    private static String[] trees = new String[]{"oak","willow","banana","coconut","pine"};
    public static String[] vegetables = new String[]{"garlic","zucchini","tomato","yam","lettuce"};
    public static String[] flowers = new String[]{"iris","lily","rose","daisy","tulip","sunflower"};
    public static String[] berries = new String[] {"strawberry", "blueberry", "cloudberry", "blackberry"};
    public static String[] plantTypes = new String[] {"flower", "tree", "vegetable", "berry"};
	/*reads infile, sets row and col paramaters, makes the garden,
	 * and then formats the list of commands before being sent to 
	 * the getCommand function.
	 * @params args, the args entered by the user
	 */
    public static void main(String[] args) throws FileNotFoundException {
    	//opening file for reading
        File input = new File(args[0]);
        Scanner scanner = new Scanner(input);
        
        //the first 2 lines are the row and col, so get and set
        String rowLine = scanner.nextLine();
        int rows = Integer.parseInt(rowLine.split(":")[1].trim());
        String colLine = scanner.nextLine();
        int cols = Integer.parseInt(colLine.split(":")[1].trim());
        
        //garden cannot be more than 16 plots long
        if (cols > 16) {
        	System.out.println("Too many plot columns.");
        	scanner.close();
        	return;
        }
        //create garden object with the row and col params
        Garden myGarden = new Garden(rows, cols);
        while (scanner.hasNextLine()) {
        	//get commands and trim them
        	String[] cmds = scanner.nextLine().split(" ");
        	for (int x = 0; x < cmds.length; x++) {
        		cmds[x] = cmds[x].toLowerCase().trim();
        	}
        	// send commands to be parsed further
        	getCommand(myGarden, cmds);
        }
        scanner.close();
    }
    /*
     * function takes command and sort it based on its respective
     * function to be used in Garden.java
     * @param Garden, the garden object
     * @param cmds, command line to be parsed
     */
    public static void getCommand(Garden myGarden, String[] cmds) {
    	
    	switch (cmds[0]) {
    	//call the command function based on the first command read
    	case "plant":
    		plant(myGarden, cmds);
    		break;
    	// this one calls the screen object for printing
	    case "print":
	        Screen myScreen = new Screen(myGarden.getRow(), myGarden.getCol());
	        myScreen.updateScreen(myGarden);
	        myScreen.printScreen();
	        break;
    	case "grow":
    		grow(myGarden, cmds);
    		break;
    	case "harvest":
    		harvest(myGarden, cmds);
    		break;
    	case "pick":
    		pick(myGarden, cmds);
    		break;
    	case "cut":
    		cut(myGarden, cmds);
    		break;
    	}
    }
    /*
     * uses the nameOfPlant function to get the plants class
     * it then adds the plant based on what class the name is in.
     * @param Garden object with plants to be passed to other functions
     * @param array of cmds, the grow commands
     */
    public static void plant(Garden myGarden, String[] cmds) {
    	//the coords for its placement in Garden
        int x = Character.getNumericValue(cmds[1].charAt(1));
        int y = Character.getNumericValue(cmds[1].charAt(3));
        String plantType = cmds[2];
        //getting the class the plant is in
        char pName= nameOfPlant(plantType, trees, vegetables, flowers, berries);
        if (pName == 't') { 
        	myGarden.addPlant(x, y, new Tree(plantType));
        	return;
        }
        if (pName == 'v') { 
        	myGarden.addPlant(x, y, new Vegetable(plantType));
        	return;
        }
        if (pName == 'f') { 
        	myGarden.addPlant(x, y, new Flower(plantType));
        	return;
        }
        if (pName == 'b') { 
        	myGarden.addPlant(x, y, new Berry(plantType));
        	return;
        }
    }
    /*
     * this parses the grow commands. sorts between name, class, locaton, and
     * all objects in the garden
     * @param Garden with plant object
     * @param cmds the list of grow commands 
     */
    public static void grow(Garden myGarden, String[] cmds) {
    	
    	//gets grow amount
    	int amount = Integer.parseInt(cmds[1]);
    	
    	//len of 2 can only be for growing all plant objects
    	if (cmds.length == 2) {
    		myGarden.grow(amount);
    	}
    	else if (cmds.length == 3) {
    		// if ',', then must be position coordinates
    		if (cmds[2].contains(",")) {
    	        int x = Character.getNumericValue(cmds[2].charAt(1));
    	        int y = Character.getNumericValue(cmds[2].charAt(3));
    			myGarden.grow(amount, x, y);
    		}
    		
    		String name = cmds[2].trim().toLowerCase();
    		//check if the name is in a class to grow objects of same class
    		if (name.equals("flower")) myGarden.grow(amount, Flower.class);
    		if (name.equals("vegetable")) myGarden.grow(amount, Vegetable.class);
    		if (name.equals("tree")) myGarden.grow(amount, Tree.class);
    		if (name.equals("berry")) myGarden.grow(amount, Berry.class);
    		// lastly, grow the objects with the same name
    		char pName = nameOfPlant(name, trees, vegetables, flowers, berries);
    		if (pName == 't' || pName == 'v' || pName == 'f' || pName == 'b') {
    			myGarden.grow(amount, name);
    		}
    	}
    }
    /*
     * parses the harvest commands for vegetable type plants
     * @param Garden, contains the plant object in question
     * @param cmds, the list of commands for harvest
     */
    public static void harvest(Garden myGarden, String[] cmds) {
    	//harvests all when not specifying a specific vegetable
    	if (cmds.length == 1) {
    		myGarden.harvest();
    	}
    	//harvests a potential vegetable at a position
		else if (cmds.length == 2) {
			if (cmds[1].contains(",")) {
		        int x = Character.getNumericValue(cmds[1].charAt(1));
		        int y = Character.getNumericValue(cmds[1].charAt(3));
				myGarden.harvest(x, y);
			}
			// harvests all vegetables of the same name
			else {
				String name = cmds[1];
				char pName = nameOfPlant(name, trees, vegetables, flowers, berries);
	    		if (pName == 'v') {
	    			myGarden.harvest(name);
	    		}
			}
		}
    }
    /*
     * parses the pick commands for flower type plants
     * @param Garden, contains the plant object in question
     * @param cmds, the list of commands for harvest
     */
    public static void pick(Garden myGarden, String[] cmds) {
    	// picks all when not specifying a specific flower
    	if (cmds.length == 1) {
    		myGarden.pick();
    	}
		else if (cmds.length == 2) {
			// picks a potential flower at a position
			if (cmds[1].contains(",")) {
		        int x = Character.getNumericValue(cmds[1].charAt(1));
		        int y = Character.getNumericValue(cmds[1].charAt(3));
				myGarden.pick(x, y);
			}
			// picks all flowers of the same name
			else {
				String name = cmds[1];
				char pName = nameOfPlant(name, trees, vegetables, flowers, berries);
	    		if (pName == 'f') {
	    			myGarden.pick(name);
	    		}
			}
		}
    }
    /*
     * parses the cut commands for tree type plants
     * @param Garden, contains the plant object in question
     * @param cmds, the list of commands for harvest
     */
    public static void cut(Garden myGarden, String[] cmds) {
    	// cut all when not specifying at specific flower
    	if (cmds.length == 1) {
    		myGarden.cut();
    	}
		else if (cmds.length == 2) {
			// cuts a potential tree at a position
			if (cmds[1].contains(",")) {
		        int x = Character.getNumericValue(cmds[1].charAt(1));
		        int y = Character.getNumericValue(cmds[1].charAt(3));
				myGarden.cut(x, y);
			}
			else {
				//cut all trees of the sae name
				String name = cmds[1];
				char pName = nameOfPlant(name, trees, vegetables, flowers, berries);
	    		if (pName == 't') {
	    			myGarden.cut(name);
	    		}
			}
		}
    }
    /*
     * looks for a plants name in the list of class plants to distinguish its class
     * @param plantName the name being searched for
     * @param String[], these are the string declared at the top of the file, and 
     * are used as a makeshift dictionary
     */
    public static char nameOfPlant(String plantName, String[] trees, String[] vegetables, String[] flowers, String[] berries) {
    	for (String tName : trees) {
        	if (tName.equals(plantName)) {
        		return 't';
        	}
        }
        for (String tName : vegetables) {
        	if (tName.equals(plantName)) {
        		return 'v';
        	}
        }
        for (String tName : flowers) {
        	if (tName.equals(plantName)) {
        		return 'f';
        	}
        }
        for (String tName : berries) {
        	if (tName.equals(plantName)) {
        		return 'b';
        	}
        }
        return 'n';
    }
}
        