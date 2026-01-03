package ch.heigvd.logic;

import ch.heigvd.exceptions.NotFoundException;
import ch.heigvd.models.Animal;
import ch.heigvd.models.AnimalGroup;

import java.util.*;

public class AnimalLogic {

    private final Map<Integer, Animal> animals = new HashMap<>();
    private int counter = 1;

    public Animal create(Animal animal) {

        // Validation des champs
        if (animal.getFrenchName() == null || animal.getLatinName() == null || animal.getGroup() == null) {
            throw new IllegalArgumentException("Invalid animal");
        }

        animal.setNumber(counter++);
        animals.put(animal.getNumber(), animal); // Stockage du nouvel animal dans la map
        return animal;
    }

    public Animal getOne(int number) {
        Animal animal = animals.get(number);
        if (animal == null) {
            throw new NotFoundException("Animal not found");
        }
        return animal;
    }

    public List<Animal> getAll(String frenchName, String latinName, AnimalGroup group) {
        List<Animal> result = new ArrayList<>();

        for (Animal animal : animals.values()) {

            if (frenchName != null && !animal.getFrenchName().toLowerCase().contains(frenchName.toLowerCase())) {
                continue;
            }

            if (latinName != null && !animal.getLatinName().toLowerCase().contains(latinName.toLowerCase())) {
                continue;
            }

            if (group != null && animal.getGroup() != group) {
                continue;
            }

            result.add(animal);
        }

        return result;
    }

    public Animal update(int number, Animal animal) {
        if (!animals.containsKey(number)) {
            throw new NotFoundException("Animal not found");
        }

        animal.setNumber(number);
        animals.put(number, animal); // Mise à jour de l'animal dans la map
        return animal;
    }

    public void delete(int number) {
        if (animals.remove(number) == null) {
            throw new NotFoundException("Animal not found");
        }
    }
}
