package render;

import model.Vertex;
import raster.ZBufferVisibility;
import transforms.Col;
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

    public void rasterize(Vec3D va, Vec3D vb) {
        Vec3D a = va.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));
        Vec3D b = vb.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));

        Graphics g =  zBufferVisibility.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.drawLine(
                (int) a.getX(),
                (int) a.getY(),
                (int) b.getX(),
                (int) b.getY()
        );
    }

    public void rasterize(Vertex va, Vertex vb) {
        Vec3D a = va.dehomog().get().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));
        Vec3D b = vb.dehomog().get().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));

        Graphics g =  zBufferVisibility.getImage().getGraphics();
        g.setColor(new Color(new Col(va.getColor().add(vb.getColor())).getRGB()));
        g.drawLine(
                (int) a.getX(),
                (int) a.getY(),
                (int) b.getX(),
                (int) b.getY()
        );
    }

    public void rasterize(int x1, int y1, int x2,int y2) {
        Graphics g =  zBufferVisibility.getImage().getGraphics();
        g.setColor(Color.WHITE);
        g.drawLine(x1,  y1,  x2, y2);
    }
}
