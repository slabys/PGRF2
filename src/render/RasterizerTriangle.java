package render;

import geometryObjects.Triangle;
import model.Vertex;
import raster.ZBufferVisibility;
import transforms.Vec3D;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RasterizerTriangle {
    private ZBufferVisibility zBufferVisibility;
    private Shader shader;
    private int width, height;

    public ZBufferVisibility getzBufferVisibility() {
        return zBufferVisibility;
    }

    public RasterizerTriangle(ZBufferVisibility zBufferVisibility) {
        this.zBufferVisibility = zBufferVisibility;
        width = zBufferVisibility.getImage().getWidth();
        height = zBufferVisibility.getImage().getHeight();
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void rasterize(Triangle triangle) {
        Vec3D a = triangle.getA().dehomog().get().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));
        Vec3D b = triangle.getB().dehomog().get().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));
        Vec3D c = triangle.getC().dehomog().get().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((width - 1) / 2, (height - 1) / 2, 1));

        //obvod trojuhelníku
        /*Graphics g = zBufferVisibility.getImage().getGraphics();
        g.setColor(new Color((triangle.getA().getColor().add(triangle.getB().getColor()).add(triangle.getC().getColor())).getRGB()/3));
        g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        g.drawLine((int) a.getX(), (int) a.getY(), (int) c.getX(), (int) c.getY());
        g.drawLine((int) b.getX(), (int) b.getY(), (int) c.getX(), (int) c.getY());*/

        Vertex vA = triangle.getA();
        Vertex vB = triangle.getB();
        Vertex vC = triangle.getC();

        List<Vec3D> vec3DList = Arrays.asList(a, b, c);
        vec3DList.sort((v1, v2) -> Double.compare(v2.getY(), v1.getY()));

        a = vec3DList.get(2);
        b = vec3DList.get(1);
        c = vec3DList.get(0);

        for (int y = Math.max((int) a.getY()+1, 0); y < Math.min(b.getY(), height - 1); y++) {
            double s1 = (y - a.getY()) / (b.getY() - a.getY());
            double s2 = (y - a.getY()) / (c.getY() - a.getY());
            Vec3D ab = a.mul(1 - s1).add(b.mul(s1));
            Vec3D ac = a.mul(1 - s2).add(c.mul(s2));
            Vertex vAB = vA.mul(1 - s1).add(vB.mul(s1));
            Vertex vAC = vA.mul(1 - s2).add(vC.mul(s2));
            if (ab.getX() > ac.getX()) {
                Vec3D tmp = ab;
                ab = ac;
                ac = tmp;

                Vertex vtmp = vAB;
                vAB = vAC;
                vAC = vtmp;
            }
            for (int x = Math.max((int) ab.getX()+1, 0); x < Math.min(ac.getX(), width-1); x++) {
                double t = (x - ab.getX()) / (ac.getX() - ab.getX());
                Vec3D abc = ab.mul(1 - t).add(ac.mul(t));
                Vertex vABC = vAB.mul(1 - t).add(vAC.mul(t));
                zBufferVisibility.drawElementWithZTest(x, y, abc.getZ(), vABC.getColor());
            }
        }


        for (int y = Math.max((int) b.getY()+1, 0); y < Math.min(c.getY(), height - 1); y++) {
            double s1 = (y - b.getY()) / (c.getY() - b.getY());
            double s2 = (y - a.getY()) / (c.getY() - a.getY());
            Vec3D bc = b.mul(1 - s1).add(c.mul(s1));
            Vec3D ac = a.mul(1 - s2).add(c.mul(s2));
            Vertex vBC = vB.mul(1 - s1).add(vC.mul(s1));
            Vertex vAC = vA.mul(1 - s2).add(vC.mul(s2));
            if (bc.getX() > ac.getX()) {
                Vec3D tmp = bc;
                bc = ac;
                ac = tmp;

                Vertex vtmp = vBC;
                vBC = vAC;
                vAC = vtmp;
            }
            for (int x = Math.max((int) bc.getX()+1, 0); x < Math.min(ac.getX(), width-1); x++) {
                double t = (x - bc.getX()) / (ac.getX() - bc.getX());
                Vec3D abc = bc.mul(1 - t).add(ac.mul(t));
                Vertex vABC = vBC.mul(1 - t).add(vAC.mul(t));
                zBufferVisibility.drawElementWithZTest(x, y, abc.getZ(), vABC.getColor());
            }
        }
    }
}
