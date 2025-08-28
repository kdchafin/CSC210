import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

/*
 * The garden object, which holds all plant objects.
 * Contains plant objects in a 2d array of plant objects
 * This contains all the function necessary to manipulate plants
 * in the garden
 */
public class Garden {
    private int rows;
    private int cols;
    private int rSize;
    private int pSize;
    private Color bgClr;

    private Plant[][] garden;

    //constructor for generating an empty garden
    public Garden(int rows, int cols, int pSize, int rSize, Color bgClr) {
        this.rows = rows;
        this.cols = cols;
        this.pSize = pSize;
        this.rSize = rSize;
        this.bgClr = bgClr;
        garden = new Plant[rows][cols];
    }

    //adds plant at position
    public void addPlant(int xPos, int yPos, Plant plant, TextArea textArea) {
        if (xPos >= 0 && xPos < rows && yPos >= 0 && yPos < cols) {
            if (garden[xPos][yPos] == null) {
                garden[xPos][yPos] = plant;
                return;
            }
        }
        System.out.println("Can't plant there\n");
        textArea.appendText("Can't plant there\n");
    }

    //grows all plants
    public void grow(int amount) {
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {
                if (garden[xPos][yPos] != null) {garden[xPos][yPos].grow(amount);}
    }}}

    //grows plant at a specific position
    public void grow(int amount, int xPos, int yPos, TextArea textArea) {
        if (garden[xPos][yPos] != null) {garden[xPos][yPos].grow(amount);}
        else{
            System.out.println("Can't grow there.\n");
            textArea.appendText("Can't grow there.\n");
        }
    }

    //grows all plant of a common name or class
    public void grow(int amount, String name) {
        for(int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {

                if (garden[xPos][yPos] != null) {
                    if (garden[xPos][yPos].getName().equals(name) ||
                        garden[xPos][yPos].getFamily().equals(name)) {
                        garden[xPos][yPos].grow(amount);}
    }}}}

    //removes all vegetables plants
    public void harvest() {
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {

                if (garden[xPos][yPos] != null &&
                    garden[xPos][yPos].isHarvestable()) {
                    garden[xPos][yPos] = null;}
    }}}

    //removes all vegetables plants at a specific position
    public void harvest(int xPos, int yPos, TextArea textArea) {
        if (garden[xPos][yPos] != null &&
            garden[xPos][yPos].isHarvestable()) {
            garden[xPos][yPos] = null;}
        else {
            System.out.println("Can't harvest there.\n");
            textArea.appendText("Can't harvest there.\n");
        }
    }

    //removes all vegetables plants of a common name
    public void harvest(String name) {
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {

                if (garden[xPos][yPos] != null &&
                    garden[xPos][yPos].isHarvestable() &&
                    garden[xPos][yPos].getName().equals(name)) {
                    garden[xPos][yPos] = null;}
    }}}

    //picks all flowers
    public void pick() {
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {

                if (garden[xPos][yPos] != null &&
                    garden[xPos][yPos].isPickable()) {
                    garden[xPos][yPos] = null;}
    }}}

    //picks all flowers at a specific position
    public void pick(int xPos, int yPos, TextArea textArea) {
        if (garden[xPos][yPos] != null &&
            garden[xPos][yPos].isPickable()) {
            garden[xPos][yPos] = null;}
        else {
            System.out.println("Can't pick there.\n");
            textArea.appendText("pick't grow there.\n");
        }
    }

    //picks all flowers of a common name
    public void pick(String name) {
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {

                if (garden[xPos][yPos] != null &&
                    garden[xPos][yPos].isPickable() &&
                    garden[xPos][yPos].getName().equals(name)) {
                    garden[xPos][yPos] = null;}
    }}}

    //cut all trees 
    public void cut() {
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {

                if (garden[xPos][yPos] != null &&
                    garden[xPos][yPos].isCuttable()) {
                    garden[xPos][yPos] = null;}
    }}}

    //cuts all trees at a specific position
    public void cut(int xPos, int yPos, TextArea textArea) {
        if (garden[xPos][yPos] != null &&
            garden[xPos][yPos].isCuttable()) {
            garden[xPos][yPos] = null;}
        else {
            System.out.println("Can't cut there.\n");
            textArea.appendText("Can't cut there.\n");
        }
    }

    //cut all trees of a common name
    public void cut(String name) {
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int yPos = 0; yPos < cols; yPos++) {

                if (garden[xPos][yPos] != null &&
                    garden[xPos][yPos].isCuttable() &&
                    garden[xPos][yPos].getName().equals(name)) {
                    garden[xPos][yPos] = null;}
    }}}

    //Displays the garden in the terminal (for debugging)
    public String toString() {
        System.out.println("> PRINT\n");
        String temp = "";
        //row, then the line(col)
        for (int xPos = 0; xPos < rows; xPos++) {
            for (int line = 0; line < 5; line++) {

                //itterate over plants of yPos
                for (int yPos = 0; yPos < cols; yPos++) {
                    if (garden[xPos][yPos] == null) {temp += ".....";}
                    else {temp += garden[xPos][yPos].getLine(line);}
                }
                temp += "\n";
        }}
        return temp;
    }

    //below is all new gui commands
    //I basically wanted the gui simulation to be as least invasive as possible
    //so i could still have the terminal ouput untouched for

    //displays the empty garden for first call to set rows and cols
    public void displayEmptyGarden(GraphicsContext gc, int rows, int cols) {
        for (int yPos = 0; yPos < rows; yPos++) {
			for (int xPos = 0; xPos < cols; xPos++) {
                //offsets for each plot for gui
                double xOffset = xPos * pSize;
                double yOffset = yPos * pSize;
                displayEmptyPlot(gc, xOffset, yOffset);
    }}}

    //prints an empty plot of small black cubes
    public void displayEmptyPlot(GraphicsContext gc, double xOffset, double yOffset) {
		for (int yPos = 0; yPos < 5; yPos++) {
			for (int xPos = 0; xPos < 5; xPos++) {
				gc.setFill(Color.BLACK); //smaller, temporary cube (for visual)
                gc.fillRect(xOffset + xPos * rSize + 8, yOffset + yPos * rSize + 8, 4, 4);
	}}}

    //displays the garden to the screen
    public void display(GraphicsContext gc) {
        // set background color for the canvas
        gc.setFill(bgClr);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // loop through all garden plots
        for (int yPos = 0; yPos < rows ; yPos++) {
            for (int xPos = 0; xPos < cols; xPos++) {
                double yOffset = xPos * pSize;  // 5 cells per plot in width
                double xOffset = yPos * pSize;  // 5 cells per plot in height

                if (garden[xPos][yPos] == null) {
                    displayEmptyPlot(gc, xOffset, yOffset);
                }
                else {garden[xPos][yPos].display(gc, rSize, xOffset, yOffset);}
    }}}
}