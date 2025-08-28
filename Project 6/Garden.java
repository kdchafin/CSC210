//package com.gradescope.garden;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
/*
 * This class holds the Garden and its methods. The Garden is a user defined
 * size, and holds a 2d array of plant objects, aswell as some of their more 
 * intuitive variables
 */
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
    public void addPlant(int x, int y, Plant plant, TextArea textArea) {
        if (x >= 0 && x < rows && y >= 0 && y < cols) { 
            if (gardenPlot[x][y] == null) {
                gardenPlot[x][y] = plant;
            }
        }
    }
    
    /*
     * grows all plants in the garden object by an amount
     * @param amount (int), the amount to grow by
     */
    public void grow(int amount) {
    	//loop through all objects
        for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) {
            	//if a plant exists at this location, grow it
                if (gardenPlot[i][j] != null) 
                    gardenPlot[i][j].grow(amount);
    }}}
    
    /*
     * grows a plant at a specific location
     * @param amount, the amount to grow
     * @param row, col, the locaton of the plant
     */
    public void grow(int amount, int row, int col, TextArea textArea) {
    	// check for plant object at (row,col)
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
        	if (gardenPlot[row][col] != null) 
                gardenPlot[row][col].grow(amount); return;
        }
        
        //if no plant or out of bounds
        textArea.appendText("Can't grow there.\n");
    }
    
    /*
     * grows plants based on their name
     * @param amount, the amount to grow the plant
     * @param name, the name of the plants the be grown
     */
    public void grow(int amount, String name) {
    	// loop through and check if the plant has the same name
        for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) {
            	//name check, if name matches, then grow
                if (gardenPlot[i][j] != null && gardenPlot[i][j].getName().equals(name)) {
                    gardenPlot[i][j].grow(amount);
                }
    }}}
    
    /*
     * grow all plants of the same plant class
     * @param amount, the amount to grow the plant
     * @param plantClass, the class representation for the plants to be grown
     */
    public void grow(int amount, String className, String temp) {
    	
    	//string to catch the class name
    	String type = null;
        for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) {
            	//if plant is of the same class, then grow
                if (gardenPlot[i][j] != null) {
                	if (className.equals(gardenPlot[i][j].getType())) {
                		gardenPlot[i][j].grow(amount);
                	    if (type == null) type = gardenPlot[i][j].getType();
                	}
        }}}
    }
    
    /*
     * harvests all plants of type vegetable
     */
    public void harvest(GraphicsContext graphics) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gardenPlot[i][j] == null) continue;
                else if (gardenPlot[i][j].getType().equals("vegetable")) {
                    gardenPlot[i][j] = null;
                    drawEmptyPlot(i, j, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    //gardenPlot[i][j] = null;
                }
            }
        }
    }
    
    /*
     * harvest a vegetable at a location
     * @param row, col, the potential location of a veggie to remove
     */
    public void harvest(int row, int col, TextArea textArea, GraphicsContext graphics) {
    	//ensure the location is in bounds
    	if (row >= 0 && row < rows && col >= 0 && col < cols) {
    		if (gardenPlot[row][col] != null) {
	    		if (gardenPlot[row][col].getType() == "vegetable") {
	    			gardenPlot[row][col].removePlant();
                    drawEmptyPlot(row, col, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[row][col ] = null;
                    return;
	    		}
            textArea.appendText("Can't harvest there.\n");
    		}
        }
    }
    
    /*
     * harvest all vegetables of a given name
     * @params name, the name of the veggies to remove
     */
    public void harvest(String name, GraphicsContext graphics) {
        // find veggies with the same name.
        for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	//if vegetable is found, remove
            	else if (gardenPlot[i][j].getName().equals(name)) {
                	gardenPlot[i][j].removePlant();
                    drawEmptyPlot(i, j, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[i][j ] = null;
                }
    }}}
    
    /*
     * pick all plants of type flower
     */
    public void pick(GraphicsContext graphics) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gardenPlot[i][j] == null) continue;
                else if (gardenPlot[i][j].getType().equals("flower")) {
                    gardenPlot[i][j].removePlant();
                    drawEmptyPlot(i, j, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[i][j] = null;
                }
            }
        }
    }
    
    /*
     * pick a flower at a location
     * @param row, col, the potential location of a flower to remove
     */
    public void pick(int row, int col, TextArea textArea, GraphicsContext graphics) {
    	//ensure the location is in bounds
    	if (row >= 0 && row < rows && col >= 0 && col < cols) {
    		if (gardenPlot[row][col] != null) {
	    		if (gardenPlot[row][col].getType() == "flower") {
	    			gardenPlot[row][col].removePlant();
                    drawEmptyPlot(row, col, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[row][col ] = null;
                    return;
	    		}
                textArea.appendText("Can't pick there.\n");
    		}
        }
    }
    
    /*
     * pick all flowers of a given name
     * @params name, the name of the flowers to remove
     */
    public void pick(String name, GraphicsContext graphics) {
        for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	// if name matches, remove
            	else if (gardenPlot[i][j].getName().equals(name)) {
                	drawEmptyPlot(i, j, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[i][j ] = null;
                }
    }}}
    
    /*
     * cut all plants of type tree
     */

    public void cut(GraphicsContext graphics) {
    	//loop to find trees
    	for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	//if tree found, remove
            	else if (gardenPlot[i][j].getType() == "tree") {
                	drawEmptyPlot(i, j, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[i][j ] = null;
                }
    }}}
    
    /*
     * cut a tree at a location
     * @param row, col, the potential location of a tree to remove
     */
    public void cut(int row, int col, TextArea textArea, GraphicsContext graphics) {
    	//check that the location is in bounds
    	if (row >= 0 && row < rows && col >= 0 && col < cols) {
    		if (gardenPlot[row][col] != null) {
    			
    			// if type tree is found, remove
	    		if (gardenPlot[row][col].getType() == "tree") {
	    			drawEmptyPlot(row, col, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[row][col] = null;
	    			return;
	    		}
    		}
            textArea.appendText("Can't grow there.\n");
        }
    }
    
    /*
     * cut trees of same name
     * @param name, the names of trees to remove
     */
    public void cut(String name, GraphicsContext graphics) {
    	//loop to find trees
        for (int i = 0; i < rows; i++) { for (int j = 0; j < cols; j++) {
            	if (gardenPlot[i][j] == null) continue;
            	//if tree of same name found, remove it
            	else if (gardenPlot[i][j].getName().equals(name)) {
                	drawEmptyPlot(i, j, graphics, 120, 120, Color.LIGHTGRAY); // Update the plot to clear it visually
                    gardenPlot[i][j ] = null;
                }
    }}}

    /*
     * Displays the entire garden when called. Uses the Override methods
     * in each plant subclass to get the Plot representation
     * @param int pSize, rSize, the location of the plot in the garden
     * 
     */
    public void display(GraphicsContext graphics, int pSize, int rSize, Color bgClr) {
        for (int i =0; i < this.gardenPlot.length; i++) {
            for (int j = 0; j < this.gardenPlot[i].length; j++) {
                if(this.gardenPlot[i][j] != null) this.gardenPlot[i][j].display(graphics, i, j);
                else {
                    drawEmptyPlot(i, j, graphics, pSize, pSize, bgClr);
    }}}}

    /*
     * Draws an empty plot representation to the screen
     * @param int x,y the location of the plots current object
     * @param int pSize, rSize the location of the screen of the entire plot
     */
    private void drawEmptyPlot(int x, int y, GraphicsContext graphics, int pSize, int rSize, Color bgClr) {
        graphics.clearRect(x * pSize, y * pSize, pSize, pSize);
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                graphics.setFill(bgClr);
                graphics.fillRect(x * pSize + i * Math.floorDiv(pSize,5) + 10,
                                  y * pSize + j * Math.floorDiv(pSize,5) + 10, 2,2);
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