package model;

import transforms.Col;
import transforms.Point3D;

public class Vertex {
    private Point3D position;
    private Col color;

    public Vertex(int x, int y, int z, Col color){
        position = new Point3D(x, y, z);
        this.color = color;
    }

    public Vertex(Point3D point3D, Col color){
        position = point3D;
        this.color = color;
    }
    public Vertex mul(double mul){
        return new Vertex(position.mul(mul), color);
    }

    public Vertex add(Vertex vertex){
        return new Vertex(position.add(vertex.getPosition()), getColor());
    }
    public Vertex dehomog(){
        return new Vertex(new Point3D(position.dehomog().get()), getColor());
    }

    public Point3D getPosition() {
        return position;
    }

    public Col getColor() {
        return color;
    }
}
