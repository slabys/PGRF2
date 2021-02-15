package raster;

public interface Raster<V> {

    void clear();

    void setClearValue(V value);

    int getWidth();

    int getHeight();

    V getElement(int x, int y);

    void setElement(int x, int y, V value);

    default boolean checkBorders(int x, int y){
        if(x <= getWidth() && y <= getHeight())
            return true;
        return false;
    }



}
