
/* File: BoolSat.java
 * Author: Kieran Chafin
 * Course: CSC 210. Fall 2024
 * Purpose: This program solves a symbolic equation consisting of 1, or multiple variables
 * 			and the logical operator NAND. It uses other defined classes to generate a 
 * 			binary tree of variables and NAND nodes, and uses recursion in this file
 * 			to read in the tree. It then evaluates the expression with all possible
 * 			true and false values for the variables. It prints the configurations that
 * 			satisfy the equation. optionally, the DEBUG opton prints all configs and their values.
 * 			
 * 
 *          To run the program, compile the code in terminal, then use the following
 *          line to run "java BoolSat *infile* <DEBUG*>.
 */

package com.gradescope.bool_exp;
//package bool_exp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class BoolSat {
	public static void main(String[] args) throws FileNotFoundException{
        if(args.length < 1 || args.length > 2){
            System.err.println(
                    "USAGE: java BoolSat <inputFile.txt> <DEBUG*>");
            System.exit(1);
        }

        // get the expression from the file
        String expression = null;
        Scanner s = new Scanner(new File(args[0]));
        expression = s.nextLine();

        System.out.println("input: " + expression);
        
        s.close();

        // call the parser to generate the AST for the expression
        ASTNode root = BoolSatParser.parse(expression);
        
        // get string output to print out
        String output;
        if (args.length == 2 && args[1].equals("DEBUG")) {
            output = getOuput(root, true);
        } else {
            output = getOuput(root, false);
        }
        
        // print output string
        System.out.println(output);
    }
	/* this method generates the configs for only true, and debug mode. It uses
	 * recursion and a binary tree to test all truth in a logical expression with NAND
	 * operators and multiple variables
	 * @param rootNode (ASTNode) the rootnode for the tree, debug (bool) if the user wants to see all expressions
	 * @return a string consisting of a formatted list of all expressions */
    public static String getOuput(ASTNode rootNode, boolean debug) {
    	String satisConfigs = "";
        String allConfigs = "";
        ArrayList<String> varList = generateVariables(rootNode);
        
        //loop the truth value for each item in varlist
        for (int i = 0; i < (1 << varList.size()); i++) {
            HashMap<String, Boolean> config = new HashMap<>();
            String configString = "";
            
            //loop through every item, checking both true and false (in reverse due to sorting)
            for (int j = varList.size() - 1; j >= 0; j--) {
                boolean value = (i & (1 << j)) != 0;
                //add key and value of truth to map. Also add to configString.
                config.put(varList.get(j), value);
                configString += varList.get(j) + ": " + value + (", ");
            }
            //remove last comma and space from end of string. then evaluate the config
            configString = configString.substring(0, configString.length() - 2);
            boolean result = evaluate(rootNode, config);
            //add true expressions to satisfaction string, add all expressions to allConfigs
            if (result) satisConfigs += configString.toString() + "\n";
            allConfigs += configString.toString() + ", " + result + "\n";
        }
        // return the string associated with the debug enable or disables, appends satvalue to front.
        String satValue = isSatisfiable(satisConfigs);
        if (debug) return satValue + allConfigs;
        else return satValue + satisConfigs;
    }
    
    /* Checks for a expressions satisfiability (if a true expression exists)
     * @param satisString (String), string of satisfiable configs. (empty if no satisfiable configs)
     * @return String of the satisfaction to be printed*/
    private static String isSatisfiable(String satisString) {
    	String satValue = "";
    	//if the satisfaction string has a config, then it is satisfiable
    	if (satisString.length() != 0) satValue = "SAT\n";
    	else satValue = "UNSAT\n";
    	
    	return satValue;
    }
    /* Generates a sorted list of variables to be used as keys in the getOuput hashmap
     * @param rootNode (ASTNode) the rootnode of the binary tree
     * @return varList, the hashset of unique variables */
    private static ArrayList<String> generateVariables (ASTNode rootNode) {
    	// Collect all unique variable names
        HashSet<String> variables = new HashSet<>();
        collectVariables(rootNode, variables);

        // Generate all configurations
        ArrayList<String> varList = new ArrayList<>(variables);
        Collections.sort(varList, Collections.reverseOrder());
        
        return varList;
    }
    /* recursively collects all unique id's in the tree to be used as hasmap keys.
     * @param node (ASTNode) tree root node, variables (HashSet) the unique id's found
     */
    private static void collectVariables(ASTNode node, HashSet<String> variables) { // working
        // Base case: if the node is a boolean variable, add it to the set
        if (node.isId()) {
            variables.add(node.getId()); //--------------
        } 
        // if the node isNand, recursively collect variables from its children
        else if (node.isNand()) {
            if (node.child1 != null) {
                collectVariables(node.child1, variables);
            }
            if (node.child2 != null) {
                collectVariables(node.child2, variables);
            }
        }
    }
    /* recursively evaluates all nodes in the tree with their counternode. Values stored in hashmap
     * @param node (ASTNode) the rootNode of the tree, config (hashmap) the values of the id's(keys)
     * @return boolean value of the evaluated expression*/
    private static boolean evaluate(ASTNode node, HashMap<String, Boolean> config) { // working
    	//base case, returns value for the node
        if (node.isId()) {
            return config.getOrDefault(node.getId(), false);
            
        } 
        //gets both children ofthe NAND node and evalutes
        else if (node.isNand()) {
        	
            boolean left = evaluate(node.child1, config);
            boolean right = evaluate(node.child2, config);
            return !(left && right);
        }
        return false;
    }
}
