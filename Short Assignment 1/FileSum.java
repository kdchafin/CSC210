package com.gradescope.filesum;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSum {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner userInputScanner = new Scanner(System.in);
        System.out.print("Enter file name: ");

        String fileName = userInputScanner.nextLine();
        File myFile = new File(fileName);
        Scanner fileScanner = new Scanner(myFile);
        
        userInputScanner.close();

        int totalSum = 0;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] gradeItems = line.split(" ");

            for (int x = 0; x < gradeItems.length; x++) {
                gradeItems[x] = gradeItems[x].trim();
                totalSum += Integer.valueOf(gradeItems[x]);
            }
        }

        System.out.println(totalSum);
        fileScanner.close();
        userInputScanner.close();
    }
}