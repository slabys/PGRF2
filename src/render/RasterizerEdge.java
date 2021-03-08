package render;

import model.Vertex;
import raster.ZBufferVisibility;
import transforms.Vec3D;

import java.awt.*;

public class RasterizerEdge {
    private ZBufferVisibility zBufferVisibility;
    private int width, height;

    public RasterizerEdge(ZBufferVisibility zBufferVisibility) {
        this.zBufferVisibility = zBufferVisibility;
        width = zBufferVisibility.getImage().getWidth();
        height = zBufferVisibility.getImage().getHeight();
    }

    public static void rasterize(Vertex a, Vertex b) {

    }
}
