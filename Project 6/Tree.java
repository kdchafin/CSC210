//package com.gradescope.garden;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/*
 * Holds the Tree type for Plant
 */
public class Tree extends Plant {
	
	/*
	 * initalizer for the tree object
	 */
	public Tree (String name, int rSize, int pSize) {
		super(name, rSize, pSize, "tree");
		super.updatePlot(4,2,super.getSymbol());
	}
	
	@Override
	public String getClassName() {
		return "tree";
	}
	@Override
	public Color getColor() {
		return Color.rgb(100, 100, 255, 1);
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
				if (currentSize[row][col] == this.getSymbol()) {
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