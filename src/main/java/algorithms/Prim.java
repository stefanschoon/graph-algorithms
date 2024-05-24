package algorithms;

import exceptions.NoConnectedGraphException;
import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.SingleGraph;
import utils.GraphUtils;

import java.util.PriorityQueue;

public class Prim extends algorithms.Algorithm {

    private final Graph minimalSpanningTree;
    //private final PriorityQueue<Edge> priorityQueue;
    private final FibonacciHeap<Integer, Edge> fibonacciHeap;
    private double weight;

    public Prim() {
        this.minimalSpanningTree = new SingleGraph("P");
        //this.priorityQueue = new PriorityQueue<>();
        this.fibonacciHeap = new FibonacciHeap<>();
        this.weight = 0.0;
    }

    public double getTreeWeight() {
        return this.weight;
    }

    public Graph getMinimalSpanningTree() {
        return this.minimalSpanningTree;
    }

    public void init(Graph inputGraph) {
        this.graph = Graphs.clone(inputGraph);
        // Transform input graph to directed graph for better graph handling:
        this.graph = GraphUtils.transformToDirectedGraph(this.graph);
        minimalSpanningTree.setStrict(false);
        minimalSpanningTree.setAutoCreate(true);
    }

    /**
     * Executes algorithm for Prims minimal spanning tree.
     *
     * @throws Exception
     */
    public void compute() throws Exception {
        compute(0);
    }

    private void compute(int startNodeIndex) throws Exception {
        if (!GraphUtils.isConnectedGraph(this.graph)) {
            throw new NoConnectedGraphException();
        }

        Node startNode = this.graph.getNode(startNodeIndex);
        // Marks start node visited:
        GraphUtils.markVisited(startNode);
        // Adds leaving edges of start node to queue:
        addEdges(startNode);

        // Run algorithm while node count of mst is less than node count of input graph.
        while (minimalSpanningTree.getNodeCount() < graph.getNodeCount()) {
            //Edge edge = this.priorityQueue.poll();
            Edge edge = fibonacciHeap.extractMin();
            Node sourceNode = edge.getSourceNode();
            Node targetNode = edge.getTargetNode();

            if (!GraphUtils.isVisited(targetNode)) {
                GraphUtils.markVisited(targetNode);
                minimalSpanningTree.addEdge(edge.getId(), sourceNode.getId(), targetNode.getId());
                weight += GraphUtils.getEdgeWeight(edge);
                // Adds all edges of current target node to queue.
                addEdges(targetNode);
            }
        }
    }

    /**
     * Helper method for adding neighbour edges of scoped edge to queue.
     *
     * @param targetNode scoped edge
     */
    private void addEdges(Node targetNode) {
        targetNode.leavingEdges().forEach(edge -> {
            if (!GraphUtils.isVisited(edge.getTargetNode())) {
                //priorityQueue.add(edge);
                fibonacciHeap.add(GraphUtils.getEdgeWeight(edge), edge);
            }
        });
    }
}
