package de.hawhh.informatik.gka.algorithms;

import de.hawhh.informatik.gka.common.ConnectedComponents;
import de.hawhh.informatik.gka.exceptions.UnsuitableGraphException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.Graphs;
import de.hawhh.informatik.gka.common.GraphUtils;

public class Fleury extends de.hawhh.informatik.gka.algorithms.Algorithm {

    public Fleury() {
        this.path = new Path();
    }

    public Path getEulerianCycle() {
        return path;
    }

    public void init(Graph inputGraph) {
        this.inputGraph = Graphs.clone(inputGraph);
        this.startNode = this.inputGraph.getNode(0);
        this.path.setRoot(this.startNode);
    }

    public void compute() throws Exception {
        if (!GraphUtils.isConnectedGraph(inputGraph) || !GraphUtils.eachNodeHasEvenDegree(inputGraph)) {
            throw new UnsuitableGraphException();
        }
        compute(startNode);
    }

    private void compute(Node sourceNode) {
        for (Edge edge : sourceNode) {
            if (edge != null) {
                Node targetNode = edge.getOpposite(sourceNode);
                inputGraph.removeEdge(edge);
                if (sourceNode.getDegree() == 0 ||
                        ConnectedComponents.getConnectedComponentOf(sourceNode).contains(targetNode)) {
                    path.add(edge);
                    compute(targetNode);
                } else {
                    inputGraph.addEdge(edge.getId(), sourceNode.getId(), targetNode.getId());
                }
            }
        }
    }
}
