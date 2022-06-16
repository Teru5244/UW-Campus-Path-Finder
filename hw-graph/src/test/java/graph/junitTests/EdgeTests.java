package graph.junitTests;

import graph.*;

import org.junit.Test;

import static org.junit.Assert.*;


public class EdgeTests {

    private final Node<String> n1 = new Node<>("a");
    private final Node<String> n2 = new Node<>("b");
    private final Edge<String, String>  e1 = new Edge<>(n1, "1");
    private final Edge<String, String>  e2 = new Edge<>(n2, "2");
    private final Edge<String, String>  e3 = new Edge<>(n1, "1");
    private final Edge<String, String>  e4 = new Edge<>(n2, "2");

    @Test
    public void testGetLabel() {
        assertEquals("1", e1.getLabel());
        assertEquals("2", e2.getLabel());
        assertEquals("1", e3.getLabel());
        assertEquals("2", e4.getLabel());
    }

    @Test
    public void testGetChild() {
        assertEquals(n1, e1.getChild());
        assertEquals(n2, e2.getChild());
        assertEquals(n1, e3.getChild());
        assertEquals(n2, e4.getChild());
    }

    @Test
    public void testEquals() {
        assertEquals(e1, e3);
        assertEquals(e2, e4);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(e1, e2);
        assertNotEquals(e3, e4);
        assertNotEquals("1", n1);
    }

}
