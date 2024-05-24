package algorithms;

import exceptions.UnsuitableGraphException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.Graphs;
import utils.GraphUtils;
import utils._ConnectedComponents;

public class Fleury extends algorithms.Algorithm {

    public Fleury() {
        this.path = new Path();
    }

    public void init(Graph inputGraph) {
        this.graph = Graphs.clone(inputGraph);
        this.startNode = this.graph.getNode(0);
        this.path.setRoot(this.startNode);
    }

    public void compute() throws Exception {
        if (!GraphUtils.isConnectedGraph(graph) || !GraphUtils.eachNodeHasEvenDegree(graph)){
            throw new UnsuitableGraphException();
        }
        compute(startNode);
    }

    private void compute(Node sourceNode) {
        for (Edge edge : sourceNode) {
            if (edge != null) {
                Node targetNode = edge.getOpposite(sourceNode);
                graph.removeEdge(edge);
                if (sourceNode.getDegree() == 0 ||
                        _ConnectedComponents.getConnectedComponentOf(sourceNode).contains(targetNode)) {
                    path.add(edge);
                    compute(targetNode);
                } else {
                    graph.addEdge(edge.getId(), sourceNode.getId(), targetNode.getId());
                }
            }
        }
    }
}
