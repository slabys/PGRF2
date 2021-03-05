package render;

import model.Solid;
import transforms.Col;
import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public class Scene extends Solid{
    private List<Solid> solids = new ArrayList<>();
    private Mat4 model = new Mat4Identity();
    private Mat4 view = new Mat4Identity();
    private Mat4 projection = new Mat4Identity();
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

    public Scene(Mat4 viewMatrix) {
        setView(viewMatrix);
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public Mat4 getView() {
        return view;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public Mat4 getProjection() {
        return projection;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }
}
