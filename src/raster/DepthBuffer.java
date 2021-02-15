package raster;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DepthBuffer implements Raster<Double> {
    private final double[][] buffer;
    private Double clearValue = 1.0;
    private int width, height;

    public DepthBuffer(int width, int height) {
        this.buffer = new double[width][height];
        setClearValue(1.);
    }

    @Override
    public void clear() {
        for(double[] num : buffer){
            Arrays.fill(num, clearValue);
        }
    }

    @Override
    public void setClearValue(Double value) {
        this.clearValue = value;
    }

    @Override
    public int getWidth() {
        return buffer.length;
    }

    @Override
    public int getHeight() {
        return buffer[0].length;
    }

    @Override
    public Double getElement(int x, int y) {
        if(checkBorders(x, y)) return buffer[x][y];
        return null;
    }

    @Override
    public void setElement(int x, int y, Double value) {
        if(checkBorders(x, y))
            buffer[x][y] = value;
    }
}
