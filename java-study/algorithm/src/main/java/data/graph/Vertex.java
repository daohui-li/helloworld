package data.graph;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    Map<Vertex, Integer> neighborMap = new HashMap<>();
    String label;

    public Vertex(String lebel) {
        this.label = lebel;
    }

    public void add(Vertex neighbor, Integer length) {
        if (neighbor.equals(this) || neighborMap.containsKey(neighbor)) {
            throw new RuntimeException("invalid input");
        }
        if (!neighborMap.containsKey(neighbor)) {
            neighborMap.put(neighbor, length);
        }
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex) {
            Vertex other = (Vertex) obj;
            return label.equals(other.label);
        }
        return super.equals(obj);
    }

    public Map<Vertex, Integer> getNeighborMap() {
        return this.neighborMap;
    }
}
