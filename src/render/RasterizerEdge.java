package render;

import geometryObjects.Triangle;
import model.Vertex;
import raster.ZBufferVisibility;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;

public class RasterizerEdge {
    private ZBufferVisibility zBufferVisibility;
    private int width, height;
    private Col color = new Col(1., 1, 1);

    public RasterizerEdge(ZBufferVisibility zBufferVisibility) {
        this.zBufferVisibility = zBufferVisibility;
        width = zBufferVisibility.getImage().getWidth();
        height = zBufferVisibility.getImage().getHeight();
    }

    public void rasterize(Vertex va, Vertex vb) {
        this.color = va.getColor().add(vb.getColor());
        Vec3D a = va.dehomog().get().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));
        Vec3D b = vb.dehomog().get().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));

        drawLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public void rasterize(Vec3D a, Vec3D b, Vec3D c) {
        drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        drawLine((int) a.getX(), (int) a.getY(), (int) c.getX(), (int) c.getY());
        drawLine((int) b.getX(), (int) b.getY(), (int) c.getX(), (int) c.getY());
    }

    private void drawLine(double x1, double y1, double x2, double y2) {
        Graphics g = zBufferVisibility.getImage().getGraphics();
        g.setColor(new Color(color.getRGB()));
        g.drawLine((int) x1,(int) y1,(int) x2,(int) y2);
    }
}
