/* File: App.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024, Adrianna Picoral
 * Purpose: This program simulates a garden by generating 
 * 			5x5 plots for plant objects to grow. All plots are 
 * 			held together in the Garden object, which is used as
 * 			a call for plot manipulation (ie. removal of plant, 
 * 			grow plant...)
 * 
 *          To run the program, simply run this application file
 *          using javaFX libaraies.
 *          
 *          The infile is declared on lines 54 and 78, and should use the
 *          commands available in the Garden,java file.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    // variables that will be read in from file
    private static double delay;
    private static int rows;
    private static int cols;

    // constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 20;
    private final static int PLOT_SIZE = RECT_SIZE * 5;

    // temporary constants for starter code
    private int SIZE_ACROSS;
    private int SIZE_DOWN;

    public static void main(String[] args) {
        launch(args);
    }

    /*
     * Starts the GUI, and sets variables for the window
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        //File path and reade
        File myFile = new File("src/Garden.in");
        Scanner fileScanner = new Scanner(myFile);

        //First 3 lines set the rows, cols, a delay
        rows = Integer.parseInt(fileScanner.nextLine().split(" ")[1]);
        cols = Integer.parseInt(fileScanner.nextLine().split(" ")[1]);
        delay = Double.parseDouble(fileScanner.nextLine().split(" ")[1]);

        //the size each plot can be
        SIZE_DOWN = rows * PLOT_SIZE;
        SIZE_ACROSS = cols * PLOT_SIZE;

        TextArea cmd = new TextArea();
        GraphicsContext graphics = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN,
                cmd);

        primaryStage.show();
        simulateGarden(graphics, cmd); //main simulation call here
        fileScanner.close();
    }

    /*
     * Simulates the garden using the commands read in from the infile path
     *  @params, the text field and the graphical interface
     */
    private void simulateGarden(GraphicsContext graphics, TextArea textArea) throws FileNotFoundException {
        File myFile = new File("src/Garden.in");
        Scanner fileScanner = new Scanner(myFile);
    
        // skip the first 3 lines (cols, rows, delay)
        for (int i = 0; i < 3; i++) {
            fileScanner.nextLine();
        }
        
        // set color
        Color bgClr = Color.rgb(0, 0, 0, 1);
        RunGarden gardenCmd = new RunGarden(graphics, rows, cols, PLOT_SIZE, RECT_SIZE, bgClr);
    
        // initialize the PauseTransition
        PauseTransition wait = new PauseTransition(Duration.seconds(delay));
        
        // define the action when the wait period is over
        wait.setOnFinished((e) -> {
            if (fileScanner.hasNextLine()) {
                String curCmd = fileScanner.nextLine();
                if (!curCmd.isEmpty()) {
                    gardenCmd.parseCmd(curCmd, graphics, textArea, PLOT_SIZE, RECT_SIZE);
                }
                wait.playFromStart();  // Restart the wait transition
            } else {
                wait.stop();           // Stop the wait if there are no more lines
                fileScanner.close();   // Close the scanner only when done processing
            }
        });
    
        // start the PauseTransition
        wait.play();
    }

    /**
     * Sets up the whole application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
     * Notes: You shouldn't need to modify this method.
     * 
     * @param primaryStage
     *            Reference to the stage passed to start().
     * @param canvas_width
     *            Width to draw the canvas.
     * @param canvas_height
     *            Height to draw the canvas.
     * @param command
     *            Reference to a TextArea that will be setup.
     * @return Reference to a GraphicsContext for drawing on.
     */
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width,
            int canvas_height, TextArea command) {
        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Canvas(pixels across, pixels down)
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);

        // Command TextArea will hold the commands from the file
        command.setPrefHeight(TEXT_SIZE);
        command.setEditable(false);

        // Place the canvas and command output areas in pane.
        p.setCenter(canvas);
        p.setBottom(command);

        // title the stage and place the pane into the scene into the stage
        // change this to have your name on it (required for when recording
        // the demonstation video)
        primaryStage.setTitle("Kieran Chafin - Garden");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }

    // Helper method to draw a rectangular tile and output info in given
    // text area.
    /*
    private void drawTileDebug(TextArea command, GraphicsContext gc,
            String colorname, int x, int y, int size) {
        command.appendText("drawTileDebug: x=" + x + ", y = " + y + "\n");
        Color c = Color.valueOf(colorname);
        gc.setFill(c);
        gc.fillRect(x, y, size, size);
    }
    */
}