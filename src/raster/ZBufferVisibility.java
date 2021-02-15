package raster;

import transforms.Col;

public class ZBufferVisibility {
    private ImageBuffer iBuffer;
    private DepthBuffer zBuffer;

    public ZBufferVisibility(int width, int height) {
        iBuffer = new ImageBuffer(width, height);
        zBuffer = new DepthBuffer(width, height);
    }

    public void drawElementWithTest(int x, int y, double z, Col color){
    }
}
