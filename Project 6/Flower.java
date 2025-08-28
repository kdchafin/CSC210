//package com.gradescope.garden;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/*
 * The Flower object from the Plant class
 */
public class Flower extends Plant {
	
	char sym = super.getSymbol();
	
	//the hardcoded representation of the Flower
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
	public Flower (String name, int rSize, int pSize) {
		super(name, rSize, pSize, "flower");
		super.updatePlot(2,2,super.getSymbol());
	}
	
	@Override
	public String getClassName() {
		return "flower";
	}
	
	@Override
	public Color getColor() {
		return Color.rgb(100, 255, 100, 1);
	}

	/*
	 * Displays the 5x5 gird for this plant object
	 * @params int x,y for location of each quares on the GUI
	 */
	@Override
	public void display(GraphicsContext gc, int x, int y) {
		int cellSize = 20;
		char[][] currentSize = super.getPlot();
	
		// offsets based on cell size
		int xOffset = x * cellSize * 5;
		int yOffset = y * cellSize * 5;
	
		// loop through the 5x5 grid to display each cell
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				if (currentSize[row][col] == sym) {
					// draw the full-size filled cell for the plant part
					gc.setFill(getColor());
					gc.fillRect(xOffset + col * cellSize, yOffset + row * cellSize, cellSize, cellSize);
				} else {
					// draw a smaller, lighter version in empty cells
					gc.setFill(getColor().brighter().brighter());
					double smallerSize = cellSize / 2.0;
					double xCenter = xOffset + col * cellSize + (cellSize - smallerSize) / 2;
					double yCenter = yOffset + row * cellSize + (cellSize - smallerSize) / 2;
					gc.fillRect(xCenter, yCenter, smallerSize, smallerSize);
				}
			}
		}
	}

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