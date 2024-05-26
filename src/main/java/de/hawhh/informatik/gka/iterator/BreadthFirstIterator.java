package de.hawhh.informatik.gka.iterator;

import org.graphstream.graph.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BreadthFirstIterator implements Iterator<Node> {

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
