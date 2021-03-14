package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class ArrowY extends Solid {
    Col color = new Col(0,1.,0);

    public ArrowY(){
        super();
        setLineColor(new Col(1.,1,1));
        setColor(color);
        getVertices().add(new Vertex(new Point3D(0,0,0), color));
        getVertices().add(new Vertex(new Point3D(0,1,0), color));
        getVertices().add(new Vertex(new Point3D(0,0.8,0.05), color));
        getVertices().add(new Vertex(new Point3D(0.05, 0.8,0), color));
        getVertices().add(new Vertex(new Point3D(0,0.8,-0.05), color));
        getVertices().add(new Vertex(new Point3D(-0.05, 0.8,0), color));


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
