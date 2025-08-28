import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * The plant object, which holds all necessary variables for each plant
 * Defines a plant as a 5x5 2d array of '.' and plant symbols
 */
abstract class Plant {
    private String name;
	private String family;
	private char symbol;
	private int size;
	private Color color;
	private char[][] plot = new char[5][5];

	//constructor for new plants
    public Plant (String name) {
		this.name = name;
		this.symbol = this.name.charAt(0);
		this.size = 1;
		this.makeEmptyPlot();
	}

	//overridden method for each individual plant type
	public abstract void grow(int amount);

	//updates the plot with a symbol at a position
	//used for grow command
	protected void updatePlot(int xPos, int yPos, char sym) {
		this.plot[xPos][yPos] = sym;
	}

	//checks if the plant can be harvested
	public Boolean isHarvestable() {
		if (this.family.equals("vegetable")) {return true;}
		return false;
	}

	//checks if the plant can be picked
	public Boolean isPickable() {
		if (this.family.equals("flower")) {return true;}
		return false;
	}

	//checks if the plant can be cut
	public Boolean isCuttable() {
		if (this.family.equals("tree")) {return true;}
		return false;
	}

	//checks what char is at a position for drawing to the screen.
	public Boolean isSymbol(int xPos, int yPos) {
		if (this.plot[xPos][yPos] != '.') {return true;}
		return false;
	}

	//getters
	public String getName() {return this.name;}
	public String getFamily() {return this.family;}
	public char getSymbol() {return this.symbol;}
	public int getSize() {return this.size;}
	public Color getColor() {return this.color;}

	//setters
	public void setFamily(String family) {this.family = family;}
	public void setSymbol(char symbol) {this.symbol = symbol;}
	public void setSize(int size) {
		if (size > 4) {this.size = 5;}
		else {this.size = size;}
	}
	public void setColor(Color color) {this.color = color;}

	//makes a new empty plant array
	private void makeEmptyPlot() {
		for (int xPos = 0; xPos < 5; xPos++) {
			for (int yPos = 0; yPos < 5; yPos++) {
				this.plot[xPos][yPos] = '.';}
	}}

	//printing function for garden. gets the entire list at an xPos
	public String getLine(int row) {
		String thisLine = "";
		
		for (int yPos = 0; yPos < 5; yPos++) {
			thisLine += plot[row][yPos];
		}
		return thisLine;
	}
	
	//used for displaying the plant in termial for debugging
	public String toString() {
		String temp = "";
		for (int xPos = 0; xPos < 5; xPos++) {
			for (int yPos = 0; yPos < 5; yPos++) {
				temp += this.plot[xPos][yPos];
			}
			temp += "\n";
		}
		return temp;
	}

	//draws the plant. Loops over the 5x5 array, and checks each symbol at each position
	//depending on the symbol depend son if its a full or half sized square

	public void display(GraphicsContext gc, int rSize, double xOffset, double yOffset) {
        for (int yPos = 0; yPos < 5; yPos++) {
            String plantLine = getLine(yPos);
            for (int xPos = 0; xPos < 5; xPos++) {
                char ch = plantLine.charAt(xPos);

                // draw small cubes for '.'
                if (ch == '.') {
                    gc.setFill(this.getColor());  // Large cube color
                	gc.fillRect(xOffset + xPos * 20 + 6, yOffset + yPos * 20 + 6, 8, 8);
                } else {
                    // draw larger cubes for other characters
					gc.setFill(this.getColor());  // Large cube color
					gc.fillRect(xOffset + xPos * 20, yOffset + yPos * 20, 20, 20);}
	}}}
}