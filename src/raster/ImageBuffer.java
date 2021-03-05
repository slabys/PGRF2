package raster;

import transforms.Col;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBuffer implements Raster<Col> {

    private final BufferedImage img;

    public BufferedImage getImg() {
        return img;
    }

    private Col col;

    public ImageBuffer(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        col = new Col(BufferedImage.TYPE_INT_RGB);
    }

    public void repaint(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw(ImageBuffer raster) {
        Graphics graphics = getGraphics();
        graphics.setColor(new Color(col.getRGB()));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawImage(raster.img, 0, 0, null);
    }

    public Graphics getGraphics() {
        return img.getGraphics();
    }

    @Override
    public Col getElement(int x, int y) {
        return new Col(img.getRGB(x, y));
    }

    @Override
    public void setElement(int x, int y, Col value) {
        img.setRGB(x, y, value.getRGB());
    }

    @Override
    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(new Color(col.getRGB()));
        g.clearRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
    }

    @Override
    public void setClearValue(Col value) {
        this.col = value;
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

}

