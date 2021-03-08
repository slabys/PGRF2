package model;

import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public class Solid {
    private List<Part> parts;
    private List<Vertex> vertices;
    private List<Integer> indices;

    private Mat4 model = new Mat4Identity();

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

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
