package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;

public class Triangle extends Solid {
    Vertex a,b,c;

    public Triangle(Vertex a, Vertex b, Vertex c) {
        this.a = a;
        this.b = b;
        this.c = c;
        getParts().add(new Part(TopologyType.TRIANGLES, 0,1));
        getIndices().add(0);getIndices().add(1);getIndices().add(2);
        getVertices().add(a);getVertices().add(b);getVertices().add(c);
    }

    public Vertex getA() {
        return a;
    }

    public Vertex getB() {
        return b;
    }

    public Vertex getC() {
        return c;
    }


}
