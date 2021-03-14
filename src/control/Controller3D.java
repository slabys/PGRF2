package control;

import geometryObjects.*;
import model.RenderType;
import model.Solid;
import model.Texture;
import model.Vertex;
import raster.ImageBuffer;
import raster.ZBufferVisibility;
import render.*;
import render.Renderer;
import transforms.*;
import view.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller3D implements Controller {

    private final Panel panel;

    Solid arrowX = new ArrowX();
    Solid arrowY = new ArrowY();
    Solid arrowZ = new ArrowZ();
    TriangleStrip triangleStrip = new TriangleStrip();
    Cube cube = new Cube(
            new Vertex(new Point3D(-0.3,-0.3,0.5), new Col(0.7,0.0,0.0)), 0.2
    );
    Tetrahedron tetrahedron = new Tetrahedron(
            new Vertex(new Point3D(0, 1,0), new Col(0.7,0.7,0.7)),
            new Vertex(new Point3D(0.4, 1,0), new Col(0.7,0.7,0.7)),
            new Vertex(new Point3D(1, 2,0), new Col(0.7,0.7,0.7)),
            new Vertex(new Point3D(0, 1,0.4), new Col(0.7,0.7,0.7))
    );

    Triangle triangle = new Triangle(
            new Vertex( new Point3D(0.5, -0.5 ,0), new Col(1.,0,0), new Vec2D(0,0)),
            new Vertex( new Point3D(0.1, -1. ,0), new Col(0,1.,0), new Vec2D(0,1)),
            new Vertex( new Point3D(1., -1 ,2), new Col(0,0,1.), new Vec2D(1,0))
            );

    private ZBufferVisibility zBufferVisibility;
    private RasterizerTriangle rasterizerTriangle;
    private RasterizerEdge rasterizerEdge;
    private Renderer renderer;
    private double yTransform = 0, xTransform = 0, zTransform = 0,
            yInc = 0, xInc = 0, zInc = 0, zoom = 1;
    private boolean perspective = true;
    private JComboBox comboBox = new JComboBox();
    private Col changeColor = null;

    private Scene scene;
    private Solid active;
    private List<Solid> axis = new ArrayList();
    private Camera cameraView = new Camera()
            .withPosition(new Vec3D(-0.1,0.2,0.4)).withAzimuth(0.5).withZenith(-0.25).withFirstPerson(false);


    private int width, height;
    private boolean pressed = false;
    private int ox, oy;

    boolean modeCleared = true;

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
        rasterizerEdge = new RasterizerEdge(zBufferVisibility);
        renderer = new Renderer(rasterizerTriangle, rasterizerEdge);
        scene = new Scene(renderer);

        initMat();
        initGeometry();

        Shader shader = v -> Texture.getTexel(v.getTexCoord().getX(), v.getTexCoord().getY());
        rasterizerTriangle.setShader(shader);

    }

    private void initMat() {
        scene.setView(cameraView.getViewMatrix());
        scene.setProjection(new Mat4PerspRH((float) Math.PI / 2, 1, 0.1, 100));
    }

    private void initGeometry() {
        scene.getAxis().addAll(Arrays.asList(arrowX, arrowY, arrowZ));
        scene.getSolids().addAll(Arrays.asList(triangleStrip, tetrahedron, triangle, cube));
        triangle.setRenderType(RenderType.Shader);
    }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent ev) {
                if (ev.getButton() == MouseEvent.BUTTON1) {
                    pressed = true;
                    ox = ev.getX();
                    oy = ev.getY();
                    redraw();
                }
            }

            public void mouseReleased(MouseEvent ev) {
                if (ev.getButton() == MouseEvent.BUTTON1) {
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
                    redraw();
                }
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent key) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_BACK_SPACE:
                        hardClear();
                        break;
                    case KeyEvent.VK_M:
                        modeCleared = !modeCleared;
                        panel.clear();
                        break;
                }

                switch (key.getKeyCode()) {
                    //Movement
                    case KeyEvent.VK_SHIFT -> {
                        cameraView = cameraView.up(0.1);
                        redraw();
                    }
                    case KeyEvent.VK_CONTROL -> {
                        cameraView = cameraView.down(0.1);
                        redraw();
                    }
                    case KeyEvent.VK_W -> {
                        cameraView = cameraView.forward(0.1);
                        redraw();
                    }
                    case KeyEvent.VK_S -> {
                        cameraView = cameraView.backward(0.1);
                        redraw();
                    }
                    case KeyEvent.VK_A -> {
                        cameraView = cameraView.left(0.1);
                        redraw();
                    }
                    case KeyEvent.VK_D -> {
                        cameraView = cameraView.right(0.1);
                        redraw();
                    }
                    //Rotations
                    case KeyEvent.VK_E -> zInc += 0.1;
                    case KeyEvent.VK_Q -> zInc -= 0.1;
                    case KeyEvent.VK_R -> yInc += 0.1;
                    case KeyEvent.VK_F -> yInc -= 0.1;
                    case KeyEvent.VK_X -> xInc -= 0.1;
                    case KeyEvent.VK_C -> xInc += 0.1;
                    //Transforms
                    case KeyEvent.VK_NUMPAD9 -> zTransform += 0.1;
                    case KeyEvent.VK_NUMPAD7 -> zTransform -= 0.1;
                    case KeyEvent.VK_NUMPAD4 -> yTransform += 0.1;
                    case KeyEvent.VK_NUMPAD6 -> yTransform -= 0.1;
                    case KeyEvent.VK_NUMPAD8 -> xTransform += 0.1;
                    case KeyEvent.VK_NUMPAD5 -> xTransform -= 0.1;
                    //zoom
                    case KeyEvent.VK_NUMPAD1 -> zoom += 0.1;
                    case KeyEvent.VK_NUMPAD2 -> zoom -= 0.1;
                    //extends
                    case KeyEvent.VK_P -> {
                        if (perspective) {
                            scene.setProjection(new Mat4OrthoRH(
                                    2, 2, 0.1, 100));
                        } else {
                            scene.setProjection(new Mat4PerspRH(
                                    (float) Math.PI / 2, 1, 0.1, 100));
                        }
                        perspective = !perspective;
                        redraw();
                    }

                    case KeyEvent.VK_G -> {
                        JOptionPane.showMessageDialog(null,
                                getSolidDialog(), "Choose solid dialog", JOptionPane.PLAIN_MESSAGE);

                        if(comboBox.getSelectedItem() != null){
                            JOptionPane.showMessageDialog(null,
                                    getTransformDialog(),
                                    comboBox.getSelectedItem().toString() + " transform dialog",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                        redraw();
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + key.getKeyCode());
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

    private Object getTransformDialog() {
        JPanel jPanel = new JPanel();

        JLabel geometryObj = new JLabel(comboBox.getSelectedItem().toString());

        Font font = new Font("Courier", Font.BOLD,12);
        geometryObj.setFont(font);

        Button btnActive = new Button("Set active: " + geometryObj.getText());

        for (Solid s : scene.getSolids()) {

            if (s.getClass().getName().substring(16).equals(comboBox.getSelectedItem().toString())) {
                active = s;
            }
        }

        ActionListener transformListener = actionEvent -> {
            deActivate();
            active.setActiveSolid();
        };

        btnActive.addActionListener(transformListener);
        jPanel.add(btnActive);
        return jPanel;
    }

    private void deActivate() {
        for (Solid deActive : scene.getSolids()) {
            deActive.deActiveSolid();
        }
    }

    private JPanel getSolidDialog() {
        JPanel jPanel = new JPanel();
        jPanel.add(new Label("Choose what solid do you want to change:"));
        List<String> options = new ArrayList<>();

        for (Solid s : scene.getSolids()) {
            String tmpStr = s.getClass().getName();
            options.add(tmpStr.substring(16));
        }

        JComboBox comboBox = new JComboBox(options.toArray());
        setComboBox(comboBox);
        comboBox.addItemListener(e -> setComboBox(comboBox));
        jPanel.add(comboBox);
        return jPanel;
    }

    private void setComboBox(JComboBox comboBox) {
        this.comboBox = comboBox;
    }

    private void redraw() {
        if (modeCleared)
            panel.clear();
        width = panel.getRaster().getWidth();
        height = panel.getRaster().getHeight();

        Graphics g = panel.getRaster().getGraphics();
        g.setColor(Color.white);
        g.drawString("mode (cleared every redraw): " + modeCleared, 10, 10);
        g.drawString("(c) UHK FIM PGRF", width - 120, height - 10);

        scene.getSolids().forEach(solid -> {
            if(solid.isActive()){
                solid.setModel(new Mat4RotXYZ(xInc, yInc, zInc)
                        .mul(new Mat4Transl(xTransform, yTransform, zTransform))
                        .mul(new Mat4Scale(zoom, zoom, zoom)));
            }
        });

        scene.render(scene);

        zBufferVisibility.clear();
        scene.setView(cameraView.getViewMatrix());
        panel.repaint();
    }

    private void hardClear() {
        panel.clear();
        initObjects(panel.getRaster());
    }

}
