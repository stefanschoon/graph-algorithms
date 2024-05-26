package de.hawhh.informatik.gka.algorithms;

import de.hawhh.informatik.gka.exceptions.UnsuitableGraphException;
import de.hawhh.informatik.gka.exceptions.ZeroDegreeException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.Graphs;
import de.hawhh.informatik.gka.common.GraphUtils;

public class Hierholzer extends de.hawhh.informatik.gka.algorithms.Algorithm {

    public Hierholzer() {
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
        if (!GraphUtils.eachNodeHasEvenDegree(inputGraph)) {
            throw new UnsuitableGraphException();
        }
        if (startNode.getDegree() == 0) {
            throw new ZeroDegreeException();
        }
        compute(startNode);
    }

    private void compute(Node sourceNode) {
        Node currentNode = sourceNode;

        do {
            for (Edge edge : currentNode.edges().toList()) {
                path.add(currentNode, edge);
                currentNode = edge.getOpposite(currentNode);
                inputGraph.removeEdge(edge);
                break;
            }
        } while (currentNode != sourceNode);

        for (Node node : path.getNodePath()) {
            if (node.getDegree() > 0) {
                compute(node);
            }
        }
    }
}
