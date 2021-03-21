package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;
import transforms.Bicubic;
import transforms.Col;
import transforms.Cubic;
import transforms.Point3D;



public class BicubicSurface extends Solid {

    public BicubicSurface() {
        super();
        Bicubic bicubic = new Bicubic(Cubic.FERGUSON,
                new Point3D(2.5,2.5,0),new Point3D(1,1,1),new Point3D(1,1,1),new Point3D(2.5,2.5,0),
                new Point3D(2,2,1),new Point3D(1,1,2),new Point3D(4,1,2),new Point3D(2,3,1),
                new Point3D(2,3,1),new Point3D(1,4,2),new Point3D(4,4,2),new Point3D(3,3,1),
                new Point3D(2.5,2.5,0),new Point3D(1,4,1),new Point3D(4,4,1),new Point3D(2.5,2.5,0)
        );
        for(int i = 0; i < 4;i++) {
            for(double j = 0; j < 1; j += 0.1){
                getVertices().add(new Vertex(bicubic.compute(j,j),new Col(100,255,100)));
            }
        }

        for (int i = 0; i < getVertices().size() - 1; i++) {
            getIndices().add(i);
            getIndices().add(i + 1);
        }

        getParts().add(new Part(TopologyType.LINES, 0, getVertices().size()-1));
    }
}