package de.hawhh.informatik.gka.algorithms;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

public abstract class Algorithm {

    protected double weight;
    protected Graph inputGraph, minimalSpanningTree;
    protected Path path;
    protected Node startNode, targetNode;

    public Algorithm() {
    }
}
