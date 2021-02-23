package render;

import model.Vertex;

public class Renderer {
    public void clipTriangle(Triangle triangle){
        Vertex a = triangle.getA();
        Vertex b = triangle.getB();
        Vertex c = triangle.getC();

        Vertex vertex;
        if(a.getPosition().getZ() > c.getPosition().getZ()){
            vertex = a;
            a = c;
            c = vertex;
        }
        if(a.getPosition().getZ() >b.getPosition().getZ()){
            vertex = a;
            a = b;
            b = vertex;
        }
        if(b.getPosition().getZ() > c.getPosition().getZ()){
            vertex = b;
            b = c;
            c = vertex;
        }

        //1. condition
        if(a.getPosition().getZ() <= 0){
            return;
        }
        if(b.getPosition().getZ() <= 0){
            //Calculate intersections D and E
            //dehomog A,B,C
            //rasterizace ADE
        }
    }
}
