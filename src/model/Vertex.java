package model;

import transforms.*;

import java.util.Optional;
import java.util.Random;

public class Vertex implements Vectorizable<Vertex>{
    private Point3D position;
    private Col color;
    private Vec2D texCoord;
    private Col lineColor = new Col(1., 1, 1);

    public Col getLineColor() {
        return lineColor;
    }

    public void setLineColor(Col lineColor) {
        this.lineColor = lineColor;
    }

    public Vertex(Point3D point3D, Col color) {
        this.position = point3D;
        this.color = color;
        texCoord = new Vec2D(0, 0);
    }

    public Vertex(Vec3D vec3D, Col color) {
        this.position = new Point3D(vec3D.getX(), vec3D.getY(), vec3D.getZ());
        this.color = color;
        texCoord = new Vec2D(0, 0);
    }

    public Vertex(Point3D point3D, Col color, Vec2D texCoord){
        position = point3D;
        this.color = color;
        this.texCoord = texCoord;
    }

    public Vertex(Point3D point3D){
        position = point3D;
        if(color == null){
            Random rColor = new Random();
            setColor(new Col(rColor.nextInt(256),rColor.nextInt(256),rColor.nextInt(256)));
        }
        texCoord = new Vec2D(0, 0);
    }

    public Vertex transform(Mat4 model) {
        return new Vertex(position.mul(model), color);
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

    public Vertex ignoreW(){
        return this.mul(1/this.position.getW());
    }

    public Optional<Vec3D> dehomog() {
        return position.dehomog();
    }

    public Point3D getPosition() {
        return position;
    }

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vertex{" + "position=" + position + '}';
    }

    public boolean isInView() {
        return (-position.getW() <= position.getX() &&
                position.getY() <= position.getW() &&
                0 <= position.getZ() && 0<= position.getW());
    }
}
