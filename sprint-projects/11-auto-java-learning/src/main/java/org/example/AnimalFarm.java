package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AnimalFarm {
    private ArrayList<String> farmAnimals;

    public AnimalFarm(ArrayList<String> animals) {
        farmAnimals = animals;
    }

    public HashMap<Animal, Integer> countedAnimals() {
        HashMap<Animal, Integer> counted = new HashMap<>();
        String[] split;

        for (String animal : farmAnimals) {
            try {
                split = animal.split(" ");
                if (counted.containsKey(Animal.valueOf(split[0]))) {
                    counted.put(Animal.valueOf(split[0]), counted.get(Animal.valueOf(split[0])) + 1);
                } else {
                    counted.put(Animal.valueOf(split[0]), 1);
                }
            } catch (Exception exception) {
                System.out.println("Please correct string " + animal + ". Incorrect input data.");
            }
        }

        return counted;
    }

    public HashSet<String> uniqueNames() {
        HashSet<String> names = new HashSet<>();
        String[] split;

        for (String animal : farmAnimals) {
            try {
                split = animal.split(" ");
                if (split[0].isBlank()) {
                    throw new Exception();
                }
                names.add(split[1]);
            } catch (Exception exception) {
                System.out.println("Please correct string " + animal + ". Incorrect input data.");
            }
        }

        return names;
    }

    public void add(Animal animal, String name) {
        farmAnimals.add(animal.toString() + " " + name);
    }

    public void add(Animal animal) {
        farmAnimals.add(animal.toString() + " " + "N");
    }

    public void add(String name) {
        farmAnimals.add(Animal.NOT_DEFINED.toString() + " " + name);
    }

    @Override
    public String toString() {
        String[] split;
        String result = "";

        for (String animal : farmAnimals) {
            try {
                split = animal.split(" ");
                result = String.join("\n", result, split[0] + ":" + split[1]);
            } catch (Exception exception) {
                System.out.println("Please correct string " + animal + ". Incorrect input data.");
            }
        }

        return result;
    }
}
