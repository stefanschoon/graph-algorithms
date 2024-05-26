# Graph Algorithms Repository

This repository contains various graph theoretical algorithms implemented in Java, using the GraphStream 2.0 library. The algorithms included are Breadth First Search (BFS), Kruskal's Minimum Spanning Tree, Prim's Minimum Spanning Tree, Fleury's Eulerian Path, and Hierholzer's Eulerian Circuit. Additionally, there is a general-purpose Union Find class provided for these algorithms.

## Prerequisites

Make sure you have the following installed before running the code:

- Java Development Kit (JDK) 8 or higher
- Apache Maven

## Algorithms Implemented

### 1. Breadth First Search (BFS)

BFS is a graph traversal algorithm that explores all the vertices of a graph in breadth-first manner, i.e., it visits all the neighbors of a node before moving to the next level neighbors.

### 2. Kruskal's Minimum Spanning Tree

Kruskal's algorithm finds the minimum spanning tree for a connected, undirected graph. It does this by adding edges to the spanning tree in ascending order of their weights until all the vertices are included.

### 3. Prim's Minimum Spanning Tree

Prim's algorithm also finds the minimum spanning tree for a connected, undirected graph. It starts with an arbitrary node and grows the spanning tree by adding the smallest edge that connects a vertex in the tree to a vertex outside the tree.

### 4. Fleury's Eulerian Path

Fleury's algorithm finds an Eulerian path in a graph, which is a path that visits every edge exactly once. This algorithm ensures that the graph has either zero or two vertices with an odd degree.

### 5. Hierholzer's Eulerian Circuit

Hierholzer's algorithm finds an Eulerian circuit in a graph, which is a closed walk that covers every edge exactly once. Similar to Fleury's algorithm, it also checks for the existence of an Eulerian circuit in the graph.

### 6. Union Find

The Union Find class is a data structure that helps in efficiently managing disjoint sets and performing union and find operations.
