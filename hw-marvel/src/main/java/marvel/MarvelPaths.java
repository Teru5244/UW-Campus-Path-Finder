package marvel;

import graph.*;

import java.util.*;

/**
 * This is the MarvelPaths class that will read from csv file and create
 * a graph based on the data. A line from csv file will be like "Node,Edge"
 * format in which we should reflexively connect all the nodes that share
 * the same edges. It has a graph build-up function and BST function which
 * will find the shortest path between two nodes. It also has a main
 * function which will read from the user input and do the graph build-up
 * and BFS search correspondingly.
 */

public class MarvelPaths {

    /**
     * This function will create a new graph based on the csv file given.
     * The nodes in the graph will all be reflexive with the child nodes.
     *
     * @param filename the name of the csv file as a string
     * @throws IllegalArgumentException if filename is null
     * @return a Graph of the data in the csv file given
     */
    public static Graph<String, String> buildGraph(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }

        Graph<String, String> graph = new Graph<>();
        HashMap<String, HashSet<String>> dictionary = MarvelParser.parseData(filename);

        for (String key: dictionary.keySet()) {
            for (String node1: dictionary.get(key)) {
                for (String node2: dictionary.get(key)) {
                    Node<String> start = new Node<>(node1);
                    Node<String> end = new Node<>(node2);
                    graph.addNode(start);
                    graph.addNode(end);
                    if (!start.equals(end)) {
                        graph.addEdge(start, end, key);
                    }
                }
            }
        }

        return graph;

    }

    /**
     * This function will perform the Breadth-First-Search on the given
     * graph to find the shortest path between two nodes.
     *
     * @param start the start node
     * @param end the end node that the path should point to
     * @param graph the graph to be examined
     * @throws IllegalArgumentException if the graph is null or start node
     * is null or the end node is null.
     * @return a list of the edges that indicates the path from the start
     * node to the end node, returns null if one of the given nodes is
     * not in the graph or a path from start node to end node is not found
     */
    public static List<Edge<String, String>> BFS(Node<String> start, Node<String> end, Graph<String, String> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph cannot be null.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("start and end cannot be null.");
        }
        if (!graph.containsNode(start) || !graph.containsNode(end)) {
            return null;
        }

        Queue<Node<String>> q = new LinkedList<>();
        Map<Node<String>, List<Edge<String, String>>> m = new HashMap<>();
        q.add(start);
        m.put(start, new ArrayList<>());

        while (!q.isEmpty()) {
            Node<String> parent = q.remove();

            if (parent.equals(end)) {
                return new ArrayList<>(m.get(parent));
            }

            HashSet<Edge<String, String>> edges = graph.getEdges(parent);
            Set<Edge<String, String>> sortedEdges =
                    new TreeSet<>((e1, e2) -> {
                        if (!(e1.getChild().equals(e2.getChild()))) {
                            return e1.getChild().getData().compareTo(e2.getChild().getData());
                        }
                        if (!(e1.getLabel().equals(e2.getLabel()))) {
                            return e1.getLabel().compareTo(e2.getLabel());
                        }
                        return 0;
                    });
            sortedEdges.addAll(edges);
            for (Edge<String, String> e: sortedEdges) {
                Node<String> child = e.getChild();
                if (!m.containsKey(child)) {
                    List<Edge<String, String>> path = m.get(parent);
                    List<Edge<String, String>> newPath = new ArrayList<>(path);
                    newPath.add(e);
                    m.put(child, newPath);
                    q.add(child);
                }
            }
        }
        return null;
    }

    public static void main (String[] args) {
        Scanner reader = new Scanner(System.in);
        Graph<String, String> g = buildGraph("marvel.csv");
        System.out.println("Type two characters to find the shortest path between them.");

        boolean again = true;
        while (again) {
            System.out.println();
            System.out.println("Please type the start character's name:");
            String start = reader.nextLine();
            System.out.println("Please type the end character's name:");
            String end = reader.nextLine();
            System.out.println();

            if (!g.containsNode(new Node<>(start)) || !g.containsNode(new Node<>(end))) {
                System.out.println("Unknown character: " + start);
                System.out.println("Unknown character: " + end);
                System.out.println("Please try again!");
            } else if (!g.containsNode(new Node<>(start))) {
                System.out.println("Unknown character: " + start);
                System.out.println("Please try again!");
            } else if (!g.containsNode(new Node<>(end))) {
                System.out.println("Unknown character: " + end);
                System.out.println("Please try again!");
            } else {
                String head = start;
                String tail;
                System.out.println("Retrieving path from " + start + " to " + end + ":");
                List<Edge<String, String>> path = BFS(new Node<>(start), new Node<>(end), g);
                if (path == null) {
                    System.out.println("Sorry, no path found between " + start + " and " + end + "!");
                } else {
                    for (Edge<String, String> e: path) {
                        tail = e.getChild().getData();
                        System.out.println(head + " to " + tail + " via " + e.getLabel());
                        head = tail;
                    }
                }
                System.out.println("Path finished.");
            }

            System.out.println();
            System.out.println("Do you want to search again?");
            String answer = reader.nextLine();
            answer = answer.toLowerCase();
            if (answer.length() == 0 || answer.charAt(0) != 'y') {
                again = false;
            }
        }
        System.out.println();
        System.out.println("See you next time!");
        reader.close();
    }

}
