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

    public void drawElementWithZTest(int x, int y, double z, Col color){
        if (zBuffer.getElement(x, y) != null && z < zBuffer.getElement(x, y)) {
            zBuffer.setElement(x,y,z);
            iBuffer.setElement(x,y,color);
        }
    }

    public ImageBuffer getImage() {
        return iBuffer;
    }

    public void clear(){
        zBuffer.clear();
    }
}
