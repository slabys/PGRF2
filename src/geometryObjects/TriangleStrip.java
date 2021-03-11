package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class TriangleStrip extends Solid {
    Col white = new Col(1.,1,1);
    Col green = new Col(0,1.,0);
    Col blue = new Col(0,0,1.);
    Col red = new Col(1.,0,0);

    /*public TriangleStrip() {
        super();
        //Strip
        getVertices().add(new Vertex(new Point3D(0,0,0.2), white)); //0
        getVertices().add(new Vertex(new Point3D(-0.2,0.2,0.2), blue)); //1
        getVertices().add(new Vertex(new Point3D(0.2,0.2,0.2), blue)); //2
        getVertices().add(new Vertex(new Point3D(-0.2,-0.2,0.2), green)); //3
        getVertices().add(new Vertex(new Point3D(0.2,-0.2,0.2), green)); //4
        getVertices().add(new Vertex(new Point3D(0.4,0,0.2), white)); //5
        getVertices().add(new Vertex(new Point3D(0.6,0.2,0.2), blue)); //6
        getVertices().add(new Vertex(new Point3D(0.6,-0.2,0.2), green)); //7
        getVertices().add(new Vertex(new Point3D(0.8,0,0.2), white)); //8
        //Line
        getVertices().add(new Vertex(new Point3D(1,0,0.2), red)); //9
        //Triangle
        //getVertices().add(new Vertex(new Point3D(1,0,0.2), red)); //9

        getIndices().add(0);getIndices().add(1);getIndices().add(2);
        getIndices().add(0);getIndices().add(3);getIndices().add(4);
        getIndices().add(0);getIndices().add(2);getIndices().add(5);
        getIndices().add(0);getIndices().add(4);getIndices().add(5);
        getIndices().add(5);getIndices().add(2);getIndices().add(6);
        getIndices().add(5);getIndices().add(4);getIndices().add(7);
        getIndices().add(5);getIndices().add(6);getIndices().add(8);
        getIndices().add(5);getIndices().add(7);getIndices().add(8);

        getIndices().add(8);getIndices().add(9); //27

        getParts().add(new Part(TopologyType.TRIANGLES, 0, 8));
        //getParts().add(new Part(TopologyType.LINES, 9, 1));
        //getParts().add(new Part(TopologyType.TRIANGLES, 10, 1));
    }*/

    public TriangleStrip() {
        super();
        //Strip
        getVertices().add(new Vertex(new Point3D(0,0,0.2), white)); //0
        getVertices().add(new Vertex(new Point3D(-0.2,0.2,0.2), blue)); //1
        getVertices().add(new Vertex(new Point3D(0.2,0.2,0.2), blue)); //2
        getVertices().add(new Vertex(new Point3D(-0.2,-0.2,0.2), green)); //3
        getVertices().add(new Vertex(new Point3D(0.2,-0.2,0.2), green)); //4
        getVertices().add(new Vertex(new Point3D(0.4,0,0.2), white)); //5
        getVertices().add(new Vertex(new Point3D(0.6,0.2,0.2), blue)); //6
        getVertices().add(new Vertex(new Point3D(0.6,-0.2,0.2), green)); //7
        getVertices().add(new Vertex(new Point3D(0.8,0,0.2), white)); //8
        //Line
        getVertices().add(new Vertex(new Point3D(1,0,0.2), red)); //9
        //Triangle
        //getVertices().add(new Vertex(new Point3D(1,0,0.2), red)); //9

        getIndices().add(1);getIndices().add(0);getIndices().add(2);getIndices().add(5);getIndices().add(6);getIndices().add(8);
        getIndices().add(3);getIndices().add(0);getIndices().add(4);getIndices().add(5);getIndices().add(7);getIndices().add(8);


        System.out.println("Indicies: " + getIndices().size());
        System.out.println("Verticies: " + getVertices().size());
        getParts().add(new Part(TopologyType.TRIANGLE_STRIP, 0, 6));
        getParts().add(new Part(TopologyType.TRIANGLE_STRIP, 6, 6));
    }
}
