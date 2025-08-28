/* File: Plant.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This File holds the Plant objects, which is a
 * 			superclass to Flower, Vegetable,Tree and Berry.java
 */

package com.gradescope.garden;
/*
 * This class is responsible for for holding information regarding
 * the plant classes. It declares setters and getters for accessing information
 * for the Plant classes
 */
abstract class Plant {
	
	//private variables to define plant objects
	private String name;
	private char symbol;
	private int size;
	private String type;
	//the plants plot
	private char[][] plot = new char[5][5];
	
	/*
	 * main method for making a Plant object
	 * @param name of the plant
	 */
	public Plant (String name) {
		this.name = name.toLowerCase();
		this.symbol = this.name.charAt(0);
		this.createEmptyPlot();
		this.size = 1;
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
	
	//getters 
	
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
	
	public void setType(String type) {
		this.type = type;
	}
}