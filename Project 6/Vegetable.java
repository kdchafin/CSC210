//package com.gradescope.garden;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * Holds the Vegetable plant type fro Plant
 */
public class Vegetable extends Plant{
	/*
	 * vegetable initalizer to declare a new veggie
	 */
	public Vegetable (String name, int rSize, int pSize) {
		super(name, rSize, pSize, "vegetable");
		super.updatePlot(0,2,super.getSymbol());
	}
	//getters
	@Override
	public String getClassName() {
		return "vegetable";
	}

	@Override
	public Color getColor() {
		return Color.rgb(100, 100, 100, 1);
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
					gc.setFill(getColor().brighter().brighter()); // Lighter shade of plant color
					double smallerSize = cellSize / 2.0; // Half the size of the full cell
					double xCenter = xOffset + col * cellSize + (cellSize - smallerSize) / 2;
					double yCenter = yOffset + row * cellSize + (cellSize - smallerSize) / 2;
					gc.fillRect(xCenter, yCenter, smallerSize, smallerSize);
				}
			}
		}
	}

	/*
	 * The grow method, which incriments the size of the plant object
	 * @param int num, the amoutn to grom
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