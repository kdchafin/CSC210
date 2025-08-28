/* File: Vegetable.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This File holds the Vegetable subclass to Plant
 * 			Grows using a looping algorithm
 */

package com.gradescope.garden;

public class Vegetable extends Plant{
	/*
	 * vegetable initalizer 
	 */
	public Vegetable(String name) {
		super(name);
		super.setType("vegetable");
		super.updatePlot(0,2,super.getSymbol());
	}
	
	/*
	 * overridden from Plant .java
	 * defines the growth pattern for the vegetable at n growth level 
	 */
	@Override
	public void grow(int num) {
		//get new size and set it
		int totalSize = this.getSize() + num;
		this.setSize(totalSize);
		
		//check edge cases for size
	    if (totalSize <= 0) return;
	    if (totalSize > 4) totalSize = 5;

	    //vegetables grow down from (0,2)
	    for (int x = 0; x < totalSize; x++) {
	        super.updatePlot(x, 2, super.getSymbol());
	    }
	}
}