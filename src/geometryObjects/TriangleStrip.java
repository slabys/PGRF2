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
    private double sx = 0.1,sy = 0.1,sz = 0.1;

    public TriangleStrip() {
        super();
        //Strip
        getVertices().add(new Vertex(new Point3D(0+sx,0+sy,0.1+sz), white)); //0
        getVertices().add(new Vertex(new Point3D(-0.1+sx,0.1+sy,0.1+sz), blue)); //1
        getVertices().add(new Vertex(new Point3D(0.1+sx,0.1+sy,0.1+sz), blue)); //2
        getVertices().add(new Vertex(new Point3D(-0.1+sx,-0.1+sy,0.1+sz), green)); //3
        getVertices().add(new Vertex(new Point3D(0.1+sx,-0.1+sy,0.1+sz), green)); //4
        getVertices().add(new Vertex(new Point3D(0.2+sx,0+sy,0.1+sz), white)); //5
        getVertices().add(new Vertex(new Point3D(0.3+sx,0.1+sy,0.1+sz), blue)); //6
        getVertices().add(new Vertex(new Point3D(0.3+sx,-0.1+sy,0.1+sz), green)); //7
        getVertices().add(new Vertex(new Point3D(0.4+sx,0+sy,0.1+sz), white)); //8
        //Line
        getVertices().add(new Vertex(new Point3D(0.5+sx,0+sy,0.1+sz), white)); //9
        //Triangle
        getVertices().add(new Vertex(new Point3D(0.5+sx,0.1+sy,0.1+sz), red)); //10
        getVertices().add(new Vertex(new Point3D(0.5+sx,-0.1+sy,0.1+sz), red)); //11
        getVertices().add(new Vertex(new Point3D(0.6+sx,0+sy,0.1+sz), red)); //12

        getIndices().add(1);getIndices().add(0);getIndices().add(2);getIndices().add(5);getIndices().add(6);getIndices().add(8);
        getIndices().add(3);getIndices().add(0);getIndices().add(4);getIndices().add(5);getIndices().add(7);getIndices().add(8);
        getIndices().add(9);getIndices().add(10);
        getIndices().add(10);getIndices().add(11);getIndices().add(12);

        getParts().add(new Part(TopologyType.TRIANGLE_STRIP, 0, 6));
        getParts().add(new Part(TopologyType.TRIANGLE_STRIP, 6, 6));
        getParts().add(new Part(TopologyType.LINES, 11, 1));
        getParts().add(new Part(TopologyType.TRIANGLES, 14, 1));
    }
}
