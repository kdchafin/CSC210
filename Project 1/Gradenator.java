
/*
 * File: Gradenator.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This java file takes a user input and outputs their grade grade 
 *          total. It takes a string out format "score score ...; coursename;
 *          weight%" where score is the points recieved on the coursework
 *          completed, and the weight is the % of the total grade that course
 *          is weighted. It outputs the final with respect to the total %
 *          recieved, ie. if 2 courses totaling 45% are read in, then it
 *          outputs the total percentage out of 45%
 */
package com.gradescope.gradenator;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Gradenator {
  /*
   * Promts user for filename, opens file, and creates the file object
   * 
   * @returns the file object myFile
   */
  public static File getFile () {
    // get the filename from the user and creates the file object
    Scanner userInputScanner = new Scanner(System.in);
    System.out.println("File name?");

    String fileName = userInputScanner.nextLine();
    File myFile = new File(fileName);

    userInputScanner.close();
    return myFile;
  }
  /*
   * This class trims the strings in an array
   * (used in both main and getPointTotalAvg)
   * @params myString (Array) array of string type elements to be trimmed
   * @returns an array of trimmed strings
   */
  public static String[] trimString (String[] myString) {
    // basic method for trimming whitespace off of elements in an array
    // used in main and in getTotalPoints
    String[] trimmedString = new String[myString.length];
    
    for (int x = 0; x < myString.length; x++) {
      trimmedString[x] = myString[x].strip();
    }
    return trimmedString;
  }
  /*
   * Averages points for section by converting to array and looping
   * @params nums (string) - string of grade numbers
   * @returns a double representing the point average
   */
  public static Double getPointTotalAvg (String nums) {
    // gets the point total average for each individual category.
    String[] numAry = trimString(nums.split(" "));
    double totalPoints = 0;

    // for loop itterates over all grades and adds them to totalPoints 
    for (int i = 0; i < numAry.length; i++) {
      totalPoints += Double.valueOf(numAry[i]);
    }
    // divides total by number of elements added
    return totalPoints / numAry.length;
  }
  /*
   * Removes the % sign and and converts to double for math use
   * @params percent (string)
   * @returns Double for use in addition in main
   */
  public static Double getWeight(String percent) {

  // using replace to remove %, then set datatype to double
    double formattedPercent = Double.valueOf(
      percent.replace("%", "")
      );

    return formattedPercent;
  }

  public static void main(String[] args) throws FileNotFoundException {
    // gets and opens file
    File myFile = getFile();
    Scanner fileScanner = new Scanner(myFile);
    // variables to store the final grade % and the denominator total
    double finalGrade = 0;
    double finalWeight = 0;

    //reading in file lines
    while (fileScanner.hasNextLine()) {
      String line = fileScanner.nextLine();
      String[] gradeItems = trimString(line.split(";"));

      //declaring values to hold grades and percents, then prints
      double categoryGrade = getPointTotalAvg(gradeItems[0]);
      double categoryWeight = getWeight(gradeItems[2]);
      System.out.format("%s; %s%%; avg=%.1f\n",
        gradeItems[1],
        categoryWeight,
        categoryGrade);
      
      // settign final total values
      finalGrade += categoryWeight * categoryGrade * .01;
      finalWeight += categoryWeight;
    }
    fileScanner.close();

    System.out.format(
    "TOTAL = %.1f%% out of %.1f%%", finalGrade, finalWeight);
  }
}