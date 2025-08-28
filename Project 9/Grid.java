//package com.gradescope.wordsearch;

import java.lang.reflect.AccessFlag.Location;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * This class golds the Grid that defineds the word search puzzle. It contains
 * a 2d list of Locataion Data objects, and is what the user interacts with when
 * playing word search.
 */
public class Grid {
    private int rows;
    private int cols;
    private LocationData[][] board;
    private Random random;

    /*
     * Sets the dimensions of the grid, sets the LocationData list, seeds the rand function
     * and generates a Character for every position on the board.
     */
    public Grid(Integer[] dimensions) {
        this.rows = dimensions[1];
        this.cols = dimensions[0];
        this.board = new LocationData[rows][cols];
        this.random = new Random();
        generateBlankBoard();
    }

    /*
     * Calls a method for adding a word based on its assigned orientation when created.
     */
    public void addWord(Word word) {
        switch (word.getOrientation()) {
            case HORIZONTAL:
                 placeHorizontal(word);
                 break;
            case VERTICAL: 
                placeVertical(word);
                break;
            case DIAGONAL: 
                placeDiagonal(word);
                break;
        }
    }

    //see if this position contains a letter in the guessed word
    public boolean hasWord(Word word, int x, int y, char direction) {
        if (board[y][x].hasWord(word) && directionCheck(word, direction)) {
            return true;
        }
        return false;
    }

    /*
     * this method checks that the player found the word in the correct orientation
     */
    private boolean directionCheck(Word word, char direction) {
        
        switch (word.getOrientation()) {
            case HORIZONTAL:
                if (direction == 'h') return true;
                break;
            case VERTICAL: 
                if (direction == 'v') return true;
                break;
            case DIAGONAL: 
                if (direction == 'd') return true;
                break;
        }
        return false;
    }

    //remove the word entirely from the board once guessed correctly
    public void removeWord(Word word) {
        for (int yPos = 0; yPos < rows; yPos++) {
            for (int xPos = 0; xPos < cols; xPos++) {
                if (board[yPos][xPos].hasWord(word)) {
                    board[yPos][xPos].removeWord(word);
                }
            }
        }
        return;
    }

    /*
     * Places a word horizontally on the board. Uses rand to find a random location
     */
    public void placeHorizontal(Word word) {
        //get a position to place to word at
        int xPos = getNewXPosition(word.getWord().length());
        int yPos = getNewYPosition(0);
        boolean hasBeenPlaced = false;
    
        while (!hasBeenPlaced) {
            //check if it can be placed at this location
            if (horizontalCheck(word.getWord(), xPos, yPos)) {
                for (int i = 0; i < word.getWord().length(); i++) {
                    char currentChar = word.getWord().charAt(i);
                    if (!board[yPos][xPos + i].isWord()) {
                        board[yPos][xPos + i] = new LocationData(currentChar);
                        board[yPos][xPos + i].addToWordList(word);
                }}
                hasBeenPlaced = true;
            } else {
                xPos = getNewXPosition(word.getWord().length());
                yPos = getNewYPosition(0);
            }
        }
    }
    /*
     * checks the boundries to ensure a horixzontal word can be placed
     */
    public boolean horizontalCheck(String word, int xPos, int yPos) {
        // Check if the word fits within the grid's boundaries
        if (xPos + word.length() > cols) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            LocationData position = board[yPos][xPos + i];
    
            // If position is not initialized or has a different character, return false
            if (position != null && position.isWord() && position.getCharacter() != currentChar) {
                return false;
        }}
        return true; // All positions are valid for placement
    }

    /*
     * Places a word vertically on the board. Uses rand to find a random location
     */
    public void placeVertical(Word word) {
        //get a position to place to word at
        int xPos = getNewXPosition(0);
        int yPos = getNewYPosition(word.getWord().length());
        boolean hasBeenPlaced = false;
    
        while (!hasBeenPlaced) {
            //check if it can be placed at this location
            if (verticalCheck(word.getWord(), xPos, yPos)) {
                for (int i = 0; i < word.getWord().length(); i++) {
                    char currentChar = word.getWord().charAt(i);
                    if (!board[yPos + i][xPos].isWord()) {
                        board[yPos + i][xPos] = new LocationData(currentChar);
                        board[yPos + i][xPos].addToWordList(word);
                }}
                hasBeenPlaced = true;
            } else {
                xPos = getNewXPosition(word.getWord().length());
                yPos = getNewYPosition(0);
            }
        }
    }
    
    /*
     * checks the boundries to ensure a vertical word can be placed
     */
    public boolean verticalCheck(String word, int xPos, int yPos) {
        // Check if the word fits within the grid's boundaries
        if (yPos + word.length() > rows) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            LocationData position = board[yPos + i][xPos];
    
            // If position is not initialized or has a different character, return false
            if (position != null && position.isWord() && position.getCharacter() != currentChar) {
                return false;
        }}
        return true; // All positions are valid for placement
    }

    /*
     * Places a word diagonally on the board. Uses rand to find a random location
     */
    public void placeDiagonal(Word word) {
        //get a position to place to word at
        boolean hasBeenPlaced = false;
    
        while (!hasBeenPlaced) {
            int xPos = getNewXPosition(word.getWord().length());
            int yPos = getNewYPosition(word.getWord().length());
            //check if it can be placed at this location
            if (diagonalCheck(word.getWord(), xPos, yPos)) {
                for (int i = 0; i < word.getWord().length(); i++) {
                    char currentChar = word.getWord().charAt(i);
                    if (!board[yPos + i][xPos + i].isWord()) {
                        board[yPos + i][xPos + i] = new LocationData(currentChar);
                        board[yPos + i][xPos + i].addToWordList(word);
                }}
                hasBeenPlaced = true;
            }
        }
    }
    
    /*
     * checks the boundries to ensure a diagonal word can be placed
     */
    public boolean diagonalCheck(String word, int xPos, int yPos) {
        // Check if the word fits within the grid's boundaries
        if (xPos + word.length() > cols || yPos + word.length() > rows) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            LocationData position = board[yPos + i][xPos + i];
    
            // If position is not initialized or has a different character, return false
            if (position != null && position.isWord() && position.getCharacter() != currentChar) {
                return false;
        }}
        return true;
    }
    
    //generates a completly blank board of rand chars
    //this is done initially at the beginning, and the words overwrite this when being placed
    private void generateBlankBoard() {
        for (int yPos = 0; yPos < rows; yPos++) {
            for (int xPos = 0; xPos < cols; xPos++) {
                board[yPos][xPos] = new LocationData(getRandomChar()); //getRandomChar());
            }
        }
    }

    //getters
    public int getNewXPosition(int offset) {
        return random.nextInt(cols - offset);
    }
    
    public int getNewYPosition(int offset) {
        return random.nextInt(rows - offset);
    }

    public Character getRandomChar() {
        return (char) ('A' + random.nextInt(26));
    }

    //string representation of the board, including row and col numbers and letters.
    @Override
    public String toString() {
        StringBuilder boardRepresentation = new StringBuilder();

        boardRepresentation.append("   "); //padding for row numbers

        //add row letters
        for (int col = 0; col < cols; col++) {
            boardRepresentation.append((char) ('a' + col) + " ");
        }
        boardRepresentation.append("\n");

        //add col numbers
        for (int yPos = 0; yPos < rows; yPos++) {
            boardRepresentation.append(String.format("%02d ", yPos));

            //then add the chars from the grid
            for (int xPos = 0; xPos < cols; xPos++) {
                boardRepresentation.append(board[yPos][xPos].getCharacter() + " ");
            }
            boardRepresentation.append("\n");
        }
        return boardRepresentation.toString();
    }
}