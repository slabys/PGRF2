package render;

import model.Part;
import model.Solid;
import model.Vertex;

import java.util.List;

public class Renderer {
    private RasterizerTriangle rasterizerTriangle;

    public Renderer(RasterizerTriangle rasterizerTriangle) {
        this.rasterizerTriangle = rasterizerTriangle;
    }

    public void render(Solid solid){
        //transformations
        for(Part part : solid.getParts()){
            switch (part.getType()){
                case LINES -> {
                    //TODO lines
                }
                case POINTS -> {
                    //TODO points
                }
                case TRIANGLES -> {
                    for(int i=0; i < part.getCount() ; i++){
                        int indexA = part.getStartIndex() + i*3;
                        int indexB = part.getStartIndex() + i*3+1;
                        int indexC = part.getStartIndex() + i*3+2;

                        Vertex a = solid.getVertices().get(solid.getIndices().get(indexA));
                        Vertex b = solid.getVertices().get(solid.getIndices().get(indexB));
                        Vertex c = solid.getVertices().get(solid.getIndices().get(indexC));

                        clipTriangle(new Triangle(a, b, c));
                    }
                }
            }
        }
    }

    public void render(List<Solid> scene){
        for (Solid solid : scene){
            render(solid);
        }
    }

    public void clipTriangle(Triangle triangle){
        /*
        Vertex a = triangle.a;
        Vertex b = triangle.b;
        Vertex c = triangle.c;

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
        }*/

        /*
        //1. condition
        if(a.getPosition().getZ() <= 0){
            return;
        }
        if(b.getPosition().getZ() <= 0){
            //Calculate intersections D and E
            //dehomog A,B,C
            //rasterizace ADE
        }
        */


        rasterizerTriangle.rasterize(triangle);
    }
}
