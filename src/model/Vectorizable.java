package model;

public interface Vectorizable<V> {
    
    V mul(double d);

    V add(V v);

}
