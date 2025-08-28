
/* File: Garden.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This File holds the garden object, which holds all plant objects
 * 			and their respective methods. 
 */

package com.gradescope.garden;

public class Garden {
	//private dimesions and gardenPlot for the garden
    private int rows;
    private int cols;
    private Plant[][] gardenPlot;
    
    /*
     * creates the garden object
     * @param rows,cols, the int dimesions for the size of the garden
     */
    public Garden(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        gardenPlot = new Plant[rows][cols];
    }
    
    /*
     * adds plant to x,y location in the garden
     * @params the x,y location integers
     * @param plant, the plant object
     */
    public void addPlant(int x, int y, Plant plant) {
        if (x >= 0 && x < rows && y >= 0 && y < cols) {
            gardenPlot[x][y] = plant;
        }
    }
    
    /*
     * grows all plants in the garden object by an amount
     * @param amount (int), the amount to grow by
     */
    public void grow(int amount) {
    	System.out.println("> GROW " + amount + "\n");
    	
    	//loop through all objects
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	//if a plant exists at this location, grow it
                if (gardenPlot[i][j] != null) {
                    gardenPlot[i][j].grow(amount);
                }
            }
        }
    }
    
    /*
     * grows a plant at a specific location
     * @param amount, the amount to grow
     * @param row, col, the locaton of the plant
     */
    public void grow(int amount, int row, int col) {
    	System.out.println("> GROW " + amount + " (" + row + "," + col + ")\n");
    	
    	// check for plant object at (row,col)
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
        	if (gardenPlot[row][col] != null) {
        		gardenPlot[row][col].grow(amount);
        		return;
        	}
        }
        
        //if no plant or out of bounds
        System.out.println("Can't grow there.\n");
    }
    
    /*
     * grows plants based on their name
     * @param amount, the amount to grow the plant
     * @param name, the name of the plants the be grown
     */
    public void grow(int amount, String name) {
    	System.out.println("> GROW " + amount + " " + name + "\n");
    	
    	// loop through and check if the plant has the same name
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	//name check, if name matches, then grow
                if (gardenPlot[i][j] != null && gardenPlot[i][j].getName().equals(name)) {
                    gardenPlot[i][j].grow(amount);
                }
            }
        }
    }
    
    /*
     * grow all plants of the same plant class
     * @param amount, the amount to grow the plant
     * @param plantClass, the class representation for the plants to be grown
     */
    public void grow(int amount, Class<? extends Plant> plantClass) {
    	
    	//string to catch the class name
    	String type = null;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	//if plant is of the same class, then grow
                if (gardenPlot[i][j] != null && plantClass.isInstance(gardenPlot[i][j])) {
                    gardenPlot[i][j].grow(amount);
                    if (type == null) type = gardenPlot[i][j].getType();
                }
            }
        }
        //print the grow command using the type name
        System.out.println("> GROW " + amount + " " + type + "\n");
    }
    
    /*
     * harvests all plants of type vegetable
     */
    public void harvest() {
    	System.out.println("> HARVEST\n");
    	for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	//if plot contains vegetable object, then remove it
            	if (gardenPlot[i][j] == null) continue;
            	else if (gardenPlot[i][j].getType() == "vegetable") {
                	gardenPlot[i][j].removePlant();
                }
            }
        }
    }
    
    /*
     * harvest a vegetable at a location
     * @param row, col, the potential location of a veggie to remove
     */
    public void harvest(int row, int col) {
    	System.out.println("> HARVEST (" + row + "," + col + ")\n");
    	
    	//ensure the location is in bounds
    	if (row >= 0 && row < rows && col >= 0 && col < cols) {
    		if (gardenPlot[row][col] != null) {
	    		if (gardenPlot[row][col].getType() == "vegetable") {
	    			gardenPlot[row][col].removePlant();
	    			return;
	    		}
	    	System.out.println("Can't harvest there.\n");
    		}
        }
    }
    
    /*
     * harvest all vegetables of a given name
     * @params name, the name of the veggies to remove
     */
    public void harvest(String name) {
    	System.out.println("> HARVEST " + name + "\n");
    	
    	// find veggies with the same name.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	
            	//if vegetable is found, remove
            	else if (gardenPlot[i][j].getName().equals(name)) {
                	gardenPlot[i][j].removePlant();
                }
            }
        }
    }
    
    /*
     * pick all plants of type flower
     */
    public void pick() {
    	System.out.println("> PICK\n");
    	
    	//loop to check all plants
    	for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	
            	//if flower is found, remove it
            	else if (gardenPlot[i][j].getType() == "flower") {
                	gardenPlot[i][j].removePlant();
                	gardenPlot[i][j] = null;
                }
            }
        }
    }
    
    /*
     * pick a flower at a location
     * @param row, col, the potential location of a flower to remove
     */
    public void pick(int row, int col) {
    	System.out.println("> PICK (" + row + "," + col + ")\n");
    	
    	//check the position is in bounds
    	if (row >= 0 && row < rows && col >= 0 && col < cols) {
    		if (gardenPlot[row][col] != null) {
    			
    			//if flower of same name is found, remove
	    		if (gardenPlot[row][col].getType() == "flower") {
	    			gardenPlot[row][col].removePlant();
	    			return;
	    		}
	    		System.out.println("Can't pick there.\n");
    		}
        }
    }
    
    /*
     * pick all flowers of a given name
     * @params name, the name of the flowers to remove
     */
    public void pick(String name) {
    	System.out.println("> PICK " + name + "\n");
    	
    	//loop to find flowers
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	
            	// if name matches, remove
            	else if (gardenPlot[i][j].getName().equals(name)) {
                	gardenPlot[i][j].removePlant();
                }
            }
        }
    }
    
    /*
     * cut all plants of type tree
     */

    public void cut() {
    	System.out.println("> CUT\n");
    	
    	//loop to find trees
    	for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	
            	//if tree found, remove
            	else if (gardenPlot[i][j].getType() == "tree") {
                	gardenPlot[i][j].removePlant();
                }
            }
        }
    }
    
    /*
     * cut a tree at a location
     * @param row, col, the potential location of a tree to remove
     */
    public void cut(int row, int col) {
    	System.out.println("> CUT (" + row + "," + col + ")\n");
    	
    	//check that the location is in bounds
    	if (row >= 0 && row < rows && col >= 0 && col < cols) {
    		if (gardenPlot[row][col] != null) {
    			
    			// if type tree is found, remove
	    		if (gardenPlot[row][col].getType() == "tree") {
	    			gardenPlot[row][col].removePlant();
	    			return;
	    		}
    		}
    		System.out.println("Can't cut there.\n");
        }
    }
    
    /*
     * cut trees of same name
     * @param name, the names of trees to remove
     */
    public void cut(String name) {
    	System.out.println("> CUT " + name + "\n");
    	
    	//loop to find trees
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	
            	//if tree of same name found, remove it
            	else if (gardenPlot[i][j].getName().equals(name)) {
                	gardenPlot[i][j].removePlant();
                }
            }
        }
    }
    
    //getter methods below
    
    public Plant getPlant(int x, int y) {
        return gardenPlot[x][y];
    }

    public Plant[][] getGarden() {
        return gardenPlot;
    }

    public int getRow() {
        return rows;
    }
    
    public int getCol() {
        return cols;
    }
}