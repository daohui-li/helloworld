package data.graph;

import java.util.*;

public class Dijkstra {
    public static class MinPath {
        MinPath() {
            length = Integer.MAX_VALUE;
            nodes = new ArrayList<>();
        }
        Integer length;
        ArrayList<Vertex> nodes;

        public Integer getLength() {
            return length;
        }
        public ArrayList<Vertex> getNodes() {
            return nodes;
        }

        public void setPath(ArrayList<Vertex> nodes, Vertex next) {
            this.nodes.clear();
            this.nodes.addAll(nodes);
            this.nodes.add(next);
        }
    }

    Graph graph;

    Map<Vertex, Integer> distanceMap;
    Map<Vertex, MinPath> distanceDetailedMap;
    boolean detailed = false;

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    public Integer findMinPath(Vertex start, Vertex end) {
        if (!graph.contains(start) || !graph.contains(end)) {
            throw new RuntimeException("bad input");
        }

        Set<Vertex> workingNode = init(start, false);
        Vertex v;
        while ((v = getMinDistance(workingNode)) != null) {
            if (v.equals(end)) {
                break;
            }
            workingNode.remove(v);
            checkNeighborAndAddToWorkingNode(v, workingNode);
        }

        return distanceMap.get(end);
    }

    public MinPath findMinPathWithDetails(Vertex start, Vertex end) {
        if (!graph.contains(start) || !graph.contains(end)) {
            throw new RuntimeException("bad input");
        }

        detailed = true;
        Set<Vertex> workingNode = init(start, true);
        Vertex v;
        while ((v = getMinDistance(workingNode)) != null) {
            if (v.equals(end)) {
                break;
            }
            workingNode.remove(v);
            checkNeighborAndAddToWorkingNodeWithDetails(v, workingNode);
        }

        return distanceDetailedMap.get(end);
    }

    private Vertex getMinDistance(Set<Vertex> workingNode) {
        Vertex rc = null;
        Integer min = Integer.MAX_VALUE;
        for (Vertex v : workingNode) {
            Integer l = (!detailed) ? distanceMap.get(v)
                                    : distanceDetailedMap.get(v).length;
            if (l < min) {
                min = l;
                rc = v;
            }
        }
        return rc;
    }

    private Set<Vertex> init(Vertex start, boolean needDetails) {
        if (needDetails) {
            distanceDetailedMap = new HashMap<>();
            for (Vertex v : graph.getVertices()) {
                distanceDetailedMap.put(v, new MinPath());
            }
            MinPath m = new MinPath();
            m.length = 0;
            m.nodes.add(start);
            distanceDetailedMap.put(start, m);
        } else {
            distanceMap = new HashMap<>();
            for (Vertex v : graph.getVertices()) {
                distanceMap.put(v, Integer.MAX_VALUE);
            }
            distanceMap.put(start, 0);
        }

        Set<Vertex> workingSet = new HashSet<>();
        workingSet.add(start);
        return workingSet;
    }
    private void checkNeighborAndAddToWorkingNode(Vertex v, Set<Vertex> workingNode) {
        Integer startL = distanceMap.get(v);
        for (Map.Entry<Vertex, Integer> entry : v.getNeighborMap().entrySet()) {
            Integer l = startL + entry.getValue();
            int dist0 = distanceMap.get(entry.getKey());
            if (l < dist0) {
                distanceMap.put(entry.getKey(), l);
            }
            if (dist0 == Integer.MAX_VALUE) { // not visited
                workingNode.add(entry.getKey());
            }
        }
    }
    private void checkNeighborAndAddToWorkingNodeWithDetails(Vertex v, Set<Vertex> workingNode) {
        MinPath startL = distanceDetailedMap.get(v);
        for (Map.Entry<Vertex, Integer> entry : v.getNeighborMap().entrySet()) {
            Integer l = startL.length + entry.getValue();
            MinPath dist0 = distanceDetailedMap.get(entry.getKey());

            if (dist0.length == Integer.MAX_VALUE) { // not visited
                workingNode.add(entry.getKey());
            }

            if (l < dist0.length) {
                dist0.length = l;
                dist0.setPath(startL.nodes, entry.getKey());
                distanceDetailedMap.put(entry.getKey(), dist0);
            }
        }
    }
}
