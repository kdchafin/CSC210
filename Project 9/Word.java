//package com.gradescope.wordsearch;

import java.util.Random;

/*
 * This class holds the information for each word being placed on the grid.
 * It gives each new word a random placement style, as well as the string for the
 *  word itself
 */
public class Word {
    protected String word;
    protected Orientation orientation;

    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL
    }

    /*
     * Constructor for all words on the board. Each word gets a random placement method
     */
    public Word(String word) {
        this.word = word.toUpperCase();
        this.orientation = getRandomOrientation();
    }

    /*
     * Declares the placement method using rand
     */
    public Orientation getRandomOrientation() {
        Random random = new Random();
        int number = random.nextInt(3);

        if (number == 0) {
            return Orientation.HORIZONTAL;
        }
        else if (number == 1) {
            return Orientation.VERTICAL;
        }
        else {
            return Orientation.DIAGONAL;
        }
    }

    //getters and setters below
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return this.word;
    }
}
