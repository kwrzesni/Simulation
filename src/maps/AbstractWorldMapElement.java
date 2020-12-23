package maps;

import maps_elements.IMapElement;
import maps_elements.data_types.Vector2d;

public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2d position = new Vector2d(0, 0);

    public Vector2d getPosition() {
        return position;
    }
}
