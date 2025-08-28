
package com.gradescope.medalinfo;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

public class MedalInfo {

    public static String getMax(HashMap<String, Integer> countryCount) {
        int max = Integer.MIN_VALUE;
        String name = null;

        // for each key in countryCount's keyset, declared as strings
        for (String key : countryCount.keySet()) {
            if (countryCount.get(key) > max) {
                max = countryCount.get(key);
                name = key;
            }
        }
        return (name + " had the most medals with a total of " + max + " medals.");
    }

    public static String getMin(HashMap<String, Integer> countryCount) {
        int min = Integer.MAX_VALUE;
        String name = "";
        
        for (String key : countryCount.keySet()) {
            if (countryCount.get(key)< min) {
                min = countryCount.get(key);
                name = key;
            }
        }
        return (name + " had the fewest medals with a total of " + min + " medal.");
    }

    public static String getCountry(HashMap<String, Integer> countryCount, String country) {
        if (countryCount.get(country) > 1){
            return (country + " had a total of " + countryCount.get(country) + " medals.");
        }
        else {
            return (country + " had a total of " + countryCount.get(country) + " medal.");
        }
    }

    public static HashMap<String, Integer> getMedalCount(String fileName) throws FileNotFoundException {
        HashMap<String, Integer> medalCounts = new HashMap<>();

        File myFile = new File(fileName);
        Scanner scanner = new Scanner(myFile);

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");
            String country = data[6].trim();

            medalCounts.put(country, medalCounts.getOrDefault(country, 0) + 1);
        }

        scanner.close();
        return medalCounts;
    }

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, Integer> countryCount = getMedalCount("medallists.csv");

        if (args[0].equals("MAX")) {
            System.out.println(getMax(countryCount));
        }

        if (args[0].equals("MIN")) {
            System.out.println(getMin(countryCount));
        }

        if (args[0].equals("COUNTRY")) {
            System.out.println(getCountry(countryCount, args[1]));
        }
    }
}