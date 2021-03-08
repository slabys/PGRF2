package render;

import model.Part;
import model.Solid;
import model.Vertex;
import raster.ImageBuffer;
import transforms.Mat4;
import transforms.Mat4Identity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Renderer{
    private ImageBuffer raster;
    private RasterizerTriangle rasterizerTriangle;

    private Mat4 model = new Mat4Identity();
    private Mat4 view = new Mat4Identity();
    private Mat4 projection = new Mat4Identity();

    public Renderer(RasterizerTriangle rasterizerTriangle) {
        this.rasterizerTriangle = rasterizerTriangle;
    }

    public Renderer(ImageBuffer raster, RasterizerTriangle rasterizerTriangle) {
        this.raster = raster;
        this.rasterizerTriangle = rasterizerTriangle;
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public Mat4 getView() {
        return view;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public Mat4 getProjection() {
        return projection;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    public void render(Solid solid){
        Mat4 mat = solid.getModel().mul(view).mul(projection);

        //transformations
        for(Part part : solid.getParts()){
            switch (part.getType()){
                case LINES -> {
                    //TODO lines
                    for(int i=0; i < part.getCount() ; i++) {
                        int indexA = part.getStartIndex() + i ;
                        int indexB = part.getStartIndex() + i  + 1;
                        //TODO ??? RIGHT OR NOT ??? (static)
                        //RasterizerEdge.rasterize(a, b);
                    }
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

    public void render(Scene scene){
        for (Solid solid : scene.getSolids()){
            render(solid);
        }
    }

    public void clipTriangle(Triangle triangle){

        Vertex a = triangle.getA();
        Vertex b = triangle.getB();
        Vertex c = triangle.getC();
        
        Vertex temp;
        if (a.getPosition().getZ() < c.getPosition().getZ()) {
            temp = a;
            a = c;
            c = temp;
        }
        if (a.getPosition().getZ() < b.getPosition().getZ()) {
            temp = a;
            a = b;
            b = temp;
        }
        if (b.getPosition().getZ() < c.getPosition().getZ()) {
            temp = b;
            b = c;
            c = temp;
        }

        if (0 >= a.getPosition().getZ() && a.getPosition().getZ() >= b.getPosition().getZ() && b.getPosition().getZ() >= c.getPosition().getZ()) {
            return;
        }

        double t = a.getPosition().getZ() / (a.getPosition().getZ() - b.getPosition().getZ());
        //double tt = 1 - t;

        Vertex va = a.mul(1 - t).add(b.mul(t)).dehomog();
        Vertex vb = b.mul(1 - t).add(c.mul(t)).dehomog();

        if (a.getPosition().getZ() >= 0 && 0 >= b.getPosition().getZ() && b.getPosition().getZ() >= c.getPosition().getZ()) {
            rasterizerTriangle.rasterize(new Triangle(a, va, vb));
        }


        if (a.getPosition().getZ() >= b.getPosition().getZ() && b.getPosition().getZ() >= 0 && 0 >= c.getPosition().getZ()) {
            rasterizerTriangle.rasterize(new Triangle(a, b, va));
            rasterizerTriangle.rasterize(new Triangle(a, va, vb));
        }

        if (a.getPosition().getZ() >= b.getPosition().getZ() && b.getPosition().getZ() >= 0 && c.getPosition().getZ() >= 0){
            rasterizerTriangle.rasterize(triangle);
        }
    }
}
