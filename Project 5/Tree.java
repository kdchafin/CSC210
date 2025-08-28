/* File: Tree.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This File holds the Tree subclass to Plant
 * 			Grows using a looping algorithm
 */

package com.gradescope.garden;

public class Tree extends Plant {
	
	/*
	 * initalizer for the tree object
	 */
	public Tree(String name) {
		super(name);
		super.setType("tree");
		super.updatePlot(4,2,super.getSymbol());
	}
	 
	/*
	 * overridden from Plant .java
	 * defines the growth pattern for the Tree at n growth level 
	 */
	@Override
	public void grow(int num) {
		
		//get new size and set it
		int totalSize = this.getSize() + num;
		this.setSize(totalSize);
		
		//check edge cases
	    if (totalSize <= 0) return;
	    if (totalSize > 4) totalSize = 5;
	    
	    // trees grow upwards
	    for (int x = 1; x <= totalSize; x++) {
	        super.updatePlot(5 - x, 2, super.getSymbol());
	    }
	}
}