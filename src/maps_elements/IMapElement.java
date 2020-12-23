package maps_elements;

import maps_elements.data_types.Vector2d;

public interface IMapElement {
    Vector2d getPosition();

    boolean canMove();
}
