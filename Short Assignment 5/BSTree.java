package com.gradescope.bst;

import java.util.ArrayList;

public class BSTree implements BTNode {
    // binary tree nodes have two children
    private BTNode left, right;
    // set the value to integer
    private int value;
    
    // constructor of a BST, root with null children
    public BSTree(int value) {
        left = null;
        right = null;
        this.value = value;
    }
    
    // getters
    public int getValue() {
        return value;
    }
    
    public BTNode getLeft() {
        return left;
    }
    
    public BTNode getRight() {
        return right;
    }
    
    // setters
    public void setRight(BTNode node) {
        right = node;
    }
    
    public void setLeft(BTNode node) {
        left = node;
    }
    
    // add node, check where node goes
    public void addNode(BTNode node, int value) {
        if (value < node.getValue()) {
            if (node.getLeft() == null) node.setLeft(new BSTree(value));
            else addNode(node.getLeft(), value);
        } else {
            if (node.getRight() == null) node.setRight(new BSTree(value));
            else addNode(node.getRight(), value);
        }
    }
    
    // search
    public boolean search(BTNode node, int value) {
        // end of tree, base case, value not in BST
        if (node == null) return false; 
        
        // found value
        if (node.getValue() == value) return true;
        
        // keep looking
        if (value < node.getValue()) return search(node.getLeft(), value);
        else return search(node.getRight(), value);
        
    }

	@Override
	public void toList(BTNode node, ArrayList<Integer> result) {
        // end of tree, base case, value not in BST
        if (node == null) return;
        
        toList(node.getLeft(), result);
        result.add(node.getValue());
        toList(node.getRight(), result);
	}
}