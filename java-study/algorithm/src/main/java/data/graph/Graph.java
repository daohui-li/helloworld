package data.graph;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    Set<Vertex> vertices = new HashSet<>();

    public Vertex addVertex(Vertex vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            return vertex;
        }
        for (Vertex v : vertices) {
            if (v.equals(vertex)) {
                return v;
            }
        }
        return null;
    }

    public void buildNeighbor(Vertex v1, Vertex v2, Integer len) {
        v1 = addVertex(v1);
        v2 = addVertex(v2);

        v1.add(v2, len);
        v2.add(v1, len);
    }

    public boolean contains(Vertex v) {
        return vertices.contains(v);
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }
}
