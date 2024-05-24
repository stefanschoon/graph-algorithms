package utils;

import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class _ConnectedComponents {

    public static List<Node> getConnectedComponentOf(Node node) {
        Iterator<Node> breadthFirstIterator = new BreadthFirstIterator(node);
        List<Node> nodes = new ArrayList<>();

        while (breadthFirstIterator.hasNext()) {
            nodes.add(breadthFirstIterator.next());
        }

        return nodes;
    }
}
