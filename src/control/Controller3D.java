package control;

import geometryObjects.ArrowX;
import model.Solid;
import model.Vertex;
import raster.ImageBuffer;
import raster.ZBufferVisibility;
import render.RasterizerTriangle;
import render.Renderer;
import render.Triangle;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;
import view.Panel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller3D implements Controller {

    private final Panel panel;

    private ZBufferVisibility zBufferVisibility;
    private RasterizerTriangle rasterizerTriangle;
    private Renderer renderer;

    private int width, height;
    private boolean pressed = false;
    private int ox, oy;

    List<Point> points;
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
        renderer = new Renderer(rasterizerTriangle);

        points = new ArrayList<>();
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
                    points.add(new Point(ox, oy));
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
        if (modeCleared)
            panel.clear();
        width = panel.getRaster().getWidth();
        height = panel.getRaster().getHeight();
        Graphics g = panel.getRaster().getGraphics();
        g.setColor(Color.white);
        g.drawLine(0, 0, width, height); //draw line from start to end
        panel.getRaster().getImg().getGraphics().drawLine(0,0, ox, oy);

        //testing
        zBufferVisibility.drawElementWithTest(10,100,0.5,new Col(0xffff00));
        zBufferVisibility.drawElementWithTest(10,100,0.7,new Col(0xffff));


        //Arrow
        Solid a = new ArrowX();
        renderer.render(a);

        //Color triangle
        rasterizerTriangle.rasterize(new Triangle(
                new Vertex( new Point3D(1, 1 ,0), new Col(1.,0.,0.)),
                new Vertex( new Point3D(-1, 0 ,0), new Col(0.,1.,0.)),
                new Vertex( new Point3D(0, -1 ,0), new Col(0.,0.,1.))
        ));

        for (Point p : points) {
            panel.getRaster().setElement(p.x, p.y, new Col(0xff0000));
        }

        g.drawString("mode (cleared every redraw): " + modeCleared, 10, 10);
        g.drawString("(c) UHK FIM PGRF", width - 120, height - 10);
        panel.repaint();
    }

    private void hardClear() {
        panel.clear();
        initObjects(panel.getRaster());
    }

}
