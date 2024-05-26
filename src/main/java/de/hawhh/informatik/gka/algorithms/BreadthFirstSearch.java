package de.hawhh.informatik.gka.algorithms;

import de.hawhh.informatik.gka.exceptions.NoConnectedGraphException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import de.hawhh.informatik.gka.iterator.BreadthFirstIterator;
import de.hawhh.informatik.gka.common.GraphUtils;

import java.util.*;

public class BreadthFirstSearch extends de.hawhh.informatik.gka.algorithms.Algorithm {

    private int index, length;
    private Stack<Node> stack;
    private BreadthFirstIterator breadthFirstIterator;

    public BreadthFirstSearch() {
        this.index = 1;
        this.length = 0;
        this.stack = new Stack<>();
    }

    public int getLength() {
        return this.length;
    }

    public Path getPath() {
        return this.path;
    }

    public void init(Graph inputGraph) {
        this.inputGraph = inputGraph;
    }

    public void compute(Node startNode, Node targetNode) throws Exception {
        if (!GraphUtils.isConnectedGraph(this.inputGraph)) {
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

    // Reconstruct the shortest path.
    private void shortestPath() {
        // Set index back to index of target node and assign currentNode to targetNode.
        index = breadthFirstIterator.indexOf(targetNode);
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

    private class BreadthFirstIterator implements Iterator<Node> {

        private final int[] indexes;
        private final boolean[] visited;
        private final Node startNode;
        private final Queue<Node> queue;
        private int index;

        public BreadthFirstIterator(Node startNode) {
            this.index = 0;
            this.indexes = new int[startNode.getGraph().getNodeCount()];
            this.visited = new boolean[startNode.getGraph().getNodeCount()];
            this.startNode = startNode;
            this.queue = new LinkedList<>();
            this.queue.add(startNode);
        }

        public String getStartNode() {
            return this.startNode.toString();
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public Node next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node currentNode = queue.remove();
            // Adjust index:
            index = indexes[currentNode.getIndex()] + 1;
            // Set currentNode visited
            this.visited[currentNode.getIndex()] = true;

            // Add remaining nodes to queue:
            currentNode.neighborNodes().forEach(node -> {
                if (!this.visited[node.getIndex()] && !this.queue.contains(node)) {
                    this.queue.add(node);
                    // All nodes on this level have the same distance from start.
                    this.indexes[node.getIndex()] = index;
                }
            });

            return currentNode;
        }

        public int indexOf(Node node) {
            return this.indexes[node.getIndex()];
        }
    }
}
