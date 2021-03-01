package render;

import model.Vertex;
import raster.ZBufferVisibility;
import transforms.Vec3D;

import java.awt.*;

public class RasterizerTriangle {
    private ZBufferVisibility zBufferVisibility;
    private int width, height;

    public RasterizerTriangle(ZBufferVisibility zBufferVisibility) {
        this.zBufferVisibility = zBufferVisibility;
        width = zBufferVisibility.getImage().getWidth();
        height = zBufferVisibility.getImage().getHeight();
    }

    public void rasterize(Triangle triangle){
        Vec3D a = triangle.getA().getPosition().ignoreW().mul(new Vec3D(1,-1,1).add(new Vec3D(1,1,0)).mul(new Vec3D((width-1)/2.,(height-1)/2., 1)));
        Vec3D b = triangle.getB().getPosition().ignoreW().mul(new Vec3D(1,-1,1).add(new Vec3D(1,1,0)).mul(new Vec3D((width-1)/2.,(height-1)/2., 1)));
        Vec3D c = triangle.getC().getPosition().ignoreW().mul(new Vec3D(1,-1,1).add(new Vec3D(1,1,0)).mul(new Vec3D((width-1)/2.,(height-1)/2., 1)));

        Graphics g = zBufferVisibility.getImage().getGraphics();
        g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        g.drawLine((int) a.getX(), (int) a.getY(), (int) c.getX(), (int) c.getY());
        g.drawLine((int) b.getX(), (int) b.getY(), (int) b.getX(), (int) b.getY());

        Vertex vA = triangle.getA();
        Vertex vB = triangle.getB();
        Vertex vC = triangle.getC();


        /*Vec3D vec3D;

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
        }*/

        for (int y = (int) a.getY(); y < b.getY(); y++) {
            //if(y <= zBufferVisibility.getImage().getHeight()){
                double s1 = (y - a.getY()) / (b.getY() - a.getY());
                Vec3D ab = a.mul(1 - s1).add(b.mul(s1));
                Vertex vAB = vA.mul(1-s1).add(vB.mul(s1));

                double s2 = (y - a.getY()) / (c.getY() - a.getY());
                Vec3D ac = a.mul(1 - s2).add(c.mul(s2));
                Vertex vAC = vA.mul(1-s2).add(vC.mul(s2));

                for (int x = (int) ab.getX(); x < ac.getX(); x++) {
                    //interpolate z coordinate
                    // double z = abc.getZ();
                    //calculate color
                    double t = (x-ab.getX())/(ac.getX()-ab.getX());
                    Vertex vABC = vAB.mul(1-t).add(vAC.mul(t));
                    zBufferVisibility.drawElementWithTest(x, y, 0.5, vABC.getColor());
                }
            //}
        }
    }
}
