package render;

import model.Vertex;
import transforms.Col;

public class Triangle {
    Vertex a,b,c;

    public Triangle(Vertex a, Vertex b, Vertex c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Col getColor(){
        return new Col(1,0,0);
    }

    public Vertex getA() {
        return a;
    }

    public Vertex getB() {
        return b;
    }

    public Vertex getC() {
        return c;
    }

}
