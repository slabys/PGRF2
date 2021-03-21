package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid {
    public Cube(Vertex a, double size){
        super();
        Col secondCol = new Col(a.getColor().getR(), a.getColor().getG()/10, a.getColor().getB());

        getVertices().add(a); //0
        getVertices().add(new Vertex(
                new Point3D(a.getPosition().getX()+size, a.getPosition().getY(), a.getPosition().getZ()), a.getColor())
        ); //1    +x
        getVertices().add(new Vertex(
                new Point3D(a.getPosition().getX(), a.getPosition().getY()+size, a.getPosition().getZ()), a.getColor())
        ); //2    +y
        getVertices().add(new Vertex(
                new Point3D(a.getPosition().getX()+size, a.getPosition().getY()+size, a.getPosition().getZ()), a.getColor())
        ); //3    +x, +y

        getVertices().add(new Vertex(
                new Point3D(a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ()-size), secondCol)
        );//4
        getVertices().add(new Vertex(
                new Point3D(a.getPosition().getX()+size, a.getPosition().getY(), a.getPosition().getZ()-size), secondCol)
        ); //5    +x
        getVertices().add(new Vertex(
                new Point3D(a.getPosition().getX(), a.getPosition().getY()+size, a.getPosition().getZ()-size), secondCol)
        ); //6    +y
        getVertices().add(new Vertex(
                new Point3D(a.getPosition().getX()+size, a.getPosition().getY()+size, a.getPosition().getZ()-size), secondCol)
        ); //7    +x, +y

        //top
        getIndices().add(0); getIndices().add(1);getIndices().add(3);
        getIndices().add(0); getIndices().add(2);getIndices().add(3);

        getIndices().add(0); getIndices().add(1);getIndices().add(4);
        getIndices().add(1); getIndices().add(5);getIndices().add(4);

        getIndices().add(2); getIndices().add(3);getIndices().add(6);
        getIndices().add(3); getIndices().add(6);getIndices().add(7);

        getIndices().add(0); getIndices().add(6);getIndices().add(4);
        getIndices().add(0); getIndices().add(2);getIndices().add(6);

        getIndices().add(1); getIndices().add(5);getIndices().add(7);
        getIndices().add(1); getIndices().add(3);getIndices().add(7);
        //bottom
        getIndices().add(4); getIndices().add(5);getIndices().add(6);
        getIndices().add(5); getIndices().add(6);getIndices().add(7);

        getParts().add(new Part(TopologyType.TRIANGLES, 0, 12));
    }
}
