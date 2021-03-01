package raster;

import transforms.Col;

public class ZBufferVisibility {
    private ImageBuffer iBuffer;
    private DepthBuffer zBuffer;

    public ZBufferVisibility(int width, int height) {
        this(new ImageBuffer(width, height));
    }

    public ZBufferVisibility(ImageBuffer imageBuffer) {
        iBuffer = imageBuffer;
        zBuffer = new DepthBuffer(imageBuffer.getWidth(), imageBuffer.getHeight());
    }

    public void drawElementWithTest(int x, int y, double z, Col color){
        iBuffer.setElement(x,y,color);
    }

    public ImageBuffer getImage() {
        return iBuffer;
    }
}
