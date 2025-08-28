import java.util.Arrays;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

/*
 * This class holds all methods for parsing user input commands. Calls
 * the respective method in Garden.java, aswell as displaying the commands to
 * the textArea on the gui
 */
public class RunGarden {

    //the garden object
    private Garden garden;

    private static final List<String> vegetables = Arrays.asList("garlic","zucchini","tomato","yam","lettuce");
    private static final List<String> flowers = Arrays.asList("iris","lily","rose","daisy","tulip","sunflower");
    private static final List<String> trees = Arrays.asList("oak", "willow", "banana", "coconut", "pine");
    private static final List<String> berries = Arrays.asList("strawberry", "blueberry", "cloudberry", "blackberry");

    //constructor for garden variables
    public RunGarden(GraphicsContext graphics, int rows, int cols, int pSize, int rSize, Color bgClr) {
        this.garden = new Garden(rows, cols, pSize, rSize, bgClr);
		garden.displayEmptyGarden(graphics, rows, cols);
    }

    //catches first command, send to function for that specific action
    public void parseCommand(String command, GraphicsContext gc, TextArea textArea) {

        String[] cmds = command.split(" ");
        switch (cmds[0].toLowerCase()) {
            case "plant" -> plant(cmds, garden, textArea);
            case "print" -> print(garden);
            case "grow" -> grow(cmds, garden, textArea);
            case "harvest" -> harvest(cmds, garden, textArea);
            case "pick" -> pick(cmds, garden, textArea);
            case "cut" -> cut(cmds, garden, textArea);
        }
        //update after each command passes
        garden.display(gc);
    }

    //plant commands for placing plants
    public static void plant(String[] args, Garden garden, TextArea textArea) {
        printCommand(args, textArea);
        
        int[] cords = getCords(args[1]);
        
        //determine what plant type for plant creation
        if (vegetables.contains(args[2].toLowerCase())) {
            garden.addPlant(cords[0], cords[1], new Vegetable(args[2]), textArea);
        }
        if (flowers.contains(args[2].toLowerCase())) {
            garden.addPlant(cords[0], cords[1], new Flower(args[2]), textArea);
        }
        if (trees.contains(args[2].toLowerCase())){
            garden.addPlant(cords[0], cords[1], new Tree(args[2]), textArea);
        }
        if (berries.contains(args[2].toLowerCase())){
            garden.addPlant(cords[0], cords[1], new Berry(args[2]), textArea);
        }
    }

    //print function to print to terminal. used for debugging in gui
    public static void print(Garden garden) {
        System.out.println(garden);
    }

    //parses all possible grow commands
    public static void grow(String[] args, Garden garden, TextArea textArea) {
        printCommand(args, textArea);

        //only 1 possible argument of length 2 for grow
        if (args.length == 2) {
            garden.grow(Integer.parseInt(args[1]));
        }
        //else must be a plant at a location or specific type
        else if (isCords(args[1])) {
            int[] cords = getCords(args[1]);
            garden.grow(Integer.parseInt(args[2]), cords[0], cords[1], textArea);
        }
        else {
            garden.grow(Integer.parseInt(args[1]), args[2]);
        }
    }

    //parses all possible harvest commands
    public static void harvest(String[] args, Garden garden, TextArea textArea) {
        printCommand(args, textArea);

        //only 1 possible argument of length 1 for harvest
        if (args.length == 1) {
            garden.harvest();
        }
        //else must be harvesting a type or a location
        else if (isCords(args[1])) {
            int[] cords = getCords(args[1]);
            garden.harvest(cords[0], cords[1], textArea);
        }
        else {
            garden.harvest(args[1]);
        }
    }

    //parses all possible pick commands
    public static void pick(String[] args, Garden garden, TextArea textArea) {
        printCommand(args, textArea);

        //only 1 possible argument of length 1 for pick
        if (args.length == 1) {
            garden.pick();
        }
        //else must be picking a type or a location
        else if (isCords(args[1])) {
            int[] cords = getCords(args[1]);
            garden.pick(cords[0], cords[1], textArea);
        }
        else {
            garden.pick(args[1]);
        }
    }

    //parses all possible cut commands
    public static void cut(String[] args, Garden garden, TextArea textArea) {
        printCommand(args, textArea);

        //only 1 possible argument of length 1 for cut
        if (args.length == 1) {
            garden.cut();
        }
        //else must be cut a type or a location
        else if (isCords(args[1])) {
            int[] cords = getCords(args[1]);
            garden.cut(cords[0], cords[1], textArea);
        }
        else {
            garden.cut(args[1]);
        }
    }

    //determine if an argument is a set of cords
    public static Boolean isCords(String temp) {
        if (temp.contains(",")) {return true;}
        return false;
    }

    //converts string to [x,y] cordinate list
    public static int[] getCords(String cords) {
        int[] cordList = new int[2];

        cordList[0] = Character.getNumericValue(cords.charAt(1));
        cordList[1] = Character.getNumericValue(cords.charAt(3));

        return cordList;
    }

    //prints the commands entered by the user for textArea display
    private static void printCommand(String[] args, TextArea textArea) {
        String command = "> ";
        String spc = " ";

        switch (args.length) {
            case 1 -> command += args[0].toUpperCase();
            case 2 -> command += args[0].toUpperCase() +
                      spc +  args[1].toLowerCase();
            case 3 -> command += args[0].toUpperCase() + 
                      spc + args[1].toLowerCase() + 
                      spc + args[2].toLowerCase();
        }

        command += "\n";
        textArea.appendText(command);
        System.out.println(command);
    }
}