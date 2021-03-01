package model;

import java.util.ArrayList;
import java.util.List;

public class Solid {
    private List<Part> parts;
    private List<Vertex> vertices;
    private List<Integer> indices;

    public Solid() {
        parts = new ArrayList<>();
        vertices = new ArrayList<>();
        indices = new ArrayList<>();
    }

    public List<Part> getParts() {
        return parts;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }
}
