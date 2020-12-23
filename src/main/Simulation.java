package main;
import maps.MapWithJungle;
import maps_elements.Animal;
import maps_elements.Plant;
import maps_elements.data_types.Vector2d;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Simulation {
    private Parameters parameters;
    private final MapWithJungle map;
    private final int nStartingAnimals = 10;

    public Simulation(Parameters parameters) {
        map = new MapWithJungle(parameters.width, parameters.height, parameters.jungleRatio);

        Animal.setAnimalsProperites(parameters.startEnergy, parameters.moveEnergy);
        for(int i=0; i<nStartingAnimals; ++i){
            map.place(new Animal(map));
        }

        Plant.setPlantsEnergy(parameters.plantEnergy);
    }

    public void Update() {
        RemoveDeadAnimals();
        MoveAnimals();
        AnimalsEat();
        AnimalsCopulate();
        AddNewPlants();
    }

    private void RemoveDeadAnimals() {
        ArrayList<Animal> animals = new ArrayList<Animal>(map.getAnimalList());
        for(Animal animal : animals) {
            if (!animal.isAlive()) {
                animal.die();
            }
        }
    }

    private void MoveAnimals() {
        ArrayList<Animal> animals = new ArrayList<Animal>(map.getAnimalList());
        for(Animal animal : animals) {
            animal.move();
        }
    }

    private void AnimalsEat() {
        ArrayList<Plant> plants = new ArrayList<Plant>(map.getPlantList());
        for(Plant plant : plants) {
            ArrayList<Animal> eatingAnimals = map.getAnimalsWithMostEnergyAtPosition(plant.getPosition());
            if(eatingAnimals != null) {
                plant.getEaten(eatingAnimals);
            }
        }
    }

    private void AnimalsCopulate() {
        Map<Vector2d, ArrayList<Animal>> animalMap = new HashMap<Vector2d, ArrayList<Animal>>(map.getAnimalMap());
        for(HashMap.Entry<Vector2d, ArrayList<Animal>> entry : animalMap.entrySet()) {
            ArrayList<Animal> animals = entry.getValue();
            if(animals.size() >= 2){
                if(animals.get(0).canCopule() && animals.get(1).canCopule()) {
                    animals.get(0).copulate(animals.get(1));
                }
            }
        }
    }

    public void AddNewPlants() {
        map.place(new Plant(map, map.getJungleEmptyPlace()));
        map.place(new Plant(map, map.getSteppeEmptyPlace()));
    }

    public MapWithJungle getMap() {
        return map;
    }

    public static class Parameters{
        Parameters(String inputFilePath) {
            JSONParser jsonParser = new JSONParser();
            try {
                FileReader reader = new FileReader(inputFilePath);
                Object obj = jsonParser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;
                width = ((Long)jsonObject.get("width")).intValue();
                height = ((Long)jsonObject.get("height")).intValue();
                startEnergy = ((Long)jsonObject.get("startEnergy")).intValue();
                moveEnergy = ((Long)jsonObject.get("moveEnergy")).intValue();
                plantEnergy = ((Long)jsonObject.get("plantEnergy")).intValue();
                jungleRatio = (double)jsonObject.get("jungleRatio");
            }
            catch(FileNotFoundException e){
                System.err.println("File does not exist");
            } catch (IOException e) {
                System.err.println("File exists, but there was IOException");
            } catch (ParseException e) {
                System.err.println("File parse error");
            }
        }
        public int width;
        public int height;
        public int startEnergy;
        public int moveEnergy;
        public int plantEnergy;
        public double jungleRatio;
    }
}
