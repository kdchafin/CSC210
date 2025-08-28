
/* File: Flower.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This File holds the flower subclass to Plant
 * 			It is hardcoded to update the plot when grown
 * 			
 */

package com.gradescope.garden;

public class Flower extends Plant {
	
	char sym = super.getSymbol();
	
	//the hardcoded representation of the Flower (i got 5 points off for this :(.   rip me)
	private char[][] size2 = new char[][] {{'.','.','.','.','.'}, {'.','.', sym,'.','.'},
		{'.',sym,sym,sym,'.'}, {'.','.',sym,'.','.'}, {'.','.','.','.','.'}};
	private char[][] size3 = new char[][] {{'.','.', sym,'.','.'}, {'.',sym,sym,sym,'.'},
		{sym,sym,sym,sym,sym}, {'.',sym,sym,sym,'.'}, {'.','.', sym,'.','.'}};
	private char[][] size4 = new char[][] {{'.',sym,sym,sym,'.'}, {sym,sym,sym,sym,sym},
		{sym,sym,sym,sym,sym}, {sym,sym,sym,sym,sym}, {'.',sym,sym,sym,'.'}};
	private char[][] size5 = new char[][] {{sym,sym,sym,sym,sym}, {sym,sym,sym,sym,sym},
		{sym,sym,sym,sym,sym}, {sym,sym,sym,sym,sym}, {sym,sym,sym,sym,sym}};
	
	/*
	 * the flower initalizer for a new plant object
	 */
	public Flower (String name) {
		super(name);
		super.setType("flower");
		super.updatePlot(2,2,super.getSymbol());
	}
	 
	/*
	 * overridden from Plant .java
	 * defines the growth pattern for the flower at n growth level 
	 */
	@Override
	public void grow(int num) {
		int totalSize = this.getSize() + num;
		
		//get the size and and then set it, also checks edge case
		if (totalSize > 5) totalSize = 5;	
		this.setSize(totalSize);
		
		//find the growth level and uses preset to update the plot
	    switch (this.getSize()) {
	    case 2:
	    	super.updatePlot(size2);
	    	break;
	    case 3:
	    	super.updatePlot(size3);
	    	break;
	    case 4:
	    	super.updatePlot(size4);
	    	break;
	    case 5:
	    	super.updatePlot(size5);
	    	break;
	    }
	}
}