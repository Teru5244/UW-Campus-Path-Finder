package graph;

import java.util.HashSet;
import java.util.HashMap;

/**
 * This is the immutable class of the Graph which represents
 * a complete graph that is consisted with multiple nodes and
 * edges with labels. It will be interpreted as a map/dictionary
 * that have key as the individual nodes and value as the edges
 * that belongs to the corresponding nodes.
 */
public class Graph<T, E> {

    // Abstract Function:
    // AF(this) = a directed graph with labels on edges.
    // All nodes are in this.graph.keySet()
    // All the edges that belong to a node are in the set of edges
    // corresponding to the node.
    //
    // Representation Invariants:
    // graph != null && nodes (keys) in graph are all not null &&
    // the set of edges of a node is not null &&
    // the edges in the set of edges of a node are not null &&
    // size of child nodes of a node is no bigger than the size of
    // nodes in the graph

    private final HashMap<Node<T>, HashSet<Edge<T, E>>> graph;

    /**
     * Constructs a new Graph with no initial data.
     *
     * @spec.effects construct a new graph with no values within it.
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Adds a new node to the graph. Returns true if add succeeds.
     *
     * @param node The node to be added as a Node object.
     * @return true if add succeeds, otherwise false.
     * @spec.requires param node is not null
     * @spec.modifies add a new node to graph.
     * @spec.effects The graph is not empty.
     */
    public boolean addNode(Node<T> node) {
        if (!graph.containsKey(node) && node != null) {
            graph.put(node, new HashSet<>());
            return true;
        }
        return false;
    }

    /**
     * Adds a new edge to a certain new start node with given end
     * node and a label.
     *
     * @param start The start node that to be added a new edge.
     * @param end The end node that the start node will point to.
     * @param label The label of the edge.
     * @return true if the edge is added successfully.
     * @spec.requires Node start and end are not null, label != null.
     * @spec.modifies add the edge with given parameters to graph.
     */
    public boolean addEdge(Node<T> start, Node<T> end, E label) {
        if (graph.containsKey(start) && graph.containsKey(end) && !graph.get(start).contains(new Edge<>(end, label))
                && start != null && end != null && label != null) {
            graph.get(start).add(new Edge<>(end, label));
            return true;
        }
        return false;
    }

    /**
     * Remove one of the node with the given parameter. It will
     * also remove all the edges of it and all the edges that
     * points to it.
     *
     * @param node The node to be removed.
     * @return true if the node is removed successfully.
     * @spec.requires Node node is not null.
     * @spec.modifies remove the node from graph.
     */
    public boolean removeNode(Node<T> node) {
        if (node != null && graph.containsKey(node)) {
            graph.remove(node);
            for (Node<T> n: graph.keySet()) {
                for (Edge<T, E> e: graph.get(n)) {
                    if (e.getChild().equals(node)) {
                        this.removeEdge(n, node, e.getLabel());
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Remove one of the edge with the given parameters.
     *
     * @param start The start node that needs to delete an edge.
     * @param end The end node that the start node is pointing to, in
     *            which the edge needs to be deleted.
     * @param label The label of the edge.
     * @return true if the edge is removed successfully.
     * @spec.requires Node start and end are not null and label != null.
     * @spec.modifies remove the edge with given parameters from graph.
     */
    public boolean removeEdge(Node<T> start, Node<T> end, E label) {
        if (graph.containsKey(start) && graph.get(start).contains(new Edge<>(end, label))
                && start != null && end != null && label != null) {
            graph.get(start).remove(new Edge<>(end, label));
            return true;
        }
        return false;
    }

    /**
     * Return all the edges that have node as the starting node.
     *
     * @param node The start node given.
     * @return All the edges of the starting node "node".
     * @throws IllegalArgumentException If node is not in graph.
     * @spec.requires node is not null.
     */
    public HashSet<Edge<T, E>> getEdges(Node<T> node) {
        if (!graph.containsKey(node)) {
            throw new IllegalArgumentException();
        }
        HashSet<Edge<T, E>> result = new HashSet<>(graph.get(node));
        return result;
    }

    /**
     * Returns all nodes of the graph as a set.
     *
     * @return The array of the nodes in the graph.
     */
    public HashSet<Node<T>> getNodes() {
        return new HashSet<>(graph.keySet());
    }

    /**
     * Returns a set of the child nodes of a given node.
     *
     * @param node the given node to be examined.
     * @return a set of child nodes of the given node
     * @throws IllegalArgumentException if given node is not in
     * the graph
     * @spec.requires node != null
     */
    public HashSet<Node<T>> getChildNodes(Node<T> node) {
        if (!graph.containsKey(node)) {
            throw new IllegalArgumentException();
        }
        HashSet<Node<T>> result = new HashSet<>();
        for (Edge<T, E> e: graph.get(node)) {
            result.add(e.getChild());
        }
        return result;
    }

    /**
     * Check if the graph contains the node.
     *
     * @param node The node to be checked.
     * @return true if contains the node.
     * @spec.requires node is not null.
     */
    public boolean containsNode(Node<T> node) {
        return graph.containsKey(node);
    }

    /**
     * Returns the size of the nodes in the graph.
     *
     * @return the size of the nodes as an int.
     */
    public int nodeSize() {
        return graph.keySet().size();
    }

    public int edgeSize() {
        int sum =  0;
        for (Node<T> n: graph.keySet()) {
            sum += graph.get(n).size();
        }
        return sum;
    }

    /**
     * Check if the graph is empty.
     *
     * @return true if the graph is empty, false otherwise.
     */
    public boolean isEmpty() {
        return graph.size() == 0;
    }

    /**
     * Check if the graph instance holds the representation invariant.
     *
     * @throws RuntimeException if the graph violates the rep inv.
     */
    public void checkRep() {
        if (graph == null) {
            throw new RuntimeException("graph is null");
        }
        for (Node<T> n: graph.keySet()) {
            if (n == null) {
                throw new RuntimeException("one of node is null");
            }
            if (graph.get(n) == null) {
                throw new RuntimeException("one of the edge set is null");
            }
            if (this.getChildNodes(n).size() > this.nodeSize()) {
                throw new RuntimeException("one node has more child nodes than the nodes in the graph");
            }
            for (Edge<T, E> e: graph.get(n)) {
                if (e == null) {
                    throw new RuntimeException("one of the edges is null");
                }
            }
        }
    }

}
