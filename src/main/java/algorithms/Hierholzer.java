package algorithms;

import exceptions.UnsuitableGraphException;
import exceptions.ZeroDegreeException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.Graphs;
import utils.GraphUtils;

import java.util.Iterator;
import java.util.LinkedList;

public class Hierholzer extends algorithms.Algorithm {

    public Hierholzer() {
        this.path = new Path();
    }

    public void init(Graph inputGraph) {
        this.graph = Graphs.clone(inputGraph);
        this.startNode = this.graph.getNode(0);
        this.path.setRoot(this.startNode);
    }

    public void compute() throws Exception {
        if (!GraphUtils.eachNodeHasEvenDegree(graph)) {
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
                graph.removeEdge(edge);
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
