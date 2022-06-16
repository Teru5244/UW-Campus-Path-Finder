package pathfinder;

import graph.*;

import java.util.*;

/**
 * PathFinder contains one function that helps to find the path with the least cost
 * between two nodes in a graph by the Dijkstra's Algorithm.
 */
public class PathFinder {

    /**
     * This function helps to find the shortest path between two nodes.
     *
     * @param start The given start node.
     * @param end The given end node.
     * @param graph The given graph to be searched on.
     * @param <T> The type of the data in the nodes.
     * @return A list of Edges of the start node that points to the end node
     * with the least cost, null if no path is found or start and end nodes are
     * not in the graph.
     * @throws IllegalArgumentException If graph is null or two given nodes are
     * null.
     */
    public static <T> List<Edge<T, Double>> minCostPath(Node<T> start, Node<T> end, Graph<T, Double> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end nodes cannot be null.");
        }
        if (!graph.containsNode(start) || !graph.containsNode(end)) {
            return null;
        }
        // Creating a new priority queue with new comparator to compare two priority queues'
        // by their total costs.
        PriorityQueue<List<Edge<T,Double>>> active =
                new PriorityQueue<>((q1, q2) -> {
                    double cost1 = 0;
                    double cost2 = 0;
                    for (Edge<T, Double> e1: q1) {
                        cost1 += e1.getLabel();
                    }
                    for (Edge<T, Double> e2: q2) {
                        cost2 += e2.getLabel();
                    }
                    int result = 0;
                    if (cost1 - cost2 > 0) {
                        result = 1;
                    } else if (cost1 - cost2 < 0) {
                        result = -1;
                    }
                    return result;
                });
        Set<Node<T>> finished = new HashSet<>();

        List<Edge<T, Double>> init = new ArrayList<>();
        init.add(new Edge<>(start, 0.0));
        active.add(init);

        while (!active.isEmpty()) {
            List<Edge<T, Double>> minPath = active.remove();
            Node<T> minDest = minPath.get(minPath.size() - 1).getChild();

            if (minDest.equals(end)) {
                return minPath;
            }
            if (!finished.contains(minDest)) {
                for (Edge<T, Double> e: graph.getEdges(minDest)) {
                    if (e.getLabel() < 0) {
                        throw new RuntimeException("One of the cost is smaller than 0.");
                    }
                    if (!finished.contains(e.getChild())) {
                        // remove the self-pointing edge
                        minPath.remove(new Edge<>(start, 0.0));
                        List<Edge<T, Double>> newPath = new ArrayList<>(minPath);
                        newPath.add(e);
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
        return null;
    }

}
