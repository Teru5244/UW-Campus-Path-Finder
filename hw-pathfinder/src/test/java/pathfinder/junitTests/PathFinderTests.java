package pathfinder.junitTests;

import graph.*;
import pathfinder.PathFinder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class PathFinderTests {

    private Graph<String, Double> g;
    private Graph<String, Double> negativeEdge;
    private List<Edge<String, Double>> l = new ArrayList<>();

    @Before
    public void buildUpGraph() {
        // initialization
        g = new Graph<>();
        negativeEdge = new Graph<>();
        // 18560
        // build up implementation graph
        g.addNode(new Node<>("n1"));
        g.addNode(new Node<>("n2"));
        g.addNode(new Node<>("n3"));
        g.addNode(new Node<>("n4"));
        g.addEdge(new Node<>("n1"), new Node<>("n2"), 2.0);
        g.addEdge(new Node<>("n2"), new Node<>("n3"), 2.0);
        g.addEdge(new Node<>("n1"), new Node<>("n3"), 1.0);
        // build up negative edge graph
        negativeEdge.addNode(new Node<>("n1"));
        negativeEdge.addNode(new Node<>("n2"));
        negativeEdge.addEdge(new Node<>("n1"), new Node<>("n2"), -0.5);
    }

    @Test
    public void testImplementation() {
        // no path case
        assertNull(PathFinder.minCostPath(new Node<>("n1"), new Node<>("n4"), g));
        // normal case
        l.add(new Edge<>(new Node<>("n3"), 1.0));
        assertEquals(l, PathFinder.minCostPath(new Node<>("n1"), new Node<>("n3"), g));
        l.remove(new Edge<>(new Node<>("n3"), 1.0));
        // self pointing case
        l.add(new Edge<>(new Node<>("n1"), 0.0));
        assertEquals(l, PathFinder.minCostPath(new Node<>("n1"), new Node<>("n1"), g));
    }

    @Test(expected = RuntimeException.class)
    public void testThrowExceptionWithNegativeEdges() {
        PathFinder.minCostPath(new Node<>("n1"), new Node<>("n2"), negativeEdge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionWithNullGraph() {
        PathFinder.minCostPath(new Node<>(""), new Node<>(""), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionWithNullStart() {
        PathFinder.minCostPath(null, new Node<>(""), g);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionWithNullEnd() {
        PathFinder.minCostPath(new Node<>(""), null, g);
    }

}
