package de.hawhh.informatik.gka.common;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.hawhh.informatik.gka.common.GraphUtils.setEdgeWeight;

public class Generator {

    private static final String CHARSET_NAME = "Cp1252";
    private static final Pattern ADJACENCY_PATTERN =
            Pattern.compile("^(\\w+)\\s*?(--|->)?\\s*?(\\w+)?(?:\\s*?[:(]\\s*?(\\w+?)?)?\\)?;$");

    /**
     * Erstellt einen Graphen aus einer GKA-Datei.
     * Über den Matcher werden 4 Gruppen erstellt:
     * 1. Gruppe: source node
     * 2. Gruppe: -> gerichtete Kante, -- ungerichtete Kante
     * 3. Gruppe: target node
     * 4. Gruppe: weight
     *
     * @param filename GKA-File
     * @return graph
     * @throws FileNotFoundException thrown if file was not found
     */
    public static Graph createFromFile(String filename) throws FileNotFoundException {
        Graph graph = new SingleGraph("G", false, true);
        Scanner scanner = new Scanner(new File(filename), CHARSET_NAME);

        while (scanner.hasNextLine()) {
            Matcher matcher = ADJACENCY_PATTERN.matcher(scanner.nextLine());
            while (matcher.find()) {
                String sourceId = matcher.group(1);
                String arc = matcher.group(2);
                String targetId = matcher.group(3);
                String weight = matcher.group(4);
                String edgeId = sourceId + targetId;

                if (arc == null) {
                    graph.addNode(sourceId);
                } else {
                    if (arc.equals("--")) {
                        graph.addEdge(edgeId, sourceId, targetId);
                        setEdgeWeight(graph.getEdge(edgeId), weight);
                    } else if (arc.equals("->")) {
                        graph.addEdge(edgeId, sourceId, targetId, true);
                        setEdgeWeight(graph.getEdge(edgeId), weight);
                    }
                }
            }
        }

        return graph;
    }

    /**
     * Creates a randomized graph.
     *
     * @param nodeCount     Exact count of nodes for the graph
     * @param averageDegree Average degree for each node
     * @param maxEdgeWeight Maximal weight for each edge; minimal weight is always 1
     * @return Graph
     */
    public static Graph _randomGraph(int nodeCount, int averageDegree, int maxEdgeWeight) {
        // TODO
        // edgeCount = (nodeCount * averageDegree) / 2
        return new SingleGraph("H", false, true);
    }

    /**
     * Creates a complete graph for a given count of nodes.
     *
     * @param nodeCount     Count of nodes for the graph
     * @return Graph
     */
    public static Graph completeGraph(int nodeCount) {
        Graph completeGraph = new SingleGraph("I", false, true);
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (i != j) {
                    completeGraph.addEdge(i + String.valueOf(j), String.valueOf(i), String.valueOf(j));
                }
            }
        }
        return completeGraph;
    }

    public static Graph randomEulerianGraph(int nodeCount) {
        Random random = new Random();

        Graph graph = new SingleGraph("J", false, true);
        List<Node> evenDegree = new ArrayList<>();
        List<Node> unevenDegree = new ArrayList<>();

        // Add nodes to graph:
        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(String.valueOf(i));
            // Initially add all nodes to even degree list.
            evenDegree.add(graph.getNode(i));
        }

        // Hardcoded test:
        int edgeCount = 2 * nodeCount;

        while (graph.getEdgeCount() < edgeCount) {
            Node randomNode0;
            if (!unevenDegree.isEmpty()) {
                randomNode0 = unevenDegree.remove(random.nextInt(0, unevenDegree.size()));
                evenDegree.add(randomNode0);
            } else {
                randomNode0 = evenDegree.remove(random.nextInt(0, evenDegree.size()));
                unevenDegree.add(randomNode0);
            }

            //Node randomNode0 = GraphUtils.randomNode(graph);
            Node randomNode1 = evenDegree.remove(random.nextInt(0, evenDegree.size()));
            unevenDegree.add(randomNode1);

            // FIXME: ?
            if (randomNode0 != randomNode1) {
                String edgeId = randomNode0.getId() + randomNode1.getId();
                graph.addEdge(edgeId, randomNode0, randomNode1);
            }
        }

        return graph;
    }
}
