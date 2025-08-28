//package com.gradescope.wordsearch;

import java.util.ArrayList;

/*
* This class holds information for each location on the grid, namely
* the character at that position, and an array list that tracks what words
* are occupying that location.
*/
public class LocationData {
    private Character character;
    private ArrayList<Word> wordList;

    /*
     * LocationData for each x,y location on the grid. Is reresented by a character
     * The ArrayList holds Word objects and is used as a check for if a location can
     * be overwritten by a word, or subsequently, if 2 words can occupy with overlap.
     */
    public LocationData(Character character) {
        this.character = character;
        this.wordList = new ArrayList<>();
    }

    /*
     * Adds the word to word list when it places in this location
     */
    public void addToWordList(Word word) {
        wordList.add(word);
    }
    
    //Check if a word object exists in the arrayList
    public boolean hasWord(Word word) {
        if (wordList.contains(word)) return true;
        return false;
    }

    /*
     * Removes word from the array.
     * If array is empty, replace symbol, else there is overlap here
     */
    public void removeWord(Word word) {
        wordList.remove(word);
        if (wordList.isEmpty()) {
            this.setCharacter('*');
        }
    }

    //setters and getters below (and a bool check)
    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return this.character;
    }

    //unused
    public ArrayList<Word> getWordList() {
        return wordList;
    }

    public Boolean isWord() {
        return !wordList.isEmpty();
    }
}
