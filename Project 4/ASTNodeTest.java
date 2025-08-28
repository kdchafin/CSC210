package com.gradescope.bool_exp;
//package bool_exp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ASTNodeTest {

    @Test
    void testNullChildren() {
        ASTNode nandNode = ASTNode.createNandNode(null, null);
        assertNotNull(nandNode);
        assertTrue(nandNode.isNand());
        assertNull(nandNode.child1);
        assertNull(nandNode.child2);
    }

    @Test
    void testWithChildren() {
        ASTNode leftChild = ASTNode.createIdNode("A");
        ASTNode rightChild = ASTNode.createIdNode("B");
        ASTNode nandNode = ASTNode.createNandNode(leftChild, rightChild);
        
        assertNotNull(nandNode);
        assertTrue(nandNode.isNand());
        assertEquals(leftChild, nandNode.child1);
        assertEquals(rightChild, nandNode.child2);
    }

    @Test
    void testWithIdNode() {
        ASTNode idNode = ASTNode.createIdNode("variableX");
        assertNotNull(idNode);
        assertTrue(idNode.isId());
        assertEquals("variableX", idNode.getId());
        assertNull(idNode.child1);
        assertNull(idNode.child2);
    }

    @Test
    void testIsNandOnIdNode() {
        ASTNode idNode = ASTNode.createIdNode("variableY");
        assertFalse(idNode.isNand());
    }

    @Test
    void testIsIdOnNandNode() {
        ASTNode nandNode = ASTNode.createNandNode(null, null);
        assertFalse(nandNode.isId());
    }

    @Test
    void testGetIdOnIdNode() {
        ASTNode idNode = ASTNode.createIdNode("testId");
        assertEquals("testId", idNode.getId());
    }
}