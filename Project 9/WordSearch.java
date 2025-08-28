
/*
 * File: WordSearch.java
 * Author: Kieran
 * CSC210 Fall 2024 Picoral
 * Purpose: This program generates a wordsearch by generating a board
 *          of random chars, and then placing words onto the board. The
 *          words are placed horizontally, vertically and diagonally, and
 *          can overlap. 
 * 
 *          To run, compile the java file and run WordSearch in terminal
 *          -java WordSearch infile.txt-
 */

//package com.gradescope.wordsearch;

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
            Word word = new Word(fileReader.nextLine());
            words.add(word);
        }
        fileReader.close();
        addWordsToGrid(words, grid);
        startGame(grid, words);
    }
    /*
     * This method is where the player plays the game after setup.
     * It loops through untill every word has been found and removed from the list
     */
    private static void startGame(Grid grid, List<Word> words) {
        Scanner scanner = new Scanner(System.in);

        while (!words.isEmpty()) {
            System.out.println(grid);
            // get word, position and orientation from player.
            System.out.print("Enter word found: ");
            String word = scanner.nextLine().trim().toUpperCase(); // Read the word found
            System.out.print("Enter x: ");
            int x = Integer.parseInt(scanner.nextLine().trim()); // Read the x-coordinate
            System.out.print("Enter y: ");
            char temp = scanner.nextLine().toLowerCase().charAt(0);
            int y = temp - 'a';
            System.out.print("[H]orizontal [V]ertical [D]iagonal? ");
            char direction = scanner.nextLine().trim().toLowerCase().charAt(0); // Read direction
            //get the word object
            Word guessedWord = getWordFromList(word, words);
            //check that the word exists at that location and remove
            if (grid.hasWord(guessedWord, y, x, direction)) {
                words.remove(guessedWord);
                grid.removeWord(guessedWord);
                System.out.println("Word found and removed!");
            } else {
                System.out.println("Word not found or incorrect guess.");
            }
        }
        System.out.println("All words found!");
        return;
    }

    //checks to see if the word guessed is on the board
    private static boolean wordInList(String word, List<Word> words) {
        for (Word t : words) {
            if (t.getWord().equals(word)) return true;
        }
        return false;
    }

    // gets word object for comparison in the grid
    private static Word getWordFromList(String word, List<Word> words) {
        Word thisWord = null;
        for (Word t : words) {
            if (t.getWord().equals(word)) {
                thisWord = t;
                break;
            }
        }
        return thisWord;
    }

    //adds words in the list to the grid
    private static void addWordsToGrid(List<Word> words, Grid grid) {
        for (Word word : words) {
            grid.addWord(word);
        }
        return;
    }

    //gets row and col size for the board
    public static Integer[] getDimensions(String thisLine) {
        String[] parts = thisLine.split(" ");
        Integer[] dimensions = new Integer[2];

        dimensions[0] = Integer.parseInt(parts[0]);
        dimensions[1] = Integer.parseInt(parts[1]);

        return dimensions;
    }
}