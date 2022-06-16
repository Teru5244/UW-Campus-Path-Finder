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

package pathfinder;

import graph.Edge;
import graph.Graph;
import graph.Node;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CampusMap is an immutable model of a campus map. It is used in the model-view-controller
 * design of the campus map, which is used for the GUI and text-input based campus pathfinder
 * application.
 */
public class CampusMap implements ModelAPI {

    // Abstract Function:
    //     AF(this) = a model in the campus map model-view-controller design.
    //     A building has a short name and long name as well as a location (as
    //     a cartesian point on a map).
    //     this will find the shortest path between two buildings with least cost.
    //
    // Representation Invariants:
    //     shortToFull != null, shortBuildingsLocation != null, campusGraph != null.
    //     Every element in shortToFull is not null.
    //     Every element in shortBuildingsLocation is not null.

    private final Map<String, String> shortToFull;

    private final Map<String, Point> shortBuildingsLocation;

    private final Graph<Point, Double> campusGraph;

    /**
     * The constructor of the CampusMap model.
     *
     * @spec.effects Construct new maps and graphs with given dataset from
     * parser.
     * @spec.modifies Add value from the parser to the graph.
     */
    public CampusMap() {
        shortToFull = new HashMap<>();
        shortBuildingsLocation = new HashMap<>();
        campusGraph = new Graph<>();
        List<CampusBuilding> parsedBuildingNames = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        List<CampusPath> parsedPaths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for (CampusBuilding b: parsedBuildingNames) {
            shortToFull.put(b.getShortName(), b.getLongName());
            shortBuildingsLocation.put(b.getShortName(), new Point(b.getX(), b.getY()));
        }
        for (CampusPath p: parsedPaths) {
            Node<Point> n1 = new Node<>(new Point(p.getX1(), p.getY1()));
            Node<Point> n2 = new Node<>(new Point(p.getX2(), p.getY2()));
            campusGraph.addNode(n1);
            campusGraph.addNode(n2);
            campusGraph.addEdge(n1, n2, p.getDistance());
        }

        checkRep();
    }

    /**
     * Check if the given short building name exists in the campus map.
     *
     * @param shortName The short name of a building to query.
     * @return true if the short name exists, false otherwise.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        return shortToFull.containsKey(shortName);
    }

    /**
     * Get the long name of a building in the graph with given short name.
     *
     * @param shortName The short name of a building to look up.
     * @throws IllegalArgumentException If the given building does not exist
     * in the data.
     * @return The long name of given short building name.
     */
    @Override
    public String longNameForShort(String shortName) {
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException("building not exists");
        }
        return shortToFull.get(shortName);
    }

    /**
     * @return A new Map of short name to long name of buildings in the campus
     * map.
     */
    @Override
    public Map<String, String> buildingNames() {
        return new HashMap<>(shortToFull);
    }

    /**
     * Find the shortest path from given start building to end building.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A Path of Points from start building to end building, if no path is found or
     * either building is not in the graph or if either the arguments is null, return null.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || endShortName == null) {
            return null;
        }
        if (!shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            return null;
        }
        Point start = getLocation(startShortName);
        Point end = getLocation(endShortName);
        Path<Point> result = new Path<>(start);

        List<Edge<Point, Double>> path = PathFinder.minCostPath(new Node<>(start), new Node<>(end), campusGraph);

        // if no path, return null
        if (path == null) {
            return null;
        }

        for (Edge<Point, Double> p: path) {
            result = result.extend(p.getChild().getData(), p.getLabel());
        }

        return result;
    }

    /**
     * @param shortName Short building name requested.
     * @return The location of the building on the map as a Point object.
     */
    private Point getLocation(String shortName) {
        return shortBuildingsLocation.get(shortName);
    }

    /**
     * Check if the class's representation invariants holds.
     *
     * @throws RuntimeException If the representation invariants are broken.
     */
    private void checkRep() {
        if (shortToFull == null) {
            throw new RuntimeException("shortToFull map cannot be null.");
        }
        if (shortBuildingsLocation == null) {
            throw new RuntimeException("shortBuildingsLocation map cannot be null.");
        }
        if (campusGraph == null) {
            throw new RuntimeException("campusGraph graph cannot be null");
        }
        for (String s: shortToFull.keySet()) {
            if (s == null) {
                throw new RuntimeException("Keys in shortToFull map cannot be null.");
            }
            if (shortToFull.get(s) == null) {
                throw new RuntimeException("Values in shortToFull map cannot be null");
            }
        }
        for (String s: shortBuildingsLocation.keySet()) {
            if (s == null) {
                throw new RuntimeException("Keys in shortBuildingsLocation map cannot be null.");
            }
            if (shortToFull.get(s) == null) {
                throw new RuntimeException("Values in shortBuildingsLocation map cannot be null");
            }
        }
    }
}
