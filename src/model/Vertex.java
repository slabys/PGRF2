package model;

import transforms.Col;
import transforms.Point3D;

public class Vertex implements Vectorizable<Vertex> {
    private Point3D position;
    private Col color;

    public Vertex(Point3D position) {
        this.position = position;
    }

    public Vertex(Point3D point3D, Col color){
        position = point3D;
        this.color = color;
    }

    @Override
    public Vertex mul(double d){
        return new Vertex(position.mul(d), color.mul(d));
    }

    @Override
    public Vertex add(Vertex vertex){
        return new Vertex(position.add(vertex.getPosition()), color.add(vertex.color));
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

    @Override
    public String toString() {
        return "Vertex{" + "position=" + position + '}';
    }
}
