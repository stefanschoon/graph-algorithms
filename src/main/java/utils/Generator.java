package utils;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.GraphUtils.setEdgeWeight;

public class Generator {

    private static final String CHARSET_NAME = "Cp1252";
    private static final Pattern ADJACENCY_PATTERN = Pattern.compile(
            "^(\\w+)\\s*?(--|->)?\\s*?(\\w+)?(?:\\s*?[:(]\\s*?(\\w+?)?)?\\)?;$");

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
}
