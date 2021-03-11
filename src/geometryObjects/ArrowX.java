package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class ArrowX extends Solid {
    Col color = new Col(1.,0,0);

    public ArrowX(){
        super();
        getVertices().add(new Vertex(new Point3D(0,0,0), color)); //0
        getVertices().add(new Vertex(new Point3D(1,0,0), color)); //1
        getVertices().add(new Vertex(new Point3D(0.95,0.1,0), color)); //2
        getVertices().add(new Vertex(new Point3D(0.95,0,0.1), color)); //3
        getVertices().add(new Vertex(new Point3D(0.95,-0.1,0), color)); //4
        getVertices().add(new Vertex(new Point3D(0.95,0,-0.1), color)); //5


        getIndices().add(0);getIndices().add(1);

        getIndices().add(1);getIndices().add(2);getIndices().add(3);
        getIndices().add(1);getIndices().add(3);getIndices().add(4);
        getIndices().add(1);getIndices().add(3);getIndices().add(4);
        getIndices().add(1);getIndices().add(4);getIndices().add(5);
        getIndices().add(1);getIndices().add(5);getIndices().add(2);
        getIndices().add(1);getIndices().add(2);getIndices().add(3);
        getIndices().add(1);getIndices().add(5);getIndices().add(3);

        getParts().add(new Part(TopologyType.LINES, 0, 1));
        getParts().add(new Part(TopologyType.TRIANGLES, 2, 7));
    }
}
