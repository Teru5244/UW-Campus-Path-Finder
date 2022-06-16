/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.scriptTestRunner;

import graph.Edge;
import graph.Graph;
import graph.Node;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    private final Map<String, Graph<String, String>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new MarvelTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "LoadGraph":
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void loadGraph(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String filename = arguments.get(1);
        loadGraph(graphName, filename);
    }

    private void loadGraph(String graphName, String filename) {
        graphs.put(graphName, MarvelPaths.buildGraph(filename));
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String startName = arguments.get(1);
        String endName = arguments.get(2);
        findPath(graphName, startName, endName);
    }

    private void findPath(String graphName, String startName, String endName) {
        Graph<String, String> g = graphs.get(graphName);

        if (!g.containsNode(new Node<>(startName)) && !g.containsNode(new Node<>(endName))) {
            output.println("unknown: " + startName);
            output.println("unknown: " + endName);
        }else if (!g.containsNode(new Node<>(startName))) {
            output.println("unknown: " + startName);
        } else if (!g.containsNode(new Node<>(endName))) {
            output.println("unknown: " + endName);
        } else {
            List<Edge<String, String>> paths = MarvelPaths.BFS(new Node<>(startName), new Node<>(endName), g);

            // print the paths
            output.println("path from " + startName + " to " + endName + ":");

            String start = startName;
            String end;


            if (paths == null) {
                output.println("no path found");
            } else {
                for (Edge<String, String> path : paths) {
                    end = path.getChild().getData();
                    output.println(start + " to " + end + " via " + path.getLabel());
                    start = end;
                }
            }
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, String> g = graphs.get(graphName);
        g.addNode(new Node<>(nodeName));
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        Graph<String, String> g = graphs.get(graphName);
        g.addEdge(new Node<>(parentName), new Node<>(childName), edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, String> g = graphs.get(graphName);
        StringBuilder result = new StringBuilder(graphName + " contains:");
        Set<Node<String>> sortNodes =
                new TreeSet<>((n1, n2) -> {
                    if (!n1.getData().equals(n2.getData())) {
                        return n1.getData().compareTo(n2.getData());
                    }
                    return 0;
                });
        sortNodes.addAll(g.getNodes());
        for (Node<String> n: sortNodes) {
            result.append(" ").append(n.getData());
        }
        output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String, String> g = graphs.get(graphName);
        StringBuilder result = new StringBuilder("the children of " + parentName + " in " + graphName + " are:");
        Set<Edge<String, String>> sortEdges =
                new TreeSet<>((e1, e2) -> {
                    if (!(e1.getChild().equals(e2.getChild()))) {
                        return e1.getChild().getData().compareTo(e2.getChild().getData());
                    }
                    if (!(e1.getLabel().equals(e2.getLabel()))) {
                        return e1.getLabel().compareTo(e2.getLabel());
                    }
                    return 0;
                });
        sortEdges.addAll(g.getEdges(new Node<>(parentName)));
        for (Edge<String, String> e: sortEdges) {
            result.append(" ").append(e.getChild().getData()).append("(").append(e.getLabel()).append(")");
        }
        output.println(result);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
