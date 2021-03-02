package model;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;

public class Vertex implements Vectorizable<Vertex> {
    Point3D position;
    Col color;
    private Vec2D texCoord;
    private Vec3D normal;

    public Vec3D getNormal() {
        return normal;
    }

    public Vertex(Point3D position) {
        this.position = position;
    }

    public Vertex(Point3D point3D, Col color){
        position = point3D;
        this.color = color;
    }

    public Vertex(Point3D point3D, Col color, Vec2D texCoord){
        position = point3D;
        this.color = color;
        this.texCoord = texCoord;
    }

    public Vec2D getTexCoord() {
        return texCoord;
    }

    @Override
    public Vertex mul(double d){
        return new Vertex(position.mul(d), color.mul(d), texCoord.mul(d));
    }

    @Override
    public Vertex add(Vertex vertex){
        return new Vertex(position.add(
                vertex.getPosition()),
                color.add(vertex.getColor()),
                texCoord.add(vertex.texCoord)
        );
    }

    public Vertex dehomog(){
        return this.mul(1/this.position.getW());
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
