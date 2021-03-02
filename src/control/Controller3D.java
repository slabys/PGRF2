package control;

import geometryObjects.ArrowX;
import model.Solid;
import model.Texture;
import model.Vertex;
import raster.ImageBuffer;
import raster.ZBufferVisibility;
import render.RasterizerTriangle;
import render.Renderer;
import render.Shader;
import render.Triangle;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;
import transforms.Vec3D;
import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller3D implements Controller {

    private final Panel panel;

    private ZBufferVisibility zBufferVisibility;
    private RasterizerTriangle rasterizerTriangle;
    private Renderer renderer;

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

    public void initObjects(ImageBuffer  raster) {
        raster.setClearValue(new Col(0x101010));
        zBufferVisibility = new ZBufferVisibility(raster);
        rasterizerTriangle = new RasterizerTriangle(zBufferVisibility);
        renderer = new Renderer(rasterizerTriangle);

        /*Shader shader = new Shader(){
            @Override
            public Col shade(Vertex vertex) {
                return Texture.getTexel(vertex.getTexCoord().getX(), vertex.getTexCoord().getY());
            }
        };*/

        Shader shader = (Vertex a, Vertex b, Vertex c,Vertex v) -> new Col(
                a.getColor().add(b.getColor().add(c.getColor()).mul(1/3.))
        );

        /*Shader shader = (Vertex a, Vertex b, Vertex c,Vertex v) -> {
            Vec3D normal = v.getNormal().normalized().get();
            Vec3D light = new Vec3D(1,2,3).normalized().get();
            double cosA = normal.dot(light);
            return new Col(cosA, cosA, 1);//vrací difuzní složku osvětlení
        };*/

        rasterizerTriangle.setShader(shader);
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
                new Vertex( new Point3D(1, 1 ,0), new Col(1,0,0), new Vec2D(0,0)),
                new Vertex( new Point3D(-1, 0 ,0), new Col(0,1,0), new Vec2D(0,1)),
                new Vertex( new Point3D(0, -1 ,0), new Col(0,0,1), new Vec2D(0,0))
        ));

        g.drawString("mode (cleared every redraw): " + modeCleared, 10, 10);
        g.drawString("(c) UHK FIM PGRF", width - 120, height - 10);
        panel.repaint();
    }

    private void hardClear() {
        panel.clear();
        initObjects(panel.getRaster());
    }

}
