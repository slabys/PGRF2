package render;

import model.Solid;
import transforms.Col;

import java.util.ArrayList;
import java.util.List;

public class Scene{
    private List<Solid> solids = new ArrayList<>();

    public List<Solid> getSolids() {
        return solids;
    }
}
