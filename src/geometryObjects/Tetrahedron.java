package geometryObjects;

import model.Part;
import model.Solid;
import model.TopologyType;
import model.Vertex;

public class Tetrahedron extends Solid {
    public Tetrahedron(Vertex a, Vertex b, Vertex c, Vertex d){
        super();
        getVertices().add(a);
        getVertices().add(b);
        getVertices().add(c);
        getVertices().add(d);

        getIndices().add(0); getIndices().add(1);getIndices().add(2);
        getIndices().add(0); getIndices().add(2);getIndices().add(3);
        getIndices().add(0); getIndices().add(1);getIndices().add(3);
        getIndices().add(1); getIndices().add(2);getIndices().add(3);


        getParts().add(new Part(TopologyType.TRIANGLES, 0, 4));
    }
}
