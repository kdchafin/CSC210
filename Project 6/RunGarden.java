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
//package com.gradescope.garden;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
/*
 * This class is responsible for reading and parsing commands
 * from the user entered infile
 */
public class RunGarden {

	private Garden myGarden;

    private static String[] trees = new String[]{"oak","willow","banana","coconut","pine"};
	public static String[] vegetables = new String[]{"garlic","zucchini","tomato","yam","lettuce"};
    public static String[] flowers = new String[]{"iris","lily","rose","daisy","tulip","sunflower"};
    public static String[] berries = new String[] {"strawberry", "blueberry", "cloudberry", "blackberry"};
    public static String[] plantTypes = new String[] {"flower", "tree", "vegetable", "berry"};

    private final Color bgClr;

	//RunGarden(graphics, rows, cols, PLOT_SIZE, RECT_SIZE, bgClr);

    public RunGarden(GraphicsContext graphics, int rows, int cols, int pSize, int rSize, Color bgClr) {
        this.myGarden = new Garden(rows, cols);
        this.bgClr = bgClr;
		myGarden.display(graphics, pSize, rSize, bgClr);
    }

	//gardenCmd.parseCmd(curCmd, graphics, textArea, PLOT_SIZE, RECT_SIZE);
	/*reads infile, sets row and col paramaters, makes the garden,
	 * and then formats the list of commands before being sent to 
	 * the getCommand function.
	 * @params args, the args entered by the user
	 */
    public void parseCmd(String curCmd, GraphicsContext graphics, TextArea textArea, int pSize, int rSize) {

		//get commands and trim them
		String[] cmds = curCmd.split(" ");
		for (int x = 0; x < cmds.length; x++) {
			cmds[x] = cmds[x].toLowerCase().trim();
		}
		// send commands to be parsed further
		getCommand(myGarden, cmds, graphics, rSize, pSize, textArea);
		
		textArea.appendText(curCmd + "\n");
		this.drawActiveGarden(graphics, pSize, rSize);
    }
    /*
     * function takes command and sort it based on its respective
     * function to be used in Garden.java
     * @param Garden, the garden object
     * @param cmds, command line to be parsed
     */
    
    public static Boolean checkCols(int cols) {
    	if (cols > 16) return false;
    	return true;
    }
    
	/*
	 * Gets the command type to be parsed
	 */
    public static void getCommand(Garden myGarden, String[] cmds,
		GraphicsContext graphics, int rSize, int pSize, TextArea textArea) {
    	
    	switch (cmds[0]) {
    	//call the command function based on the first command read
    	case "plant":
    		plant(myGarden, cmds, rSize, pSize, textArea);
    		break;
    	case "grow":
    		grow(myGarden, cmds, textArea);
    		break;
    	case "harvest":
    		harvest(myGarden, cmds, textArea, graphics);
    		break;
    	case "pick":
    		pick(myGarden, cmds, textArea, graphics);
    		break;
    	case "cut":
    		cut(myGarden, cmds, textArea, graphics);
    		break;
    	}
    }
    /*
     * uses the nameOfPlant function to get the plants class
     * it then adds the plant based on what class the name is in.
     * @param Garden object with plants to be passed to other functions
     * @param array of cmds, the grow commands
     */
	public static void plant(Garden myGarden, String[] cmds, int rSize, int pSize, TextArea textArea) {
		// Assuming cmds[1] is in the format "(x,y)"
		int x = Character.getNumericValue(cmds[1].charAt(1)); // Get x-coordinate
		int y = Character.getNumericValue(cmds[1].charAt(3)); // Get y-coordinate
		String plantType = cmds[2]; // Get plant type
	
		// Getting the class the plant is in
		char pName = nameOfPlant(plantType, trees, vegetables, flowers, berries);
		switch (pName) {
			case 't': 
				myGarden.addPlant(x, y, new Tree(plantType, rSize, pSize), textArea);
				break;
			case 'v': 
				myGarden.addPlant(x, y, new Vegetable(plantType, rSize, pSize), textArea);
				break;
			case 'f': 
				myGarden.addPlant(x, y, new Flower(plantType, rSize, pSize), textArea);
				break;
			case 'b': 
				myGarden.addPlant(x, y, new Berry(plantType, rSize, pSize), textArea);
				break;
			default:
				textArea.appendText("Unknown plant type: " + plantType + ".\n");
				break;
		}
	}
    /*
     * this parses the grow commands. sorts between name, class, locaton, and
     * all objects in the garden
     * @param Garden with plant object
     * @param cmds the list of grow commands 
     */
    public static void grow(Garden myGarden, String[] cmds, TextArea textArea) {
    	
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
    			myGarden.grow(amount, x, y, textArea);
    		}
    		
    		String name = cmds[2].trim().toLowerCase();
    		//check if the name is in a class to grow objects of same class
    		if (name.equals("flower")) myGarden.grow(amount, "flower", "");
    		if (name.equals("vegetable")) myGarden.grow(amount, "vegetable", "");
    		if (name.equals("tree")) myGarden.grow(amount, "tree", "");
    		if (name.equals("berry")) myGarden.grow(amount, "berry", "");
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
    public static void harvest(Garden myGarden, String[] cmds, TextArea textArea, GraphicsContext graphics) {
    	//harvests all when not specifying a specific vegetable
    	if (cmds.length == 1) {
    		myGarden.harvest(graphics);
    	}
    	//harvests a potential vegetable at a position
		else if (cmds.length == 2) {
			if (cmds[1].contains(",")) {
		        int x = Character.getNumericValue(cmds[1].charAt(1));
		        int y = Character.getNumericValue(cmds[1].charAt(3));
				myGarden.harvest(x, y, textArea, graphics);
			}
			// harvests all vegetables of the same name
			else {
				String name = cmds[1];
				char pName = nameOfPlant(name, trees, vegetables, flowers, berries);
	    		if (pName == 'v') {
	    			myGarden.harvest(name, graphics);
	    		}
			}
		}
    }
    /*
     * parses the pick commands for flower type plants
     * @param Garden, contains the plant object in question
     * @param cmds, the list of commands for harvest
     */
    public static void pick(Garden myGarden, String[] cmds, TextArea textArea, GraphicsContext graphics) {
    	// picks all when not specifying a specific flower
    	if (cmds.length == 1) {
    		myGarden.pick(graphics);
    	}
		else if (cmds.length == 2) {
			// picks a potential flower at a position
			if (cmds[1].contains(",")) {
		        int x = Character.getNumericValue(cmds[1].charAt(1));
		        int y = Character.getNumericValue(cmds[1].charAt(3));
				myGarden.pick(x, y, textArea, graphics);
			}
			// picks all flowers of the same name
			else {
				String name = cmds[1];
				char pName = nameOfPlant(name, trees, vegetables, flowers, berries);
	    		if (pName == 'f') {
	    			myGarden.pick(name, graphics);
	    		}
			}
		}
    }
    /*
     * parses the cut commands for tree type plants
     * @param Garden, contains the plant object in question
     * @param cmds, the list of commands for harvest
     */
    public static void cut(Garden myGarden, String[] cmds, TextArea textArea, GraphicsContext graphics) {
    	// cut all when not specifying at specific flower
    	if (cmds.length == 1) {
    		myGarden.cut(graphics);
    	}
		else if (cmds.length == 2) {
			// cuts a potential tree at a position
			if (cmds[1].contains(",")) {
		        int x = Character.getNumericValue(cmds[1].charAt(1));
		        int y = Character.getNumericValue(cmds[1].charAt(3));
				myGarden.cut(x, y, textArea, graphics);
			}
			else {
				//cut all trees of the sae name
				String name = cmds[1];
				char pName = nameOfPlant(name, trees, vegetables, flowers, berries);
	    		if (pName == 't') {
	    			myGarden.cut(name, graphics);
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

	public void drawActiveGarden(GraphicsContext graphics, int pSize, int rSize) {
        myGarden.display(graphics, pSize, rSize, bgClr);
    }
}
        