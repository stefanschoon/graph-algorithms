package de.hawhh.informatik.gka.algorithms;

import de.hawhh.informatik.gka.exceptions.NoConnectedGraphException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import de.hawhh.informatik.gka.common.GraphUtils;
import de.hawhh.informatik.gka.common.UnionFind;

import java.util.*;

public class Kruskal extends de.hawhh.informatik.gka.algorithms.Algorithm {

    public Kruskal() {
        this.minimalSpanningTree = new SingleGraph("K");
        this.weight = 0.0;
    }

    public double getTreeWeight() {
        return this.weight;
    }

    public Graph getMinimalSpanningTree() {
        return this.minimalSpanningTree;
    }

    public void init(Graph inputGraph) {
        this.inputGraph = inputGraph;
        minimalSpanningTree.setStrict(false);
        minimalSpanningTree.setAutoCreate(true);
    }

    /**
     * Executes algorithm for Kruskal's minimal spanning tree.
     */
    public void compute() throws Exception {
        if (!GraphUtils.isConnectedGraph(inputGraph)) {
            throw new NoConnectedGraphException();
        }

        // Add all weighted edges of graph to a list and sorts it by weight:
        List<Edge> edges = inputGraph.edges()
                .sorted(Comparator.comparingInt(GraphUtils::getEdgeWeight))
                .toList();

        // New union find data structure:
        UnionFind unionFind = new UnionFind(inputGraph.getNodeCount());

        for (Edge edge : edges) {
            Node sourceNode = edge.getSourceNode();
            Node targetNode = edge.getTargetNode();
            int sourceParent = unionFind.getParent(sourceNode.getIndex());
            int targetParent = unionFind.getParent(targetNode.getIndex());
            if (sourceParent != targetParent) {
                weight += GraphUtils.getEdgeWeight(edge);
                minimalSpanningTree.addEdge(edge.getId(), sourceNode.toString(), targetNode.toString());
                unionFind.unify(sourceNode.getIndex(), targetNode.getIndex());
            }
        }
    }
}
