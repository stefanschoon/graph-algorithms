import algorithms.BreadthFirstSearch;
import algorithms.Fleury;
import algorithms.Hierholzer;
import org.graphstream.graph.Graph;
import utils.Generator;
import utils.GraphUtils;

public class Main {

    public static void main(String[] args) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = Generator.createFromFile("files/graph03.gka");
        GraphUtils.displayGraph(graph);

        BreadthFirstSearch algo = new BreadthFirstSearch();
        algo.init(graph);
        algo.compute(graph.getNode("Luebeck"), graph.getNode("Husum"));
        System.out.println("Path: " + algo.getPath());
    }
}
