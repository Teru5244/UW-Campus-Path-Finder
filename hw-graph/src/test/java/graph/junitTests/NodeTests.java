package graph.junitTests;

import graph.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTests {

    private final Node<String> n1 = new Node<>("a");
    private final Node<String> n2 = new Node<>("b");
    private final Node<String> n3 = new Node<>("b");
    private final Node<String> n4 = new Node<>("a");

    @Test
    public void testGetData() {
        assertEquals("a", n1.getData());
        assertEquals("b", n2.getData());
        assertEquals("b", n3.getData());
        assertEquals("a", n4.getData());
    }

    @Test
    public void testEquals() {
        assertEquals(n2, n3);
        assertEquals(n1, n4);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(n1, n3);
        assertNotEquals(n2, n4);
        assertNotEquals("1", n1);
    }

}
