package render;

import geometryObjects.Triangle;
import model.Part;
import model.Solid;
import model.Vertex;
import raster.ImageBuffer;
import raster.ZBufferVisibility;
import transforms.Mat4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Renderer{
    private ImageBuffer raster;
    private ZBufferVisibility zBufferVisibility;
    private RasterizerTriangle rasterizerTriangle;
    private RasterizerEdge rasterizerEdge;
    private int width, height;

    public Renderer(RasterizerTriangle rasterizerTriangle, RasterizerEdge rasterizerEdge) {
        this.rasterizerEdge = rasterizerEdge;
        this.rasterizerTriangle = rasterizerTriangle;
        rasterizerTriangle.setRasterizerEdge(rasterizerEdge);

        this.zBufferVisibility = rasterizerTriangle.getzBufferVisibility();
        this.width = zBufferVisibility.getImage().getWidth();
        this.height = zBufferVisibility.getImage().getHeight();
    }


    public void render(Solid solid, Mat4 mat){

        //transformations
        for(Part part : solid.getParts()){
            switch (part.getType()){
                case LINES -> {
                    for(int i=0; i < part.getCount() ; i++) {
                        int indexA = part.getStartIndex() + i ;
                        int indexB = part.getStartIndex() + i + 1;

                        Vertex a = solid.getVertices().get(solid.getIndices().get(indexA)).transform(mat);
                        Vertex b = solid.getVertices().get(solid.getIndices().get(indexB)).transform(mat);

                        if(a.isInView() && b.isInView()){
                            rasterizerEdge.rasterize(a, b);
                        }else{
                            return;
                        }
                    }
                }
                case POINTS -> {
                    for(int i=0; i < part.getCount() ; i++) {
                        int indexA = part.getStartIndex() + i ;
                        Vertex a = solid.getVertices().get(solid.getIndices().get(indexA)).transform(mat);
                        zBufferVisibility.getImage().setElement(
                                (int) a.getPosition().getX(), (int) a.getPosition().getY(), a.getColor()
                        );
                    }
                }
                case TRIANGLES -> {
                    for(int i=0; i < part.getCount() ; i++){
                        int indexA = part.getStartIndex() + i*3;
                        int indexB = part.getStartIndex() + i*3+1;
                        int indexC = part.getStartIndex() + i*3+2;

                        Vertex a = solid.getVertices().get(solid.getIndices().get(indexA)).transform(mat);
                        Vertex b = solid.getVertices().get(solid.getIndices().get(indexB)).transform(mat);
                        Vertex c = solid.getVertices().get(solid.getIndices().get(indexC)).transform(mat);

                        clipTriangle(new Triangle(a, b, c));

                    }
                }
                case TRIANGLE_STRIP -> {
                    for (int i = 0; i < part.getCount()-2; i++) {
                        int indexA = part.getStartIndex() + i;
                        int indexB = part.getStartIndex() + i + 1;
                        int indexC = part.getStartIndex() + i + 2;

                        Vertex a = solid.getVertices().get(solid.getIndices().get(indexA)).transform(mat);
                        Vertex b = solid.getVertices().get(solid.getIndices().get(indexB)).transform(mat);
                        Vertex c = solid.getVertices().get(solid.getIndices().get(indexC)).transform(mat);

                        clipTriangle(new Triangle(a,b,c));
                    }
                }
            }
        }
    }

    public void clipTriangle(Triangle triangle){

        Vertex a = triangle.getA();
        Vertex b = triangle.getB();
        Vertex c = triangle.getC();

        List<Vertex> vec3DList = Arrays.asList(a, b, c);
        vec3DList.sort(Comparator.comparingDouble(v -> v.getPosition().getZ()));

        a = vec3DList.get(0);
        b = vec3DList.get(1);
        c = vec3DList.get(2);

        if (0 >= a.getPosition().getZ()) {
            return;
        }else if (a.getPosition().getZ() >= 0 && 0 >= b.getPosition().getZ() ) {
            double t = a.getPosition().getZ() / (a.getPosition().getZ() - b.getPosition().getZ());
            Vertex va = a.mul(1 - t).add(b.mul(t));

            t = a.getPosition().getZ() / (a.getPosition().getZ() - c.getPosition().getZ());
            Vertex vb = a.mul(1 - t).add(c.mul(t));
            rasterizerTriangle.rasterize(new Triangle(a, va, vb));
        }else if (b.getPosition().getZ() >= 0 && 0 >= c.getPosition().getZ()) {
            double t = a.getPosition().getZ() / (a.getPosition().getZ() - c.getPosition().getZ());
            Vertex va = a.mul(1 - t).add(c.mul(t));

            t = b.getPosition().getZ() / (b.getPosition().getZ() - c.getPosition().getZ());
            Vertex vb = b.mul(1 - t).add(c.mul(t));
            rasterizerTriangle.rasterize(new Triangle(a, b, va));
            rasterizerTriangle.rasterize(new Triangle(a, va, vb));
        }else{
            rasterizerTriangle.rasterize(triangle);
        }
    }
}
