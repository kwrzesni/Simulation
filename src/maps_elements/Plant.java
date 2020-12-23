package maps_elements;

import maps.AbstractWorldMapElement;
import maps.MapWithJungle;
import maps_elements.data_types.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Plant extends AbstractWorldMapElement implements IMapElementPublisher{
    static private int energy;
    private final List<IMapElementObserver> observers = new ArrayList<IMapElementObserver>();

    public Plant(MapWithJungle map, Vector2d pos)
    {
        addObserver(map);
        this.position = pos;
    }

    public static void setPlantsEnergy(int energy) {
        Plant.energy = energy;
    }

    public static int getEnergy() {
        return energy;
    }

    public boolean canMove() {
        return false;
    }

    public void addObserver(IMapElementObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IMapElementObserver observer) {
        observers.remove(observer);
    }

    public void getEaten(List<Animal> animals) {
        int energyPortion = energy / animals.size();
        for(Animal animal : animals) {
            animal.addEnergy(energyPortion);
        }
        die();
    }

    public void die() {
        for (IMapElementObserver observer : observers){
            observer.destroyed(this, position);
        }
    }
}
