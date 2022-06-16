package graph.junitTests;

import graph.*;

import java.util.HashSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTests {

    private final Node<String> n1 = new Node<>("a");
    private final Node<String> n2 = new Node<>("b");
    private final Edge<String, String> e1 = new Edge<>(n1, "1");
    private final Edge<String, String> e2 = new Edge<>(n2, "2");
    private final HashSet<Node<String>> s1 = new HashSet<>();
    private final HashSet<Edge<String, String>> s2 = new HashSet<>();

    private final Graph<String, String> g = new Graph<>();

    @Test
    public void testZeroNodes() {
        // Test size of the graph
        assertEquals(0, g.nodeSize());
        assertEquals(0, g.edgeSize());
        assertTrue(g.isEmpty());
        // Test removeNode
        assertFalse(g.removeNode(n1));
    }

    @Test
    public void testOneNodeZeroEdges() {
        // Test addNode
        assertTrue(g.addNode(n1));
        assertFalse(g.addNode(n1));
        // Test size of the graph
        assertEquals(1, g.nodeSize());
        assertFalse(g.isEmpty());
        // Test getEdge()
        assertTrue(g.getEdges(n1).isEmpty());
        // Test getNodes()
        s1.add(n1);
        assertEquals(s1, g.getNodes());
        // Test contains
        assertTrue(g.containsNode(n1));
        // Test removeNode()
        assertTrue(g.removeNode(n1));
        assertFalse(g.removeNode(n1));
        // Test size of the graph
        assertEquals(0, g.nodeSize());
        assertEquals(0, g.edgeSize());
        assertTrue(g.isEmpty());
    }

    @Test
    public void testTwoNodesZeroEdges() {
        // Test addNode
        assertTrue(g.addNode(n1));
        assertTrue(g.addNode(n2));
        assertFalse(g.addNode(n1));
        // Test size of the graph
        assertEquals(2, g.nodeSize());
        assertFalse(g.isEmpty());
        // Test getEdge()
        assertTrue(g.getEdges(n1).isEmpty());
        assertTrue(g.getEdges(n2).isEmpty());
        // Test getNodes()
        s1.add(n1);
        s1.add(n2);
        assertEquals(s1, g.getNodes());
        // Test contains
        assertTrue(g.containsNode(n1));
        assertTrue(g.containsNode(n2));
        // Test removeNode()
        assertTrue(g.removeNode(n1));
        assertFalse(g.removeNode(n1));
        assertTrue(g.removeNode(n2));
        assertFalse(g.removeNode(n2));
        // Test size of the graph
        assertEquals(0, g.nodeSize());
        assertEquals(0, g.edgeSize());
        assertTrue(g.isEmpty());
    }

    @Test
    public void testOneNodeOneEdge() {
        // Test addNode
        assertTrue(g.addNode(n1));
        assertFalse(g.addNode(n1));
        // Test size of the graph
        assertEquals(1, g.nodeSize());
        assertFalse(g.isEmpty());
        // Test addEdge()
        assertTrue(g.addEdge(n1, n1, "1"));
        // Test getEdge()
        assertFalse(g.getEdges(n1).isEmpty());
        s2.add(e1);
        assertEquals(s2, g.getEdges(n1));
        // Test size of the edges
        assertEquals(1, g.edgeSize());
        // Test getChildNodes()
        s1.add(n1);
        assertEquals(s1, g.getChildNodes(n1));
        // Test removeEdge()
        assertTrue(g.removeEdge(n1, n1, "1"));
        // Test getEdge() after removing the edge
        assertTrue(g.getEdges(n1).isEmpty());
    }

    // This method tests for two nodes and two edges in a graph
    // with a self pointing edge case.
    @Test
    public void testTwoNodesTwoEdge() {
        // Test addNode
        assertTrue(g.addNode(n1));
        assertTrue(g.addNode(n2));
        assertFalse(g.addNode(n1));
        // Test size of the graph
        assertEquals(2, g.nodeSize());
        assertFalse(g.isEmpty());
        // Test addEdge()
        assertTrue(g.addEdge(n1, n1, "1"));
        assertTrue(g.addEdge(n1, n2, "2"));
        assertFalse(g.addEdge(n1, n2, "2"));
        // Test getEdge()
        assertFalse(g.getEdges(n1).isEmpty());
        s2.add(e1);
        s2.add(e2);
        assertEquals(s2, g.getEdges(n1));
        // Test size of the edges
        assertEquals(2, g.edgeSize());
        // Test getChildNodes()
        s1.add(n1);
        s1.add(n2);
        assertEquals(s1, g.getChildNodes(n1));
        // Test removeNode()
        assertTrue(g.removeNode(n2));
        assertFalse(g.addEdge(n1, n2, "2"));
        // Test removeEdge()
        assertTrue(g.removeEdge(n1, n1, "1"));
        assertFalse(g.removeEdge(n1, n2, "2"));
        assertFalse(g.removeEdge(n1, n2, "2"));
        // Test getEdge() after removing the edge
        assertTrue(g.getEdges(n1).isEmpty());
    }

}
