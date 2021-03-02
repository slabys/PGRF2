package model;

import transforms.Col;

public class Texture {

    public static Col getTexel(double x, double y){//x,y interval <0,1>
        int xI = (int) (x * 8);
        int yI = (int) (y * 8);
        if((xI+yI)%2==0){
            return new Col(0,1,1);
        }
        return new Col(1,1,0);
    }
}
