package render;

import raster.ZBufferVisibility;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;

public class RasterizerTriangle {
    private ZBufferVisibility zBufferVisibility;
    private int width, height;

    public RasterizerTriangle(ZBufferVisibility zBufferVisibility, int width, int heigh) {
        this.zBufferVisibility = zBufferVisibility;
        this.width = zBufferVisibility.getImage().getWidth();
        this.height = zBufferVisibility.getImage().getHeight();
    }

    void rasterize(Triangle triangle){
        Vec3D a = triangle.getA().getPosition().ignoreW().mul(new Vec3D(1,-1,1).add(new Vec3D(1,1,0)).mul(new Vec3D((width-1)/2.,(height-1)/2., 1)));
        Vec3D b = triangle.getB().getPosition().ignoreW().mul(new Vec3D(1,-1,1).add(new Vec3D(1,1,0)).mul(new Vec3D((width-1)/2.,(height-1)/2., 1)));
        Vec3D c = triangle.getC().getPosition().ignoreW().mul(new Vec3D(1,-1,1).add(new Vec3D(1,1,0)).mul(new Vec3D((width-1)/2.,(height-1)/2., 1)));

        Graphics g = zBufferVisibility.getImage().getGraphics();
        g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        g.drawLine((int) a.getX(), (int) a.getY(), (int) c.getX(), (int) c.getY());
        g.drawLine((int) b.getX(), (int) b.getY(), (int) b.getX(), (int) b.getY());

        Vec3D vec3D;

        if(a.getY() > c.getY()){
            vec3D = a;
            a = c;
            c = vec3D;
        }
        if(a.getY() > b.getY()){
            vec3D = a;
            a = b;
            b = vec3D;
        }
        if (b.getY() > c.getY()){
            vec3D = b;
            b = c;
            c = vec3D;
        }

        for (int y = (int) a.getY(); y > b.getY(); y--) {
            if(y <= zBufferVisibility.getImage().getHeight()){
                double s1 = (y - b.getY()) / (a.getY() - b.getY());
                Vec3D ab = a.mul(s1).add(b.mul(1 - s1));

                double s2 = (y - c.getY()) / (a.getY() - c.getY());
                Vec3D ac = a.mul(s2).add(c.mul(1 - s2));

                for (int x = (int) ab.getX(); x < ac.getX(); x++) {
                    //interpolate z coordinate
                    //calculate color
                    zBufferVisibility.drawElementWithTest(x, y, 0.5, new Col(0xff0000));
                }
            }
        }
    }
}
