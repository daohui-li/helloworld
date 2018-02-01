package data.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestDijkstra {
    Graph graph;

    @Before
    public void init() {
        graph = new Graph();
    }

    @Test
    public void testFindMinPath() {
        init1();

        Dijkstra worker = new Dijkstra(graph);
        Vertex start = graph.getVertices().iterator().next();
        for (Vertex v1: graph.getVertices()){
            Integer min = worker.findMinPath(start, v1);
            System.out.println(String.format("%s, %s => %d", start.label, v1.label, min));
        }
    }

    @Test
    public void testFindMinPathDetails() {
        init1();

        Dijkstra worker = new Dijkstra(graph);
        Vertex start = graph.getVertices().iterator().next();
        for (Vertex v1: graph.getVertices()){
            Dijkstra.MinPath min = worker.findMinPathWithDetails(start, v1);
            System.out.println(String.format("%s, %s => %d(%s)", start.label, v1.label, min.getLength(), buildNodesString(min.getNodes())));
        }
    }

    @Test
    public void testFindMinPath2() {
        init2();

        Dijkstra worker = new Dijkstra(graph);
        Vertex start = graph.getVertices().iterator().next();
        for (Vertex v1: graph.getVertices()){
            Integer min = worker.findMinPath(start, v1);
            System.out.println(String.format("%s, %s => %d", start.label, v1.label, min));
        }
    }

    @Test
    public void testFindMinPathDetails2() {
        init2();

        Dijkstra worker = new Dijkstra(graph);
        Vertex start = graph.getVertices().iterator().next();
        for (Vertex v1: graph.getVertices()){
            Dijkstra.MinPath min = worker.findMinPathWithDetails(start, v1);
            System.out.println(String.format("%s, %s => %d(%s)", start.label, v1.label, min.length, buildNodesString(min.getNodes())));
        }
    }

    private String buildNodesString(List<Vertex> list) {
        String text = list.stream().map(v->v.label).reduce("", (sum, s)-> sum+s+", ");
        int lastIndex = text.lastIndexOf(',');
        return text.substring(0, lastIndex);
    }

    private void init1() {
        add("1", "2", 7);
        add("1", "3", 9);
        add("1", "6", 14);
        add("2", "3", 10);
        add("2", "4", 15);
        add("3", "4", 11);
        add("3", "6", 2);
        add("4", "5", 6);
        add("5", "6", 9);
    }

    private void init2() {
        add("1", "2", 10);
        add("1", "3", 15);
        add("2", "4", 12);
        add("2", "6", 15);
        add("3", "5", 10);
        add("4", "5", 2);
        add("4", "6", 1);
        add("5", "6", 5);
    }

    private void add(String l1, String l2, int len) {
        Vertex v1 = new Vertex(l1);
        Vertex v2 = new Vertex(l2);
        this.graph.buildNeighbor(v1, v2, len);
    }
}
