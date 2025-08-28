
/* File: Berry.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This File holds the Berry subclass to Plant
 * 			Grows using a looping algorithm
 */

package com.gradescope.garden;

public class Berry extends Plant {
	
	/*
	 * Initalizer for the berry object
	 */
	public Berry(String name) {
		super(name);
		super.setType("berry");
		super.updatePlot(2,2,super.getSymbol());
	}
	
	/*
	 * overridden from Plant .java
	 * defines the growth pattern for the Berry at n growth level 
	 */
	@Override
	public void grow(int num) {
		//get new size and set it
		int totalSize = this.getSize() + num;
		this.setSize(totalSize);
		
		// uses a left a right bound, as berry bushes grow outwards horizontally
		int left = 0;
		int right = 0;
		
		if (totalSize == 1) {
			left = 2;
			right = 2;
		}
		else if (totalSize == 2) {
			left = 1;
			right = 3;
		}
		else if (totalSize >= 3) {
			left = 0;
			right = 4;
		}
		// use bounds to grow the berry bush
	    for (int x = left; x <= right; x++) {
	        super.updatePlot(2, x, super.getSymbol());
	        
	    }
	}
}