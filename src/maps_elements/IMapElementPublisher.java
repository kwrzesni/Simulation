package maps_elements;

public interface IMapElementPublisher {
    void addObserver(IMapElementObserver observer);

    void removeObserver(IMapElementObserver observer);
}
