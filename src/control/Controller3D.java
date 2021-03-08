package control;

import geometryObjects.ArrowX;
import model.Solid;
import model.Texture;
import model.Vertex;
import raster.ImageBuffer;
import raster.ZBufferVisibility;
import render.*;
import transforms.*;
import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller3D implements Controller {

    private final Panel panel;

    private ZBufferVisibility zBufferVisibility;
    private RasterizerTriangle rasterizerTriangle;
    private Renderer renderer;
    private double yTransform = 0, xTransform = 0, zTransform = 0,
            yInc = 0, xInc = 0, zInc = 0, zoom = 1;
    private Camera cameraView = new Camera().withPosition(new Vec3D(-7, 4.5, 5)).withFirstPerson(true);


    private int width, height;
    private boolean pressed = false;
    private int ox, oy;

    boolean modeCleared = false;

    public Controller3D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners(panel);
        redraw();
    }

    public void initObjects(ImageBuffer raster) {
        raster.setClearValue(new Col(0x101010));
        zBufferVisibility = new ZBufferVisibility(raster);
        rasterizerTriangle = new RasterizerTriangle(zBufferVisibility);
        renderer = new Renderer(raster, rasterizerTriangle);

        renderer.setView(cameraView.getViewMatrix());

        initMat();

        Shader shader = v -> Texture.getTexel(v.getTexCoord().getX(), v.getTexCoord().getY());

        //medium color
        /*Shader shader = (Vertex a, Vertex b, Vertex c,Vertex v) -> new Col(
                a.getColor().add(b.getColor()).add(c.getColor()).mul(1/3.)
        );*/

        /*Shader shader = (Vertex a, Vertex b, Vertex c,Vertex v) -> {
            Vec3D normal = v.getNormal().normalized().get();
            Vec3D light = new Vec3D(1,2,3).normalized().get();
            double cosA = normal.dot(light);
            return new Col(cosA, cosA, 1);//vrací difuzní složku osvětlení
        };*/

        rasterizerTriangle.setShader(shader);
    }

    private void initMat() {
        renderer.setModel(new Mat4RotXYZ(xInc, yInc, zInc)
                .mul(new Mat4Transl(xTransform, yTransform, zTransform))
                .mul(new Mat4Scale(zoom, zoom, zoom)));
        renderer.setView(cameraView.getViewMatrix());
        renderer.setProjection(new Mat4PerspRH((float) Math.PI / 1.75, 1, 0, 10));
    }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent ev) {
                if (ev.getButton() == MouseEvent.BUTTON1) {
                    pressed = true;
                    ox = ev.getX();
                    oy = ev.getY();
                    panel.getRaster().setElement(ox, oy, new Col(0xff0000));
                    redraw();
                }
            }

            public void mouseReleased(MouseEvent ev) {
                if (ev.getButton() == MouseEvent.BUTTON1) {
                    panel.getRaster().setElement(ox, oy, new Col(0xffff));
                    pressed = false;
                    redraw();
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent ev) {
                if (pressed) {
                    cameraView = cameraView.addAzimuth((float) Math.PI * (ev.getX() - ox) / (float) panel.getWidth());
                    cameraView = cameraView.addZenith((float) Math.PI * (ev.getY() - oy) / (float) panel.getWidth());
                    ox = ev.getX();
                    oy = ev.getY();
                    panel.getRaster().setElement(ox, oy, new Col(0xffff00));
                    redraw();
                }
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent key) {
                // out.print(key.getKeyCode());
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_BACK_SPACE:
                        hardClear();
                        break;
                    case KeyEvent.VK_M:
                        modeCleared = !modeCleared;
                        break;
                }
                if (key.getKeyCode() == KeyEvent.VK_SHIFT) {
                    cameraView = cameraView.up(0.1);
                    redraw();
                }
                if (key.getKeyCode() == KeyEvent.VK_CONTROL) {
                    cameraView = cameraView.down(0.1);
                    redraw();
                }
                if (key.getKeyCode() == KeyEvent.VK_W) {
                    cameraView = cameraView.forward(0.1);
                    redraw();
                }
                if (key.getKeyCode() == KeyEvent.VK_S) {
                    cameraView = cameraView.backward(0.1);
                    redraw();
                }
                if (key.getKeyCode() == KeyEvent.VK_D) {
                    cameraView = cameraView.right(0.1);
                    redraw();
                }
                if (key.getKeyCode() == KeyEvent.VK_A) {
                    cameraView = cameraView.left(0.1);
                    redraw();
                }
                redraw();
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });
    }

    private void redraw() {
        //if (modeCleared)
            panel.clear();
        width = panel.getRaster().getWidth();
        height = panel.getRaster().getHeight();
        Graphics g = panel.getRaster().getGraphics();
        g.setColor(Color.white);
        //g.drawLine(0, 0, width, height); //draw line from start to end
        //panel.getRaster().getImg().getGraphics().drawLine(0,0, ox, oy);

        //testing
        /*
        zBufferVisibility.drawElementWithZTest(10,100,0.5,new Col(0xffff00));
        zBufferVisibility.drawElementWithZTest(10,120,0.7,new Col(0xffff));
        */


        //Arrow
        Solid a = new ArrowX();
        renderer.render(a);

        //Color triangle
        rasterizerTriangle.rasterize(new Triangle(
                new Vertex( new Point3D(1, 1 ,0), new Col(1.,0,0), new Vec2D(0,0)),
                new Vertex( new Point3D(-1, 0 ,0), new Col(0,1.,0), new Vec2D(0,1)),
                new Vertex( new Point3D(0, -1 ,0), new Col(0,0,1.), new Vec2D(1,0))
        ));

        //renderer.render(scene);
        renderer.setView(cameraView.getViewMatrix());

        g.drawString("mode (cleared every redraw): " + modeCleared, 10, 10);
        g.drawString("(c) UHK FIM PGRF", width - 120, height - 10);
        panel.repaint();
    }

    private void hardClear() {
        panel.clear();
        initObjects(panel.getRaster());
    }

}
