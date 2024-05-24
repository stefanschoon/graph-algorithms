package algorithms;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

public abstract class Algorithm {

    protected Path path;
    protected Graph graph;
    protected Node startNode;

    public Algorithm() {
    }

    public Path getPath() {
        return path;
    }
}
