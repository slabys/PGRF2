package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;
import transforms.Point3D;

public class Cube extends Solid {
    public Cube(Vertex a, double size){
        super();
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

        getIndices().add(0); getIndices().add(1);getIndices().add(3);
        getIndices().add(0); getIndices().add(2);getIndices().add(3);


        getParts().add(new Part(TopologyType.TRIANGLES, 0, 2));
    }
}
