package model;

import transforms.Col;
import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solid {
    private List<Part> parts;
    private List<Vertex> vertices;
    private List<Integer> indices;
    private RenderType renderType = RenderType.Combine;
    private boolean active = false;
    private Mat4 model = new Mat4Identity();

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    private Col lineColor = new Col(1., 1, 1);
    private Col color;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {

        this.color = color;
    }

    public Col getLineColor() {
        return lineColor;
    }

    public void setLineColor(Col lineColor) {
        this.lineColor = lineColor;
    }

    public Solid() {
        parts = new ArrayList<>();
        vertices = new ArrayList<>();
        indices = new ArrayList<>();

        if(color == null) {
            Random rColor = new Random();
            setColor(new Col(rColor.nextInt(256), rColor.nextInt(256), rColor.nextInt(256)));
        }
    }

    public List<Part> getParts() {
        return parts;
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public void setRenderType(RenderType renderType) {
        this.renderType = renderType;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void deActiveSolid() {
        this.active = false;
    }

    public void setActiveSolid() {
        this.active = true;
    }
}
