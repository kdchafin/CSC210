//package com.gradescope.garden;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/*
 * Hold the Berry plant type from Plant
 */
public class Berry extends Plant {
	
	/*
	 * Initalizer for the berry object
	 */
	public Berry (String name, int rSize, int pSize) {
		super(name, rSize, pSize, "berry");
		super.updatePlot(2,2,super.getSymbol());
	}
	
	@Override
	public String getClassName() {
		return "berry";
	}
	
	@Override
	public Color getColor() {
		return Color.rgb(255, 100, 100, 1);
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