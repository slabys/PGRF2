package render;

import geometryObjects.Triangle;
import model.Vertex;
import transforms.Col;

@FunctionalInterface
public interface Shader {

    Col shade(Triangle t, Vertex v);

}
