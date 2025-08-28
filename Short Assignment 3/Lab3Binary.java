
package com.gradescope.lab03;
import java.util.ArrayList;

public class Lab3Binary {
    public static void binary(int size, String bitString, ArrayList<String> allSolutions) {
        if (bitString.length() == size){
            allSolutions.add(bitString);
            return;
        }

        binary(size, bitString + "0", allSolutions);
        binary(size, bitString + "1", allSolutions);
    }
}