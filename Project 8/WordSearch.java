/*
 * File: WordSearch.java
 * Author: Kieran
 * CSC210 Fall 2024 Picoral
 * Purpose: This program generates a wordsearch by generating a board
 *          of random chars, and then placing words onto the board. The
 *          words are placed horizontally, vertically and diagonally, and
 *          can overlap. It outputs to "output_*input_file_name*.txt"
 */

package com.gradescope.wordsearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
 * The setup class for wordsearch. Reads in the file, gets the row and col parameters,
 * and prints the board to a text file.
 */
public class WordSearch {
    /*
     * Main takes in the file name, and generates a word list of word objects
     * that it reads in. It then calls for those words to be added to the grid.
     */
    public static void main(String[] args) throws IOException {

        File file = new File(args[0]);
        
        Scanner fileReader = new Scanner(file);
        Integer[] dimensions = getDimensions(fileReader.nextLine());

        //seeding the grid for wordsearch
        Grid grid = new Grid(dimensions);
        List<Word> words = new ArrayList<>();

        //reads in all words from the file
        while (fileReader.hasNextLine()) {
            String wordString = fileReader.nextLine();
            Word word = new Word(wordString);
            words.add(word);
        }
        fileReader.close();

        //adding words to grid
        for (Word word : words) {
            grid.addWord(word);
        }
        writeToFile(args[0], grid);
    }
    
    /*
     * Writes the string representation of the wordsearch grid to a file.
     */
    public static void writeToFile(String name, Grid grid) throws IOException {

        FileWriter fileWriter = new FileWriter("output_" + name);
        fileWriter.write(grid.toString());
        fileWriter.close();
    }

    /*
     * Gets the row and col size of the board.
     */
    public static Integer[] getDimensions(String thisLine) {
        String[] parts = thisLine.split(" ");
        Integer[] dimensions = new Integer[2];

        dimensions[0] = Integer.parseInt(parts[0]);
        dimensions[1] = Integer.parseInt(parts[1]);

        return dimensions;
    }
}