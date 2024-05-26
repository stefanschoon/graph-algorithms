package de.hawhh.informatik.gka;

import de.hawhh.informatik.gka.algorithms.*;
import org.graphstream.graph.Graph;
import de.hawhh.informatik.gka.common.Generator;
import de.hawhh.informatik.gka.common.GraphUtils;

public class Main {

    public static void main(String[] args) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = Generator.createFromFile("files/graph03.gka");
        GraphUtils.displayGraph(graph);

        BreadthFirstSearch algo = new BreadthFirstSearch();
        algo.init(graph);
        algo.compute(graph.getNode("Luebeck"), graph.getNode("Husum"));
        System.out.println("Path: " + algo.getPath());

        Graph randomGraph = Generator.completeGraph(6);
        GraphUtils.displayGraph(randomGraph);

        graph = Generator.createFromFile("files/graph101.gka");
        GraphUtils.displayGraph(graph);

        Kruskal kruskal = new Kruskal();
        kruskal.init(graph);
        kruskal.compute();
        System.out.println("Kruskal: " + kruskal.getTreeWeight());
        GraphUtils.displayGraph(kruskal.getMinimalSpanningTree());

        Prim prim = new Prim();
        prim.init(graph);
        prim.compute();
        System.out.println("Prim: " + prim.getTreeWeight());
        GraphUtils.displayGraph(prim.getMinimalSpanningTree());

        // Test Generator.createFromFile
        //Graph graph02 = Generator.createFromFile("graph02.gka");
        //Graph graph03 = Generator.createFromFile("graph03.gka");
        //Graph graph04 = Generator.createFromFile("graph04.gka");
        //Graph graph05 = Generator.createFromFile("graph05.gka");
        //Graph graph06 = Generator.createFromFile("graph06.gka");
        //Graph graph07 = Generator.createFromFile("graph07.gka");
        //Graph graph08 = Generator.createFromFile("graph08.gka");
        //Graph graph09 = Generator.createFromFile("graph09.gka");
        //Graph graph10 = Generator.createFromFile("graph10.gka");
        //Graph graph11 = Generator.createFromFile("graph11.gka");

        graph = Generator.createFromFile("files/euler_graph02.gka");
        //graph = Generator.randomEulerianGraph(6);
        GraphUtils.displayGraph(graph);

        Fleury fleury = new Fleury();
        fleury.init(graph);
        fleury.compute();
        System.out.println(fleury.getEulerianCycle());

        Hierholzer hierholzer = new Hierholzer();
        hierholzer.init(graph);
        hierholzer.compute();
        System.out.println(hierholzer.getEulerianCycle());
    }
}
