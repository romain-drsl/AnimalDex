package ch.heigvd.logic;

import ch.heigvd.exceptions.NotFoundException;
import ch.heigvd.models.Animal;
import ch.heigvd.models.AnimalGroup;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimalLogic {

  private final Map<Integer, Animal> animals = new ConcurrentHashMap<>();
  private final AtomicInteger counter = new AtomicInteger(1);

  public Animal create(Animal animal) {

    // Validation des champs
    if (animal.getFrenchName() == null
        || animal.getLatinName() == null
        || animal.getGroup() == null) {
      throw new IllegalArgumentException("Invalid animal");
    }

    // Attribution d'un numéro unique
    animal.setNumber(counter.getAndIncrement());
    // Stockage du nouvel animal dans la map
    animals.put(animal.getNumber(), animal);
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

      if (frenchName != null
          && !animal.getFrenchName().toLowerCase().contains(frenchName.toLowerCase())) {
        continue;
      }

      if (latinName != null
          && !animal.getLatinName().toLowerCase().contains(latinName.toLowerCase())) {
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
    if (animal.getFrenchName() == null
        || animal.getLatinName() == null
        || animal.getGroup() == null) {
      throw new IllegalArgumentException("Invalid animal");
    }

    animal.setNumber(number);
    if (animals.replace(number, animal) == null) {
      throw new NotFoundException("Animal not found");
    }
    return animal;
  }

  public void delete(int number) {
    if (animals.remove(number) == null) {
      throw new NotFoundException("Animal not found");
    }
  }
}
