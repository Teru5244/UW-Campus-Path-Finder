package marvel.junitTests;

import marvel.*;
import graph.*;
import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarvelPathsTests {

    private Graph<String, String> g;
    private final List<Edge<String, String>> l = new ArrayList<>();

    @Before
    public void buildUpGraph() {
        g = MarvelPaths.buildGraph("campusMapUW.csv");
    }

    @Test
    public void testMarvelPathsPerformance() {
        // test size of the graph
        assertEquals(6, g.nodeSize());
        assertEquals(12, g.edgeSize());
        // test BFS search correctness
        l.add(new Edge<>(new Node<>("Red_Square"), "Memorial_Way"));
        l.add(new Edge<>(new Node<>("HUB"), "Grant_Lane"));
        l.add(new Edge<>(new Node<>("CSE"), "Stevens_Way"));
        assertEquals(l, MarvelPaths.BFS(new Node<>("LAW"), new Node<>("CSE"), g));
        // test BFS search not found
        assertNull(MarvelPaths.BFS(new Node<>("IMA"), new Node<>("CSE"), g));
        // test BFS search no start or end node
        assertNull(MarvelPaths.BFS(new Node<>("CSE2"), new Node<>("CSE"), g));
        assertNull(MarvelPaths.BFS(new Node<>("CSE"), new Node<>("CSE2"), g));
        assertNull(MarvelPaths.BFS(new Node<>("ECE"), new Node<>("CSE2"), g));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildGraphThrowException() {
        MarvelPaths.buildGraph(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSThrowExceptionWithNullGraph() {
        MarvelPaths.BFS(new Node<>(""), new Node<>(""), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSThrowExceptionWithNullStart() {
        MarvelPaths.BFS(null, new Node<>(""), g);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSThrowExceptionWithNullEnd() {
        MarvelPaths.BFS(new Node<>(""), null, g);
    }
}
