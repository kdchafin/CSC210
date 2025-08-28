
/* File: Anagrams.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This program takes in a word from the user, and a file of words, and
 *          generates anagrams up to length n. The file words are compared to the 
 *          user input word by recursing through every letter of the input word. They
 *          sorted, and then recursively built into anagrams and appended to a list of
 *          all solutions, where they are then printed. The max length of anagrams can
 *          be configured by the user.
 * 
 *          Run: to run use the following command:
 *                  thisfile.java wordlist.txt *WORD* *INT* 
 *          where word is the anagram seeding word, and int is the max length.
 */
package com.gradescope.anagrams;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Anagrams {

    /* reads in words from file, sets them to a hashset to remove duplicates
     * @param filename (String) name of the file path for words
     * @return wordlist (HashSet) the set of words from the file */
    public static HashSet<String> getWordList(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner fileScanner = new Scanner(file);
        HashSet<String> wordList = new HashSet<>();

        //read in line, add to hashset
        while (fileScanner.hasNextLine()) {
            String currentWord = fileScanner.nextLine();
            wordList.add(currentWord.trim());
        }
        fileScanner.close();
        return wordList;
    }

    /* gets the character array for all chars in the user entered word
     * @param word (String) the word to be made into anagrams
     * @return letterList (ArrayList) the list for all letters in the word */
    public static ArrayList<Character> getChars(String word) {  
        ArrayList<Character> letterList = new ArrayList<>();
        
        //get char and add to list
        for (char x : word.toCharArray()) letterList.add(x);
        
        //sorts char for ease of access later
        Collections.sort(letterList);
        return letterList;
    }

    /* gets all words that can potentially be represented as an anagram (letters balance out)
     *
     * @param allChar (ArrayList) the list is chars from getChars method
     * @param temp (string) user for the recursive building to comapre with validWords
     * @param validWords (HashSet) set of all words, built in getWordList
     * @param solutions (HashSet) the set of all words that can can be anagram words */
    public static void getCombinations(ArrayList<Character> allChars,
        String temp, HashSet<String> validWords, HashSet<String> solutions) {

        //if word is proper length and in th elist, add to solutions
        if (temp.length() >= 0 && validWords.contains(temp)) {
            solutions.add(temp);
        }
        //for loop for building the temp string
        for (int i = 0; i < allChars.size(); i++) {
            //get char from allChars, update allChars as remainingChars
            char chosenChar = allChars.get(i);
            ArrayList<Character> remainingChars = new ArrayList<>(allChars);
            //remove chosen char from List as to not accidentally use it again
            remainingChars.remove(i);
            //recur with the chosen character appended to temp
            getCombinations(remainingChars, temp + chosenChar, validWords, solutions);
        }
    }

    /* generates anagrams of maxAnas size using the solution words from getCombinations
     * @param sizeRemaining (int) the character limit that all words in the anagram must add up to
     * @param orderedSolution (ArrayList) all words that have been verified to be first sets for an anagram
     * @param letterString (String) the string representation of the user enteres word (used for rechecking words in the ordered list)
     * @param result (ArrayList) the currently being built anagram. is empty by default
     * @param maxAnas (int) defines max words that an anagram can contain
     * @param count (int) tracks number of recursive calls to govern the maxAnas limit
     * @param allResults (ArrayList) the list of all anagrams that satisfy all conditions */
    public static void getAnagrams(int sizeRemaining,
        ArrayList<String> orderedSolution, String letterString, 
        ArrayList<String> result, int maxAnas, int count,
        ArrayList<ArrayList<String>> allResults) {
        //base case for proper anagram, returns to line 109
        if ((sizeRemaining == 0)) {
            allResults.add(new ArrayList<>(result));
            return;
        }
        //basecase for improper anagram. returns to line 109, and gets backtracked at 112
        if ((sizeRemaining < 0) || count == maxAnas) return;
        //itterate through all possible solution words, taking the x'th word
        for (int x = 0; x < orderedSolution.size(); x++ ) {
            String currWord = orderedSolution.get(x);
            // check that the word is of proper length, has valid characters, and 
            if (isCurrWordValid(currWord, letterString, result)) {
                String newLetterString = removeChars(letterString, currWord);
                result.add(currWord);
                //recurse with word added. subtract words length, and increase count to track recursion depth.
                getAnagrams(sizeRemaining - currWord.length(), orderedSolution, newLetterString, 
                result, maxAnas, count + 1, allResults);
                //backtrack call
                result.remove(result.size() - 1);
            }
        }
    }

    /* Checks if the next available word in getAnagrams is valid in size and char content
     * @param currWord (String) word to check validity. letterString (String) available letters,
     * @param result (ArrayList) the current words in the anagram result
     * @return Boolean that decides if word is valid or not for placement in result List */
    public static Boolean isCurrWordValid(String currWord, String letterString, ArrayList<String> result) {
        ArrayList<Character> charList = new ArrayList<>();
        //check if word is in the current anagram
        if (result.contains(currWord)) return false;
        //add every char into the List
        for (char c : letterString.toCharArray()) {
            charList.add(c);
        }
        //see if every char in currWord is contained in charList, and remove if so.
        for (int x = 0; x < currWord.length(); x++) {
            char c = currWord.charAt(x);

            if (charList.contains(c)) charList.remove(Character.valueOf(c));
            else return false;
        }
        return true; // if all validations pass
    }
    
    /* removes chars from letterString using currWord. if letter in currord doesnt exist, 
     * @param letterString (String) letters available for words, currWord (String) the current word whoes chars are being removed
     * @return letterString (String) with removed letters */
    public static String removeChars(String letterString, String currWord) {
        //remove all chars from letterString
        for (int x = 0; x < currWord.length(); x++) {
            char c = currWord.charAt(x);

            int index = letterString.indexOf(c);
            // check that the letter exists in letterString
            if (index != -1) {
                //use substring to remove char from string
                letterString = letterString.substring(0, index) + letterString.substring(index + 1);
            }
        }
        return letterString;
    }

    public static void main(String[] args) throws FileNotFoundException {
        
        String wordList = args[0];
        String word = args[1];
        int maxAnas = Integer.valueOf(args[2]);
        if (maxAnas == 0) maxAnas = -1;  // set to -1 for no limit

        System.out.println("Phrase to scramble: " + word);
        
        HashSet<String> validWords = getWordList(wordList);
        HashSet<String> solutions = new HashSet<String>();
        ArrayList<Character> allChars = getChars(word);
        
        getCombinations(allChars, "", validWords, solutions);
        ArrayList<String> orderedSolution = new ArrayList<String>(solutions);
        Collections.sort(orderedSolution);
        
        System.out.println("\nAll words found in " + word + ":");
        System.out.println(orderedSolution);
        
        ArrayList<String> result = new ArrayList<String>();
        System.out.println("\nAnagrams for " + word + ":");
        ArrayList<ArrayList<String>> allResults = new ArrayList<ArrayList<String>>();
        getAnagrams(word.length(), orderedSolution, word, result, maxAnas, 0, allResults);
        for (int i = 0; i < allResults.size(); i++) System.out.println(allResults.get(i));
    }
}