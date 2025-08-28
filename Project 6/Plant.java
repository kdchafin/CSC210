//package com.gradescope.garden;
/*
 * This class is responsible for for holding information regarding
 * the plant classes. It declares setters and getters for accessing information
 * for the Plant classes
 */

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/*
 * Holds the Plant object, which is a 2d arrray of chars. These chars 
 * are used as placeholders for the GUI, which reads them and replaces them with
 * colored squares to represent the plant
 */
abstract class Plant {
	
	//private variables to define plant objects
	private String name;
	private char symbol;
	private int size;
	private String type;
	private int rSize;
	private int pSize;
	//the plants plot
	private char[][] plot = new char[5][5];
	
	/*
	 * main method for making a Plant object
	 * @param name of the plant
	 */
	public Plant (String name, int rSize, int pSize, String type) {
		this.name = name.toLowerCase();
		this.symbol = this.name.charAt(0);
		this.createEmptyPlot();
		this.size = 1;
		this.rSize = rSize;
		this.pSize = pSize;
		this.type = type;
	}
	
	/*
	 * updates the plot with the symbol of the plant
	 * @param row,col, the location of the char to replace in the plot
	 */
	public void updatePlot(int row, int col, char phold) {
		this.plot[row][col] = phold;
	}
	
	/*
	 * another plotUpdater, not used
	 */
	public void updatePlot(char[][] phold) {
		this.plot = phold;
	}
	
	/*
	 * get the String representation of a row of a plot
	 */
	public String getLine(int row) {
	    StringBuilder line = new StringBuilder();
	    for (int col = 0; col < 5; col++) {
	        line.append(this.plot[row][col]);
	    }
	    return line.toString();
	}
	
	/*
	 * removes a plant completely from a plot by overwriting
	 * all object info
	 */
	public void removePlant() {
		this.name = "DEFAULT";
		this.symbol = '.';
		this.createEmptyPlot();
		this.size = 1;
	}
	
	//makes an empty 5x5 plot of '.'
	public void createEmptyPlot() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				this.plot[i][j] = '.';
			}
		}	
	}
	
	//the grow method, which is overridden by the plant subclasses
	public abstract void grow(int num);

	//Gets the class name for each distinct plant, used in RunGarden
	public abstract String getClassName();

	//gets the plants types color
	protected abstract Color getColor();

	//Displays the Plant object in the garden
	public abstract void display(GraphicsContext graphics, int i, int j);

	// more etters 
	public int getRSize() {
		return this.rSize;
	}

	public int getPSize() {
		return this.pSize;
	}

	public String getName() {
		return this.name;
	}

	public char getSymbol() {
		return this.symbol;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public char[][] getPlot() {
		return this.plot;
	}
	
	public String getType() {
		return this.type;
	}
	
	//setters
	public void setSize(int size) {
		this.size = size;
	}
}