package algorithms;

import exceptions.NoConnectedGraphException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import utils.BreadthFirstIterator;
import utils.GraphUtils;

import java.util.Stack;

public class BreadthFirstSearch {

    private int index, length;
    private Graph graph;
    private Node startNode, targetNode;
    private Path path;
    private Stack<Node> stack;
    private BreadthFirstIterator breadthFirstIterator;

    public BreadthFirstSearch() {
        this.index = 1;
        this.length = 0;
    }

    public int getLength() {
        return this.length;
    }

    public Path getPath() {
        return this.path;
    }

    public void init(Graph inputGraph) {
        this.graph = inputGraph;
        this.path = new Path();
        this.stack = new Stack<>();
    }

    public void compute(Node startNode, Node targetNode) throws Exception {
        if (!GraphUtils.isConnectedGraph(this.graph)) {
            throw new NoConnectedGraphException();
        }

        this.startNode = startNode;
        this.targetNode = targetNode;
        // Create new breadth first iterator:
        breadthFirstIterator = new BreadthFirstIterator(startNode);
        // Skip startNode.
        Node currentNode = breadthFirstIterator.next();

        // Build indexes list with bfIterator; stop when target is reached:
        while (breadthFirstIterator.hasNext() && currentNode != targetNode) {
            currentNode = breadthFirstIterator.next();
        }

        // Determine length of the shortest path:
        length = breadthFirstIterator.indexOf(targetNode) + 1;

        shortestPath();
        buildPath();
    }

    // Reconstruct the shortest path
    private void shortestPath() {
        // Set index back to index of target node and assign currentNode to targetNode.
        index = breadthFirstIterator.indexOf(targetNode);
        //index = bfIterator.indexOf(targetNode);
        Node currentNode = targetNode;
        stack.add(currentNode);

        while (currentNode != startNode) {
            for (Node node : currentNode.neighborNodes().toList()) {
                if (breadthFirstIterator.indexOf(node) == index - 1) {
                    index -= 1;
                    currentNode = node;
                    stack.add(currentNode);
                    // Break for loop to prevent that other nodes with same distance are added to stack.
                    break;
                }
            }
        }
    }

    private void buildPath() {
        // Build path with the shortest path:
        path.setRoot(startNode);
        while (stack.size() > 1) {
            Node node0 = stack.pop();
            Node node1 = stack.peek();
            path.add(node0.getEdgeBetween(node1));
        }
    }
}
