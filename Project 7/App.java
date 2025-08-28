/*Program: GardenGUI
 * Author: Kieran Chafin
 * CSC 210, FALL 2024 (Picoral)
 * This program simulates a garden, which holds plant objects.
 *      These plants can planted, be grown, and be removed, all
 *      encompased by their own methods. The plants use an rgb valuation
 *      to make every plant a unique color
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
 * Application class holds all information for the gui output, including
 * the positioning of screen elements, handeling userinput, and updating
 * runtime variables to the display.
 */
public class App extends Application {

    // variables that will be read in from file
    private static int rows;
    private static int cols;

    // constants for the program
    private final static int RECT_SIZE = 20;
    private final static int PLOT_SIZE = RECT_SIZE * 5;
    private final static Color BACKGROUND_COLOR = Color.WHITE;

    // temporary constants for starter code
    private int SIZE_ACROSS;
    private int SIZE_DOWN;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a new window for row and column selection
        createSettingsWindow(primaryStage);
    }

    // settings window for rows and cols
    private void createSettingsWindow(Stage primaryStage) {
        // Create the new window
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Select Rows and Columns");

        // Create the combo boxes for rows and cols
        ComboBox<Integer> rowsComboBox = new ComboBox<>();
        ComboBox<Integer> colsComboBox = new ComboBox<>();
        for (int i = 1; i <= 16; i++) {
            rowsComboBox.getItems().add(i);
            colsComboBox.getItems().add(i);
        }

        // Set default selections (can also be modified)
        rowsComboBox.setValue(5);
        colsComboBox.setValue(5);

        // Create the confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            // Get selected rows and cols values
            rows = rowsComboBox.getValue();
            cols = colsComboBox.getValue();

            // Close the settings window and move to the main simulation
            settingsStage.close();
            setupMainSimulation(primaryStage);
        });

        // Set up the layout for the settings window
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
            new Label("Select Number of Rows:"),
            rowsComboBox,
            new Label("Select Number of Columns:"),
            colsComboBox,
            confirmButton
        );

        Scene settingsScene = new Scene(vbox, 300, 200);
        settingsStage.setScene(settingsScene);
        settingsStage.show();
    }

    // Method to set up the main simulation once rows and cols are selected
    private void setupMainSimulation(Stage primaryStage) {
        // the size each plot can be
        SIZE_DOWN = rows * PLOT_SIZE;
        SIZE_ACROSS = cols * PLOT_SIZE;

        TextArea cmd = new TextArea();
        TextField cmdInput = new TextField();
        Button button = new Button("Enter Command");
        cmdInput.setPromptText("Enter command...");

        GraphicsContext graphics = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN, cmd, cmdInput, button);

        primaryStage.show();
        simulateGarden(graphics, cmd, cmdInput, button); // main simulation call here
    }

    // handels user input on button click, parses the users typed commands
    private void simulateGarden(GraphicsContext gc, TextArea textArea, TextField cmdInput, Button button) {
        RunGarden garden = new RunGarden(gc, rows, cols, PLOT_SIZE, RECT_SIZE, BACKGROUND_COLOR);

        // define button press
        button.setOnAction(event -> {
            String command = cmdInput.getText();
            if (!command.isEmpty()) {
                cmdInput.clear();
                // send command to be processed
                garden.parseCommand(command, gc, textArea);
            }
        });
    }

    // sets up the location of all onscreen objects
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width, int canvas_height,
                                       TextArea command, TextField cmdInput, Button button) {
        BorderPane p = new BorderPane();
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);

        // command TextArea will hold the commands from the file
        command.setPrefWidth(250);
        command.setEditable(false);

        // Hbox to hold button, grows to size of container
        HBox bottomHBox = new HBox(0);
        HBox.setHgrow(cmdInput, javafx.scene.layout.Priority.ALWAYS);

        // define the HBox for button and cmd line
        bottomHBox.getChildren().addAll(cmdInput, button);
        bottomHBox.setAlignment(Pos.CENTER_LEFT);

        // gen canvas, lace button and command line box at bottom
        p.setCenter(canvas);
        p.setRight(command);
        p.setBottom(bottomHBox);

        primaryStage.setTitle("Kieran Chafin - Garden");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }
}