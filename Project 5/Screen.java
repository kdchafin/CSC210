/* File: Screen.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This File holds the screen 2d array for displaying the garden object.
 */

package com.gradescope.garden;

public class Screen {
	//screen array to hold the screen object
    private char[][] screenArray;
    /*
     * this method generates the screen object as '.''s.
     * @param gardenRows, gardenCols, the dimensions of the garden 
     */
    public Screen(int gardenRows, int gardenCols) {
    	//creating screen size
        screenArray = new char[gardenRows * 5][gardenCols * 5];
        //looping to make all available areas a 5x5 representation of '.''s
        for (int i = 0; i < screenArray.length; i++) {
            for (int j = 0; j < screenArray[i].length; j++) {
                screenArray[i][j] = '.';
            }
        }
    }
    /* 2025 Kieran here, bro this function is so fucking gross what was i thinking :(  </3
    * 
     * this updates the screen with the Garden object before printing
     * @param Garden, the garden object to update screen with
     */
    public void updateScreen(Garden garden) {
    	//looping to get rows and cols of garden
        for (int i = 0; i < garden.getRow(); i++) {
            for (int j = 0; j < garden.getCol(); j++) {
            	
            	//get the plant object to add to screen
                Plant plant = garden.getPlant(i, j);
                
                //if the location is not null, then a plant object exists, add to screen
                if (plant != null) {
                    for (int row = 0; row < 5; row++) {
                        String line = plant.getLine(row);
                        for (int col = 0; col < line.length() && col < 5; col++) {
                        	//screen representation as described in assignment criteria
                            screenArray[(i * 5) + row][(j * 5) + col] = line.charAt(col);
                        }
                    }
                }
            }
        }
    }
    /*
     * prints the entire screen by generating lines for every row
     */
    public void printScreen() {
    	// print command prints before screen
    	System.out.println("> PRINT");
    	
    	//get every object by looping
        for (int i = 0; i < screenArray.length; i++) {
            for (int j = 0; j < screenArray[i].length; j++) {
                System.out.print(screenArray[i][j]);
            }
            //new liens for formatting
            System.out.println();
        }
        System.out.println();
    }
}