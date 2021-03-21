package render;

import geometryObjects.Triangle;
import model.RenderType;
import model.Vertex;
import raster.ZBufferVisibility;
import transforms.Vec3D;

import java.util.*;
import java.util.List;

public class RasterizerTriangle {
    private ZBufferVisibility zBufferVisibility;
    private RasterizerEdge rasterizerEdge;
    private Shader shader;
    private RenderType renderType = RenderType.Combine;
    private int width, height;

    public RenderType getRenderType() {
        return renderType;
    }

    public void setRenderType(RenderType renderType) {
        this.renderType = renderType;
    }

    public ZBufferVisibility getzBufferVisibility() {
        return zBufferVisibility;
    }

    public void setRasterizerEdge(RasterizerEdge rasterizerEdge) {
        this.rasterizerEdge = rasterizerEdge;
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
        //Outline
        if(zBufferVisibility.getOutline()) rasterizerEdge.rasterize(a, b, c);

        Vertex vA = triangle.getA();
        Vertex vB = triangle.getB();
        Vertex vC = triangle.getC();

        List<Vec3D> vec3DList = Arrays.asList(a, b, c);
        Collections.sort(vec3DList,Comparator.comparingDouble(Vec3D::getY));

        a = vec3DList.get(0);
        b = vec3DList.get(1);
        c = vec3DList.get(2);

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
            }
            for (int x = Math.max((int) ab.getX()+1, 0); x < Math.min(ac.getX(), width-1); x++) {
                double t = (x - ab.getX()) / (ac.getX() - ab.getX());
                double z = ab.mul(1 - t).add(ac.mul(t)).getZ();
                Vertex vABC = vAB.mul(1 - t).add(vAC.mul(t));
                zBufferVisibility.drawElementWithZTest(x, y, z, shader.shade(triangle, vABC));
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
            }
            for (int x = Math.max((int) bc.getX()+1, 0); x < Math.min(ac.getX(), width-1); x++) {
                double t = (x - bc.getX()) / (ac.getX() - bc.getX());
                double z = bc.mul(1 - t).add(ac.mul(t)).getZ();
                Vertex vABC = vBC.mul(1 - t).add(vAC.mul(t));
                zBufferVisibility.drawElementWithZTest(x, y, z, shader.shade(triangle, vABC));
            }
        }
    }
}
