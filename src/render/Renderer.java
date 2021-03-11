package render;

import geometryObjects.Triangle;
import model.Part;
import model.Solid;
import model.Vertex;
import raster.ImageBuffer;
import raster.ZBufferVisibility;
import transforms.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Renderer{
    private ImageBuffer raster;
    private ZBufferVisibility zBufferVisibility;
    private RasterizerTriangle rasterizerTriangle;
    private RasterizerEdge rasterizerEdge;
    private int width, height;

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

    public Renderer(RasterizerTriangle rasterizerTriangle, RasterizerEdge rasterizerEdge) {
        this.rasterizerEdge = rasterizerEdge;
        this.rasterizerTriangle = rasterizerTriangle;

        this.zBufferVisibility = rasterizerTriangle.getzBufferVisibility();
        this.width = zBufferVisibility.getImage().getWidth();
        this.height = zBufferVisibility.getImage().getHeight();

    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    public Mat4 getModel() {
        return model;
    }

    public Mat4 getView() {
        return view;
    }

    public Mat4 getProjection() {
        return projection;
    }

    public void render(Solid solid){
        Mat4 mat = getModel().mul(getView()).mul(getProjection());

        //transformations
        for(Part part : solid.getParts()){
            switch (part.getType()){
                case LINES -> {
                    //TODO lines
                    for(int i=0; i < part.getCount() ; i++) {
                        int indexA = part.getStartIndex() + i ;
                        int indexB = part.getStartIndex() + i + 1;

                        Vertex a = solid.getVertices().get(solid.getIndices().get(indexA)).transform(mat);
                        Vertex b = solid.getVertices().get(solid.getIndices().get(indexB)).transform(mat);

//                        Vertex temp;
//                        if (a.getPosition().getZ() < b.getPosition().getZ()) {
//                            temp = a;
//                            a = b;
//                            b = temp;
//                        }

                       /* if(a.getPosition().getZ() >= 0 && 0 >= b.getPosition().getZ()){
                            double t = a.getPosition().getZ() / (a.getPosition().getZ() - b.getPosition().getZ());
                            Vertex vb = a.mul(1 - t).add(b.mul(t));

                            final Vec3D vecA = a.getPosition().dehomog().get();
                            final Vec3D vecB = vb.getPosition().dehomog().get();

                            int x1 = (int) ((vecA.getX() + 1) * (zBufferVisibility.getImage().getWidth() - 1) / 2);
                            int y1 = (int) ((1 - vecA.getY()) * (zBufferVisibility.getImage().getHeight() - 1) / 2);
                            int x2= (int) ((vecB.getX() + 1) * (zBufferVisibility.getImage().getWidth() - 1) / 2);
                            int y2 = (int) ((1 - vecB.getY()) * (zBufferVisibility.getImage().getHeight() - 1) / 2);

                            rasterizerEdge.rasterize(x1, y1, x2, y2);

                        }else if (a.isInView() && b.isInView()){
                            final Vec3D vecA = a.getPosition().dehomog().get();
                            final Vec3D vecB = b.getPosition().dehomog().get();

                            int x1 = (int) ((vecA.getX() + 1) * (zBufferVisibility.getImage().getWidth() - 1) / 2);
                            int y1 = (int) ((1 - vecA.getY()) * (zBufferVisibility.getImage().getHeight() - 1) / 2);
                            int x2 = (int) ((vecB.getX() + 1) * (zBufferVisibility.getImage().getWidth() - 1) / 2);
                            int y2 = (int) ((1 - vecB.getY()) * (zBufferVisibility.getImage().getHeight() - 1) / 2);

                            rasterizerEdge.rasterize(x1, y1, x2, y2);
                        }*/
                        //TODO.... Is clip demanded or is Graphics.drawLine enough?
                        /*if(a.isInView() && b.isInView()) {
                            final Vec3D vecA = a.getPosition().dehomog().get();
                            final Vec3D vecB = b.getPosition().dehomog().get();

                            int x1 = (int) ((vecA.getX() + 1) * (zBufferVisibility.getImage().getWidth() - 1) / 2);
                            int y1 = (int) ((1 - vecA.getY()) * (zBufferVisibility.getImage().getHeight() - 1) / 2);
                            int x2 = (int) ((vecB.getX() + 1) * (zBufferVisibility.getImage().getWidth() - 1) / 2);
                            int y2 = (int) ((1 - vecB.getY()) * (zBufferVisibility.getImage().getHeight() - 1) / 2);

                            rasterizerEdge.rasterize(x1, y1, x2, y2);
                        }*/

                        /*if (0 >= a.getPosition().getZ() && a.getPosition().getZ() >= b.getPosition().getZ()) {
                            return;
                        }

                        if (a.getPosition().getZ() >= b.getPosition().getZ() && b.getPosition().getZ() >= 0 && c.getPosition().getZ() >= 0){
                            rasterizerEdge.rasterize(va, vb);
                        }
                           */
                        rasterizerEdge.rasterize(a, b);
                    }
                }
                case POINTS -> {
                    //TODO points
                    //raster.setElement();

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

                        rasterizerTriangle.rasterize(new Triangle(a, b, c));
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

        if (a.getPosition().getZ() >= 0 && 0 >= b.getPosition().getZ() && b.getPosition().getZ() >= c.getPosition().getZ()) {
            double t = a.getPosition().getZ() / (a.getPosition().getZ() - b.getPosition().getZ());
            Vertex va = a.mul(1 - t).add(b.mul(t));

            t = a.getPosition().getZ() / (a.getPosition().getZ() - c.getPosition().getZ());
            Vertex vb = a.mul(1 - t).add(c.mul(t));
            rasterizerTriangle.rasterize(new Triangle(a, va, vb));
        }


        if (a.getPosition().getZ() >= b.getPosition().getZ() && b.getPosition().getZ() >= 0 && 0 >= c.getPosition().getZ()) {
            double t = a.getPosition().getZ() / (a.getPosition().getZ() - c.getPosition().getZ());
            Vertex va = a.mul(1 - t).add(c.mul(t));

            t = b.getPosition().getZ() / (b.getPosition().getZ() - c.getPosition().getZ());
            Vertex vb = b.mul(1 - t).add(c.mul(t));
            rasterizerTriangle.rasterize(new Triangle(a, b, va));
            rasterizerTriangle.rasterize(new Triangle(a, va, vb));
        }

        if (a.getPosition().getZ() >= b.getPosition().getZ() && b.getPosition().getZ() >= 0 && c.getPosition().getZ() >= 0){
            rasterizerTriangle.rasterize(triangle);
        }

    }
}
