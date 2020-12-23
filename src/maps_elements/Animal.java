package maps_elements;

import maps.AbstractWorldMapElement;
import maps_elements.data_types.Vector2d;
import maps_elements.data_types.Direction;
import maps_elements.data_types.Genome;
import maps.MapWithJungle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractWorldMapElement implements IMapElementPublisher{
    private static int startEnergy;
    private static int moveEnergy;
    private static int copulationEnergy;
    private static Random random = new Random();

    private final List<IMapElementObserver> observers = new ArrayList<IMapElementObserver>();
    private final MapWithJungle map;
    private final Genome genome;

    private Direction direction;
    private int energy;


    public Animal(MapWithJungle map) {
        this(map, map.getRandomEmptyPlace(), startEnergy, new Genome());
    }

    private Animal(MapWithJungle map, Vector2d position, int energy, Genome genome) {
        this.map = map;
        addObserver(map);
        this.position = position;
        this.energy = energy;
        direction = Direction.intToDirection(random.nextInt(Direction.nDirections));
        this.genome = genome;
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IMapElementObserver observer : observers){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    public void die() {
        for (IMapElementObserver observer : observers){
            observer.destroyed(this, position);
        }
    }

    public void addObserver(IMapElementObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IMapElementObserver observer) {
        observers.remove(observer);
    }

    public void move() {
        int gene = genome.getRandomGene();
        direction = direction.rotate(gene);

        Vector2d oldPostion = position;
        position = position.add(direction.toUnitVector());
        if(position.x < 0 || position.x >= map.getWidth()) {
            position.x = position.x % map.getWidth();
        }
        if(position.y < 0 || position.y >= map.getHeight()) {
            position.y = position.y % map.getHeight();
        }
        positionChanged(oldPostion, position);
        energy -= moveEnergy;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void copulate(Animal otherAnimal) {
        Genome crossedGenome = Genome.crrosGenes(genome, otherAnimal.genome);
        int usedEnergy1 = energy/4;
        int usedEnergy2 = otherAnimal.energy/4;
        energy -= usedEnergy1;
        otherAnimal.energy -= usedEnergy2;

        ArrayList<Vector2d> freePositonsArround = map.getFreePlacesArround(position);
        Vector2d childPosition;
        if(freePositonsArround.isEmpty()) {
            childPosition =  new Vector2d((position.x+1)%map.getWidth(), position.y);
        } else {
            childPosition = freePositonsArround.get(random.nextInt(freePositonsArround.size()));
        }

        new Animal(map, childPosition, usedEnergy1 + usedEnergy2, crossedGenome);
    }

    public boolean canCopule() {
        return energy >= copulationEnergy;
    }

    public static void setAnimalsProperites(int startEnergy, int moveEnergy) {
        Animal.startEnergy = startEnergy;
        Animal.moveEnergy = moveEnergy;
        Animal.copulationEnergy = startEnergy/2;
    }

    public boolean canMove() {
        return true;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public int getEnergy() {
        return energy;
    }

}
