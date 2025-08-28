
/*
 * File: AirportInfo.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This program reads in airport arrival and departure data from a csv, 
 *          It parses the info, geing the user the option to print the MAX flights
 *          from an airport, get the DEPARTURE(s) and their destinations, and get
 *          the LIMIT representing the airports with >= n amount of flight (limit is
 *          defined by the user).
 * 
 *          To run the program, compile the code in terminal, then use the following
 *          line to run "java AirportInfo *infile* *COMMAND* *optional*".
 */

package com.gradescope.airportinfo;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class AirportInfo {

    /* finds airports with total flights equal or greater than the user input limit
     * @param airportCount (HashMap<String, Integer>) 
     * @returns String representing all airports with flights >= limit*/
    public static String getLimits(Integer max, HashMap<String, Integer> airportCount) {
        // create arrayList for all keys and a arrayList for keys >= limit
        ArrayList<String> keyArray = new ArrayList<String>(airportCount.keySet());
        ArrayList<String> limitList = new ArrayList<>();

        //System.out.println(destinations); //visual check for keys
        for (String key : keyArray) {
            //if max amount of dest. is larger, reset maxDestinations and set new max value
            if (airportCount.get(key) > max) {
                limitList.add(key);
            }
        }
        //sort items for printing
        Collections.sort(limitList);

        //itterate over limitList and append values to finalString
        String printString = "";
        for (String item : limitList){
            printString += (item + " - " + airportCount.get(item) + "\n");
        }
        return printString;
    }

    /* shows the departures for each airport (HashMap<String, String>)
     * @param destinations (HashMap<HashMap<String, String>) where key = departure airport and value = destination airport
     * @returns String showing which airports fly to which destinations */
    public static String getDepartures(HashMap<String, String> destinations) {
        //create list for keys and then sort alphabetically
        ArrayList<String> sortedKeys = new ArrayList<>(destinations.keySet());
        Collections.sort(sortedKeys);

        //itterate over limitList and append values to finalString
        String printString = "";
        for (String key : sortedKeys) {
            printString += (key + " flies to " + destinations.get(key) + "\n");
        }
        return printString.toString();
    }

     /* returns the airports with the most max arriving flights
     * @param destinations (HashMap<HashMap<String, Integer>) map of key airport name and value # arriving flights
     * @returns (String) of the airports with the max flights */
    public static String getMax(HashMap<String, Integer> airportCount) {
        int max = Integer.MIN_VALUE;
        ArrayList<String> keyArray = new ArrayList<String>(airportCount.keySet());
        ArrayList<String> maxFlights = new ArrayList<>();
        //System.out.println(destinations); //debug comment
        for (String key : keyArray) {
            // if max amount of dest. is larger, reset maxDestinations and set new max value
            if (airportCount.get(key) > max) {
                max = airportCount.get(key);
                maxFlights.clear();
                maxFlights.add(key);
            }
            // if tied for max destinations, add to maxDestinations
            else if (airportCount.get(key) == max) {
                maxFlights.add(key);
            }
        }
        //sort and return
        Collections.sort(maxFlights);

        // loop through maxFlight to generate output string
        String airportNameList = "";
        for (String item : maxFlights){
            airportNameList += " " + item;
        }
        return ("MAX FLIGHTS " + max + " :" + airportNameList);
    }

     /* creates a hashmap of key with airport name, and value with # of flights
     * @param fileName (String) used to open and read csv file
     * @returns airportCount(HashMap<String, Integer>) maps the airport with the integer amount of arrivals */
    public static HashMap<String, Integer> getAirportCount(String fileName) throws FileNotFoundException {
        HashMap<String, Integer> airportCount = new HashMap<>();
        File myFile = new File(fileName);
        Scanner scanner = new Scanner(myFile);
        //skip info line of csv
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        //split csv line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] csvData = line.split(",");

            // add destination airport key (element 4) then add value
            if (!airportCount.containsKey(csvData[4])) {
                airportCount.put(csvData[4], 0);
            }
            airportCount.put(csvData[4], airportCount.get(csvData[4]) + 1);
            // add source airport key (element 2) then add value
            if (!airportCount.containsKey(csvData[2])) {
                airportCount.put(csvData[2], 0);
            }
            airportCount.put(csvData[2], airportCount.get(csvData[2]) + 1);
        }
        scanner.close();
        return airportCount;
    }

     /* creates a hashmap for the departure airport, and a string of arrival airports
     * @param fileName (String) used to open and read csv file
     * @returns destinations (Hashmap<String, String>) the hashmap of departure airport and its destinations. */
    public static HashMap<String, String> getDestinations(String fileName) throws FileNotFoundException {
        HashMap<String, String> destinations = new HashMap<>();
        File myFile = new File(fileName);
        Scanner scanner = new Scanner(myFile);

        // skip info line of csv
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        // split csv line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] csvData = line.split(",");

            // add source airport key (element 4) then add value
            if (!destinations.containsKey(csvData[2])) {
                destinations.put(csvData[2], csvData[4]);
            }
            // if source airport exists, append the next destination to value string
            else {
                destinations.put(csvData[2], destinations.get(csvData[2]) + " " + csvData[4]);
            }
        }
        scanner.close();
        return destinations;
    }

    public static void main(String[] args) throws FileNotFoundException {
        
        HashMap<String, String> destinations = getDestinations(args[0]);
        HashMap<String, Integer> airportCount = getAirportCount(args[0]);
        
        if (args[1].equals("MAX")) {
            System.out.println(getMax(airportCount));
        }
        
        if (args[1].equals("DEPARTURES")) {
            System.out.println(getDepartures(destinations));
        }
        
        if (args[1].equals("LIMIT")) {
            System.out.println(getLimits(Integer.valueOf(args[2]), airportCount));
        }
    }
}