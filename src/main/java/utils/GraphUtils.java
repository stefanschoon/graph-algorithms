package utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Iterator;

public class GraphUtils {

    public static final String styleSheet = "url(file:resources/stylesheet.css)";

    /**
     * Creates a random int.
     *
     * @param min lower bound
     * @param max upper bound
     * @return randomInt
     */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Creates a graphical display of the transferred graph.
     *
     * @param graph Graph to display
     */
    public static void displayGraph(Graph graph) {
        for (Node node : graph) {
            node.setAttribute("ui.label", node.getId());
        }
        graph.edges().forEach(edge -> edge.setAttribute("ui.label", edge.getAttribute("weight")));
        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.display();
    }

    /**
     * Checks if a graph is connected aka has only of one connected component.
     *
     * @param graph Graph to check
     * @return true if connected, false if not
     */
    public static boolean isConnectedGraph(Graph graph) {
        Iterator<Node> breadthFirstIterator = new BreadthFirstIterator(graph.getNode(0));
        int nodeCount = graph.getNodeCount();
        int count = 0;
        while (breadthFirstIterator.hasNext()) {
            breadthFirstIterator.next();
            count++;
        }
        return count == nodeCount;
    }

    /**
     * Checks if each node has an even degree.
     *
     * @param graph Graph to check
     * @return true if eulerian, false if not
     */
    public static boolean eachNodeHasEvenDegree(Graph graph) {
        for (Node node : graph) {
            if (node.getDegree() % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    public static Node randomNode(Graph graph) {
        return graph.getNode(random(0, graph.getNodeCount()));
    }

    public static int getEdgeWeight(Edge edge) {
        if (edge.hasAttribute("weight")) {
            return Integer.parseInt((String) edge.getAttribute("weight"));
        }
        return 1;
    }

    /**
     * Sets the weight of appropriate edge.
     *
     * @param edge Edge, for weight
     * @param weight Weight for the edge
     */
    public static void setEdgeWeight(Edge edge, String weight) {
        if (weight == null) {
            edge.setAttribute("weight", "1");
        } else {
            edge.setAttribute("weight", weight);
        }
    }

    /**
     * Turns an undirected graph into a directed graph by creating two opposite directed edges from each undirected
     * edge.
     *
     * @param graph undirected graph
     * @return directedGraph
     */
    public static Graph transformToDirectedGraph(Graph graph) {
        Graph directedGraph = new SingleGraph("D" + graph.getId(), false, true);
        for (Node node : graph) {
            node.edges().forEach(edge -> {
                Node node0 = edge.getNode0();
                Node node1 = edge.getNode1();
                String weight = String.valueOf(edge.getAttribute("weight"));

                directedGraph.addEdge(edge.getId(), node0, node1, true);
                setEdgeWeight(directedGraph.getEdge(edge.getId()), weight);
                directedGraph.addEdge(node1.getId() + node0.getId(), node1, node0, true);
                setEdgeWeight(directedGraph.getEdge(node1.getId() + node0.getId()), weight);
            });
        }
        return directedGraph;
    }

    public static void markVisited(Element element) {
        element.setAttribute("visited", true);
        element.setAttribute("ui.class", "marked");
    }

    /**
     * Checks if an element was visited before.
     *
     * @param element element to check
     * @return true if visited, false else
     */
    public static boolean isVisited(Element element) {
        if (element.hasAttribute("visited")) {
            return (boolean) element.getAttribute("visited");
        }
        return false;
    }
}
