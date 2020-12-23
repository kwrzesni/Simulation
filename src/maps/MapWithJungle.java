package maps;

import maps_elements.IMapElement;
import maps_elements.IMapElementObserver;
import maps_elements.data_types.Vector2d;
import maps_elements.Animal;
import maps_elements.Plant;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;

public class MapWithJungle implements IMapElementObserver {
    private int width;
    private int height;
    private int jungleWidth;
    private int jungleHeight;
    private Vector2d junglePosition;
    private Random random = new Random();

    private final List<Plant> plantList = new ArrayList<Plant>();
    private final List<Animal> animalList = new ArrayList<Animal>();
    private final Map<Vector2d, ArrayList<Animal>> animalMap = new HashMap<Vector2d, ArrayList<Animal>>();
    private final Map<Vector2d, Plant> plantMap = new HashMap<Vector2d, Plant>();

    public MapWithJungle(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        this.jungleWidth = (int) ((double) width * java.lang.StrictMath.sqrt(jungleRatio));
        this.jungleHeight = (int) ((double) height * java.lang.StrictMath.sqrt(jungleRatio));
        this.junglePosition = new Vector2d((width - jungleWidth) / 2, (height - jungleHeight) / 2);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getJungleWidth() {
        return jungleWidth;
    }

    public int getJungleHeight() {
        return jungleHeight;
    }

    public Vector2d getJunglePosition() {
        return junglePosition;
    }

    public void place(IMapElement mapElement) {
        if (mapElement.canMove()) {
            Animal animal = (Animal) mapElement;
            animalList.add(animal);
            ArrayList<Animal> animalsAtNewPositions = animalMap.get(animal.getPosition());
            if(animalsAtNewPositions == null) {
                animalsAtNewPositions = new ArrayList<Animal>();
            }
            animalsAtNewPositions.add(animal);
            animalsAtNewPositions.sort((animal1, animal2) -> animal1.getEnergy() - animal2.getEnergy());
            animalMap.put(animal.getPosition(), animalsAtNewPositions);
        } else {
            Plant plant = (Plant)mapElement;
            plantList.add(plant);
        }
    }

    public void positionChanged(IMapElement movedElement, Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal)movedElement;
        animalList.remove(animal);
        animalMap.get(oldPosition).remove(animal);
        ArrayList<Animal> animalsAtNewPositions = animalMap.get(newPosition);
        if(animalsAtNewPositions == null) {
            animalsAtNewPositions = new ArrayList<Animal>();
        }
        animalsAtNewPositions.add(animal);
        animalsAtNewPositions.sort((animal1, animal2) -> animal1.getEnergy() - animal2.getEnergy());
        animalMap.put(newPosition, animalsAtNewPositions);
    }

    public void destroyed(IMapElement destroyedElement, Vector2d position) {
        if (destroyedElement.canMove()) {
            Animal animal = (Animal)destroyedElement;
            animalList.remove(animal);
            animalMap.get(position).remove(animal);
        }
        else {
            Plant plant = (Plant)destroyedElement;
            plantList.remove(plant);
            plantMap.remove(position);
        }
    }

    public Vector2d getRandomEmptyPlace() {
        Vector2d position;
        do {
            position = new Vector2d(random.nextInt(width), random.nextInt(height));
        } while(!isPlaceEmpty(position));
        return position;
    }

    public Vector2d getJungleEmptyPlace() {
        Vector2d position;
        do {
            position = new Vector2d(random.nextInt(jungleWidth), random.nextInt(jungleHeight));
            position = position.add(junglePosition);
        } while(!isPlaceEmpty(position));
        return position;
    }

    public Vector2d getSteppeEmptyPlace() {

        Vector2d position;
        do {
            position = new Vector2d(random.nextInt(width), random.nextInt(height));
        } while(!isPlaceEmpty(position) || isPlaceJungle(position));
        return position;
    }

    public ArrayList<Vector2d> getFreePlacesArround(Vector2d position) {
        ArrayList<Vector2d> out = new ArrayList<Vector2d>();
        for(int x=position.x-1; x<position.x-1; ++x) {
            if(x<0 || x>width) {
                x = x%width;
            }
            for(int y=position.y-1; y<position.y-1; ++y) {
                if(y<0 || y>height) {
                    y = x%height;
                }
                Vector2d currentPosition = new Vector2d(x, y);
                if(!currentPosition.equals(position) && isPlaceEmpty(currentPosition)){
                    out.add(currentPosition);
                }
            }
        }
        return out;
    }

    boolean isPlaceEmpty(Vector2d position) {
        return animalMap.get(position) == null && plantMap.get(position) == null;
    }

    boolean isPlaceJungle(Vector2d position) {
        Vector2d jungleSecondCorner = junglePosition.add(new Vector2d(jungleWidth, jungleHeight));
        return position.follows(junglePosition) && position.precedes(jungleSecondCorner);
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public ArrayList<Animal> getAnimalsWithMostEnergyAtPosition(Vector2d postition) {
        ArrayList<Animal> animalsAtPostition = animalMap.get(postition);
        if(animalsAtPostition == null) {
            return new ArrayList<Animal>();
        }
        ArrayList<Animal> out = new ArrayList<Animal>();
        int maxEnergy = animalsAtPostition.get(0).getEnergy();
        for(Animal animal : animalsAtPostition) {
            if(animal.getEnergy() > maxEnergy) {
                maxEnergy = animal.getEnergy();
                out = new ArrayList<Animal>();
                out.add(animal);
            } else if(animal.getEnergy() == maxEnergy) {
                out.add(animal);
            }
        }
        return out;
    }

    public Map<Vector2d, ArrayList<Animal>> getAnimalMap() {
        return animalMap;
    }
}
