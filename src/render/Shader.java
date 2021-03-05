package render;

import model.Vertex;
import transforms.Col;

public interface Shader {

    Col shade(Vertex a, Vertex b, Vertex c, Vertex v);

    Col shade(Vertex v);


}