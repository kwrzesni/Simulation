package maps_elements;

import maps_elements.data_types.Vector2d;

public interface IMapElementObserver {
    void destroyed(IMapElement movedElement, Vector2d position);

    void positionChanged(IMapElement movedElement, Vector2d oldPosition, Vector2d newPosition);
}
