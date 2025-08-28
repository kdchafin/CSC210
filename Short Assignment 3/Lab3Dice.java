
package com.gradescope.lab03;
import java.util.ArrayList;

public class Lab3Dice {

    public static void roll(int size, String currSolution, ArrayList<String> allSolutions) {

        if (currSolution.length() == size) {
            allSolutions.add(currSolution);
            return;
        }
        else {
            for (int i = 1; i < 7; i ++) {
                roll(size, currSolution + String.valueOf(i), allSolutions);
            }
        }
    }
}