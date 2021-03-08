package render;

import model.Solid;
import transforms.Col;
import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public class Scene{
    private List<Solid> solids = new ArrayList<>();
    private Col color;

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    public List<Solid> getSolids() {
        return solids;
    }

    public void addSolid(Solid solid){
        solids.add(solid);
    }

    public void addSolidList(List<Solid> solidList){
        solids.addAll(solidList);
    }
}
